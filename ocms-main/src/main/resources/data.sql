-- Insert sample users
INSERT INTO users (username, email, role) VALUES 
('john_instructor', 'john@example.com', 'INSTRUCTOR'),
('jane_instructor', 'jane@example.com', 'INSTRUCTOR'),
('alice_student', 'alice@example.com', 'STUDENT'),
('bob_student', 'bob@example.com', 'STUDENT'),
('charlie_student', 'charlie@example.com', 'STUDENT');

-- Insert sample courses
INSERT INTO courses (instructor_id, title, description) VALUES 
(1, 'Introduction to Java', 'Learn the basics of Java programming'),
(1, 'Advanced Spring Boot', 'Master Spring Boot framework'),
(2, 'Database Design', 'Learn how to design efficient databases');

-- Insert sample modules
INSERT INTO modules (course_id, module_title, content, order_index) VALUES 
(1, 'Java Basics', 'Variables, data types, and control structures', 1),
(1, 'Object-Oriented Programming', 'Classes, objects, inheritance, and polymorphism', 2),
(1, 'Exception Handling', 'Try-catch blocks and custom exceptions', 3),
(2, 'Spring Boot Fundamentals', 'Auto-configuration and starter dependencies', 1),
(2, 'REST APIs', 'Building RESTful web services', 2),
(3, 'Database Normalization', 'First, second, and third normal forms', 1),
(3, 'SQL Optimization', 'Query optimization techniques', 2);

-- Insert sample assignments
INSERT INTO assignments (course_id, title, description, due_date, max_score) VALUES 
(1, 'Hello World Program', 'Create a simple Hello World program in Java', '2024-02-15', 100.0),
(1, 'Calculator Application', 'Build a basic calculator with OOP principles', '2024-02-28', 150.0),
(2, 'REST API Project', 'Create a complete REST API using Spring Boot', '2024-03-15', 200.0),
(3, 'Database Schema Design', 'Design a normalized database schema', '2024-03-10', 120.0);

-- Insert sample submissions
INSERT INTO submissions (assignment_id, user_id, content, submitted_on, score) VALUES 
(1, 3, 'public class HelloWorld { public static void main(String[] args) { System.out.println("Hello World!"); } }', '2024-02-10 10:30:00', 95.0),
(1, 4, 'class Hello { public static void main(String[] args) { System.out.println("Hello World"); } }', '2024-02-12 14:20:00', 90.0),
(1, 5, 'public class Main { public static void main(String[] args) { System.out.println("Hello, World!"); } }', '2024-02-14 09:15:00', 100.0),
(2, 3, 'Calculator class with add, subtract, multiply, divide methods', '2024-02-25 16:45:00', 140.0),
(2, 4, 'Basic calculator implementation with error handling', '2024-02-27 11:30:00', 135.0),
(3, 3, 'Spring Boot REST API with CRUD operations', '2024-03-12 13:20:00', 180.0),
(4, 3, 'Normalized database schema with proper relationships', '2024-03-08 15:10:00', 110.0),
(4, 4, 'Database design following 3NF principles', '2024-03-09 12:00:00', 115.0);