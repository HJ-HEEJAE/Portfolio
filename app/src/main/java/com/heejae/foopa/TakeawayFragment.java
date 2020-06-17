package com.heejae.foopa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.heejae.foopa.SQLite.DBHelper;

import java.util.ArrayList;

import static com.heejae.foopa.MainActivity.keyword_All;
import static com.heejae.foopa.MainActivity.keyword_takeaway;

public class TakeawayFragment extends Fragment {
    private View view;
    private DBHelper db;
    private FragmentTransaction transaction;
    private ArrayList<String[]> resultList = new ArrayList<String[]>();
//    public static String keyword_takeaway = "포장";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_takeaway, container, false);
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

        ibMenuAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, keyword_All);
                renderList(resultList, "전체보기");
            }
        });
        ibMenuStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "스트릿");
                renderList(resultList, "스트릿");
            }
        });
        ibMenuKo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "한식");
                renderList(resultList, "한식");
            }
        });
        ibMenuWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "양식");
                renderList(resultList, "양식");
            }
        });
        ibMenuCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "중식");
                renderList(resultList, "중식");
            }
        });
        ibMenuJp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "일식");
                renderList(resultList, "일식");
            }
        });
        ibMenuSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "분식");
                renderList(resultList, "분식");
            }
        });
        ibMenuDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "드링크");
                renderList(resultList, "드링크");
            }
        });
        ibMenuEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList = db.getStores(keyword_takeaway, "기타");
                renderList(resultList, "기타");
            }
        });

        return view;
    }

    public void renderList(ArrayList<String[]> resultList, String menu_category) {
        // 다음 프래그먼트에 전달할 데이터
        Bundle bundle = new Bundle();
        for (int i = 0; i < resultList.size(); i++) {
            Log.d("wholestoreId: ", resultList.get(i)[0]);
            bundle.putStringArray("id_" + i, resultList.get(i)); // key and value
        }
        bundle.putString("Menu_Category", menu_category);

        StorelistFragment storelistFragment = new StorelistFragment();
        storelistFragment.setArguments(bundle);  // fragment에 데이터(번들) 넘기기
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, storelistFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
