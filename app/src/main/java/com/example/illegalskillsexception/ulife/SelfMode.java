package com.example.illegalskillsexception.ulife;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by healthappy on 10/20/18.
 */

public class SelfMode extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    static int startHour; static int startMinute;
    boolean timeSet = false;
    public static boolean detectionService;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_mode_view);


        Button timeButton = findViewById(R.id.time_mode_button);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment startTimeFragment = new TimePickerFragment(); // ("Enter Start Time", 1);
                startTimeFragment.setTitle("Enter Event Time");
                startTimeFragment.setID(1);
                startTimeFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        final Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timeSet) {
                    startBackgroundService();
                    Toast.makeText(startButton.getContext(), "Detection Service Started", Toast.LENGTH_SHORT).show();
                }
                else {
                    String msg;
                    msg = "You Need to Specify A Time Before You Can Start detoX Session";
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelfMode.this,  AlertDialog.THEME_HOLO_LIGHT);
                    // android.R.style
                    // .Theme_Material_Light_Dialog_Alert);
                    alertDialogBuilder.setTitle("Your Group Code!");
                    alertDialogBuilder.setMessage(Html.fromHtml(msg))
                            .setCancelable(false)
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel(); // close current activity
                                }
                            });
                    alertDialogBuilder.create(); // create alert dialog
                    alertDialogBuilder.show();
                }
            }
        });

        final Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopBackgroundService();
                Toast.makeText(stopButton.getContext(), "Detection Service Stopped", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void startBackgroundService() {
        Intent intent = new Intent(SelfMode.this, DetectionService.class);
        ((SelfMode) SelfMode.this).startService(intent);
        detectionService = true;
    }

    public void stopBackgroundService() {
        detectionService = false;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Toast.makeText(this, "Time Has Been Set!", Toast.LENGTH_SHORT).show();
        startHour = hourOfDay;
        startMinute = minute;
        timeSet = true;
        //tvTime.setText(startHour + ":" + startMinute);
        // WHEN TIME SET SET BOOLEAN TO TRUE TO ENABLE THE START BUTTON
    }

    public static int getStartHour() {
        return startHour;
    }

    public static int getStartMinute() {
        return startMinute;
    }


}
