package com.devstorm.growingtools.adapterss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devstorm.growingtools.R;
import com.devstorm.growingtools.models.RecyclerViewModel;
import com.devstorm.growingtools.services.ServicioClientes;
import com.devstorm.growingtools.services.ServicioVender;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
   //Construir el card
    private int Resourcer= 0;
    private List<RecyclerViewModel> list;

    public RecyclerViewAdapter(int resourcer, List<RecyclerViewModel> list, Activity activity) {
        Resourcer = resourcer;
        this.list = list;
        this.activity = activity;
    }

    private Activity activity;

    //Para controlar la venta
    private Button confirmarventa = null;
    private ViewGroup viewGroup;
    private Spinner spinner;
    private ArrayList<String> clientes;
    private ProgressBar progressBar;
    private int maximoavender;
    private String Descontinuado;
    private TextView cantidad = null;
    private TextView contraseñaventa = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflando la vista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlayoutmodel,parent,false);
        viewGroup = parent;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RecyclerViewModel recyclerViewModel;

        recyclerViewModel = list.get(position);
        holder.nombre.setText("Nombre : "+ recyclerViewModel.getNombre());
        holder.cantidad.setText("Cantidad : "+ String.valueOf(recyclerViewModel.getCantidad()));
        holder.precio.setText("Precio unitario : "+ String.valueOf(recyclerViewModel.getPrecio())+"$");
        holder.costo.setText("Costo : "+ String.valueOf(recyclerViewModel.getCosto())+"$");
        holder.proveedor.setText("Proveedor : "+recyclerViewModel.getNombreProveedor());
        holder.categoria.setText("Categoria : "+recyclerViewModel.getCategoria());
        holder.ID.setText("ID : "+String.valueOf(recyclerViewModel.getID()));
       //ventas
        holder.floatingActionButtonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.w("RECYCLERMAX", (String.valueOf(tv.getText())));
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                View view = activity.getLayoutInflater().inflate(R.layout.sellmodellayout,null);
                //Llenando los elementos necesarios
                spinner = (Spinner) view.findViewById(R.id.cbclientes);
                progressBar = (ProgressBar) view.findViewById(R.id.progressbarventa);
                cantidad = (TextView) view.findViewById(R.id.cantidadavender);
                confirmarventa = (Button) view.findViewById(R.id.buttonVender);

                cantidad.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString()!=""){
                            try{
                                if(Integer.parseInt(s.toString())>recyclerViewModel.getCantidad()){
                                    confirmarventa.setEnabled(false);
                                }else{
                                    confirmarventa.setEnabled(true);
                                }
                            }catch(NumberFormatException e){
                                Log.w("NUMBERFORMATEXCEPTION",e.getMessage());
                                confirmarventa.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                confirmarventa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!confirmarventa.isEnabled()){

                        }else{
                            progressBar.setVisibility(View.VISIBLE);
                            String Empresa = "";
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                            Empresa = sharedPreferences.getString("Empresa","");
                            PostVender(recyclerViewModel.getID(),spinner.getSelectedItem().toString(),Empresa,"Android app",Integer.parseInt(cantidad.getText().toString()));
                        }
                    }
                });

                progressBar.setVisibility(View.VISIBLE);
                clientes = new ArrayList<String>();
                GetClientes();

                mBuilder.setView(view);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //Cargado de las vistas
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagen;
        private TextView nombre;
        private TextView precio;
        private TextView costo;
        private TextView proveedor;
        private TextView categoria;
        private TextView cantidad;
        private TextView ID;
        private FloatingActionButton floatingActionButtonl;

        public ViewHolder(View itemView) {

            super(itemView);

            imagen = itemView.findViewById(R.id.imageviewsliding);
            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.preciounitario);
            costo = itemView.findViewById(R.id.costo);
            proveedor = itemView.findViewById(R.id.proveedor);
            categoria = itemView.findViewById(R.id.categoria);
            cantidad = itemView.findViewById(R.id.cantidad);
            ID = itemView.findViewById(R.id.ID);
            floatingActionButtonl = itemView.findViewById(R.id.floatingsell);

        }
    }

    //Llamados a la api
    public void GetClientes(){
        String Empresa = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        Empresa = sharedPreferences.getString("Empresa","");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gcapi20181127075037.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServicioClientes services = retrofit.create(ServicioClientes.class);
        Call<List<String>> call = services.getListCall(Empresa);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                for (String item: response.body()) {
                    clientes.add(item);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(activity.getApplicationContext(),android.R.layout.simple_spinner_item,clientes);
                spinner.setAdapter(arrayAdapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.w("ERRORCLIENTES",t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void PostVender(int ID,String Cliente,String Empresa,String user,int cantidad){
        Retrofit retrofit = new  Retrofit.Builder().
        baseUrl("http://gcapi20181127075037.azurewebsites.net")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
        ServicioVender service = retrofit.create(ServicioVender.class);
        Call<String> call = service.getStringCall(ID,Cliente,Empresa,user,cantidad);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(activity.getApplicationContext(),response.body(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(activity.getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}
