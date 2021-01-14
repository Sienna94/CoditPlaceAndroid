package com.example.coditplace2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchDetailActivity extends BaseActivity{
    SearchDetailFrag searchDetailFrag;
    SearchDetailFrag2 searchDetailFrag2;
    SearchDetailFrag3 searchDetailFrag3;
    SearchDetailFrag4 searchDetailFrag4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        String getpidx = getIntent().getStringExtra("pidx");
        Log.d("pidx", "상세activity pidx: ");
        searchDetailFrag = new SearchDetailFrag(getpidx);
        searchDetailFrag2 = new SearchDetailFrag2(getpidx);
        searchDetailFrag3 = new SearchDetailFrag3(getpidx);
        searchDetailFrag4 = new SearchDetailFrag4(getpidx);

        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.my_layout, searchDetailFrag).commit();
    }
    public void replaceFragment(int type){ //프래그먼트 전환해주는 메소드
        if(type==1){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.my_layout, searchDetailFrag);
            fragmentTransaction.commit();
        }else if(type==2){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.my_layout, searchDetailFrag2);
            fragmentTransaction.commit();
        }else if(type==3){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.my_layout, searchDetailFrag3);
            fragmentTransaction.commit();
        }else if(type==4){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.my_layout, searchDetailFrag4);
            fragmentTransaction.commit();
        }
    }


}