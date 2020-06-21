package com.heejae.foopa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.heejae.foopa.SQLite.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class MystoreFragment extends Fragment {
    private View view;
    private DBHelper db;
    private MyApplication myApp;
    private FragmentTransaction transaction;
    private String user_id;
    private String[] storeInfo;

    // 포장, 매장 각각 등록된 매장 있는 경우 매장추가 비활성화/매장보이기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mystore, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);
        // 전역변수 사용위해 어플리케이션 클래스 가져오기
        myApp = (MyApplication) getActivity().getApplication();

        Button addTaStoreBtn = view.findViewById(R.id.add_ta_store_btn);
        Button addEhStoreBtn = view.findViewById(R.id.add_eh_store_btn);

        LinearLayout taContent = view.findViewById(R.id.ta_frame);
        LinearLayout ehContent = view.findViewById(R.id.eh_frame);
        TextView ta_title = view.findViewById(R.id.ta_title);
        TextView eh_title = view.findViewById(R.id.eh_title);
        ListView lv_ta = view.findViewById(R.id.ta_menu_list);
        ListView lv_eh = view.findViewById(R.id.eh_menu_list);

        taContent.setEnabled(false);
        ehContent.setEnabled(false);
        ta_title.setText("[포장] 매장을 등록해주세요.");
        eh_title.setText("[매장] 매장을 등록해주세요.");
//        taContent.setVisibility(View.INVISIBLE);
//        ehContent.setVisibility(View.INVISIBLE);

        // 로그인 더블 체크 & 포장 or 매장식사 등록 확인
        user_id = myApp.getLoggedUser();
        Log.d("user_id", user_id);
        if (user_id.length() == 0){
            Toast.makeText(getActivity(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            MypageFragment mypageFragment = new MypageFragment();
            transaction.replace(R.id.main_frame, mypageFragment);
            transaction.commit();
        } else {
            String[] store_kind_Arr = db.isStore(user_id);
            String takeAway = "";
            String eatHere = "";
            if (store_kind_Arr.length < 2){
                Log.d("mystore_", "error");
            }else{
                takeAway = store_kind_Arr[0];
                eatHere = store_kind_Arr[1];
            }

            // 포장
            if (takeAway.equals(getString(R.string.takeAway))){
                addTaStoreBtn.setEnabled(false);
                // 등록된 포장매장 표시
                showStoreInfo(user_id, getString(R.string.takeAway), view);
                // 편집 버튼 클릭이벤트
                Log.d("storeinfo_id ", storeInfo[3]);
                Button editTaStoreBtn = view.findViewById(R.id.edit_ta_btn);
                editTaStoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        modifyStoreDetail(user_id, getString(R.string.takeAway), storeInfo[3], storeInfo[4], storeInfo[5], storeInfo[6]);
                    }
                });
                // 리스트 크기 조절
                LinearLayout.LayoutParams taParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, lv_ta.getHeight());
                taParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lv_ta.setLayoutParams(taParams);
//                taParams.height = 1000;
//                taContent.setEnabled(true);
//                taContent.setVisibility(View.VISIBLE);
            } else {
                // 포장매장 추가 버튼활성화
                addTaStoreBtn.setEnabled(true);
                addTaStoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputStoreDetail(user_id, getString(R.string.takeAway));
                    }
                });
            }

            // 매장식사
            if (eatHere.equals(getString(R.string.eatHere))){
                addEhStoreBtn.setEnabled(false);
                // 등록된 매장식사매장 표시
                showStoreInfo(user_id, getString(R.string.eatHere), view);
                // 편집 버튼 클릭이벤트
                Button editEhStoreBtn = view.findViewById(R.id.edit_eh_btn);
                editEhStoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        modifyStoreDetail(user_id, getString(R.string.eatHere), storeInfo[3], storeInfo[4], storeInfo[5], storeInfo[6]);
                    }
                });
                // 리스트 크기 조절
                LinearLayout.LayoutParams ehParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, lv_eh.getHeight());
                ehParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lv_eh.setLayoutParams(ehParams);
