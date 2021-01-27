package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);


        //Metodo para modificar el texto
        EditText editText = (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);  //El valor por defecto es -1
        //El EditText obtiene el valor con el intent
        if(noteID != -1)
        {
            editText.setText(Notas.notes.get(noteID));
        }

        //De lo contrario significa que la nota esta vacia
        else
        {
            Notas.notes.add("");                // La nota esta vacia
            noteID = Notas.notes.size() - 1;
            Notas.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Notas.notes.set(noteID, String.valueOf(s));
                Notas.arrayAdapter.notifyDataSetChanged();   //Actualiza el ListView cuando se modifica la nota


                //Para guardar las notas en la aplicacion y esten cada vez que la usemos
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.menu", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(Notas.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }
}

