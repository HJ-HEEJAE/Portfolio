package com.heejae.foopa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MypageFragment extends Fragment {
    private View view;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    // Fragment 사용에 필수로 정의되어야하는 메소드. xml에 정의되어 있는 view를 객체로서 사용할 수 있도록 확장해줌.
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        // List
        List<String> list = new ArrayList<>();
        // ListView와 List를 연결.
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);    // getActivity : Activity의 참조를 획득할 수 있는 함수 (Activity의 Context)
        ListView listView = (ListView) view.findViewById(R.id.mypage_list);
        listView.setAdapter(adapter);
//        CustomAdapter adapter = new ArrayAdapter((getActivity()), android.R.layout.simple_list_item_1, LIST_MYPAGE);
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mypage_list);
//        recyclerView.setAdapter(adapter);

        list.add("로그인");
        list.add("회원가입");

        // Click event listener on List(ListView)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = (String)parent.getItemAtPosition(position);
//                view = inflater.inflate(R.layout.fragment_login, container);
                if (selected_item.equals("로그인")){
                    loginView();
                }
                if (selected_item.equals("회원가입")){
                    registerView();
                }
            }
        });
        return view;
    }

    public void loginView() {
//        MypageFragment mypageFragment = (MypageFragment) getChildFragmentManager().findFragmentById(R.id.login_frame);
//
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        switch (n) {
//            case 0:
//                ft.replace(R.id.main_frame, homeFragment);
//                ft.commit();
        // Fragment transition object (프래그먼트 전환)
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fm = getActivity().getSupportFragmentManager();
        transaction = fm.beginTransaction();
        LoginFragment loginFragment = new LoginFragment(); // 전환할 프래그먼트
        transaction.replace(R.id.main_frame, loginFragment); // mypage_frame -> loginfragment 교체
        transaction.addToBackStack(null); // 뒤로가기를 위해 트랜잭션을 백스택에 추가
        transaction.commit(); // 프래그먼트 상태 (교체된) 저장
    }

    public void registerView() {
        fm = getActivity().getSupportFragmentManager();
        transaction = fm.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment(); // 전환할 프래그먼트
        transaction.replace(R.id.main_frame, registerFragment); // mypage_frame -> loginfragment 교체
        transaction.addToBackStack(null); // 뒤로가기를 위해 트랜잭션을 백스택에 추가
        transaction.commit(); // 프래그먼트 상태 (교체된) 저장
    }
}