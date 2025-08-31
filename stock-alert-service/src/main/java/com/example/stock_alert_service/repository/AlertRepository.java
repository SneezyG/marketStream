package com.example.stock_alert_service.repository;

import com.example.stock_alert_service.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    // You can add custom queries later, e.g. findByStockSymbol, findByEmail, etc.
}
