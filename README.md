# Basic Akka HTTP service deployable on Heroku with sbt-heroku

## Requirements
This is an application that is built and deployed via sbt, and thus
requires a working installation of sbt.

In order to deploy this application to Heroku, you need a Heroku account, 
as well a created Heroku application, i.e. you ran 
```bash
heroku create -n
```
with the Heroku CLI.

## How to deploy
```bash
sbt -Dheroku_name=<HEROKU_NAME> clean compile stage deployHeroku
```

Note: deploying via git (`git push heroku master`) will *BREAK* this application. 

## Environment
This application uses a few environment variables which can be overriden.
A `.env` file can be used as follows:
```
cp .env.sample .env
```
and configure your custom values from there.

In case of config running on Heroku, you can also set environment variables
via the `herokuConfigVars` in the `build.sbt`, via the Heroku CLI (`heroku config:add`) 
or via the Heroku Dashboard.
