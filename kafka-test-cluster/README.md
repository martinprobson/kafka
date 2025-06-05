# Setup a Test Kafka Cluster

## Overview
Setup a test kafka cluster with : -
* Three controllors
* Three brokers.
* A [kafka-ui](https://github.com/provectus/kafka-ui) running on port 8080.

See "multiple nodes section" [here](https://hub.docker.com/r/apache/kafka)

Run: -
```
docker-compose up -d
```

**Note:** The kafka data is created inside the containers so is not saved when the cluster is 
shutdown.
