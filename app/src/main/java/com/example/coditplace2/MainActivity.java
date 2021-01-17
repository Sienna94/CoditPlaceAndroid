package com.example.coditplace2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    Button btn2;
    TextView tv_tit;
    TextView tv_extra;
    TextView tv_tit2;

    //검색
    EditText et_pname;
    EditText et_plocation;
    EditText et_paddress;

    Button btn_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        tv_tit=findViewById(R.id.tv_tit);
        tv_extra=findViewById(R.id.tv_extra);
        tv_tit2=findViewById(R.id.tv_tit2);
        btn_search=findViewById(R.id.btn_search);
        //
        et_pname =findViewById(R.id.et_pname);
        et_plocation=findViewById(R.id.et_plocation);
        et_paddress=findViewById(R.id.et_paddress);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn){ //로긴 버튼
            Intent intent = new Intent(this, com.example.coditplace2.LoginActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn2){ //마이페이지 버튼
            Intent intent = new Intent(this, com.example.coditplace2.MypageActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_search) { //검색 버튼
            searchset();
            Intent intent = new Intent(this, com.example.coditplace2.SearchActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("search", search_final);
            startActivity(intent);
        }
    }
    //검색어 설정
    String type = "";
    String search_final ="";
    public void searchset(){ //입력값 정리
       //어디서 온 값인지 구분
         //실제 입력되는 값

        String search_name = et_pname.getText().toString().trim();
        String search_loca = et_plocation.getText().toString().trim();
        String search_addr = et_paddress.getText().toString().trim();

        if(search_name.length() >1){ // 장소 이름 검색
            type = "pname";
            search_final = search_name;
        }else if (search_loca.length() >1){ // 장소 위치 검색
            type = "plocation";
            search_final = search_loca;
        }else if (search_addr.length() >1){ // 장소 주소 검색
            type = "paddress";
            search_final = search_addr;
        }else {
            type = "none";
            search_final = ""; //전체 검색
        }
    }

}