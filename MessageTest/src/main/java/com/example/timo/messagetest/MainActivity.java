package com.example.timo.messagetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mqttClient.MqttClient;
import mqttClient.MqttMessageHandler;

/*
 * This small app sends HTTP requests to the server running in a virtual machine, and creates
 * Message objects based on the recieved JSON-file.
 *
 * The Message and MessageType classes need to be identical (atleast consrtuctors) to those in the
 * server app.
 *
 *
 * To run this you will need to at least add this to build.gradle:
 *
   compile 'org.springframework.android:spring-android-rest-template:1.0.1.RELEASE'
   compile 'com.fasterxml.jackson.core:jackson-databind:2.3.2'
 *
 * and to AndroidManifest.xml:
 *
   <uses-permission android:name="android.permission.INTERNET" />
 *
 */




public class MainActivity extends AppCompatActivity{

    private final int REQUEST_INTERVAL_MS = 5000;
    private MqttMessageHandler mqttHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //mqttHandler = new MqttMessageHandler(this);

        //listenMQTT();


        //Test http requests
        while(true) {
            try {
                Thread.sleep(REQUEST_INTERVAL_MS);
            } catch (Exception e) {}

            new CreateArcherTask().execute();
        }

    }

    private void listenMQTT() {

        while(true) {
            String s = MqttMessageHandler.getNewestMessage();

            System.out.println(s);
        }

    }

}