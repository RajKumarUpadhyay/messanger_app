package com.message.messanger;

import com.message.api.MessangerController;
import com.message.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

@Component
public class MessageSender {

    private static final Logger log = LoggerFactory.getLogger(MessangerController.class);

    String queueName = "queue";
    @Autowired
    private ConnectionFactory connectionFactory;
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() {
        this.jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public void sendMessage(Message message) {
        log.info("sending: " + message);
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public javax.jms.Message createMessage(Session session) throws JMSException {
                return (javax.jms.Message) session.createTextMessage(String.valueOf(message));
            }
        });
    }
}
