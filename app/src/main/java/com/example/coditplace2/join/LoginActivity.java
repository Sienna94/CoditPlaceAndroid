package com.example.coditplace2.join;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.coditplace2.login.LoginFrag;
import com.example.coditplace2.R;

public class LoginActivity extends AppCompatActivity{
    JoinFrag joinFrag = new JoinFrag();
    LoginFrag loginFrag = new LoginFrag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_layout, loginFrag.newInstance()).commit();
    }
    public void replaceFragment(Fragment fragment){ //프래그먼트 전환해주는 메소드
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.my_layout, fragment);
        fragmentTransaction.commit();
    }
}