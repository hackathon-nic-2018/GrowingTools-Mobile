package com.devstorm.growingtools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.devstorm.growingtools.POJOS.Inventario;
import com.devstorm.growingtools.POJOS.Resumen;
import com.devstorm.growingtools.adapterss.RecyclerViewAdapter;
import com.devstorm.growingtools.models.RecyclerViewModel;
import com.devstorm.growingtools.services.ServicioInventario;
import com.devstorm.growingtools.services.ServicioResumen;
import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadoActivity extends AppCompatActivity {

    private TextView mejorcliente;
    private TextView  productomasvendido;
    private TextView  productomayorrotacion;
    private TextView  ganancias;
    private ProgressBar progressBar;
    private FloatingActionButton statisticButtom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        //Barra
        getSupportActionBar().setTitle("Resultados");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       //LoadVistas
        mejorcliente = findViewById(R.id.mejorcliente);
        productomasvendido = findViewById(R.id.productomasvendido);
        productomayorrotacion = findViewById(R.id.productomayorrotacion);
        ganancias = findViewById(R.id.gananciasAlafecha);
        statisticButtom = findViewById(R.id.floatingActionButton);
        progressBar = findViewById(R.id.progressResultado);
        progressBar.setVisibility(View.VISIBLE);
        GetResumen();
        //Listeners de eventos
       statisticButtom.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent puente = new Intent(getApplicationContext(),PlusActivity.class);
               startActivity(puente);
           }
       });
    }

    public void GetResumen(){

        String empresa = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        empresa = sharedPreferences.getString("Empresa","");
        Log.w("EMPRESA", empresa);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gcapi20181127075037.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServicioResumen services = retrofit.create(ServicioResumen.class);

        Call<Resumen> call  = services.getPost(empresa);
        call.enqueue(new Callback<Resumen>() {
            @Override
            public void onResponse(Call<Resumen> call, Response<Resumen> response) {

                Resumen re = response.body();

                try{
                    //re.getUnidadesMasVendidad();
                    mejorcliente.setText(re.getMejorCliente().toString());
                    productomasvendido.setText(response.body().getUnidadesMasVendidad());
                    productomayorrotacion.setText(response.body().getMayorMovimiento());
                    ganancias.setText(String.valueOf((-1)*response.body().getGanancias()) +"$");
                    progressBar.setVisibility(View.GONE);
                }catch (Exception er){
                    Log.w("RESPONDE",er.getMessage());
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Resumen> call, Throwable t) {
                Log.w("RESPONDE",t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
