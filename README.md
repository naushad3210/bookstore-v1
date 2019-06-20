# Bookstore

BookStore is an online application that users can use to search and buy books from. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

```
1) Maven should be installed.
```

### Installing

Script to Build and Install Project:
```
1) Download the project from the Git Repository.
2) Open command propmt, then switch to directory where the project is downloaded {dir}/bookstore-v1. Please verify that the pom.xml is present in this directory.
3) Run the command "mvn clean install"
4) Check the target folder for the generated jar file named "BookStore.jar"
5) Optionally the Jar file created in the previous step can be placed to a different directory.
6) Switch to the directory in which the jar file is placed.
7) Execute the command "java -jar BookStore.jar"
```

### APIs Details
APIs details can be checked with the swagger documentation at http://{hostname:post}/bookstore/swagger-ui.html

## Running Test Cases

```
1) Open command propmt. Then switch the directory to {dir}/bookstore-v1. Please verify that the pom.xml is present in this directory.  
2) To run the unit tests, issue the below command :  
	mvn test  
 ```
 
## NOTE
1) server port can be changed in the application properties file in resource folder. Presently 9000 is set.
2) Java 8 should be installed
3) Mysql database is used. So, the db settings need to be configured in application.properties file .

