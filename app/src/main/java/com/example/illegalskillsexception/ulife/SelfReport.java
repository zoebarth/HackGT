package com.example.illegalskillsexception.ulife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by healthappy on 10/21/18.
 */

public class SelfReport extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_selfmode);

        TextView tv = (TextView) findViewById(R.id.pick_up_textview);
        tv.setText(DetectionService.getPickedUp() + "");

        //TextView tv2 = (TextView) findViewById(R.id.time_elapsed_textview);
        //tv2.setText(SelfMode.getDifHour() + ":" + SelfMode.getDifMinute());

        final Button DoneButton = findViewById(R.id.button2);
        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfReport.this, MainActivity.class);
                (SelfReport.this).startActivity(intent);
            }
        });

        final Button AgainButton = findViewById(R.id.button1);
        AgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfReport.this, SelfMode.class);
                (SelfReport.this).startActivity(intent);
            }
        });
    }
}
