# Cinema Management System

## Overview
The Cinema Management System is a robust Java application designed for cinema administrators and customers, enabling efficient management of cinema operations and ticket bookings. This application supports various functionalities, including account management, movie listings, ticket reservations, and purchase tracking, all integrated with a backend database for secure data handling.

## Objective
The objective of this project is to provide a comprehensive application that seamlessly integrates various components of software architecture, including user interaction through the view layer, data handling in the model layer, and database operations within the database package. This application aims to demonstrate effective software design patterns, particularly focusing on the Model-View-Controller (MVC) architecture, to ensure scalability, maintainability, and separation of concerns.

## Features

### For Administrators
- **Movie Management**: Add and manage movie listings.
- **Ticket Management**: Add tickets and manage bookings.
- **Viewings and Income Tracking**: View all available tickets and track total income from ticket sales.
- **Purchase Overview**: Access a detailed list of all purchases with comprehensive details.

### For Customers
- **Account Management**: Secure login and account management.
- **Ticket Booking**: Browse movies and book tickets.
- **Purchase History**: View detailed history of all ticket purchases.

## Implementation
The application is structured around the MVC architecture to separate concerns, making the codebase more manageable and extensible:

- **Model**: Defines the data structure and logic. The model package contains classes that represent the application's data and the business rules that govern access to and updates of this data.
- **View**: Responsible for the presentation layer. The view package includes all classes related to the user interface, ensuring that the user interaction is kept separate from the business logic.
- **Controller**: Acts as an intermediary between the view and model, handling user input and responding by updating the model and the view. The controller package contains classes that manage the flow of data between the model and view, and update the view when the data changes.
- **Database**: The database package is likely to contain classes for database connectivity and operations, providing a layer of abstraction over the raw database queries.

## Future developements
Future enhancements could include expanding the application's functionality, integrating more complex business rules, or improving the user interface design.

## Conclusion
This project exemplifies the application of the MVC architecture in a Java application, demonstrating the separation of concerns, ease of maintenance, and scalability. Through its structured approach, it showcases how to efficiently manage the interactions between the user interface, the business logic, and the database.
