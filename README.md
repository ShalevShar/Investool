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

<p align="center">
  <a title="Build Status" href="https://github.com/your/repository/actions?query=workflow%3ABuild"><img alt="Build Status" src="https://img.shields.io/github/actions/workflow/status/your/repository/build.yml?style=for-the-badge&label=Build&logoColor=fff&logo=GitHub%20Actions&branch=master"></a>
  <a title="Discord Server" href="https://discord.gg/yourserver"><img alt="Discord Server" src="https://img.shields.io/discord/yourserverid?label=Discord&logo=Discord&logoColor=fff&style=for-the-badge"></a>
  <a title="Total Downloads" href="https://github.com/your/repository/releases/latest"><img alt="Total Downloads" src="https://img.shields.io/github/downloads/your/repository/total?style=for-the-badge&label=Downloads&logoColor=fff&logo=GitHub"></a>
  <a title="Code Quality" href="https://www.codefactor.io/repository/github/your/repository"><img alt="Code Quality" src="https://img.shields.io/codefactor/grade/github/your/repository?style=for-the-badge&label=Code%20Quality&logoColor=fff&logo=CodeFactor&branch=master"></a>
</p>

## Overview

Investool is a mobile application designed for users interested in real-time financial data. It provides a user-friendly interface to monitor and analyze stocks, currencies, commodities, and cryptocurrencies.

## Screenshots

![Screenshot 1](https://example.com/screenshot1.png)
![Screenshot 2](https://example.com/screenshot2.png)

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
