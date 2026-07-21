# 🎒 Campus Lost & Found Portal

A full-stack web application that helps students and staff report, search, and recover lost and found items within a campus environment.

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

# AI Face Verification Feature for Existing Campus Lost & Found Project

Implement an AI-powered Student Identity Verification feature into my existing Campus Lost & Found project without changing the current folder structure or breaking any existing functionality.

## Existing Project Structure

### Backend (Spring Boot)

```
backend/
└── src/main/java/com/campus/lostandfound/
    ├── config/
    ├── controller/
    ├── dto/
    ├── model/
    ├── repository/
    ├── security/
    ├── service/
    └── CampusLostFoundApplication.java
```

### Frontend (React + TypeScript + Vite)

```
frontend/
└── src/
    ├── components/
    ├── contexts/
    ├── hooks/
    ├── lib/
    ├── pages/
    │   ├── Dashboard.tsx
    │   ├── Login.tsx
    │   ├── Profile.tsx
    │   ├── Register.tsx
    │   └── ...
    ├── App.tsx
    └── main.tsx
```

---

## Service

Use

```
service/
```

Create or update

```
VerificationService.java
```

Responsibilities:

* Upload ID image
* Store image
* Face detection
* Face comparison
* Liveness verification
* Return confidence score
* Update verification status

---

## DTO

Inside

```
dto/
```

Create

```
FaceMatchRequest.java

FaceMatchResponse.java

VerificationStatusResponse.java
```

---


## Student ID Upload

Allow

✔ JPG

✔ PNG

✔ JPEG

Maximum

5 MB

Only ONE upload.

If uploaded already:

Show preview

Disable upload

Show

"Replace ID Card"

---


---

## Run AI Face Match

Button

Run AI Face Match

Disabled until

✔ ID uploaded

✔ Selfie captured

---


## Verification Rules

Score ≥ 85%

Verified Student

Green badge

Score 70–84%

Manual Review

Yellow badge

Below 70%

Verification Failed

Red badge

---


# Backend APIs

POST

```
/api/verification/upload-id
```

POST

```
/api/verification/capture-selfie
```

POST

```
/api/verification/face-match
```

GET

```
/api/verification/status
```

---

# Expected Flow

1. User opens Profile page.

2. Uploads Student ID.

3. ID preview appears.

4. Camera opens.

5. User captures selfie.

6. Selfie preview appears.

7. User clicks **Run AI Face Match**.

8. Liveness detection runs.

9. AI compares both faces.

10. Match score is calculated.

11. Verification result is displayed.

12. User profile is updated with a **Verified Student** badge if the score is 85% or higher.

Ensure the implementation integrates cleanly with the existing Spring Boot backend and React frontend, follows the current architecture, and does not modify unrelated modules or break existing functionality.

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

<img width="1468" height="875" alt="image" src="https://github.com/user-attachments/assets/e0316d93-47d4-4119-a38b-b8b4586107a2" />
<img width="1467" height="878" alt="image" src="https://github.com/user-attachments/assets/e4ebbc5c-272e-4388-815d-a7c8a41bd714" />
<img width="1470" height="832" alt="image" src="https://github.com/user-attachments/assets/a0790b9d-3cf2-466d-9c92-bb73b4cfd69d" />
Profile
<img width="1470" height="831" alt="image" src="https://github.com/user-attachments/assets/1b5426c2-1b67-4944-b2de-b3cc873c6a81" />
<img width="1470" height="879" alt="image" src="https://github.com/user-attachments/assets/dff7f6da-ca25-494b-af0c-8de9d6971367" />

---

## 👨‍💻 Authors

### Bobbili Chandana Sri
[![GitHub](https://img.shields.io/badge/GitHub-chandanasri26-181717?style=flat-square&logo=github)](https://github.com/chandanasri26)

---

# ⭐ Support

If you found this project useful, please consider giving it a ⭐ on GitHub.
