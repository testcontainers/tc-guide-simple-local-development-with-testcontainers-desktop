# GUIDE_TITLE

This is sample code for [GUIDE_TITLE](https://testcontainers.com/guides/GUIDE_URL) Guide.

## 1. Setup Environment
Make sure you have a [compatible Docker environment](https://www.testcontainers.org/supported_docker_environment/) installed.

```shell
$ docker version
...
Server: Docker Desktop 4.12.0 (85629)
 Engine:
  Version:          20.10.17
  API version:      1.41 (minimum version 1.12)
  Go version:       go1.17.11
...
```

* Add language-specific prerequisites here

**For Java:**
Make sure you have Java 8+ installed.
If you are going to use Maven build tool then make sure Java 17+ is installed.

For example:

```shell
$ java -version
openjdk version "17.0.4" 2022-07-19
OpenJDK Runtime Environment Temurin-17.0.4+8 (build 17.0.4+8)
OpenJDK 64-Bit Server VM Temurin-17.0.4+8 (build 17.0.4+8, mixed mode, sharing)
```

## 2. Setup Project

* Clone the repository

```shell
git clone https://github.com/testcontainers/TC_GUIDE_REPO_NAME.git
cd TC_GUIDE_REPO_NAME
```

* Open the **TC_GUIDE_REPO_NAME** project in your favorite IDE.

## 3. Run Tests

Run the command to run the tests.

```shell
$ ./gradlew test //for Gradle
$ ./mvnw verify  //for Maven
$ go test ./...  //for Go
$ npm test       //for Node
```

The tests should pass.
