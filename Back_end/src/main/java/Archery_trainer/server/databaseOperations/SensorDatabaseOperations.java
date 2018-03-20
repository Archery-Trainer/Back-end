package Archery_trainer.server.databaseOperations;

import Archery_trainer.server.Main;
import Archery_trainer.server.models.MeasuredDataSet;
import Archery_trainer.server.models.SensorData;
import Archery_trainer.server.models.Sensors;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SensorDatabaseOperations {

    /**
     * Insert sensor data to a shot in the database
     *
     * @param set       The sensor values
     * @param shotId    The corresponding shot id
     * @throws SQLException
     */
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
            pstmt.setLong(1, set.getTimestamp());
            pstmt.setInt(2, dataItem.getValue());

            //@TODO This should be handled with enums, currently the id is string in the model and int in the database
            pstmt.setInt(3, i);

            pstmt.setInt(4, shotId);

            pstmt.execute();

            i++;
        }
      
        conn.close();
    }


    /**
     * Get sensor data of a single shot
     *
     * @param shotId    The shot whose data to set
     * @return          The sensor data
     * @throws SQLException
     */
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
        while(res.next()) {

            int sensorId = res.getInt(4);
            int sensorVal = res.getInt(3);

            sensors.add(new SensorData(
                    sensorId,
                    sensorVal
            ));

            if(sensorId == Sensors.NUM_SENSORS) {
                //We have gathered one set of sensor values, create a MeasuredDataSet from them

                if (sensors != null && !sensors.isEmpty()) {
                    long timestamp = res.getLong(1);
                    MeasuredDataSet measurement = new MeasuredDataSet(timestamp, sensors);
                    readings.add(measurement);
                }

                //Start gathering new set
                sensors = new LinkedList<>();
            }
        }

        conn.close();
        res.close();

        return readings;

    }
}
