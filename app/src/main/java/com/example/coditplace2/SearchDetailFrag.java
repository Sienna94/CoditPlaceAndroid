package com.example.coditplace2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchDetailFrag extends BaseFrag implements View.OnClickListener{
    ArrayList<ImgArr> arr = new ArrayList<>();

    ImageView iv_bg;
    ImageView iv_icon;
    TextView tv_pname;
    Button btn_like;
    TextView tv_info;
    TextView tv_eval;
    TextView tv_review;
    TextView tv_contact;

    GridView gridView;
    MyAdapter adapter;

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

        iv_bg=layout.findViewById(R.id.iv_bg);
        iv_icon=layout.findViewById(R.id.iv_icon);
        tv_pname=layout.findViewById(R.id.tv_pname);
        btn_like=layout.findViewById(R.id.btn_like);
        tv_info=layout.findViewById(R.id.tv_info);//매장정보
        tv_eval=layout.findViewById(R.id.tv_evaluation);//코더의 평가
        tv_review=layout.findViewById(R.id.tv_review);//리뷰(댓글)
        tv_contact=layout.findViewById(R.id.tv_contact);//연락처

        btn_like.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_eval.setOnClickListener(this);
        tv_review.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
        Log.d("chk", "dfsdf");
        gridView=layout.findViewById(R.id.grid); //그리드뷰

        test();
        return layout;
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
    public void test(){ //이미지 넣어보기
        arr.add(new ImgArr("2.jpeg"));
        arr.add(new ImgArr("2.jpeg"));
        arr.add(new ImgArr("3.jpeg"));

//        Log.d("chk", arr.get(0).pImage);
//        Log.d("chk", arr.get(1).pImage);
//        Log.d("chk", arr.get(2).pImage);

        adapter = new MyAdapter(getActivity());
        gridView.setAdapter(adapter);
    }
    class ImgArr{ //그리드뷰 arr
        String pImage;

        public ImgArr(String pImage) {
            this.pImage = pImage;
        }
    }

    class ItemHolder {
        ImageView ivHolder;
    }
    class  MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;
        public MyAdapter(Activity context) {
            super(context, R.layout.item_grid, arr);

            Log.d("chk", "MyAdapter: 시작");
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder viewHolder;
            if(convertView == null){
                convertView = lnf.inflate(R.layout.item_grid, parent, false);
                viewHolder = new ItemHolder();
                viewHolder.ivHolder = convertView.findViewById(R.id.iv_img);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }
//            카페 사진
            Glide.with(getActivity())
                    .load("http://192.168.7.31:8180/oop/img/place/"+arr.get(position).pImage)
                    .into(viewHolder.ivHolder);

            Log.d("chk", "글라이드: 완료 "+position+",  size"+arr.size());
//            Log.d("img", "http://192.168.7.31:8180/oop/img/place/"+arr.get(position).pImage);
            //카페 아이
//            Glide.with(getActivity())
//                    .load("http://172.20.10.4:8180/oop/img/shoes/"+arr.get(position).pImage)
//                    .into(viewHolder.ivPiconHolder);
//            Log.d("img", "http://172.20.10.4/oop/img/shoes/"+arr.get(position).pImage);

//        http://192.168.7.26
//        http://172.20.10.4
            return convertView;
        }
    }
}
