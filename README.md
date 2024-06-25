
# Bag Shopping App

![](/app/src/main/res/drawable/bag.gif)

This is a bag shopping Android app that allows users to sign up, sign in, browse a list of products, view product categories, select products to add to their cart, proceed to a payment link, view payment results, and leave comments for each product. The app is built using Kotlin, Jetpack Compose, Room for offline caching, Retrofit for API calls, Koin for dependency injection, MVVM architecture pattern, Coroutines for asynchronous programming, and Coil for image loading.

## Features

- User authentication: Sign up and sign in to access the app
- Browse products: View a list of available products
- Browse categories: Filter products by category
- Add to cart: Select products to add to the shopping cart
- Check out: Proceed to a payment link to complete the purchase
- Payment results: View the payment result after completing the purchase
- Leave comments: Insert comments for each product

## Installation

1. Clone this repository
2. Open the project in Android Studio
3. Build and run the app on an Android device or emulator

## Dependencies

- Jetpack Compose: Modern UI toolkit for building native Android apps
- Room: SQLite object mapping library for database caching
- Retrofit: Type-safe HTTP client for making network calls
- Koin: Lightweight dependency injection framework
- Coroutines: Kotlin library for handling asynchronous programming
- Coil: Image loading library for loading product images

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern, separating data presentation logic, business logic, and data fetching logic. This allows for easy testing and maintenance of the app.

## Contributions

Contributions are welcome! If you have any ideas or suggestions for improving the app, please feel free to submit a pull request or create an issue.
