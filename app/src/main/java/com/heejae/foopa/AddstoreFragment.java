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

public class AddstoreFragment extends Fragment {
    private View view;
    private DBHelper db;
    String user_id;
    String store_kind;
    String menu_kind;
    String store;
    double locationX;
    double locationY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addstore, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        Bundle bundle = new Bundle();
        String user_id = bundle.getString("user_id");
        String store_kind = bundle.getString("store_kind");
//        String menu_kind = bundle.getString("menu_kind");
//        String store = bundle.getString("store");
//        String location_x = bundle.getString("locationX");
//        String location_y = bundle.getString("locationY");
//        Double locationX = Double.parseDouble(location_x);
//        Double locationY = Double.parseDouble(location_y);
        Button addStoreBtn = getActivity().findViewById(R.id.add_store_btn);
        addStoreBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView store_user_id = getActivity().findViewById(R.id.store_user_id);
                TextView add_store_kind = getActivity().findViewById(R.id.add_store_kind);
                TextView add_store_menu_kind = getActivity().findViewById(R.id.add_store_menu_kind);
                TextView add_store_name = getActivity().findViewById(R.id.add_store_name);
                TextView add_store_loc_x = getActivity().findViewById(R.id.add_store_loc_x);
                TextView add_store_loc_y = getActivity().findViewById(R.id.add_store_loc_y);
                String user_id = store_user_id.getText().toString();
                String store_kind = add_store_kind.getText().toString();
                String menu_kind = add_store_menu_kind.getText().toString();
                String store = add_store_name.getText().toString();
                double locationX = Double.parseDouble(add_store_loc_x.getText().toString());
                double locationY = Double.parseDouble(add_store_loc_y.getText().toString());
                boolean result = db.storeInsert(user_id, store_kind, menu_kind, store, locationX, locationY);
                if (result){
                    Toast.makeText(getActivity(), "매장이 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}