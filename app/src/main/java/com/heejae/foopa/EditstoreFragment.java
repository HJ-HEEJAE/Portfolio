package com.heejae.foopa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.heejae.foopa.SQLite.DBHelper;

public class EditstoreFragment extends Fragment {
    private View view;
    private DBHelper db;
    String menu;
    String str_price;
    int price;
    String user_id;
    String store_kind;
    String menu_kind;
    String store;
    double locationX;
    double locationY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_editstore, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        Bundle bundle = getArguments();
        user_id = bundle.getString("user_id");
        store_kind = bundle.getString("store_kind");
        menu_kind = bundle.getString("menu_kind");
        store = bundle.getString("store");
        String location_x = bundle.getString("locationX");
        String location_y = bundle.getString("locationY");
        locationX = Double.parseDouble(location_x);
        locationY = Double.parseDouble(location_y);

        Spinner menu_kind_spinner = (Spinner) view.findViewById(R.id.edit_store_menu_kind);
        final ArrayAdapter menu_kind_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.menu_kind_arr, android.R.layout.simple_spinner_item);
        menu_kind_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu_kind_spinner.setAdapter(menu_kind_adapter);
        menu_kind = menu_kind_spinner.getSelectedItem().toString();

        TextView store_user_id = view.findViewById(R.id.store_edit_user_id);
        TextView edit_store_kind = view.findViewById(R.id.edit_store_kind);
        Spinner edit_store_menu_kind = view.findViewById(R.id.edit_store_menu_kind);
        TextView edit_store_name = view.findViewById(R.id.edit_store_name);
        TextView edit_store_loc_x = view.findViewById(R.id.edit_store_loc_x);
        TextView edit_store_loc_y = view.findViewById(R.id.edit_store_loc_y);
        store_user_id.setText(user_id);
        edit_store_kind.setText(store_kind);
        edit_store_name.setText(store);
        edit_store_loc_x.setText(location_x);
        edit_store_loc_y.setText(location_y);

        store_user_id.setEnabled(false);
        edit_store_kind.setEnabled(false);
        edit_store_menu_kind.setEnabled(false);
        edit_store_name.setEnabled(false);
        edit_store_loc_x.setEnabled(false);
        edit_store_loc_y.setEnabled(false);

        Button editStoreBtn = view.findViewById(R.id.edit_store_btn);
        editStoreBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView store_menu = view.findViewById(R.id.store_menu);
                TextView store_price = view.findViewById(R.id.store_price);
                menu = store_menu.getText().toString();
                str_price = store_price.getText().toString();
                if (menu.length() != 0 && str_price.length() != 0){
                    try{
                        price = Integer.parseInt(str_price);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "올바른 데이터를 입력하십시오 : 가격", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean result = db.menuInsert(user_id, store_kind, menu_kind, store, menu, price);
                    if (result) {
                        Toast.makeText(getActivity(), "메뉴가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "메뉴 업데이트에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                    getActivity().onBackPressed();
                }else{
                    Toast.makeText(getActivity(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

//    public void editStore(String user_id, String store_kind, String menu_kind, String store, String menu, int price){
//
//    }
}