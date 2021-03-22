package com.example.medicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LayoutDrug5 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Member Variable ===========================================================================
    private final String TAG = "LayoutDrug5";
    private final boolean D = true;


    private ListView resLST;        //결과들 모으기 위한 리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private Button preBTN5;
    private TextView now_timeTXT;
    private Switch SW;

    //List Data
   private ArrayList<HashMap<String, String>> arrDatas;

    //데이터와 리스트뷰 연결시켜줄 어댑터!!
   private SimpleAdapter adapter;



   /////////////////////////////////

    //데이터 담을
    private ArrayList<com.example.medicationproject.itemData> dataArray;

    //데이터와 리스트뷰 연결시켜줄 어댑터!!
    private com.example.medicationproject.itemDataAdapter ad;

    private boolean on_off_ck = false;


    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
    //////////////////////////////////
    private TimePicker timePicker;
    private Calendar calendar;
    private String timeMessage;
    //////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_druglist_5);


        //초기화 메서드
        init();

        content(); //내용 출력

        //디버깅 확인 용
        if (D)
            Log.i(TAG, "LayoutDrug5 OK");



        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////알람때문!!!!!!!시작

//        Calendar nextNotifyTime = initTime();
//
//        initView();
//
//        initTimePicker(nextNotifyTime);



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////알람때문!!!!!!!!끝


    }


    //Memeber Method - Custome ====================================================
    //App 초기화 메소드
    private void init() {
        if (D)
            Log.i(TAG, "init OK");


        resLST = findViewById(R.id.resLST);       //결과들 모으기 위한 리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        preBTN5 = findViewById(R.id.preBTN5);
        now_timeTXT = findViewById(R.id.now_timeTXT);

        SW = findViewById(R.id.SW);


    }



    public void onClick(View v) throws ParseException {
        Intent rxintent = this.getIntent();


        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        String formatDate = mFormat.format(mDate);
        now_timeTXT.setText(formatDate);

         //현재 시간 출력--------------------------------

        Log.i(TAG, "onClick SW");

        SimpleDateFormat dateFormat = new SimpleDateFormat("hhmm", Locale.getDefault());

        String get_timeETXT1 = rxintent.getStringExtra("Time1");
        String time1 = "";
        time1 = get_timeETXT1;

        String get_timeETXT2 = rxintent.getStringExtra("Time2");
        String time2 = "";
        time2 = get_timeETXT2;

        String get_timeETXT3 = rxintent.getStringExtra("Time3");
        String time3 = "";
        time3 = get_timeETXT3;


        Date date1 = new Date();
        String strDate= dateFormat.format(date1);

        com.example.medicationproject.SwData ck1,ck2,ck3;
        int i_time1, i_time2, i_time3, dn;

        dn = Integer.parseInt(strDate);
        i_time1 = Integer.parseInt(time1);
        i_time2 = Integer.parseInt(time2);
        i_time3 = Integer.parseInt(time3);

        ck1=new com.example.medicationproject.SwData();
        ck2=new com.example.medicationproject.SwData();
        ck3=new com.example.medicationproject.SwData();
        ck1.Swich1();
        ck2.Swich2();
        ck3.Swich3();
        ck1.on1 = ((Switch)v).isChecked();
        ck2.on2 = ((Switch)v).isChecked();
        ck3.on3 = ((Switch)v).isChecked();

//        switch (v.getId()){
//
//            case R.id.SW:
//                if(((dn - i_time1) == 0)&&(ck1.on1 == true)){
//                    Toast.makeText(LayoutDrug5.this,"시간확인 같을 때"+i_time1,Toast.LENGTH_SHORT).show();
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                    ringtone.play();
//                } else if(((dn - i_time2) == 0)&&(ck2.on2 == true)){
//                    Toast.makeText(LayoutDrug5.this,"시간확인 같을 때"+i_time2,Toast.LENGTH_SHORT).show();
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                    ringtone.play();
//
//                } else if(((dn - i_time3) == 0)&&(ck3.on3 == true)){
//                    Toast.makeText(LayoutDrug5.this,"시간확인 같을 때"+i_time3,Toast.LENGTH_SHORT).show();
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                    ringtone.play();
//                }
//                else{
//                    Toast.makeText(LayoutDrug5.this,"시간이 다릅니다."+i_time1,Toast.LENGTH_SHORT).show();
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                    ringtone.stop();
//                }
//
//             break;
//        }

//        if((dn - i_time1) == 0){
//            ck1.on1 = ((Switch)v).isChecked();
//            if(ck1.on1==true){
//                Toast.makeText(LayoutDrug5.this,"시간확인 같을 때"+i_time1,Toast.LENGTH_SHORT).show();
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                ringtone.play();
//
//            }
//            else{
//                Toast.makeText(LayoutDrug5.this,"시간이 다릅니다."+i_time1,Toast.LENGTH_SHORT).show();
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                ringtone.stop();
//            }
//        }
//        else if((dn - i_time2) == 0){
//            ck2.on2 = ((Switch)v).isChecked();
//            if(ck2.on2==true){
//                Toast.makeText(LayoutDrug5.this,"시간확인 같을 때"+i_time2,Toast.LENGTH_SHORT).show();
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                ringtone.play();
//
//            }
//            else{
//                Toast.makeText(LayoutDrug5.this,"시간이 다릅니다."+i_time2,Toast.LENGTH_SHORT).show();
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                ringtone.stop();
//            }
//        }
//        else if((dn - i_time3)==0){
//            ck3.on3 = ((Switch)v).isChecked();
//            if(ck3.on3==true){
//                Toast.makeText(LayoutDrug5.this,"시간확인 같을 때"+i_time3,Toast.LENGTH_SHORT).show();
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                ringtone.play();
//
//            }
//            else{
//                Toast.makeText(LayoutDrug5.this,"시간이 다릅니다."+i_time3,Toast.LENGTH_SHORT).show();
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                ringtone.stop();
//            }
//        }
//        else
//        {
//            return;
//        }
    }




    public void content(){
        //전 페이지로 부터 받기 위한 변수들==============================================================
        //현재 Acitivity 실행한 Intent 객체 가져오기
        Intent rxintent = this.getIntent(); //받은거 읽는다.

        String rxData = rxintent.getStringExtra("msg");
        String msg = "";
        msg = msg + rxData;
        Log.i(TAG, "LayoutDrug4 msg ==> " + msg);


        String get_everyBTN = rxintent.getStringExtra("EveryDay");//"매일" 클릭을 전달하기 위한 변수
        String every = "";
        every = every + get_everyBTN;
        Log.i(TAG, "LayoutDrug4 EveryDay==> " + every);


        String get_specificETXT = rxintent.getStringExtra("SpecificDay");    //특정 일수 일 EditTXT 메시지 받기위한 변수
        String specific = "";
        specific = specific + get_specificETXT;
        Log.i(TAG, "LayoutDrug4 SpecificDay==> " + specific);


        String[] checkDay = {"", "", "", "", "", "", ""};
        checkDay[0] = checkDay[0] + rxintent.getStringExtra("Monday");
        checkDay[1] = checkDay[1] + rxintent.getStringExtra("Tuesday");
        checkDay[2] = checkDay[2] + rxintent.getStringExtra("Wednesday");
        checkDay[3] = checkDay[3] + rxintent.getStringExtra("Thursday");
        checkDay[4] = checkDay[4] + rxintent.getStringExtra("Friday");
        checkDay[5] = checkDay[5] + rxintent.getStringExtra("Saturday");
        checkDay[6] = checkDay[6] + rxintent.getStringExtra("Sunday");
        Log.i(TAG, checkDay[0] + checkDay[1] + checkDay[2] + checkDay[3] + checkDay[4] + checkDay[5] + checkDay[6]);


        String get_daysETXT = rxintent.getStringExtra("Days");
        String days = "";
        days = days + get_daysETXT;
        Log.i(TAG, "LayoutDrug4 days==> " + days);

        String get_numETXT = rxintent.getStringExtra("Nums");
        String num = "";
        num = num + get_numETXT;
        Log.i(TAG, "LayoutDrug4 num==> " + num);

        String get_quantityETXT = rxintent.getStringExtra("Quantities");
        String quantity = "";
        quantity = quantity + get_quantityETXT;
        Log.i(TAG, "LayoutDrug4 quantity==> " + quantity);


        String get_timeETXT1 = rxintent.getStringExtra("Time1");
        String time1 = "";
        time1 = time1 + get_timeETXT1;

        String get_countETXT1 = rxintent.getStringExtra("Count1");
        String count1 = "";
        count1 = count1 + get_countETXT1;


        String get_timeETXT2 = rxintent.getStringExtra("Time2");
        String time2 = "";
        time2 = time2 + get_timeETXT2;

        String get_countETXT2 = rxintent.getStringExtra("Count2");
        String count2 = "";
        count2 = count2 + get_countETXT2;

        String get_timeETXT3 = rxintent.getStringExtra("Time3");
        String time3 = "";
        time3 = time3 + get_timeETXT3;

        String get_countETXT3 = rxintent.getStringExtra("Count3");
        String count3 = "";
        count3 = count3 + get_countETXT3;
        //==================================================================================================================







        //데이터준비
        dataArray = new ArrayList<com.example.medicationproject.itemData>();
        if (time1.length() > 0) {
            dataArray.add(new com.example.medicationproject.itemData(msg, time1, false));
        }

        if (time2.length() > 0) {
            dataArray.add(new com.example.medicationproject.itemData(msg, time2, false));
        }

        if (time3.length() > 0) {
            dataArray.add(new com.example.medicationproject.itemData(msg, time3, false));
        }


        //어댑터한테 뷰랑 데이터 준다 -> 그럼 리스트 생성
        ad = new com.example.medicationproject.itemDataAdapter(com.example.medicationproject.LayoutDrug5.this, R.layout.layout_text, dataArray);

        resLST.setAdapter(ad);




/*
        //ListData준비
        arrDatas = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        map.put("약", msg);
        map.put("시간", time1); //put(키,)
        arrDatas.add(map);


        map= new HashMap<>();
        map.put("약", msg);
        map.put("시간", time2); //put(키,)
        arrDatas.add(map);


        map = new HashMap<>();
        map.put("약", msg);
        map.put("시간", time3); //put(키,)
        arrDatas.add(map);




        //List생성 및 관리 Adapter
        adapter = new SimpleAdapter(LayoutDrug5.this,
                arrDatas,
                R.layout.layout_text,
                new String[]{"약", "시간"},
                new int[]{R.id.drugTXT, R.id.timeTXT});




        //ListView(dataLST)의 리스트(item)를 설정시켜준다
        resLST.setAdapter(adapter);


 */


        //resLST.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        resLST.setOnItemClickListener(this::onItemClick);  //implement를 통해 생성해 리슨함수 실행 (밑에 처럼 하지않고)


    }


    public void preivous(View v) {


        finish();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG, "onClick ITEM");





    }
    public void time()
    {
        //현재시간 출력

    }

}