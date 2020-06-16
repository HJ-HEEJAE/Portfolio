package com.heejae.foopa.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.heejae.foopa.R;

import java.util.ArrayList;
import java.util.List;

import static com.heejae.foopa.HomeFragment.keyword_All;
import static com.heejae.foopa.TakeawayFragment.keyword_takeaway;
import static com.heejae.foopa.EathereFragment.keyword_eathere;

public class DBHelper extends android.database.sqlite.SQLiteOpenHelper {
    public final static String USER_TABLE_NAME = "user";
    public final static String USER_ID = "user_id";
    public final static String PASSWORD = "user_password";
    public final static String USER_NAME = "user_name";
    public final static String WHOLE_STORE_TABLE_NAME = "wholestore";
    public final static String WHOLE_ID = "id";
    public final static String STORE_KIND = "store_kind";
    public final static String MENU_KIND = "menu_kind";
    public final static String STORE = "store";
    public final static String STORE_LOCATION_X = "locationX";
    public final static String STORE_LOCATION_Y = "locationY";
    public final static String STORE_TABLE_NAME = "store";
    public final static String STORE_ID = "id";
    public final static String STORE_NAME = "store_name";
    public final static String MENU = "menu";
    public final static String PRICE = "price";
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table
        String create_user_query = "CREATE TABLE IF NOT exists " + USER_TABLE_NAME + "(" + USER_ID + " text PRIMARY KEY NOT NULL, " + PASSWORD + " text NOT NULL, " + USER_NAME + " text NOT NULL);";
        String create_wholestore_query = "CREATE TABLE IF NOT exists "+ WHOLE_STORE_TABLE_NAME + "(" + WHOLE_ID + " integer PRIMARY KEY NOT NULL, " + USER_ID + " text NOT NULL, " + STORE_KIND + " text NOT NULL, "
                                        + MENU_KIND + " text NOT NULL, " + STORE + " text NOT NULL, " + STORE_LOCATION_X + " real, "+ STORE_LOCATION_Y
                                        + " real, FOREIGN KEY("+USER_ID+") REFERENCES "+USER_TABLE_NAME+"("+USER_ID+"));";
        String create_store_query = "CREATE TABLE IF NOT exists "+ STORE_TABLE_NAME + "(" + STORE_ID + " integer PRIMARY KEY NOT NULL, " + USER_ID + " text NOT NULL, " + STORE_KIND + " text NOT NULL, "
                                        + MENU_KIND + " text NOT NULL, " + STORE_NAME + " text NOT NULL, " + MENU + " text NOT NULL, "+ PRICE +" text NOT NULL, FOREIGN KEY("+USER_ID+") REFERENCES "+USER_TABLE_NAME+"("+USER_ID+"));";
        db.execSQL(create_user_query);
        db.execSQL(create_wholestore_query);
        db.execSQL(create_store_query);
    }

    // Version managing
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop Table
        String drop_query = "DROP TABLE " + USER_TABLE_NAME + ";";
        String drop_wholestore_query = "DROP TABLE " + WHOLE_STORE_TABLE_NAME + ";";
        String drop_store_query = "DROP TABLE " + STORE_TABLE_NAME + ";";
        db.execSQL(drop_query);
        db.execSQL(drop_wholestore_query);
        db.execSQL(drop_store_query);
        // Re-Create Table
        onCreate(db);
    }


    // DB Insert
    // 유저 추가
    public boolean userInsert(String user_id, String user_password, String user_name) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(PASSWORD, user_password);
        values.put(USER_NAME, user_name);
