package com.example.illegalskillsexception.ulife;

/**
 * Created by healthappy on 10/20/18.
 */


import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.widget.Toast;
import java.lang.Math;
import java.util.Calendar;

public class DetectionService extends IntentService implements SensorEventListener {

    public static BroadcastReceiver mReceiver;
    public static SensorManager mSensorManager;
    public static Sensor mAccelerometer;
    static boolean isStopped;
    boolean moved;
    Vibrator v;
    static int pickedUp;
    int timer = 200;
    boolean alarmAllowed = false;
    private static final int notif_id=1000;
    private int index = 0;
    private int index2 = 0;
    private float [] histX = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            histY = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            histZ = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            histTF= new float[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            histTF2 = new float[100],
            Dif = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    float avgDif;
    float sum;
    boolean firstTime;

    public void onCreate() {
        pickedUp = 0;
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    mSensorManager.unregisterListener(DetectionService.this);
                    mSensorManager.registerListener(DetectionService.this, mAccelerometer, 50000);
                }
            }
        };
        super.onCreate();
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND);
        this.startForeground();
        isStopped = false;
        firstTime = true;
    }

    public DetectionService() {
        super("DetectionService");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND);
    }
    private void startForeground() {
        startForeground(notif_id, getMyActivityNotification("ULife is now running in the background"));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int min = rightNow.get(Calendar.MINUTE);
        if(SelfMode.getStartHour() == hour && SelfMode.getStartMinute() == min && firstTime) {
            //Toast.makeText(this, "You Picked Up Your Phone", Toast.LENGTH_SHORT).show();
            firstTime = false;
            alarmAllowed = false;
            Intent intent = new Intent(this, SelfReport.class);
            (DetectionService.this).startActivity(intent);

            //onDestroy();
        }
        final float x = event.values[0], y = event.values[1], z = event.values[2];
        final float totalForce = (float)Math.sqrt(x*x+y*y+z*z);
        histX[index] = x; histY[index] = y; histZ[index] = z; histTF[index]=totalForce; Dif[index] = Math.abs(totalForce - 10);
        index++; if (index > 19) index=0;
        for(int i = 1; i < Dif.length; i++){
            if(Math.abs(Dif[i] - Dif[i-1]) > 4){
                moved = true;
            }
            sum = sum + Dif[i];
            if(i == Dif.length-1){
                 avgDif = sum/(Dif.length-1);
                 sum = 0;
            }
        }
        if(moved && avgDif > 2.5 && alarmAllowed){
            raiseAlarm();
        }
        timer--;
        if (timer == 0){
            alarmAllowed = true;
            timer = 200;
        }
    }

    //make some sort of variable to skip an alarm
    public void raiseAlarm(){
        if(SelfMode.detectionService || GroupMode.groupDetectionService) {
            v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
            Toast.makeText(this, "You Picked Up Your Phone", Toast.LENGTH_SHORT).show();
            moved = false;
            alarmAllowed = false;
            pickedUp++;
            if(GroupMode.isGroupMode) {
                //GroupManagementService.updateUserInfo(GroupMode.getGroupID(), GroupMode.getUserID(), pickedUp);
            }
        }
    }

    private Notification getMyActivityNotification(String text){
        CharSequence title = "Detection Service";
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Notification notif = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentIntent(contentIntent).build();
        return notif;
    }

    @Override
    protected void onHandleIntent(Intent arg0) {

        if (isStopped) return;


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        String sensorVendor = mAccelerometer.getVendor();

        // when the screen is turned off, register the accelerometer receiver to make sure it works
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter, null, null);

        mSensorManager.registerListener(this, mAccelerometer, 20000);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isStopped)
            mSensorManager.unregisterListener(this, mAccelerometer);
        //unregisterReceiver(mReceiver);
    }

    public static int getPickedUp() {
        return pickedUp;
    }

}
