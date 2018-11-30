package com.devstorm.growingtools.services;

        import com.devstorm.growingtools.POJOS.ResD;
        import com.devstorm.growingtools.POJOS.Resumen;

        import retrofit2.Call;
        import retrofit2.http.POST;
        import retrofit2.http.Query;

public interface ServicioRes {
    @POST("api/Login/Ultimahope")
    Call<ResD> getPost(@Query("Empresa") String Empresa);

}
