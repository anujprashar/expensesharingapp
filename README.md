# expensesharingapp
# Expense Sharing App (Spring Boot)

This is a sample backend implementation (demo) for an Expense Sharing application (Splitwise-like) using:
- Java 17, Spring Boot
- Spring Data JPA, H2 (in-memory DB)
- Spring Security + OAuth2 (Google) skeleton
- Transactional settlement logic and simple balance recording as Settlement entries

## What is included
- Entities: User, GroupEntity, Membership, Expense, Settlement
- Repositories, Services (transactional), Controllers
- Sample `data.sql` to pre-populate some users and a group
- `application.yml` with H2 + OAuth placeholders
- Postman collection (`postman_collection.json`) with example requests

## Run
1. Install JDK 17 and Maven.
2. Fill Google OAuth client id/secret in `src/main/resources/application.yml` if you want OAuth flows.
3. Run:
```
mvn spring-boot:run
```
4. H2 console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:expenses`)

## Notes & Design
- Settlement and expense creation are transactional (`@Transactional`) to ensure ACID behavior.
- `@Version` is added to Expense for optimistic locking to handle concurrent updates.
- Balances are represented as Settlement records for demo purposes. In production, maintain a ledger table and computed balances.
- Security is relaxed for demo. Replace `permitAll()` with proper role checks and resource ownership checks.

## Deliverables
- REST endpoints: `/groups`, `/expenses`, `/settlements`
- Sample DB (data.sql)
- Postman collection included
