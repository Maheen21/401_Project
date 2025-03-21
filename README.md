## 🌐 Introduction

**Dishcraft** is a full-stack, three-layer web application designed to deliver a seamless and intelligent user experience through a modern tech stack. Built with a React-based frontend, a robust Spring Boot API server, and a Python-powered LLM API endpoint, Dishcraft leverages a MySQL relational database to manage structured data efficiently.

The system architecture separates concerns into three distinct layers:

- **Frontend (React)**: A responsive and interactive user interface that communicates with backend services via REST APIs.
- **Backend (Spring Boot)**: A scalable and secure API server that handles business logic, authentication, and database operations.
- **AI Layer (Python LLM)**: A dedicated service that provides natural language processing capabilities, offering intelligent features powered by large language models.

Dishcraft is designed with modularity, scalability, and maintainability in mind, making it ideal for teams looking to integrate AI-enhanced features into a traditional web application framework.


## 🎯 Purpose

**Dishcraft** is an AI-powered recipe platform designed to enhance the cooking experience by offering intelligent ingredient suggestions and detailed, personalized cooking instructions.

The goal of this project is to:

- Provide users with alternative ingredient suggestions based on what they have at home or dietary preferences.
- Generate step-by-step cooking instructions using AI, based on minimal recipe data stored in the database.
- Offer a lightweight, scalable solution for managing and enhancing recipe content using large language models (LLMs).

The backend database stores essential recipe data, including:

- Cooking time  
- Short description  
- Required ingredients  
- Concise cooking instructions  

From this minimal data, Dishcraft leverages AI to deliver a richer, more interactive and adaptive cooking assistant experience.


## 🧩 Backend (Spring Boot)

The backend of **Dishcraft** is built with **Spring Boot**, serving as the core API layer that manages recipes, ingredients, and user authentication. It provides structured data to the frontend and supports AI-based enhancement via minimal, clean endpoints.

This layer ensures secure, modular, and scalable data management for the application.

### 🔧 Responsibilities

- Exposes RESTful APIs for recipe and ingredient management
- Supports flexible recipe search by ingredient filters and modes
- Handles user authentication and token-based authorization using JWT
- Provides structured data (recipe titles, ingredients, short instructions) to the frontend for AI-powered enhancement
- Manages admin-level (root) permissions for content modification

### ⚙️ Tech Stack

- **Spring Boot**
- **Spring Web** (RESTful API)
- **Spring Data JPA** (MySQL)
- **Spring Security + JWT**
- **Swagger / SpringDoc** for API documentation

### 📌 Core Features

- **Recipe Management**: Create, update, delete, view recipes
- **Ingredient Management**: Add, update, delete, and search ingredients
- **Search API**: Filter recipes by selected ingredient IDs and mode (`all` / `any`)
- **User Authentication**: Register, login, refresh token via secure JWT handling
- **Pagination Support**: Use Spring's `Pageable` for paginated recipe queries
- **Swagger API Docs**: Interactive documentation accessible via `/swagger-ui.html`

> Only users with **root** privileges are allowed to create, modify, or delete content.

### 🔐 Security & Roles

- JWT-based authentication and stateless session handling
- Admin role (`root`) enforced on protected endpoints
- Token refresh supported via a dedicated endpoint

### 🛠️ Environment Configuration

Dishcraft's backend is configurable for multiple environments. All key properties (e.g., database credentials, JWT secrets, CORS settings, etc.) can be overridden using **environment variables**, enabling secure and flexible deployment.

> For example, in production:
> - `JWT_SECRET`
> - `SPRING_DATASOURCE_PASSWORD`
> - `SPRING_DATASOURCE_URL`
> - `SPRING_DATASOURCE_USERNAME`
> - `CORS_ALLOWED_ORIGINS`
> - `OPENAPI_SERVER_URL`

This allows the application to run smoothly across local, development, and production environments without changing source code.

### Prerequisite
this git is configured as the runs in development environment require mysql runs in default port which has user root with pw root 

---
This backend service forms the foundation of the Dishcraft application, providing clean APIs and secure data handling while supporting integration with intelligent frontend and AI services.




