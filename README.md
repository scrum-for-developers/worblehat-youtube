# Worblehat

## Installation and Upgrade Guide

1. Upgrade to latest version
2. Initial setup
3. Requirements

### 1. Upgrade to latest version of worblehat

1. Shutdown Tomcat
2. Upgrade database scheme
 * Run the following command in worblehat-build
 * `$ mvn liquibase:update`
3. Deploy new worblehat.war to Tomcat
4. Restart Tomcat

### II ) Initial setup

1. Setup database scheme
  * Run the following command in worblehat-build:

      `$ mysql -uroot < ddl/initdb.sql`

  * Run the following command in worblehat-build

      `$ mvn liquibase:update`
2. Deploy worblehat.war to Tomcat

### III) Requirements
* MySQL Default Setup, running on port 3306, root User without password
* Tomcat 6+
