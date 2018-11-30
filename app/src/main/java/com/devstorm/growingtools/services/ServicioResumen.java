package com.devstorm.growingtools.services;

import com.devstorm.growingtools.POJOS.Resumen;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServicioResumen {

    @POST("api/Login/Resumen")
    Call<Resumen> getPost(@Query("Empresa") String Empresa);
}
