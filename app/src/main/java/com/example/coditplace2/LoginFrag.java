package com.example.coditplace2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Response;

public class LoginFrag extends BaseFrag implements View.OnClickListener{
    TextView tv_titLogin;
    TextView tv_titJoin;
    TextView tv_forgot;
    EditText et_id;
    EditText et_pw;
    Button btn_login;
    CheckBox chk;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static LoginFrag newInstance() {
        return new LoginFrag();
    }

    public LoginFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_login, container, false);
        tv_titLogin = layout.findViewById(R.id.tv_login);
        tv_titJoin = layout.findViewById(R.id.tv_join);
        tv_forgot = layout.findViewById(R.id.tv_forgot);
        et_id = layout.findViewById(R.id.etv_id);
        et_pw = layout.findViewById(R.id.etv_pw);
        btn_login = layout.findViewById(R.id.btn_login);
        chk = layout.findViewById(R.id.chkbox);

        //가입하기 텍스트뷰에 클릭리스너 달기
        tv_titJoin.setOnClickListener(this);
        //버튼에 클릭리스너 달기
        btn_login.setOnClickListener(this);
        //비밀번호 찾기 텍스트뷰에 클릭리스너 달기
        tv_forgot.setOnClickListener(this);

//        //로그인 액티비티 위에 씌워짐
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);

        return layout;
    }
    private boolean isValid() {
        boolean isValid = true;
        if (et_id.getText().toString().trim().length() < 1) {
            isValid = false;
        } else if (et_id.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (et_pw.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (et_pw.getText().toString().trim().length() < 1) {
            isValid = false;
        }
        return isValid;
    }
    @Override
    public void onClick(View v) {
        //가입하기 tv 눌렀을 경우 join frag로 이동
        if(v.getId()==R.id.tv_join){
            Log.d("chk", "onClick: 가입하기 tv 클릭됨");
            ((LoginActivity)getActivity()).replaceFragment(JoinFrag.newInstance());

        }else if(v.getId()==R.id.btn_login){//로그인 버튼 누른 경우 DB에 요청
            Log.d("chk", "onClick: 로긴 버튼 클릭됨");
            requestForLogin();
        }else if(v.getId()==R.id.tv_forgot){ //비밀번호 찾기
            Log.d("chk", "onClick: 비밀번호 찾기 tv 클릭됨");

        }else {//프래그먼트가 올라가는 액티비티로 가게 this 말고 getActivity를 쓰자!!!!!!!
            Toast.makeText(getActivity(), "아이디 패스워드를 확인하세요 :(", Toast.LENGTH_SHORT).show();
        }
    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("login", response);
        }
    };
    private void requestForLogin(){
        //로그인 버튼 누르면 1. 통신 2. 값 받아오기 3. alert 띄우기 4. 리스트뷰로 이동
        //로그인 시도
        final String id = et_id.getText().toString().trim();
        final String pw = et_pw.getText().toString().trim();

        Log.d("chk", "로그인 통신: start");
        params.clear();
        params.put("id",id);
        params.put("pass", pw);
        Log.d("loginchk", "id:"+id+"/pass:"+pw);
        request("androidLogin.do", successListener);
    }
}
