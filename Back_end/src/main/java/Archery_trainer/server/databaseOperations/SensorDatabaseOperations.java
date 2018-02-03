package Archery_trainer.server.databaseOperations;

import Archery_trainer.server.Main;
import Archery_trainer.server.models.MeasuredDataSet;
import Archery_trainer.server.models.SensorData;

import java.sql.*;

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
            pstmt.setDate(1, new Date(set.getTimestamp()));
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
}
