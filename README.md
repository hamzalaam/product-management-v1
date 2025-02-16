Product Management Microservice
Overview
This project is a lightweight RESTful microservice designed to manage products and users. Built with Spring Boot 3 and Java 21, it leverages a PostgreSQL database and adheres to SOLID principles for clean, maintainable, and scalable code. The service includes features for product management and user creation, with additional functionality planned for future implementation.

The project also integrates Swagger for API documentation and code generation, ensuring clarity and ease of use. Detailed comments are included throughout the codebase to simplify understanding and onboarding.

Features
Implemented Features
Product Management: Create, read, update, and delete (CRUD) operations for products.

User Management: User creation and basic user-related operations.

Swagger Integration: Automatically generated API documentation and code.

SOLID Principles: Clean, modular, and maintainable codebase.

Pending Features
User Shopping Cart: Endpoints for managing user shopping carts.

Wishlist: Endpoints for managing user wishlists.

Prerequisites
Before running the project, ensure the following are installed and configured:

Java 21: Download and install Java 21.

Maven: Install Maven for dependency management and building the project.

PostgreSQL:

Install PostgreSQL and run he command below to create a database named productmanagement_dev

sql:

CREATE DATABASE productmanagement_dev;


API Testing Tool: Use Bruno or Postman to test API endpoints.

Getting Started
1. Clone the Repository

git clone <repository-url>
cd <project-directory>
2. Configure the Database if needed
Update the application.yml file with your PostgreSQL credentials:

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/productmanagement_dev
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
    

3 mvn clean install

4. Build and Run the Project

APIs implemented : 

Products Management:
GET /api/products/{id}: Retrieve a specific product by ID.
POST /api/products: Create a new product.
PUT /api/products/{id}: Update an existing product.
DELETE /api/products/{id}: Delete a product.

User Management
POST /account: Create a new user.
POST /token: Retrieve a specific user token by email and password.



Code Quality and Best Practices
SOLID Principles: The codebase is designed with SOLID principles in mind, ensuring modularity and scalability.

Swagger Code Generation: API documentation and code are auto-generated, reducing manual effort and improving consistency.

Detailed Comments: The code is well-commented to simplify understanding and maintenance.

Microservice architecture : the code is complying to 

Future Enhancements:
Implement Shopping Cart functionality to allow users to manage their carts.

Add Wishlist endpoints for users to save and manage their favorite products.

Enhance error handling and logging for better debugging and monitoring.
