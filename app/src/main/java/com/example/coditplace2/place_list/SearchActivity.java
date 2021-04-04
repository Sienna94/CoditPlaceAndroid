package com.example.coditplace2.place_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.coditplace2.R;

public class SearchActivity extends AppCompatActivity {
    SearchFrag searchFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String gettype = getIntent().getStringExtra("type");
        String getsearch = getIntent().getStringExtra("search");
        searchFrag = new SearchFrag(gettype, getsearch);
        //searchFrag.get(gettype, getsearch); //프래그먼트에 설정해준 str 받아오는 메소드


        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_layout, searchFrag).commit();

        //searchFrag.request();
        Log.d("abc", "gettype:" + gettype + "/getsearch:" + getsearch);
    }

    public void replaceFragment(Fragment fragment) { //프래그먼트 전환해주는 메소드
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.my_layout, fragment);
        fragmentTransaction.commit();
    }
}