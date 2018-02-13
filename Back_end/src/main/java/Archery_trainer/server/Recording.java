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
    private static List<String> messageList = new LinkedList<>();

    private static String archerId;
    private static long timestamp;

    //Task that will store the mqtt messages to messageList
    private static final Runnable recordingTask = new Runnable() {
        @Override
        public void run() {

            System.out.println("Starting recording thread");

            AddToCollectionCallback addToList = new AddToCollectionCallback(messageList);

        }
    };

    //Thread that will run recorderTask and can be interrupted
    private static Thread recordingThread;


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
        if (messageHandler == null)
            messageHandler = new MqttMessageHandler();

	    recordingThread = new Thread(recordingTask);
        recordingThread.start();

        return true;
    }

    /**
     * Stop the recording, clear the messages list and get the recorded messages
     *
     * @return  List of the messages saved during recording
     */
    public static List<String> stopRecording() {

        System.out.println("Stopping recording thread");
	System.out.println("Archer id: "+ archerId);

	if(recordingThread == null) //Recording not started
		return new LinkedList<String>();

        recordingThread.interrupt();

        //We need to create a 'Shot' in the database first
        int shotId = -1;
        try {
            shotId = ShotDatabaseOperations.insertShot(archerId, timestamp);
        } catch (SQLException e) {
            System.out.println("Unable to create Shot");
            e.printStackTrace();
            return null;
        }

        List<String> messages = messageList;

        // FOR DEBUGGING IF THERE ARE NO REAL MQTT MESSAGES
        // Date date = new Date();
        // messages.add("{'timestamp': ' " + date.getTime() + "', 'sensors':  [{'sensorId': '1' , 'value': '404'}, {'sensorId': '2' , 'value': '505'}]}");

        Gson gson = new Gson();
        for(String msg : messages) {
            //Storing measurements to database:

            System.out.println("Message: " + msg);

            MeasuredDataSet measData = gson.fromJson(msg, MeasuredDataSet.class);

            try {
                SensorDatabaseOperations.insertMeasuredDataSet(measData, shotId);
            } catch (SQLException e) {
                System.out.println("Unable to store measurement to database");
                e.printStackTrace();
            }
        }

        //Reset state
        archerId = "";
        shotId = -1;
        timestamp = 0;

        messageList.clear();

        return messages;
    }


}
