package com.example.sebastian.institucionespasto.api;

import com.example.sebastian.institucionespasto.modelos.Institucion;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sebastian on 11/10/17.
 */

public interface Datos {

    @GET("5dgd-hu6w.json")
    Call<List<Institucion>> obtenerListaInstituciones();
}
