package com.narcoding.chaturta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Profil extends ActionBarActivity {

    Kullanici kullanici;
    SharedPreferences mPrefs;

    TextView txt_profile_username;
    ImageButton btn_profile_image;

    private void init(){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        kullanici = gson.fromJson(mPrefs.getString("kullanici", ""), Kullanici.class);

        txt_profile_username= (TextView) findViewById(R.id.txt_profile_username);
        txt_profile_username.setText(kullanici.KullaniciAdi);

        btn_profile_image= (ImageButton) findViewById(R.id.btn_profile_image);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        init();

        btn_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Profil Resmi Yükleyin",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
