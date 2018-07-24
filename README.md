# Worblehat

[![Build Status](https://travis-ci.org/scrum-for-developers/worblehat-youtube.svg?branch=master)](https://travis-ci.org/scrum-for-developers/worblehat-youtube)
[![Floobits Status](https://floobits.com/AndreasEK/worblehat-youtube.svg)](https://floobits.com/AndreasEK/worblehat-youtube/redirect)

Worblehat is a training application for the [Scrum for Developers](https://github.com/scrum-for-developers) training
held by [codecentric AG](https://www.codecentric.de/), as well as for the [Professional Scrum Developer](https://www.codecentric.de/schulung/professional-scrum-developer/#schulung-detail) training when given by codecentric. 

## YouTube Let's Code with Bene and Andreas

In a [series of livestreams](https://www.youtube.com/playlist?list=PLD9VybHH2wnY6AdGpGinjwzq5brwRn85K), Bene and Andreas will implement the complete Backlog that is usually used during the training. 

You can vote on the issues in order to influence the order of the Product Backlog. Please react with a "Thumbs Up" to the issue that You are interested in to see.

* [Most voted issues](https://github.com/scrum-for-developers/worblehat-youtube/issues?utf8=%E2%9C%93&q=is%3Aopen+sort%3Areactions-%2B1-desc)
* [Tast Board / Product Backlog](https://github.com/scrum-for-developers/worblehat-youtube/projects/1)

## Developing the application

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

* Compile everything: `./mvnw clean install`
* Run the application: `./mvnw -pl worblehat-web spring-boot:run
* Run the acceptancetests: `./mvnw -Pinclude-acceptancetests -pl worblehat-acceptancetests test`
