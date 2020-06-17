package com.heejae.foopa;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.heejae.foopa.SQLite.DBHelper;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private TakeawayFragment takeawayFragment;
    private EathereFragment eathereFragment;
    private MypageFragment mypageFragment;
    private UserpageFragment userpageFragment;
    private ListView listview;
    private DBHelper db;
    private MyApplication myApp;
    private long backBtnTime = 0;
    public static String keyword_All = "All";
    public static String keyword_takeaway = "포장";
    public static String keyword_eathere = "매장";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this, "foopa.db", null, 1);
        myApp = (MyApplication) getApplication(); // 어플리케이션 객체

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
                        //if문 - 로그인 여부로
                        if (myApp.getLoggedUser().length() == 0){
                            setFragment(3);
                        }else{
                            setFragment(4);
                        }
                        break;
                }
                return true;
            }
        });
        homeFragment = new HomeFragment();
        takeawayFragment = new TakeawayFragment();
        eathereFragment = new EathereFragment();
        mypageFragment = new MypageFragment();
        userpageFragment = new UserpageFragment();
        setFragment(0); //첫 화면을 home 프래그먼트로 지정

    }

    // 프래그먼트 교체 메소드
    private void setFragment(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                for(int i=0; i<fm.getBackStackEntryCount(); i++){
                    fm.popBackStack();
                }
                ft.replace(R.id.main_frame, homeFragment);
                ft.commit();
                break;
            case 1:
                for(int i=0; i<fm.getBackStackEntryCount(); i++){
                    fm.popBackStack();
                }
                ft.replace(R.id.main_frame, takeawayFragment);
                ft.commit();
                break;
            case 2:
                for(int i=0; i<fm.getBackStackEntryCount(); i++){
                    fm.popBackStack();
                }
                ft.replace(R.id.main_frame, eathereFragment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, mypageFragment);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, userpageFragment);
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

//    // 앱 종료 이벤트
//    @Override
//    public void onBackPressed() {
//        long curTime = System.currentTimeMillis();
//        long gapTime = curTime - backBtnTime;
//
//        if (fm.getBackStackEntryCount() == 0)
//        {
////            fm.popBackStack();
////            ft.commit();
////        } else {
//            if (0 <= gapTime && 2000 >= gapTime) {
//                super.onBackPressed();
////                MyApplication myApp = (MyApplication) this.getApplication();
////                myApp.setLoggedUser(""); // 로그인 세션 방식으로 아이디를 전역변수로 설정
////                finish();
//            } else {
//                backBtnTime = curTime;
//                Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
