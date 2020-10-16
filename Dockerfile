FROM maven:3.6.3-openjdk-11-slim AS build

WORKDIR /usr/local/src/worblehat_youtube
COPY . /usr/local/src/worblehat_youtube

RUN mvn clean install

FROM openjdk:11-jre-slim

ENV PORT=8080 DATABASE_PORT=5432 DATABASE_HOST=localhost

EXPOSE $PORT

COPY --from=build /usr/local/src/worblehat_youtube/worblehat-web/target/*.jar /worblehat-web/

CMD java -Dserver.port=$PORT -jar /worblehat-web/*.jar --spring.datasource.url=jdbc:postgresql://$DATABASE_HOST:$DATABASE_PORT/postgres
