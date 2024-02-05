package com.example.preferencias;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button boton, buttonAlarma;
    InputStream in;
    BufferedReader bf;
    Toolbar toolbar;
    TextView texto, inputDisco, inputGrupo;
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton = findViewById(R.id.button);
        buttonAlarma = findViewById(R.id.buttonAlarma);

        buttonAlarma.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, (view, hourOfDay, minute) -> setAlarma(hourOfDay, minute), 0, 0, true);
            timePickerDialog.show();
        });
        texto = findViewById(R.id.textViewArchivo);
        inputDisco = findViewById(R.id.editTextSendDisco);
        inputGrupo = findViewById(R.id.editTextSendGrupo);
        boton.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, DiscosActivity.class), 1);
            mostrar();
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Resources res = getResources();
        in = res.openRawResource(R.raw.archivo);
        bf = new BufferedReader(new InputStreamReader(in));

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // Aquí se están recogiendo los datos de la segunda actividad
                            texto.setText(data.getStringExtra("lastItem"));
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ppal, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opcion1) {
            Intent i = new Intent(this, MisFragmentPreferencias.class);
            startActivity(i);
            mostrar();
            return true;
        } else if (item.getItemId() == R.id.opcion2) {

            Intent i = new Intent(this, DiscosActivity.class);
            i.putExtra("grupo", inputGrupo.getText().toString());
            i.putExtra("disco", inputDisco.getText().toString());
            launcher.launch(i);
            return true;
        } else if (item.getItemId() == R.id.opcion3) {

            Intent i = new Intent(this, MusicaActivity.class);

            launcher.launch(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void mostrar() {
        String cad;
        String texto = "";
        try {
            while ((cad = bf.readLine()) != null) {
                texto += cad + "\n";
            }
            Toast toast = Toast.makeText(this, texto, Toast.LENGTH_SHORT);
            toast.show();

        } catch (IOException e) {
            Log.e("IOException", "Error de lectura");
        }
    }

    public void setAlarma(int hora, int minutos){
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minutos);
        /*Crear alarma */
        Intent intent= new Intent(this, Alarma.class);
        alarmIntent= PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr= (AlarmManager)
                this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        Toast.makeText(this, "Alarma programada a las "+hora+":"+minutos, Toast.LENGTH_SHORT).show();
    }
}