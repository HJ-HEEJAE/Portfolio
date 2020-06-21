package com.heejae.foopa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StorelistFragment extends Fragment {
    private View view;
//    private DBHelper db;
    private FragmentTransaction transaction;
    private ArrayList<String[]> resultList = new ArrayList<String[]>();
    private RecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_storelist, container, false);

        // (Linear)LayoutManager와 Adapter를 RecyclerView에 연결
        init();

        getData();

        return view;
    }

    // 초기화 및 세팅
    private void init(){
        RecyclerView recyclerView = view.findViewById(R.id.store_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }
    // DB에서 가져온 데이터를 리스트에 넣어서 그리기
    private void getData(){
        resultList.clear(); // initialize
        Bundle bundle = getArguments();
        for (int i=0; i<bundle.size()-1; i++) {
            resultList.add(bundle.getStringArray("id_"+i));
        }
        String menu_category = bundle.getString("Menu_Category");
        TextView list_title = view.findViewById(R.id.list_title);
        list_title.setText(menu_category);

        // 리사이클러에 나타낼 데이터   // 어댑터 사용
        String store_kind = "포장/매장"; //
        String menu_kind = "전체"; //
        String store_location = ""; //
        // 각 List의 값들을 data 객체에 세팅.
        for (int i = 0; i < resultList.size(); i++) {
            // 리사이클러 어댑터
            RecyclerAdapter.Data data = new RecyclerAdapter.Data();
            data.setUserId(resultList.get(i)[1]);   // 유저아이디(=매장ID)
            data.setStoreKind(resultList.get(i)[2]);    //영업 종류(store kind)
            menu_kind = resultList.get(i)[3];   //메뉴 종류
            data.setStoreName(resultList.get(i)[4]);    //매장명
            if (resultList.get(i)[5] != null && resultList.get(i)[6] != null && resultList.get(i)[5] != "" && resultList.get(i)[6] != ""){
                store_location = resultList.get(i)[5]+","+resultList.get(i)[6];
            }
            data.setStoreContent("["+store_kind+"] " + menu_kind +"\n위치: "+ store_location);
            data.setResId(R.mipmap.ic_launcher);

            // 각 값이 들어간 data를 adapter에 추가
            adapter.addItem(data);

            // 이벤트 리스너
            adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, RecyclerAdapter.Data data) {
                    String user_id = data.getUserId();
                    String store_kind = data.getStoreKind();
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", user_id);
                    bundle.putString("store_kind", store_kind);
                    StoreDetailFragment storeDetailFragment = new StoreDetailFragment();
                    storeDetailFragment.setArguments(bundle);   // 데이터(아이디) 전달
                    transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, storeDetailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        // adapter의 값이 변경되었다는 것을 알림.
        adapter.notifyDataSetChanged();
    }

}