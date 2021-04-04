package com.example.coditplace2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coditplace2.retrofit.RetroClient;
import com.example.coditplace2.retrofit.responseBody.ResponseGet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class BaseFrag extends Fragment {
    //Volley
    public static Map<String, String> params = new HashMap<String, String>();

    public BaseFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    //Volley
    public void request(String url, Response.Listener<String> successListener){
        RequestQueue stringRequest = Volley.newRequestQueue(getActivity());
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
