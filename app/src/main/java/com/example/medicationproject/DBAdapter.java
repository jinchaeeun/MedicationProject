package com.example.medicationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// SQLite Database에 관련된 모든 메서드 -----------------------------
//따로따로 만들지말고 여기 한번에 넣자
public class DBAdapter {
    // Member variable ---------------------------------------------
    private final String TAG = "BloodSugar";
    private DBOpenHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;


    // Member Method - Override ------------------------------------
    public DBAdapter(Context context) { //context만 빋겠다.
        this.context = context;
        this.dbHelper = new DBOpenHelper(context);
        Log.i(TAG, " => DBAdapter : DBAdapter()");
        //DBOpenHelper에서 테이블 생성이 안되서 여기에서 다시 생성해줌
        db=dbHelper.getWritableDatabase();
        db.execSQL(DBOpenHelper.CREATE_TABLE_MESSAGE);
        db.close();
    }

    // Member Method - Custom -----------------------------------
    //DB Open & Close---------------------------------------
    public void openDB(boolean isWrite) {
        if (isWrite == true) {
            this.db = dbHelper.getWritableDatabase();
        } else {
            this.db = dbHelper.getReadableDatabase();
        }
        Log.i(TAG, " => DBAdapter : openDB()");

    }

    public void closeDB() {
        if (db.isOpen()) dbHelper.close();
        Log.i(TAG, " => DBAdapter : closeDB()");
    }

    //DB Insert/Update/Delete -------------------------------------------------
    public long insertRow(String tablename, ContentValues newValue) {
        openDB(true);   //insert니까 true 로 줘야 권한 허용

        long rowId = db.insert(tablename, null, newValue);

        closeDB();
        Log.i(TAG, " => DBAdapter : insertRow(), rowId = " + rowId);    //행 몇 번째 들어갔는지 확인. rowID는 삭제해도 이후 값만..
        return rowId;
    }

    public boolean deleteRow(String tablename, long rowID){
        openDB(true);

        // delete from message_tbl where_id=
        int delNums = db.delete(tablename, DBInfo.SUGAR_ID + "=" + rowID, null);
        Log.i(TAG, " => DBAdapter : deleteRow() || rowID : " + rowID + "|| delNums : " + delNums);

        closeDB();

        return (delNums >0)? true:false;
    }
    //db 선택 조회
    public Cursor getSelectRow(int sugDate) {
        Log.i(TAG, "getSelectRow() sugDate => " + sugDate );
        if(db.isOpen())  db.close();

        openDB(true);

        //Cursor cursor = db.rawQuery("select * from " + DBInfo.TABLE_BLOOD_SUGAR, null);
        Cursor cursor = db.rawQuery("select * from " + DBInfo.TABLE_BLOOD_SUGAR + " where mTxtDate = " + sugDate, null);

        //closeDB(); //뭐 안되면 getAllow했는데서 DB 닫아주면 됨
        Log.i(TAG, " => DBAdapter : insertRow(), Count - " + cursor.getCount()); //db 갯수 보기

        showCursor(cursor);         //Data 확인용 잘 가져왔는지.
        cursor.moveToFirst();       //Data 전달용
        return cursor;
    }


    // Debug ----내가 제대로 가져왔는지 보고싶을 때--------------------------------------------------------------
    private void showCursor(Cursor cursor) {
        //cursor 안 다 확인
        if (cursor != null) {
            String tmp = "";
            while (cursor.moveToNext()) {
                tmp = "[ " + cursor.getInt(cursor.getColumnIndex(DBInfo.SUGAR_ID)) + " ] ";  //이거의 indexID를 주세요
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.MEAL_SPINNER)) + " , ";
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.SUGAR_MEASURE)) + " , ";
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.SUGAR_TIME)) + " , ";
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.SMEASURE_DATE)) + "";
            }
            Log.i(TAG, " => DBAdapter : showCursor() ===> " + tmp);
        }
    }

    private int getRowCount(Cursor cursor) {
        if (cursor != null) {
            Log.i(TAG, " => DBAdapter : getRowCount() ===> ");
            return cursor.getCount();
        } else {
            return -1;
        }
    }

}
