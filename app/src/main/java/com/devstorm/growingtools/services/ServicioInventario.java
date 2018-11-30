package com.devstorm.growingtools.services;

import com.devstorm.growingtools.POJOS.Inventario;
import com.devstorm.growingtools.models.RecyclerViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicioInventario {
    @GET("/api/Login/Inventario")
    Call<List<Inventario>> getPost(@Query("Empresa")String nombre);
}
