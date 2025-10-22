#  MediDoor Microservices

A modern **Medicine Ordering Platform** built with **Spring Boot (Java 8/11/17)** using **Microservices Architecture**.
It supports **real-time events**, **inventory management**, **auto-expiry scheduling**, **notifications**, and **payment processing**.

---

##  Overview

**MediDoor** connects users, pharmacies, and delivery services in one system — similar to Uber/Zomato for medicines.
It is built with **Kafka**, **WebSocket**, and **REST APIs** to handle real-time operations.

---

##  Services

### 1. **Inventory Service**

* Manages medicine stock.
* Supports CRUD (Create, Read, Update, Delete).
* Updates stock quantity when order placed or cancelled.
* Uses Kafka events to sync with other services.
* Auto-delete expired medicines using **Spring Scheduler**.
* Adds expired medicines to a **To-Do list** for destruction.

#### Example Medicine Object

```json
{
  "id": 1,
  "name": "Metrogyl 400",
  "stock": 100,
  "expiryDate": "2025-12-01"
}
```

---

### 2. **Order Service**

* Handles medicine orders.
* Accepts order requests and publishes **order events** to Kafka.
* Supports **multi-threaded booking queue** to handle high traffic safely.
* Reduces stock in Inventory Service via events.

#### Example Order Request

```json
{
  "userId": "user123",
  "userEmail": "anmolmehla2@gmail.com",
  "medicineId": 1,
  "quantity": 10,
  "amount": 500
}
```

>  Future Update:
> User details (ID & email) will be extracted automatically from JWT token.

---

### 3. **Notification Service**

* Sends notifications via:

  *  **Email**
  *  **SMS & WhatsApp** using **Twilio**
* Listens to Kafka events like:

  * `OrderPlaced`
  * `PaymentCompleted`
  * `StockLow`
* Fully asynchronous and scalable.

---

### 4. **Payment Service**

* Triggered automatically via **WebSocket event** from Order Service.
* Processes user payments.
* Updates order status to *Paid*.
* Can integrate with external payment gateways.

---

## 🧠 Tech Stack

| Component     | Technology                   |
| ------------- | ---------------------------- |
| Language      | Java (8/11/17)             |
| Framework     | Spring Boot                  |
| Communication | REST, Kafka, WebSocket       |
| Scheduling    | Spring Scheduler             |
| Database      | MySQL / PostgreSQL           |
| Message Queue | Apache Kafka                 |
| Notification  | Email, Twilio (SMS/WhatsApp) |
| Build Tool    | Maven                        |
| Architecture  | Microservices                |

---

## ⚙️ Key Features

✅ Event-driven communication (Kafka)
✅ Auto medicine expiry scheduling
✅ Real-time payment trigger (WebSocket)
✅ Notification system (Email/SMS/WhatsApp)
✅ Thread-safe order queue
✅ Follows **SOLID**, **DRY** principles
✅ Uses **Creational**, **Structural**, and **Behavioral** design patterns

---

## 🔮 Future Enhancements

* JWT-based authentication
* Admin Dashboard
* Delivery tracking microservice
* Containerized setup (Docker Compose)
* Centralized logging and tracing (ELK / Zipkin)

---

## 🧾 Setup

1. Clone the repo

   ```bash
   git clone https://github.com/Ak-Sat09/medidoor-microservices.git
   cd medidoor-microservices
   ```

2. Start services one by one or with Docker Compose.

3. Kafka must be running before services start.

4. Test API endpoints using Postman.

---

## 👨‍💻 Author

**Anmol Mehla  
