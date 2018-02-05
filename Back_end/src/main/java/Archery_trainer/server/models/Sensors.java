package Archery_trainer.server.models;

public enum Sensors {
    SENSOR_LTRAP,
    SENSOR_RTRAP,
    SENSOR_LDELT,
    SENSOR_RDELT,
    SENSOR_LTRIC,
    SENSOR_RTRIC;

    public static final int NUM_SENSORS = 6;

    public static String getSensorName(int sensor_id) {
        return Sensors.values()[sensor_id].name();
    }
}


