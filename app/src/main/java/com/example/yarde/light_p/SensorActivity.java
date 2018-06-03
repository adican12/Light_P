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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.example.yarde.light_p.helpers.MQTTHelper;

//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
//import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SensorActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    Sensor sensor;
    Sensor sensorGPS;
    TextView textView;  //text view for result
    TextView dataReceived;
    //FirebaseDatabase mRef;

    //MQTTHelper mqttHelper;
    private Button Report_data;


    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  //accses to senspr manger
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);   // choose sensor
        sensorGPS =mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);   // choose sensor




        textView = (TextView) findViewById(R.id.textView);  // show deatils
        dataReceived = (TextView) findViewById(R.id.dataReceived);

        //mRef = new FirebaseDatabase("https://lightp-ef920.firebaseio.com/");
        Report_data = (Button) findViewById(R.id.repord_data);

  //      startMqtt();
    }



//    private void startMqtt() {
//        mqttHelper = new MQTTHelper(getApplicationContext());
//        mqttHelper.setCallback(new MqttCallbackExtended() {
//            @Override
//            public void connectComplete(boolean b, String s) {
//
//            }
//
//            @Override
//            public void connectionLost(Throwable throwable) {
//
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
//                Log.w("Debug", mqttMessage.toString());
//                dataReceived.setText(mqttMessage.toString());
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//
//            }
//        });
//

//    }



    @Override
    public void onSensorChanged(SensorEvent event) {  //check changed
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            textView.setText("" + event.values[0]);  //update text view
        }
        if (event.sensor.getType() == sensorGPS.TYPE_MAGNETIC_FIELD) {
            dataReceived.setText("" + event.values[0]);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    protected void onResume() {
//        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensorGPS, SensorManager.SENSOR_DELAY_NORMAL);

    }

    //
//    @Override
    protected void onPause() {
//        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }



    public void onClick(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();//work version
        DatabaseReference myRef = database.getReference("message");//work version
        //DatabaseReference myRef1 = database.getReference("message1");//work version


        myRef.setValue(textView.getText());
        //myRef1.setValue(textView.getText());

    }
}

