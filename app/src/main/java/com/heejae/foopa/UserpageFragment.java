package com.heejae.foopa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserpageFragment extends Fragment {
private View view;
private FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_userpage, container, false);

        // 로그인 성공한 유저의 정보 표시
        TextView tv_userinfo = (TextView)view.findViewById(R.id.userinfo); // textview 객체 가져오기
        Bundle bundle = getArguments(); // 번들(데이터 - 유저 정보) 받기
        if (bundle != null){
            String userID = bundle.getString("userId");
            String userName = bundle.getString("userName");
            tv_userinfo.setText("안녕하세요! "+userName +"("+userID+")님");
        }

        // 로그인한 유저의 기능
        List<String> list = new ArrayList<>();
        // ListView와 List를 연결.
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);    // getActivity : Activity의 참조를 획득할 수 있는 함수 (Activity의 Context)
        ListView listView = (ListView) view.findViewById(R.id.userpage_list);
        listView.setAdapter(adapter);
//        CustomAdapter adapter = new ArrayAdapter((getActivity()), android.R.layout.simple_list_item_1, LIST_MYPAGE);
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mypage_list);
//        recyclerView.setAdapter(adapter);

        list.add("나의 매장관리");
        list.add("로그아웃");

        // Click event listener on List(ListView)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String selected_item = (String)parent.getItemAtPosition(position);
//                view = inflater.inflate(R.layout.fragment_login, container);
            if (selected_item.equals("나의 매장관리")){
//                    loginView();
            }
            if (selected_item.equals("로그아웃")){
                Toast.makeText(getActivity(), "로그아웃하였습니다.", Toast.LENGTH_SHORT).show();
                MypageFragment mypageFragment = new MypageFragment();
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, mypageFragment);
                transaction.commit();
            }
            }
        });
        return view;
    }
}