package com.narcoding.chaturta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Profil extends ActionBarActivity {

    private final static int SELECT_PHOTO = 12345;

    Kullanici kullanici;
    SharedPreferences mPrefs;

    TextView txt_profile_username;
    ImageButton imgbtn_profile_image;

    private void init(){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        kullanici = gson.fromJson(mPrefs.getString("kullanici", ""), Kullanici.class);

        txt_profile_username= (TextView) findViewById(R.id.txt_profile_username);
        txt_profile_username.setText(kullanici.KullaniciAdi);

        imgbtn_profile_image= (ImageButton) findViewById(R.id.imgbtn_profile_image);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        init();


        imgbtn_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);


                Toast.makeText(getApplicationContext(), "Profil Resmi Yükleyin", Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            imgbtn_profile_image.setBackground(null);
            imgbtn_profile_image.setImageBitmap(bitmap);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

}
