package com.example.coditplace2.place_detail;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;
import com.example.coditplace2.Storage;
import com.example.coditplace2.databinding.FragSearchBinding;
import com.example.coditplace2.databinding.FragSearchdetailBinding;
import com.example.coditplace2.dto.ItemData;
import com.example.coditplace2.retrofit.RetroClient;
import com.example.coditplace2.retrofit.responseBody.ResponseGet;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_bkinsert;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_detail1;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchDetailFrag extends BaseFrag implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    //googleMap
    private MapView mapView = null;

    String address_changed;
    Double mLat_changed;
    Double mLng_changed;
    ArrayList<ImgArr> arr = new ArrayList<>();
    MyAdapter adapter;

    //viewBinding
    private FragSearchdetailBinding binding;

    public SearchDetailFrag(String pidx) {
        this.pidx = pidx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //viewBinding
        binding = FragSearchdetailBinding.inflate(inflater, container, false);
        View layout = binding.getRoot();
        binding.btnLike.setOnClickListener(this);
        binding.tvInfo.setOnClickListener(this);
        binding.tvEvaluation.setOnClickListener(this);
        binding.tvReview.setOnClickListener(this);
        binding.tvContact.setOnClickListener(this);
        requestR(arr);
        return layout;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //액티비티가 처음 생성될 때 실행되는 함수
        if(mapView!=null){
            mapView.onCreate(savedInstanceState);
        }
    }
    //지오코딩
    public Double setLat_changed(String str){ //위도 변환
        Geocoder mGeoCoder = new Geocoder(getContext());
        try{
            List<Address> mResultLocation = mGeoCoder.getFromLocationName(str, 1);
            mLat_changed = mResultLocation.get(0).getLatitude();
            mLng_changed = mResultLocation.get(0).getLongitude();
            Log.d("abc", "mLat_changed :"+ mLat_changed);
        }catch(IOException e){
            e.printStackTrace();
            Log.d("abc", "주소변환 실패");
        }
        return mLat_changed;
    }
    public Double setLng_changed(String str){ //경도 변환
        Geocoder mGeoCoder = new Geocoder(getContext());
        try{
            List<Address> mResultLocation = mGeoCoder.getFromLocationName(str, 1);
            mLat_changed = mResultLocation.get(0).getLatitude();
            mLng_changed = mResultLocation.get(0).getLongitude();
            Log.d("abc", "mLng_changed: " + mLng_changed);
        }catch(IOException e){
            e.printStackTrace();
            Log.d("abc", "주소변환 실패");
        }
        return mLng_changed;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("addressChanger", "changer test: " +address_changed);
        LatLng CAFE = new LatLng(setLat_changed(address_changed), setLng_changed(address_changed));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CAFE);
        markerOptions.title("cafe_test");
        markerOptions.snippet("cafe_test_snippet");
        googleMap.addMarker(markerOptions);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(CAFE));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17)); //지도 크게 작게 (ZOOM 정도)
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    //해당 pidx 받아오기
    String pidx;
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_like){//좋아요 버튼
            if(Storage.USER.equals("")){
                Toast.makeText(getActivity(), "로그인 후 이용 가능합니다 :(", Toast.LENGTH_SHORT).show();
            }else{
                rBkInsert();
                Toast.makeText(getActivity(), "북마크에 추가! 마이페이지에서 확인하세요 :)", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()==R.id.tv_info){ //매장정보
            Log.d("chk", "onClick: 매장정보 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(1);
        }else if(v.getId()==R.id.tv_evaluation){ //코더 평가
            Log.d("chk", "onClick: 코더 평가 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(2);

        }else if(v.getId()==R.id.tv_review) { //리뷰(댓글)
            Log.d("chk", "onClick: 리뷰 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(3);

        }else if(v.getId()==R.id.tv_contact){ //연락처
            Log.d("chk", "onClick: 연락처 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(4);
        }
    }
    //Retrofit
    private void rBkInsert(){
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("pidx", pidx);
        params2.put("mid", Storage.USER);
        RetroClient.getRetroBaseApiService().rBk_insert(params2).enqueue(new Callback<List<ResponseGet_bkinsert>>() {
            @Override
            public void onResponse(Call<List<ResponseGet_bkinsert>> call, retrofit2.Response<List<ResponseGet_bkinsert>> response) {
                Log.d("kkk", "북마크 추가 성공" + response);
            }

            @Override
            public void onFailure(Call<List<ResponseGet_bkinsert>> call, Throwable t) {
                Log.d("retrofit", "onResponse: bkinsert failed");
            }
        });
    }

    private void requestR(ArrayList<ImgArr> arr){
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("pidx", pidx);
        RetroClient.getRetroBaseApiService().rPlace_basic(params2).enqueue(new Callback<List<ResponseGet_detail1>>() {
            @Override
            public void onResponse(Call<List<ResponseGet_detail1>> call, retrofit2.Response<List<ResponseGet_detail1>>  response) {
                List<ResponseGet_detail1> result = response.body();
                Log.d("retrofit", "onResponse: success1" + result); //200 정상통신
                for(int i = 0; i<result.size(); i++){
                    String pimage1 = result.get(i).getPimage1();
                    String pimage2 = result.get(i).getPimage2();
                    String pimage3 = result.get(i).getPimage3();
                    String pname = result.get(i).getPname();
                    String pvisit = result.get(i).getPvisit();
                    String pcontent = result.get(i).getPcontent();
                    String paddress = result.get(i).getPaddress();
                    address_changed = paddress;
                    arr.add(new ImgArr(pimage1));
                    arr.add(new ImgArr(pimage2));
                    arr.add(new ImgArr(pimage3));
                    adapter = new MyAdapter(getActivity());
                    binding.grid.setAdapter(adapter);

                    //response에 맞게 화면 변화시켜주기
                    Glide.with(getActivity()).load(Storage.IMG_URL+pimage1)
                            .into(binding.ivBg);
                    binding.tvPname.setText(pname);
                    binding.tvVisit.setText(pvisit);
                    binding.tvComment.setText(pcontent);
                    binding.tvPaddress.setText(paddress);
                }
                mapView.getMapAsync(SearchDetailFrag.this);
            }
            @Override
            public void onFailure(Call<List<ResponseGet_detail1>>  call, Throwable t) {
                Log.d("retrofit", "onResponse: failed");
            }
        });
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
            Glide.with(getActivity())
                    .load(Storage.IMG_URL+arr.get(position).pImage)
                    .into(viewHolder.ivHolder);

            Log.d("chk", "글라이드: 완료 "+position+",  size"+arr.size());
            return convertView;
        }
    }
}
