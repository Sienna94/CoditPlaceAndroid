package com.example.coditplace2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coditplace2.dto.ItemData;
import com.example.coditplace2.R;
import com.example.coditplace2.Storage;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private static final String TAG = "MyAdapter";
    //뷰 홀더에 들어갈 arr
    public ArrayList<ItemData> arr = new ArrayList<>();
    public MyListener myListener; // 클릭리스너

    public MyAdapter(ArrayList<ItemData> arr, MyListener myListener2){
        this.arr = arr;
        this.myListener = myListener2;
    }

    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수
    // 뷰 홀더는 실제 레이아웃 파일과 매핑되어야하며, extends의 Adater<>에서 <>안에들어가는 타입을 따른다.
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view, myListener);
    }

    // 실제 각 뷰 홀더에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int position) {
        try{
            //가게 이름, 가게방문일, 가게 카테고리, 가게 연락처, 가게설명, 좋아요(사용자)
            viewHolder.tvPnameHolder.setText(arr.get(position).getpName());
            viewHolder.tvPvisitHolder.setText(arr.get(position).getpVisit());
            viewHolder.tvPcategoryHolder.setText(arr.get(position).getpCategory());
            viewHolder.tvPphoneHolder.setText(arr.get(position).getpPhone());
            viewHolder.tvPcontentHolder.setText(arr.get(position).getpContent());
            viewHolder.tvPlikeHolder.setText(arr.get(position).getpLike());

            //카페 사진
            Glide.with(viewHolder.itemView.getContext())
                    .load(Storage.IMG_URL + arr.get(position).getpImage())
                    .into(viewHolder.ivPimage1Holder);
        }catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: "+ e.getMessage());
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
    //리사이클러뷰에 들어가는 뷰홀더 및 해당 아이템 지정
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvPnameHolder;
        ImageView ivPimage1Holder;
        TextView tvPvisitHolder;
        ImageView ivPiconHolder;
        TextView tvPcategoryHolder;
        TextView tvPphoneHolder;
        TextView tvPcontentHolder;
        TextView tvPlikeHolder;

        MyListener myListener;

        public ViewHolder(@NonNull View itemView, MyListener myListener2) {
            super(itemView);
            tvPnameHolder = itemView.findViewById(R.id.tv_pname);
            ivPimage1Holder = itemView.findViewById(R.id.iv_bg);
            tvPvisitHolder = itemView.findViewById(R.id.tv_visit);
            ivPiconHolder = itemView.findViewById(R.id.iv_icon);
            tvPcategoryHolder = itemView.findViewById(R.id.tv_category);
            tvPphoneHolder = itemView.findViewById(R.id.tv_pphone);
            tvPcontentHolder = itemView.findViewById(R.id.tv_pcontent);
            tvPlikeHolder = itemView.findViewById(R.id.tv_like);

            myListener = myListener2;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " +getAdapterPosition());
            myListener.myClick(getAdapterPosition());
        }
    }

    //클릭 리스너는 인터페이스로!
    public interface MyListener{
        void myClick(int position);
    }

}
