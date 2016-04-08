package com.example.rubn29.culebrapmdm;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Clase que llama a la pantalla inicial del juego
 **/
public class MainActivity extends AppCompatActivity {
    public static final int juegoIniciar = 1;
    private MediaPlayer music;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.actividad_principal);


        Button boton = (Button) findViewById(R.id.botonIniciar);

        music = MediaPlayer.create(MainActivity.this, R.raw.musica);
        music.setLooping(true);
        music.start();

    }

    /**
     * Metodo para iniciar el juego enlazado con el boton jugar
     **/
    public void iniciar(View view) {
        setContentView(new MetodosCulebra(this));
    }

    /**
     * Metodo para salir con el boton salir
     **/
    public void salir(View view) {
        System.exit(0);


    }


}