package com.heejae.foopa;

import android.os.Bundle;
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

        TextView store_user_id = view.findViewById(R.id.store_user_id);
        TextView add_store_kind = view.findViewById(R.id.add_store_kind);
        Bundle bundle = getArguments();
        user_id = bundle.getString("user_id");
        store_kind = bundle.getString("store_kind");
        store_user_id.setText(user_id);
        add_store_kind.setText(store_kind);
        store_user_id.setEnabled(false);
        add_store_kind.setEnabled(false);

        Spinner menu_kind_spinner = (Spinner) view.findViewById(R.id.add_store_menu_kind);
        final ArrayAdapter menu_kind_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.menu_kind_arr, android.R.layout.simple_spinner_item);
        menu_kind_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu_kind_spinner.setAdapter(menu_kind_adapter);
        menu_kind = menu_kind_spinner.getSelectedItem().toString();
//        menu_kind_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object selected_kind = menu_kind_adapter.getItem(position);
//                store_kind = selected_kind.toString();
//                Log.d("store_kind", store_kind);
//            }
//        });

//        String menu_kind = bundle.getString("menu_kind");
//        String store = bundle.getString("store");
//        String location_x = bundle.getString("locationX");
//        String location_y = bundle.getString("locationY");
//        Double locationX = Double.parseDouble(location_x);
//        Double locationY = Double.parseDouble(location_y);
        Button addStoreBtn = view.findViewById(R.id.add_store_btn);

        addStoreBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                TextView add_store_menu_kind = view.findViewById(R.id.add_store_menu_kind);
                Spinner menu_kind_spinner = v.findViewById(R.id.add_store_menu_kind);
                TextView add_store_name = view.findViewById(R.id.add_store_name);
                TextView add_store_loc_x = view.findViewById(R.id.add_store_loc_x);
                TextView add_store_loc_y = view.findViewById(R.id.add_store_loc_y);
//                String user_id = store_user_id.getText().toString();
//                String store_kind = add_store_kind.getText().toString();
//                String menu_kind = menu_kind_spinner.getText().toString();
                String store = add_store_name.getText().toString();
                double locationX = Double.parseDouble(add_store_loc_x.getText().toString());
                double locationY = Double.parseDouble(add_store_loc_y.getText().toString());
                boolean result = db.storeInsert(user_id, store_kind, menu_kind, store, locationX, locationY);
                if (result){
                    Toast.makeText(getActivity(), "매장이 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                    // 뒤로가기
                    getActivity().onBackPressed();
                }
            }
        });

        return view;
    }
}