# 🎒 Campus Lost & Found Portal

A full-stack web application that helps students and staff report, search, and recover lost and found items within a campus environment.

## 🚀 Live Demo

* **Frontend:** *Add your Vercel URL here*
* **Backend API:** *Add your Render URL here*

---

## 📌 Project Overview

Campus Lost & Found Portal is a centralized platform where users can:

* Register and log in securely
* Report lost items
* Report found items
* Search available items
* Upload item images
* Chat with item owners
* Manage personal profile
* Track item status
* Enable administrators to monitor platform activity

The system simplifies the recovery process and improves communication between students and campus staff.

---

# ✨ Features

### 👤 User Authentication

* Secure Registration
* Secure Login
* JWT Authentication
* Protected Routes

### 🎒 Lost & Found Management

* Post Lost Items
* Post Found Items
* Upload Images
* Edit/Delete Own Posts
* Search Items

### 💬 Messaging

* User-to-user chat
* Contact item owner
* Conversation history

### 👨‍💼 Profile Management

* Update profile
* View posted items
* Manage account information

### 🔒 Security

* JWT Token Authentication
* Password Encryption
* Role-based authorization
* CORS Configuration

---

# 🛠️ Tech Stack

## Frontend

* React
* TypeScript
* Vite
* Tailwind CSS
* Axios

## Backend

* Spring Boot
* Spring Security
* JWT Authentication
* Maven

## Database

* MongoDB Atlas

## Deployment

* Vercel (Frontend)
* Render (Backend)

---

# 📂 Project Structure

```
campus-lost-and-found/
│
├── frontend/
│   ├── src/
│   ├── components/
│   ├── pages/
│   └── public/
│
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── security/
│   └── config/
│
└── README.md
```

---

# ⚙️ Installation

## Clone Repository

```bash
git clone https://github.com/chandanasri26/campustrack-lost-and-found-.git
```

## Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend runs on:

```
http://localhost:8080/api
```

---

## Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on:

```
http://localhost:3000
```

or

```
http://localhost:5173
```

---

# 🌐 Environment Variables

Backend:

```
spring.data.mongodb.uri=YOUR_MONGODB_URI
jwt.secret=YOUR_SECRET_KEY
```

Frontend:

```
VITE_API_URL=http://localhost:8080/api
```

---

# 📸 Screenshots

Add screenshots of:

* Home Page
* Login Page
* Register Page
* Lost Items
* Found Items
* Chat
* Profile
* Post Item

---

# 🎯 Future Enhancements

* Email Notifications
* AI Image Matching
* QR Code Item Tracking
* Admin Dashboard Analytics
* Mobile Application

---

# ⭐ Support

If you found this project useful, please consider giving it a ⭐ on GitHub.
