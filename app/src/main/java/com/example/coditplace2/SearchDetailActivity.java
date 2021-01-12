package com.example.coditplace2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchDetailActivity extends BaseActivity implements View.OnClickListener {
    SearchDetailFrag searchDetailFrag = new SearchDetailFrag();

    ImageView iv_bg;
    ImageView iv_icon;
    TextView tv_pname;
    Button btn_like;
    TextView tv_info;
    TextView tv_eval;
    TextView tv_review;
    TextView tv_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        //액티비티 레이아웃 요소
        iv_bg=findViewById(R.id.iv_bg);
        iv_icon=findViewById(R.id.iv_icon);
        tv_pname=findViewById(R.id.tv_pname);
        btn_like=findViewById(R.id.btn_like);
        tv_info=findViewById(R.id.tv_info);//매장정보
        tv_eval=findViewById(R.id.tv_evaluation);//코더의 평가
        tv_review=findViewById(R.id.tv_review);//리뷰(댓글)
        tv_contact=findViewById(R.id.tv_contact);//연락처

        btn_like.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_eval.setOnClickListener(this);
        tv_review.setOnClickListener(this);
        tv_contact.setOnClickListener(this);


        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_layout, searchDetailFrag.newInstance()).commit();
    }
    public void replaceFragment(Fragment fragment){ //프래그먼트 전환해주는 메소드
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.my_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_like){//좋아요 버튼

        }else if(v.getId()==R.id.tv_info){ //매장정보

        }else if(v.getId()==R.id.tv_evaluation){ //코더 평가

        }else if(v.getId()==R.id.tv_review) { //리뷰(댓글)

        }else if(v.getId()==R.id.tv_contact){ //연락처

        }
    }
}