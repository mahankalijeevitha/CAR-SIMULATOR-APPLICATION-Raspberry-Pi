package com.example.mahan.finalcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    MqttHelper mqttHelper;
    MqttHelper1 mqttHelper1;
    MqttHelper2 mqttHelper2;

    private Button startstop, acc, bre, right, left;
    TextView carm, carj, carp;
    boolean set_start=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onclickStartStopListener();
    }
    public void onclickStartStopListener() {
        startstop = (Button) findViewById(R.id.startstop);
        acc = (Button) findViewById(R.id.accelerate);
        bre = (Button) findViewById(R.id.brake);
        right = (Button) findViewById(R.id.right);
        left = (Button) findViewById(R.id.left);
        carm = (TextView) findViewById(R.id.carm);
        carj = (TextView) findViewById(R.id.carj);
        carp = (TextView) findViewById(R.id.carp);
        startstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_start = !set_start;
                if (set_start) {
                    onclickAccListener();
                    onclickRightListener();
                    onclickLeftListener();
                    onclickBreakListener();
                    //Log.w("Debug", "x " + carj.getX() + "y " + carj.getY());
                    startMqtt();
                    startMqtt1();
                    startMqtt2();
                }
            }
        });
    }

    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());

        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Toast.makeText(MainActivity.this, "connection complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", topic + " " + mqttMessage.toString());
                //Toast.makeText(MainActivity.this, mqttMessage.toString(), Toast.LENGTH_SHORT).show();
                //dataReceived.setText(mqttMessage.toString());
                String msg[] = mqttMessage.toString().split(";");
                if (msg[0].equalsIgnoreCase("j")) {
                    carj.setX(Float.parseFloat(msg[1]));
                    carj.setY(Float.parseFloat(msg[2]));
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
    private void startMqtt1() {
        mqttHelper1 = new MqttHelper1(getApplicationContext());

        mqttHelper1.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", topic + " " + mqttMessage.toString());
                //Toast.makeText(MainActivity.this, mqttMessage.toString(), Toast.LENGTH_SHORT).show();
                //dataReceived.setText(mqttMessage.toString());
                String msg[] = mqttMessage.toString().split(";");
                if (msg[0].equalsIgnoreCase("m")) {
                    carm.setX(Float.parseFloat(msg[1]));
                    carm.setY(Float.parseFloat(msg[2]));
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
    private void startMqtt2() {
        mqttHelper2 = new MqttHelper2(getApplicationContext());

        mqttHelper2.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", topic + " " + mqttMessage.toString());
                //Toast.makeText(MainActivity.this, mqttMessage.toString(), Toast.LENGTH_SHORT).show();
                //dataReceived.setText(mqttMessage.toString());
                String msg[] = mqttMessage.toString().split(";");
                if (msg[0].equalsIgnoreCase("p")) {
                    carp.setX(Float.parseFloat(msg[1]));
                    carp.setY(Float.parseFloat(msg[2]));
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
    public void onclickAccListener()
    {
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publishMessage("jj;3;0");
            }
        });

    }
    public void onclickBreakListener()
    {
        bre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publishMessage("jj;-5;0");
            }
        });

    }
    public void onclickRightListener()
    {
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publishMessage("jj;0;1");
            }
        });

    }
    public void onclickLeftListener()
    {
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.publishMessage("jj;0;-1");
            }
        });

    }
}

