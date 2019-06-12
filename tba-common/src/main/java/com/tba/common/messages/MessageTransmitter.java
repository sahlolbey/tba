package com.tba.common.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Iterator;
import java.util.Map;

@Component
public class MessageTransmitter {
    protected static final String MSG_COUNT = "messageCount";
    @Autowired
    private JmsTemplate jmsTemplate = null;


    public void sendMessage(MessageMap userMessage){
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return convertMessageMapToMapMessage(session.createMapMessage(), userMessage);
            }
        });
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    public static MapMessage convertMessageMapToMapMessage(MapMessage mapMessage,MessageMap userMessage) throws JMSException {
        Iterator<Map.Entry<String,Object>> iterator = userMessage.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Object> entry =iterator.next();
            if (entry.getValue() instanceof Double){
                mapMessage.setDouble(entry.getKey(), (Double) entry.getValue());
            }else if (entry.getValue() instanceof String){
                mapMessage.setString(entry.getKey(), (String) entry.getValue());
            }else if (entry.getValue() instanceof Integer){
                mapMessage.setInt(entry.getKey(), (Integer) entry.getValue());
            }
        }
        return mapMessage;
    }
}
