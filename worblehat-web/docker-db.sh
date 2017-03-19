#!/bin/bash
  
docker run --detach \
  --name worblehat-db \
  --env MYSQL_ROOT_PASSWORD=root \
  --env MYSQL_USER=worblehat \
  --env MYSQL_PASSWORD=worblehat \
  --env MYSQL_DATABASE=worblehat_test \
  --publish 3306:3306 \
  mysql:5.6.25

