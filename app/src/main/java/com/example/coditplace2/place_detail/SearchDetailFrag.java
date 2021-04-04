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
import java.util.List;

public class SearchDetailFrag extends BaseFrag implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    //googleMap
    private MapView mapView = null;
    String address_changed;
    Double mLat_changed;
    Double mLng_changed;
/*    public SearchDetailFrag(){
        //required
        왜 두버ㅏㄴ째 부터는 앱이ㅓ 종료되는가! 와이! 왜죠?
        난 진짜 잘못한 게 ㄹ없어
        잘못이ㅏ라곤 열심히 한 죄ㅏ 밖에 없 ㅣㄹㄴ알., ㅋㅊㅌ..
    }*/
    ArrayList<ImgArr> arr = new ArrayList<>();
    ImageView iv_bg;
    ImageView iv_icon;
    TextView tv_pname;
    Button btn_like;
    TextView tv_info;
    TextView tv_eval;
    TextView tv_review;
    TextView tv_contact;

    TextView tv_visit;
    TextView tv_comment;
    TextView tv_paddress;

    GridView gridView;
    MyAdapter adapter;

    public SearchDetailFrag(String pidx) {
        this.pidx = pidx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_searchdetail, null);

        iv_bg=layout.findViewById(R.id.iv_bg);
        iv_icon=layout.findViewById(R.id.iv_icon);
        tv_pname=layout.findViewById(R.id.tv_pname);
        btn_like=layout.findViewById(R.id.btn_like);
        tv_info=layout.findViewById(R.id.tv_info);//매장정보
        tv_eval=layout.findViewById(R.id.tv_evaluation);//코더의 평가
        tv_review=layout.findViewById(R.id.tv_review);//리뷰(댓글)
        tv_contact=layout.findViewById(R.id.tv_contact);//연락처

        tv_visit=layout.findViewById(R.id.tv_visit); //방문 날짜
        tv_comment=layout.findViewById(R.id.tv_comment); // 코멘트
        tv_paddress=layout.findViewById(R.id.tv_paddress); // 주소
        
        btn_like.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_eval.setOnClickListener(this);
        tv_review.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
        Log.d("chk", "dfsdf");
        gridView=layout.findViewById(R.id.grid); //그리드뷰

        // map
        mapView = (MapView)layout.findViewById(R.id.map);

        //geocoder
        Geocoder geocoder = new Geocoder(getActivity());

        requestForData();

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
            Log.d("chk", "onClick: 북마크 클릭됨");
            if(Storage.USER.equals("")){
                Toast.makeText(getActivity(), "로그인 후 이용 가능합니다 :(", Toast.LENGTH_SHORT).show();
            }else{
                bkInsert();
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
    //북마크 추가하기
    private void bkInsert(){
        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kkk", "북마크 추가 성공" + response);
            }
        };
        //좋아요 버튼 클릭시 북마크 등록하기
        Log.d("chk", "북마크 등록 통신 start");
        params.clear();
        params.put("pidx", pidx);
        params.put("mid", Storage.USER);
        Log.d("bk", "bkInsert pidx: "+pidx);
        Log.d("bk", "bkInsert mid: "+Storage.USER);
        request("BkInsert.do", successListener);
    }

    //해당 pidx에 해당하는 detail 화면1
    private void requestForData(){
        Log.d("chk", "장소 상세");
        params.clear();
        params.put("pidx", pidx);
        request("getPlacebasic.do", successListener);
    }
    Response.Listener<String> successListener = new Response.Listener<String>() {
        //가져온 jsonArray 리스트뷰로 나타내기
        @Override
        public void onResponse(String response) {
            Log.d("res11", "onResponse: response" + response);
            try {
                JSONArray proArr = new JSONArray(response);
                Log.d("proArr", "onResponse:" + response);
                for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
                    JSONObject proObj = proArr.getJSONObject(i);
                    //장소 상세 이미지 1, 2
                    String pimage2 = proObj.getString("pimage2");
                    String pimage3 = proObj.getString("pimage3");
                    //장소 기본
                    String pimage1 = proObj.getString("pimage1");
                    String pname = proObj.getString("pname");
                    String pvisit = proObj.getString("pvisit");
                    String picon = proObj.getString("picon");
                    String pcontent = proObj.getString("pcontent");
                    String paddress = proObj.getString("paddress");
                    //response에 맞게 주소 바꿔주기
                    address_changed = paddress;

                    //response에 맞게 이미지 바꿔주기 (그리드)
                    arr.add(new ImgArr(pimage1));
                    arr.add(new ImgArr(pimage2));
                    arr.add(new ImgArr(pimage3));

                    adapter = new MyAdapter(getActivity());
                    gridView.setAdapter(adapter);

                    //response에 맞게 화면 변화시켜주기
                    //대표이미지
                    Glide.with(getActivity()).load("http://172.20.10.4:8180/oop/img/place/"+pimage1)
                                            .into(iv_bg);
                    tv_pname.setText(pname);
                    tv_visit.setText(pvisit);
                    tv_comment.setText(pcontent);
                    tv_paddress.setText(paddress);
                }
                mapView.getMapAsync(SearchDetailFrag.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

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
                    .load(Storage.HOME_URL+":8180/oop/img/place/"+arr.get(position).pImage)
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