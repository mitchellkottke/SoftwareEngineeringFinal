package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.example.cs4531.finalsoftware.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontPage extends AppCompatActivity {

    private Button play;
    private Button seeLikes;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        play = (Button)findViewById(R.id.play);
        seeLikes = (Button)findViewById(R.id.seeLikes);

        username = getIntent().getStringExtra("username");

    }

    public void goToNameGameFront(View v){
        Intent intent = new Intent(FrontPage.this, NameGameFront.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void goToSeeLikesAndDislikes(View v){
        Intent intent = new Intent(FrontPage.this, LikedNames.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}
