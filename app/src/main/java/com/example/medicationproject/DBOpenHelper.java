package com.example.medicationproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    // Member variable --------------------------------------------
    private final String    TAG ="DBOpenHelper";
    //테이블 생성. 공백 잘 지키기
    public static final String CREATE_TABLE_MESSAGE = "create table if not exists " +
            DBInfo.TABLE_BLOOD_SUGAR +
            "(" + DBInfo.SUGAR_ID + " integer primary key autoincrement, " +
            DBInfo.MEAL_SPINNER + " text not null, " +
            DBInfo.SUGAR_MEASURE + " integer not null, " +
            DBInfo.SUGAR_TIME + " text not null, " +
            DBInfo.SMEASURE_DATE + " integer not null );";

    //테이블 삭제
    private static final String DROP_TABLE_MESSAGE =    "drop table if exists "
            + DBInfo.TABLE_BLOOD_SUGAR +";";

    private Context context;

    // Member Method - Constructor -----------------------------------
    // Constructor에서 DB 생성됨
    // 지정된 이름의 Database 생성
    public DBOpenHelper(@Nullable Context context) {
        super(context, DBInfo.DB_NAME, null, DBInfo.DB_VERSION);
        //context 값 넣어주기
        this.context = context;
        Log.i(TAG, " => DBOpenHelper : DBOpenHelper()");
    }
    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, Context context1) {
        super(context, name, factory, version);
        this.context = context;
        Log.i(TAG, " => DBOpenHelper : DBOpenHelper()");
    }


    // Member Method - Override -----------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) { //Constructor에서 db인자
        //db가 생성되었을 때 호출.
        //Table 생성
        db.execSQL(CREATE_TABLE_MESSAGE); //생성이 안되서 DBAdapter 클래스
        Log.i(TAG, " => DBOpenHelper : OnCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DB 업그레이드 시에 사용
        //if(newVersion != DBInfo.DB_VERSION) 안해도 자동으로 된다.

        db.execSQL(DROP_TABLE_MESSAGE); //기존 Table 삭제
        onCreate(db); //db.execSQL(CREATE_TABLE_MESSAGE); // Table 새로 생성
        Log.i(TAG, "DBOpenHelper : onUpgrade()");

    }
}
