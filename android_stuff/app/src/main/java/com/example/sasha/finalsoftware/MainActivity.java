package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs4531.finalsoftware.R;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView loginAttempts;
    private int counter = 3;

    private Button createNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.loginUser);
        password = (EditText)findViewById(R.id.loginPassword);
        login = (Button) findViewById(R.id.loginButton);
        loginAttempts = (TextView)findViewById(R.id.attemptsTextView);

        loginAttempts.setText("No of attempts remaining: " + String.valueOf(counter));

    }


    public void switchToFrontPage(View myView) {
        validateLogin(username.getText().toString(), password.getText().toString());
    }

    private void validateLogin(String userName, String userPass){

        if((userName.equals("Admin")) && (userPass.equals("1234"))){
            Intent intent = new Intent(MainActivity.this, FrontPage.class);
            //intent.putExtra("account", account);
            startActivity(intent);
        }else{
            counter--;
            loginAttempts.setText("Login Failed. No of attempts remaining: " + String.valueOf(counter));
            //Log.d("LOGIN VALIDATION ERROR", "USERNAME: " + userName + " PASSWORD: " + userPass + " ********");
            if(counter == 0){
                login.setEnabled(false);
            }
        }
    }
    public void switchToCreateUser(View view){
        Intent myIntent = new Intent(MainActivity.this, CreateNewUser.class);
        startActivity(myIntent);

    }

}
