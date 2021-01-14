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

import java.util.HashMap;
import java.util.Map;

public class BaseFrag extends Fragment {
    Map<String, String> params = new HashMap<String, String>();
    public BaseFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void request(String url, Response.Listener<String> successListener){
        RequestQueue stringRequest = Volley.newRequestQueue(getActivity());
        url = "http://192.168.7.31:8180/oop/" + url;
//        http://192.168.7.4 (학원)
//        http://172.20.10.4 (집)
//        192.168.7.22

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
