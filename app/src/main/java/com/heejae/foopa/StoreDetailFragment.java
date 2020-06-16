package com.heejae.foopa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.heejae.foopa.SQLite.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class StoreDetailFragment extends Fragment {
    private View view;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store_detail, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        TextView tv_title = view.findViewById(R.id.detail_textview);
        ListView menu_list = view.findViewById(R.id.detail_menu_list);
        ImageView banner_img = view.findViewById(R.id.detail_banner_img);

        Bundle bundle = getArguments();
        String user_id = bundle.getString("user_id");
        String store_kind = bundle.getString("store_kind");

        ArrayList<String[]> storeInfo = db.getStore(user_id, store_kind);
        tv_title.setText(storeInfo.get(0)[4]);  // 매장 이름
        banner_img.setImageResource(R.drawable.food_restaurant);

        // 메뉴 리스트
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> item = new HashMap<String, String>();
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), list, android.R.layout.simple_list_item_2, new String[]{"menu_name", "menu_price"}, new int[]{android.R.id.text1, android.R.id.text2});
        for (int i=0; i<storeInfo.size(); i++){
            // 메뉴, 리스트 추가
            item.put("menu_name", storeInfo.get(i)[5]);
            item.put("menu_price", storeInfo.get(i)[6]+getString(R.string.currency_won));
            list.add(item);
        }
        menu_list.setAdapter(adapter);

        return view;
    }
}