# 职责链模式（Chain of Responsibility）

## 意图

> Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.
>
> 通过提供多个对象处理请求来避免请求的发送者和接收者耦合在一起。将处理请求的对象链接起来，将请求依次传递给它们，直到其中一个对象处理了这个请求为止。

例如一个后端系统定义了很多拦截器来拦截非法的请求，某一个具体请求到来时，并不知道这个请求会不会被拦截，以及会被哪个拦截器拦截，可以将这些拦截器组织为一个链条结构，将请求交给链条上的第一个拦截器，如果拦截器拦截了请求，则拒绝请求，否则就将请求传递给下一个拦截器，以此类推，直到请求被拦截，或者所有拦截器都没有拦截。

## 适用场景

- 有多个对象都可以处理一个请求，且事先不知道具体哪个对象可以处理这个请求。
- 将一个请求交给一些类对象处理而又不指定具体的对象。
- 可以处理请求的对象集合需要动态指定。

## 结构图

![职责链模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191206210133.png)

一个典型的对象结构可能如下所示：

![职责链模式对象结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191206210134.png)

## 参与角色

- Handler
  - 定义处理请求的接口
  - （可选的）实现后继者链接
- ConcreteHandler
  - 处理可以处理的请求
  - 可以获取它的后继者
  - 如果可以处理请求，则处理请求，否则将请求转发给它的后继者
- Client
  - 将请求交给责任链上的对象

## 结果

- 降低耦合度，使得客户端不需要知道哪个对象处理哪个请求。
- 可以灵活地增加或者减少处理请求的对象。
- 不能保证请求一定会被处理。
