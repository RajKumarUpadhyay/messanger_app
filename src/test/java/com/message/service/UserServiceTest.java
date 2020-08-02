package com.message.service;

import com.message.entity.Message;
import com.message.entity.MessageHistory;
import com.message.entity.UserModel;
import com.message.repository.MessageTransactionRepository;
import com.message.repository.UserRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;
    @Mock
    MessageTransactionRepository messageTransactionRepository;

    UserModel userModel;
    UUID uuid;
    Message message;
    MessageHistory messageHistory;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setName("Raj");
        uuid = UUID.randomUUID();
        message = new Message();
        message.setReceiver(UUID.randomUUID().toString());
        message.setMessage_body("Hi Raj");
        messageHistory = new MessageHistory();
        messageHistory.setMessage_body(message.getMessage_body());
        messageHistory.setSender(uuid);
        messageHistory.setReceiver(UUID.fromString(message.getReceiver()));
    }

    @Test
    void findUserModelByName() {
        UserModel userModel = new UserModel();
        userModel.setName("Raj");
    }

    @Test
    void createUser() {

        when(userRepository.findUserModelByName(userModel.getName())).thenReturn(Optional.of(userModel));
        when(userRepository.save(userModel)).thenReturn(userModel);
        UserModel userModel1 = userService.createUser(userModel);
        assertNotNull(userModel1);
        assertEquals(userModel1.getName(), userModel.getName());
    }

    @Test
    void saveMessageHistory() {
        when(messageTransactionRepository.save(messageHistory)).thenReturn(messageHistory);
        userService.saveMessageHistory(message,uuid);
        Mockito.verify(messageTransactionRepository).save(messageHistory);
    }

    @Test
    void getAllSavedMessage() {
        List<MessageHistory> messageHistories = new ArrayList<>();
        messageHistories.add(messageHistory);
        when(messageTransactionRepository.findAll()).thenReturn(messageHistories);
        Optional<List<MessageHistory>> messageHistoryList = userService.getAllSavedMessage();
        assertNotNull(messageHistoryList);
    }

    @Test
    void findAllMessageSendedByMe() {
        List<MessageHistory> messageHistories = new ArrayList<>();
        messageHistories.add(messageHistory);
        when(userRepository.findUserModelById(uuid)).thenReturn(Optional.ofNullable(userModel));
        when(messageTransactionRepository.findMessageHistoryBySender(uuid)).thenReturn(messageHistories);
        Optional<List<MessageHistory>> messageHistoryList = userService.findAllMessageSendedByMe(uuid);
        assertNotNull(messageHistoryList);
    }

    @Test
    void findAllMessageReceivedByMe() {
        List<MessageHistory> messageHistories = new ArrayList<>();
        messageHistories.add(messageHistory);
        when(userRepository.findUserModelById(uuid)).thenReturn(Optional.ofNullable(userModel));
        when(messageTransactionRepository.findMessageHistoryByReceiver(uuid)).thenReturn(messageHistories);
        Optional<List<MessageHistory>> messageHistoryList = userService.findAllMessageReceivedByMe(uuid);
        assertNotNull(messageHistoryList);
    }
}