package com.example.coditplace2.place_list;

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

import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;
import com.example.coditplace2.adapter.MyAdapter;
import com.example.coditplace2.dto.ItemData;
import com.example.coditplace2.place_detail.SearchDetailActivity;
import com.example.coditplace2.retrofit.RetroClient;
import com.example.coditplace2.retrofit.responseBody.ResponseGet;
import com.example.coditplace2.util.ItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchFrag extends BaseFrag implements MyAdapter.MyListener {
    ArrayList<ItemData> arr = new ArrayList<>();

    TextView tv_tit;
    RecyclerView rv;
    MyAdapter adapter; //전역에서 안쓰면 못 불러옴.

    String search; //검색어
    String type; //타입
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
        //recyclerView 초기화
        rv = layout.findViewById(R.id.rv);
        //Retrofit
        request();
        return layout;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public void request(){ //검색 장소가 있을 때
        if(type.equals("pname")){ //장소 이름 검색
            requestRname(type, search, arr);
        }else if(type.equals("plocation")){ // 장소 지역 검색
            requestRlocation(type, search, arr);
        }else if(type.equals("paddress")){ // 장소 주소 검색
            requestRaddress(type, search, arr);
        }else{                  //검색 없으면 리스트 전체출력
            requestRplace(arr);
        }
    }
    //recyclerview!
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        ItemDecoration itemDecorator = new ItemDecoration(50); //리사이클러뷰 아이템 간격
        rv.addItemDecoration(itemDecorator);
        adapter = new MyAdapter(arr, this);
        rv.setAdapter(adapter);
    }
    // 해당 장소 후기 페이지로 넘어가도록 pIDX 넘겨주기(position), 상세페이지
    @Override
    public void myClick(int position) {
        Log.d("myClick", "myClick: Clicked");
        Intent intent = new Intent(getActivity(), SearchDetailActivity.class);
        intent.putExtra("pidx", arr.get(position).getpIdx());
        Log.d("chk", "onItemClick: pidx="+arr.get(position).getpIdx());
        startActivity(intent);
    }
    //Retrofit 메서드
    public void requestRplace(ArrayList<ItemData> arr){
        RetroClient.getRetroBaseApiService().rPlaceList().enqueue(new Callback<List<ResponseGet>>() {
            @Override
            public void onResponse(Call<List<ResponseGet>> call, retrofit2.Response<List<ResponseGet>>  response) {
                List<ResponseGet> result = response.body();
                Log.d("retrofit", "onResponse: success1" + result); //200 정상통신
                Log.d("retrofit", "onResponse: success2"+ result.get(0)+"/"+result.get(1));
                for(int i = 0; i<result.size(); i++){
                    String pidx = String.valueOf(result.get(i).getPidx());
                    String pname = result.get(i).getPname();
                    String pimage1 = result.get(i).getPimage1();
                    String pvisit = result.get(i).getPvisit();
                    String picon = (String) result.get(i).getPicon();
                    String pcategory = String.valueOf(result.get(i).getPcategory());
                    String pphone = result.get(i).getPphone();
                    String pcontent = result.get(i).getPcontent();
                    arr.add(i, new ItemData(pidx, pname, pimage1, pvisit, picon, pcategory, pphone, pcontent, "0"));
                    Log.d("retrofit", "arr:" + arr.get(i).getpName());
                }
                adapter.notifyDataSetChanged();
                total=arr.size();
                tv_tit.setText(total+"개의 결과가 있습니다");
            }
            @Override
            public void onFailure(Call<List<ResponseGet>>  call, Throwable t) {
                Log.d("retrofit", "onResponse: failed");
            }
        });
        initRecyclerView();
    }
    public void requestRlocation(String type, String search, ArrayList<ItemData> arr){
        HashMap<String, String> params2 = new HashMap<>();
        params2.put(type, search);
        RetroClient.getRetroBaseApiService().rPlocation(params2).enqueue(new Callback<List<ResponseGet>>() {
            @Override
            public void onResponse(Call<List<ResponseGet>> call, retrofit2.Response<List<ResponseGet>>  response) {
                List<ResponseGet> result = response.body();
                Log.d("retrofit", "onResponse: success1" + result); //200 정상통신
                Log.d("retrofit", "onResponse: success2"+ result.get(0)+"/"+result.get(1));
                for(int i = 0; i<result.size(); i++){
                    String pidx = String.valueOf(result.get(i).getPidx());
                    String pname = result.get(i).getPname();
                    String pimage1 = result.get(i).getPimage1();
                    String pvisit = result.get(i).getPvisit();
                    String picon = (String) result.get(i).getPicon();
                    String pcategory = String.valueOf(result.get(i).getPcategory());
                    String pphone = result.get(i).getPphone();
                    String pcontent = result.get(i).getPcontent();
                    arr.add(i, new ItemData(pidx, pname, pimage1, pvisit, picon, pcategory, pphone, pcontent, "0"));
                    Log.d("retrofit", "arr:" + arr.get(i).getpName());
                }
                adapter.notifyDataSetChanged();
                total=arr.size();
                tv_tit.setText(total+"개의 결과가 있습니다");
            }
            @Override
            public void onFailure(Call<List<ResponseGet>>  call, Throwable t) {
                Log.d("retrofit", "onResponse: failed");
            }
        });
        initRecyclerView();
    }
    public void requestRname(String type, String search, ArrayList<ItemData> arr){
        HashMap<String, String> params2 = new HashMap<>();
        params2.put(type, search);
        RetroClient.getRetroBaseApiService().rPname(params2).enqueue(new Callback<List<ResponseGet>>() {
            @Override
            public void onResponse(Call<List<ResponseGet>> call, retrofit2.Response<List<ResponseGet>>  response) {
                List<ResponseGet> result = response.body();
                Log.d("retrofit", "onResponse: success1" + result); //200 정상통신
                Log.d("retrofit", "onResponse: success2"+ result.get(0)+"/"+result.get(1));
                for(int i = 0; i<result.size(); i++){
                    String pidx = String.valueOf(result.get(i).getPidx());
                    String pname = result.get(i).getPname();
                    String pimage1 = result.get(i).getPimage1();
                    String pvisit = result.get(i).getPvisit();
                    String picon = (String) result.get(i).getPicon();
                    String pcategory = String.valueOf(result.get(i).getPcategory());
                    String pphone = result.get(i).getPphone();
                    String pcontent = result.get(i).getPcontent();
                    arr.add(i, new ItemData(pidx, pname, pimage1, pvisit, picon, pcategory, pphone, pcontent, "0"));
                    Log.d("retrofit", "arr:" + arr.get(i).getpName());
                }
                adapter.notifyDataSetChanged();
                total=arr.size();
                tv_tit.setText(total+"개의 결과가 있습니다");
            }
            @Override
            public void onFailure(Call<List<ResponseGet>>  call, Throwable t) {
                Log.d("retrofit", "onResponse: failed");
            }
        });
        initRecyclerView();
    }
    public void requestRaddress(String type, String search, ArrayList<ItemData> arr){
        HashMap<String, String> params2 = new HashMap<>();
        params2.put(type, search);
        RetroClient.getRetroBaseApiService().rPaddress(params2).enqueue(new Callback<List<ResponseGet>>() {
            @Override
            public void onResponse(Call<List<ResponseGet>> call, retrofit2.Response<List<ResponseGet>>  response) {
                List<ResponseGet> result = response.body();
                Log.d("retrofit", "onResponse: success1" + result); //200 정상통신
                Log.d("retrofit", "onResponse: success2"+ result.get(0)+"/"+result.get(1));
                for(int i = 0; i<result.size(); i++){
                    String pidx = String.valueOf(result.get(i).getPidx());
                    String pname = result.get(i).getPname();
                    String pimage1 = result.get(i).getPimage1();
                    String pvisit = result.get(i).getPvisit();
                    String picon = (String) result.get(i).getPicon();
                    String pcategory = String.valueOf(result.get(i).getPcategory());
                    String pphone = result.get(i).getPphone();
                    String pcontent = result.get(i).getPcontent();
                    arr.add(i, new ItemData(pidx, pname, pimage1, pvisit, picon, pcategory, pphone, pcontent, "0"));
                    Log.d("retrofit", "arr:" + arr.get(i).getpName());
                }
                adapter.notifyDataSetChanged();
                total=arr.size();
                tv_tit.setText(total+"개의 결과가 있습니다");
            }
            @Override
            public void onFailure(Call<List<ResponseGet>>  call, Throwable t) {
                Log.d("retrofit", "onResponse: failed");
            }
        });
        initRecyclerView();
    }
}
