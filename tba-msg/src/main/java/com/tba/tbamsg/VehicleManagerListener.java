package com.tba.tbamsg;

import com.tba.common.messages.MessageMap;
import com.tba.common.messages.MessageTransmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class VehicleManagerListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(VehicleManagerListener.class);
    @Autowired
    private AtomicInteger count = null;
    @Autowired
    MessageTransmitter messageTransmitter;
    private Map<String, Vehicle> vehicles;

    public static Integer LOCATION_REFRESH_INTERVAL;
    @Value("${vehicle.location.refereshInterval:2}")
    public void setLocationRefreshInterval(Integer locationRefreshInterval) {
        LOCATION_REFRESH_INTERVAL = locationRefreshInterval;
    }

    @Override
    public void onMessage(Message message) {
        logger.debug("onMessage started");
        if (vehicles == null)
            vehicles = new ConcurrentHashMap<>();
        if (message instanceof MapMessage) {
            try {
                String messageType = ((MapMessage) message).getString(MessageMap.MSG_TYPE);
                if (messageType.equals(MessageMap.MSG_CREATE)) {
                    logger.debug("messageType="+messageType);
                    String vehicleId=((MapMessage) message).
                            getString(MessageMap.VEHICLE_ID);
                    // check if vehicle id exist before or not
                    if(vehicles.get(vehicleId)!=null)
                    {
                        return;
                    }
                    Vehicle vehicle = new Vehicle();
                    vehicle.setVehicleId(vehicleId);
                    vehicle.setLatitude(((MapMessage) message).
                            getDouble(MessageMap.VEHICLE_START_LATITUDE));
                    vehicle.setLongitude(((MapMessage) message).
                            getDouble(MessageMap.VEHICLE_START_LONGITUDE));
                    vehicle.setTransmitter(messageTransmitter);
                    vehicles.put(vehicle.getVehicleId(), vehicle);
                    logger.info("vehicle create successfully vehicleId="+vehicle.getVehicleId());
                } else if (messageType.equals(MessageMap.MSG_MOVE)) {
                    logger.debug("messageType="+messageType);
                    String vehicleId = ((MapMessage) message).getString(MessageMap.VEHICLE_ID);
                    logger.debug("vehicleId="+vehicleId);
                    Vehicle vehicle = vehicles.get(vehicleId);
                    if(vehicle!=null) {
                        vehicle.onMessage((MapMessage) message);
                    }else {
                        logger.debug("VehicleId Not exists");
                    }
                }
            } catch (JMSException e) {
                logger.error(e.getMessage(), e);
            }


        }
        logger.debug("finished onMessage");
    }

    public Map<String, Vehicle> getVehicles() {
        return vehicles;
    }
}
