# StudCare Back End Service
This repository holds all the backend level logics for StudCare application.

## Pre-requisites
* Gradle

## Steps
1. Configure `gradle.properties`

| Configuration                | Description               |
|------------------------------|---------------------------|
| project.group                | ArtifactID                |
| project.version              | Stub Version              |
| project.source.compatibility | Java Version              |

2. Build Project : `./gradlew clean build`

3. Execute : `./gradlew bootRun`

## Docker
DB hosted in Azure

1. Build Jar : `./gradlew clean build`

2. Build Docker Image : `docker build -t stud-care-backend-service:latest .`

3. Run Docker Container : `docker run -p 8080:8080 stud-care-backend-service:latest`

Or can be pulled from docker hub : `docker pull pwick/stud-care` and run the image

