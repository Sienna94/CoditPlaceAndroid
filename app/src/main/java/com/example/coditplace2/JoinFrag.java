package com.example.coditplace2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;

public class JoinFrag extends BaseFrag implements View.OnClickListener{
    TextView tv_titLogin;
    TextView tv_titJoin;
    TextView tv_nickinfo;
    TextView tv_introinfo;
    TextView tv_gittit;
    TextView tv_gitinfo;
    TextView tv_agr;

    EditText et_email;
    EditText et_pw;
    EditText et_nick;
    EditText et_intro;
    EditText et_git;

    Button btn_git;
    Button btn_join;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static JoinFrag newInstance() {
        return new JoinFrag();
    }

    public JoinFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_join, container, false);
        tv_titLogin = layout.findViewById(R.id.tv_login);
        tv_titJoin = layout.findViewById(R.id.tv_join);
        tv_nickinfo = layout.findViewById(R.id.tv_nickinfo);
        tv_introinfo = layout.findViewById(R.id.tv_introinfo);
        tv_gittit =layout.findViewById(R.id.tv_git);
        tv_gitinfo = layout.findViewById(R.id.tv_gitinfo);
        tv_agr = layout.findViewById(R.id.tv_agr);
        et_email = layout.findViewById(R.id.etv_email);
        et_pw = layout.findViewById(R.id.etv_pw);
        et_nick =layout.findViewById(R.id.etv_nick);
        et_intro =layout.findViewById(R.id.etv_nick);
        et_git = layout.findViewById(R.id.etv_github);
        btn_git = layout.findViewById(R.id.btn_git);
        btn_join = layout.findViewById(R.id.btn_join);
        //텍스트뷰, 버튼 클릭
        tv_titLogin.setOnClickListener(this);
        btn_join.setOnClickListener(this);

        //로그인 액티비티 위에 씌워짐
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        return layout;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_join){
            Log.d("chk", "onClick: 회원가입 버튼 클릭됨");
            requestForJoin();

        }else if(v.getId()==R.id.tv_login){
            ((LoginActivity)getActivity()).replaceFragment(LoginFrag.newInstance());
        }
    }
    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("kkk", response);

        }
    };
    private void requestForJoin(){
        final String email = et_email.getText().toString().trim();
        final String pw = et_pw.getText().toString().trim();
        final String nick = et_nick.getText().toString().trim();
        final String info = et_intro.getText().toString().trim();
        final String github = et_git.getText().toString().trim();

        Log.d("chk", "회원가입 통신: start");
        params.clear();
        params.put("email", email);
        params.put("pw", pw);
        params.put("nick", nick);
        params.put("intro", info);
        params.put("github", github);
        Log.d("joinchk", "email:"+email+"/pw:"+pw
                +"/nick:"+nick+"/intro:"+info+"/github:"+github);
        request("Join.do", successListener);
    }
}
