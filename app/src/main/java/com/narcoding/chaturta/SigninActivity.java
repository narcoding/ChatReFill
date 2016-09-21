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

public class SigninActivity extends Activity {

    private EditText mUserMailView;

    private TextView text;


    private Button signInMailButton;
    private Button logInNickButton;

    private String mUserMail;

    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mUserMailView= (EditText) findViewById(R.id.edumail_input);

        text= (TextView) findViewById(R.id.textView);


        signInMailButton = (Button) findViewById(R.id.sign_in_mail);
        signInMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMailTo();
            }
        });


        logInNickButton= (Button) findViewById(R.id.log_in_nick);
        logInNickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent();
                mIntent.setClass(SigninActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();

            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }



    public final class SessionIdentifieerGenerator{
        private SecureRandom random=new SecureRandom();
        public String nextSessionId(){
          return new BigInteger(5,random).toString();
        };
    }


    private void sendMailTo(){

        SessionIdentifieerGenerator Code=new SessionIdentifieerGenerator();

        String fromEmail = "chatrefill.noreply@gmail.com";//((TextView) findViewById(R.id.editText1)).getText().toString();
        String fromPassword = "selamlar";//((TextView) findViewById(R.id.editText2)).getText().toString();
        String toEmails = mUserMailView.getText().toString().trim();
        List<String> toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + toEmailList);
        String emailSubject = "ChatReFill Account";
        String emailBody ="Your Code: " + Code.nextSessionId();
        new SendMailTask(SigninActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, emailBody);


        text.setVisibility(View.VISIBLE);
        signInMailButton.setVisibility(View.GONE);
        logInNickButton.setVisibility(View.GONE);

    }


}
