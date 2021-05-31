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

## CI/CD pipeline

### The following environment variables must be set:

Common for the project:

```
SONAR_URL
SONAR_TOKEN

MAVEN_URL
MAVEN_USERNAME
MAVEN_PASSWORD

SERVER_USER
SERVER_IP
$ID_RSA

COMMON_DOCKER_PARAMS
```

Unique for the app:

```
APP_NAME
APP_DOCKER_PARAMS
```
