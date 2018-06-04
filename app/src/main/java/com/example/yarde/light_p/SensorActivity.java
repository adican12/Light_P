package com.example.yarde.light_p;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;


public class SensorActivity extends Activity implements SensorEventListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private SensorManager mSensorManager;
    Sensor sensor;
    float mLightSensor;
    int i = 0;
    TextView textView;                                                              //text view for result
    TextView dataReceived;
    Button Report_data;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);                   // Obtain the FirebaseAnalytics instance.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  //accses to senspr manger
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);                // choose sensor
        textView = (TextView) findViewById(R.id.textView);                          // show deatils
        dataReceived = (TextView) findViewById(R.id.dataReceived);                  // show deatils
        Report_data = (Button) findViewById(R.id.repord_data);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {                                //check changed
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            textView.setText("" + event.values[0]);                                 //update text view
            mLightSensor=event.values[0];                                           // update mLightSensor var
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onClick(View view) {
        dataReceived.setText(""+Float.toString(mLightSensor));                          // update dataReceived textView
        Bundle bundle = new Bundle();                                                   // new bundle
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Integer.toString(i++) );      // enter id
        bundle.putString(FirebaseAnalytics.Param.VALUE, Float.toString(mLightSensor));  // enter the Lux meter
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);    // send to firebase bundle
    }
}