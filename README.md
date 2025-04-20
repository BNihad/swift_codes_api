# 🏦 SWIFT Codes API

Spring Boot application for managing and querying SWIFT codes.  
Designed as part of the 2025 intern exercise.

---

## 🔧 Tech Stack

- Java 23
- Spring Boot 3.4
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- JUnit 5 & Mockito
- Maven

---

## 🚀 Setup

### 1. Clone the repository

git clone https://github.com/your-username/swift-codes-api.git
cd swift-codes-api
2. Configure environment (if needed)
Check the application.properties file under src/main/resources:

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=your_password_here

3. Run with Docker Compose
docker compose up --build
The API will be available at:
http://localhost:8080/api/v1/swift-codes
PostgreSQL will be running on:
localhost:5432, db name: postgres
🌐 API Endpoints

➕ Add a new SWIFT code
POST /api/v1/swift-codes
JSON Body:

{
  "swiftCode": "BANKUSNEW",
  "bankName": "Bank New",
  "address": "Wall Street",
  "countryISO2": "US",
  "countryName": "UNITED STATES",
  "isHeadquarter": true
}
🔍 Get details by SWIFT code
GET /api/v1/swift-codes/{swiftCode}

🌍 Get SWIFT codes by country
GET /api/v1/swift-codes/country/{countryISO2}

❌ Delete a SWIFT code
DELETE /api/v1/swift-codes/{swiftCode}
🧪 Testing

Unit & Integration Tests
Run all tests using Maven:

./mvnw test
Tests include:

Service layer unit tests with Mockito
Controller integration tests using MockMvc
Context loading to verify Spring configuration
📁 Additional Notes

The original Excel file is imported from:
src/main/resources/SWIFT_CODES.xlsx
Uploaded files are stored in:
./uploads
👤 Author

Nihad Babazade
Internship Assignment 2025