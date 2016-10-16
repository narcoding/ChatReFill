package com.narcoding.chaturta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.google.gson.Gson;

public class Profil extends ActionBarActivity {

    Kullanici kullanici;
    SharedPreferences mPrefs;

    TextView txt_profile_username;

    private void init(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        kullanici = gson.fromJson(mPrefs.getString("kullanici", ""), Kullanici.class);

        txt_profile_username= (TextView) findViewById(R.id.txt_profile_username);
        txt_profile_username.setText(kullanici.KullaniciAdi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        init();

    }
}
