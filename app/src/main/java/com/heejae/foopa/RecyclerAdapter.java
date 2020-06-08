package com.heejae.foopa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<Data> storeList = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용해 하나의 아이템의 틀을 짜는 storeitem을 inflate.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storeitem, parent, false);
        return new ItemViewHolder(view); // store의 요소 홀딩
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(storeList.get(position)); // 각 리스팅 아이템 바인딩
    }

    @Override
    public int getItemCount() {
        return storeList.size(); // 리스팅 된 store의 개수
    }

    // 아이템 추가 메소드
    void addItem(Data data){
        storeList.add(data);
    }

    // ItemViewHolder - 리스트의 요소(텍스트, 이미지)를 설정하고 홀딩하기 위한 클래스, 메소드.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1;
        private TextView tv2;
        private ImageView iv;

        // constructor
        ItemViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.store_name);
            tv2 = itemView.findViewById(R.id.store_content);
            iv = itemView.findViewById(R.id.store_image);
        }

        public void onBind(Data data) {
            tv1.setText(data.getStoreName());
            tv2.setText(data.getStoreContent());
            iv.setImageResource(data.resId);
        }
    }

    // Data - 리스트의 요소를 담는 클래스
    public static class Data {
        private String storeName;
        private String storeContent;
        private int resId;

        //        Data(String storeName, String storeContent, int resId){
        //            this.storeName = storeName;
        //            this.storeContent = storeContent;
        //            getResources().getIdentifier("@drawable/", "drawable", getActivity().getPackageName());
        //            this.resId = resId;
        //        }
        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public void setStoreContent(String storeContent) {
            this.storeContent = storeContent;
        }

        public String getStoreName() {
            return storeName;
        }

        public String getStoreContent() {
            return storeContent;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }
    }
}
