# Task Management API

This Spring Boot application provides a RESTful API for managing tasks. It allows CRUD (Create, Read, Update, Delete) operations on tasks with support for filtering and searching based on various criteria.

## Running the Application

### Prerequisites

- Java Development Kit (JDK) installed (version 8 or higher)
- Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse
- Maven or Gradle build tools

### Setup Instructions

1. Clone the repository:

   ```bash
   git clone https://github.com/mustafakataya00/TaskManagerAPI.git

2. Open the project in your IDE.

3. Build the project.

4. Run the Spring Boot application. This will start the embedded Tomcat server on port 8080 by default.

**Database**
The application uses an embedded H2 database by default. No additional setup is required for database configuration.

**Testing API Endpoints**
You can use tools like Postman or any REST client to test the API endpoints.

**Example Requests**
Create Task: POST /TaskAPI with JSON body containing title, description, completed.
Update Task: PUT /TaskAPI/{id} with JSON body containing updated fields.
Delete Task: DELETE /TaskAPI/{id} to delete a task by ID.
Retrieve Tasks: GET /TaskAPI to retrieve all tasks.

**Additional Endpoints**
Filter by Completion Status: GET /TaskAPI/completed/{completed}
Filter by Title: GET /TaskAPI/title/{title}
Filter by Description Keyword: GET /TaskAPI/description/{keyword}
Filter by ID Range: GET /TaskAPI/id-range/{startId}/{endId}
Search by Title or Description: GET /TaskAPI/keyword/{keyword}





