package com.tba.tbaweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ImportResource("classpath:messaging-config.xml")
@ComponentScan(basePackages = "com.tba.common.messages")
@ComponentScan(basePackages = "com.tba.tbaweb.websocket")
public class MessagingConfig {

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory jmsFactory, Destination destination){
       JmsTemplate jmsTemplate = new JmsTemplate(jmsFactory);
       jmsTemplate.setDefaultDestination(destination);
        return jmsTemplate;
    }
  /*  @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/event-emitter", webSocketHandler);

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }
    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
*/
}
