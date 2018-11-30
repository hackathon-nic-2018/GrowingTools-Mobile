package com.devstorm.growingtools.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicioLogin {
     String API_ROUT = "/api/Login/Login";
    @GET(API_ROUT)
    Call<String> getStringCall(@Query("Nombre")String nombre,
                               @Query("Contraseña") String contra, @Query("Empresa") String empresa
    );
}
