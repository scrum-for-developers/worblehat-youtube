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
* Run the application: `./mvnw -pl worblehat-web spring-boot:run`
* Run the acceptancetests _(The application needs to be running, if you do this.)_: \
 `./mvnw verify`
  

### Acceptance Tests

When the acceptance tests are running, a chrome browser is started within a test container. While it's 
running, it is recording a video, which can later be found in `./target`. If you want to connect to the 
container, you can do so via vnc. The URL is logged while the tests are starting up, for example:

```...
16:06:53.354 [main] INFO  d.c.w.a.s.AllAcceptanceTestStories - Connect to VNC via vnc://vnc:secret@localhost:33148
...
``` 

The most simple way (at least for macs) to open the vnc connection is on the terminal with `open vnc://vnc:secret@localhost:33148`.

#### Running a single Story or Scenario

If you want to run a single Story or Scenario, add the Meta-Tag `@Solo`.

For example, if this is your `Add Book.story`, you can tag the whole Story:

```
Meta:
@themes Book
@Solo

Narrative:
In order to add new books to the library
As a librarian
I want to add books through the website

Scenario:

Given an empty library
When a librarian adds a book with title <title>, author <author>, edition <edition>, year <year>, description <description> and isbn <isbn>
Then the booklist contains a book with values title <title>, author <author>, year <year>, edition <edition>, isbn <isbn> and description <description>

```

or just a single scenario:

```
Meta:
@themes Book

Narrative:
In order to add new books to the library
As a librarian
I want to add books through the website

Scenario:

Meta: 
@Solo

Given an empty library
When a librarian adds a book with title <title>, author <author>, edition <edition>, year <year>, description <description> and isbn <isbn>
Then the booklist contains a book with values title <title>, author <author>, year <year>, edition <edition>, isbn <isbn> and description <description>

```
