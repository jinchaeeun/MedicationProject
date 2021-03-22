package com.example.medicationproject;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class ItemDataHolder {
//
    public TextView drugTXT;
    public TextView timeTXT;
    public Switch SW;

    public ItemDataHolder(TextView drugTXT, TextView timeTXT, Switch SW) {

        this.drugTXT = drugTXT;
        this.timeTXT = timeTXT;
        this.SW = SW;
    }

    public ItemDataHolder(View root){

        this.drugTXT = root.findViewById(R.id.drugTXT);
        this.timeTXT = root.findViewById(R.id.timeTXT);
        this.SW = root.findViewById(R.id.SW);
    }


}
