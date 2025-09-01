# 📈 Stock Alert Service: Real-Time Stock Price Monitoring  

A backend service that listens to real-time stock market data and notifies users when their target price conditions are met. Built with **Java + Spring Boot**, it integrates with live financial data streams and supports email notifications.  

## [See marketStream Code Repo](https://github.com/SneezyG/marketStream)  

---

## 🚀 Key Features  

- **Real-Time Data Streaming**: Uses WebSockets to receive live stock prices.  
- **Custom Alerts**: Users can define alerts with conditions (`GREATER_THAN` / `LESS_THAN`).  
- **Email Notifications**: Sends email alerts instantly when conditions are triggered.  
- **REST API**: Create and manage stock alerts via HTTP endpoints.  
- **Extensible Design**: Can be adapted to multiple financial data providers.  

---

## 📍 Use Cases  

- **Personal Trading**: Get notified when your favorite stock hits your buy/sell target.  
- **Portfolio Management**: Track multiple stock price movements in real time.  
- **Market Monitoring**: Stay updated without keeping trading platforms open all day.  
- **Learning Project**: A practical introduction to Spring Boot, WebSockets, and finance APIs.  

---

## 🧠 Architecture  

1. **Spring Boot Backend** — REST API + WebSocket client.  
2. **Database (H2 / PostgreSQL / MySQL)** — Stores user alerts.  
3. **Stock Data Stream** — Subscribed via Finnhub WebSocket API.  
4. **Alert Engine** — Matches live prices with stored alerts.  
5. **Notification Layer** — Sends alert emails when conditions are met.  

---

## ⚙️ Technology Stack  

| Layer         | Technology                    |
|---------------|-------------------------------|
| Backend       | Spring Boot (Java 17+)        |
| Streaming API | Finnhub WebSocket API         |
| Database      | H2 (dev) / PostgreSQL (prod)  |
| Notifications | Spring Boot Mail (SMTP)       |
| Build Tool    | Maven                         |
| Deployment    | Docker (optional)             |

---

## 🔌 API Endpoints  

**Create Alert**  
- `POST /api/alerts`  
{
  "email": "user@example.com",
  "stockSymbol": "AAPL",
  "targetPrice": 150.0,
  "condition": "LESS_THAN"
}

**Get All Alerts**  
- `GET /api/alerts`  

**Delete Alert**  
- `DELETE /api/alerts/{id}`  

---

## 📦 Alert Logic  

- Alerts are stored with:  
  - User email  
  - Stock symbol (e.g., `AAPL`, `TSLA`)  
  - Condition (`GREATER_THAN`, `LESS_THAN`)  
  - Target price  

- The WebSocket listener checks each incoming trade price against stored alerts.  
- If conditions are met → alert triggers → email notification is sent.  

---

## 🛡️ Notes  

- Uses **Finnhub.io** for stock price streaming (requires free API key).  
- For email, configure SMTP (e.g., Gmail, SendGrid, Mailgun) in `application.yml`.  
- Alerts currently support **single price triggers** — can be extended to support advanced rules.  
- Designed for modularity: swapping in another stock data provider is simple.  
