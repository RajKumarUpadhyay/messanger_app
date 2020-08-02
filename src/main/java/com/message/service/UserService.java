package com.message.service;
import com.message.entity.Message;
import com.message.entity.MessageHistory;
import com.message.entity.UserModel;
import com.message.exception.DuplicateResourceRequestException;
import com.message.exception.ResourceNotFoundException;
import com.message.repository.MessageTransactionRepository;
import com.message.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageTransactionRepository messageTransactionRepository;

    public Optional<UserModel> findUserModelByName(String name)
    {
        return userRepository.findUserModelByName(name);
    }

    public UserModel createUser(UserModel user) {
        try {
            Optional<UserModel> userModel = userRepository.findUserModelByName(user.getName());

            if(userModel.isPresent() && Objects.nonNull(userModel.get().getId()))
                throw new DuplicateResourceRequestException("Provided User Name Already exist");
            else
                return  userRepository.save(user);
        }catch (Exception e) {
                throw new RuntimeException("Failed To Create User. Try after some time!");
        }
    }

    public void saveMessageHistory(Message message, UUID from) {

        MessageHistory messageHistory = new MessageHistory();
        messageHistory.setMessage_body(message.getMessage_body());
        messageHistory.setSender(from);
        messageHistory.setReceiver(UUID.fromString(message.getReceiver()));
        messageTransactionRepository.save(messageHistory);
    }

    public Optional<List<MessageHistory>> getAllSavedMessage() {
        return Optional.ofNullable(messageTransactionRepository.findAll());
    }

    public Optional<List<MessageHistory>> findAllMessageSendedByMe(UUID userId) {
        if(!userRepository.findUserModelById(userId).isPresent())
            throw new ResourceNotFoundException("Provided Resource ID not present");
        return Optional.ofNullable(messageTransactionRepository.findMessageHistoryBySender(userId));
    }

    public Optional<List<MessageHistory>> findAllMessageReceivedByMe(UUID userId) {
        if(!userRepository.findUserModelById(userId).isPresent())
            throw new ResourceNotFoundException("Provided Resource ID not present");
        return Optional.ofNullable(messageTransactionRepository.findMessageHistoryByReceiver(userId));
    }
}
