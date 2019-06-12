package com.tba.common.messages;

public class MessageFactory{
    /**
     * This method creates a message to be sent to server to notify the vehicle for movement
     * @param vehicleId
     * @param speedKH : speed of vehicle in kilommeter per hour unit
     * @param period : period of movement in seconds
     * @param direction
     * @return
     */
    public static MessageMap createMovementMessage(String vehicleId, Double speedKH, Integer period, Double direction
                                                   ){
        MessageMap messageMap = new MessageMap();
        messageMap.put(MessageMap.MSG_TYPE, MessageMap.MSG_MOVE);
        messageMap.put(MessageMap.VEHICLE_ID,vehicleId);
        messageMap.put(MessageMap.MOVE_PERIOD,period);
        messageMap.put(MessageMap.VEHICLE_SPEED,speedKH);
        messageMap.put(MessageMap.VEHICLE_DIRECTION, direction);

        return messageMap;
    }
    public static MessageMap createVehicleCreateMessage(String vehicleId,double latitude,double longitude){
        MessageMap messageMap = new MessageMap();
        messageMap.put(MessageMap.MSG_TYPE, MessageMap.MSG_CREATE);
        messageMap.put(MessageMap.VEHICLE_ID,vehicleId);
        messageMap.put(MessageMap.VEHICLE_START_LATITUDE,Double.valueOf(latitude));
        messageMap.put(MessageMap.VEHICLE_START_LONGITUDE,Double.valueOf(longitude));
        return messageMap;
    }
    /**
     * Creates  a location message to be sent  to user
     * @param vehicleId
     * @param latitude
     * @param longitude
     * @param speed
     * @param direction
     * @param status
     * @param timeStamp
     * @return
     */
    public static MessageMap createLocationMessage(String vehicleId,
                                                   Double latitude,
                                                   Double longitude,
                                                   Double speed,
                                                   Double direction,
                                                   String status,
                                                   String timeStamp){
        MessageMap message = new MessageMap();
        message.put(MessageMap.MSG_TYPE,MessageMap.MSG_LOCATION);
        message.put(MessageMap.VEHICLE_ID,vehicleId);
        message.put(MessageMap.VEHICLE_START_LATITUDE, Double.valueOf(latitude));
        message.put(MessageMap.VEHICLE_START_LONGITUDE,Double.valueOf(longitude));
        message.put(MessageMap.VEHICLE_SPEED, Double.valueOf(speed));
        message.put(MessageMap.VEHICLE_DIRECTION, Double.valueOf(direction));
        message.put(MessageMap.VEHICLE_STATUS, status);
        message.put(MessageMap.TIME_STAMP, timeStamp);
        return message;
    }

}
