package com.example.preferencias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

public class MisFragmentPreferencias extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target){
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.preferences_headers,target);
    }

    @Override
    protected boolean isValidFragment (String fragmentName) {
        if (FragmentoDatosPersonales.class.getName().equals(fragmentName)) return true;
        return false;
    }

}