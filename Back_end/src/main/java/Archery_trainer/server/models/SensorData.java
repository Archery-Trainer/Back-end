package Archery_trainer.server.models;

/**
 * A single measurement from a sensor
 *
 * Created by mkkvj on 25.1.2018.
 */
public class SensorData {
    private int sensorId;
    private int value;

    public SensorData(int sensorId, int value){
        this.sensorId=sensorId;
        this.value=value;
    }
    public void setValue(int val){this.value=val;}
    public int getSensorId(){return this.sensorId;}
    public int getValue(){return this.value;}

    public String toString(){
        return Integer.toString(this.sensorId) + ":" + Integer.toString(this.value);
    }
}
