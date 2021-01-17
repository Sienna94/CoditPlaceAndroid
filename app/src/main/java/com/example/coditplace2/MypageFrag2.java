package com.example.coditplace2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MypageFrag2 extends BaseFrag implements View.OnClickListener{ //북마크 페이지
    ArrayList<ItemDataBk> arr = new ArrayList<>();
    TextView tv;
    ListView lv;
    MyAdapter adapter;

    public MypageFrag2(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_mypage2, container, false);

        tv=layout.findViewById(R.id.tit);
        lv=layout.findViewById(R.id.lv);
        test();
        return layout;
    }
    //리스트뷰 테스트
    public void test(){
        arr.add(0, new ItemDataBk(0,"1","아일랜드팩토리 풍류",  "picon"));
        arr.add(1, new ItemDataBk(1,"2","프릳츠 양재점",  "picon"));
        arr.add(2, new ItemDataBk(2,"3","와겐커피 미아점(본점)",  "picon"));
        Log.d("chk", "test: 실행되냐"+arr.get(0).pName+arr.get(1).pName+arr.get(2).pName);
        //어댑터 적용
        adapter = new MyAdapter(getActivity());
        lv.setAdapter(adapter);
    }
    //테스트 삭제
    public void test_del(int bKidx){
        arr.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }


    class ItemHolder {
        TextView tvPnameHolder;
        ImageView ivPiconHolder;
        TextView tvGetpostHolder;
        TextView tvDelbkHolder;
    }
    int position; // 액티비티 안의 position을 말한다! myadapter안의 position이 아니라 액티비티 포지션을 찾아서 지워줘야되니까.
    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.items_bkmark, arr);
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
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.items_bkmark, parent, false);
                viewHolder = new ItemHolder();
                viewHolder.tvPnameHolder = convertView.findViewById(R.id.tv_pname);
                viewHolder.ivPiconHolder = convertView.findViewById(R.id.iv_icon);
                viewHolder.tvGetpostHolder = convertView.findViewById(R.id.tv_list);
                viewHolder.tvDelbkHolder = convertView.findViewById(R.id.tv_del);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemHolder) convertView.getTag();
            }
            //가게 아이콘, 가게 이름,
            viewHolder.tvPnameHolder.setText(arr.get(position).pName);
            //북마크 삭제 클릭
            viewHolder.tvDelbkHolder.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("chk", "북마크 삭제 클릭");
                    MypageFrag2.this.position = position;
                    int bKidx = arr.get(position).bKidx;
                    Log.d("chk", "북마크 삭제, bKidx :" + bKidx);
                    test_del(bKidx);
                }
            });

////            카페 사진
//            Glide.with(getActivity())
//                    .load("http://192.168.7.31:8180/oop/img/place/" + arr.get(position).pImage)
//                    .into(viewHolder.ivPimage1Holder);
//            Log.d("img", "http://192.168.7.31:8180/oop/img/place/" + arr.get(position).pImage);
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
