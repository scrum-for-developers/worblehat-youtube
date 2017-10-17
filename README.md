# Worblehat

[![Build Status](https://travis-ci.org/scrum-for-developers/worblehat.svg?branch=master)](https://travis-ci.org/scrum-for-developers/worblehat)

Worblehat is a training application for the [Scrum for Developers](https://github.com/scrum-for-developers) training
held by [codecentric AG](https://www.codecentric.de/).

## Requirements
* JDK 7+
* Apache Maven (https://maven.apache.org)
* Docker (for running acceptance tests, https://www.docker.com)

## Running the application

1. Build the project: For example by running `mvn clean install` in the root directory
1. Start the database. The easiest way is to fire up a docker container:
```
#!/bin/bash

docker run --detach \
  --name worblehat-db \
  --env MYSQL_ROOT_PASSWORD=root \
  --env MYSQL_USER=worblehat \
  --env MYSQL_PASSWORD=worblehat \
  --env MYSQL_DATABASE=worblehat_test \
  --publish 3306:3306 \
  mysql:5.6.25
```
3. Run the application. In the directory `worblehat-web`:
  * Either run `mvn spring-boot:run`
  * Or start as plain Java main class: `de.codecentric.Application`
4. Access the application at <http://localhost:8080/worblehat/>

## Running acceptance tests

_Acceptance Tests are currently broken_

The acceptance tests currently don't work with the in-memory data base,
which is provided by Spring Boot. For this reason you need to start a
real data base. The acceptance test build will use the data base directly
to insert test data.

1. Setup the database
  * Run the `docker-db.sh` script in the worblehat-web directory. It will
    start a MySQL docker container and expose port 3306 for you. Depending
    on your operating system you can access the data base via `localhost:3006`
    (Linux) or \<DOCKER-HOST-IP\>:3306 (MacOS + boot2docker).
    
    Notice: To find out your DOCKER-HOST-IP execute `docker-machine env default`
1. Configure worblehat to use the MySQL data base
  * Uncomment the data base connection configuration in
    `worblehat-web/src/main/resources/application.properties`
  * Depending on your operating system you may have to modify the
    `spring.datasource.url` property to the URL your docker MySQL data base it
    running at.
  * Start the application.
1. Configure the acceptance test build to use the MySQL data base
  * Change the `DB_URL` property in the `local` profile in
    `worblehat-acceptancetests/pom.xml` to the URL your docker MySQL data base
    it running at (if necessary).
1. Run acceptance tests by calling `$ mvn -Plocal clean verify` in the
   `worblehat-acceptancetests` directory.

## Howto Release

To release for example version 1.2 follow these steps:

1. Set next development version: `mvn -Pinclude-acceptancetests versions:set -DnewVersion=1.2 -DgenerateBackupPoms=false`
1. Create a tag and push the tag back to the team repository
1. Let jenkins build the release and deploy it to the test and production environments
1. Bump the version for the next development iteration: `mvn -Pinclude-acceptancetests versions:set -DnewVersion=1.3-SNAPSHOT -DgenerateBackupPoms=false`
