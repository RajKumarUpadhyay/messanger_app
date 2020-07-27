package com.message.api;

import com.message.entity.Message;
import com.message.entity.MessageHistory;
import com.message.entity.UserModel;
import com.message.messanger.MessageReceiver;
import com.message.messanger.MessageSender;
import com.message.repository.MessageTransactionRepository;
import com.message.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MessangerController {

    private static final Logger log = LoggerFactory.getLogger(MessangerController.class);

    @Autowired
    MessageTransactionRepository messageTransactionRepository;

    @Autowired
    MessageSender messageSender;

    @Autowired
    MessageReceiver messageReceiver;

    @Autowired
    private UserService userService;

    @PostMapping("/createAccount")
    public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<String> send(@RequestBody Message transaction, @RequestHeader("senderId") UUID senderId) {
        log.info("Sending a transaction.");
        if(senderId.equals(transaction.getReceiver()) && Objects.nonNull(senderId))
            throw new RuntimeException("Can't send message to himself");
        messageSender.sendMessage(transaction);
        messageReceiver.receiveMessage();
        userService.saveMessageHistory(transaction, senderId);
        log.info("Saved a transaction.");
        return new ResponseEntity<>("!Message Sent!", HttpStatus.OK);
    }

    @GetMapping("/findAllMessage")
    public ResponseEntity<Optional<List<MessageHistory>>> getMessage() {
        if(userService.getAllSavedMessage().isPresent())
            return new ResponseEntity<>(userService.getAllSavedMessage(), HttpStatus.OK);
        else
            throw new RuntimeException("NO Message Found!");
    }

    @GetMapping("/findAllMessageSendedByMe/{userId}")
    public ResponseEntity<Optional<List<MessageHistory>>> findAllMessageSendedByMe(@PathVariable("userId") UUID userId) {
        if(userService.findAllMessageSendedByMe(userId).isPresent())
            return new ResponseEntity<>(userService.findAllMessageSendedByMe(userId), HttpStatus.OK);
        else
            throw new RuntimeException("NO Message Found!");
    }

    @GetMapping("/findAllMessageReceivedByMe/{userId}")
    public ResponseEntity<Optional<List<MessageHistory>>> findAllMessageReceivedByMe(@PathVariable("userId") UUID userId) {
        if(userService.findAllMessageReceivedByMe(userId).isPresent())
            return new ResponseEntity<>(userService.findAllMessageReceivedByMe(userId), HttpStatus.OK);
        else
            throw new RuntimeException("NO Message Found!");
    }
}
