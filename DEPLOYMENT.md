# Deployment Guide - Workspace Allocation System

## Overview
This guide covers deploying the Workspace Allocation System using Docker containers to various free hosting platforms.

## Prerequisites
- Docker installed locally
- Git repository set up
- Account on chosen hosting platform (Railway, Render, or Fly.io)

## Local Development with Docker

### Building and Running Locally
```bash
# Build and run with docker-compose
docker-compose up --build

# Or build individually
cd backend
docker build -t was-backend .

cd ../frontend
docker build -t was-frontend .
```

### Access Points
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api
- Health Checks: 
  - Backend: http://localhost:8080/api/actuator/health
  - Frontend: http://localhost:3000/health

## Production Deployment

### Option 1: Railway (Recommended)

#### Backend Deployment
1. Create new Railway project
2. Connect your GitHub repository
3. Add backend service:
   ```bash
   railway login
   railway link
   railway up --service backend
   ```
4. Set environment variables:
   ```
   SPRING_PROFILES_ACTIVE=prod
   ALLOWED_ORIGINS=https://your-frontend-domain.railway.app
   PORT=8080
   ```
5. Add volume for SQLite database: `/app/data`

#### Frontend Deployment
1. Add frontend service to same Railway project
2. Set environment variables:
   ```
   REACT_APP_API_BASE_URL=https://your-backend-domain.railway.app/api
   BACKEND_URL=https://your-backend-domain.railway.app
   ```
3. Deploy frontend service

### Option 2: Render

#### Backend Deployment
1. Create new Web Service on Render
2. Connect GitHub repository
3. Configure:
   - Build Command: `docker build -t backend ./backend`
   - Start Command: `docker run -p 10000:8080 backend`
   - Environment Variables:
     ```
     SPRING_PROFILES_ACTIVE=prod
     ALLOWED_ORIGINS=https://your-frontend.onrender.com
     PORT=8080
     ```

#### Frontend Deployment
1. Create new Static Site on Render
2. Configure:
   - Build Command: `cd frontend && npm install && npm run build`
   - Publish Directory: `frontend/build`
   - Environment Variables:
     ```
     REACT_APP_API_BASE_URL=https://your-backend.onrender.com/api
     ```

### Option 3: Fly.io

#### Setup
```bash
# Install flyctl
# Follow instructions at https://fly.io/docs/getting-started/installing-flyctl/

# Login
fly auth login
```

#### Backend Deployment
```bash
cd backend
fly launch --dockerfile --name was-backend
fly volumes create data --size 1 --region <your-region>
fly deploy
```

#### Frontend Deployment
```bash
cd frontend
fly launch --dockerfile --name was-frontend
fly deploy
```

## Environment Variables Reference

### Backend
- `SPRING_PROFILES_ACTIVE=prod` - Activates production profile
- `ALLOWED_ORIGINS` - Comma-separated list of allowed frontend URLs
- `PORT` - Port number (defaults to 8080)

### Frontend
- `REACT_APP_API_BASE_URL` - Full URL to backend API
- `BACKEND_URL` - Backend URL for nginx proxy (Docker only)

## Database Persistence

### SQLite File Location
- Development: `./backend/was_database.db`
- Production: `/app/data/was_database.db` (mounted volume)

### Data Backup
```bash
# Copy database from running container
docker cp container_name:/app/data/was_database.db ./backup.db

# Restore database
docker cp ./backup.db container_name:/app/data/was_database.db
```

## Monitoring and Health Checks

### Health Endpoints
- Backend: `/api/actuator/health`
- Frontend: `/health`

### Logs
```bash
# View logs
docker-compose logs -f

# Railway
railway logs

# Render
# Available in dashboard

# Fly.io
fly logs
```

## Troubleshooting

### Common Issues

1. **CORS Errors**
   - Ensure `ALLOWED_ORIGINS` includes your frontend domain
   - Check protocol (http vs https)

2. **Database Connection Issues**
   - Verify volume mount for SQLite file
   - Check file permissions

3. **API Connection Issues**
   - Verify `REACT_APP_API_BASE_URL` is correct
   - Check network connectivity between services

4. **Build Failures**
   - Ensure all dependencies are in package.json/pom.xml
   - Check Docker build context

### Performance Optimization

1. **Frontend**
   - Gzip compression enabled in nginx
   - Static asset caching
   - Bundle size optimization

2. **Backend**
   - JVM memory tuning
   - Connection pool optimization
   - Logging configuration

## Security Considerations

1. **HTTPS**: All production deployments should use HTTPS
2. **CORS**: Restrict allowed origins to your domains only
3. **Headers**: Security headers configured in nginx
4. **Database**: SQLite file should be in mounted volume with proper permissions

## Cost Estimation

### Free Tier Limits
- **Railway**: $5/month credit, 512MB RAM
- **Render**: 512MB RAM, sleeps after 15min inactivity
- **Fly.io**: 3 shared-cpu-1x machines, 160GB bandwidth

### Scaling Options
All platforms offer paid tiers for:
- More memory/CPU
- Custom domains
- Advanced monitoring
- Higher availability