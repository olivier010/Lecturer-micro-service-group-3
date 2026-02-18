# Lecturer Service - SmartCampus Microservice

## Overview
This is the **Lecturer Service** (Group 3) for the SmartCampus University Management System. It's built as an independent microservice following the assignment requirements.

## Features
- ✅ Add lecturer
- ✅ Update lecturer information  
- ✅ Manage department assignments
- ✅ View lecturer profile
- ✅ REST API endpoints for inter-service communication
- ✅ JSON request/response handling
- ✅ MVC architecture (Servlet + JSP + JDBC)

## Technology Stack
- **Java Servlet** - Web framework
- **JSP** - View layer
- **MySQL** - Database
- **JDBC** - Database access (no ORM)
- **Jackson** - JSON processing
- **Bootstrap** - CSS framework
- **Tomcat** - Application server

## Project Structure
```
src/main/java/com/lecturer/
├── controller/
│   ├── LecturerServlet.java          # Web UI controller
│   ├── LecturerApiController.java    # REST API controller
│   └── IntegrationController.java    # Inter-service integration
├── service/
│   └── LecturerService.java          # Business logic layer
├── dao/
│   └── LecturerDAO.java              # Data access layer
├── model/
│   └── Lecturer.java                 # Entity model
└── util/
    ├── DBConnection.java             # Database connection
    └── JsonUtil.java                 # JSON utilities

src/main/webapp/
├── views/
│   ├── list-lecturers.jsp            # Main listing page
│   ├── lecturer-profile.jsp          # Profile view
│   └── edit-lecturer.jsp             # Edit form
└── WEB-INF/
    └── web.xml                       # Web configuration
```

## Database Schema
```sql
CREATE DATABASE smartcampus_lecturer_db;
USE smartcampus_lecturer_db;

CREATE TABLE lecturers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    staff_id VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## API Endpoints

### Web UI Endpoints
- `GET /lecturers` - List all lecturers
- `GET /lecturers?action=view&id={id}` - View lecturer profile
- `GET /lecturers?action=edit&id={id}` - Edit lecturer form
- `POST /lecturers` - Add/update/delete lecturer

### REST API Endpoints
- `GET /api/lecturers` - Get all lecturers (JSON)
- `GET /api/lecturers/{id}` - Get lecturer by ID (JSON)
- `GET /api/lecturers/department/{dept}` - Get lecturers by department (JSON)
- `POST /api/lecturers` - Create lecturer (JSON)
- `PUT /api/lecturers/{id}` - Update lecturer (JSON)
- `DELETE /api/lecturers/{id}` - Delete lecturer (JSON)

### Integration Endpoints
- `GET /api/integration/validate/lecturer/{id}` - Validate lecturer exists
- `GET /api/integration/lecturer/{id}` - Get basic lecturer info
- `GET /api/integration/health` - Health check
- `POST /api/integration/test-communication` - Test communication

## Configuration

### Database Connection
Update `DBConnection.java` with your database credentials:
```java
private static String url = "jdbc:mysql://localhost:3306/smartcampus_lecturer_db";
private static String user = "root";
private static String password = "";
```

### Port Configuration
Configure Tomcat to run on a different port (e.g., 8083) to avoid conflicts with other services.

## Inter-Service Communication

### For Other Services to Use:
1. **Validate Lecturer**: `GET /api/integration/validate/lecturer/{id}`
   - Response: `{"success": true, "data": true}`

2. **Get Lecturer Info**: `GET /api/integration/lecturer/{id}`
   - Response: `{"success": true, "data": {"id": 1, "staffId": "LEC001", "fullName": "John Doe", "department": "Computer Science"}}`

3. **Health Check**: `GET /api/integration/health`
   - Response: `{"success": true, "data": "Lecturer Service is running"}`

### Consuming Other Services:
The service includes a test endpoint to receive communication from other services:
- `POST /api/integration/test-communication`

## Installation & Running

1. **Setup Database**: Create the database and table as shown above
2. **Configure Connection**: Update database credentials in `DBConnection.java`
3. **Build Project**: `mvn clean install`
4. **Deploy**: Deploy the WAR file to Tomcat
5. **Configure Port**: Set Tomcat to run on port 8083 (or any available port)
6. **Access**: 
   - Web UI: `http://localhost:8083/LecturerService_Group3/lecturers`
   - API: `http://localhost:8083/LecturerService_Group3_war_exploded/api/lecturers`

## Testing API Endpoints

### Get All Lecturers
```bash
curl -X GET http://localhost:8083/LecturerService_Group3/api/lecturers
```

### Create Lecturer
```bash
curl -X POST http://localhost:8083/LecturerService_Group3/api/lecturers \
  -H "Content-Type: application/json" \
  -d '{"staffId":"LEC001","fullName":"John Doe","email":"john@university.edu","department":"Computer Science","phone":"123-456-7890"}'
```

### Validate Lecturer (for other services)
```bash
curl -X GET http://localhost:8083/LecturerService_Group3/api/integration/validate/lecturer/1
```

## Assignment Compliance
- ✅ Independent Dynamic Web Project (WAR)
- ✅ Separate database schema
- ✅ MVC architecture (Servlet + JSP + JDBC)
- ✅ REST API endpoints
- ✅ JSON request/response handling
- ✅ Inter-service communication endpoints
- ✅ No Spring Boot or Hibernate
- ✅ Proper error handling with HTTP status codes
- ✅ Service layer for business logic

## Notes for Integration
- This service runs independently on its own port
- Database is isolated from other services
- All communication is via HTTP requests
- JSON format is standardized across endpoints
- Health check endpoint available for monitoring
