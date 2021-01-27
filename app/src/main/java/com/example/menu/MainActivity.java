package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Colocar icono de la app en el action bar

        getSupportActionBar() .setDisplayShowHomeEnabled(true);
        getSupportActionBar() .setIcon(R.mipmap.ic_launcher);
    }
    //Metodo de los botones

    //Lanzar activity de lector pdf
    public void Libros(View view) {
        Intent libros = new Intent (this,PDF.class);
        startActivity(libros);

    }

    //Lanzar activity de notas
    public void Escribir(View view) {
        Intent escribir = new Intent (this,Notas.class);
        startActivity(escribir);

    }


}