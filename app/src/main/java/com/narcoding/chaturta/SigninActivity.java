package com.narcoding.chaturta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.socket.client.Socket;

public class SigninActivity extends Activity implements View.OnClickListener {

    private EditText edittext_edumail;
    private EditText edittext_username;
    private EditText edittext_password;
    private EditText edittext_new_password;
    private EditText edittext_repeat_new_password;

    private String str_edumail;
    private String str_password;
    private String str_username;

    private int KullaniciId;

    private Button btn_sign_up;
    private Button btn_log_in;
    private Button btn_sign_up_mail;
    private Button btn_log_in_username;
    private Button btn_new_password_confirm;
    private Button btn_back;

    private TextView text;
    private TextView txt_or;

    private Socket mSocket;

    private void init(){

        btn_sign_up= (Button) findViewById(R.id.btn_sign_up);
        btn_log_in= (Button) findViewById(R.id.btn_log_in);
        btn_sign_up_mail= (Button) findViewById(R.id.btn_sign_up_mail);
        btn_log_in_username= (Button) findViewById(R.id.btn_log_in_username);
        btn_new_password_confirm= (Button) findViewById(R.id.btn_new_password_confirm);
        btn_back= (Button) findViewById(R.id.btn_back);

        btn_sign_up.setOnClickListener((View.OnClickListener)this);
        btn_log_in.setOnClickListener((View.OnClickListener)this);
        btn_sign_up_mail.setOnClickListener((View.OnClickListener)this);
        btn_log_in_username.setOnClickListener((View.OnClickListener)this);
        btn_new_password_confirm.setOnClickListener((View.OnClickListener)this);
        btn_back.setOnClickListener((View.OnClickListener)this);

        text= (TextView) findViewById(R.id.textView);
        txt_or= (TextView) findViewById(R.id.txt_or);

        edittext_edumail= (EditText) findViewById(R.id.edittext_edumail);
        edittext_username= (EditText) findViewById(R.id.edittext_username);
        edittext_password= (EditText) findViewById(R.id.edittext_password);
        edittext_new_password= (EditText) findViewById(R.id.edittext_new_password);
        edittext_repeat_new_password= (EditText) findViewById(R.id.edittext_repeat_new_password);

    }

    //@Override
    //public void onBackPressed() {
    //    super.onBackPressed();
//
    //    btn_sign_up_mail.setVisibility(View.GONE);
    //    edittext_edumail.setVisibility(View.GONE);
    //    edittext_username.setVisibility(View.GONE);
    //    edittext_password.setVisibility(View.GONE);
    //    text.setVisibility(View.GONE);
    //    btn_log_in.setVisibility(View.VISIBLE);
//
    //}






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        init();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_sign_up:
                edittext_edumail.setVisibility(View.VISIBLE);
                edittext_username.setVisibility(View.VISIBLE);
                btn_sign_up_mail.setVisibility(View.VISIBLE);
                btn_sign_up.setVisibility(View.GONE);
                btn_log_in.setVisibility(View.GONE);
                txt_or.setVisibility(View.GONE);
                break;

            case R.id.btn_log_in:
                edittext_username.setVisibility(View.VISIBLE);
                edittext_password.setVisibility(View.VISIBLE);
                btn_log_in_username.setVisibility(View.VISIBLE);
                btn_sign_up.setVisibility(View.GONE);
                txt_or.setVisibility(View.GONE);
                btn_log_in.setVisibility(View.GONE);
                break;

            case R.id.btn_sign_up_mail:
                sendMailTo();
                edittext_edumail.setVisibility(View.GONE);
                edittext_username.setVisibility(View.GONE);
                btn_sign_up_mail.setVisibility(View.GONE);
                btn_back.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_back:
                btn_sign_up.setVisibility(View.VISIBLE);
                txt_or.setVisibility(View.VISIBLE);
                btn_log_in.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                break;

