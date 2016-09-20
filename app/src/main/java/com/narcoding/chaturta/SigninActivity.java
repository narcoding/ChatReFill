package com.narcoding.chaturta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SigninActivity extends Activity {

    private EditText mUserMailView;
    private EditText mUserPasswordView;

    private TextView text;

    private Button LoginButton;
    private Button signInMailButton;

    private String mUserMail;
    private String mUserPassword;

    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();

        mUserMailView= (EditText) findViewById(R.id.edumail_input);
        mUserMailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mUserPasswordView= (EditText) findViewById(R.id.password_input);
        text= (TextView) findViewById(R.id.textView);


        signInMailButton = (Button) findViewById(R.id.sign_in_mail);
        signInMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMailTo();
            }
        });

        LoginButton= (Button) findViewById(R.id.Join_button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        mSocket.on("login", onSignIn);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.off("login", onSignIn);
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

        mUserPasswordView.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        signInMailButton.setVisibility(View.GONE);
        LoginButton.setVisibility(View.VISIBLE);

    }

    private void attemptLogin() {
        // Reset errors.
        mUserMailView.setError(null);

        // Store values at the time of the login attempt.
        String userMail = mUserMailView.getText().toString().trim();

        // Check for a valid username.
        if (TextUtils.isEmpty(userMail)) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mUserMailView.setError(getString(R.string.error_field_required));
            mUserMailView.requestFocus();
            return;
        }


        mUserMail = userMail;

        // perform the user login attempt.
        mSocket.emit("add user", userMail);
    }

    private Emitter.Listener onSignIn = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("username", mUserMail);
            intent.putExtra("numUsers", numUsers);
            setResult(RESULT_OK, intent);
            intent.setClass(SigninActivity.this,MainActivity.class);
            finish();
        }
    };

}
