package com.example.anders.wifilamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
        RequestQueue queue = Volley.newRequestQueue(this);
        lampRequest("check");
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
        else if(action.equals("check")){ url = "http://192.168.1.100/"; }
        else{ return; }
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("REPLY", response);
                ImageView lamp_image = (ImageView) findViewById(R.id.LAMP_VIEW);
                if (response.indexOf("On<br><br>") != -1){
                    lamp_image.setImageResource(R.drawable.led_on);
                }
                else if (response.indexOf("Off<br><br>") != -1){
                    lamp_image.setImageResource(R.drawable.led_off);
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
