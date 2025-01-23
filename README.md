## Requirements
### Java 17
Check you have java 17 or later with `java --version`

## Running project from IntelliJ IDEA

- Launch IntelliJ IDEA.
- Click File > Open... and select the root folder of the project.
- Install maven dependencies from Maven > Reload
- Once it's finished, select `CourierTrackingApplication` as your run configuration
- Run the project



## Running project from Terminal
### Install dependencies

If you are on a unix system, run `chmod +x ./mvnw` to make sure it is executable.

Run `./mvnw install` to install maven dependencies.


### Run project

`./mvnw spring-boot:run`

## Example Requests
Check the example request from [example-requests.http](example-requests.http)