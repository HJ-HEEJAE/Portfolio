package com.heejae.foopa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heejae.foopa.SQLite.DBHelper;

import java.util.ArrayList;


public class StorelistFragment extends Fragment {
    private View view;
    private DBHelper db;
    private RecyclerAdapter adapter = new RecyclerAdapter();
    private ArrayList<String[]> resultList = new ArrayList<String[]>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_storelist, container, false);

        TextView list_title = view.findViewById(R.id.list_title);
        list_title.setText("전체보기");

        // (Linear)LayoutManager와 Adapter를 RecyclerView에 연결
        RecyclerView recyclerView = view.findViewById(R.id.store_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // DB에서 가져온 데이터를 리스트에 넣어서 그리기
        getData();
        return view;
    }

    private void getData(){
        resultList.clear(); // initialize
        Bundle bundle = getArguments();
        for (int i=0; i<bundle.size(); i++) {
            resultList.add(bundle.getStringArray("id_"+i));
        }

//        List<Integer> listResId = Arrays.asList(
//                R.drawable.chrysanthemum,
//        );
        String store_kind = "";
        String menu_kind = "";
        String store_location = "";
        for (int i = 0; i < resultList.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            RecyclerAdapter.Data data = new RecyclerAdapter.Data();
            data.setStoreName(resultList.get(i)[3]);
            if (resultList.get(i)[1]=="all") {
                store_kind = "포장/매장";
            }else {
                store_kind = resultList.get(i)[1];
            }
            if (resultList.get(i)[2]=="all"){
                menu_kind = "전체";
            }else{
                menu_kind = resultList.get(i)[2];
            }
            if (resultList.get(i)[4] != null && resultList.get(i)[5] == null){
                store_location = resultList.get(i)[4]+","+resultList.get(i)[5];
            }
            data.setStoreContent("["+store_kind+"] " + menu_kind +" / 위치: "+ store_location);
            data.setResId(R.mipmap.ic_launcher);

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }

}