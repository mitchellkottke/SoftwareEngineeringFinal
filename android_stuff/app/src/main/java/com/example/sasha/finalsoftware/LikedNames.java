package com.example.sasha.finalsoftware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cs4531.finalsoftware.R;
import com.google.gson.JsonObject;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LikedNames extends AppCompatActivity {

    private Button unLike;
    private RecyclerView mRecycleView;
    //private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String username;
    private String url;

    public RestRequests requests;

    private ArrayList<ExampleItem> exampleItems = new ArrayList<>();
    private String name;
    private String sex;
    private int year = 2005;
    private double percent = 0.089;

    private ExampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_names);

        requests = RestRequests.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        url = getString(R.string.serverURL);

        String targetURL = url + "/getList";
        JSONObject json = new JSONObject();
        try {
            json.put("user", username);
        }catch(Exception e){}

        CustomJsonArrayRequest post = new CustomJsonArrayRequest(Request.Method.POST, targetURL, json, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0; i<response.length(); i++){
                        JSONObject jo = response.getJSONObject(i);
                            name = jo.getString("name");
                            sex = jo.getString("sex");
                            //year = 2005;
                            //percent = 0.5;

                            //exampleItems.add(new ExampleItem(name, sex, year, percent));
                            exampleItems.add(new ExampleItem(name, sex));
                    }//end of for

                }catch (JSONException e){
                    Log.d("ERROR", "Error getting list of likedNames");
                    e.printStackTrace();
                }//end of try/catch

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        requests.addToRequestQueue(post);

//        exampleItems.add(new ExampleItem("Joshua", "boy", year, percent));
//        exampleItems.add(new ExampleItem("Jakob", "boy", year, percent));
//        exampleItems.add(new ExampleItem("Betty", "boy", year, percent));

//        exampleItems.add(new ExampleItem("Jakob", "boy"));
//        exampleItems.add(new ExampleItem("Joshua", "boy"));
//        exampleItems.add(new ExampleItem("Betty", "boy"));


        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(LikedNames.this);
        mAdapter = new ExampleAdapter(exampleItems);

        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);

    }


}
