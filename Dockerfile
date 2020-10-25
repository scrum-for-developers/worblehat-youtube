FROM gradle:6.7.0-jdk11 AS build

WORKDIR /usr/local/src/worblehat_youtube
COPY . /usr/local/src/worblehat_youtube

RUN ./gradlew clean :worblehat-web:assemble

FROM openjdk:11-jre-slim

ENV PORT=8080 DATABASE_PORT=5432 DATABASE_HOST=localhost

EXPOSE $PORT

COPY --from=build /usr/local/src/worblehat_youtube/worblehat-web/build/libs/*-executable.jar /worblehat-web/

CMD java -Dserver.port=$PORT -jar /worblehat-web/*.jar --spring.datasource.url=jdbc:postgresql://$DATABASE_HOST:$DATABASE_PORT/postgres
