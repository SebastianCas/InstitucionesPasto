package com.example.sebastian.institucionespasto;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class Screen extends AppCompatActivity {

    private final int DURACION_SPLASH = 3000;
    public final static String TAG="DATOS INSTITUCIONES";
    private ProgressBar bar;
    public final static int TIEMPO=3000;
    private Boolean activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        bar=(ProgressBar)findViewById(R.id.bar);

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

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(Screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DURACION_SPLASH);

    }

    public void progreso(final int passed){
        if (null!=bar){
            final int progres=bar.getMax()*passed/TIEMPO;
            bar.setProgress(progres);
        }
    }

    public void onContinue(){

        Log.i(TAG,"Cargado!!!");
    }

}
