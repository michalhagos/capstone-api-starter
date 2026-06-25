# EasyShop E-Commerce API

This is the backend API for the EasyShop Capstone 3 e-commerce project.

## Project Overview

EasyShop is an online store backend built with Spring Boot and MySQL. The API supports browsing products, filtering by category/price/color, admin product/category management, user profile management, shopping cart, and checkout.

## Store Selected

**EasyShop**

Use this database script:

```text
database/create_database_easyshop.sql
```

## Main Features

- User registration and login with JWT authentication
- Product search and filtering
- Category browsing
- Admin-only category CRUD
- Admin-only product CRUD
- Logged-in user shopping cart
- Logged-in user profile view/update
- Checkout into orders and order line items

## Required Bug Fixes

### Bug 1: Product search returned incomplete results

The old search code always filtered products by `featured = true`. This made normal products disappear from the EasyShop product list.

Fixed in:

```text
src/main/java/org/yearup/service/ProductService.java
```

### Bug 2: Product stock update did not save

The old update code did not copy the `stock` field from the request body to the existing product.

Fixed in:

```text
src/main/java/org/yearup/service/ProductService.java
```

## Java Version

The project keeps Java 17 in `pom.xml`

## Run API

```bash
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

## Run Website

Open the EasyShop web client folder in IntelliJ:

```text
../capstone-web-applications/capstone-client-easyshop
```

Then open `index.html` in a browser.

## Demo Users

| Username | Password | Role |
| --- | --- | --- |
| user | password | USER |
| admin | password | ADMIN |
