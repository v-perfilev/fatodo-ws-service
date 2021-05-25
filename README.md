# Fatodo base-skeleton

Very simple spring app that is used for development of base microservices.

## Tests

### Unit tests

```
./mvnw test
```

### Integration tests

```
./mvnw verify -DskipUTs
```

### Contract tests

```
./mvnw -ntp --batch-mode -s etc/maven/settings.xml initialize test-compile failsafe:integration-test failsafe:verify 
    -Pcontracts
    -Dmaven.repo.url=URL
    -Dmaven.repo.username=USERNAME
    -Dmaven.repo.password=PASSWORD
```

## Pipeline

The following environment variables must be set:

```
SONAR_URL
SONAR_TOKEN
MAVEN_URL
MAVEN_USERNAME
MAVEN_PASSWORD
```
