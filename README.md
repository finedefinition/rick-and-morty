# **Rick & Morty API**

![img.png](img.png)

<!-- ABOUT THE PROJECT -->
## **📢Project Description:**

This is a simple project based on the Rick & Morty cartoon. It contents only with backend part.
The main purpose of this application is to get characters from open API https://rickandmortyapi.com/api/character
and filter them by customer request. 

## ⚡Application functionality
1. Get random character from api /movie-characters/random
2. Get all characters contains name /movie-characters/by-name
3. Get all characters by gender
4. Get all characters by status

## ⚡How run this project
This manual describes main steps for application running. To run this project you will need to install and set up following components:

- JDK 11 or higher
- Apache Maven
- Postgres

1. Clone this project to your IDE as Maven project.
2. Check a pom.xml file if any errors are occurred - fix them.
3. Configure Postgres database.
4. Initialize database. It can be local or remote database.
5. In the package resources, open an application.properties file and set there your credentials instead of dummies for DB connection.

  ````
      spring.datasource.url="jdbc:postgresql://<URL_TO_DATABASE>:5432/<DATABASE_NAME>"
      spring.datasource.username=<DATABASE_NAME>
      spring.datasource.password=<DATABASE_PASSWORD>
   ````  
  where:
- <URL_TO_DATABASE> - URL to Database (for local database: localhost)
- <DATABASE_NAME> - name of database(for local database: taxi-service)
- <DATABASE_USERNAME> - username to get permission to read and write to database
- <DATABASE_PASSWORD> - password for <DATABASE_USERNAME> user

6. Start application.
7. Open [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) page in your browser and test the application.

**You can also get this project from [Docker](https://hub.docker.com/r/finedefinition/rick-and-morty) and run in your terminal.**

## ⚡Application technologies used

- JDK 11
- Maven
- Postgres
- Spring Boot Data, Web
- Liquibase
- Docker
- Swagger <br>
[Get more about Swagger settings](https://www.bezkoder.com/spring-boot-swagger-3/)