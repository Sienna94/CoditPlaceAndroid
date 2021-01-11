package com.example.coditplace2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ForgotFrag extends BaseFrag implements View.OnClickListener{
    TextView tv_tit;
    TextView tv_info;
    TextView tv_info2;

    EditText et_email;

    Button btn;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static ForgotFrag newInstance() {
        return new ForgotFrag();
    }

    public ForgotFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_forgot, container, false);
        tv_tit=layout.findViewById(R.id.tv_tit);
        tv_info=layout.findViewById(R.id.tv_forgotinfo);
        tv_info2=layout.findViewById(R.id.tv_forgotemail);
        et_email=layout.findViewById(R.id.et_email);
        btn=layout.findViewById(R.id.btn_forgot);

        //비밀번호 이메일보내기 버튼 리스너
        btn.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        Log.d("chk", "onClick: 비밀번호 찾기 이메일 버튼 클릭됨");
    }
}
