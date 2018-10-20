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


public class DetectionService extends IntentService implements SensorEventListener {

    public BroadcastReceiver mReceiver;
    public SensorManager mSensorManager;
    public Sensor mAccelerometer;
    boolean isStopped;
    private static final int notif_id=1000;
    private int index = 0;
    private float [] histX = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            histY = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            histZ = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            histTF= new float[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

    public void onCreate() {
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
        final float x = event.values[0], y = event.values[1], z = event.values[2];
        final float totalForce = (float)Math.sqrt(x*x+y*y+z*z);

        histX[index] = x; histY[index] = y; histZ[index] = z; histTF[index]=totalForce;
        index++; if (index > 19) index=0;

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

}
