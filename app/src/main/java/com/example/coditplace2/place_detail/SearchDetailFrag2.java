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
import com.example.coditplace2.databinding.FragSearchdetail2Binding;
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

    private FragSearchdetail2Binding binding;

    public SearchDetailFrag2(String pidx) {
        this.pidx = pidx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //viewBinding
        binding = FragSearchdetail2Binding.inflate(inflater, container, false);
        View layout = binding.getRoot();
        binding.btnLike.setOnClickListener(this);
        binding.tvInfo.setOnClickListener(this);
        binding.tvEvaluation.setOnClickListener(this);
        binding.tvReview.setOnClickListener(this);
        binding.tvContact.setOnClickListener(this);
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

                    Glide.with(getActivity()).load(Storage.IMG_URL + pimage1)
                            .into(binding.ivBg);
                    binding.tvPname.setText(pname);
                    binding.tvComment.setText(pval);
                    binding.tvPspace.setText(evalChanger(pspace));
                    binding.tvPplug.setText(evalChanger(pplug));
                    binding.tvPtable.setText(evalChanger(ptable));
                    binding.tvWifi.setText(evalChanger4(wifi));
                    binding.tvWifibreak.setText(evalChanger3(wifi_break));
                    binding.tvPnoise.setText(evalChanger(pnoise));
                    binding.tvPmusic.setText(evalChanger(pmusic));
                    binding.tvPbright.setText(evalChanger(pbright));
                    binding.tvPlight.setText(evalChanger2(plight));
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
