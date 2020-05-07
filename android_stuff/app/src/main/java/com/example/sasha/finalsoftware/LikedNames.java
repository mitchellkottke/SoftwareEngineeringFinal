package com.example.sasha.finalsoftware;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.DocumentsContract;
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
import java.util.List;

//import org.bson.Document;
//import org.bson.conversions.Bson;
//import com.mongodb.ClientSessionOptions;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.ChangeStreamIterable;
//import com.mongodb.client.ClientSession;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.ListDatabasesIterable;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.ConnectionString;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.MongoIterable;
//import com.mongodb.stitch.android.core.Stitch;
//import com.mongodb.stitch.android.core.StitchAppClient;
//import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
//import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
//import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

public class LikedNames extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView errorTV;

    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExampleAdapter mAdapter;

    private String username;
    private String url;

    public RestRequests requests;

    private ArrayList<ExampleItem> exampleItems = new ArrayList<>();
    private String name;
    private String sex;
    private String answer;
    private int year = 2005;
    private double percent = 0.089;

    String currentAnswer;
    private ArrayList<ExampleItem> filteredList = new ArrayList<>();

    private Button likeDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_names);

        errorTV = (TextView)findViewById(R.id.errorMessage);
        likeDislike = findViewById(R.id.unLikeButton);

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
                    }catch(Exception e){
                        obj = null;
                        Log.d("Res", "No object found");
                    }

                    if(obj != null) {
                        try {
                            Log.d("Res", "Object found");
                            exampleItems.add(new ExampleItem(obj.getString("name"),
                                    obj.getString("sex"),
                                    obj.getString("answer")));
                                    //obj.getInt("year"),
                                    //obj.getDouble("percent")));
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

    }//end of onCreate

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

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (filteredList.size() !=0){
                    ExampleItem currentItem = filteredList.get(position);
                    currentAnswer = currentItem.getmAnswer();
                    changeLikeDisliked(currentAnswer);
                    Toast.makeText(LikedNames.this, currentItem.getmName() + " was clicked", Toast.LENGTH_SHORT).show();
                }else {
                    ExampleItem currentItem = exampleItems.get(position);
                    currentAnswer = currentItem.getmAnswer();
                    changeLikeDisliked(currentAnswer);
                    Toast.makeText(LikedNames.this, currentItem.getmName() + " was clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        likeDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //findAndUpdateMongo(currentAnswer);
            }
        });
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
                //Toast.makeText(LikedNames.this, toastText + " was clicked", Toast.LENGTH_SHORT).show();
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
        filteredList = new ArrayList<>();

        for(ExampleItem item : exampleItems) {
            if (item.getmAnswer().equals(text)){
                filteredList.add(item);
            }else if (item.getmSex().equals(text)){
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void changeLikeDisliked(String answer){
        Button butt = findViewById(R.id.unLikeButton);

        if(answer.equals("Liked")){
            butt.setText("Unlike");



        }else if(answer.equals("Disliked")){
            butt.setText("Like");
        }

    }

}

//ALL NEW MONGO STUFF
//        MongoClient client = MongoClients.create("mongodb://ukko.d.umn.edu:4321");
//        MongoDatabase db = client.getDatabase("AppNull");
//        Log.d("MGCLIENT", db.toString());
//        MongoCollection<Document> answeredCollection = db.getCollection("Answered");
//
//        System.out.println("beforeUpdate");
//        findAndPrint(answeredCollection);

//        Document doc = new Document("name", new Document("$gt", username));
//        doc.get("liked");
//
//        Bson query = doc;
//        Bson update;
//
//        if(answer.equals("Liked")){
//            update = new Document("$set",
//                    new Document("answer", "Disliked"));
//        }else{
//            update = new Document("$set",
//                    new Document("answer", "Liked"));
//        }
//
//        answeredCollection.findOneAndUpdate(query, update);

//        MongoClient mongoClient = MongoClients.create(
//                MongoClientSettings.builder()
//                        .applyToClusterSettings(builder ->
//                                builder.hosts(Arrays.asList(new ServerAddress("mongodb://ukko.d.umn.edu:", 4321))))
//                        .build());
//        MongoClient mongoClient = MongoClients.create("mongodb://ukko.d.umn.edu:4321");
//        MongoDatabase dbs = mongoClient.getDatabase("AppNull");
//        MongoCollection<Document> collection = dbs.getCollection("Answered");
//        MongoCursor<Document> cursor = collection.find().iterator();
//        try {
//            while (cursor.hasNext()) {
//                System.out.println(cursor.next().toJson());
//            }
//        } finally {
//            cursor.close();
//        }

//    private static void findAndPrint(MongoCollection<Document> coll) {
//        FindIterable<Document> cursor = coll.find();
//
//        for (Document d : cursor)
//            System.out.println(d);
//        //Log.d("MGDOC", d);
//    }