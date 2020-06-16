package com.heejae.foopa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.heejae.foopa.SQLite.DBHelper;

public class EditstoreFragment extends Fragment {
    private View view;
    private DBHelper db;
    String menu;
    int price;
    String user_id;
    String store_kind;
    String menu_kind;
    String store;
    double locationX;
    double locationY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addstore, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        Bundle bundle = new Bundle();
        user_id = bundle.getString("user_id");
        store_kind = bundle.getString("store_kind");
        menu_kind = bundle.getString("menu_kind");
        store = bundle.getString("store");
        String location_x = bundle.getString("locationX");
        String location_y = bundle.getString("locationY");
        locationX = (double) Double.parseDouble(location_x);
        locationY = (double) Double.parseDouble(location_y);

        TextView store_user_id = getActivity().findViewById(R.id.store_user_id);
        TextView add_store_kind = getActivity().findViewById(R.id.add_store_kind);
        TextView add_store_menu_kind = getActivity().findViewById(R.id.add_store_menu_kind);
        TextView add_store_name = getActivity().findViewById(R.id.add_store_name);
        TextView add_store_loc_x = getActivity().findViewById(R.id.add_store_loc_x);
        TextView add_store_loc_y = getActivity().findViewById(R.id.add_store_loc_y);
        store_user_id.setText(user_id);
        add_store_kind.setText(store_kind);
        add_store_menu_kind.setText(menu_kind);
        add_store_name.setText(store);
//        add_store_loc_x.setText(location_x);
//        add_store_loc_y.setText(location_y);

        store_user_id.setEnabled(false);
        add_store_kind.setEnabled(false);
        add_store_menu_kind.setEnabled(false);
        add_store_name.setEnabled(false);
        add_store_loc_x.setEnabled(false);
        add_store_loc_y.setEnabled(false);

        Button editStoreBtn = getActivity().findViewById(R.id.edit_store_btn);
        editStoreBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView store_menu = getActivity().findViewById(R.id.store_menu);
                TextView store_price = getActivity().findViewById(R.id.store_price);
                menu = store_menu.getText().toString();
                String str_price = store_price.getText().toString();
                price = Integer.parseInt(str_price);
                boolean result = db.menuInsert(user_id, store_kind, menu_kind, store, menu, price);
                if (result){
                    Toast.makeText(getActivity(), "매장정보가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

//    public void editStore(String user_id, String store_kind, String menu_kind, String store, String menu, int price){
//
//    }
}