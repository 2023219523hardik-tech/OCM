# Online Course Management System (OCMS)

A modular Spring Boot application for managing online courses, assignments, and student submissions.

## 🏗️ Architecture

The application follows a modular architecture with the following modules:

- **common**: Shared DTOs, exceptions, and utilities
- **user-management**: User registration and management
- **course-management**: Course and module management with LinkedList structure
- **assignment-management**: Assignment creation and submission handling with PriorityQueue
- **reporting-module**: Analytics and reporting with TreeMap for sorted data
- **ocms-main**: Main application module

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Java Version**: 17

## 📊 Data Structures Used

- **HashMap**: User enrollments and course modules storage
- **LinkedList**: Course modules ordering (Module 1 → Module 2 → ...)
- **PriorityQueue**: Assignment submissions ordered by submission date
- **TreeMap**: Sorted reporting data by date

## 🗃️ Database Schema

### Tables:
- `users` (user_id, username, email, role)
- `courses` (course_id, instructor_id, title, description)
- `modules` (module_id, course_id, module_title, content, order_index)
- `assignments` (assignment_id, course_id, title, description, due_date, max_score)
- `submissions` (submission_id, assignment_id, user_id, content, submitted_on, score, feedback)

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd online-course-management-system
   ```

2. **Configure MySQL Database**
   - Create a MySQL database named `ocms_db`
   - Update database credentials in `ocms-main/src/main/resources/application.yml`

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   cd ocms-main
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## 📚 API Endpoints

### User Management
- `POST /api/users/register` - Register a new user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username

### Course Management
- `POST /api/courses` - Create a new course (instructors only)
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course
- `POST /api/courses/{id}/modules` - Add module to course
- `GET /api/courses/{id}/modules` - Get course modules

### Assignment Management
- `POST /api/assignments` - Create assignment
- `GET /api/assignments` - Get all assignments
- `GET /api/assignments/{id}` - Get assignment by ID
- `POST /api/assignments/{id}/submit` - Submit assignment
- `PUT /api/assignments/submissions/{id}/grade` - Grade submission
- `GET /api/assignments/submissions/ordered` - Get submissions ordered by date

### Reporting
- `GET /api/reports` - Get detailed reports
- `GET /api/reports/course-completion` - Course completion rates
- `GET /api/reports/assignment-performance` - Assignment performance statistics
- `GET /api/reports/submissions-by-date` - Submissions grouped by date
- `GET /api/reports/overall-statistics` - Overall system statistics

## 🧪 Sample API Requests

### Register a User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "email": "student1@example.com",
    "role": "STUDENT"
  }'
```

### Create a Course
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "instructorId": 1,
    "title": "Spring Boot Fundamentals",
    "description": "Learn Spring Boot from scratch"
  }'
```

### Submit Assignment
```bash
curl -X POST http://localhost:8080/api/assignments/1/submit \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 3,
    "content": "My assignment solution here..."
  }'
```

### Get Reports
```bash
curl -X GET http://localhost:8080/api/reports
```

## 🎯 Key Features

### Data Structure Implementation
- **LinkedList for Modules**: Maintains ordered sequence of course modules
- **HashMap for Users**: Fast lookup for user enrollment tracking
- **PriorityQueue for Submissions**: Automatic ordering by submission date
- **TreeMap for Reports**: Sorted analytics by date

### Business Logic
- Role-based operations (Students can submit, Instructors can create)
- Assignment deadline validation
- Duplicate submission prevention
- Automatic grading workflow

### Reporting & Analytics
- Course completion rates
- Assignment performance statistics
- Submission trends over time
- Overall system metrics

## 🔧 Configuration

### Database Configuration
The application uses MySQL with the following default settings:
- URL: `jdbc:mysql://localhost:3306/ocms_db`
- Username: `root`
- Password: `password`

### Sample Data
The application includes sample data for testing:
- 2 Instructors, 3 Students
- 3 Courses with modules
- 4 Assignments with submissions

## 📈 Monitoring

The application provides comprehensive logging and includes:
- SQL query logging
- Debug level logging for application components
- Error handling with custom exceptions

## 🚫 Security Note

This application does not include authentication or authorization features as per requirements. All endpoints are publicly accessible.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is for educational purposes.