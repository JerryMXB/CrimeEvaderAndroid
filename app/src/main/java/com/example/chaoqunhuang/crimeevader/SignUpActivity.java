package com.example.chaoqunhuang.crimeevader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import bean.User;
import request.GetUrlContentTask;

public class SignUpActivity extends AppCompatActivity {


    private Button login;
    private Button signUp;
    final Context context = this;
    final String url = "http://34.201.113.162:8080";
    private static final String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp = findViewById(R.id.signUpButton2);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.usernameInput)).getText().toString();
                String email = ((EditText) findViewById(R.id.emailInput)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phoneInput)).getText().toString();

                User loginUser = new User(username, password, email, phone);
                Log.i(TAG, new JSONObject(loginUser.toSignUpMap()).toString());
                new SignUp().execute(new JSONObject(loginUser.toSignUpMap()).toString());
            }
        });
        login = findViewById(R.id.signInButton2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private class SignUp extends GetUrlContentTask {
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, result);

            if (result.trim().equals("Success")) {
                Toast.makeText(context, "Successfully SignUp", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(SignUpActivity.this, MapsActivity.class);
                startActivity(myIntent);
            } else {
                Log.i(TAG, "Calling alert");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Sign Up Error")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }});

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        }
    }
}
