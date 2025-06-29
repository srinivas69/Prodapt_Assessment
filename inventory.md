# **Inventory Management Technical Test Instructions**

The purpose of this assessment is to evaluate your ability to implement features, resolve issues, and suggest improvements in a Java-based inventory management application. The application uses Swagger for API documentation and includes a SupplierService that simulates an external API. The SupplierService should be treated as a black box and must not be modified.

Familiarize yourself with the code, read the tasks, then complete them in order. When complete, provide us with read access to your Git repository.

---

#### **Task 1**

A new feature has been requested for the inventory management application. The consumer would like to add a way to filter products based on their availability status. If a product is marked as "out of stock," it should not appear in the results. If no availability filter is specified, all products should be returned.

Ensure that this filtering is optional and configurable for each request. Update the API to support this feature and ensure it is reflected in the Swagger documentation.

---

#### **Task 2**

An issue has been raised by a customer, and investigation has determined that the inventory management application is the source. When filtering products by availability (implemented in Task 1), the application occasionally returns incorrect results due to a race condition in the `ProductService`.

To simulate this issue, assume that the `ProductService` may occasionally return duplicate or incomplete results. Implement a solution to ensure that the results returned by the API are always accurate and free of duplicates. Follow best practices and existing project conventions where appropriate.

---

#### **Task 3**

Having completed the tasks above, consider what improvements you might like to see in the code. These could include refactors, optimizations, test improvements, general code housekeeping, etc. Prepare some suggestions for discussion in the next interview.

---

#### **Code Improvements**

1. Refactor for Separation of Concerns
The ProductService class is handling both business logic and supplier enrichment. Consider moving the supplier enrichment logic to a dedicated SupplierService method or utility class to improve separation of concerns.

2. Use DTOs for API Responses
Instead of exposing the Product entity directly in the API, use Data Transfer Objects (DTOs). This ensures better encapsulation and allows for more control over the API response structure.

3. Improve Exception Handling
The GlobalExceptionHandler could be extended to handle more specific exceptions, such as validation errors or supplier related issues. Additionally, logging exceptions would help with debugging and monitoring.

4. Add Unit and Integration Tests
Ensure comprehensive test coverage for the ProductService and InventoryController. Include tests for edge cases, such as concurrent updates, invalid inputs, and supplier enrichment failures.

5. Optimize Database Queries
If the application uses a database, review the queries for performance. Consider using pagination for large result sets and ensure that indexes are used effectively to speed up lookups.

6. Use Optional for Supplier Information
Instead of setting "Unknown Supplier" when supplier information is unavailable, consider using Optional to represent the absence of supplier data more explicitly.

7. Improve Swagger Documentation
Enhance Swagger documentation by adding examples for query parameters and response schemas. This will make the API easier to understand for consumers.

8. Validation for Product Input
Add validation annotations (e.g., @NotNull, @Size) to the Product class fields to ensure data integrity when creating or updating products.

9. Use Constructor Injection
Replace @Autowired field injection with constructor injection for better testability and immutability.

10. Refactor Hardcoded Sample Data
Move the initialization of sample products to a configuration class or a database seed script to avoid hardcoding in the service layer.

11. Add Logging
Add logging at key points in the application (e.g., service methods, exception handling) to improve observability and debugging.

12. Optimize Supplier Enrichment
If supplier enrichment is a frequent operation, consider caching supplier data to reduce repeated calls to SupplierService.

13. Use Enum for Availability
Replace the boolean available field with an enum to represent availability status (e.g., IN_STOCK, OUT_OF_STOCK). This improves readability and extensibility.
    
These improvements can enhance code quality, maintainability, and performance while ensuring better scalability and reliability.
