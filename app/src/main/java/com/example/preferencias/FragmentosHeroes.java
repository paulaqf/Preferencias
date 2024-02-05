package com.example.preferencias;

import android.os.Bundle;

import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;

public class FragmentosHeroes extends PreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}