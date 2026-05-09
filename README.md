# 📱 Snapchat Clone 

A full-stack Android social media clone inspired by Snapchat, built using **Jetpack Compose**, **Firebase Authentication**, **Firestore**, and **Firebase Storage**. 
The app supports real-time messaging, story sharing, camera integration, and user profiles.

---

## 🚀 Features

### 🔐 Authentication
- Email/password sign up and login (Firebase Auth)
- Profile setup with username + avatar selection
- Persistent login session handling

### 💬 Real-time Chat
- 1:1 messaging system using Firestore
- Live message updates with snapshot listeners
- Chat creation based on user pairing

### 📸 Camera & Media
- Camera preview using CameraX
- Tap-to-focus support
- Capture and upload images to Firebase Storage

### 📖 Stories System
- Upload image-based stories
- Stories displayed in horizontal feed (LazyRow)
- Real-time updates using Firestore listeners
- 24-hour filtering logic for active stories

### 👤 Profile System
- User profile page with avatar support
- Logout and session reset support

---

## 🧱 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Firebase Authentication**
- **Firebase Firestore**
- **Firebase Storage**
- **CameraX**
- **StateFlow + MVVM Architecture**
- **Navigation Compose**

---

## 🏗 Architecture

- MVVM (Model–View–ViewModel)
- Repository pattern for data layer
- Reactive UI using StateFlow
- Modular navigation graph structure
- Separation of Auth and Main app flows

---

## 📷 Images

<p>
<img width="250" alt="Screenshot_20260508_011546" src="https://github.com/user-attachments/assets/17b87429-1137-4f01-8b4f-74932cfaf705" />
<img width="250" alt="Screenshot_20260508_140602" src="https://github.com/user-attachments/assets/7a7c6a46-4b17-4431-a4dd-d93648342f23" />
<img width="250" alt="Screenshot_20260508_011726" src="https://github.com/user-attachments/assets/fc4a45f3-b038-489f-9169-e7d5b148b8bc" />
<img width="250" alt="Screenshot_20260508_011708" src="https://github.com/user-attachments/assets/8e4dca5e-1480-4a6b-b153-1e051bd165f9" />
<img width="250" alt="Screenshot_20260508_125246" src="https://github.com/user-attachments/assets/41676afa-d455-48fa-9544-de390dff4672" />
<img width="250" alt="Screenshot_20260508_140535" src="https://github.com/user-attachments/assets/b3655e04-aad1-48b0-a178-aa6380a93f3b" />

</p>

---

## 🧪 Test the App

### 1. Clone the repository

git clone https://github.com/ronnyppp/snapchat-clone.git

cd snapchat-clone

### 2. Open project in Android Studio

Run the app with emulator

---

## 📄 License

This project is for educational and portfolio purposes.
