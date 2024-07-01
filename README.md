<img width="244" alt="Screenshot 2024-07-01 at 18 59 01" src="https://github.com/ShalevShar/Investool/assets/127881894/15938232-0dba-4d4d-b2f8-b120bc3392fc">


<a href="https://yourwebsite.com">
  <h1 align="center">
    <picture>
      <img height="300px" style="margin: 0; padding: 0" src="https://example.com/logo.png">
    </picture>
  </h1>
</a>

<p align="center">
    Investool - Your Comprehensive Financial Companion
    <br>
    Track live stocks, currencies, commodities, and cryptocurrencies with ease.
</p>

## Overview

Investool is a mobile application designed for users interested in real-time financial data. It provides a user-friendly interface to monitor and analyze stocks, currencies, commodities, and cryptocurrencies.

## Home Page
![ref1](https://github.com/ShalevShar/Investool/assets/127881894/59475cfc-a858-4902-b306-ba4ef676bdfa)

## Layouts XML
<img width="836" alt="Screenshot 2024-07-01 at 18 58 33" src="https://github.com/ShalevShar/Investool/assets/127881894/4237891f-3210-4d37-bee9-73df5db426be">
<img width="592" alt="Screenshot 2024-07-01 at 18 58 13" src="https://github.com/ShalevShar/Investool/assets/127881894/388df82e-e25b-45b1-acd7-b42404194687">
<img width="612" alt="Screenshot 2024-07-01 at 18 57 45" src="https://github.com/ShalevShar/Investool/assets/127881894/49c7321e-c7e3-4523-9594-1d833f9a226e">
<img width="627" alt="Screenshot 2024-07-01 at 18 57 30" src="https://github.com/ShalevShar/Investool/assets/127881894/ed4b012c-98fe-43c1-bc4f-68f84ed19d84">
<img width="599" alt="Screenshot 2024-07-01 at 18 57 13" src="https://github.com/ShalevShar/Investool/assets/127881894/d7fc9fb4-1a80-494b-9c35-42788d6b30cc">

## Features

- **Real-Time Data**: Stay updated with live feeds of financial markets.
- **Customizable Watchlists**: Create personalized lists for easy tracking.
- **Interactive Charts**: Visualize market trends with interactive graphs.
- **Alerts and Notifications**: Set alerts for price changes and market news.

## Libraries Used

- **Retrofit**: For handling REST API calls.
- **Gson**: JSON parsing library for data serialization.
- **Android Jetpack Components**: Used for UI and lifecycle management.

## Project Structure

### Activity

- **LoginActivity**: Handles user authentication.
- **MainActivity**: Main dashboard displaying financial data.
- **SignUpActivity**: Allows users to create new accounts.

### Animation

- **DateTimeAnimation**: Displays current time and date with animation effects.

### Command

- **CommandId**: Unique identifiers for commands.
- **CreatedBy, InvokedBy**: Command metadata.
- **MiniAppCommandBoundary**: Boundary class for executing commands.
- **ObjectId, TargetObject**: Identification and targeting of objects.
- **UserKey**: User-specific access keys.

### Data

- **UserData**: Storage and management of user-specific data.

### Dialog

- **UserMenuDialog**: Dialog for user interaction and settings.

### Logic

- **Commodity, Cryptocurrency, Currency, Stock**: Business logic for respective financial categories.

### Network

- **ApiService**: Interface for REST API endpoints.
- **ApproveCallback, CurrencyCallback**: Callbacks for API responses.
- **RetrofitClient**: Retrofit setup for network operations.
- **StockCallback**: Callbacks specific to stock data handling.

### Supperapp

- **SupperAppBoundary**: Integration layer for external services.

### User

- **NewUser, UserId, User**: User-related data models and identifiers.

### View

- **CommodityAdapter, CryptocurrencyAdapter, CurrencyAdapter, FavoriteAdapter, StockAdapter**: Adapters for displaying data in UI components.
![VIDEO-2024-03-11-10-52-43-ezgif com-video-to-gif-converter](https://github.com/ShalevShar/Investool/assets/127881894/6b8a02fe-e351-47d3-ba52-33ffbc8f4c85)
