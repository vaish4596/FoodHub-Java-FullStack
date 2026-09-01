# 🍔 FoodHub — Java Full Stack Food Delivery Application

<p align="center">

**A Java-based full-stack food delivery web application built with JSP, Servlets, JDBC, MySQL and modern web technologies.**

</p>

<p align="center">

<img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
<img src="https://img.shields.io/badge/JSP-Servlets-8A2BE2?style=for-the-badge" alt="JSP">
<img src="https://img.shields.io/badge/JDBC-MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="JDBC MySQL">
<img src="https://img.shields.io/badge/HTML-CSS-JavaScript-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML CSS JavaScript">
<img alt="Static Badge" src="https://img.shields.io/badge/HTML%20and%20css">

<img src="https://img.shields.io/badge/Tomcat-10-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black" alt="Tomcat">
<img src="https://img.shields.io/badge/Google-OAuth2-4285F4?style=for-the-badge&logo=google&logoColor=white" alt="Google OAuth">
<img src="https://img.shields.io/badge/BCrypt-Password%20Hashing-2F2F2F?style=for-the-badge" alt="BCrypt">

</p>

---

## 📌 Overview

**FoodHub** is a full-stack food delivery web application developed using Java EE technologies.

The application allows users to browse restaurants, explore menus, manage their cart, place food orders and securely authenticate using traditional email/password login or Google OAuth 2.0.

The project follows a layered architecture with separate **Servlet, DAO, DAO Implementation and Model** components for better maintainability and separation of concerns.

---

## ✨ Features

### 🔐 Authentication & Authorization

* User registration
* Email/password login
* Secure password hashing using **BCrypt**
* Session-based authentication
* Logout functionality
* Google OAuth 2.0 Login
* Automatic FoodHub account creation for new Google users

### 🍽️ Restaurant & Menu

* Browse restaurants
* View restaurant menus
* Search/filter restaurants
* View menu item details
* Dynamic quantity selection

### 🛒 Shopping Cart

* Add items to cart
* Increase/decrease quantity
* Remove items
* Session-based cart management
* Automatic subtotal calculation

### 💳 Checkout

* Order summary
* Subtotal calculation
* Delivery charge calculation
* GST calculation
* Grand total calculation
* Payment method selection

### 📦 Orders

* Place orders
* Generate order records
* Store individual order items
* View previous orders
* Maintain order history

### 🗄️ Database

* MySQL relational database
* JDBC-based database connectivity
* DAO pattern for database operations
* Prepared statements for database queries

---

# 🛠️ Tech Stack

| Category              | Technologies                         |
| --------------------- | ------------------------------------ |
| Language              | Java                                 |
| Backend               | JSP, Servlets                        |
| Database              | MySQL                                |
| Database Connectivity | JDBC                                 |
| Frontend              | HTML, CSS, JavaScript                |
| Server                | Apache Tomcat 10                     |
| Authentication        | Session Management, Google OAuth 2.0 |
| Security              | BCrypt Password Hashing              |
| IDE                   | Eclipse                              |
| Build Tool            | Maven                                |
| Version Control       | Git & GitHub                         |

---

# 🏗️ Architecture

FoodHub follows a layered architecture inspired by the MVC pattern.

```text
                    ┌─────────────────────┐
                    │      Frontend       │
                    │ HTML / CSS / JS/JSP │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Servlets       │
                    │  Request Handling   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        DAO          │
                    │ Database Operations │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        JDBC         │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       MySQL         │
                    └─────────────────────┘
```

---

# 🔑 Google OAuth Login

FoodHub supports **Login with Google** using OAuth 2.0.

### Authentication Flow

```text
User
  │
  ▼
FoodHub Login Page
  │
  ▼
Google Login Button
  │
  ▼
GoogleLoginServlet
  │
  ▼
Google OAuth Authorization
  │
  ▼
GoogleCallbackServlet
  │
  ▼
Fetch Google User Information
  │
  ▼
Check User in MySQL
  │
  ├── Existing User
  │        ↓
  │     Login
  │
  └── New User
           ↓
       Create Account
           ↓
       Create Session
           ↓
       Home Page
```

### OAuth Configuration

Create:

```text
WEB-INF/google-oauth.properties
```

using the provided example:

```text
WEB-INF/google-oauth.properties.example
```

Configure:

```properties
google.clientId=YOUR_GOOGLE_CLIENT_ID
google.clientSecret=YOUR_GOOGLE_CLIENT_SECRET
google.redirectUri=http://localhost:8080/firstJEEproject/oauth2callback
```

