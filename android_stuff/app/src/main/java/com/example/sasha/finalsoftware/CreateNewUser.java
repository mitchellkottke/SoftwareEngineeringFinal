package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import com.example.cs4531.finalsoftware.R;

import java.util.HashMap;
import java.util.Map;

public class CreateNewUser extends AppCompatActivity {

    private EditText username;
    private EditText password;

    private String newUserName;
    private String newPassWord;

    public RestRequests requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        username = (EditText) findViewById(R.id.newUsername);
        password = (EditText) findViewById(R.id.newPassword);
        requests = RestRequests.getInstance(getApplicationContext());
    }

    public void switchToMainPage(View view){
        createNewUser(username.getText().toString(), password.getText().toString());
    }

    private void createNewUser(String username, String password) {
        newUserName = username;
        newPassWord = password;

        String targetURL = getString(R.string.serverURL) + "/createUser";
        StringRequest postRequest = new StringRequest(Request.Method.POST, targetURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("CREATE USER BUTTON SENT", response);
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
                report.put("user", newUserName);
                report.put("password", newPassWord);
                return report;
            }
        };
        requests.addToRequestQueue(postRequest);

            Intent intent = new Intent(CreateNewUser.this, MainActivity.class);
            startActivity(intent);
    }
}
