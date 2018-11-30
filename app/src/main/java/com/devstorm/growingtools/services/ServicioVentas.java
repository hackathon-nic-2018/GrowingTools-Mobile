package com.devstorm.growingtools.services;

import com.devstorm.growingtools.POJOS.MesVenta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicioVentas {
String API_ROUT = "api/Login/VentasMeses";

@GET(API_ROUT)
    Call<List<MesVenta>> getListCall(@Query("Empresa") String Empresa,@Query("producto") String producto);
}
