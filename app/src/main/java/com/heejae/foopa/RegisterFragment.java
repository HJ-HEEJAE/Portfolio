package com.heejae.foopa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.heejae.foopa.SQLite.DBHelper;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link RegisterFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class RegisterFragment extends Fragment {
    private View view;
    private DBHelper db;
    private FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        db = new DBHelper(getActivity(), "foopa.db", null, 1);

        final EditText regi_id = view.findViewById(R.id.regi_id);
        final EditText regi_password = view.findViewById(R.id.regi_password);
        final EditText regi_name = view.findViewById(R.id.regi_name);
        Button regi_button = view.findViewById(R.id.register_button);
        regi_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user_id = regi_id.getText().toString();
                String user_password = regi_password.getText().toString();
                String user_name = regi_name.getText().toString();
                if (user_id.equals("") || user_password.equals("") || user_name.equals("")){
                    Toast.makeText(getActivity(), "회원가입 정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    // 아이디 중복체크
                    if(db.checkId(user_id)){
                        Boolean insert = db.userInsert(user_id, user_password, user_name);
                        if(insert){
                            Toast.makeText(getActivity(), "가입을 환영합니다.", Toast.LENGTH_SHORT).show();
                            transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            MypageFragment mypageFragment = new MypageFragment();
                            transaction.replace(R.id.main_frame, mypageFragment);
                            transaction.commit();
                        }else{
                            Toast.makeText(getActivity(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "아이디가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

}