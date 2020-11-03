# setup db

```
#note db password is 'password'
docker-compose up
psql -h localhost -p 5432 -U postgres -f ./postgres/create.sql
psql -h localhost -p 5432 -U postgres -f ./postgres/data.sql
```

#launch app
```
./gradlew clean :quarkus-api:quarkusDev
```

#issues
## uncomplete tests

tests we not able to be executed due to an issue with kotlin binding to quarkus entity bindings.

tests can be located at src/test/kotlin/co/my/app/CliTest.kt

```
cat ./quarkus-api/src/test/kotlin/co/my/app/CliTest.kt
```

#easter eggs

## binary compilation - uncomplete (requires docker environment or host graalvm setup)

```
# https://quarkus.io/guides/gradle-tooling#building-a-native-executable
./gradlew clean build -Dquarkus.package.type=native && ./quarkus-api/build/quarkus-api-unspecified-runner
```