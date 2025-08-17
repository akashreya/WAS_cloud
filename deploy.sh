#!/bin/bash

# Workspace Allocation System - Deployment Script
set -e

echo "🚀 Workspace Allocation System Deployment Script"
echo "================================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    print_error "Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if docker-compose is available
if ! command -v docker-compose &> /dev/null; then
    print_error "docker-compose is not installed. Please install docker-compose first."
    exit 1
fi

# Function to build and test locally
local_deployment() {
    print_status "Starting local deployment..."
    
    # Stop any running containers
    print_status "Stopping existing containers..."
    docker-compose down || true
    
    # Build and start services
    print_status "Building and starting services..."
    docker-compose up --build -d
    
    # Wait for services to be healthy
    print_status "Waiting for services to be ready..."
    sleep 10
    
    # Check health
    print_status "Checking service health..."
    
    # Check backend health
    if curl -f http://localhost:8080/api/actuator/health &> /dev/null; then
        print_success "Backend is healthy"
    else
        print_error "Backend health check failed"
        docker-compose logs backend
        exit 1
    fi
    
    # Check frontend health
    if curl -f http://localhost:3000/health &> /dev/null; then
        print_success "Frontend is healthy"
    else
        print_error "Frontend health check failed"
        docker-compose logs frontend
        exit 1
    fi
    
    print_success "Local deployment completed successfully!"
    print_status "Access your application at:"
    print_status "  Frontend: http://localhost:3000"
    print_status "  Backend API: http://localhost:8080/api"
    print_status ""
    print_status "To view logs: docker-compose logs -f"
    print_status "To stop: docker-compose down"
}

# Function to prepare for Railway deployment
railway_preparation() {
    print_status "Preparing for Railway deployment..."
    
    print_status "Repository should be pushed to GitHub first"
    print_status "Then follow these steps:"
    echo ""
    echo "1. Go to https://railway.app and create an account"
    echo "2. Create a new project and connect your GitHub repository"
    echo "3. Add backend service:"
    echo "   - Root directory: /backend"
    echo "   - Add environment variables:"
    echo "     SPRING_PROFILES_ACTIVE=prod"
    echo "     ALLOWED_ORIGINS=https://your-frontend-domain.railway.app"
    echo "4. Add frontend service:"
    echo "   - Root directory: /frontend"
    echo "   - Add environment variables:"
    echo "     REACT_APP_API_BASE_URL=https://your-backend-domain.railway.app/api"
    echo "5. Deploy both services"
    echo ""
    print_success "Railway preparation guide displayed"
}

# Function to show status
show_status() {
    print_status "Checking deployment status..."
    
    if docker-compose ps | grep -q "Up"; then
        print_success "Local deployment is running"
        docker-compose ps
    else
        print_warning "Local deployment is not running"
    fi
}

# Main menu
case "${1:-menu}" in
    "local")
        local_deployment
        ;;
    "railway")
        railway_preparation
        ;;
    "status")
        show_status
        ;;
    "stop")
        print_status "Stopping local deployment..."
        docker-compose down
        print_success "Local deployment stopped"
        ;;
    "logs")
        docker-compose logs -f
        ;;
    "menu"|*)
        echo "Usage: $0 {local|railway|status|stop|logs}"
        echo ""
        echo "Commands:"
        echo "  local   - Build and run locally with Docker"
        echo "  railway - Show Railway deployment preparation steps"
        echo "  status  - Check current deployment status"
        echo "  stop    - Stop local deployment"
        echo "  logs    - Show logs from local deployment"
        echo ""
        echo "For detailed deployment instructions, see DEPLOYMENT.md"
        ;;
esac