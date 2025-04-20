✅ README.md
# SWIFT Codes API

A Spring Boot application that exposes RESTful endpoints to manage and retrieve SWIFT codes and bank details. Data is imported from an Excel sheet and stored in a PostgreSQL database. The project is fully containerized using Docker.

---

## 🚀 Features

- Import SWIFT code data from Excel on app startup
- Expose REST endpoints to:
  - Get SWIFT code details
  - Get SWIFT codes by country
  - Add new SWIFT code
  - Delete a SWIFT code
- PostgreSQL database integration
- Integration & unit testing (JUnit + Mockito)
- Containerized via Docker

---

## 📦 Tech Stack

- Java 23
- Spring Boot 3.4.4
- PostgreSQL
- Docker & Docker Compose
- Maven
- JUnit 5 + Mockito
- Apache POI (for Excel handling)
- IntelliJ IDEA

---

## 🧪 Run Tests

To run unit and integration tests:

```bash
mvn clean test
🐳 Run with Docker

Make sure Docker is installed and running
1. Build & Start Containers:
docker-compose up --build
2. API will be available at:
http://localhost:8080/api/v1/swift-codes
3. PostgreSQL is accessible at:
Host: localhost
Port: 5432
DB: postgres
User: postgres
Password: <your_password_here>
📂 API Endpoints


Method	Endpoint	Description
GET	/v1/swift-codes/{code}	Get details for SWIFT code
GET	/v1/swift-codes/country/{iso2}	Get all SWIFT codes by country
POST	/v1/swift-codes	Add a new SWIFT code
DELETE	/v1/swift-codes/{code}	Delete a SWIFT code
🗂 Data Import

On first run, data is imported from the Excel file:

src/main/resources/SWIFT_CODES.xlsx
To reload from Excel, you can clean the database volume and restart.

👤 Author

Nihad Babazade



