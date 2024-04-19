
首先启动 zookeeper
/home/kafka/bin/zookeeper-server-start.sh /home/kafka/config/zookeeper.properties

创建SCRAM证书
/home/kafka/bin/kafka-configs.sh --zookeeper localhost:2181 --alter --add-config SCRAM-SHA-256=[iterations=8192,password=liebe],SCRAM-SHA-512=[password=liebe] --entity-type users --entity-name liebe

/home/kafka/bin/kafka-configs.sh  --zookeeper 127.0.0.1:2181 --alter --add-config SCRAM-SHA-256=[password=admin],SCRAM-SHA-512=[password=admin] --entity-type users --entity-name admin


证书查看
/home/kafka/bin/kafka-configs.sh --zookeeper localhost:2181 --describe --entity-type users --entity-name admin


证书删除
/home/kafka/bin/kafka-configs.sh --zookeeper localhost:2181 --alter --delete-config SCRAM-SHA-512 --delete-config SCRAM-SHA-256 --entity-type users --entity-name liebe


服务端配置
在 kafka 配置文件目录 config 创建文件 kafka-server-jass.conf，如我的目录是：/home/kafka/config
文件内容为：
KafkaServer {
        org.apache.kafka.common.security.scram.ScramLoginModule required
        username="admin"
        password="admin";
};

kafka-run-class.sh文件追加内容
# Generic jvm settings you want to add
if [ -z "$KAFKA_OPTS" ]; then
  KAFKA_OPTS="-Djava.security.auth.login.config=/home/kafka/config/kafka-server-jass.conf"
fi

/home/kafka/config/server.properties设置配置
broker.id=0
listeners=SASL_PLAINTEXT://:9092
security.inter.broker.protocol=SASL_PLAINTEXT
sasl.mechanism.inter.broker.protocol=SCRAM-SHA-256
sasl.enabled.mechanisms=SCRAM-SHA-256
advertised.listeners=SASL_PLAINTEXT://10.10.10.99:9092
allow.everyone.if.no.acl.found=false
super.users=User:admin
authorizer.class.name=kafka.security.authorizer.AclAuthorizer
listener.security.protocol.map=PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL
zookeeper.set.acl=true
zookeeper.authProvider.sasl=org.apache.zookeeper.server.auth.SASLAuthenticationProvider
zookeeper.sasl.client=true
zookeeper.sasl.clientconfig=Server
zookeeper.sasl.login.context=Server
num.network.threads=3
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600
log.dirs=/home/kafka/kafka-logs
num.partitions=1
num.recovery.threads.per.data.dir=1
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
log.flush.interval.messages=10000
log.flush.interval.ms=1000
log.retention.hours=168
log.retention.bytes=1073741824
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000
zookeeper.connect=localhost:2181
zookeeper.connection.timeout.ms=18000
group.initial.rebalance.delay.ms=0
delete.topic.enable=true
auto.create.topics.enable=true


/home/kafka/config/producer.properties
/home/kafka/config/consumer.properties
/home/kafka/config/auth.conf

将下面的内容追加到producer.properties和consumer.properties,并创建auth.conf文件
security.protocol=SASL_PLAINTEXT
sasl.mechanism=SCRAM-SHA-256
sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required username="admin" password="admin";


启动kafka
/home/kafka/bin/kafka-server-start.sh /home/kafka/config/server.properties

创建topic命令

/home/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic test --command-config /home/kafka/config/auth.conf


发送消息

/home/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test --producer.config /home/kafka/config/auth.conf


控制台监听消费消息

/home/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning --consumer.config /home/kafka/config/auth.conf


警告日志
[2024-04-19 22:36:49,196] WARN SASL configuration failed. Will continue connection to Zookeeper server without SASL authentication, if Zookeeper server allows it. (org.apache.zookeeper.ClientCnxn)
javax.security.auth.login.LoginException: No JAAS configuration section named 'Client' was found in specified JAAS configuration file: '/home/kafka/config/kafka-server-jass.conf'.
	at org.apache.zookeeper.client.ZooKeeperSaslClient.<init>(ZooKeeperSaslClient.java:189)
	at org.apache.zookeeper.ClientCnxn$SendThread.startConnect(ClientCnxn.java:1157)
	at org.apache.zookeeper.ClientCnxn$SendThread.run(ClientCnxn.java:1207)
[2024-04-19 22:36:49,200] ERROR [ZooKeeperClient ConfigCommand] Auth failed, initialized=false connectionState=CONNECTING (kafka.zookeeper.ZooKeeperClient)
Completed updating config for entity: user-principal 'admin'.
