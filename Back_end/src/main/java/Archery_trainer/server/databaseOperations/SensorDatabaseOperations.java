package Archery_trainer.server.databaseOperations;

import Archery_trainer.server.Main;
import Archery_trainer.server.models.MeasuredDataSet;
import Archery_trainer.server.models.SensorData;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SensorDatabaseOperations {

    public static void insertMeasuredDataSet(MeasuredDataSet set, int shotId) throws SQLException{

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());


        int i = 1;
        for(SensorData dataItem : set.getSensors()) {

            String query = "INSERT INTO " + MeasuredDataSet.getTableName() +
                    " (Timestamp, Value, SensorID, ShotID) VALUES (?, ?, ?, ?)";

            //Create statement and set the values
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, new Timestamp(set.getTimestamp()));
            pstmt.setInt(2, dataItem.getValue());

            //@TODO This should be handled with enums, currently the id is string in the model and int in the database
            pstmt.setInt(3, i);

            pstmt.setInt(4, shotId);

            pstmt.execute();

            i++;
        }

        System.out.println("Stored sensor data with timestamp " + (set.getTimestamp()));

        conn.close();
    }

    /**
     * Determine whether a sensor can belong in a list of SensorData objects.
     * It cannot if there are already 6 sensors in the list or two sensors with the same id
     *
     * @param list
     * @param sensorId
     * @return
     */
    private static boolean okToAddSensorDataToList(List<SensorData> list, int sensorId) {
        if(list.size() >= 6)
            return false;

        for(SensorData s : list) {
            if(s.getSensorId() == sensorId)
                return false;
        }

        return true;
    }


    public static List<MeasuredDataSet> getMeasuredDataSetsOfShot(int shotId) throws SQLException {
        List<MeasuredDataSet> readings = new LinkedList<>();

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());


        String query = "SELECT * FROM " + MeasuredDataSet.getTableName() +
                " WHERE ShotID=" + shotId + ";";

        System.out.println("Fetching all measurements from shot " + shotId);

        PreparedStatement pstmt = conn.prepareStatement(query);

        ResultSet res = pstmt.executeQuery();

        if(res.wasNull()) {
            conn.close();
            res.close();

            return readings;
        }


        List<SensorData> sensors = new LinkedList<>();
        long timestamp = 0;
        while(res.next()) {
            int sensorId = res.getInt(4);

            //If sensors already contains more than 6 entries or two entries with the same sensorId,
            // we have gathered data from two measurement sets.
            if(!okToAddSensorDataToList(sensors, sensorId)) {
                //Add the gathered data into readings list and clear sensors list
                timestamp = res.getLong(2);
                MeasuredDataSet measurement = new MeasuredDataSet(timestamp, sensors);
                readings.add(measurement);

                sensors.clear();
            }

            sensors.add(new SensorData(
                    res.getInt(4), //Sensor id
                    res.getInt(3)  //Sensor value
            ));
        }

        //Data left in sensors that is not added to measurements:
        if(!sensors.isEmpty()) {
            MeasuredDataSet measurement = new MeasuredDataSet(timestamp, sensors);
            readings.add(measurement);
        }


        conn.close();
        res.close();

        return readings;

    }
}
