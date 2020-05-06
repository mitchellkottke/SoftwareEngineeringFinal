package com.example.sasha.finalsoftware;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.cs4531.finalsoftware.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NamesListedActivity extends AppCompatActivity {

    private String url;

    private String currentName;
    private String genderText;
    private String username;

    private TextView nameView;
    private TextView genderView;
    private GraphView graph;
    private boolean specifiedGender;

    public RestRequests requests;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names_listed);

        url = getString(R.string.serverURL);

        requests = RestRequests.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        genderText = extras.getString("nameText");
        username = extras.getString("username");

        nameView = (TextView)findViewById(R.id.nameView);
        graph = (GraphView)findViewById(R.id.namePopGraph);

        genderView = (TextView)findViewById(R.id.genderView);

        if(genderText.equals("both"))
            specifiedGender = false;
        else {
            specifiedGender = true;
            genderView.setText(genderText.substring(0,1).toUpperCase() + genderText.substring(1));//Capitalize first char
        }

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX){
                if(isValueX){
                    return Integer.toString((int)value);//Return number in year format
                }else{
                    int temp = (int)(value*100000);
                    return (double)temp/1000 + "%";
                }
            }
        });

        getName();
    }

    /**
     * Get and display a name and gender of the name
     * @author kottk055
     */
    private void getName(){
        String target = url + "/getName";

        JSONObject json = new JSONObject();
        try {
            json.put("user", username);
            if (specifiedGender)
                json.put("sex", genderText);
        }catch(Exception e){}
        JsonObjectRequest post = new JsonObjectRequest(Request.Method.POST, target, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    currentName = response.getString("name");
                    nameView.setText(response.getString("name"));
                    if (!specifiedGender) {
                        genderText = response.getString("sex");
                        String gender = response.getString("sex").substring(0,1).toUpperCase() +
                                response.getString("sex").substring(1);
                        genderView.setText(gender);
                    }
                }catch(Exception e){
                    getName();
                }
                updateGraph();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Occurred", error.toString());
            }
        });
        requests.addToRequestQueue(post);
    }

    /**
     * Helper to getName()
     * Update the graph to show data for the next name
     * @author kottk055
     */
    private void updateGraph(){
        String target = url + "/getGraphData";
        String name = nameView.getText().toString();
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
        }catch(Exception e){}

        CustomJsonArrayRequest post = new CustomJsonArrayRequest(Request.Method.POST, target, json, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                graph.removeAllSeries();

                LineGraphSeries<DataPoint> series;
                DataPoint[] data = new DataPoint[response.length()];

                for(int i=0; i<response.length(); i++){
                    JSONObject obj;
                    try {
                        obj = response.getJSONObject(i);
                    }catch(Exception e){obj = null;}
                    if(obj != null) {
                        try {
                            data[i] = new DataPoint(obj.getInt("year"), obj.getDouble("percent"));
                        } catch (Exception e) {}
                    }
                }
                series = new LineGraphSeries<>(data);
                graph.addSeries(series);
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(series.getHighestValueY() + (0.00005-(series.getHighestValueY()%0.00005)));//Round to nearest .005%
                graph.getViewport().setMinX(1880);
                graph.getViewport().setMaxX(2020);

                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setYAxisBoundsManual(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                graph.removeAllSeries();
                Toast.makeText(NamesListedActivity.this, "Cannot get popularity data at this time", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        requests.addToRequestQueue(post);
    }

    public void likeClicked(View view){
        final String answer = "Liked";
        final String gender = "boy";//genderText;
        final String name = currentName;
        final String user = username;
        String target = url + "/nameAnswered";

        StringRequest post = new StringRequest(Request.Method.POST, target, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Answer added")){
                    getName();
                }else{
                    Toast.makeText(NamesListedActivity.this, "Cannot submit answer at this time", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                Toast.makeText(NamesListedActivity.this, "Cannot submit answer at this time", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> json = new HashMap<String, String>();
                json.put("name", name);
                json.put("sex", gender);
                json.put("answer", answer);
                json.put("user", user);
                return json;
            }

        };
        requests.addToRequestQueue(post);
    }

    public void dislikeClicked(View view){
        final String answer = "Disliked";
        final String gender = genderText;
        final String name = currentName;
        final String user = username;
        String target = url + "/nameAnswered";

        StringRequest post = new StringRequest(Request.Method.POST, target, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Answer added")){
                    getName();
                }else{
                    Toast.makeText(NamesListedActivity.this, "Cannot submit answer at this time", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                Toast.makeText(NamesListedActivity.this, "Cannot submit answer at this time", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> json = new HashMap<String, String>();
                json.put("name", name);
                json.put("sex", gender);
                json.put("answer", answer);
                json.put("user", user);
                return json;
            }

        };
        requests.addToRequestQueue(post);
    }

}
