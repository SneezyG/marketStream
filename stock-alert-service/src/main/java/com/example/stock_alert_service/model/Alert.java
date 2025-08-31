package com.example.stock_alert_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data   // Lombok: generates getters, setters, toString, etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockSymbol;

    private Double targetPrice;

    @Enumerated(EnumType.STRING)
    private Condition condition; // GREATER_THAN, LESS_THAN

    private String email;

    private LocalDateTime createdAt;
}
