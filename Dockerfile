# BUILD
FROM openjdk:13-alpine as build
WORKDIR /build

# important libs
RUN apk --no-cache add ca-certificates && \
    wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub && \
    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.29-r0/glibc-2.29-r0.apk && \
    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.29-r0/glibc-bin-2.29-r0.apk && \
    apk add glibc-2.29-r0.apk glibc-bin-2.29-r0.apk

# maven dependencies layer
COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY etc etc
RUN ./mvnw verify clean --fail-never

# app build layer
COPY src src
RUN ./mvnw install -Dmaven.test.skip=true

# DEPLOY
FROM openjdk:13-alpine
VOLUME /app
COPY --from=build /build/target/fatodo.jar /app/app.jar

# wait tool layer
COPY ./etc/tools/wait wait
RUN chmod +x /wait

# final command
CMD /wait && java -jar /app/app.jar
