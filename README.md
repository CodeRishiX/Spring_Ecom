# SpringCart - E-commerce Application

## Overview
SpringCart is a modern e-commerce web application built with Spring Boot, featuring product management, user authentication via Google OAuth, and a responsive frontend. Deployed on Render, it includes Swagger for API documentation and integrates with a MySQL database hosted on `https://aiven.io/`.

## Features
- **Product Management**: View, add, update, and delete products with admin controls.
- **User Authentication**: Secure login with Google OAuth.
- **Shopping Cart**: Basic cart functionality.
- **Responsive Design**: Mobile-friendly UI with Bootstrap.
- **API Documentation**: Swagger UI for exploring endpoints.
- **Deployment**: Live at `https://springcart.onrender.com`.

## Technologies
- **Backend**: Spring Boot, Hibernate, JPA
- **Database**: MySQL
- **Frontend**: HTML, CSS, JavaScript, Bootstrap
- **Authentication**: Spring Security with Google OAuth
- **Documentation**: Swagger
- **Hosting**: Render

## Prerequisites
- Java 21
- Maven
- MySQL
- Git
- Google Cloud Console (for OAuth setup)

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Debangshu6/Ecommerce.git
   cd Ecommerce
   ```
2. Configure `application.properties`:
   - Update `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` with your MySQL credentials.
   - Set `spring.security.oauth2.client.registration.google.client-id` and `spring.security.oauth2.client.registration.google.client-secret` from Google Cloud Console.
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run locally:
   ```bash
   mvn spring-boot:run
   ```

## Deployment
- Deployed on Render: `https://springcart.onrender.com`
- Environment variables set in Render dashboard (e.g., `SPRING_DATASOURCE_URL`, `GOOGLE_CLIENT_ID`).
- Redeploy via GitHub push or manual deploy on Render.

## API Documentation
- Access Swagger UI at `https://springcart.onrender.com/swagger-ui.html` (post-deployment).
- Endpoints include `/api/products`, `/api/auth/status`, `/api/cart`, etc.

## Usage
- Browse products at the homepage.
- Sign in with Google to access admin features (e.g., add/update products).
- Use the search bar to filter products.


## Contact
- Email: debangshubhattacharya4@gmail.com

