package Archery_trainer.server.models;

import java.util.List;

/**
 * Created by mkkvj on 25.1.2018.
 */

public class MeasuredDataSet {
    private long timestamp;
    private List<SensorData> sensors;

    private static final String TABLENAME = "SensorReadings";

    public static String getTableName() { return TABLENAME; }

    public List<SensorData> getSensors(){return this.sensors;}

    public MeasuredDataSet(long _timestamp, List<SensorData> _sensors) {
        timestamp = _timestamp;
        sensors = _sensors;
    }

    public long getTimestamp(){return this.timestamp;}

    public void setTimestamp(int timestamp){this.timestamp=timestamp;}
    public void setSensors(List<SensorData> sensors){this.sensors=sensors;}
    public SensorData getSensorData(int indx){return this.sensors.get(indx);}
    public String toString(){
        String result=new String("");
        for(SensorData data: sensors){
            result += data.toString() + "\n";
        }
        return result;
    }
}