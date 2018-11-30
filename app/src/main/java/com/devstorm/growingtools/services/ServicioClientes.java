package com.devstorm.growingtools.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicioClientes {
    String API_ROUT = "api/Login/Clientes";
    @GET(API_ROUT)
    Call<List<String>> getListCall(@Query("Empresa") String Empresa);

}
