package Archery_trainer.server;

import Archery_trainer.mqttClient.AddToCollectionCallback;
import Archery_trainer.mqttClient.MqttMessageHandler;
import Archery_trainer.server.databaseOperations.SensorDatabaseOperations;
import Archery_trainer.server.databaseOperations.ShotDatabaseOperations;
import Archery_trainer.server.models.MeasuredDataSet;
import Archery_trainer.server.models.RecordingRequest;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.lang.Thread;

public class Recording {

    private static MqttMessageHandler messageHandler;

    private static String archerId;
    private static long timestamp;

    /**
     * Start recording the MQTT messages
     * @param _archerEmail   The email address of the archer to record
     * @param _timestamp     Starting time of the recording
     *
     * @return  true if starting was successful, otherwise false
     */
    public static boolean startRecording(String _archerEmail, long _timestamp) {

	    System.out.println("Archer id: " + _archerEmail);

        archerId = _archerEmail;
        timestamp = _timestamp;

        //Start MQTT-client
        if (messageHandler == null) {
            System.out.println("Creating new message handler");
            messageHandler = new MqttMessageHandler();
        }

        return true;
    }

    /**
     * Stop the recording, clear the messages list and get the recorded messages
     *
     * @return  Id of the Shot that was stored in the database. -1 for failure
     */
    public static int stopRecording() {

        System.out.println("Stopping recording thread");
        System.out.println("Archer id: "+ archerId);

        int shotId = -1;
        if(messageHandler == null) //Recording not started
		    return shotId;

        //We need to create a 'Shot' in the database first
        try {
            shotId = ShotDatabaseOperations.insertShot(archerId, timestamp);
        } catch (SQLException e) {
            System.out.println("Unable to create Shot");
            e.printStackTrace();
            messageHandler.disconnect();
            messageHandler.cleanUp();
            messageHandler = null;
            return shotId;
        }

        //Need to disconnect before getting messages so that messages are not added and read at the same time
        messageHandler.disconnect();
        List<String> messages = messageHandler.getMessages();

        System.out.println("Got " + messages.size() + " messages");
        Gson gson = new Gson();
        for(String msg : messages) {

            MeasuredDataSet measData = gson.fromJson(msg, MeasuredDataSet.class);

            try {
                SensorDatabaseOperations.insertMeasuredDataSet(measData, shotId);
            } catch (SQLException e) {
                System.out.println("Unable to store measurement to database");
                e.printStackTrace();
                messageHandler.cleanUp();
                messageHandler.disconnect();
                messageHandler = null;
            }
        }

        //Reset state
        archerId = "";
        shotId = -1;
        timestamp = 0;

        messageHandler.cleanUp();
        messageHandler = null;


        return shotId;
    }


}
