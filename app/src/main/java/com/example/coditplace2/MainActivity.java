package com.example.coditplace2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    TextView tv_tit;
    TextView tv_extra;
    TextView tv_tit2;

    Button btn_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);

        tv_tit=findViewById(R.id.tv_tit);
        tv_extra=findViewById(R.id.tv_extra);
        tv_tit2=findViewById(R.id.tv_tit2);
        btn_search=findViewById(R.id.btn_search);

        btn.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn){ //로긴 버튼
            Intent intent = new Intent(this, com.example.coditplace2.LoginActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_search){ //검색 버튼
            Intent intent = new Intent(this, com.example.coditplace2.SearchActivity.class);
            startActivity(intent);
        }
    }

}