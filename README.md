# BCNC Test

## Overview
The following repo contains a example of a microservice of spring boot with a database of MongoDB.

## Guidelines
Run the microservice following next steps

1. Clone this repository in your workspace

2. Execute the next command for download dependencies and compile project:
 
 ```
    mvn com.spotify.fmt:fmt-maven-plugin:format clean install
 ```

3. For local environment, you can to visualize the H2 database, 
   putting the following url in the browser:

 ```
    http://localhost:8080/h2-console
 ```

4. We could now run the microservice with the next command:

 ```
   mvn spring-boot:run
 ```

### Swagger access in Local environment

- You can access to Swagger ui from:

   ```
     http://localhost:8080/swagger-ui/index.html
   ```