# WAS Architecture Documentation

## Project Overview

This is a modernized Workspace Allocation System (WAS) - a full-stack web application for managing employee seat assignments in an office environment. The system has been converted from a legacy Spring MVC application to a modern Spring Boot + React architecture.

## Architecture

### Backend (Spring Boot)

- **Location**: `backend/` directory
- **Framework**: Spring Boot 3.2.0 with Java 17
- **Database**: H2 in-memory database (for development)
- **API**: RESTful web services with JSON responses

### Frontend (React)

- **Location**: `frontend/` directory
- **Framework**: React 18 with modern hooks
- **Styling**: Tailwind CSS for responsive design
- **State Management**: Local state with hooks
- **HTTP Client**: Axios for API communication

## Getting Started

### Backend Setup

```bash
cd backend
mvn spring-boot:run
```

- Application runs on http://localhost:8080
- API endpoints available at http://localhost:8080/api
- H2 Console available at http://localhost:8080/api/h2-console

### Frontend Setup

```bash
cd frontend
npm install
npm start
```

- React app runs on http://localhost:3000
- Proxy configured to backend on port 8080

## API Endpoints

### Designations

- `GET /api/designations` - Get all designations
- `GET /api/designations/managers` - Get manager designations
- `GET /api/designations/non-managers` - Get non-manager designations

### Employees

- `GET /api/employees` - Get all employees
- `GET /api/employees/unassigned` - Get unassigned employees
- `GET /api/employees/assigned` - Get assigned employees
- `GET /api/employees/{id}` - Get employee by ID
- `GET /api/employees/search?name={name}` - Search employees
- `POST /api/employees` - Create employee
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

### Seats

- `GET /api/seats` - Get all seats
- `GET /api/seats/available` - Get available seats
- `GET /api/seats/occupied` - Get occupied seats
- `GET /api/seats/manager-seats` - Get manager seats
- `POST /api/seats` - Create seat
- `DELETE /api/seats/{seatNumber}` - Delete seat
- `POST /api/seats/assign` - Assign seat to employee
- `POST /api/seats/{seatNumber}/unassign` - Unassign seat
- `POST /api/seats/reassign` - Reassign employee to different seat
- `POST /api/seats/swap` - Swap employees between seats

## Application Architecture

### Backend Structure

```
backend/src/main/java/com/smartans/
├── entity/           # JPA entities (Employee, Seat, Designation)
├── repository/       # Spring Data JPA repositories
├── service/          # Business logic services
├── controller/       # REST API controllers
├── dto/             # Data Transfer Objects
└── WorkspaceAllocationSystemApplication.java
```

### Frontend Structure

```
frontend/src/
├── components/      # React components
│   ├── Dashboard.js
│   ├── EmployeeManagement.js
│   ├── SeatManagement.js
│   ├── SeatGrid.js
│   └── Header.js
├── services/        # API service layer
└── App.js          # Main application component
```

## Key Business Rules

- Maximum 100 seats allowed in the system
- Maximum 5 manager seats allowed
- Employees can be assigned, reassigned, or have seats swapped
- Manager designation employees require manager seats
- Occupied seats cannot be deleted

## Database Schema

The application uses H2 in-memory database with the following tables:

- `designation` - Job designations and manager flags
- `employee` - Employee records with designation relationships
- `seat` - Seat information with employee assignments

Data is automatically seeded from `schema.sql` and `data.sql` on startup.

## Development Workflow

### Common Commands

**Backend**:

```bash
# Run application
mvn spring-boot:run

# Run tests
mvn test

# Build JAR
mvn clean package
```

**Frontend**:

```bash
# Start development server
npm start

# Run tests
npm test

# Build for production
npm run build
```

### Testing

- Backend: Use H2 console at http://localhost:8080/api/h2-console
- Frontend: React DevTools for component debugging
- API: Test endpoints with browser or Postman

## UI Features

- **Dashboard**: Overview with statistics and interactive seat grid
- **Employee Management**: CRUD operations for employees with search
- **Seat Management**: Seat creation, deletion, and assignment management
- **Responsive Design**: Works on desktop, tablet, and mobile devices
- **Real-time Updates**: UI updates immediately after operations

## Migration Notes

This project has been modernized from:

- Spring 3.1 → Spring Boot 3.2
- JSP + AJAX → React + REST API
- MySQL → H2 (in-memory)
- XML configuration → Java annotations
- Hibernate 3 → JPA with Spring Data

The core business logic and data model remain unchanged, ensuring feature parity with the original system.