Play Kafka Example
------------------

Start docker container with Apache Kafka
```
docker-compose up -d
```

Create a topic and start publisher
```
docker exec -it kafka /bin/bash
cd $KAFKA_HOME/bin
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
./kafka-console-producer.sh --broker-list localhost:9092 --topic test
```

Start web application
```
sbt run
```

and open the app in browser [http://localhost:9000](http://localhost:9000)