//        String insert_query = "INSERT INTO " + TABLE_NAME + "("+ ID +","+PASSWORD+","+NAME+") VALUES ("+user_id+","+user_password+","+user_name+")";
        long ins = db.insert(USER_TABLE_NAME, null, values);
        Log.d("insert", "id: "+ins);
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
        Log.d("login: ", user_id+" "+user_password);
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_id = ? AND user_password = ?", new String[] {user_id, user_password});
        // 정보가 일치하는 유저 존재
        if (cursor.getCount() > 0){
            // Get the result data
            String login_user_id = "";
            String login_user_password = "";
            // 계속해서 다음 행 선택 (여기서는 id가 unique하므로 결과는 하나의 행만 도출될 것)
            while (cursor.moveToNext()) {
                login_user_id = cursor.getString(cursor.getColumnIndex(USER_ID));
                login_user_password = cursor.getString(cursor.getColumnIndex(USER_NAME));
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
            return new String[] {""};
        }
    }

    // 가게 추가
    public boolean storeInsert(String user_id, String store_kind, String menu_kind, String store, double locationX, double locationY) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(STORE_KIND, store_kind);
        values.put(MENU_KIND, menu_kind);
        values.put(STORE, store);
        values.put(STORE_LOCATION_X, locationX);
        values.put(STORE_LOCATION_Y, locationY);
        long ins = db.insert(WHOLE_STORE_TABLE_NAME, null, values);
        Log.d("insert store", "id: "+ins);
        if (ins == -1){
            return false;
        }else{
            return true;
        }
    }

    //////////////////////(추후수정) 배열이 아닌 클래스 객체 사용으로 바꾸기/////////////////////////

    // 가게 select
    // Home 화면에서 넘어가는 카테고리 별 가게 목록
    public ArrayList<String[]> getStores(String store_kind, String menu_kind){
        db = this.getReadableDatabase();
        Cursor cursor = null;
        // Home - 전체(포장/매장)
        if (store_kind.equals(keyword_All)){
            // 전체 메뉴
            if (menu_kind.equals(keyword_All)){
                cursor = db.rawQuery("SELECT * FROM wholestore", null);
            // 메뉴 카테고리 선택
            }else{
                cursor = db.rawQuery("SELECT * FROM wholestore WHERE menu_kind = ?", new String[] {menu_kind});
            }
        // 포장 or 매장
        }else{
            // 전체 메뉴
            if (menu_kind.equals(keyword_All)){
                cursor = db.rawQuery("SELECT * FROM wholestore WHERE store_kind = ?", new String[] {store_kind});
            // 메뉴 카테고리 선택
            }else{
                cursor = db.rawQuery("SELECT * FROM wholestore WHERE store_kind = ? AND menu_kind = ?", new String[] {store_kind, menu_kind});
            }
        }
        ArrayList<String[]> storelist = new ArrayList<String[]>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(WHOLE_ID));
                String user_id = cursor.getString(cursor.getColumnIndex(USER_ID));
                store_kind = cursor.getString(cursor.getColumnIndex(STORE_KIND));
                menu_kind = cursor.getString(cursor.getColumnIndex(MENU_KIND));
                String store = cursor.getString(cursor.getColumnIndex(STORE));
                Double locationX = cursor.getDouble(cursor.getColumnIndex(STORE_LOCATION_X));
                Double locationY = cursor.getDouble(cursor.getColumnIndex(STORE_LOCATION_Y));
//                storelist.add(new store(id, user_id, store_kind, menu_kind, store, locationX, locationY));
                storelist.add(new String[] {id, user_id, store_kind, menu_kind, store, locationX.toString(), locationY.toString()});
            }
            cursor.close();
            return storelist;
        }else{
            cursor.close();
            return storelist;
        }
    }

    // 메뉴 추가
    public boolean menuInsert(String user_id, String store_kind, String menu_kind, String store, String menu, int price) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user_id);
        values.put(STORE_KIND, store_kind);
        values.put(MENU_KIND, menu_kind);
        values.put(STORE_NAME, store);
