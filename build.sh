#!/bin/bash
# Build script for Render deployment

echo "Starting build process..."
echo "Current directory: $(pwd)"
echo "Contents: $(ls -la)"

# Set absolute path to frontend directory
PROJECT_ROOT=$(pwd)
FRONTEND_DIR="$PROJECT_ROOT/frontend"

echo "Project root: $PROJECT_ROOT"
echo "Frontend directory: $FRONTEND_DIR"

# Navigate to frontend directory
if [ -d "$FRONTEND_DIR" ]; then
    echo "Found frontend directory, navigating..."
    cd "$FRONTEND_DIR"
else
    echo "Frontend directory not found at $FRONTEND_DIR!"
    exit 1
fi

echo "Current directory after cd: $(pwd)"
echo "Contents: $(ls -la)"

# Check if package.json exists
if [ -f "package.json" ]; then
    echo "Found package.json, proceeding with build..."
else
    echo "package.json not found!"
    exit 1
fi

# Check if public/index.html exists
if [ -f "public/index.html" ]; then
    echo "Found public/index.html"
else
    echo "public/index.html not found!"
    exit 1
fi

# Set environment variables to ensure correct paths
export PUBLIC_URL="."
export GENERATE_SOURCEMAP="false"

# Install dependencies and build
echo "Installing dependencies..."
npm install

echo "Building application..."
npm run build

echo "Build completed successfully!"
echo "Build directory contents:"
ls -la build/