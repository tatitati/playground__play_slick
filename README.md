[![Build Status](https://travis-ci.org/tatitati/play_slick_project.svg?branch=master)](https://travis-ci.org/tatitati/play_slick_project)

# Research

- [ ] How to execute Play evolutions without web UI?


## Set up

```
sbt reload update
```

## Run

```
sbt run
```

## run tests

```
sbt test
```


## Some stuff to play

http://localhost:9000

http://localhost:9000/hello

http://localhost:9000/detail

http://localhost:9000/list


**Using DI:**

http://localhost:9000/say  -> this route is using a service injected with Guice (DI)

http://localhost:9000/sayenglish  -> this route is using a service injected with Guice (DI)


**Using slick:**

http://localhost:9000/insert

