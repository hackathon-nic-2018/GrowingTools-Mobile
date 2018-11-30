package com.devstorm.growingtools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devstorm.growingtools.services.ServicioEmpresas;
import com.devstorm.growingtools.services.ServicioLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    //Variables para las vistas
    private Button button;
    private Intent puente;
    private Spinner spinner;
    private List<String> Empresas;
    private ProgressBar progressBar;
    private EditText user;
    private EditText contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loggued = sharedPreferences.getString("User","");
        if(loggued!="") {
            puente = new Intent(getApplicationContext(), LauncherActivity.class);
            startActivity(puente);
        }
        //Escondiendo la barra de accion
        getSupportActionBar().hide();

        //Rellenando las vistas
        button = (Button) findViewById(R.id.ButtonLogin);
        user = findViewById(R.id.txtuserlogin);
        contra = findViewById(R.id.txtcontralogin);
        spinner = (Spinner) findViewById(R.id.spinneempresas);
        progressBar=(ProgressBar) findViewById(R.id.progressLogin);
        //Listeners de las vistas
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String loggued = sharedPreferences.getString("User","");
                    progressBar.setVisibility(View.VISIBLE);
                    GetLogin();
            }
        });

        //Llamada a la api para obtener las empresas
        //Llenando las empresas
        progressBar.setVisibility(View.VISIBLE);
        Empresas = new ArrayList<>();
        GetEmpresas();
    }


    //Llamados a la api

    private void GetEmpresas(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gcapi20181127075037.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ServicioEmpresas services = retrofit.create(ServicioEmpresas.class);
        Call<List<String>> call = services.getPost();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                for(String item :response.body()){
                    Empresas.add(item);
                }
                ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, Empresas);
                spinner.setAdapter(arrayAdapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    private void GetLogin(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gcapi20181127075037.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ServicioLogin service = retrofit.create(ServicioLogin.class);
        Call<String> call = service.getStringCall(user.getText().toString(),contra.getText().toString(),spinner.getSelectedItem().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("OK")){
                    //Guardando empresa,idempresa,usuario y codigo de usuario logueado
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Empresa",spinner.getSelectedItem().toString());
                    editor.putString("User",user.getText().toString());
                    editor.commit();

                    //Sacandome a la verga del login
                    Toast.makeText(getApplicationContext(),"Bienvenido :)",Toast.LENGTH_LONG).show();
                    Intent puente = new Intent(getApplicationContext(),LauncherActivity.class);
                    progressBar.setVisibility(View.GONE);
                    startActivity(puente);
                    Log.w("Logindc","Loggued");
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
