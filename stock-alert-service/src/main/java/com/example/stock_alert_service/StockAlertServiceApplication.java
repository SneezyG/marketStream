package com.example.stock_alert_service;

import com.example.stock_alert_service.stream.StockPriceStreamer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockAlertServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockAlertServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner startStreamer(StockPriceStreamer streamer) {
        return args -> {
            streamer.connect();
        };
    }
}
