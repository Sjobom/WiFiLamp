package com.example.anders.wifilamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ledOn (View v){
        Log.d("BEFORE REQUEST", "");
        lampRequest("on");
    }

    public void ledOff (View v){
        lampRequest("off");
    }

    public void lampRequest (String action){
        String url;
        if (action.equals("on")){ url = "http://192.168.1.100/LED=ON/"; }
        else if(action.equals("off")){ url = "http://192.168.1.100/LED=OFF/"; }
        else{ return; }
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("REPLY", response);
                TextView stateText = (TextView) findViewById(R.id.stateText);
                if (response.indexOf("On<br><br>") != -1){
                    stateText.setText("LED is ON!");
                }
                else if (response.indexOf("Off<br><br>") != -1){
                    stateText.setText("LED is OFF!");
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });
        queue.add(stringRequest);
    }
}
