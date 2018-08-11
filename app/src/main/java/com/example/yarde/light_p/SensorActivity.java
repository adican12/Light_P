package com.example.yarde.light_p;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class SensorActivity extends Activity implements SensorEventListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private SensorManager mSensorManager;
    Sensor sensor;
    float mLightSensor;
    int i = 0;
    TextView textView;                                                              //text view for result
    TextView dataReceived;
    Button Report_data;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        // Create a new sensorRead with a name and read
        Map<String, Object> sensorRead = new HashMap<>();
        sensorRead.put("name", "yarden");
        sensorRead .put("value", Float.toString(mLightSensor));

        // Add a new document with a generated ID
        db.collection("users")
                .add(sensorRead )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}