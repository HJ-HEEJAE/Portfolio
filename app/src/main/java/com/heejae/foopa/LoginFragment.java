package com.heejae.foopa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.heejae.foopa.SQLite.DBHelper;

public class LoginFragment extends Fragment {
    private View view;
    private DBHelper db;
    private FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        final EditText login_id = view.findViewById(R.id.login_id);
        final EditText login_password = view.findViewById(R.id.login_password);
        Button login_button = view.findViewById(R.id.login_button);
        Button regi_button = view.findViewById(R.id.register_button);
        // Login event
        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String user_id = login_id.getText().toString();
                String user_password = login_password.getText().toString();
                if (user_id.equals("") || user_password.equals("")) {
                    Toast.makeText(getActivity(), "로그인 정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 로그인 정보 체크
                    String[] result = db.checkLogin(user_id, user_password);
                    String login_user_id = result[0];
                    // 로그인 성공
                    if (login_user_id != "") {
                        MyApplication myApp = (MyApplication) getActivity().getApplication();
                        myApp.setLoggedUser(login_user_id); // 로그인 세션 방식으로 아이디를 전역변수로 설정
                        // 전달할 변수를 번들을 만들어 담기.
                        Toast.makeText(getActivity(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                        Bundle bundle = new Bundle();
                        bundle.putString("userId", login_user_id); // key and value
                        bundle.putString("userName", result[1]); // key and value

                        UserpageFragment userpageFragment = new UserpageFragment();
                        userpageFragment.setArguments(bundle);  // fragment에 데이터(번들) 넘기기
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        transaction = fm.beginTransaction();
                        // 백스택 비우기
                        for (int i=0; i<fm.getBackStackEntryCount(); i++){
                            fm.popBackStack();
                        }
                        transaction.replace(R.id.main_frame, userpageFragment);
                        transaction.commit();
                    } else {
                        Toast.makeText(getActivity(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Register event
        regi_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}