package com.example.coditplace2.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;

public class MypageFrag1 extends BaseFrag {
    //공간 정보 추가하기

    public MypageFrag1(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_mypage1, container, false);


        return layout;
    }
}
