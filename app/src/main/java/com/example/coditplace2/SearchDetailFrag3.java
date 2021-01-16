package com.example.coditplace2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchDetailFrag3 extends BaseFrag implements View.OnClickListener{
    ArrayList<ReplyData> arr = new ArrayList<>();
    MyAdapter adapter;

    Spinner spinner_score;

    //상단 basic
    ImageView iv_bg;
    ImageView iv_icon;
    TextView tv_pname;
    Button btn_like;
    TextView tv_info;
    TextView tv_eval;
    TextView tv_review;
    TextView tv_contact;
    //리스트뷰
    ListView lv;
    //댓글 추가창
    TextView tv_rtit;
    TextView tv_rscore;
    EditText et_reply;
    Button btn_submit;

    public SearchDetailFrag3(String pidx) {
        this.pidx = pidx;
    }

    int ridx;
    String mid;
    int star;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_searchdetail3, container, false);

        //상단 basic
        iv_bg=layout.findViewById(R.id.iv_bg);
        iv_icon=layout.findViewById(R.id.iv_icon);
        tv_pname=layout.findViewById(R.id.tv_pname);
        btn_like=layout.findViewById(R.id.btn_like);
        tv_info=layout.findViewById(R.id.tv_info);//매장정보
        tv_eval=layout.findViewById(R.id.tv_evaluation);//코더의 평가
        tv_review=layout.findViewById(R.id.tv_review);//리뷰(댓글)
        tv_contact=layout.findViewById(R.id.tv_contact);//연락처

        //리스트뷰
        lv =layout.findViewById(R.id.lv);
        //댓글 추가창
        tv_rtit=layout.findViewById(R.id.tv_tit);
        //별점 스피너
        spinner_score = layout.findViewById(R.id.spinner_score);
        tv_rscore=layout.findViewById(R.id.tv_score);

        et_reply=layout.findViewById(R.id.et_coderfriendly);
        btn_submit=layout.findViewById(R.id.btn_submit);

        btn_like.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_eval.setOnClickListener(this);
        tv_review.setOnClickListener(this);
        tv_contact.setOnClickListener(this);

        //스피너.set
        spinner_score.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    tv_rscore.setText("☆☆☆☆☆");
                    star=0;
