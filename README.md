# Factory_Bosso

The admin-side backend for the factory management system. It runs alongside `factory_back2` and is tailored for the boss/manager role — same core stack, with a database snapshot included in the repo and a sample worker report spreadsheet for reference.

---

## Stack

- Java 17
- Spring Boot 3.3.1
- Spring Data JPA
- PostgreSQL
- Apache POI (Excel export)
- ZXing (QR code generation)
- Jackson (with JSR310 for date/time serialization)
- Lombok

---

## Prerequisites

- Java 17+
- Maven (or use the included `mvnw` wrapper)
- PostgreSQL running locally or remotely

---

## Getting started

Clone the repo:

```bash
git clone https://github.com/AkmalHakimov/Factory_Bosso.git
cd Factory_Bosso
```

If you want to start with the included database snapshot, import it from the `Bosso Db` folder:

```bash
psql -U your_username -d your_database < "Bosso Db/your_dump_file.sql"
```

Then configure your connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/factory_bosso
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Run the application:

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080` by default. Make sure the port doesn't conflict with `factory_back2` if you're running both at the same time.

---

## What's included

The `Bosso Db` folder contains a database dump you can restore to get a working starting state. `WorkerReport.xlsx` is a sample Excel export showing the format used for worker reporting — useful for understanding the expected output before running the actual export endpoint.

---

## Project structure

```
Factory_Bosso/
├── Bosso Db/             # Database dump/snapshot
├── WorkerReport.xlsx     # Sample Excel report output
├── src/
│   └── main/
│       ├── java/com/
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/
│       │   ├── model/
│       │   └── dto/
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## Related

- [factory_back2](https://github.com/AkmalHakimov/factory_back2) — the main operational backend
- [factory_front](https://github.com/AkmalHakimov/factory_front) — the React frontend dashboard
