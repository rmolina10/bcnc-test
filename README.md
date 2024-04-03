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

3. For local environment, you need to connect to MongoDB database.
   In the Docker-Compose folder there is a docker image to run MongoDB.

Into docker-compose folder execute the next command:

 ```
    docker-compose up -d
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