package com.example.loggingservice.repository;

import com.example.loggingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с таблицей users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
