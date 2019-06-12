package com.tba.common.messages;

/**
 * This a Factory class for creation of different messages communicated between component of system
 */


public class MessageFactory{
    /**
     * This method creates a message to be sent to Vehicle Simulation server to notify the vehicle for movement
     * @param vehicleId :Name of Vehicle
     * @param speedKH : speed of vehicle in kilommeter per hour unit
     * @param period : period of movement in seconds
     * @param direction: direction of movement. It's an angle in degree unit and angle is to horizontal axis
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

    /**
     * This method creates a message to be sent to Vehicle Simulation server to instantiate a new vehicle
     * @param vehicleId::Name of Vehicle
     * @param latitude: initial Y-coordinate of vehicle
     * @param longitude: initial X-coordinate of vehicle
     * @return
     */
    public static MessageMap createVehicleCreateMessage(String vehicleId,double latitude,double longitude){
        MessageMap messageMap = new MessageMap();
        messageMap.put(MessageMap.MSG_TYPE, MessageMap.MSG_CREATE);
        messageMap.put(MessageMap.VEHICLE_ID,vehicleId);
        messageMap.put(MessageMap.VEHICLE_START_LATITUDE,Double.valueOf(latitude));
        messageMap.put(MessageMap.VEHICLE_START_LONGITUDE,Double.valueOf(longitude));
        return messageMap;
    }
    /**
     * Creates  a location message to be sent  to Terminal Web Application
     * @param vehicleId: name of vehicle
     * @param latitude: Y-coordinate of vehicle
     * @param longitude: X-coordinate of vehicle
     * @param speed: speed of vehicle in KH unit.
     * @param direction: direction of movement. It's an angle in degree unit and angle is to horizontal axis
     * @param status: current status of vehicle moving on stoped.
     * @param timeStamp: the date and time sampled location
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
