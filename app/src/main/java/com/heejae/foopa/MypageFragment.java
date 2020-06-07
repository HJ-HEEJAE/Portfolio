package com.heejae.foopa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MypageFragment extends Fragment {
    private View view;
<<<<<<< HEAD
    private FragmentManager fm;
    private FragmentTransaction transaction;
=======
    static final String[] LIST_MYPAGE = {"로그인", "나의 매장 관리", "회원가입"};
>>>>>>> parent of 39ac0e2... 로그인, 회원가입 화면전환.

    // Fragment 사용에 필수로 정의되어야하는 메소드. xml에 정의되어 있는 view를 객체로서 사용할 수 있도록 확장해줌.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage, container, false);

<<<<<<< HEAD
        // List
        List<String> list = new ArrayList<>();
        // ListView와 List를 연결.
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);    // getActivity : Activity의 참조를 획득할 수 있는 함수 (Activity의 Context)
=======
        ArrayAdapter adapter = new ArrayAdapter((getActivity()), android.R.layout.simple_list_item_1, LIST_MYPAGE);
>>>>>>> parent of 39ac0e2... 로그인, 회원가입 화면전환.
        ListView listView = (ListView) view.findViewById(R.id.mypage_list);
        listView.setAdapter(adapter);
//        CustomAdapter adapter = new ArrayAdapter((getActivity()), android.R.layout.simple_list_item_1, LIST_MYPAGE);
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mypage_list);
//        recyclerView.setAdapter(adapter);

<<<<<<< HEAD
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
=======
>>>>>>> parent of 39ac0e2... 로그인, 회원가입 화면전환.
        return view;
    }

}
