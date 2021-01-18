package com.example.coditplace2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchDetailFrag2 extends BaseFrag implements View.OnClickListener{

    ImageView iv_bg;
    ImageView iv_icon;
    TextView tv_pname;
    Button btn_like;
    TextView tv_info;
    TextView tv_eval;
    TextView tv_review;
    TextView tv_contact;

    TextView tv_comment;
    TextView tv_pspace;
    TextView tv_pplug;
    TextView tv_ptable;
    TextView tv_wifi;
    TextView tv_wifibreak;
    TextView tv_pnoise;
    TextView tv_pmusic;
    TextView tv_pbright;
    TextView tv_plight;

    public SearchDetailFrag2(String pidx) {
        this.pidx = pidx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_searchdetail2, container, false);

        iv_bg=layout.findViewById(R.id.iv_bg);
        iv_icon=layout.findViewById(R.id.iv_icon);
        tv_pname=layout.findViewById(R.id.tv_pname);
        btn_like=layout.findViewById(R.id.btn_like);
        tv_info=layout.findViewById(R.id.tv_info);//매장정보
        tv_eval=layout.findViewById(R.id.tv_evaluation);//코더의 평가
        tv_review=layout.findViewById(R.id.tv_review);//리뷰(댓글)
        tv_contact=layout.findViewById(R.id.tv_contact);//연락처

        tv_comment=layout.findViewById(R.id.tv_comment);
        tv_pspace=layout.findViewById(R.id.tv_pspace);
        tv_pplug=layout.findViewById(R.id.tv_pplug);
        tv_ptable=layout.findViewById(R.id.tv_ptable);
        tv_wifi=layout.findViewById(R.id.tv_wifi);
        tv_wifibreak=layout.findViewById(R.id.tv_wifibreak);
        tv_pnoise=layout.findViewById(R.id.tv_pnoise);
        tv_pmusic=layout.findViewById(R.id.tv_pmusic);
        tv_pbright=layout.findViewById(R.id.tv_pbright);
        tv_plight=layout.findViewById(R.id.tv_plight);

        btn_like.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_eval.setOnClickListener(this);
        tv_review.setOnClickListener(this);
        tv_contact.setOnClickListener(this);

        requestForData();
        return layout;
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
    //해당 pidx 받아오기
    String pidx;

    private void requestForData(){
        Log.d("chk", "장소 상세 코더평가");
        Log.d("chk", "pidx :" +pidx);
        params.clear();
        params.put("pidx", pidx);
        request("getPlaceDetailList.do", successListener);
    }
    Response.Listener<String> successListener = new Response.Listener<String>() {
        //가져온 jsonArray 리스트뷰로 나타내기
        @Override
        public void onResponse(String response) {
            Log.d("123", "onResponse: response" + response);
            try {
                JSONArray proArr = new JSONArray(response);
                Log.d("proArr", "onResponse:" + response);
                for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
                    JSONObject proObj = proArr.getJSONObject(i);
                    //장소 기본
                    String pimage1 = proObj.getString("pimage1");
                    String pname = proObj.getString("pname");
//                    String picon = proObj.getString("picon");
//                    String pcontent = proObj.getString("pcontent");
//                    String paddress = proObj.getString("paddress");
                    //에디터 평가 척도
                    String pval = proObj.getString("pval");
                    String pspace = proObj.getString("pspace");
                    String pplug = proObj.getString("pplug");
                    String ptable = proObj.getString("ptable");
                    String wifi = proObj.getString("wifi");
                    String wifi_break = proObj.getString("wifi_break");
                    String pnoise = proObj.getString("pnoise");
                    String pmusic = proObj.getString("pmusic");
                    String pbright = proObj.getString("pbright");
                    String plight = proObj.getString("plight");

                    Glide.with(getActivity()).load("http://192.168.7.31:8180/oop/img/place/"+pimage1)
                            .into(iv_bg);
                    tv_pname.setText(pname);
                    tv_comment.setText(pval);
                    tv_pspace.setText(pspace);
                    tv_pplug.setText(pplug);
                    tv_ptable.setText(ptable);
                    tv_wifi.setText(wifi);
                    tv_wifibreak.setText(wifi_break);
                    tv_pnoise.setText(pnoise);
                    tv_pmusic.setText(pmusic);
                    tv_pbright.setText(pbright);
                    tv_plight.setText(plight);

                    // 에디터 평가

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

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
}
