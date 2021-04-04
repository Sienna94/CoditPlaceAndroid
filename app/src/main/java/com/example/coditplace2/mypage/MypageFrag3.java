package com.example.coditplace2.mypage;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;
import com.example.coditplace2.Storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MypageFrag3 extends BaseFrag {
    //계정 변경
    TextView tv_email;
    EditText et_nick;
    EditText et_self;
    EditText et_github;
    EditText et_pw1; //기존 비번
    EditText et_pw2; //새 비번
    EditText et_pw3; //새 비번 chk
    Button btn_save;
    //비번 체크
    TextView tv_chk; //비밀번호 체크
    TextView tv_chk2;//새로운 비밀번호 체크

    public MypageFrag3(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_mypage3, container, false);
        tv_email=layout.findViewById(R.id.tv_email2);
        et_nick=layout.findViewById(R.id.etv_nick);
        et_self=layout.findViewById(R.id.et_self);
        et_github=layout.findViewById(R.id.et_github);
        et_pw1=layout.findViewById(R.id.et_pw1);
        et_pw2=layout.findViewById(R.id.et_pw2);
        et_pw3=layout.findViewById(R.id.et_pw3);
        btn_save = layout.findViewById(R.id.btn_save);

        //기존 비밀번호 알림
        tv_chk = layout.findViewById(R.id.tv_pw1);
        tv_chk2 = layout.findViewById(R.id.tv_pwchk);
        //기존 비번
        et_pw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_pw1.getText().toString().equals(pw_origin)){
                    tv_chk.setText("비밀번호가 일치합니다");
                    tv_chk.setTextColor(Color.parseColor("#FF3F51B5"));
                    tv_chk.setVisibility(View.VISIBLE);
                }else{
                    tv_chk.setText("비밀번호가 일치하지 않습니다");
                    tv_chk.setTextColor(Color.parseColor("#F44336"));
                    tv_chk.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //새로운 비밀번호
        et_pw3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_pw3.getText().toString().equals(et_pw3.getText().toString())){
                    tv_chk2.setText("비밀번호가 일치합니다");
                    tv_chk2.setTextColor(Color.parseColor("#FF3F51B5"));
                    tv_chk2.setVisibility(View.VISIBLE);
                }else{
                    tv_chk2.setText("비밀번호가 일치하지 않습니다");
                    tv_chk2.setTextColor(Color.parseColor("#F44336"));
                    tv_chk2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return layout;
    }
    String pw_origin;
    Response.Listener<String> successListener = new Response.Listener<String>() {
        //가져온 jsonArray 리스트뷰로 나타내기
        @Override
        public void onResponse(String response) {
            Log.d("userinfo", "회원정보 수정 데이터 가져오기" + response);
            try {
                JSONArray proArr = new JSONArray(response);
                Log.d("proArr", "onResponse:" + response);
                for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
                    JSONObject proObj = proArr.getJSONObject(i);
                    //
                    String mid = proObj.getString("mid");
                    String mnick = proObj.getString("mnick");
                    String minfo = proObj.getString("minfo");
                    String mgithub = proObj.getString("mgithub");
                    String mpw = proObj.getString("mpw");
                    //response에 맞게 바꿔주기
                    tv_email.setText(mid);
                    et_nick.setText(mnick);
                    et_self.setText(minfo);
                    et_github.setText(mgithub);
//                    et_pw1.setText(mpw);
                    //비밀번호
                    pw_origin = mpw;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    public void requestForData(){ //초기화면은 회원 정보 가져오기

        Log.d("chk", "회원 정보 데이터 요청");
        params.clear();
        params.put("mid", Storage.USER);
        request("getUserinfo.do", successListener);
    }
    public void requestForUpdate(){
        Log.d("chk", "회원 정보 수정 요청");
        final String mnick = et_nick.getText().toString().trim();
        final String minfo = et_self.getText().toString().trim();
        final String mgithub = et_github.getText().toString().trim();
        final String pw_updated = et_pw2.getText().toString().trim();
        params.clear();
        params.put("mid", Storage.USER);
        params.put("mnick", mnick);
        params.put("minfo", minfo);
        params.put("mgithub", mgithub);
        params.put("mpw", pw_updated);

        request("getUserinfo.do", successListener);
    }

//    //원래 비번 일치하는가
//    public void pwchk(){
//        Log.d("chk", "비밀번호 일치여부 체크");
//        if(et_pw1.getText().toString().equals(pw_origin)){
//
//        }
//
//    }
//    //새로운 비번 일치여부
//    public void newPwchk(){
//        Log.d("chk", "새 비밀번호 일치여부 체크");
//    }
}
