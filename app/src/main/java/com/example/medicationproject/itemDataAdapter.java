package com.example.medicationproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class itemDataAdapter extends ArrayAdapter<itemData> {


    //Member Variable ===============================================================================
    private Context context;
    private int layoutResId;
    private ArrayList<itemData> dataList;

    private final String TAG = "LayoutDrug5";
    private final boolean D = true;

    public itemDataAdapter(@NonNull Context context, int resource, @NonNull ArrayList<itemData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layoutResId = resource;
        this.dataList = objects;
    }



    // Overridee Method =============================================================================
    @Override
    //현재 들어있는 데이터 개수 반환
    public int getCount() {
        return dataList.size();


    }

    //@ 없으면 이게 뭔지 모르는데, @있으면 이거는 상속받은 메소드라고 명시한 거다.
    @NonNull
    @Override
    //화면에 그려주는 함수, 특정 position에 convertView에다 그린다.
    //@Nonable은 널일수도 있다~ @NonNull은 널이면 안된다.
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Data ==> XML layout에 넣어서 보이고 사용 할 수 있도록 객체생성성
        // (1) item layout XML  ==> 자바 객체로 변환

        if (convertView == null) {    //null이면 만들어야해
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResId, null);

            ItemDataHolder holder = new ItemDataHolder(convertView);
            convertView.setTag(holder); //이때 생성했는데 모를수 도 있으니.. TAG
        }


        //null이 아니면 만들어 놓은 홀더 가지고 와야해
        ItemDataHolder holder = (ItemDataHolder) convertView.getTag();

        /*      ==> 위의 ItemDataHolder 클래스에서 진행
        // (2) item layout's View 객체 가져오기
        TextView nameTXT = convertView.findViewById(R.id.nameTXT);
        TextView phoneTXT = convertView.findViewById(R.id.phoneTXT);
        TextView addressTXT = convertView.findViewById(R.id.addressTXT);
        ImageView iconIMG = convertView.findViewById(R.id.iconIMG);
        */

        TextView drugTXT = holder.drugTXT;
        TextView timeTXT = holder.timeTXT;
        Switch SW = holder.SW;


        // (3) Data 준비
        final itemData item = dataList.get(position);


        // (4) Layout 이랑 Data 연결
        drugTXT.setText(item.getDrug());
        timeTXT.setText(item.getDate());
        SW.setTag(item.getDate());



        SW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG,""+buttonView.getTag());
            }
        });


        return convertView;
    }

}
