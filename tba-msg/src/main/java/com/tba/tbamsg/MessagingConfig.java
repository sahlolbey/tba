package com.tba.tbamsg;

import org.springframework.context.annotation.*;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
@ImportResource("classpath:vehicle-manager.xml")
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.tba.common.messages")
public class MessagingConfig {
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory jmsFactory, Destination webDestination){
        JmsTemplate jmsTemplate = new JmsTemplate(jmsFactory);
        jmsTemplate.setDefaultDestination(webDestination);
        return jmsTemplate;
    }
}
