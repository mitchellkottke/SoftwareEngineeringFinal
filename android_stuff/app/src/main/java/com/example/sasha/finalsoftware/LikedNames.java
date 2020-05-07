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
    private String year;
    private String percent;

    private ExampleAdapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_liked_names);
//
//        url = getString(R.string.serverURL);
        requests = RestRequests.getInstance(getApplicationContext());
//
//        unLike = (Button)findViewById(R.id.unLikeButton);
//
//        Bundle extras = getIntent().getExtras();
//        username = extras.getString("username");
//
//        mRecycleView = findViewById(R.id.recyclerView);
//        mRecycleView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(LikedNames.this);
//        //mAdapter = new ExampleAdapter(exampleList);
//
//        mRecycleView.setLayoutManager(mLayoutManager);
//        //mRecycleView.setAdapter(mAdapter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_names);

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
                for(int i=0; i<response.length(); i++) {
                    JSONObject jo;
                    try {
                        jo = response.getJSONObject(i);
                        name = jo.getString("name");
                        sex = jo.getString("sex");
                        year = jo.getString("year");
                        percent = jo.getString("percent");

                        exampleItems.add(new ExampleItem(name, sex, year, percent));
                    } catch (JSONException e) {
                        dataMissing(name, sex);
//                    Log.d("ERROR", "Error getting list of likedNames");
//                    e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        requests.addToRequestQueue(post);

        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(LikedNames.this);
        mAdapter = new ExampleAdapter(exampleItems);

        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);

    }

    private void dataMissing(String nameStr, String sexStr){
        final String name = nameStr;
        final String sex = sexStr;

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
        }catch(Exception e){}

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url + "/getRecentData", json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    double percent = response.getDouble("percent");
                    int year = response.getInt("year");
                    exampleItems.add(new ExampleItem(name, sex, Integer.toString(year), Double.toString(percent)));
                }catch(Exception e){Log.d("Error","Could not get name data");}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
    }


}
