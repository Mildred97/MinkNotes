package com.example.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class Notas extends AppCompatActivity {

    //Se declara arraylist y un arrayadapter fuera del metodo onCreate para que sean globales
    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();  //Infla el menu, es decir la vista para agregar notas
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        //Si se hace click en agregar nota, se lanza el activity NoteEditor
        if(item.getItemId() == R.id.add_note)
        {
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);


        //ListView de la interfaz
        ListView listView = (ListView)findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.menu", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("notes", null);

        if(set == null)
        {
            notes.add("Nota de ejemplo");
        }

        else
        {
            notes = new ArrayList<>(set);
        }

        //El Array adapter enlaza el arraylist y la listview
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);


        //Al hacer click sobre una nota,se lanza la activity Note Editor
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteID", position);   //Nos indica la posicion donde se clickeo para poder editar
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //Ejecuta el codigo para eliminar cuando se presiona por largo tiempo el item de la nota
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                new AlertDialog.Builder(Notas.this)                   // La actividad "Notas" es el context
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Eliminar")
                        .setMessage("¿Estas seguro que quieres eliminar esta nota?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {  // Borra la nota al presionar "si"
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged(); //Actualiza el listView

                                //Para guardar las notas en la aplicacion, cuando se ha eliminado alguna.

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.menu", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(Notas.notes);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}



