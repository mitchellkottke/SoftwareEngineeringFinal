package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.os.Bundle;
import com.example.cs4531.finalsoftware.R;

//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NamesListedActivity extends AppCompatActivity {

    private String nameText;

    private TextView typeOfNAme;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names_listed);

        Bundle extras = getIntent().getExtras();
        nameText = extras.getString("nameText");

        typeOfNAme = (TextView)findViewById(R.id.typeOfNames);
        typeOfNAme.setText(nameText);

    }

}
