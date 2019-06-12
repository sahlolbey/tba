package com.tba.common.messages;

import java.util.HashMap;

public class MessageMap extends HashMap {
    public static final String MSG_TYPE="msgType";
    public static final String MSG_CREATE="create";
    public static final String MSG_MOVE="move";
    public static final String MSG_LOCATION="location";
    public static final String VEHICLE_ID =  "vehicleId";
    public static final String VEHICLE_START_LATITUDE = "startLatitude";
    public static final String VEHICLE_START_LONGITUDE = "startLongitude";
    public static final String VEHICLE_SPEED = "speed";
    public static final String VEHICLE_STATUS= "status";
    public static final String VEHICLE_DIRECTION= "dir";
    public static final String MOVE_PERIOD="movePeriod";
    public static final String TIME_STAMP="timeStamp";
    public String getMessageType(){
        return (String) this.get(MSG_TYPE);
}
}
