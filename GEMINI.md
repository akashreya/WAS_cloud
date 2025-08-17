# Workspace Allocation System (WAS) Project Overview

This project is a Workspace Allocation System (WAS) designed to manage seat assignments for employees. It's a multi-module application consisting of a modern Spring Boot backend, a React frontend, and an older, legacy Java web application. The system enforces detailed business rules for seat and employee management, including capacity limits, assignment logic, and data integrity.

## Project Structure

The project is organized into three main modules:

*   **`backend/`**: Contains the modern Spring Boot backend application.
*   **`frontend/`**: Contains the modern React frontend application.
*   **`WAS/`**: Contains the legacy Java web application (Spring MVC, Hibernate, JSP).

## Building and Running

### Modern Backend (`backend` directory)

*   **Technologies**: Java 17, Spring Boot 3.2.0, Maven, Spring Data JPA, H2 Database.
*   **Purpose**: Provides RESTful APIs for managing employees, seats, and designations, enforcing business logic.
*   **Build**:
    ```bash
    cd backend
    mvn clean install
    ```
*   **Run**:
    ```bash
    cd backend
    mvn spring-boot:run
    ```
    Alternatively, run the `com.smartans.WorkspaceAllocationSystemApplication` class directly from an IDE.

### Modern Frontend (`frontend` directory)

*   **Technologies**: React, JavaScript, npm, Tailwind CSS, React Router DOM, Axios.
*   **Purpose**: Provides a modern, interactive user interface for the Workspace Allocation System, consuming APIs from the modern backend.
*   **Install Dependencies**:
    ```bash
    cd frontend
    npm install
    ```
*   **Run**:
    ```bash
    cd frontend
    npm start
    ```
*   **Build**:
    ```bash
    cd frontend
    npm run build
    ```

### Legacy WAS Application (`WAS` directory)

*   **Technologies**: Java, Spring MVC 3.0, Hibernate 3, JSP, MySQL.
*   **Purpose**: An older version of the Workspace Allocation System, likely serving as a reference or for specific legacy functionalities. It has its own persistence layer and controllers.
*   **Build**:
    *   This module is a traditional Java web application. Building typically involves compiling Java sources and packaging into a WAR file.
    *   **TODO**: Determine the exact build process (e.g., Ant build file, specific Maven profile, or manual compilation/packaging).
*   **Run**:
    *   **TODO**: Deploy the generated WAR file to a servlet container (e.g., Apache Tomcat). Access via `index.jsp` or `.action` URLs.

## Development Conventions

*   **Business Rules**: Comprehensive business rules are documented in `BUSINESS_RULES.md` and are implemented across both backend systems and partially in the frontend for client-side validation.
*   **Code Style**: Follows standard Java conventions for backend and React/JavaScript best practices for frontend.
*   **Testing**: Unit and integration tests are present in both `backend/src/test/` and `frontend/src/` directories.
*   **UI Design**: The `.superdesign/design_iterations/workspace_allocation_ui.html` and associated CSS files serve as detailed UI mockups. The modern frontend aims to visually match the design shown in `marauders-map-front-page.webp`.
