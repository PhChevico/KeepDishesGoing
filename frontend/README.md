# KeepDishesGoing

KeepDishesGoing (KDG) is a full-stack food ordering platform where restaurants manage their menus and customers place and track orders in real time. It emphasizes reliability, modular design, and clear user experience for both restaurant owners and customers.

## Overview

* Restaurants can register, manage their dishes, and handle incoming orders.
* Customers can browse restaurants, build baskets, and track order progress without signing in.
* The platform handles order status changes asynchronously and integrates with external delivery services via RabbitMQ.

## Core Features

### Restaurant Owner

* Sign up and create a restaurant (name, address, email, type, photos, opening hours).
* Add, edit, publish/unpublish, or schedule dish updates.
* Mark dishes out of stock instantly.
* Accept or reject orders (auto-reject after 5 min).
* Mark orders ready for pickup.
* Manually open/close the restaurant.

### Customer

* Explore restaurants in a list or on a map.
* Filter by cuisine, price range, distance, or delivery time.
* View restaurant details and dishes, filter by type or dietary tags.
* Build and edit a basket (one restaurant per order).
* Checkout with name, address, email, and Stripe payment.
* Track order status in real time.

## Technologies

### Backend

* Java 21, Spring Boot, Gradle Kotlin DSL
* Hexagonal Architecture + Domain-Driven Design
* Spring Data JPA, PostgreSQL
* Spring Security (OAuth2 / JWT / Keycloak)
* RabbitMQ for asynchronous communication
* Stripe payment integration
* Spring Modulith for modular events
* Docker for deployment

### Frontend

* React 19 + TypeScript + Vite
* Material UI (MUI)
* React Query for data fetching
* React Router for routing
* Axios for API requests
* Keycloak for authentication
* SASS for styling

## Architecture

* Implements Hexagonal Architecture with domain, application, and infrastructure layers clearly separated.
* Follows DDD principles with entities, value objects, aggregates, and domain events.
* Uses event sourcing and snapshotted aggregates for order and price-range tracking.

## Asynchronous Workflows

* Message broker: RabbitMQ
* Exchange: kdg.events
* Routing pattern: `<context>.<restaurantId>.<resource>.<action>.v1`
* Publishes:

  * `restaurant.*.order.accepted.v1`
  * `restaurant.*.order.ready.v1`
* Consumes:

  * `delivery.*.order.pickedup.v1`
  * `delivery.*.order.delivered.v1`
  * `delivery.*.order.location.v1`

## Local Setup

1. Clone repositories (backend + frontend).
2. Start Docker services using `compose.yaml` (PostgreSQL, RabbitMQ, Keycloak).
3. Configure environment variables (database URL, RabbitMQ host, OAuth client info, Stripe keys).

### Backend

```bash
./gradlew bootRun
```

### Frontend

```bash
npm install
npm run dev
```
