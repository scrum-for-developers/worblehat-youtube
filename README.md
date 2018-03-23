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

1. The acceptance tests have a dependency on the domain module, so the first
   step is to ensure that the most recent snapshot is installed in the local
   maven repository
   
   ```
   root> mvn clean instal 
   ```
   
1. Then, we need the application running, that should be tested

   ```
   root> mvn -f worblehat-web spring-boot:run
   ```

1. Run acceptance tests. It currently requires Chrome to be installed.

   ```
   root> cd worblehat-acceptancetests
   worblehat-acceptancetests> mvn clean verify
   ```
 
   To run the tests headless, use the headless profile ```> mvn -Pheadless clean verify```
1. The report can be found in ```target/jbehave/view/reports.html```

## Howto Release

To release for example version 1.2 follow these steps:

1. Set next development version: `mvn -Pinclude-acceptancetests versions:set -DnewVersion=1.2 -DgenerateBackupPoms=false`
1. Create a tag and push the tag back to the team repository
1. Let jenkins build the release and deploy it to the test and production environments
1. Bump the version for the next development iteration: `mvn -Pinclude-acceptancetests versions:set -DnewVersion=1.3-SNAPSHOT -DgenerateBackupPoms=false`
