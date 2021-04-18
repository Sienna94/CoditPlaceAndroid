package com.example.coditplace2.login;

import android.content.Intent;
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

import com.android.volley.Response;
import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;
import com.example.coditplace2.Storage;
import com.example.coditplace2.databinding.FragForgotBinding;
import com.example.coditplace2.databinding.FragLoginBinding;
import com.example.coditplace2.join.JoinFrag;
import com.example.coditplace2.join.LoginActivity;
import com.example.coditplace2.main.MainActivity;

public class LoginFrag extends BaseFrag implements View.OnClickListener{
    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static LoginFrag newInstance() {
        return new LoginFrag();
    }

    public LoginFrag() {
    }

    FragLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragLoginBinding.inflate(inflater, container, false);
        View layout = binding.getRoot();
        binding.tvJoin.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.tvForgot.setOnClickListener(this);

        return layout;
    }
    private boolean isValid() {
        boolean isValid = true;
        if (binding.etvId.getText().toString().trim().length() < 1) {
            isValid = false;
        } else if (binding.etvId.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (binding.etvPw.getText().toString().trim().contains(" ")) {
            isValid = false;
        } else if (binding.etvPw.getText().toString().trim().length() < 1) {
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
            ((LoginActivity)getActivity()).replaceFragment(ForgotFrag.newInstance());

        }else {//프래그먼트가 올라가는 액티비티로 가게 this 말고 getActivity를 쓰자!!!!!!!
            Toast.makeText(getActivity(), "아이디 패스워드를 확인하세요 :(", Toast.LENGTH_SHORT).show();
        }
    }
    String id;
    String pw;
    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("login", response);
            //로그인 성공시 storage 값 바꿔줌
            Storage.USER = id;
            Log.d("chk", "onResponse: 로그인 success 아이디값 저장:" + id);
            //이동
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    };
    private void requestForLogin(){
        //로그인 버튼 누르면 1. 통신 2. 값 받아오기 3. alert 띄우기 4. 리스트뷰로 이동
        //로그인 시도
        id = binding.etvId.getText().toString().trim();
        pw = binding.etvPw.getText().toString().trim();

        Log.d("chk", "로그인 통신: start");
        params.clear();
        params.put("mid",id);
        params.put("mpw", pw);
        Log.d("loginchk", "id:"+id+"/pass:"+pw);
        request("Login.do", successListener);
    }
}
