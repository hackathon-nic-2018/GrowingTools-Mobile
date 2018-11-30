package com.devstorm.growingtools.services;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServicioVender {
    String API_ROUT = "api/Login/VenderAndroid";
    @POST(API_ROUT)
    Call<String> getStringCall(@Query("ID_inventario") int ID_inventario
    ,@Query("Cliente") String Cliente,@Query("Empresa") String Empresa,@Query("user") String user
    ,@Query("cantidad") int cantidad,@Query("contra") String contra);

}
