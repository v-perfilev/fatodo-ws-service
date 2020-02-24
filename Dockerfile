# BUILD
FROM openjdk:13-jdk-alpine as build
WORKDIR /build

# important libs
RUN apk --no-cache add ca-certificates
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.29-r0/glibc-2.29-r0.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.29-r0/glibc-bin-2.29-r0.apk
RUN apk add glibc-2.29-r0.apk glibc-bin-2.29-r0.apk

# maven dependencies layer
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw clean verify -fn

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



