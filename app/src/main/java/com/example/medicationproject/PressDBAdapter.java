package com.example.medicationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//Press
// SQLite Database에 관련된 모든 메서드 ----------------------------
//따로따로 만들지말고 여기 한번에 넣자
public class PressDBAdapter {
    // Member variable --------------------------------------------
    private final String TAG = "BloodPress";
    private PressDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;


    // Member Method - Override -----------------------------------
    public PressDBAdapter(Context context) { //context만 빋겠다.
        this.context = context;
        this.dbHelper = new PressDBOpenHelper(context);
        Log.i(TAG, " => PressDBAdapter : PressDBAdapter()");
    }

    // Member Method - Custom -----------------------------------
    //DB Open & Close---------------------------------------
    public void openDB(boolean isWrite) {
        if (isWrite == true) {
            this.db = dbHelper.getWritableDatabase();
        } else {
            this.db = dbHelper.getReadableDatabase();
        }
        Log.i(TAG, " => PressDBAdapter : openDB()");
    }

    public void closeDB() {
        if (db.isOpen()) dbHelper.close();
        Log.i(TAG, " => PressDBAdapter : closeDB()");
    }

    //DB Insert/Update/Delete -------------------------------------------------
    public long insertRow(String tablename, ContentValues newValue) {
        openDB(true);   //insert니까 true 로 줘야 권한 허용

        long rowId = db.insert(tablename, null, newValue);

        closeDB();
        Log.i(TAG, " => PressDBAdapter : insertRow(), rowId = " + rowId);    //행 몇 번째 들어갔는지 확인. rowID는 삭제해도 이후 값만..
        return rowId;
    }

    public boolean deleteRow(String tablename, long rowID){
        openDB(true);

        // delete from message_tbl where_id=
        int delNums = db.delete(tablename, DBInfo.PRESSURE_ID + "=" + rowID, null);
        Log.i(TAG, " => PressDBAdapter : deleteRow() || rowID : " + rowID + "|| delNums : " + delNums);

        closeDB();

        return (delNums >0)? true:false;
    }
    //db 선택 조회
    public Cursor getSelectRow(int sugDate) {
        Log.i(TAG, "PressDBAdapter : getSelectRow() sugDate => " + sugDate );
        openDB(true);

        //Cursor cursor = db.rawQuery("select * from " + DBInfo.TABLE_BLOOD_SUGAR, null);
        Cursor cursor = db.rawQuery("select * from " + DBInfo.TABLE_BLOOD_PRESSURE + " where mTxtDate = " + sugDate, null);

        //closeDB(); //뭐 안되면 getAllow했는데서 DB 닫아주면 됨
        Log.i(TAG, " => PressDBAdapter : insertRow(), Count - " + cursor.getCount()); //db 갯수 보기

        showCursor(cursor);         //Data 확인용 잘 가져왔는지.
        cursor.moveToFirst();       //Data 전달용.  while문이 안들어간게 moveToFirst()가 적용이 안되서. showCursor에서 문제가 있었을 수 있음.
        return cursor;
    }


    // Debug ----내가 제대로 가져왔는지 보고싶을 때--------------------------------------------------------------
    private void showCursor(Cursor cursor) {
        //cursor 안 다 확인
        if (cursor != null) {
            String tmp = "";
            while (cursor.moveToNext()) {
                tmp = "[ " + cursor.getInt(cursor.getColumnIndex(DBInfo.PRESSURE_ID)) + " ] ";  //이거의 indexID를 주세요
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.MOR_EVE_SPINNER)) + " , ";
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.PRESS_MEASURE)) + " , ";
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.PRESS_TIME)) + " , ";
                tmp += cursor.getString(cursor.getColumnIndex(DBInfo.PMEASURE_DATE)) + "";
            }
            Log.i(TAG, " => PressDBAdapter : showCursor() ===> " + DBInfo.MOR_EVE_SPINNER + ", " + DBInfo.PRESS_MEASURE + ", "  + DBInfo.PRESS_TIME + ", "  + DBInfo.PMEASURE_DATE);

        }
    }

    private int getRowCount(Cursor cursor) {
        if (cursor != null) {
            Log.i(TAG, " => PressDBAdapter : getRowCount() ===> ");
            return cursor.getCount();
        } else {
            return -1;
        }
    }

}
