FROM openjdk:13-jdk-alpine as build
WORKDIR /build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw verify clean --fail-never

COPY src src
RUN ./mvnw install -DskipTests

FROM openjdk:13-jdk-alpine
VOLUME /app
COPY ./tools/wait wait
COPY --from=build /build/target/*.jar /app/app.jar

CMD wait && java -jar /app/app.jar



