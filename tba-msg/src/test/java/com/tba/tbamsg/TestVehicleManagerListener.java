package com.tba.tbamsg;

import com.tba.common.messages.MessageFactory;
import com.tba.common.messages.MessageMap;
import com.tba.common.messages.MessageTransmitter;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVehicleManagerListener {
    @Autowired
    VehicleManagerListener vehicleManagerListener;
    @Autowired
    JmsTemplate jmsTemplate;
    @Test
    public void testOnMessage() {
        try {
            //  Testing create command
            MessageMap messageMap = MessageFactory.
                    createVehicleCreateMessage("x3", 30, 40);
            MapMessage mapMessage=  MessageTransmitter.
                    convertMessageMapToMapMessage(new ActiveMQMapMessage(), messageMap);

            vehicleManagerListener.onMessage(mapMessage);
            Vehicle vehicle= vehicleManagerListener.getVehicles().get("x3");
            Assert.assertEquals("x3", vehicle.getVehicleId());
            Assert.assertEquals(30, vehicle.getLatitude(),0.0022);
            Assert.assertEquals(40, vehicle.getLongitude(),0.0022);

            // testing move command
            messageMap = MessageFactory.createMovementMessage("x3",
                    10.0,10 , 60.0);

            mapMessage = MessageTransmitter.convertMessageMapToMapMessage(
                    new ActiveMQMapMessage(),messageMap);
            vehicleManagerListener.onMessage(mapMessage);
            // waiting for that vehicle movement period finishes
            Thread.sleep(12000);
            vehicle = vehicleManagerListener.getVehicles().get("x3");
            Assert.assertEquals(54, vehicle.getLatitude(),.9999);
            Assert.assertEquals(53, vehicle.getLongitude(),.9999);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
