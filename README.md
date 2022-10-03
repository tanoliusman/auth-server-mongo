# Sprint Authorization Server with MongoDB
This project added functionality to pull client details from mongodb instead of provided ClientRepositories.
### Prerequisites
1. Java 17
2. MongoDB
3. Maven

### Initial Setup
To connect with mongodb, update the application.yml file properties.
    
    spring:
        data:
            mongodb:
                host: localhost
                port: 27017
                database: auth
Import the seed file into MongoDB database from /seed folder.

### Run Application
To run this project, Open AuthServerMongoApplication.java file and execute it with Java.

### Client Repository
Mongo Client repository is present in "repository" folder named MongoClientRepository.java
which implements all abstract methods from RegisteredClientRepository and provide there 
implementation according to the requirement.

