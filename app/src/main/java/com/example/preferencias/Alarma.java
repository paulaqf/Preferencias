package com.example.preferencias;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Alarma extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"La lógica de negocio irá aquí. ",Toast.LENGTH_LONG).show();
    }
}

