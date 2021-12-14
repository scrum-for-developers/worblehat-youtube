FROM maven:3.8.1-openjdk-15-slim AS build

WORKDIR /usr/local/src/worblehat_youtube
COPY . /usr/local/src/worblehat_youtube

RUN mvn clean install

FROM adoptopenjdk/openjdk15:jre-15.0.2_7-ubi-minimal

ENV PORT=8080 DATABASE_PORT=5432 DATABASE_HOST=localhost

EXPOSE $PORT

COPY --from=build /usr/local/src/worblehat_youtube/worblehat-web/target/*.jar /worblehat-web/

CMD java -Dserver.port=$PORT -jar /worblehat-web/*.jar --spring.datasource.url=jdbc:postgresql://$DATABASE_HOST:$DATABASE_PORT/postgres
