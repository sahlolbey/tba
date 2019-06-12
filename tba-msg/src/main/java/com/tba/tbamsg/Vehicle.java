package com.tba.tbamsg;

import com.tba.common.messages.MessageFactory;
import com.tba.common.messages.MessageMap;
import com.tba.common.messages.MessageTransmitter;
import com.tba.common.util.ArithmeticUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The use to simulate vehicle. It has and inner class name Engine which is actually a thread to simulate movement when
 * requested.
 */
public class Vehicle {
    private static final Logger logger = LoggerFactory.getLogger(Vehicle.class);
    private String vehicleId;
    private double latitude;
    private double longitude;
    // angle in degree between 0 to 360
    private double direction;
    // speed is in KH
    private double speed;
    private String status;
    private int timePeriod;
    MessageTransmitter transmitter;
    Engine engine;


    public void onMessage(MapMessage message) throws JMSException {

        direction = ((MapMessage) message).getDouble(MessageMap.VEHICLE_DIRECTION);
        logger.debug("direction="+direction);
        speed = ((MapMessage) message).getDouble(MessageMap.VEHICLE_SPEED);
        logger.debug("speed="+speed);
        timePeriod = ((MapMessage) message).getInt(MessageMap.MOVE_PERIOD);
        logger.debug("movePeriod="+timePeriod);
        try {
            // ignoring the move command if the vehicle is already moving
           if(isMoving()){
                return;
            }
            if (((MapMessage) message).getString(MessageMap.MSG_TYPE).equals(MessageMap.MSG_MOVE)) {
                // instantiate a new engine and ask it to move the vehicle
                engine = new Engine();
                engine.start();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

    }
    private boolean isMoving(){
        if (engine==null || !engine.isAlive())
            return false;
        return true;
    }

    /**
     * This class is instantiated when the vehicle move requested by user.
     */
    class Engine extends Thread {
        @Override
        public void run() {
            logger.debug("Vehicle id=" + getVehicleId() + " started ....");
            logger.debug("timePeriod=" + timePeriod);

            int refreshInterval = Integer.valueOf(VehicleManagerListener.LOCATION_REFRESH_INTERVAL);
            while (timePeriod > 0) {
                logger.debug("refreshInterval=" + refreshInterval);
                timePeriod -= refreshInterval;
                logger.debug("remaining period=" + timePeriod);
                status = "moving";
                try {
                    sleep(refreshInterval * 1000);
                } catch(InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
                longitude = ArithmeticUtil.calculateXcoordinate(longitude, speed, direction, refreshInterval);
                latitude = ArithmeticUtil.calculateYcoordinate(latitude, speed, direction, refreshInterval);
                logger.debug("vehicleId=" + getVehicleId() + " x=" + longitude + " y=" + latitude);

                sendLocation();
            }
            status = "stop";
            sendLocation();
            logger.debug("vehicle stopped!");

        }
    }
   public String getVehicleId() {
        return vehicleId;
    }

   public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

   private void sendLocation() {
       logger.debug("sending vehicle location to user...");
       LocalDateTime timeStamp = LocalDateTime.now();
       DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

       transmitter.sendMessage(MessageFactory.createLocationMessage(
               vehicleId,latitude,longitude,speed,direction,status,timeStamp.format(dateFormat)));
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setTransmitter(MessageTransmitter transmitter) {
        this.transmitter = transmitter;
    }
}