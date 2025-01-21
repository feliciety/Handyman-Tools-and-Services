# 🛠️ HandyMan Tools and Services

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-007396?style=for-the-badge&logo=java&logoColor=white)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow?style=for-the-badge)
![Contributions](https://img.shields.io/badge/Contributions-Welcome-brightgreen?style=for-the-badge)

Welcome to **HandyMan Tools and Services**! 🚀  
This Java-based desktop application is designed to streamline the process of booking services, hiring skilled workers, and renting tools. Built with **JavaFX** for a rich user interface and utilizing **XAMPP** for database management, this application is currently a work-in-progress but already offers several key features to enhance user experience.


 <div style="display: flex; flex-wrap: wrap; gap: 50px;">
  <img src="/src/main/resources/project/demo/applicationImage/Home.jpg" alt="Profile Page Shipping Address Edit" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/AboutUs.jpg" alt="Profile Page Payment Method Edit" width="48%">
  
</div>

---

## 📖 **Table of Contents**
- [🌟 Features](#-features)
- [🚧 What’s Missing?](#-whats-missing)
- [💡 Future Enhancements](#-future-enhancements)
- [🛠️ Tech Stack](#️-tech-stack)
- [🔧 Setup & Installation](#-setup--installation)
- [🤝 Contribute](#-contribute)
- [📜 License](#-license)

---

## 🌟 **Features**

### 👤 **User Profile Page**
- **Manage Personal Information**: Update your personal details effortlessly.
- **Payment Methods & Addresses**: Store and modify your payment methods and addresses for seamless transactions.
- **Service & Order History**: Access a comprehensive history of your service bookings and tool rentals.

<div style="display: flex; flex-wrap: wrap; gap: 50px;">
 <img src="/src/main/resources/project/demo/applicationImage/ProfileEdit.png" alt="Profile Page Shipping Order History" width="48%"> 
 <img src="/src/main/resources/project/demo/applicationImage/PAymentmethosedit.jpg" alt="Profile Page Payment Method Edit" width="48%">
 <img src="/src/main/resources/project/demo/applicationImage/ShippingAddressedit.jpg" alt="Profile Page Shipping Address Edit" width="48%">
 <img src="/src/main/resources/project/demo/applicationImage/orderhsitory.jpg" alt="Profile Page Shipping Order History" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/Service History.jpg" alt="Profile Page Shipping Order History" width="48%">
   
</div>


### 🔑 **Login & Signup**
- **User Authentication**: Secure registration and login system to protect user data.

<div style="display: flex; flex-wrap: wrap; gap: 50px;">
  <img src="/src/main/resources/project/demo/applicationImage/Login.jpg" alt="Log In Page" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/Signup.jpg" alt="Sign Up Page" width="48%">

</div>

### 🛠️ **Employee Directory**
- **Skilled Workers**: Browse a list of available employees with details such as:
  - **Name**
  - **Phone Number**
  - **Availability**
  - **Specializations**
- **Advanced Search Filters**: Locate the ideal professional using filters based on specialization, availability, or location.
-   <img src="/src/main/resources/project/demo/applicationImage/Employees.jpg" alt="Sign Up Page" width="48%">

### 📋 **Service Booking**
- **Book Services**: Explore and book from a variety of available services.
- **Receipt Generation**: Receive a downloadable receipt upon booking confirmation.

<div style="display: flex; flex-wrap: wrap; gap: 50px;">
  <img src="/src/main/resources/project/demo/applicationImage/Services.jpg" alt="Services Cards" width="48%">
    <img src="/src/main/resources/project/demo/applicationImage/bookedservicecart.jpg" alt="Log In Page" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/Receipt.jpg" alt="Receipt Download" width="48%">

</div>

### 🛒 **Tools Checkout**
- **Tool Rental**: Add desired tools to your cart and proceed to checkout.
- **Transaction Receipt**: Obtain a downloadable receipt for your tool rentals.
- - <div style="display: flex; flex-wrap: wrap; gap: 50px;">
  <img src="/src/main/resources/project/demo/applicationImage/Services.jpg" alt="Log In Page" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/ToolCart.jpg" alt="Log In Page" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/ShippingAddress.jpg" alt="Log In Page" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/ShippingMethod.jpg" alt="Log In Page" width="48%">
  <img src="/src/main/resources/project/demo/applicationImage/Receipt.jpg" alt="Sign Up Page" width="48%">

</div>

### 🔍 **Advanced Search Filters**
- **Employees**: Filter by specialization, availability, and location.
- **Services**: Filter by service type, price range, and duration.
- **Tools**: Search by tool type, brand, and rental price.

---

## 🚧 **What’s Missing?**

🔒 **Admin Panel**: A centralized dashboard for administrators to manage users, services, and tools efficiently.

---

## 💡 **Future Enhancements**

1. 💬 **Real-Time Chat**: Enable direct communication between customers and employees prior to booking.
2. ⭐ **Service Ratings & Reviews**: Allow customers to provide feedback and rate services and employees.
3. 🔔 **Push Notifications**: Notify users about upcoming bookings, payments, and special promotions.
4. 📍 **Tool Tracking System**: Implement real-time tracking for rented tools to ensure accountability.
5. 💎 **Subscription Plans**: Offer premium services with exclusive benefits such as discounts and priority booking.
6. 🌎 **Multi-Language Support**: Expand accessibility by supporting multiple languages for a diverse user base.

---

## 🛠️ **Tech Stack**

- **Programming Language**: Java
- **User Interface**: JavaFX
- **Database Management**: MySQL (Managed via XAMPP)
- **Build Tool**: Apache Maven

---

## 🔧 **Setup & Installation**

To set up **HandyMan Tools and Services** on your local machine, follow these steps:

### Prerequisites:
- **Java Development Kit (JDK)**: Ensure JDK 11 or higher is installed.
- **XAMPP**: Install XAMPP to manage the MySQL database.
- **Apache Maven**: Install Maven for dependency management.

### Steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/feliciety/Handyman-Tools-and-Services.git
   cd Handyman-Tools-and-Services
   ```

2. **Set Up the Database**:
   - Launch XAMPP and start the **MySQL** module.
   - Open **phpMyAdmin** and create a new database named `handyman_services`.
   - Import the provided SQL file located at `project/demo/handyman_db.sql` to set up the necessary tables.

3. **Configure Database Connection**:
   - Navigate to `src/main/resources/`.
   - Open `application.properties` and update the database connection details:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/handyman_services
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

5. **Run the Application**:
   ```bash
   mvn javafx:run
   ```

6. **Access the Application**:
   - The application should now be running. Interact with the user interface to explore available features.

---

## 📜 **License**

This project is licensed under the [MIT License](LICENSE). You are free to use, modify, and distribute the code, but please provide appropriate credit to the original authors.

---

## 🎉 **Get Started**

Clone the repository and begin exploring:

```bash
git clone https://github.com/feliciety/handyman-tools-and-services.git
cd handyman-tools-and-services
```

*Note: For a visual guide on connecting JavaFX applications to a MySQL database, you may find this tutorial helpful: [JavaFX Connect to MySQL Database (2021) - Basic Demo](https://www.youtube.com/watch?v=whhSR0wlWQY).* 
