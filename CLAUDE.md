# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a modernized Workspace Allocation System (WAS) - a full-stack web application for managing employee seat assignments in an office environment.

## Documentation References

For detailed project information, please refer to:

- **Architecture & Technical Details**: See [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)
- **UI Design Guidelines**: See [docs/SUPERDESIGN.md](docs/SUPERDESIGN.md)

## Quick Start

### Backend

```bash
cd backend && mvn spring-boot:run
```

### Frontend

```bash
cd frontend && npm install && npm start
```

## Key Points

- Spring Boot 3.2 + React 18 full-stack application
- SQLite database for persistent storage
- REST API with JSON responses
- Tailwind CSS for responsive design

## Special Instructions

When asked to design UI & frontend interface, refer to the complete SuperDesign guidelines in [docs/SUPERDESIGN.md](docs/SUPERDESIGN.md).
