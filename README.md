# Interview Demo 🧩
Thank you for reviewing my interview project. I hope that this work meets your expectations despite taking only 2-3 hours for me to complete it. I'm looking forward to discussing this project (structure, design, trade-offs, decisions, and even potential bugs) in detail in our further interview. 
This project is a demo project showcasing API development, validation, error handling, and best practices.

## 🚀 Overview

This repository contains a Spring Boot–based demo application built to highlight clean coding practices, REST API design, input validation, and foundational backend patterns commonly discussed in technical interviews.  
It is intentionally structured to be easy to read, review, and extend.

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot** (Web, Validation, Actuator)
- **Spring Web MVC**
- **Maven**
- **Lombok** (if used in project)
- **JUnit 5** (if tests included)

---

## ✨ Features

### ✅ RESTful API Design  
Includes clean controller → service → model architecture demonstrating separation of concerns and maintainability.

### ✅ Input Validation  
Demonstrates real‑world validation patterns, these are implemented using a combination of Java code, validation classes, and configuration files. These patterns include (but are not limited to):

- Date of Birth format (`yyyy-MM-dd`)
- SSN (with and without dashes)
- U.S. State code validation
- Custom validator annotations

### ✅ Utility Logic  
Reusable Java utilities such as:
- UUID generation
- Age calculation from DOB
- Date manipulation (current date minus 5 years)
- Regex‑based validation utilities

### ✅ Error Handling  
Centralized `@RestControllerAdvice` for consistent, interview‑ready error responses.

### ✅ Actuator Endpoints  
Application exposes health and diagnostic endpoints for operational visibility.

---

## 📂 Project Structure:
<img width="339" height="1226" alt="image" src="https://github.com/user-attachments/assets/83ccf08d-e224-4cb3-9f93-a97c131f9da5" />