> ⚠️ Never commit your actual Google Client Secret to GitHub.

---

# 🗃️ Database Design

The application uses MySQL to manage:

* Users
* Restaurants
* Menu items
* Orders
* Order items

The database script is available in:

```text
database/foodhub.sql
```

Import the SQL file into MySQL before running the application.

---

# 🚀 Getting Started

## 1. Clone the repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

```bash
cd FoodHub
```

---

## 2. Create the MySQL database

Open MySQL and run:

```sql
CREATE DATABASE foodhub;
```

Then import:

```text
database/foodhub.sql
```

---

## 3. Configure database connection

Update your JDBC configuration with your local MySQL details.

Example:

```text
URL      : jdbc:mysql://localhost:3306/foodhub
Username : root
Password : YOUR_PASSWORD
```

Do not commit your actual database password.

---

## 4. Configure Google Login

Copy:

```text
WEB-INF/google-oauth.properties.example
```

to:

```text
WEB-INF/google-oauth.properties
```

Add your Google OAuth credentials.

Create a Google OAuth 2.0 Web Application client and configure the redirect URI:

```text
http://localhost:8080/firstJEEproject/oauth2callback
```

Make sure the redirect URI exactly matches the value configured in Google Cloud.

---

## 5. Configure Tomcat

Use:

```text
Apache Tomcat 10+
```

Deploy the application to Tomcat.

---

## 6. Run the application

Start the Tomcat server and open:

```text
http://localhost:8080/firstJEEproject/
```

---

# 📸 Screenshots

### 🏠 Home Page

![FoodHub Home](screenshots/home.png)

### 🔐 Login

![FoodHub Login](screenshots/login.png)

### 🍕 Menu

![FoodHub Menu](screenshots/menu.png)

### 🛒 Cart

![FoodHub Cart](screenshots/cart.png)

### 💳 Checkout

![FoodHub Checkout](screenshots/checkout.png)

### 📦 Orders

![FoodHub Orders](screenshots/orders.png)

---

# 📂 Project Structure

```text
FoodHub
│
├── src
│   └── main
│       └── java
│           └── com.tap
│               ├── DAO
│               ├── DAOImpl
│               ├── model
│               ├── utility
│               ├── LoginServlet.java
│               ├── RegisterServlet.java
│               ├── LogoutServlet.java
│               ├── CartServlet.java
│               ├── CheckoutServlet.java
│               ├── OrderServlet.java
│               ├── GoogleLoginServlet.java
│               └── GoogleCallbackServlet.java
│
├── WebContent
│   ├── WEB-INF
│   ├── login.html
│   ├── register.html
│   ├── home.jsp
│   ├── menu.jsp
│   ├── cart.jsp
│   ├── checkout.jsp
│   └── orders.jsp
│
├── database
│   └── foodhub.sql
│
├── screenshots
│   ├── home.png
│   ├── login.png
│   ├── menu.png
│   ├── cart.png
│   ├── checkout.png
│   └── orders.png
│
├── .gitignore
├── pom.xml
└── README.md
```

---

# 🔒 Security Practices

FoodHub implements several security practices:

* BCrypt password hashing
* Session-based authentication
* Prepared statements for database operations
* OAuth 2.0 authentication
* Secrets excluded from version control
* Separate configuration template for local credentials

---

# 🧠 Key Concepts Demonstrated

This project demonstrates practical knowledge of:

* Java OOP
* MVC architecture
* Servlets & JSP
* JDBC
* DAO design pattern
* HTTP request/response handling
* Session management
* Authentication & authorization
* OAuth 2.0
* Password hashing
* CRUD operations
* SQL joins and relational database design
* Exception handling
* Maven
* Tomcat deployment
* Git & GitHub

---

# 🔮 Future Improvements

Planned improvements include:

* [ ] Spring Boot migration
* [ ] Spring Security
* [ ] JWT authentication
* [ ] REST API architecture
* [ ] Online payment gateway integration
* [ ] Admin dashboard
* [ ] Restaurant owner dashboard
* [ ] Order tracking
* [ ] Email notifications
* [ ] Docker deployment
* [ ] Cloud deployment
* [ ] Automated unit and integration testing

---

# 👩‍💻 Developer

**Vaishnavi Shetty**

B.Tech – Information Science & Engineering

**Java Full Stack Developer | Spring Boot | REST APIs | MySQL | React | Docker**

Interested in backend development, scalable applications and modern software technologies.

---

## ⭐ If you found this project useful

Give the repository a ⭐ and feel free to explore the code.

---

## 📄 License

This project is developed for educational and portfolio purposes.

