package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cs4531.finalsoftware.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView loginAttempts;
    private int counter = 3;

    private String user;
    private String pass;

    public RestRequests requests;

    private Button createNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.loginUser);
        password = (EditText)findViewById(R.id.loginPassword);
        login = (Button) findViewById(R.id.loginButton);
        loginAttempts = (TextView)findViewById(R.id.attemptsTextView);
        requests = RestRequests.getInstance(getApplicationContext());
        loginAttempts.setText("No of attempts remaining: " + String.valueOf(counter));

    }


    public void switchToFrontPage(View myView) {
        validateLogin(username.getText().toString(), password.getText().toString());
    }

    private void validateLogin(String userName, String userPass){
        user = userName;
        pass = userPass;

        String targetURL = getString(R.string.serverURL) + "/checkPassword";
        StringRequest postRequest = new StringRequest(Request.Method.POST, targetURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("LOGIN BUTTON SENT", response);
                        if(response.equals("The password is correct")) {
                            Intent intent = new Intent(MainActivity.this, FrontPage.class);
                            startActivity(intent);
                        }
                        else {
                            counter--;
                            loginAttempts.setText("Login Failed. No of attempts remaining: " + String.valueOf(counter));
                            //Log.d("LOGIN VALIDATION ERROR", "USERNAME: " + userName + " PASSWORD: " + userPass + " ********");
                            if (counter == 0) {
                                login.setEnabled(false);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> report = new HashMap<String, String>();
                report.put("user", user);
                report.put("password", pass);
                return report;
            }
        };
        requests.addToRequestQueue(postRequest);


        //if((userName.equals("Admin")) && (userPass.equals("1234"))){
            //Intent intent = new Intent(MainActivity.this, FrontPage.class);
            //intent.putExtra("account", account);
            //startActivity(intent);

            }

    public void switchToCreateUser(View view){
        Intent myIntent = new Intent(MainActivity.this, CreateNewUser.class);
        startActivity(myIntent);

    }

}
