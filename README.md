[![Build Status](https://travis-ci.org/tatitati/play_slick_project.svg?branch=master)](https://travis-ci.org/tatitati/play_slick_project)



![Build history](https://buildstats.info/travisci/chart/tatitati/play_slick_project?branch=master)

### Notes:
1- Evolution is broken. Since we introduced DI with GUICE evolutions doesnt work. This is because there is a different approach as it is pointed in the doc:
https://www.playframework.com/documentation/2.6.x/PlaySlick#databaseconfig-via-runtime-dependency-injection

2- There is no way of running evolutions by command line. This is a very big fucking shame

# Research

cHow to run before/after (setUp/teardDOwn) with ScalaTest? --> getting "no application started error"
- [x] ~Run plain sql with slick~
- [x] ~Select only some fields, not all~
- [x] ~Implement different style of dependency injection (nottations, and explicit binding)~
- [ ] Joins and Mappers?, what I get when requesting nested entities?
- [ ] DBIO vs Query
- [ ] Filter queries

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

http://localhost:9000/create-db   -> play is so shit that only can create the db once is running through the UI (shame)
http://localhost:9000/insert


# Gitter:
[![Join the chat in Scala](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/explore/tags/curated:scala,scala) => Scala

[![Join the chat in Scala](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/slick/slick) => Slick


[![Join the chat in Scala](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/playframework/playframework)
=> Play

