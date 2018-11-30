package com.devstorm.growingtools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.devstorm.growingtools.POJOS.Inventario;
import com.devstorm.growingtools.adapterss.RecyclerViewAdapter;
import com.devstorm.growingtools.models.RecyclerViewModel;
import com.devstorm.growingtools.services.ServicioEmpresas;
import com.devstorm.growingtools.services.ServicioInventario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InventoryActivity extends AppCompatActivity {


    private LinearLayout linearLayout;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private List<RecyclerViewModel> list;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar ;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        //llamadas al layout
        progressBar = findViewById(R.id.progressinventoryload);
        recyclerView = findViewById(R.id.recyclerviewinventory);
        list = new ArrayList<>();
        //Encender barra
        progressBar.setVisibility(View.VISIBLE);
        activity =this;
        CargarInventario();

    }

    //llamados a la API
    private void CargarInventario(){
        String empresa = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        empresa = sharedPreferences.getString("Empresa","");
        Log.w("EMPRESA", empresa);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gcapi20181127075037.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServicioInventario services = retrofit.create(ServicioInventario.class);

        Call<List<Inventario>> call  = services.getPost(empresa);
        call.enqueue(new Callback<List<Inventario>>() {
            @Override
            public void onResponse(Call<List<Inventario>> call, Response<List<Inventario>> response) {
                for (Inventario item : response.body()) {

                    list.add(new RecyclerViewModel(item.getPrecio_venta(),item.getCantidad(),item.getCategoria(),item.getCosto(),R.drawable.xddd,item.getNombre(),item.getNombreProveedor(),item.getID_inventario()));
                    Log.w("INVSUCCESS",""+item.getCategoria());
                }
                recyclerViewAdapter = new RecyclerViewAdapter(R.id.recyclerviewinventory,list,activity);
                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Inventario>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.w("INVFAIL",""+t.getMessage());
            }
        });
    }





}
