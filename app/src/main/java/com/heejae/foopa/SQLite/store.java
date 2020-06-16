package com.heejae.foopa.SQLite;

public class store {
    private String id;
    private String user_id;
    private String store_kind;
    private String menu_kind;
    private String store;
    private Double locationX;
    private Double locationY;
    public store (String id, String user_id, String store_kind, String menu_kind, String store, Double locationX, Double locationY){
        this.id = id;
        this.user_id = user_id;
        this.store_kind = store_kind;
        this.menu_kind = menu_kind;
        this.store = store;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
