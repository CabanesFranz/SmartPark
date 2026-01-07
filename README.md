# SmartPark

SmartPark is a Spring Boot–based application built with Java and Maven. This document provides the necessary steps to build, run, and test the application locally.

---

## Prerequisites

Before running the application, ensure that the following are installed on your machine:

* **Java 21**
* **Maven 3.5.9**

You can verify your installations by running:

```bash
java -version
mvn -version
```

---

## Build Instructions

1. **Clone or extract the project**

   If cloning from a repository:

   ```bash
   git clone <repository-url>
   ```

2. **Navigate to the project directory**

   ```bash
   cd smartpark
   ```

3. **Build the project**

   ```bash
   mvn clean install
   ```

   This will compile the source code, run tests, and generate the JAR file in the `target/` directory.

---

## Run Instructions

You can run the application in two ways:

### Option 1: Run using Maven

```bash
mvn spring-boot:run
```

### Option 2: Run the generated JAR file

```bash
java -jar target/smartpark-1.0.0.jar
```

Once started, the application will be available at:

```
http://localhost:8080
```

---

## Test Instructions

Run all tests:

```bash
mvn test
```

### Run Tests with Coverage

To generate a JaCoCo test coverage report:

```bash
mvn clean test jacoco:report
```

---

## Notes

* Ensure no other application is running on port **8080** before starting SmartPark.

---
