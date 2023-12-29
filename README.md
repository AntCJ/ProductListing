# Product Listing Application

This application lists all the products with pagination in a web page, with Angular front-end and Spring Boot back-end with REST APIs.

## Prerequisite

- Java 17, Maven
- npm
- angular-cli

## Deploying Spring Boot

1. Build Maven project in root directory:
`mvn clean install`
2. Run the built jar file:
`java -jar target/product-listing-0.0.1-SNAPSHOT.jar`

## Deploying Angular

1. Go to /angularclient directory.
2. Install all dependencies specified in package.json:
`npm install`
3. Run the Angular project:
`ng serve`
