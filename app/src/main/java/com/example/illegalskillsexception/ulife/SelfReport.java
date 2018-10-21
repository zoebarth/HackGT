package com.example.illegalskillsexception.ulife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by healthappy on 10/21/18.
 */

public class SelfReport extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_selfmode);

        TextView tv = (TextView) findViewById(R.id.pick_up_textview);
        tv.setText(DetectionService.getPickedUp() + "");

//        TextView tv2 = (TextView) findViewById(R.id.time_elapsed_textview);
//        tv2.setText(SelfMode.getDifHour() + ":" + SelfMode.getDifMinute());
    }
}
