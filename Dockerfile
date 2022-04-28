# BUILD
FROM adoptopenjdk/openjdk15 as build
WORKDIR /build

# maven dependencies layer
COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY etc etc
RUN ./mvnw dependency:resolve

# app build layer
COPY src src
RUN ./mvnw install -Dmaven.test.skip=true

# DEPLOY
FROM adoptopenjdk/openjdk15:jre
COPY --from=build /build/target/fatodo.jar /app/app.jar

# wait tool layer
COPY ./etc/tools/wait wait
RUN chmod +x /wait

# final command
CMD /wait && java $JAVA_OPTS -XX:+UseContainerSupport -XX:+TieredCompilation -jar /app/app.jar
