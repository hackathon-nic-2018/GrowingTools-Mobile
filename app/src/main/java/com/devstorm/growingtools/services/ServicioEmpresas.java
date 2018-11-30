package com.devstorm.growingtools.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicioEmpresas {

     String API_ROUT = "/api/Login/Empresas";

     @GET(API_ROUT)
     Call<List<String>> getPost();



}
