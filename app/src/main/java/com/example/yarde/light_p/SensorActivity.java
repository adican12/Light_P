package com.example.yarde.light_p;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yarde.light_p.Light_program.Data;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SensorActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    Sensor sensor;
    Sensor sensorGPS;
    float mLightSensor;

    LocationManager locationManager;
    Location loc;
    double longitude, altitude ;

    TextView textView;  //text view for result
    TextView dataReceived;


    private DatabaseReference mDatabase;
    int i = 0;


    private Button Report_data;


    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  //accses to senspr manger
//        sensorGPS = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);   // choose sensor
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);   // choose sensor

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

        textView = (TextView) findViewById(R.id.textView);  // show deatils
        dataReceived = (TextView) findViewById(R.id.dataReceived);

        Report_data = (Button) findViewById(R.id.repord_data);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {  //check changed
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            textView.setText("" + event.values[0]);  //update text view

        }
//        if (event.sensor.getType() == sensorGPS.TYPE_MAGNETIC_FIELD) {
//            dataReceived.setText("" + event.values[0]);
//        }


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
//        mSensorManager.registerListener(this, sensorGPS, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    public void onClick(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //
        // require premission to check location services
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = loc.getLongitude();
        altitude = loc.getLatitude();

        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);   // choose sensor
        mLightSensor=sensor.getPower();

        Data _data = new Data(mLightSensor);
//        Data _data = new Data(mLightSensor,longitude,altitude );
        mDatabase.child("Data").child(String.valueOf(i++)).setValue(_data);

    }
}

