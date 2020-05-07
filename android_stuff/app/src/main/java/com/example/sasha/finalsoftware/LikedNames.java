package com.example.sasha.finalsoftware;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cs4531.finalsoftware.R;
import com.google.android.gms.common.util.CollectionUtils;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class LikedNames extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView errorTV;

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
    private String answer;
    private int year = 2005;
    private double percent = 0.089;

    private ExampleAdapter mAdapter;

    //NEW SORT
    private ArrayList<String> arrayNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_names);

        errorTV = (TextView)findViewById(R.id.errorMessage);

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
                Log.d("Res", "In response");

                for(int i=0; i<response.length(); i++){
                    JSONObject obj;
                    try {
                        obj = response.getJSONObject(i);
                    }catch(Exception e){obj = null;
                    Log.d("Res", "No object found");}
                    if(obj != null) {
                        try {
                            Log.d("Res", "Object found");
                            exampleItems.add(new ExampleItem(obj.getString("name"),
                                    obj.getString("sex"),
                                    obj.getString("answer")));
                            arrayNames.add(obj.getString("name"));
                                    //obj.getInt("year"),
                                    //obj.getDouble("percent")));
                            //exampleItems.add(new ExampleItem("Should be a name", "Sexual things"));
                            Log.d("Res", "After example add");
                            throw new Exception();
                        } catch (Exception e) {
                            //errorTV.setText("Went to catch");
                            dataMissing(name,sex);
                        }
                    }
                }
                loopDone();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        requests.addToRequestQueue(post);

    }

    private void dataMissing(String nameStr, String sexStr){
        Log.d("DM", "In dataMissing");
        final String name = nameStr;
        final String sex = sexStr;

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
        }catch(Exception e){}

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url + "/getRecentData", json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DM", "In response");
                try{
                    double percent = response.getDouble("percent");
                    int year = response.getInt("year");
                    exampleItems.add(new ExampleItem(name, sex, year, percent));
                }catch(Exception e){Log.d("Error","Could not get name data");}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });

        requests.addToRequestQueue(req);
    }

    private void loopDone(){
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(LikedNames.this);
        mAdapter = new ExampleAdapter(exampleItems);

        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    public void sortPopUp(View v)
    {
        PopupMenu sortMenu = new PopupMenu(LikedNames.this, v);
        sortMenu.setOnMenuItemClickListener(LikedNames.this);
        sortMenu.inflate(R.menu.sort_menu);
        sortMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String toastText = item.getTitle().toString();

        int id = item.getItemId();
        switch (id) {
            case R.id.sortByName:
                sortByName();
                Toast.makeText(LikedNames.this, toastText + " was clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.likesOnly:
                filter("Liked");
                return true;
            case R.id.dislikesOnly:
                filter("Disliked");
                return true;
            case R.id.girlsOnly:
                filter("girl");
                return true;
            case R.id.boysOnly:
                filter("boy");
                return true;
            default:
                Toast.makeText(LikedNames.this, toastText + " was clicked", Toast.LENGTH_SHORT).show();
                return true;
        }

    }

    private void sortByName() {
        Collections.sort(exampleItems, new Comparator<ExampleItem>() {
            @Override
            public int compare(ExampleItem o1, ExampleItem o2) {
                return o1.getmName().compareTo(o2.getmName());
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    private void filter(String text) {
        ArrayList<ExampleItem> filteredList = new ArrayList<>();

        for(ExampleItem item : exampleItems) {
            if (item.getmAnswer().equals(text)){
                filteredList.add(item);
            }else if (item.getmSex().equals(text)){
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }
}
