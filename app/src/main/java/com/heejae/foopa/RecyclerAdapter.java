package com.heejae.foopa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.heejae.foopa.SQLite.DBHelper;

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

    // 클릭 이벤트 준비
    public interface  OnItemClickListener{
        void onItemClick(View v, Data data);
    }
    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener listner){
        this.mListener = listner;
    }

    // 뷰홀더
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

            // 뷰홀더 생성하면서 클릭 이벤트 리스너 붙이기
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();    // 클릭 위치
                    if (position != RecyclerView.NO_POSITION){
                        Data item = storeList.get(position);
                        if (mListener != null){
                            mListener.onItemClick(v, item);
                        }
//                        Data item = storeList.get(position);    // 포지션 이용해 데이터 추출
//                        String user_id = item.getUserId();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("user_id", user_id);
                    }
                }
            });
        }

        public void onBind(Data data) {
            tv1.setText(data.getStoreName());
            tv2.setText(data.getStoreContent());
            iv.setImageResource(data.resId);
        }
    }

    // Data - 리스트의 요소를 담는 클래스
    public static class Data {
        private String userId; // unique key
        private String storeName;
        private String storeContent;
        private String storeKind;
        private int resId;

        //        Data(String storeName, String storeContent, int resId){
        //            this.storeName = storeName;
        //            this.storeContent = storeContent;
        //            getResources().getIdentifier("@drawable/", "drawable", getActivity().getPackageName());
        //            this.resId = resId;
        //        }
        // 표시하지는 않고 식별자로서 가지고 있을 매장 ID (=사업주 아이디. 1매장 1아이디로 구상)
        public void setUserId(String user_id){
            this.userId = user_id;
        }
        public String getUserId(){
            return userId;
        }
        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
        public String getStoreName() {
            return storeName;
        }
        public void setStoreContent(String storeContent) {
            this.storeContent = storeContent;
        }
        public String getStoreContent() {
            return storeContent;
        }
        public void setStoreKind(String storeKind) {
            this.storeKind = storeKind;
        }
        public String getStoreKind() {
            return storeKind;
        }

        public int getResId() {
            return resId;
        }
        public void setResId(int resId) {
            this.resId = resId;
        }
    }
}
