package com.archery.tessa.homescreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.archery.tessa.homescreen.models.Archer;
import com.archery.tessa.homescreen.tasks.CreateArcherTask;

import mqttClient.MqttMessageHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SessionActivity.class);
                startActivity(intent);
            }
        });


        /*
        To listen to the MQTT channel for sensor messages, just create this MqttMessageHandler object.
        You can get the newest message with the handler's method getNewestMessage()
        NOTE:   you must add the androidkeystore -file from our google drive's keys and certificates folder
                to app/src/main/assets. Create this directory if it doesn't exist

        MqttMessageHandler msgHandler = new MqttMessageHandler(this);
        */

        /*
        How to send archer object to server and database

        Archer testArcher = new Archer("Matti", "Ahmatti", -1, 120, 190, false);
        new CreateArcherTask(testArcher).execute(this);
        */

}}
