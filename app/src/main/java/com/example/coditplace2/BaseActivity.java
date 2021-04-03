package com.example.coditplace2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class BaseActivity extends AppCompatActivity {
    Map<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void request(String url, Response.Listener<String> successListener){
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        url = Storage.HOME_URL+":8180/oop/" + url;


        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String str ="str";
                params.put("test",str);
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
    }
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("ErrorResponse", "통신 실패");
        }
    };
}