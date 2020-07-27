package com.message.repository;

import com.message.entity.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends CrudRepository<UserModel, UUID> {
    Optional<UserModel> findUserModelByName(String name);
    Optional<UserModel> findUserModelById(UUID userId);
}
