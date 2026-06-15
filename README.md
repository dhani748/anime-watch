# anime-watch

A legal anime info and news website backend built with Spring Boot 3.2 and Java 21.

## Prerequisites

- Java 21
- Maven
- MySQL 8+

## Quick Start

```bash
# Configure environment (or use defaults for dev)
export DB_URL=jdbc:mysql://localhost:3306/anime_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
export DB_USERNAME=root
export DB_PASSWORD=root
export JWT_SECRET=your-256-bit-secret-key-here

# Run
mvn clean install
mvn spring-boot:run
```

Default admin: `admin@animesite.com` / `admin123`

## Features

### Anime
- Trending, seasonal, and search endpoints (powered by Jikan API / MyAnimeList)
- Advanced filtering by genre, type, status, score, and more
- In-memory caching for fast repeated queries
- Rate-limited API calls to comply with Jikan rate limits

### Authentication & Authorization
- JWT-based auth (access + refresh token flow)
- Email verification on registration
- Resend verification email
- Forgot / reset password
- Logout (refresh token revocation)
- Role-based (USER / ADMIN)

### User Profile
- View / update profile name
- Change password
- Delete account

### Watchlist & Favorites
- Track anime with statuses: WATCHING, COMPLETED, PLAN_TO_WATCH
- Manage favorites list

### Reviews
- Add / edit / delete reviews with star rating (1-5)
- One review per user per anime
- Ownership verification on modifications

### Admin
- User management (list, delete)
- Anime management (set affiliate URL, delete)
- Review moderation (delete any review)
- News articles CRUD

### News
- Create, update, delete news articles (admin)
- Public read access with pagination

## API Documentation

Once running: [Swagger UI](http://localhost:8080/swagger-ui.html)

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Framework | Spring Boot 3.2 |
| Language | Java 21 |
| Database | MySQL (JPA / Hibernate) |
| Auth | JWT (jjwt 0.12) + Spring Security |
| API Docs | Springdoc OpenAPI 2.3 |
| Caching | Spring Cache (ConcurrentMap) |
| Build | Maven |
