package com.message.messanger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class MessageReceiver {

    String queueName = "queue";
    @Autowired
    private ConnectionFactory connectionFactory;
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() {
        this.jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public void receiveMessage() {
        Message message = jmsTemplate.receive(queueName);
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println("received: " + text);
        } catch (JMSException e) {
            e.getMessage();
        }
    }
}
