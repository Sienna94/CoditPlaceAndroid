package com.example.coditplace2.mypage;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coditplace2.BaseActivity;
import com.example.coditplace2.R;

public class MypageActivity extends BaseActivity implements View.OnClickListener {
    MypageFrag1 mypageFrag1; //공간정보 추가
    MypageFrag2 mypageFrag2; //북마크
    MypageFrag3 mypageFrag3; //회원정보

    //이동 text
    TextView tv;
    TextView tv2;
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // 토큰 사용해서 회원 아이디 검색

        // 화면 전환 tv
        tv = findViewById(R.id.tv_tit);//공간정보추가
        tv2 = findViewById(R.id.tv_tit2);//북마크
        tv3 = findViewById(R.id.tv_tit3);//회원정보 변경
        tv.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        mypageFrag1 = new MypageFrag1();
        mypageFrag2 = new MypageFrag2();
        mypageFrag3 = new MypageFrag3();

       // 화면 전환 프래그먼트 선언 및 초기 화면 설정 * 북마크 페이지로 *
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_layout, mypageFrag2).commit();
    }

    public void replaceFragment(int type) { //프래그먼트 전환해주는 메소드
        if (type == 1) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.my_layout, mypageFrag1);
            fragmentTransaction.commit();
        } else if (type == 2) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.my_layout, mypageFrag2);
            fragmentTransaction.commit();
        } else if (type == 3) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.my_layout, mypageFrag3);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_tit){ //공간정보 추가
            replaceFragment(1);
        }else if(v.getId()==R.id.tv_tit2){ //북마크
            replaceFragment(2);
        }else if(v.getId()==R.id.tv_tit3){ //회원정보 변경
            replaceFragment(3);
        }
    }
}