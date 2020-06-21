package com.heejae.foopa;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

///// 차후 위치정보 설정은 우편번호 API 등을 사용하여 재편 /////
public class AddressFragment extends Fragment {
    private View view;
    MyApplication myApp;
    private FragmentTransaction ft;
    Double loc_x = 0.0;
    Double loc_y = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_address, container, false);
        myApp = (MyApplication) getActivity().getApplication();

        Button add_address_btn = view.findViewById(R.id.add_adress_btn);
        add_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation();
            }
        });

//        boolean result = setLocation();
//        if (result){
//            getActivity().onBackPressed();
//        }else{
//            Toast.makeText(getActivity(), "위치 정보를 입력해주세요. (임시 - 위경도입력)", Toast.LENGTH_SHORT).show();
//        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setLocation(){
        TextView tv_loc_x = view.findViewById(R.id.set_location_x);
        TextView tv_loc_y = view.findViewById(R.id.set_location_y);
        String locationX = tv_loc_x.getText().toString();
        String locationY = tv_loc_y.getText().toString();
//        Log.d("loc ", locationX+locationY);
        if (locationX.length() != 0 && locationY.length() != 0){
            try{
                loc_x = Double.parseDouble(locationX);
                loc_y = Double.parseDouble(locationY);
            }catch (Exception e){
                Toast.makeText(getActivity(), "올바른 데이터를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            // 전역변수에 위치정보 담기
            myApp.setlocationX(loc_x);
            myApp.setlocationY(loc_y);

            Log.d("loc", locationX+""+locationY);
//            Button address_btn = view.findViewById(R.id.set_address_Btn);
//            double loc_x = myApp.getlocationX();
//            double loc_y = myApp.getlocationY();

//            if (loc_x != 0.0 && loc_y != 0.0){
//                address_btn.setText(locationX+", "+locationY);
//            }

//            Intent intent = new Intent(getActivity(), MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);

            HomeFragment homeFragment = new HomeFragment();
            ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame, homeFragment);
            ft.commit();

//            getActivity().onBackPressed();
//            return true;
        }else{
            Toast.makeText(getActivity(), "위치 정보를 입력해주세요. (임시 - 위경도입력)", Toast.LENGTH_SHORT).show();
//            return false;
        }
    }
}