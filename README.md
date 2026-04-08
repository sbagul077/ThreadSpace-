# 🚀 Reddit App (Full Stack)

A full-stack Reddit-inspired web application where users can create posts, join communities, and interact through comments and voting.

This project demonstrates end-to-end development using **Spring Boot (backend)** and **Angular (frontend)**.

---

##  Tech Stack

###  Backend

* Java
* Spring Boot
* Spring Security + JWT Authentication
* Hibernate / JPA
* REST APIs

###  Frontend

* Angular
* TypeScript
* Bootstrap / Custom CSS

---

##  Features

###  Authentication

* User Signup & Login
* JWT-based authentication
* Secure API endpoints

###  Posts

* Create, view, and delete posts
* Global feed
* View posts by user

###  Comments

* Add comments to posts
* View all comments per post

###  Voting System

* Upvote / Downvote functionality
* Real-time vote updates

###  Communities (Subreddit)

* Create and browse communities
* View posts by community

###  User Profile

* View user-specific posts
* Navigate to user profiles

---

##  Project Structure

```
reddit-app/
│
├── backend/        # Spring Boot application
├── frontend/       # Angular application
├── docs/
│   └── screenshots/
│       ├── create-post.png
│       ├── create-subreddit.png
│       ├── home.png
│       ├── login.png
│       └── profile.png
└── README.md
```

##  Screenshots

| Home Feed                      | Login Page                            |
| ------------------------------ | ------------------------------------- |
| ![](docs/home.png)             | ![](docs/login.png)                   |

| Create Post                           | Create Subreddit                           |
| ------------------------------------- | ------------------------------------------ |
| ![](docs/create-post.png)             | ![](docs/create-subreddit.png)             |

---


##  Setup Instructions

###  Backend (Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Runs on: `http://localhost:8080`

---

###  Frontend (Angular)

```bash
cd frontend
npm install
ng serve
```

Runs on: `http://localhost:4200`

---

##  API Endpoints (Sample)

| Method | Endpoint         | Description   |
| ------ | ---------------- | ------------- |
| POST   | /api/auth/signup | Register user |
| POST   | /api/auth/login  | Login user    |
| GET    | /api/posts       | Get all posts |
| POST   | /api/posts       | Create post   |
| POST   | /api/comments    | Add comment   |

---

##  Key Learnings

* Implemented JWT-based authentication and authorization
* Built scalable REST APIs using Spring Boot
* Applied component-based architecture in Angular
* Managed state and routing in frontend
* Designed full-stack integration between frontend and backend

---

##  Future Improvements

* Edit/Delete comments
* Real-time notifications
* Infinite scrolling feed
* Dark mode UI
* Deployment (AWS / Docker)

---

##  Author

**Sanket Bagul**
UX Designer & Developer

---

##  Show Your Support

If you like this project, give it a ⭐️ on GitHub!
