package com.heejae.foopa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.heejae.foopa.SQLite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View view;
    private DBHelper db;
    private FragmentTransaction transaction;
    private ArrayList<String[]> resultList = new ArrayList<String[]>();
//    private Integer[] imgArr;
//    public enum gridItem {
//        MENU_ALL, MENU_STREET, MENU_KO, MENU_WEST, MENU_CH, MENU_JP, MENU_SNACK, MENU_DRINK, MENU_ETC
//    };
//    private gridItem selectedItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        ImageButton ibMenuAll = view.findViewById(R.id.menu_all);
        ImageButton ibMenuStreet = view.findViewById(R.id.menu_street);
        ImageButton ibMenuKo = view.findViewById(R.id.menu_ko);
        ImageButton ibMenuWest = view.findViewById(R.id.menu_western);
        ImageButton ibMenuCh = view.findViewById(R.id.menu_ch);
        ImageButton ibMenuJp = view.findViewById(R.id.menu_jp);
        ImageButton ibMenuSnack = view.findViewById(R.id.menu_snack);
        ImageButton ibMenuDrink = view.findViewById(R.id.menu_drink);
        ImageButton ibMenuEtc = view.findViewById(R.id.menu_etc);

        ibMenuAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "all");
                renderList(resultList);
            }
        });
        ibMenuStreet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "스트릿");
                renderList(resultList);
            }
        });
        ibMenuKo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "한식");
                renderList(resultList);
            }
        });
        ibMenuWest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "양식");
                renderList(resultList);
            }
        });
        ibMenuCh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "중식");
                renderList(resultList);
            }
        });
        ibMenuJp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "일식");
                renderList(resultList);
            }
        });
        ibMenuSnack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "분식");
                renderList(resultList);
            }
        });
        ibMenuDrink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "드링크");
                renderList(resultList);
            }
        });
        ibMenuEtc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultList = db.getStores("all", "기타");
                renderList(resultList);
            }
        });
//        List<String> list = new ArrayList<>();
//        // ListView와 List를 연결.
////        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
//        GridView gridView = (GridView) view.findViewById(R.id.menu_grid);
//        gridView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
////        gridView.setAdapter(adapter);
//        list.add("전체");
//        list.add("스트릿");

        return view;
    }

public void renderList(ArrayList<String[]> resultList){
    // 다음 프래그먼트에 전달할 데이터
    Bundle bundle = new Bundle();
    for (int i=0; i<resultList.size(); i++){
        Log.d("wholestoreId: ", resultList.get(i)[0]);
        bundle.putStringArray("id_"+i, resultList.get(i)); // key and value
    }
    StorelistFragment storelistFragment = new StorelistFragment();
    storelistFragment.setArguments(bundle);  // fragment에 데이터(번들) 넘기기
    transaction = getActivity().getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.main_frame, storelistFragment);
    transaction.addToBackStack(null);
    transaction.commit();
}

//    public class MyAdapter extends BaseAdapter {
//        ArrayList<HomeMenuItem> items = new ArrayList<HomeMenuItem>();
//
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//        public void addItem(HomeMenuItem homeMenuItem){
//            items.add(homeMenuItem);
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return items.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
////            imgArr = new Integer[] {};
//            return null;
//        }
//    }
}


