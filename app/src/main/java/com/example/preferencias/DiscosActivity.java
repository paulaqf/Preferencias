package com.example.preferencias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class DiscosActivity extends AppCompatActivity {
    EditText txtGrupo, txtDisco;
    Button btnAñadir, btnBorrar;
    ListView lvDiscos;
    SQLiteDatabase db;
    Button botonAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discos);
        txtGrupo = findViewById(R.id.editTextGrupo);
        txtDisco = findViewById(R.id.editTextDisco);
        btnAñadir = findViewById(R.id.buttonAñadirDisco);
        btnBorrar = findViewById(R.id.buttonBorrarDisco);
        lvDiscos = findViewById(R.id.listaBD);
        botonAtras = findViewById(R.id.buttonAtras);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) lvDiscos.getAdapter();
                if (adapter.getCount() > 0) {
                    String lastItem = adapter.getItem(adapter.getCount() - 1);
                    i.putExtra("lastItem", lastItem);
                    Log.d("DiscosActivity", "Last item: " + lastItem);
                }
                setResult(RESULT_OK, i);
                finish();
            }
        });
        Intent intent = getIntent();
        txtGrupo.setText(intent.getStringExtra("grupo"));
        txtDisco.setText(intent.getStringExtra("disco"));

        db = openOrCreateDatabase("discos", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS discos (grupo VARCHAR, disco VARCHAR)");
        listarDiscos();

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirDisco();
                listarDiscos();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borraDisco();
                listarDiscos();
            }
        });
    }

    public void añadirDisco() {
        String grupo = txtGrupo.getText().toString();
        String disco = txtDisco.getText().toString();
        db.execSQL("INSERT INTO discos VALUES ('" + grupo + "','" + disco + "')");
        Toast.makeText(this, "Disco añadido", Toast.LENGTH_SHORT).show();
        txtGrupo.setText("");
        txtDisco.setText("");
    }

    public void borraDisco() {
        String grupo = txtGrupo.getText().toString();
        db.execSQL(
                "DELETE FROM discos WHERE grupo='" + grupo + "'" + "AND disco='" + txtDisco.getText().toString() + "'");
        Toast.makeText(this, "Disco borrado", Toast.LENGTH_SHORT).show();
        txtGrupo.setText("");
        txtDisco.setText("");
    }

    public void listarDiscos() {
        Cursor c = db.rawQuery("SELECT * FROM discos", null);
        List<String> lista = new ArrayList<String>();
        if (c.getCount() == 0) {
            Toast.makeText(this, "No hay discos", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                lista.add(c.getString(0) + " - " + c.getString(1));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        lvDiscos.setAdapter(adapter);
    }

}