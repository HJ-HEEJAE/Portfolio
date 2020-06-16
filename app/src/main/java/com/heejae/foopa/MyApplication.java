package com.heejae.foopa;

import android.app.Application;

// 전역변수 클래스 MyApplication
// 액티비티들을 관리하는 객체로, 어플리케이션의 라이프사이클이라고 할 수 있는 Application 을 상속
public class MyApplication extends Application {
    private String loggedUser = "";

    public String getLoggedUser(){
        return loggedUser;
    }
    public void setLoggedUser(String user_id){
        this.loggedUser = user_id;
    }
}
