# Basic Akka HTTP service deployable on Heroku with sbt-heroku

## Requirements
- sbt
- docker

## How to run
First, publish a Docker image of this application locally
```bash
sbt docker:publishLocal
```
and then run the full setup with
```bash
docker-compose up
```
