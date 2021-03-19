package com.example.coditplace2;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {

    private ArrayList<ItemData> arr = new ArrayList<>();

    public MyAdapter(ArrayList<ItemData> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    //처음으로 생성될 때의 생명주기
    public MyAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }
    @Override
    //실제 추가될 때의 생명주기
    public void onBindViewHolder(@NonNull MyAdapter.CustomViewHolder viewHolder, int position) {
        viewHolder.tvPnameHolder.setText(arr.get(position).pName);
        viewHolder.tvPvisitHolder.setText(arr.get(position).pVisit);
        viewHolder.tvPcategoryHolder.setText(arr.get(position).pCategory);
        viewHolder.tvPphoneHolder.setText(arr.get(position).pPhone);
        viewHolder.tvPcontentHolder.setText(arr.get(position).pContent);
        viewHolder.tvPlikeHolder.setText(arr.get(position).pLike);

        //리스트뷰가 클릭됐을 때를 구현
        //position
        viewHolder.itemView.setTag(position);
        //클릭리스너를 이곳에 작성
        //작성시 new 안 붙이면 자동완성 x
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchDetailActivity.class);
                intent.putExtra("pidx", arr.get(position).pIdx);
                Log.d("chk", "onClick: pidx="+arr.get(position).pIdx);
                v.getContext().startActivity(intent);
            }
        });
        //longclick할 경우 북마크 추가되도록(예정)
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null !=arr ? arr.size():0); //arr가 null이 아닐 때를 적어줌.
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvPnameHolder;
        protected ImageView ivPimage1Holder;
        protected TextView tvPvisitHolder;
        protected ImageView ivPiconHolder;
        protected TextView tvPcategoryHolder;
        protected TextView tvPphoneHolder;
        protected TextView tvPcontentHolder;
        protected TextView tvPlikeHolder;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvPnameHolder=(TextView) itemView.findViewById(R.id.tv_pname);
            this.ivPimage1Holder =(ImageView) itemView.findViewById(R.id.iv_bg);
            this.tvPvisitHolder = (TextView) itemView.findViewById(R.id.tv_visit);
            this.ivPiconHolder = (ImageView) itemView.findViewById(R.id.iv_icon);
            this.tvPcategoryHolder = (TextView) itemView.findViewById(R.id.tv_category);
            this.tvPphoneHolder = (TextView) itemView.findViewById(R.id.tv_pphone);
            this.tvPcontentHolder = (TextView) itemView.findViewById(R.id.tv_pcontent);
            this.tvPlikeHolder = (TextView) itemView.findViewById(R.id.tv_like);
        }
    }
}
