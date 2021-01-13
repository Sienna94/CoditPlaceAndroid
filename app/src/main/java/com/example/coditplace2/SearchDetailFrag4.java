package com.example.coditplace2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;

public class SearchDetailFrag4 extends BaseFrag implements View.OnClickListener{

    ImageView iv_bg;
    ImageView iv_icon;
    TextView tv_pname;
    Button btn_like;
    TextView tv_info;
    TextView tv_eval;
    TextView tv_review;
    TextView tv_contact;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static SearchDetailFrag4 newInstance(){
        return new SearchDetailFrag4();
    }
    public SearchDetailFrag4(){

    }

    public SearchDetailFrag4(String pidx) {
        this.pidx = pidx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_searchdetail4, container, false);

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

        return layout;
    }
    //해당 pidx 받아오기
    String pidx;

    private void requestForData(){
        Log.d("chk", "장소 상세 연락처");
        params.clear();
        params.put("pidx", pidx);
//        request("getPlacebasic.do", successListener);
    }
    Response.Listener<String> successListener = new Response.Listener<String>() {
        //가져온 jsonArray 리스트뷰로 나타내기
        @Override
        public void onResponse(String response) {
//            Log.d("res11", "onResponse: response" + response);
//            try {
//                JSONArray proArr = new JSONArray(response);
//                Log.d("proArr", "onResponse:" + response);
//                for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
//                    JSONObject proObj = proArr.getJSONObject(i);
//                    //장소 상세 이미지 1, 2
//                    String pimage2 = proObj.getString("pimage2");
//                    String pimage3 = proObj.getString("pimage3");
//                    //장소 기본
//                    String pimage1 = proObj.getString("pimage1");
//                    String pname = proObj.getString("pname");
//                    String pvisit = proObj.getString("pvisit");
//                    String picon = proObj.getString("picon");
//                    String pcontent = proObj.getString("pcontent");
//                    String paddress = proObj.getString("paddress");
//
//                    //response에 맞게 이미지 바꿔주기 (그리드)
//                    arr.add(new SearchDetailFrag.ImgArr(pimage1));
//                    arr.add(new SearchDetailFrag.ImgArr(pimage2));
//                    arr.add(new SearchDetailFrag.ImgArr(pimage3));
//
//                    adapter = new SearchDetailFrag.MyAdapter(getActivity());
//                    gridView.setAdapter(adapter);
//
//                    //response에 맞게 화면 변화시켜주기
//                    //대표이미지
//                    Glide.with(getActivity()).load("http://192.168.7.31:8180/oop/img/place/"+pimage1)
//                            .into(iv_bg);
//                    tv_pname.setText(pname);
//                    tv_visit.setText(pvisit);
//                    tv_comment.setText(pcontent);
//                    tv_paddress.setText(paddress);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_like){//좋아요 버튼

        }else if(v.getId()==R.id.tv_info){ //매장정보
            Log.d("chk", "onClick: 매장정보 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(SearchDetailFrag.newInstance());

        }else if(v.getId()==R.id.tv_evaluation){ //코더 평가
            Log.d("chk", "onClick: 코더 평가 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(SearchDetailFrag2.newInstance());

        }else if(v.getId()==R.id.tv_review) { //리뷰(댓글)
            Log.d("chk", "onClick: 리뷰 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(SearchDetailFrag3.newInstance());

        }else if(v.getId()==R.id.tv_contact){ //연락처
            Log.d("chk", "onClick: 연락처 tv 클릭됨");
            ((SearchDetailActivity)getActivity()).replaceFragment(SearchDetailFrag4.newInstance());

        }
    }
}