//        for (int i=0; i<menu.length; i++){
        values.put(MENU, menu);
        values.put(PRICE, price);
        long ins = db.insert(STORE_TABLE_NAME, null, values);
        Log.d("insert menu", "id: "+ins);
        if (ins == -1){
            return false;
        }else{
            // initialize menu and price keys for next menu.
            values.remove(MENU);
            values.remove(PRICE);
            return true;
        }
    }

    // 매장메뉴 select by store_kind(포장/매장)
    // 2차원 배열리스트 개념
    public ArrayList<String[]> getStore(String user_id, String store_kind){
        db = this.getReadableDatabase();
        Cursor cursor = null;
        // Home - 전체(포장/매장)
        if (store_kind.equals("all")){
            cursor = db.rawQuery("SELECT * FROM store WHERE user_id = ?", new String[] {user_id});
        // 포장 or 매장
        }else{
            cursor = db.rawQuery("SELECT * FROM store WHERE user_id = ? AND store_kind = ?", new String[] {user_id, store_kind});
        }

        ArrayList<String[]> storeInfo = new ArrayList<String[]>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(STORE_ID));
                user_id = cursor.getString(cursor.getColumnIndex(USER_ID));
                store_kind = cursor.getString(cursor.getColumnIndex(STORE_KIND));
                String menu_kind = cursor.getString(cursor.getColumnIndex(MENU_KIND));
                String store = cursor.getString(cursor.getColumnIndex(STORE_NAME));
                String menu = cursor.getString(cursor.getColumnIndex(MENU));
                String price = cursor.getString(cursor.getColumnIndex(PRICE));
                storeInfo.add(new String[] {id, user_id, store_kind, menu_kind, store, menu, price});
            }
            cursor.close();
            return storeInfo;
        }else{
            cursor.close();
            return storeInfo;
        }
    }

    // 해당 유저의 등록된 매장의 영업타입 확인
    public String[] isStore(String user_id){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT store_kind FROM wholestore WHERE user_id = ?", new String[] {user_id});
        // 유저 존재 체크
        if (cursor.getCount() > 0){
            String [] store_kind_Arr = {"N", "N"};
            while (cursor.moveToNext()) {
                Log.d("cursor", cursor.toString());
                String store_kind = cursor.getString(cursor.getColumnIndex(STORE_KIND));
                Log.d("store_kind", store_kind);
                if (store_kind.equals(keyword_takeaway)){
                    store_kind_Arr[0] = keyword_takeaway;
                } else if (store_kind.equals(keyword_eathere)){
                    store_kind_Arr[1] = keyword_eathere;
                }
            }
            cursor.close();;
            return store_kind_Arr;
        }else{
            cursor.close();
            return new String[] {""};
        }
    }

    // 매장정보 가져오기
    public String[] getStoreInfo(String user_id, String store_kind){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM wholestore WHERE user_id = ? AND store_kind = ?", new String[] {user_id, store_kind});
        // 유저 존재 체크
        if (cursor.getCount() > 0){
            String id = "";
            String menu_kind = "";
            String store = "";
            Double locationX = 0.0;
            Double locationY = 0.0;
            while (cursor.moveToNext()) {
                Log.d("userY", cursor.getString(cursor.getColumnIndex(STORE)));
                id = cursor.getString(cursor.getColumnIndex(WHOLE_ID));
                user_id = cursor.getString(cursor.getColumnIndex(USER_ID));
                store_kind = cursor.getString(cursor.getColumnIndex(STORE_KIND));
                menu_kind = cursor.getString(cursor.getColumnIndex(MENU_KIND));
                store = cursor.getString(cursor.getColumnIndex(STORE));
                locationX = cursor.getDouble(cursor.getColumnIndex(STORE_LOCATION_X));
                locationY = cursor.getDouble(cursor.getColumnIndex(STORE_LOCATION_Y));
            }
            cursor.close();
            return new String[] {id, user_id, store_kind, menu_kind, store, locationX.toString(), locationY.toString()};
        }else{
            cursor.close();
            return new String[] {""};
        }
    }
}
