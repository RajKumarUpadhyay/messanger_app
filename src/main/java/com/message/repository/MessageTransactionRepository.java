package com.message.repository;

import com.message.entity.MessageHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageTransactionRepository extends CrudRepository<MessageHistory, UUID> {

    List<MessageHistory> findMessageHistoryBySender(UUID sender);
    List<MessageHistory> findMessageHistoryByReceiver(UUID sender);
    List<MessageHistory> findAll();
}
