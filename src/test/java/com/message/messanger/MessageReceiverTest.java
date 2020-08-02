package com.message.messanger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import static org.mockito.Mockito.when;

class MessageReceiverTest {

    String queueName = "queue";
    @Mock
    private ConnectionFactory connectionFactory;
    @SpyBean
    private JmsTemplate jmsTemplate;
    @InjectMocks
    MessageReceiver messageReceiver;
    @Mock
    TextMessage textMessage;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void init() {
    }

    @Test
    void receiveMessage() throws JMSException {
        when(jmsTemplate.receive(queueName)).thenReturn(textMessage);
        when(textMessage.getText()).thenReturn("HI RAJ!");
        messageReceiver.receiveMessage();
        Mockito.verify(messageReceiver).receiveMessage();
    }
}