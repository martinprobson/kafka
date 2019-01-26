# Kafka Producer/Consumer and Spark Structured Streaming Playground

## Introduction
This repo contains code to produce, consume and spark structured streaming consume from a Kafka topic. The following projects
are contained within this repo: -

| Directory            | Contents                                                           |
| ---------            | --------                                                           |
| consumer             | Scala code to consume from a Kafka topic                           |
| producer             | Scala code to produce (integers) to a Kafka topic                  |
| kafka-docker         | A git submodule project (see kafka-docker below)                   |
| kafka-spark-consumer | A structured Spark streaming project to consume from a kafka topic |


## Kafka Docker
This is a git submodule from [here](https://github.com/wurstmeister/kafka-docker) that allows us to spin up a 
test Kafka environment in docker.
For our test purposes, we only need a single node which can be spun up using: -

```bash
    docker-compose -f docker-compose-single-broker.yml up
``` 

## Producer
The producer project publishes a set of integers to the Kafka topic specified in configuration (`reference.conf`), the number of
messages to publish is also specified in configuration. 

## Consumer
The consumer consumes from the topic and outputs the results to the console.

## Kafka Spark Consumer
This project uses spark structured streaming to consume from the Kafka topic.


Martin Robson 26/01/2019
