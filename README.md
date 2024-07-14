# Tinkering with files and streams

This is a playground project to test the following process:
* File-Consumer application gets triggered with a "groupId"
* It gets the list of all files in the group from a File-Provider application (which delivers a very slow event stream)
* Then it downloads each file from the File-Provider
* And returns the total downloaded size

The File-Consumer is implemented twice, as a reactive Spring-Boot application (on port 8002) and as an MVC Spring-Boot application (on port 8003).

The File-Provider is a reactive Spring-Boot application (on port 8001).

The APIs used by these applications can be found under `spec`.

## Requirements

* Java 21
* Maven 3.9

## How to use

Build with

```shell
mvn clean install -DskipTests
```

Run each application in a separate terminal:

````shell
mvn -f file-provider spring-boot:run
mvn -f file-consumer-reactive spring-boot:run
mvn -f file-consumer-mvc spring-boot:run
````

Call the `/trigger` endpoint on a consumer application:

````shell
curl -v http://localhost:8002/trigger/5  # reactive
curl -v http://localhost:8003/trigger/5  # mvc
````

Observe the logs of the triggered consumer as well as the provider: both consumer implementations should show the same behaviour.
