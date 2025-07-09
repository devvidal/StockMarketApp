# Stock Market App Documentation

## 📄 Overview
This application provides users with a comprehensive **list of stock markets**, allowing them to **search by name** and view **detailed information** about each stock.

The details screen includes **interactive charts** displaying stock prices over time, helping users better understand market trends.

All data is retrieved from an **open API** using the **Retrofit** HTTP client.

The app is built following the **Model-View-Intent (MVI)** architecture, based on **SOLID principles** and **Clean Code** practices to ensure a maintainable and scalable codebase.

## 🛠️ Technologies Used

- **Retrofit**  
  Used to fetch stock market data from a public API.

- **Hilt**  
  Enables dependency injection for better modularity and testability.

- **Jetpack Compose**  
  Powers the declarative UI, including dynamic chart rendering.

- **Kotlin Flow & Coroutines**  
  Handle asynchronous data streams and reactive UI updates.

- **Room**  
  Provides local data persistence and caching mechanisms.

## 🎯 Architecture Highlights

- MVI pattern ensures a predictable, unidirectional data flow.
- Search functionality with real-time filtering based on stock names.
- Chart components display price trends over time.
- Clean separation of responsibilities through SOLID and Clean Code principles.
- Highly maintainable multi-layered architecture using modern Android components.

---

https://github.com/user-attachments/assets/82ed1520-d21f-467b-9250-9bc8e40a6ea4




