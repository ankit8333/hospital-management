# 🏥 Hospital Management System

A complete **REST API** built with **Spring Boot** for managing hospital operations.

## 🚀 Tech Stack
- Java 17
- Spring Boot 3.2
- Spring Security + JWT
- MySQL
- Spring Data JPA + Hibernate
- Swagger UI

## 📌 Features
- ✅ JWT Authentication & Role-Based Access Control
- ✅ Patient Registration & Management
- ✅ Doctor & Department Management
- ✅ Appointment Booking with Token Generation
- ✅ Medical Records Management
- ✅ Swagger UI Documentation

## 🔑 Roles
| Role | Access |
|------|--------|
| ADMIN | Full Access |
| DOCTOR | Patients, Appointments, Medical Records |
| RECEPTIONIST | Departments, Doctors, Patients |
| PATIENT | View Appointments |

## ⚙️ Setup & Run

### 1. Database
```sql
CREATE DATABASE hospital_db;
```

### 2. Update application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/hospital_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Swagger UI
```
http://localhost:8080/api/swagger-ui.html
```

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /auth/register | Register user |
| POST | /auth/login | Login & get token |

### Patients
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /patients | Register patient |
| GET | /patients | Get all patients |
| GET | /patients/{id} | Get by ID |
| PUT | /patients/{id} | Update patient |
| DELETE | /patients/{id} | Delete patient |

### Doctors
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /doctors | Add doctor |
| GET | /doctors | Get all doctors |
| GET | /doctors/available | Available doctors |
| GET | /doctors/specialization/{spec} | By specialization |

### Appointments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /appointments | Book appointment |
| GET | /appointments | All appointments |
| PATCH | /appointments/{id}/status | Update status |
| DELETE | /appointments/{id}/cancel | Cancel |

## 👨‍💻 Author
**Ankit Singh**
- Email: tomarankit9634@gmail.com
- GitHub: [@ankit8333](https://github.com/ankit8333)
