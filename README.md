<div align="center">

# 🏥 CareStack

**Backend platform for hospital operations — inventory, reconciliation, and scheduling, built on Spring Boot.**

[![Java](https://img.shields.io/badge/Java-26-orange?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Flyway-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Kafka](https://img.shields.io/badge/Kafka-Event--Driven-231F20?logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Redis](https://img.shields.io/badge/Redis-Cache-DC382D?logo=redis&logoColor=white)](https://redis.io/)
[![License](https://img.shields.io/badge/license-Unlicensed-lightgrey)]()

</div>

---

## Overview

CareStack is the backend service powering hospital inventory management — tracking stock, reconciling supplier data on a schedule, and surfacing discrepancies before they become problems. Built as a modular Spring Boot application with an event-driven backbone (Kafka) and Redis-backed caching for hot paths.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 26 |
| Framework | Spring Boot 4.1.0 |
| Persistence | Spring Data JPA + PostgreSQL |
| Migrations | Flyway |
| Messaging | Spring Kafka |
| Caching | Spring Cache + Redis |
| Security | Spring Security |
| Build | Gradle |

## Getting Started

### Prerequisites

- Java 26
- Docker (for PostgreSQL, Redis, Kafka via `compose.yaml`)

### Run locally

```bash
# start dependencies (Postgres, Redis, Kafka)
docker compose up -d

# run the app
./gradlew bootRun
```

The app boots on **`http://localhost:8080`**.

### Run tests

```bash
./gradlew test
```

## Configuration

Key settings live in `src/main/resources/application.yaml`:

| Property | Purpose |
|---|---|
| `hospital.scheduling.appointment-reminder-cron` | Daily appointment reminder job (07:00) |
| `hospital.scheduling.inventory-reconciliation-cron` | Daily supplier stock reconciliation (01:30) |
| `hospital.cache.default-ttl-minutes` | Default Redis cache TTL |
| `hospital.idempotency.ttl-hours` | Idempotency key retention |

## API

### Inventory

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/inventory` | Add a new inventory item |
| `GET` | `/api/inventory` | List all inventory items |
| `GET` | `/api/inventory/{sku}` | Get an item by SKU |
| `PATCH` | `/api/inventory/{sku}/adjust?delta={n}` | Adjust stock quantity for a SKU |

## Project Structure

```
src/main/java/com/ryuken/carestack/
├── controller/    # REST endpoints
├── dto/           # Request/response payloads
├── entity/        # JPA entities
├── repository/    # Spring Data repositories
├── scheduler/     # Cron-driven jobs (e.g. inventory reconciliation)
└── service/       # Business logic
```

## Database Migrations

Schema is owned by Flyway (`src/main/resources/db/migration`) — Hibernate runs in `validate` mode only and never mutates the schema directly.

---

<div align="center">

Built with ☕ and Spring Boot.

</div>
