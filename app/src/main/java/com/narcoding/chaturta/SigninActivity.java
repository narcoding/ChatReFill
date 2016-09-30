package com.narcoding.chaturta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import io.socket.client.Socket;

public class SigninActivity extends Activity implements View.OnClickListener {

    private EditText edittext_edumail;
    private EditText edittext_username;
    private EditText edittext_password;

    private String str_edumail;
    private String str_username;

    private Button btn_sign_up;
    private Button btn_log_in;
    private Button btn_sign_up_mail;
    private Button btn_log_in_username;
    private Button btn_back;

    private TextView text;
    private TextView txt_or;

    private Socket mSocket;

    private void init(){

        btn_sign_up= (Button) findViewById(R.id.btn_sign_up);
        btn_log_in= (Button) findViewById(R.id.btn_log_in);
        btn_sign_up_mail= (Button) findViewById(R.id.btn_sign_up_mail);
        btn_log_in_username= (Button) findViewById(R.id.btn_log_in_username);
        btn_back= (Button) findViewById(R.id.btn_back);

        btn_sign_up.setOnClickListener((View.OnClickListener)this);
        btn_log_in.setOnClickListener((View.OnClickListener)this);
        btn_sign_up_mail.setOnClickListener((View.OnClickListener)this);
        btn_log_in_username.setOnClickListener((View.OnClickListener)this);
        btn_back.setOnClickListener((View.OnClickListener)this);

        text= (TextView) findViewById(R.id.textView);
        txt_or= (TextView) findViewById(R.id.txt_or);

        edittext_edumail= (EditText) findViewById(R.id.edittext_edumail);
        edittext_username= (EditText) findViewById(R.id.edittext_username);
        edittext_password= (EditText) findViewById(R.id.edittext_password);

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
                Intent mIntent = new Intent();
                mIntent.setClass(SigninActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
                break;
        }
    }


public final class SessionIdentifieerGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(5, random).toString();
    };
}

    private void sendMailTo(){

        SessionIdentifieerGenerator Code=new SessionIdentifieerGenerator();

        String fromEmail = "chatrefill.noreply@gmail.com";//((TextView) findViewById(R.id.editText1)).getText().toString();
        String fromPassword = "selamlar";//((TextView) findViewById(R.id.editText2)).getText().toString();
        String toEmails = edittext_edumail.getText().toString().trim();
        List<String> toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + toEmailList);
        String emailSubject = "ChatReFill Account";
        String emailBody ="Your Code: " + Code.nextSessionId();
        new SendMailTask(SigninActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, emailBody);


    }




}