//                    insertReply(0);
                }else if(position ==1){
                    tv_rscore.setText("★☆☆☆☆");
                    star=1;
//                    insertReply(1);
                }else if(position ==2){
                    tv_rscore.setText("★★☆☆☆");
                    star=2;
//                    insertReply(2);
                }else if(position ==3){
                    tv_rscore.setText("★★★☆☆");
                    star=3;
//                    insertReply(3);
                }else if(position ==4){
                    tv_rscore.setText("★★★★☆");
                    star=4;
//                    insertReply(4);
                }else if(position ==5){
                    tv_rscore.setText("★★★★★");
                    star=5;
//                    insertReply(5);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        test();
        requestForData();

        return layout;
    }
    //해당 pidx 받아오기ㄷ
    String pidx;

    private void test(){
        arr.add(0, new ReplyData(1,"사쿠맘", 4, "2020.12.8", "괜찮아욤. 콘센트 널널함"));
        arr.add(1, new ReplyData(2,"소다짱", 5, "2020.12.8", "조용하고 짱임"));
        arr.add(2, new ReplyData(3,"모카모카해", 5, "2020.12.8", "집중 짱 잘됨"));
        arr.add(3, new ReplyData(4,"나향단쓰", 3, "2020.12.8", "제가 까칠해서 그런가 소음 좀 있어염"));
        adapter = new MyAdapter(getActivity());
        lv.setAdapter(adapter);
    }
    public String scoreChanger(int score){
        String score_changed ="";
        if(score==0){
            score_changed = "☆☆☆☆☆";
        }else if(score==1){
            score_changed = "★☆☆☆☆";
        }else if(score==2){
            score_changed = "★★☆☆☆";
        }else if(score==3){
            score_changed = "★★★☆☆";
        }else if(score==4){
            score_changed = "★★★★☆";
        }else if(score==5){
            score_changed = "★★★★★";
        }
        return score_changed;
    }
    //스피너 테스트

    //댓글 리스트 전체 출력
    private void requestForData(){
        Response.Listener<String> successListener = new Response.Listener<String>() {
            //가져온 jsonArray 리스트뷰로 나타내기
            @Override
            public void onResponse(String response) {
                Log.d("reply", "onResponse: response" + response);
                try {
                    JSONArray proArr = new JSONArray(response);
                    Log.d("proArr", "onResponse:" + response);
                    arr.clear(); //기존에 있는 걸 날려야 중복되는 게 더해지지 않음.
                    for (int i = 0; i < proArr.length(); i++) { //10보다 작은데 <10 해놓으니까 오류나지 멍청이 똥멍청이야!!!
                        JSONObject proObj = proArr.getJSONObject(i);
                        //댓글 리스트
                        int ridx =Integer.parseInt(proObj.getString("ridx"));
                        String rwriter = proObj.getString("rwriter");
                        int rscore = Integer.parseInt(proObj.getString("rscore"));
                        String rdate = proObj.getString("rdate");
                        String rcontent = proObj.getString("rcontent");
                        //장소 기본
                        String pimage1 = proObj.getString("pimage1");
                        String pname = proObj.getString("pname");
                        String picon = proObj.getString("picon");

                        //response에 맞게 화면 변화시켜주기
                        //대표이미지
                        Glide.with(getActivity()).load("http://172.20.10.4:8180/oop/img/place/"+pimage1)
                                .into(iv_bg);
                        tv_pname.setText(pname);

                        //리스트에 보여줄 어레이에 추가
                        arr.add(i, new ReplyData(ridx,rwriter,rscore,rdate,rcontent));
                        Log.d("reply", arr.get(i).rwriter);
                        Log.d("reply", arr.get(i).rcontent);
                    }
                    //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.d("chk", "장소 상세 리뷰");
        params.clear();
        params.put("pidx", pidx);
        request("getReplyList.do", successListener);
        adapter = new MyAdapter(getActivity());
        lv.setAdapter(adapter);
    }
    //댓글 삭제
    private void deleteReply(int ridx){
        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kkk", "댓글 삭제 성공" + response);
                arr.remove(position);
                adapter.notifyDataSetChanged();
            }
        };
        //삭제 tv 클릭시 댓글 삭제하기 delete
        Log.d("bbb", "onClick : 댓글 삭제 try");
        String num =  Integer.toString(ridx);    //스트링으로 파라미터에 넣어줘야 함
        params.clear();
        params.put("ridx", num);
        request("ReplyDelete.do", successListener);
    }
    //댓글 입력하기
    private void insertReply(){
        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kkk", "댓글등록 성공" + response);
                arr.clear();

                try {
                    JSONArray proArr = new JSONArray(response);

                    for (int i = 0; i < proArr.length(); i++) {
                        JSONObject proObj = proArr.getJSONObject(i);
                        //댓글 리스트
                        int ridx =Integer.parseInt(proObj.getString("ridx"));
                        String rwriter = proObj.getString("rwriter");
                        int rscore = Integer.parseInt(proObj.getString("rscore"));
                        String rdate = proObj.getString("rdate");
                        String rcontent = proObj.getString("rcontent");
                        //장소 기본
                        String pimage1 = proObj.getString("pimage1");
                        String pname = proObj.getString("pname");
                        String picon = proObj.getString("picon");

                        //response에 맞게 화면 변화시켜주기
                        //대표이미지
                        Glide.with(getActivity()).load("http://192.168.7.31:8180/oop/img/place/"+pimage1)
                                .into(iv_bg);
                        tv_pname.setText(pname);

                        //리스트에 보여줄 어레이에 추가
                        arr.add(i, new ReplyData(ridx,rwriter,rscore,rdate,rcontent));
                        Log.d("reply", arr.get(i).rwriter);
                        Log.d("reply", arr.get(i).rcontent);

                    }
                    //데이터가 바꼈으니까 여기서 arr 변화를 notifychange해준다!
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //입력 버튼 클릭시 댓글 등록하기create
        Log.d("chk", "댓글 등록 통신 start");
        final String rcontent = et_reply.getText().toString().trim();
        final String rscore = tv_rscore.getText().toString().trim();
        Log.d("rcontent", "insertReply: " + rcontent);
        params.clear();
        params.put("pidx", pidx);
        params.put("rwriter", "tester");
        params.put("rscore", String.valueOf(star));
        params.put("rcontent", rcontent);
        request("ReplyInsert.do", successListener);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_like){//좋아요 버튼

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
            insertReply();
        }
    }
    //리스트에 출력될 아이템들 (삭제 tv 포함)
    class ItemHolder{
        TextView tvScoreHolder;
        TextView tvWriterHolder;
        TextView tvReplyHolder;
        TextView tvDateHolder;
        TextView tvDelHolder;
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
                viewHolder.tvScoreHolder = convertView.findViewById(R.id.tv_score);
                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_writer);
                viewHolder.tvReplyHolder = convertView.findViewById(R.id.tv_comment);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_date);
                //삭제 텍스트
                viewHolder.tvDelHolder=convertView.findViewById(R.id.tv_del);
                //삭제 position 받아오게
                viewHolder.tvDelHolder.setTag(position);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder) convertView.getTag();
            }

            viewHolder.tvScoreHolder.setText(scoreChanger(arr.get(position).rscore));
            viewHolder.tvWriterHolder.setText(arr.get(position).rwriter);
            viewHolder.tvReplyHolder.setText(arr.get(position).rcontent);
            viewHolder.tvDateHolder.setText(arr.get(position).rdate);//수정도 textview랑 같은 내용

            //삭제 클릭
            viewHolder.tvDelHolder.setOnClickListener(new View.OnClickListener(){
                @Override //작성글만 지울 수 있도록
                public void onClick(View v) {
                    Log.d("tv_del", "ridx:"+ridx);
//                    if(arr.get(position).rwriter.equals(mid)){
                        SearchDetailFrag3.this.position = position;
                        int ridx = arr.get(position).ridx;
                        Log.d("btndel", "btndel, ridx:" + ridx);
                        deleteReply(ridx);
//                    }else{
//                        Toast.makeText(getActivity(), "자신이 작성한 글만 지울 수 있습니다 :(", Toast.LENGTH_SHORT).show();
//                    }
                }
            });

            return convertView;
        }
    }
}
