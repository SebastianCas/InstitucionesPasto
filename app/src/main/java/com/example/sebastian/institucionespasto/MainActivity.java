package com.example.sebastian.institucionespasto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sebastian.institucionespasto.api.Datos;
import com.example.sebastian.institucionespasto.modelos.Adaptador;
import com.example.sebastian.institucionespasto.modelos.Institucion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ProgressBar barra;
    public final static String TAG="DATOS INSTITUCIONES";
    public final static int TIEMPO=1000;
    private Boolean activo;

    private RecyclerView view;
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barra=(ProgressBar)findViewById(R.id.barra);

        view=(RecyclerView)findViewById(R.id.view);
        view.setLayoutManager(new LinearLayoutManager(this));

        retrofit=new Retrofit.Builder().baseUrl("https://www.datos.gov.co/resource/").addConverterFactory(GsonConverterFactory.create()).build();


    }

    public void progreso(final int passed){
        if (null!=barra){
            final int progres=barra.getMax()*passed/TIEMPO;
            barra.setProgress(progres);
        }
    }

    public void onContinue(){

        Log.i(TAG,"Cargado!!!");
    }



    public void obtenerDatos(View v){

        Datos service=retrofit.create(Datos.class);
        final Call<List<Institucion>> institucionCall=service.obtenerListaInstituciones();

        institucionCall.enqueue(new Callback<List<Institucion>>() {
            @Override
            public void onResponse(Call<List<Institucion>> call, Response<List<Institucion>> response) {

                if (response.isSuccessful()){
                    List institucion=response.body();
                    for (int i=0; i<institucion.size(); i++){
                        Institucion ins=(Institucion) institucion.get(i);
                        adaptador = new Adaptador(institucion);
                        view.setAdapter(adaptador);

                       // Log.i(TAG,"Nombre: "+ins.getNombreEstablecimiento()+" Municipio: "+ins.getMunicipio());
                    }
                }
                else {

                    Toast notificacion = Toast.makeText(MainActivity.this,getString(R.string.Response),Toast.LENGTH_LONG);
                    notificacion.show();
                    //Log.e(TAG,"OnResponse "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Institucion>> call, Throwable t) {

                Toast notificacion = Toast.makeText(MainActivity.this,getString(R.string.Fail),Toast.LENGTH_LONG);
                notificacion.show();
                //Log.e(TAG,"OnFailure "+t.getMessage());
            }
        });


        final Thread timeThread=new Thread(){
            @Override
            public void run(){
                activo=true;
                try {
                    int waited=0;
                    while (activo&&(waited<TIEMPO)){
                        sleep(200);
                        if (activo){
                            waited+=200;
                            progreso(waited);
                        }
                    }
                }catch (InterruptedException e){

                }finally {
                    onContinue();
                }
            }
        };
        timeThread.start();
    }

    public void acerca(View v){
        Intent i = new Intent(this, acerca.class );
        startActivity(i);
    }
}
