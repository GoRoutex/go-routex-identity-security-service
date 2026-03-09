# Identity Security Starter

A production-ready Spring Boot starter that provides a standardized foundation for authentication and security in microservice architectures.

This starter simplifies the integration of **JWT authentication**, **Spring Security configuration**, and **identity-based access control** across services.

It is designed to be reusable across multiple backend services to ensure **consistent security practices** and **reduced boilerplate code**.

---

## Features

- 🔐 JWT-based authentication
- 🛡️ Preconfigured Spring Security setup
- 👤 Identity extraction from JWT tokens
- 📦 Easy integration into existing Spring Boot services
- ⚡ Lightweight and minimal configuration
- 🔧 Designed for microservice architectures

---

## Use Cases

This starter is intended for backend services that require:

- Consistent authentication across microservices
- Standardized JWT validation
- Simplified Spring Security configuration
- Shared identity context between services

---

## Installation

Add the dependency to your project.

### Maven

```xml
		<dependency>
			<groupId>vn.com.go.routex</groupId>
			<artifactId>identity-security-spring-boot-starter</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```

---

## Configuration

Example configuration in `application.yml`:

```yaml
identity:
  jwt:
    secret: ${JWT_SECRET}
    issuer: ${JWT_ISSUER}
    audience: ${JWT_AUDIENCE}
    authority-claim-name: ${JWT_CLAIM_NAME:roles}
    authority-prefix: ${JWT_AUTH_PREFIX:ROLE_}
```

---

## Usage

Once added, the starter automatically configures Spring Security and JWT authentication.

Example of accessing authenticated user information:

```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
String username = authentication.getName();
```

You can also inject identity information in your services or controllers.

---

## Architecture

This starter is designed to be used as a **shared security module** across multiple services.

Example microservice structure:

```
services/
 ├── user-service
 ├── route-service
 ├── booking-service
 └── identity-security-starter
```

Each service includes the starter to ensure a **consistent authentication layer**.

---

## Requirements

- Java 21+
- Spring Boot 3+
- Maven 3.9+

---

## Roadmap

Planned features:

- OAuth2 support
- Role-based access control helpers
- Multi-tenant identity support
- Security audit logging

---

## Contributing

Contributions are welcome.  
Please open an issue or submit a pull request.

---

## License

MIT License
