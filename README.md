# spring-boot-kafka-template
I) StartUp Kafka:
1> ./zookeeper-server-start.sh ../config/zookeeper.properties

2> Put below two lines in the server.properties 
listeners=PLAINTEXT://localhost:9092
auto.create.topics.enable=false

3>./kafka-server-start.sh ../config/server.properties

II) How to create a topic from command line

1> Make sure zookeeper and the broker is running and 
2> navigate to the bin directory

3> ./kafka-topics.sh --create --topic topic-saket-1 -zookeeper localhost:2181 --replication-factor 1 --partitions 4

III) Startup the Prodcucer
 
1> from bin directory run the command
   ./kafka-console-producer.sh --broker-list localhost:9092 --topic topic-saket-1

IV) Startup the Consumer

1> from bin directory run the command
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-saket-1 --from-beginning

Below command for reading the messages after the consumer is instanciated and the order will changes as the messages will be posted to different partitions, to gurantee the order of the message send the message with key.

./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-saket-1


V) Messages with Key and value

1> The partitioner first checks if the message contains a key
   if the message does not have key then the message is sent by using the round robin approach  to all the partitions. All the message are sent to all the partitions and they can be saved in any one of them.
   When the consumer reads the message then there will be no ordering defined.As the consumer polls the messages from all the partitions.
   
2> When message is sent with a key then the Partitioner uses hashing technique on the key to determine the unique partition to select.
Message with same key lands on the same partition this will allow consumers to read the messages in an ordered fashion

Start the producer and the consumer with this command
./kafka-console-producer.sh --broker-list localhost:9092 --topic topic-saket-1 --property "key.separator=-" "parse.key=true"

./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-saket-1 --from-beginning -property "key.separator= -" --property "print.key=true"

VI) List the topics present in the cluster
./kafka-topics.sh --zookeeper localhost:2181 --list
__consumer_offsets is the default topic present by default.

VII) CONSUMER GROUPS
 1. Consumer groups are used for scalable message consumption and applications have unique consumer groups
 2. Kafka broker manages the consumer-groups
 3. kafka broker acts as the Group Co-ordinatior
 
 4. View Consumer groups by
    ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
 5. Every time a consumer is spun up without explicitly providing the consumer group a 	 default group is created every time.
 
 6. Start consumer with group name
 
 ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic saket-topic-1 --group console-consumer-1
 
 --------------------------------------------------------------------------------------------
 event-Producer microservice
 POST WITH-OUT STATUS
Post to the controller

curl -i \
-d '{"libraryEventId":null,"book":{"bookId":456,"bookName":"Kafka Using Spring Boot","bookAuthor":"Dilip"}}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/v1/libraryevent

https://docs.spring.io/spring-kafka/reference/html/#sending-messages
