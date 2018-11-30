package com.devstorm.growingtools.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devstorm.growingtools.POJOS.MesVenta;
import com.devstorm.growingtools.POJOS.ResD;
import com.devstorm.growingtools.R;
import com.devstorm.growingtools.services.ServicioRes;
import com.devstorm.growingtools.services.ServicioVentas;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BarChart lineChart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  String Producto1;
    private  String Producto2;
    private  Context context;
    private ProgressBar progressBar;
    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public BarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarFragment newInstance(String param1, String param2) {
        BarFragment fragment = new BarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bar, container, false);
                lineChart = v.findViewById(R.id.barchart);
                progressBar = v.findViewById(R.id.progressbarventa);
                GetProductos();
                return v ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void GetObjects(String p1,String p2,Context c){
        Producto1=p1;
        Producto2 =p2;
        context =c;
    }

    public void GetProductos(){
        //Get empresa
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String Empresa = sharedPreferences.getString("Empresa","");


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gcapi20181127075037.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ServicioRes servicioVentas = retrofit.create(ServicioRes.class);
        Call<ResD> call = servicioVentas.getPost(Empresa);
        call.enqueue(new Callback<ResD>() {
            @Override
            public void onResponse(Call<ResD> call, Response<ResD> response) {
                //Dataline 1
                List<BarEntry> Line1 = new ArrayList<BarEntry>();
                //
                List<BarEntry> Line2 = new ArrayList<BarEntry>();
                int x = 0;

                    Line1.add(new BarEntry(0f,response.body().getCosto()));
                    Line2.add(new BarEntry(1f,response.body().getVenta()));


                BarDataSet datasetline1 = new BarDataSet(Line1,"Costos");
                BarDataSet datasetline2 = new BarDataSet(Line2,"Ventas");



                BarData datasetCollection  = new BarData(datasetline1,datasetline2);

                //Coleccion de lineas

                datasetCollection.setBarWidth(0.9f);
                lineChart.setData(datasetCollection);
                lineChart.setFitBars(true);
                lineChart.invalidate();

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResD> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
