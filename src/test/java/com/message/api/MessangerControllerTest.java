package com.message.api;

import com.message.MessangerApplication;
import com.message.entity.Message;
import com.message.entity.MessageHistory;
import com.message.entity.UserModel;
import com.message.messanger.MessageReceiver;
import com.message.messanger.MessageSender;
import com.message.service.UserService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessangerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Mock
    UserService userService;
    @Mock
    MessageSender messageSender;
    @Mock
    MessageReceiver messageReceiver;

    UserModel userModel;
    Message message;
    UUID uuid;
    MessageHistory messageHistory;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        userModel = new UserModel();
        userModel.setName("RAJ");
        userModel.setId(UUID.randomUUID());
        message = new Message();
        message.setMessage_body("HI Raj");
        message.setReceiver(uuid.toString());
        messageHistory = new MessageHistory();
        messageHistory.setMessage_body(message.getMessage_body());
        messageHistory.setSender(uuid);
        messageHistory.setReceiver(UUID.fromString(message.getReceiver()));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        when(userService.createUser(userModel)).thenReturn(userModel);
        ResponseEntity<UserModel> postResponse = restTemplate.postForEntity(getRootUrl() + "/createAccount", userModel, UserModel.class);
        assertNotNull(postResponse.getStatusCode().CREATED);
    }

    @Test
    void send() {
        doNothing().when(messageSender).sendMessage(message);
        doNothing().when(messageReceiver).receiveMessage();
        ResponseEntity<UserModel> postResponse = restTemplate.postForEntity(getRootUrl() + "/sendMessage", message,  UserModel.class, uuid, UUID.class);
        assertNotNull(postResponse.getStatusCode().OK);
    }

    @Test
    void getMessage() {
        List<MessageHistory> messageHistories = new ArrayList<>();
        messageHistories.add(messageHistory);
        when(userService.getAllSavedMessage()).thenReturn(java.util.Optional.of(messageHistories));
        ResponseEntity<MessageHistory> postResponse = restTemplate.getForEntity(getRootUrl() + "/findAllMessage", MessageHistory.class);
        assertNotNull(postResponse.getStatusCode().OK);
    }
}