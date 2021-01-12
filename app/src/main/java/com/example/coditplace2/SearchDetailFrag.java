package com.example.coditplace2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchDetailFrag extends BaseFrag{

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static SearchDetailFrag newInstance() {
        return new SearchDetailFrag();
    }

    public SearchDetailFrag() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_searchdetail, container, false);

        return layout;
    }
}
