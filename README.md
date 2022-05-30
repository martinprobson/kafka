# Kafka Producer/Consumer and Spark Structured Streaming Playground

## Introduction
This repo contains code to produce, consume and spark structured streaming consume from a Kafka topic. The following projects
are contained within this repo: -

| Directory            | Contents                                                                                  |
| ---------            | --------                                                                                  |
| cats-consumer        | A Kafka consumer setup using the [CATS Effect libray](https://typelevel.org/cats-effect/) |
| consumer             | Scala code to consume from a Kafka topic                                                  |
| producer             | Scala code to produce (integers) to a Kafka topic                                         |
| kafka-test-cluster   | Setup a 3 broker test cluster                                                             |
| kafka-spark-consumer | A structured Spark streaming project to consume from a kafka topic                        |


## Producer
The producer project publishes a set of integers to the Kafka topic specified in configuration (`reference.conf`), the number of
messages to publish is also specified in configuration. 

## Consumer
The consumer consumes from the topic and outputs the results to the console.

## Kafka Spark Consumer
This project uses spark structured streaming to consume from the Kafka topic.


Martin Robson 26/01/2019 (updated 30/05/2022).
