# Worblehat

[![Build Status](https://travis-ci.org/scrum-for-developers/worblehat-youtube.svg?branch=master)](https://travis-ci.org/scrum-for-developers/worblehat-youtube)
[![Floobits Status](https://floobits.com/AndreasEK/worblehat-youtube.svg)](https://floobits.com/AndreasEK/worblehat-youtube/redirect)

Worblehat is a training application for the [Scrum for Developers](https://github.com/scrum-for-developers) training
held by [codecentric AG](https://www.codecentric.de/), as well as for the [Professional Scrum Developer](https://www.codecentric.de/schulung/professional-scrum-developer/#schulung-detail) training when given by codecentric.

## YouTube Let's Code with Bene and Andreas

In a [series of livestreams](https://www.youtube.com/playlist?list=PLD9VybHH2wnY6AdGpGinjwzq5brwRn85K), Bene and Andreas will implement the complete Backlog that is usually used during the training.

You can vote on the issues in order to influence the order of the Product Backlog. Please react with a "Thumbs Up" to the issue that You are interested in to see.

* [Most voted issues](https://github.com/scrum-for-developers/worblehat-youtube/issues?utf8=%E2%9C%93&q=is%3Aopen+sort%3Areactions-%2B1-desc)
* [Task Board / Product Backlog](https://github.com/scrum-for-developers/worblehat-youtube/projects/1)

## Developing the application

Technical requirements:

* JDK 11+
* Docker

### Data base setup

A PostgreSQL data base can be started locally using docker-compose:

```shell
docker-compose up
```

The docker compose setup includes [Adminer](https://www.adminer.org) for adminstrating the data base.
Once the data base is started point your broser to http://localhost:8081 and log into the data base:

| Setting          | Value        |
|------------------|--------------|
| Data base system | PostgreSQL   |
| Server           | db           |
| User             | postgres     |
| Password         | worblehat-pw |
| Data base        | postgres     |

### Build process

You can use the maven wrapper to compile and execute the application

* Compile everything: `./mvnw -DskipITs clean install`
* Run the application: `./mvnw -pl worblehat-web spring-boot:run`
* Run the acceptancetests: `./mvnw verify`

Maven comes bundled with the maven wrapper scripts, no need for manual installation before.

## Running the application

1. Make sure the database is running (see above)
1. Run the application.:
  * Either run `./mvnw -pl worblehat-web spring-boot:run` (will automatically compile & package the application before)
  * Or start as plain Java main class in worblehat-web: `de.codecentric.psd.Worblehat`
1. Access the application at <http://localhost:8080/>

## Running tests

All tests are executed via JUnit, but can be conceptually divided in unit and integration tests. They are bound to different maven lifecycle phases, are executed by differen maven plugins, and follow a different naming scheme.

### Unit Tests

1. Unit tests are run with `./mvnw test`
1. The [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin) includes
 [all these tests](https://maven.apache.org/surefire/maven-surefire-plugin/test-mojo.html#includes) by default:
 ```
<includes>
    <include>**/Test*.java</include>
    <include>**/*Test.java</include>
    <include>**/*Tests.java</include>
    <include>**/*TestCase.java</include>
</includes>
```

### Acceptance Tests

1. Acceptance tests are run with `./mvnw verify`.

   Note: The `verify` lifecycle is executed before `install`. If you want to skip acceptance tests, consider to:
   * run up to lifecycle phase `package`
   * skip integration tests with `./mvnw -DskipITs install`
1. The [maven-failsafe-plugin](https://maven.apache.org/surefire/maven-failsafe-plugin) includes
 [all these tests](https://maven.apache.org/surefire/maven-failsafe-plugin/integration-test-mojo.html#includes) by default:
 ```
<includes>
    <include>**/IT*.java</include>
    <include>**/*IT.java</include>
    <include>**/*ITCase.java</include>
</includes>
```

The acceptance tests spin docker containers for all required dependencies (Database & Browser) via [Testcontainers](https://www.testcontainers.org/).
