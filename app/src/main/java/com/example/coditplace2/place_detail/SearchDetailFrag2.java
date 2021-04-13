package com.example.coditplace2.place_detail;

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
import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;
import com.example.coditplace2.Storage;
import com.example.coditplace2.retrofit.RetroClient;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_Review;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_bkinsert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

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

        requestR();
        return layout;
    }
    String pidx;
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
    private void requestR() {
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("pidx", pidx);
        RetroClient.getRetroBaseApiService().rPreview(params2).enqueue(new Callback<List<ResponseGet_Review>>() {
            @Override
            public void onResponse(Call<List<ResponseGet_Review>> call, retrofit2.Response<List<ResponseGet_Review>> response) {
                List<ResponseGet_Review> result = response.body();
                for (int i = 0; i < result.size(); i++) {
                    String pimage1 = result.get(i).getPimage1();
                    String pname = result.get(i).getPname();
                    String pval = result.get(i).getPval();
                    String pspace = String.valueOf(result.get(i).getPspace());
                    String pplug = String.valueOf(result.get(i).getPplug());
                    String ptable = String.valueOf(result.get(i).getPtable());
                    String wifi = String.valueOf(result.get(i).getWifi());
                    String wifi_break = String.valueOf(result.get(i).getWifiBreak());
                    String pnoise = String.valueOf(result.get(i).getPnoise());
                    String pmusic = String.valueOf(result.get(i).getPmusic());
                    String pbright = String.valueOf(result.get(i).getPbright());
                    String plight = String.valueOf(result.get(i).getPlight());

                    Glide.with(getActivity()).load("http://192.168.7.31:8180/oop/img/place/" + pimage1)
                            .into(iv_bg);
                    tv_pname.setText(pname);
                    tv_comment.setText(pval);
                    tv_pspace.setText(evalChanger(pspace));
                    tv_pplug.setText(evalChanger(pplug));
                    tv_ptable.setText(evalChanger(ptable));
                    tv_wifi.setText(evalChanger4(wifi));
                    tv_wifibreak.setText(evalChanger3(wifi_break));
                    tv_pnoise.setText(evalChanger(pnoise));
                    tv_pmusic.setText(evalChanger(pmusic));
                    tv_pbright.setText(evalChanger(pbright));
                    tv_plight.setText(evalChanger2(plight));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseGet_Review>> call, Throwable t) {

            }
        });
    }

    //eval changer()
    public String evalChanger(String str){
        String eval_changed="";
        if(str.equals("0")){
            eval_changed = "★☆☆☆☆";
        }else if(str.equals("1")){
            eval_changed = "★★★☆☆";
        }else if(str.equals("2")) {
            eval_changed = "★★★★★";
        }
        return eval_changed;
    }
    public String evalChanger2(String str){
        String eval_changed="";
        if(str.equals("0")){
            eval_changed = "형광등(하얀색 계열)";
        }else if(str.equals("1")){
            eval_changed = "백열등(노란색 계열)";
        }
        return eval_changed;
    }
    public String evalChanger3(String str){
        String eval_changed="";
        if(str.equals("0")){
            eval_changed = "자주 끊김";
        }else if(str.equals("1")){
            eval_changed = "보통(0~1회)";
        }else if(str.equals("2")) {
            eval_changed = "원활(없음)";
        }
        return eval_changed;
    }
    public String evalChanger4(String str){
        String eval_changed="";
        if(str.equals("0")){
            eval_changed = "비번 없음";
        }else if(str.equals("1")){
            eval_changed = "있음";
        }
        return eval_changed;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_like){//좋아요 버튼
            Log.d("chk", "onClick: 북마크 클릭됨");
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
}