//                ehParams.height = 1000;
//                ehContent.setEnabled(true);
//                ehContent.setVisibility(View.VISIBLE);
            } else {
                // 식사매장 추가 버튼활성화
                addEhStoreBtn.setEnabled(true);
                addEhStoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputStoreDetail(user_id, getString(R.string.eatHere));
                    }
                });
            }

        }

        return view;
    }

    public void showStoreInfo(String user_id, String store_kind, View view){
        db = new DBHelper(getActivity(), "foopa.db", null, 1);
        // 매장정보
        storeInfo = db.getStoreInfo(user_id, store_kind);
//        new String[] {id, user_id, store_kind, menu_kind, store, locationX, locationY};
        if (store_kind.equals(getString(R.string.takeAway))){
            TextView ta_title = view.findViewById(R.id.ta_title);
            TextView ta_address = view.findViewById(R.id.ta_address);
            ta_title.setText("["+getString(R.string.takeAway)+"] "+storeInfo[4]);
            ta_address.setText(getString(R.string.keyword_location)+": "+storeInfo[5]+", "+storeInfo[6]);
            showStoreMenu(user_id, store_kind, view); // 메뉴 불러오기
        } else if (store_kind.equals(getString(R.string.eatHere))){
            TextView eh_title = view.findViewById(R.id.eh_title);
            TextView eh_address = view.findViewById(R.id.eh_address);
            eh_title.setText("["+getString(R.string.eatHere)+"] "+storeInfo[4]);
            eh_address.setText(getString(R.string.keyword_location)+": "+storeInfo[5]+", "+storeInfo[6]);
            showStoreMenu(user_id, store_kind, view); // 메뉴 불러오기
        }
    }
    public void showStoreMenu(String user_id, String store_kind, View view){
        db = new DBHelper(getActivity(), "foopa.db", null, 1);
        // 메뉴 가져오기
//        List<String> list = new ArrayList<>();
        ArrayList<String[]> storeInfo = db.getStore(user_id, store_kind);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        for (int i=0; i<storeInfo.size(); i++){
            Log.d("e ",storeInfo.get(i)[5]+storeInfo.get(i)[6]);
            // String[] storeMenu = storeInfo.get(i);
//            list.add(storeInfo.get(i)[5]);
            // 메뉴, 리스트 추가
            HashMap<String, String> item = new HashMap<>();
            item.put("menu_name", storeInfo.get(i)[5]);
            item.put("menu_price", storeInfo.get(i)[6]+getString(R.string.currency_won));
            list.add(item);
//            Log.d("ii", list.get(i).values().toString());
        }
        // ListView adapter
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_2, new String[]{"menu_name", "menu_price"}, new int[]{android.R.id.text1, android.R.id.text2});
        if (store_kind.equals(getString(R.string.takeAway))){
            ListView menu_list = view.findViewById(R.id.ta_menu_list);
            menu_list.setAdapter(adapter);
        } else if (store_kind.equals(getString(R.string.eatHere))){
            ListView menu_list = view.findViewById(R.id.eh_menu_list);
            menu_list.setAdapter(adapter);
        }
    }

    public void inputStoreDetail(String user_id, String store_kind){
        Log.d("inputuser", user_id);
        AddstoreFragment addstoreFragment = new AddstoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user_id", user_id);
        bundle.putString("store_kind", store_kind);
        addstoreFragment.setArguments(bundle);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, addstoreFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void modifyStoreDetail(String user_id,  String store_kind, String menu_kind, String store, String locationX, String locationY){
        EditstoreFragment editstoreFragment = new EditstoreFragment();
        Log.d("modi_id ", menu_kind);
        Bundle bundle = new Bundle();
        bundle.putString("user_id", user_id);
        bundle.putString("store_kind", store_kind);
        bundle.putString("menu_kind", menu_kind);
        bundle.putString("store", store);
        bundle.putString("locationX", locationX);
        bundle.putString("locationY", locationY);
        editstoreFragment.setArguments(bundle);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, editstoreFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}