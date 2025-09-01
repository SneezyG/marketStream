package com.example.stock_alert_service.service;

import com.example.stock_alert_service.model.Alert;
import com.example.stock_alert_service.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    // Create or update an alert
    public Alert saveAlert(Alert alert) {
        alert.setCreatedAt(LocalDateTime.now()); // ensure timestamp
        return alertRepository.save(alert);
    }

    // Get all alerts
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    // Get a single alert
    public Optional<Alert> getAlertById(Long id) {
        return alertRepository.findById(id);
    }

    // Delete an alert
    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }
}
