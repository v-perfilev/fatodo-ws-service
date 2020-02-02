FROM openjdk:13-jdk-alpine as build
WORKDIR /build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests
COPY target/*.jar app.jar

FROM openjdk:13-jdk-alpine
VOLUME /app
COPY --from=build /build/app.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]

