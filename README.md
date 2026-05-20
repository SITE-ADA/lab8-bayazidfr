# University Management System

## Overview

University Management System is a Spring Boot microservice project for managing students, courses, and course enrollments.

The system contains two services:

- `student-service`: manages student records
- `course-service`: manages courses, enrollments, prerequisite validation, and course lookup by student name

This project was extended for WM2 Lab 8.

Implemented Lab 8 features:

- enrollment date is stored when a student enrolls in a course
- courses can have an optional prerequisite course ID
- enrollment checks prerequisite requirements before saving
- courses can be retrieved by student name
- Swagger/OpenAPI documentation is provided in Azerbaijani

## Technologies Used

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Cloud OpenFeign
- PostgreSQL
- Docker and Docker Compose
- Gradle
- Swagger/OpenAPI

## Project Structure

```text
university-system/
├── student-service/
├── course-service/
├── docker-compose.yml
├── build.gradle
├── settings.gradle
├── gradlew
└── gradlew.bat
```

## Services

### Student Service

The student service is responsible for:

- creating students
- retrieving students
- updating students
- deleting students
- searching students by name

Base URL:

```text
http://localhost:9090
```

### Course Service

The course service is responsible for:

- creating courses
- retrieving courses
- updating courses
- deleting courses
- enrolling students into courses
- storing enrollment dates
- validating prerequisite course requirements
- retrieving students enrolled in a course
- retrieving courses by student name

Base URL:

```text
http://localhost:8081
```

## How to Run with Docker

From the project root, run:

```bash
docker compose up --build
```

This starts:

- student PostgreSQL database
- course PostgreSQL database
- student-service
- course-service

To stop the services:

```bash
docker compose down
```

To stop the services and remove database volumes:

```bash
docker compose down -v
```

## Services and Ports

| Service | URL |
|---|---|
| student-service | `http://localhost:9090` |
| course-service | `http://localhost:8081` |
| student database | `localhost:5432` |
| course database | `localhost:5433` |

## Swagger URLs

Student Service Swagger:

```text
http://localhost:9090/swagger-ui/index.html
```

Course Service Swagger:

```text
http://localhost:8081/swagger-ui/index.html
```

## API Testing Examples

### Create a Student

Request:

```http
POST http://localhost:9090/api/v1/students
```

Body:

```json
{
  "firstName": "Nicat",
  "lastName": "Aliyev",
  "email": "nicat.aliyev@example.com",
  "age": 20
}
```

### Get All Students

Request:

```http
GET http://localhost:9090/api/v1/students
```

### Search Students by Name

Request:

```http
GET http://localhost:9090/api/v1/students/search?name=Nicat
```

This endpoint searches students by first name or last name.

### Create a Course Without Prerequisite

Request:

```http
POST http://localhost:8081/api/v1/courses
```

Body:

```json
{
  "title": "Java Basics",
  "code": "CS101",
  "credits": 4,
  "prerequisiteCourseId": null
}
```

If `prerequisiteCourseId` is `null`, the course does not require another course before enrollment.

### Create a Course With Prerequisite

Request:

```http
POST http://localhost:8081/api/v1/courses
```

Body:

```json
{
  "title": "Advanced Java",
  "code": "CS201",
  "credits": 4,
  "prerequisiteCourseId": 1
}
```

In this example, course `CS201` requires course ID `1` before enrollment.

### Get All Courses

Request:

```http
GET http://localhost:8081/api/v1/courses
```

### Enroll a Student Into a Course

Request:

```http
POST http://localhost:8081/api/v1/courses/1/students/1
```

Successful response example:

```json
{
  "enrollmentId": 1,
  "courseId": 1,
  "studentId": 1,
  "enrollmentDate": "2026-05-20",
  "message": "Student enrolled successfully."
}
```

### Get Students Enrolled in a Course

Request:

```http
GET http://localhost:8081/api/v1/courses/1/students
```

### Get Courses by Student Name

Request:

```http
GET http://localhost:8081/api/v1/courses/by-student-name?name=Nicat
```

This endpoint searches students by name and returns the courses associated with the matching students.

## Prerequisite Validation

Courses may have an optional `prerequisiteCourseId`.

If `prerequisiteCourseId` is `null`, the course has no prerequisite.

If `prerequisiteCourseId` has a value, the student must already be enrolled in that prerequisite course before enrolling in the selected course.

Example:

1. Course `Java Basics` has ID `1`.
2. Course `Advanced Java` has `prerequisiteCourseId` equal to `1`.
3. A student must first enroll in `Java Basics`.
4. Only after that, the student can enroll in `Advanced Java`.

If the student has not completed the prerequisite enrollment, the API returns a meaningful `400 Bad Request` response.

## Enrollment Date

When a student is enrolled into a course, the system stores the enrollment date in the enrollment record.

The enrollment date is returned in the enrollment response as:

```json
{
  "enrollmentDate": "2026-05-20"
}
```

The enrollment date belongs to the enrollment relationship because it describes when a specific student joined a specific course.

## Inter-Service Communication

The course service communicates with the student service to:

- validate that a student exists before enrollment
- retrieve student details for course-student responses
- search students by name when retrieving courses by student name

The project uses OpenFeign and RestTemplate for inter-service communication.

## Important Notes

The application uses Docker environment variables for database configuration when started with Docker Compose.

When running locally without Docker, make sure PostgreSQL databases and credentials match the `application.properties` files.

Recommended way to run the full project:

```bash
docker compose up --build
```

Recommended way to test endpoints:

- use Swagger UI
- or use Postman
- or use curl from terminal
