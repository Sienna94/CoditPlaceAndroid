package com.example.coditplace2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.coditplace2.adapter.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFrag extends BaseFrag implements MyAdapter.MyListener {
    ArrayList<ItemData> arr = new ArrayList<>();

    TextView tv_tit;
    RecyclerView rv;
    MyAdapter adapter; //전역에서 안쓰면 못 불러옴.

    String search; //검색어
    int total;

    public SearchFrag(String type, String search) {
        this.type = type;
        this.search = search;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_search, container, false);

        tv_tit = layout.findViewById(R.id.tv_tit);
        //recyclervview 초기화
        rv = layout.findViewById(R.id.rv);
        //Retrofit
        requestR(type, search, arr);
        //Volley
        request();
        return layout;
    }

    String type; //타입 // 밖으로 빼준다.

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public void request(){ //검색 장소가 있을 때
        Log.d("abc", "request: "+type);
        if(type.equals("pname")){ //장소 이름 검색
            requestPname();
        }else if(type.equals("plocation")){ // 장소 지역 검색
            requestPlocation(); 
        }else if(type.equals("paddress")){ // 장소 주소 검색
            requestPaddress();
        }else{                  //검색 없으면 리스트 전체출력
            requestForData(); 
        }
    }

    private void requestPname(){ //입력값이 장소 이름일 때 출력
        Log.d("chk", "장소 리스트 이름 requestPname: start");
        final String pname = search;
        params.clear();
        params.put("pname", search);
        request("PlaceListName.do", successListener);
        //어댑터에 적용
        initRecyclerView();
    }
    private void requestPlocation(){ //입력값이 장소 지역일 때 출력
        Log.d("chk", "장소 리스트 지역 requestPlocation: start");
        final String plocation = search;
        params.clear();
        params.put("plocation", search);
        request("PlaceListLocation.do", successListener);
        //어댑터에 적용
        initRecyclerView();
    }
    private void requestPaddress(){//입력값이 장소 주소일 때 출력
        Log.d("chk", "주소 리스트 지역 requestPaddress: start");
        final String paddress = search;
        params.clear();
        params.put("paddress", search);
        request("PlaceListAddress.do", successListener);
        //어댑터에 적용
        initRecyclerView();
    }

    private void requestForData() { //입력값 없이 전체 출력
        //상속받은 부분
        Log.d("chk", "장소 리스트 전체 requestForData: start");
        params.clear();
        request("PlaceList.do", successListener);
        //어댑터에 적용
        initRecyclerView();
    }

    Response.Listener<String> successListener = new Response.Listener<String>() {
        //가져온 jsonArray arr에 추가
        @Override
        public void onResponse(String response) {
            try {
                JSONArray proArr = new JSONArray(response);
                Log.d("proArr", "onResponse:" + response);
                for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
                    JSONObject proObj = proArr.getJSONObject(i);
                    String pidx = proObj.getString("pidx");
                    String pname = proObj.getString("pname");
                    String pimage1 = proObj.getString("pimage1");
                    String pvisit = proObj.getString("pvisit");
                    String picon = proObj.getString("picon");
                    String pcategory = proObj.getString("pcategory");
                    String pphone = proObj.getString("pphone");
                    String pcontent = proObj.getString("pcontent");
                    //리스트에 보여줄 어레이에 추가
                    arr.add(i, new ItemData(pidx, pname, pimage1, pvisit, picon, pcategory, pphone, pcontent, "0"));

                    Log.d("chk1", "arr:" + arr.get(i).pName);
                }
                //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                adapter.notifyDataSetChanged();
                total=arr.size();
                tv_tit.setText(total+"개의 결과가 있습니다");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    //recyclerview!
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
//        ItemDecoration itemDecorator = new ItemDecoration(10); //리사이클러뷰 아이템 간격
//        rv.addItemDecoration(itemDecorator);
        adapter = new MyAdapter(arr, this);
        rv.setAdapter(adapter);
    }
    // 해당 장소 후기 페이지로 넘어가도록 pIDX 넘겨주기(position), 상세페이지
    @Override
    public void myClick(int position) {
        Log.d("myClick", "myClick: Clicked");
        Intent intent = new Intent((SearchActivity) getActivity(), com.example.coditplace2.SearchDetailActivity.class);
        intent.putExtra("pidx", arr.get(position).pIdx);
        Log.d("chk", "onItemClick: pidx="+arr.get(position).pIdx);
        startActivity(intent);
    }
    //Retrofit 메서드

}
