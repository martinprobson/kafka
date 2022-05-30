
CATS Kafka Consumer
==============================

# Overview
This is an implementation of a Kafka Consumer using the [CATS Effect 3](https://typelevel.org/cats-effect/) functional library. 

Note that this code is purely for learning purposes and is not intended for production code (there is 
already a [fs2 Kafka library](https://fd4s.github.io/fs2-kafka/) that uses the CATS ecosystem).

The advantages of using CATS effect for this are: -
* The guarenteed opening and closing of the consumer using the [Resource](https://typelevel.org/cats-effect/docs/std/resource) construct.
* Easy to spin up multiple concurrent consumers (with timeouts) using the combinators provided by CATS (such as `IO.replicateA`).

