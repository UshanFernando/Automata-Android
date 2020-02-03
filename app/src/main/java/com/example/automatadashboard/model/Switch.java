package com.example.automatadashboard.model;

public class Switch {

    public static String SWITCH_STATUS_ON = "on";
    public static String SWITCH_STATUS_OFF = "off";

    public static String SWITCH_TYPE_LIGHT_BULB = "Light Bulb";
    public static String SWITCH_TYPE_FAN = "Fan";
    public static String SWITCH_TYPE_WALL_PLUG = "Wall Plug";
    public static String SWITCH_TYPE_AC = "Air Conditioner";
    public static String SWITCH_TYPE_ANALOG = "Analog Controller";

    private String switchId;
    private String switchName;
    private String switchStatus;
    private String switchType;

    public Switch() {
    }

    public Switch(String switchId, String switchName, String switchStatus, String switchType) {
        this.switchId = switchId;
        this.switchName = switchName;
        this.switchStatus = switchStatus;
        this.switchType = switchType;
    }

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }
}
