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
import com.example.coditplace2.databinding.FragSearchdetail4Binding;
import com.example.coditplace2.retrofit.RetroClient;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_bkinsert;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_detail1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchDetailFrag4 extends BaseFrag implements View.OnClickListener{

    private FragSearchdetail4Binding binding;

    public SearchDetailFrag4(String pidx) {
        this.pidx = pidx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragSearchdetail4Binding.inflate(inflater, container, false);
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
    private void requestR(){
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("pidx", pidx);
        RetroClient.getRetroBaseApiService().rPlace_basic(params2).enqueue(new Callback<List<ResponseGet_detail1>>() {
            @Override
            public void onResponse(Call<List<ResponseGet_detail1>> call, retrofit2.Response<List<ResponseGet_detail1>> response) {
                List<ResponseGet_detail1> result = response.body();
                for(int i = 0; i<result.size(); i++){
                    String pimage1 = result.get(i).getPimage1();
                    String pname = result.get(i).getPname();
                    String pphone = result.get(i).getPphone();

                    //대표이미지
                    Glide.with(getActivity()).load(Storage.IMG_URL+pimage1)
                            .into(binding.ivBg);
                    binding.tvPname.setText(pname);
                    binding.tvPhone.setText(pphone);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseGet_detail1>> call, Throwable t) {
                Log.d("retrofit", "onResponse: failed");
            }
        });
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
