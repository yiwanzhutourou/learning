# Kafka 基础

## Kafka 是什么？

> Apache Kafka 是一款开源的消息引擎系统。根据维基百科的定义，消息引擎系统是一组规范。企业利用这组规范在不同系统之间传递语义准确的消息，实现松耦合的异步方式数据传递。通俗来讲，就是系统 A 发送消息给消息引擎系统、系统 B 从消息引擎系统中读取 A 发送的消息。
>
> 消息引擎系统要定义具体的传输协议，即我们用什么方法把消息传输出去，常见的方法有两种：点对点模型；发布 / 订阅模型。Kafka 同时支持这两种消息引擎模型。

> Kafka 是消息引擎系统，也是一个分布式流处理平台。

## 关键概念

![Kafka 关键概念示意图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191212190502.png)

- 消息：Record。Kafka 处理的主要对象。
- 主题：Topic。主题是承载消息的逻辑容器，在实际使用中多用来区分具体的业务。
- 分区：Partition。一个有序不变的消息序列。每个主题下可以有多个分区。
- 消息位移：Offset。表示分区中每条消息的位置信息，是一个单调递增且不变的值。
- 副本：Replica。Kafka 中同一条能够被拷贝到多个地方以提供数据冗余，这些地方就是所谓的副本。副本分为领导者副本（Leader Replica）和追随者副本（Follower Replica），领导者副本对外提供服务，与客户端交互，而追随者副本不能对外提供服务，它只做一件事：向领导者副本发送请求，请求领导者副本把最新生产的消息发给它，保持与领导者副本同步。副本是在分区层级下的，即每个分区可配置多个副本实现高可用。
- 生产者：Producer。向主题发布消息的应用程序。
- 消费者：Consumer。从主题订阅消息的应用程序。
- 客户端：Client。生产者和消费者都是客户端。
- 服务器端：Broker。一个 Kafka 集群由多个 Broker 组成。Broker 负责接收和处理客户端发送过来的请求，以及对消息进行持久化。
- 消费者位移：Consumer Offset。表征消费者消费进度，每个消费者都有自己的消费者位移。
- 消费者组：Consumer Group。多个消费者实例共同组成一个组，同时消费多个分区已实现高吞吐。
- 重平衡：Rebalance。消费者组内某个消费者实例挂掉后，其他的消费者实例自动重新分配订阅主题分区的过程。

## 笔记

[介绍](https://github.com/yiwanzhutourou/learning/tree/master/kafka-basics/notes/介绍.md)

## 参考资料

1. [Kafka 中文文档](http://kafka.apachecn.org/)
2. [Kafka 核心技术与实战](https://time.geekbang.org/column/intro/100029201)