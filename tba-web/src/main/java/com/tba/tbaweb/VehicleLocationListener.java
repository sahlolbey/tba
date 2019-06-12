package com.tba.tbaweb;

import com.tba.common.messages.MessageFactory;
import com.tba.common.messages.MessageMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class VehicleLocationListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(VehicleLocationListener.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            try {
                String messageType = ((MapMessage) message).getString(MessageMap.MSG_TYPE);
                logger.info("messageType=" + messageType);
                String vehicleId = ((MapMessage) message).getString(MessageMap.VEHICLE_ID);
                logger.info("vehicleId=" + vehicleId);
                MessageMap messageMap = this.getMessageMap((MapMessage) message);

                simpMessagingTemplate.convertAndSend("/topic/greetings", messageMap);

            } catch (JMSException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private MessageMap getMessageMap(MapMessage message) throws JMSException {
        return MessageFactory.createLocationMessage(
                message.getString(MessageMap.VEHICLE_ID),
                message.getDouble(MessageMap.VEHICLE_START_LATITUDE),
                message.getDouble(MessageMap.VEHICLE_START_LONGITUDE),
                message.getDouble(MessageMap.VEHICLE_SPEED),
                message.getDouble(MessageMap.VEHICLE_DIRECTION),
                message.getString(MessageMap.VEHICLE_STATUS),
                message.getString(MessageMap.TIME_STAMP));
    }
}
