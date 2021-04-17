package com.example.coditplace2.place_detail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.coditplace2.BaseFrag;
import com.example.coditplace2.R;
import com.example.coditplace2.Storage;
import com.example.coditplace2.databinding.FragSearchdetail3Binding;
import com.example.coditplace2.dto.ReplyData;
import com.example.coditplace2.retrofit.RetroClient;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_Review;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_bkinsert;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_replyDel;
import com.example.coditplace2.retrofit.responseBody.ResponseGet_replyList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchDetailFrag3 extends BaseFrag implements View.OnClickListener{
    ArrayList<ReplyData> arr = new ArrayList<>();
    MyAdapter adapter;

    private FragSearchdetail3Binding binding;

    public SearchDetailFrag3(String pidx) {
        this.pidx = pidx;
    }

    int ridx;
    String mid;
    int star;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragSearchdetail3Binding.inflate(inflater, container, false);
        View layout = binding.getRoot();
        binding.ratingbar.setOnRatingBarChangeListener(new ratingListener());
        binding.btnLike.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding.tvInfo.setOnClickListener(this);
        binding.tvEvaluation.setOnClickListener(this);
        binding.tvReview.setOnClickListener(this);
        binding.tvContact.setOnClickListener(this);

        requestReply_list();

        return layout;
    }

    //RatingBar
    class ratingListener implements RatingBar.OnRatingBarChangeListener{

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            star=Integer.parseInt(String.valueOf(Math.round(rating)));

        }
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
    private void requestReply_list(){
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("pidx", pidx);
        RetroClient.getRetroBaseApiService().rReply_list(params2).enqueue(new Callback<List<ResponseGet_replyList>>() {
            @Override
            public void onResponse(Call<List<ResponseGet_replyList>> call, retrofit2.Response<List<ResponseGet_replyList>> response) {
                List<ResponseGet_replyList> result = response.body();
                arr.clear();
                for (int i = 0; i < result.size(); i++) {
                    int ridx =result.get(i).getRidx();
                    String rwriter = result.get(i).getRwriter();
                    int rscore = result.get(i).getRscore();
                    String rdate = result.get(i).getRdate();
                    String rcontent = result.get(i).getRcontent();
                    String pimage1 = result.get(i).getPimage1();
                    String pname = result.get(i).getPname();
                    String picon = result.get(i).getPicon();

                    Glide.with(getActivity()).load(Storage.IMG_URL+pimage1)
                            .into(binding.ivBg);
                    binding.tvPname.setText(pname);
                    arr.add(i, new ReplyData(ridx,rwriter,rscore,rdate,rcontent));
                }
                //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<ResponseGet_replyList>> call, Throwable t) {
                Log.d("retrofit", "onResponse: reply list failed");
            }
        });
        adapter = new MyAdapter(getActivity());
        binding.lv.setAdapter(adapter);
    }
    private void rDelReply(int ridx){
        String num =  Integer.toString(ridx);    //스트링으로 파라미터에 넣어줘야 함
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("ridx", num);
        RetroClient.getRetroBaseApiService().rReply_del(params2).enqueue(new Callback<List<ResponseGet_replyDel>>() {
            @Override
            public void onResponse(Call<List<ResponseGet_replyDel>> call, retrofit2.Response<List<ResponseGet_replyDel>> response) {
                Log.d("kkk", "댓글 삭제 성공" + response);
                arr.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResponseGet_replyDel>> call, Throwable t) {
                Log.d("retrofit", "onResponse: reply delete failed");
            }
        });
    }
    private void rInsertReply(){
        final String rcontent = binding.etCoderfriendly.getText().toString().trim();
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("pidx", pidx);
        params2.put("rwriter", Storage.USER);
        params2.put("rscore", String.valueOf(star));
        params2.put("rcontent", rcontent);
        RetroClient.getRetroBaseApiService().rReply_list(params2).enqueue(new Callback<List<ResponseGet_replyList>>() {
            @Override
            public void onResponse(Call<List<ResponseGet_replyList>> call, retrofit2.Response<List<ResponseGet_replyList>> response) {
                List<ResponseGet_replyList> result = response.body();
                arr.clear();
                for (int i = 0; i < result.size(); i++) {
                    int ridx =result.get(i).getRidx();
                    String rwriter = result.get(i).getRwriter();
                    int rscore = result.get(i).getRscore();
                    String rdate = result.get(i).getRdate();
                    String rcontent = result.get(i).getRcontent();
                    String pimage1 = result.get(i).getPimage1();
                    String pname = result.get(i).getPname();
                    String picon = result.get(i).getPicon();

                    Glide.with(getActivity()).load(Storage.IMG_URL+pimage1)
                            .into(binding.ivBg);
                    binding.tvPname.setText(pname);
                    arr.add(i, new ReplyData(ridx,rwriter,rscore,rdate,rcontent));
                }
                //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResponseGet_replyList>> call, Throwable t) {
                Log.d("retrofit", "onResponse: reply insert failed");
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

        }else if(v.getId()==R.id.btn_submit){
            Log.d("chk", "onClick: 댓글달기 버튼 클릭됨");
            rInsertReply();
        }
    }
    //리스트에 출력될 아이템들 (삭제 tv 포함)
    class ItemHolder{
        TextView tvWriterHolder;
        TextView tvReplyHolder;
        TextView tvDateHolder;
        TextView tvDelHolder;
        RatingBar ratingHolder;
    }

    int position; // 액티비티 안의 position을 말한다! myadapter안의 position이 아니라 액티비티 포지션을 찾아서 지워줘야되니까.

    class MyAdapter extends ArrayAdapter {

        LayoutInflater lnf;
        public MyAdapter(Activity context) {
            super(context, R.layout.items_reply, arr);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ItemHolder viewHolder;
            if(convertView == null){
                convertView = lnf.inflate(R.layout.items_reply, parent, false);
                viewHolder = new ItemHolder();
                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_writer);
                viewHolder.tvReplyHolder = convertView.findViewById(R.id.tv_comment);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_date);
                viewHolder.tvDelHolder=convertView.findViewById(R.id.tv_del);
                viewHolder.ratingHolder=convertView.findViewById(R.id.ratingbar_reply);
                viewHolder.tvDelHolder.setTag(position);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }
            viewHolder.ratingHolder.setRating(arr.get(position).rscore);
            viewHolder.tvWriterHolder.setText(arr.get(position).rwriter);
            viewHolder.tvReplyHolder.setText(arr.get(position).rcontent);
            viewHolder.tvDateHolder.setText(arr.get(position).rdate);//수정도 textview랑 같은 내

            //삭제 클릭
            viewHolder.tvDelHolder.setOnClickListener(new View.OnClickListener(){
                @Override //작성 댓글만 지울 수 있도록
                public void onClick(View v) {
                    Log.d("tv_del", "ridx:"+ridx);
                    if(arr.get(position).rwriter.equals(Storage.USER)){
                        SearchDetailFrag3.this.position = position;
                        int ridx = arr.get(position).ridx;
                        Log.d("btndel", "btndel, ridx:" + ridx);
                        rDelReply(ridx);
                    }else{
                        Toast.makeText(getActivity(), "자신이 작성한 글만 지울 수 있습니다 :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return convertView;
        }
    }
}