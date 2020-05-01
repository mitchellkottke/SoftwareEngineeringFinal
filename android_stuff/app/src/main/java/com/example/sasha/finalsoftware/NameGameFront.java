package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs4531.finalsoftware.R;

public class NameGameFront extends AppCompatActivity {

    private Button girl;
    private Button boy;
    private Button both;

    private String nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_game_front);

        girl = (Button)findViewById(R.id.girlButton);
        boy = (Button)findViewById(R.id.boyButton);
        both = (Button)findViewById(R.id.bothButton);

        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText = "girl";
                Intent intent = new Intent(NameGameFront.this, NamesListedActivity.class);
                intent.putExtra("nameText", nameText);
                startActivity(intent);
            }
        });

        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText = "boy";
                Intent intent = new Intent(NameGameFront.this, NamesListedActivity.class);
                intent.putExtra("nameText", nameText);
                startActivity(intent);
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText = "both";
                Intent intent = new Intent(NameGameFront.this, NamesListedActivity.class);
                intent.putExtra("nameText", nameText);
                startActivity(intent);
            }
        });

    }

}
