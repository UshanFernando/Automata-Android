package com.example.automatadashboard.model;

public class Sensor {
    public static final String SENSOR_TYPE_TEMP = "temp";
    public static final String SENSOR_TYPE_HUMIDITY = "hum";
    public static final String SENSOR_TYPE_AIR_QUALITY = "air";
    public static final String SENSOR_TYPE_PEOPLE = "people";

    private String sensorId;
    private String sensorName;
    private String sensorType;
    private String sensorValue;
    private String sensorSign;


    public Sensor() {
    }

    public Sensor(String sensorName, String sensorValue, String sensorType) {
        this.sensorType = sensorType;
        this.sensorValue = sensorValue;
        if (sensorType == Sensor.SENSOR_TYPE_TEMP)
            this.sensorSign = "°C";
        else if (sensorType == Sensor.SENSOR_TYPE_HUMIDITY)
            this.sensorSign = "%";
        else if (sensorType == Sensor.SENSOR_TYPE_AIR_QUALITY)
            this.sensorSign = "%";
        else if (sensorType == Sensor.SENSOR_TYPE_PEOPLE)
            this.sensorSign = "inside";

        this.sensorName = sensorName;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getSensorSign() {
        return sensorSign;
    }

    public void setSensorSign(String sensorSign) {
        this.sensorSign = sensorSign;
    }
}