            case R.id.btn_log_in_username:
                try {

                    Kullanici k = Login();


                    if(k.Aktif){
                        Chat();
                    }
                    else if (k.IdKullanici!=0 && k.Aktif==false ){

                        edittext_new_password.setVisibility(View.VISIBLE);
                        edittext_repeat_new_password.setVisibility(View.VISIBLE);
                        btn_new_password_confirm.setVisibility(View.VISIBLE);
                        edittext_username.setVisibility(View.GONE);
                        edittext_password.setVisibility(View.GONE);
                        btn_log_in_username.setVisibility(View.GONE);

                        KullaniciId=k.IdKullanici;

                        Toast.makeText(this, "beni aktif yap oç", Toast.LENGTH_LONG).show();
                    }
                    else if (k.Aktif==false && k.IdKullanici==0){
                        Toast.makeText(this,"Kullanıcı Adı ve Şifre Hatalı Lütfen Bilgilerinizi Kontrol Ediniz",Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_new_password_confirm:

                if(edittext_new_password.getText().toString().equals(edittext_repeat_new_password.getText().toString())){

                    List<String> key = new ArrayList<String>();
                    key.add("Kid");key.add("Sifre");
                    List<String> val = new ArrayList<String>();
                    val.add(KullaniciId+"");val.add(edittext_new_password.getText().toString());
                    new SendPostTask(SigninActivity.this).execute("Kullanici/Sifrekaydet",key,val);

                    Chat();
                }
                else {
                    Toast.makeText(this, "Girdiğiniz şifreler aynı değil!", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }


    public final class SessionIdentifieerGenerator {

        public String random() {
            char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
            StringBuilder sb1 = new StringBuilder();
            Random random1 = new Random();
            for (int i = 0; i < 6; i++)
            {
                char c1 = chars1[random1.nextInt(chars1.length)];
                sb1.append(c1);
            }
            String random_string = sb1.toString();
            return random_string.toString();
        }


        private SecureRandom random = new SecureRandom();

        public String nextSessionId() {
            return new BigInteger(5, random).toString();
        };
    }

    private Kullanici Login() throws ExecutionException, InterruptedException, JSONException {

        str_password = edittext_password.getText().toString();
        str_username = edittext_username.getText().toString();

        List<String> keyp = new ArrayList<String>();
        keyp.add("Kad");keyp.add("Kod");
        List<String> valp = new ArrayList<String>();
        valp.add(str_username);valp.add(str_password);
        JSONObject response;
        response = (JSONObject) new SendPostTask(SigninActivity.this).execute("Kullanici/Login",keyp,valp).get();

        JSONObject obj = new JSONObject(response.get("Content").toString());

        Kullanici k = new Kullanici(
                obj.getInt("IdKullanici"),
                obj.getString("Email"),
                obj.getString("KullaniciAdi"),
                obj.getString("Sifre"),
                obj.getString("Kod"),
                obj.getBoolean("Aktif")
        );

        return k;
    }

    private void Chat(){
        Intent mIntent = new Intent();
        mIntent.setClass(SigninActivity.this, MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    private void sendMailTo(){

        SessionIdentifieerGenerator Code=new SessionIdentifieerGenerator();

        String fromEmail = "chatrefill.noreply@gmail.com";//((TextView) findViewById(R.id.editText1)).getText().toString();
        String fromPassword = "selamlar";//((TextView) findViewById(R.id.editText2)).getText().toString();
        String toEmails = edittext_edumail.getText().toString().trim();
        List<String> toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));

        str_username= edittext_username.getText().toString();


        try {
            JSONObject responsea;
            List<String> keyp = new ArrayList<String>();
            keyp.add("Email");
            List<String> valp = new ArrayList<String>();
            valp.add(toEmailList.get(0));
            responsea = (JSONObject) new SendPostTask(SigninActivity.this).execute("Kullanici/Emailsorgula",keyp,valp).get();
            Log.e("csd",responsea.get("Content")+"");
            if(responsea.getBoolean("Content"))
            {
                text.setText("zaten kayıtlısın adamı hasta etme!");
            }
            else
            {
                Log.e("csd","else");
                List<String> key = new ArrayList<String>();
                key.add("Email");key.add("Kod");key.add("Kad");
                List<String> val = new ArrayList<String>();
                String code=Code.random();
                val.add(toEmailList.get(0));val.add(code);val.add(str_username);
                new SendPostTask(SigninActivity.this).execute("Kullanici/Kodkaydet",key,val);

                String emailSubject = "ChatReFill Account";
                String emailBody ="Your Code: " + code;

                new SendMailTask(SigninActivity.this).execute(fromEmail,fromPassword, toEmailList, emailSubject, emailBody);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

