package com.example.illegalskillsexception.ulife; /**
 * Created by healthappy on 10/20/18.
 */

import java.util.Calendar;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;


public class TimePickerFragment extends DialogFragment{ //implements TimePickerDialog.OnTimeSetListener {
    private Activity mActivity;
    private TimePickerDialog.OnTimeSetListener mListener;

    public TimePickerFragment() {}

    // store the start and end times in Prefs
    public String title; int ID; int startHour, startMinute, endHour, endMinute;

    public void setTitle(String s) {
        title = s;
    }

    public void setID(int id) { ID = id; }
/*    public com.example.illegalskillsexception.ulife.TimePickerFragment(String s, int id) {
        title = s; ID = id;
        Log.d("tpd", "constructor: 1");
    } */


     @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        try {
            mListener = (TimePickerDialog.OnTimeSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
        }

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("tpd", "oncreatedialog: 2");
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog TPD = new TimePickerDialog(mActivity, mListener, hour, minute,
                DateFormat.is24HourFormat(mActivity));
        TPD.setTitle(title);
        return TPD;
    }




    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if (ID == 1) { // start time
            startHour = hourOfDay; startMinute = minute;
        } else if (ID == 2) { // end time
            endHour = hourOfDay; endMinute = minute;
        }
    }
}

