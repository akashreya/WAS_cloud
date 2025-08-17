# Workspace Allocation System (WAS)

A modern, full-stack workspace allocation system for managing employee seat assignments in office environments. Built with Spring Boot and React, featuring drag-and-drop seat management, real-time updates, and comprehensive business rule enforcement.

## 🚀 Features

- **Interactive Seat Management**: Drag-and-drop interface for seat assignments
- **Employee Queue System**: Manage unassigned employees efficiently
- **Business Rules Enforcement**: Automatic validation of manager seat requirements
- **Real-time Dashboard**: Live statistics and system monitoring
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile
- **RESTful API**: Complete backend API for all operations
- **SQLite Database**: Persistent storage with automatic data seeding

## 🏗️ Architecture

### Backend (Spring Boot 3.2.0)
- **Framework**: Spring Boot with Java 17
- **Database**: SQLite with JPA/Hibernate
- **API**: RESTful services with JSON responses
- **Security**: CORS configured for cross-origin requests
- **Health Checks**: Built-in monitoring endpoints

### Frontend (React 18)
- **Framework**: React with modern hooks
- **Styling**: Tailwind CSS with Radix UI components
- **State Management**: Local state with React hooks
- **HTTP Client**: Axios for API communication
- **Notifications**: React Hot Toast for user feedback

## 🚀 Quick Start

### Option 1: Docker (Recommended)
```bash
# Clone the repository
git clone <repository-url>
cd WAS_cloud

# Run with Docker Compose
./deploy.sh local

# Access the application
# Frontend: http://localhost:3000
# Backend API: http://localhost:8080/api
```

### Option 2: Local Development
```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend (in new terminal)
cd frontend
npm install
npm start
```

## 📦 Deployment

### Free Hosting Platforms

#### Railway (Recommended)
- 🆓 Free tier with $5 monthly credit
- ✅ Automatic HTTPS and domain
- ✅ Volume persistence for database
- ✅ Git-based deployment

#### Render
- 🆓 Free tier with auto-sleep
- ✅ Automatic deploys from Git
- ✅ Built-in SSL certificates

#### Fly.io
- 🆓 Free tier with 3 machines
- ✅ Global deployment
- ✅ Volume persistence

### Deployment Commands
```bash
# Test locally
./deploy.sh local

# Prepare for Railway
./deploy.sh railway

# Check status
./deploy.sh status

# View logs
./deploy.sh logs
```

For detailed deployment instructions, see [DEPLOYMENT.md](DEPLOYMENT.md)

## 📊 API Endpoints

### Employees
- `GET /api/employees` - Get all employees
- `GET /api/employees/unassigned` - Get unassigned employees
- `POST /api/employees` - Create employee
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

### Seats
- `GET /api/seats` - Get all seats
- `GET /api/seats/available` - Get available seats
- `POST /api/seats/assign` - Assign seat to employee
- `POST /api/seats/swap-employees` - Swap employees between seats
- `POST /api/seats/{seatNumber}/unassign` - Unassign seat

### Designations
- `GET /api/designations` - Get all designations
- `GET /api/designations/managers` - Get manager designations

## 🏢 Business Rules

### Seat Allocation
- Maximum 100 seats allowed in the system
- Maximum 5 manager seats allowed
- Manager employees require manager seats
- Non-manager employees cannot occupy manager seats

### Employee Management
- Employees must have valid designation
- Queue-based assignment system
- Seat swapping with business rule validation

### System Constraints
- SQLite database with automatic schema management
- CORS protection for secure API access
- Input validation and error handling

## 🛠️ Development

### Project Structure
```
WAS_cloud/
├── backend/                 # Spring Boot application
│   ├── src/main/java/      # Java source code
│   ├── src/main/resources/ # Configuration files
│   ├── Dockerfile          # Backend container
│   └── pom.xml            # Maven dependencies
├── frontend/               # React application
│   ├── src/               # React source code
│   ├── public/            # Static assets
│   ├── Dockerfile         # Frontend container
│   └── package.json       # NPM dependencies
├── docker-compose.yml     # Local development
├── DEPLOYMENT.md          # Deployment guide
└── deploy.sh             # Deployment script
```

### Environment Variables

#### Backend
- `SPRING_PROFILES_ACTIVE=prod` - Production profile
- `ALLOWED_ORIGINS` - CORS allowed origins
- `PORT` - Server port (default: 8080)

#### Frontend
- `REACT_APP_API_BASE_URL` - Backend API URL

## 🔍 Monitoring

### Health Checks
- Backend: `/api/actuator/health`
- Frontend: `/health`

### Logging
- Application logs available in `/app/data/application.log`
- Access logs through Docker or platform dashboards

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For questions and support, please open an issue in the GitHub repository.
