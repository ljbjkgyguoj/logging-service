package com.example.loggingservice.repository;

import com.example.loggingservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с таблицей orders.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
