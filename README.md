# SWIFT Codes API

A Spring Boot application that provides RESTFUL endpoints to manage and retrieve SWIFT codes, designed as part of the Remitly Internship Exercise 2025.

---

## 🧱 Stack

- **Java 17+** (tested with OpenJDK 23)
- **Spring Boot 3.4.4**
- **PostgreSQL 17**
- **Maven**
- **Docker / Docker Compose**
- **JUnit 5** + **Mockito**

---

## 🚀 Setup Instructions

### 🔧 1. Clone the Repository

```bash
git clone https://github.com/NihadBabazade/swift-codes-api.git
cd swift-codes-api
```
### 🛠️ 2. Configure Application Properties
```bash
Edit src/main/resources/application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=your_password
Replace your_password with your actual local or container DB password.
```
### 🐳 3. Run with Docker
Make sure Docker is installed and running:
```bash

docker-compose up --build
This will start both the Spring Boot application and PostgreSQL database.
Access the API at:
http://localhost:8080/api
```

### 📫 API Endpoints

GET /v1/swift-codes/{swiftCode}
Returns full details for a specific SWIFT code.
Includes branch list if it's a headquarters.

GET /v1/swift-codes/country/{countryISO2}
Returns all SWIFT codes (HQ + branches) for a specific country.

POST /v1/swift-codes
Adds a new SWIFT code to the database.
Request Body:
 ```json
 {
  "swiftCode": "BANKUSNEW",
  "bankName": "Bank New",
  "address": "Wall Street",
  "countryISO2": "US",
  "countryName": "UNITED STATES",
  "isHeadquarter": true
}
 ```
DELETE /v1/swift-codes/{swiftCode}
Deletes a SWIFT code entry by code.

## ✅ Testing

Run all tests (unit + integration) with:

./mvnw test
Uses MockMvc for controller tests.
Unit tests cover service logic.
In-memory H2 DB used during testing.

## 🗂️ File Import

Initial data loaded from SWIFT_CODES.xlsx
Located in: src/main/resources/SWIFT_CODES.xlsx

## 📁 File Uploads

File uploads (if needed) are stored in:
file.upload-dir=./uploads

## 👤 Author

Nihad Babazade
💼 Intern – SWIFT Codes API Project
🌐 GitHub: BNihad