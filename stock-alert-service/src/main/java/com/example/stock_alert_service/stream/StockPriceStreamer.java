package com.example.stock_alert_service.stream;

import com.example.stock_alert_service.model.Alert;
import com.example.stock_alert_service.model.Condition;
import com.example.stock_alert_service.service.AlertService;
import com.example.stock_alert_service.service.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class StockPriceStreamer extends WebSocketClient {

    private final AlertService alertService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StockPriceStreamer(AlertService alertService, EmailService emailService) throws Exception {
        super(new URI("wss://ws.finnhub.io?token=YOUR_API_KEY"));
        this.alertService = alertService;
        this.emailService = emailService;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("✅ Connected to Finnhub WebSocket");
        // Example: subscribe to AAPL and TSLA
        send("{\"type\":\"subscribe\",\"symbol\":\"AAPL\"}");
        send("{\"type\":\"subscribe\",\"symbol\":\"TSLA\"}");
    }

    @Override
    public void onMessage(String message) {
        try {
            JsonNode json = objectMapper.readTree(message);

            if (json.has("data")) {
                for (JsonNode trade : json.get("data")) {
                    String symbol = trade.get("s").asText();
                    double price = trade.get("p").asDouble();

                    System.out.println("📈 Price update: " + symbol + " = " + price);

                    // Fetch alerts for this stock
                    List<Alert> alerts = alertService.getAllAlerts();
                    alerts.stream()
                          .filter(alert -> alert.getStockSymbol().equalsIgnoreCase(symbol))
                          .forEach(alert -> checkAlert(alert, price));
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error parsing message: " + e.getMessage());
        }
    }

    private void checkAlert(Alert alert, double price) {
        boolean triggered = false;

        if (alert.getCondition() == Condition.GREATER_THAN && price > alert.getTargetPrice()) {
            triggered = true;
        } else if (alert.getCondition() == Condition.LESS_THAN && price < alert.getTargetPrice()) {
            triggered = true;
        }

        if (triggered) {
            String subject = "Stock Alert Triggered: " + alert.getStockSymbol();
            String body = "Your alert for " + alert.getStockSymbol() +
                    " has been triggered.\nCurrent Price: " + price +
                    "\nTarget Price: " + alert.getTargetPrice();

            emailService.sendAlertEmail(alert.getEmail(), subject, body);

            System.out.println("🚨 ALERT TRIGGERED and email sent to " + alert.getEmail());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("❌ Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("⚠️ Error: " + ex.getMessage());
    }
}
