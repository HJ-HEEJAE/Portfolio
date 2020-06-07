package com.heejae.foopa.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends android.database.sqlite.SQLiteOpenHelper {
    public final static String TABLE_NAME = "user";
    public final static String ID = "user_id";
    public final static String PASSWORD = "user_password";
    public final static String NAME = "user_name";
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table
        String create_query = "CREATE TABLE IF NOT exists " + TABLE_NAME + "(" + ID + " text PRIMARY KEY, " + PASSWORD + " text NOT NULL, " + NAME + " text NOT NULL);";
        db.execSQL(create_query);
    }

    // Version managing
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop Table
        String drop_query = "DROP TABLE " + TABLE_NAME + ";";
        db.execSQL(drop_query);
        // Re-Create Table
        onCreate(db);
    }


    // DB Insert
    // 유저 추가
    public boolean insert(String user_id, String user_password, String user_name) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, user_id);
        values.put(PASSWORD, user_password);
        values.put(NAME, user_name);
        long ins = db.insert(TABLE_NAME, null, values);
//        return (ins == -1) ? false : true; // 성공 여부 반환
        if (ins == -1){
            return false;
        }else{
            return true;
        }
    }

    // DB Select
    // 아이디 생성 중복체크
    public boolean checkId(String user_id){
        db = this.getReadableDatabase();
        // DB 커서 사용
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_id = ?", new String[] {user_id});
        // 아이디가 이미 존재
        if (cursor.getCount() > 0){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }

    // 로그인 정보 체크, 성공시 유저 정보 전달.
    public String[] checkLogin(String user_id, String user_password){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_id = ? AND user_password = ?", new String[] {user_id, user_password});
        // 정보가 일치하는 유저 존재
        if (cursor.getCount() > 0){
            // Get the result data
            String login_user_id = "";
            String login_user_password = "";
            // 계속해서 다음 행 선택 (여기서는 id가 unique하므로 결과는 하나의 행만 도출될 것)
            while (cursor.moveToNext()) {
                login_user_id = cursor.getString(cursor.getColumnIndex(ID));
                login_user_password = cursor.getString(cursor.getColumnIndex(NAME));
            }
            // 같은 방식
//            while (cursor.moveToNext()) {
//                login_user_id = cursor.getString(0);
//                Log.d("db-selected-user_id", login_user_id); //콘솔에 출력
//                login_user_password = cursor.getString(2);
//            }
            cursor.close();
            return new String[]{login_user_id, login_user_password};

        }else{
            cursor.close();
            return new String[] {"", ""};
        }
    }
}
