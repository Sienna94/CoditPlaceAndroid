package com.example.coditplace2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFrag extends BaseFrag implements AdapterView.OnItemClickListener {
    ArrayList<ItemData> arr = new ArrayList<>();
    TextView tv_tit;
    ListView lv;
    MyAdapter adapter; //전역에서 안쓰면 못 불러옴.

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static SearchFrag newInstance() {
        return new SearchFrag();
    }

    public SearchFrag() {
        
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_search, container, false);
        tv_tit=layout.findViewById(R.id.tv_tit);
        lv=layout.findViewById(R.id.lv);

//        test();
        requestForData();
        return layout;
    }

//    private void test(){
//        arr.add(0, new ItemData("1984", "null", "방문일 : 2020.10.22",
//                "null", "카페", "02-325-1984", "대체적으로 어두운 분위기로..",
//                "1"));
//        adapter = new MyAdapter(getActivity());
//        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(this); // 여기서 말하는 this는 리스너를 가지고 있는 애가 누구인가를 물어보니까
//                                        //리스너를 갖고 있는 현재 frag 즉 this를 써줌 됨.
//    }
    private void requestForData(){
        //상속받은 부분
        Log.d("chk", "장소 리스트 requestForData: start");
        params.clear();
        request("PlaceList.do", successListener);
        //어댑터에 적용
        adapter = new MyAdapter(getActivity());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }
    Response.Listener<String> successListener = new Response.Listener<String>() {
        //가져온 jsonArray 리스트뷰로 나타내기
        @Override
        public void onResponse(String response) {
            try {
                JSONArray proArr = new JSONArray(response);
                Log.d("proArr", "onResponse:"+response);
                for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
                    JSONObject proObj = proArr.getJSONObject(i);
                    String pname = proObj.getString("pname");
                    String pimage1 = proObj.getString("pimage1");
                    String pvisit = proObj.getString("pvisit");
                    String picon = proObj.getString("picon");
                    String pcategory = proObj.getString("pcategory");
                    String pphone = proObj.getString("pphone");
                    String pcontent = proObj.getString("pcontent");
                    //리스트에 보여줄 어레이에 추가
                    arr.add(i, new ItemData(pname, pimage1, pvisit, picon, pcategory, pphone, pcontent, "0"));

                    Log.d("chk1", "arr:"+arr.get(i).pName);
                }
                //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    // 해당 장소 후기 페이지로 넘어가도록
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "예정", Toast.LENGTH_SHORT).show();

    }

    class ItemHolder {
        TextView tvPnameHolder;
        ImageView ivPimage1Holder;
        TextView tvPvisitHolder;
        ImageView ivPiconHolder;
        TextView tvPcategoryHolder;
        TextView tvPphoneHolder;
        TextView tvPcontentHolder;
        TextView tvPlikeHolder;
    }
    class  MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;
        public MyAdapter(Activity context) {
            super(context, R.layout.items, arr);
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
                convertView = lnf.inflate(R.layout.items, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvPnameHolder = convertView.findViewById(R.id.tv_pname);
                viewHolder.ivPimage1Holder = convertView.findViewById(R.id.iv_bg);
                viewHolder.tvPvisitHolder = convertView.findViewById(R.id.tv_visit);
                viewHolder.ivPiconHolder = convertView.findViewById(R.id.iv_icon);
                viewHolder.tvPcategoryHolder = convertView.findViewById(R.id.tv_category);
                viewHolder.tvPphoneHolder = convertView.findViewById(R.id.tv_pphone);
                viewHolder.tvPcontentHolder = convertView.findViewById(R.id.tv_pcontent);
                viewHolder.tvPlikeHolder = convertView.findViewById(R.id.tv_like);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }
            //가게 이름, 가게방문일, 가게 카테고리, 가게 연락처, 가게설명, 좋아요(사용자)
            viewHolder.tvPnameHolder.setText(arr.get(position).pName);
            viewHolder.tvPvisitHolder.setText(arr.get(position).pVisit);
            viewHolder.tvPcategoryHolder.setText(arr.get(position).pCategory);
            viewHolder.tvPphoneHolder.setText(arr.get(position).pPhone);
            viewHolder.tvPcontentHolder.setText(arr.get(position).pContent);
            viewHolder.tvPlikeHolder.setText(arr.get(position).pLike);

//            카페 사진
            Glide.with(getActivity())
                    .load("http://172.20.10.4:8180/oop/img/place/"+arr.get(position).pImage)
                    .into(viewHolder.ivPimage1Holder);
            Log.d("img", "http://172.20.10.4:8180/oop/img/place/"+arr.get(position).pImage);
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
