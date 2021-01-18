package com.example.coditplace2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.Response;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MypageFrag2 extends BaseFrag { //북마크 페이지
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
//        test();
        requestForData();
        return layout;
    }
    //리스트뷰 테스트
    public void test(){
        arr.add(0, new ItemDataBk(0,1,"아일랜드팩토리 풍류",  "picon"));
        arr.add(1, new ItemDataBk(1,2,"프릳츠 양재점",  "picon"));
        arr.add(2, new ItemDataBk(2,3,"와겐커피 미아점(본점)",  "picon"));
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
    //북마크 리스트
    private void requestForData(){
        Response.Listener<String> successListener = new Response.Listener<String>() {
            //가져온 jsonArray 리스트뷰로 나타내기
            @Override
            public void onResponse(String response) {
                Log.d("bookmark", "onResponse: response" + response);
                try {
                    JSONArray proArr = new JSONArray(response);
                    Log.d("proArr", "onResponse:" + response);
                    arr.clear(); //기존에 있는 걸 날려야 중복되는 게 더해지지 않음.
                    for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
                        JSONObject proObj = proArr.getJSONObject(i);
                        //댓글 리스트
                        int bkidx =Integer.parseInt(proObj.getString("bkidx"));
                        int pidx = Integer.parseInt(proObj.getString("pidx"));
                        String pname = proObj.getString("pname");
                        String picon = proObj.getString("picon");

                        //리스트에 보여줄 어레이에 추가
                        arr.add(i, new ItemDataBk(bkidx,pidx,pname,picon));
                        Log.d("reply", arr.get(i).pName);
                    }
                    //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.d("chk", "북마크 리스트 요청");
        params.clear();
        params.put("mid", Storage.USER);
        request("getBkList.do", successListener);
        adapter = new MyAdapter(getActivity());
        lv.setAdapter(adapter);
    }
    //북마크 삭제
    private void deleteBk(int bkidx){
        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kkk", "북마크 삭제 성공" + response);
                arr.remove(position);
                adapter.notifyDataSetChanged();
            }
        };
        //삭제 tv 클릭시 댓글 삭제하기 delete
        Log.d("bbb", "onClick : 댓글 삭제 try");
        String num =  Integer.toString(bkidx);    //스트링으로 파라미터에 넣어줘야 함
        params.clear();
        params.put("bidx", num);
        request("BkDelete.do", successListener);
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
            //해당 포스팅으로 이동
            viewHolder.tvGetpostHolder.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("chk", "북마크 포스팅으로 이동 클릭");
                    MypageFrag2.this.position = position;
                    Intent intent = new Intent(getActivity(), com.example.coditplace2.SearchDetailActivity.class);
                    intent.putExtra("pidx", String.valueOf(arr.get(position).pIdx));
                    Log.d("chk", "포스팅으로 이동 클릭: pidx="+arr.get(position).pIdx);
                    startActivity(intent);
                }
            });
            //북마크 삭제 클릭
            viewHolder.tvDelbkHolder.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("chk", "북마크 삭제 클릭");
                    MypageFrag2.this.position = position;
                    int bKidx = arr.get(position).bKidx;
                    Log.d("chk", "북마크 삭제, bKidx :" + bKidx);
//                    test_del(bKidx);
                    deleteBk(bKidx);
                }
            });
            return convertView;
        }
    }
}
