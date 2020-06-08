package com.heejae.foopa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heejae.foopa.SQLite.DBHelper;


public class StoreDetailFragment extends Fragment {
    private View view;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store_detail, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        Bundle bundle = getArguments();
        String user_id = bundle.getString("user_id");
        TextView tv = view.findViewById(R.id.detail_textview);
        tv.setText(user_id + "'s Detail will be updated!");
        return view;
    }
}