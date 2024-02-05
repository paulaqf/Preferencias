package com.example.preferencias;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;

public class FragmentoDatosPersonales extends PreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.datos_personales, rootKey);
    }



}