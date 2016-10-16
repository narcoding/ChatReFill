package com.narcoding.chaturta;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class Profil extends ActionBarActivity {

    Kullanici kullanici;

    private void init(){
        //kullanici=new Kullanici();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

    }
}
