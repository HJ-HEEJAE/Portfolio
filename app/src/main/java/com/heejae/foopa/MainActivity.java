package com.heejae.foopa;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private TakeawayFragment takeawayFragment;
    private EathereFragment eathereFragment;
    private MypageFragment mypageFragment;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 네비게이션
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // 바텀 네비게이션 선택에 따라 보여주도록 하는 메소드
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    // menu - bottom_nav_menu 에서 정의한 id로 식별
                    case R.id.navigation_home:
                        setFragment(0); //frame transaction 실행
                        break;
                    case R.id.navigation_takeaway:
                        setFragment(1);
                        break;
                    case R.id.navigation_eathere:
                        setFragment(2);
                        break;
                    case R.id.navigation_mypage:
                        setFragment(3);
                        break;
                }
                return true;
            }
        });
        homeFragment = new HomeFragment();
        takeawayFragment = new TakeawayFragment();
        eathereFragment = new EathereFragment();
        mypageFragment = new MypageFragment();
        setFragment(0); //첫 화면을 home 프래그먼트로 지정

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_takeaway, R.id.navigation_eathere, R.id.navigation_mypage)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);


//    //마이페이지 리스트
//        listview = (ListView)findViewById(R.id.mypage_list_re);
//        List<String> list = new ArrayList<>();
//        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.activity_list_item, list);
//        //리스트뷰의 어댑터를 지정
//        listview.setAdapter(adapter);
//
//        list.add("로그인"); // sub에 회원가입 // 로그인 시 계정관리 메뉴
//        list.add("나의 가게 정보 관리"); //업주의 경우 보이도록
//        list.add("설정");
//        list.add("고객센터");
//        list.add("앱 정보");

//        Button result_button = (Button)findViewById(R.id.result_button);
//
//        result_button.setOnClickListener(new View.OnClickListener(){

//            public void selectSetting(View view) {
//                final List<String> selectedItems = new ArrayList<>();
//
//                //리스트뷰에서 선택된 아이템의 목록을 가져온다.
//                SparseBooleanArray checkedItemPositions = listview.getCheckedItemPositions();
//                for( int i=0; i<checkedItemPositions.size(); i++){
//                    int pos = checkedItemPositions.keyAt(i);
//
//                    if (checkedItemPositions.valueAt(i))
//                    {
//                        selectedItems.add(listview.getItemAtPosition(pos).toString());
//                    }
//                }
//
//                final CharSequence[] items = selectedItems.toArray(new String[selectedItems.size()]);

//           }

    }


    // 프래그먼트 교체 메소드
    private void setFragment(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, homeFragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, takeawayFragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, eathereFragment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, mypageFragment);
                ft.commit();
                break;
        }
    }

    // 버튼 이벤트 - MapActivity로 화면 전환
    public void mapView(View v){
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
//            ((MapActivity)getActivity()).onCreate();
        startActivity(intent);
    }

    public void SearchAny(View view) {
        if (view.getId() == R.id.search_any) {
//            ft.replace(view, searchView);
//            ft.commit();
        }
    }

}
