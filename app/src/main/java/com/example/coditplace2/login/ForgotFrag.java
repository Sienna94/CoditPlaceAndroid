package com.example.coditplace2.login;

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

import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;
import com.example.coditplace2.databinding.FragForgotBinding;

public class ForgotFrag extends BaseFrag implements View.OnClickListener{
    FragForgotBinding binding;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static ForgotFrag newInstance() {
        return new ForgotFrag();
    }

    public ForgotFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragForgotBinding.inflate(inflater, container, false);
        View layout = binding.getRoot();
        binding.btnForgot.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        Log.d("chk", "onClick: 비밀번호 찾기 이메일 버튼 클릭됨");
    }
}
