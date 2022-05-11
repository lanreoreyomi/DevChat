# Backend

( Source: backend/README.md )

Devchat is a web application that connect IT professionals around the world. It is a platform where you can chat with other IT professionals and share your knowledge, find code buddy, code review, and more.


## Project setup
```
The project is setup with the following dependencies:

Follow official dependency website to get the latest version of the dependencies:

Java version: 1.8 (https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
Spring Boot version: 2.6.8  (https://start.spring.io/)
Spring Data version: 2.6.8  (https://start.spring.io/)



```
### Database
```
The project is is currntly set up with h2 database and postgresql database.
To switch between the two, comment out the database you don't want to use inside application.properties file for the project. 

```

### Bulding the project
```
mvn clean install
```
### Running the project
```
Run DevChatsApplication.java file in the backend folder.
This will expose a tomcat server on port 5050.
Navigate to the browser and type in the following url:
https://localhost:5050/

You can also use other http agents to access the application through the same url.
```

### Swagger documentation
```
The project is setup with swagger documentation. To access the documentation, navigate to the following url:
http://localhost:5050/swagger-ui.html#/user45controller
 

