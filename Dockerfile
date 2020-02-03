# BUILD
FROM openjdk:13-jdk-alpine as build
WORKDIR /build

# maven dependencies layer
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw verify clean --fail-never

# app build layer
COPY src src
RUN ./mvnw install -DskipTests

# DEPLOY
FROM openjdk:13-jdk-alpine
VOLUME /app
COPY --from=build /build/target/*.jar /app/app.jar

# wait tool layer
COPY ./tools/wait wait
RUN chmod +x /wait

# final command
CMD /wait && java -jar /app/app.jar



