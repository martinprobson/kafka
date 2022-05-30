# Setup a Test Kafka Cluster

## Overview
Setup a test kafka cluster with : -
* A single zookeeper node.
* Three brokers.
* A [kafdrop](https://github.com/obsidiandynamics/kafdrop) UI running on port 9010.
* A [kafka-ui](https://github.com/provectus/kafka-ui) running on port 8080.

## Setup
The brokers expect a volume mounted pointed to by `$KAFKA_DATA` environment variable. This can be setup in a `.env` file that will be
used by docker-compose: -

```
KAFKA_DATA=<path to a directory on host where topics will be stored>
```

Then run: -

```
docker-compose up -d
```
