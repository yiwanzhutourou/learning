# 状态模式（State）

## 意图

> Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.
>
> 允许一个对象在其内部状态改变时改变它的行为。这个对象显得像是改变了它的类。

将一个类的状态相关的方法都定义在一个状态接口中，不同的状态类实现这个接口，实现不同状态下执行这个方法的逻辑。这个类内部维护一个状态的引用，并将所有状态相关的方法委托给这个状态对象执行，当运行时改变这个内部状态时，看起来就好像这个类的实现被改变了，事实上只是更换了不同的代理对象。

## 适用场景

- 当对象的行为随它的内部状态改变而改变，并且它的内部状态会在运行时改变时。
- 使用状态模式改写 `if` `else` 逻辑，将每一个条件分支的逻辑抽象为一个状态类。

## 结构图

![状态模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191207231124.png)


## 参与角色

- Context
  - 定义客户端需要的接口
  - 维护一个 ConcreteState 对象
- State
  - 定义 Context 与内部状态相关的接口
- ConcreteState
  - 每一个 ConcreteState 实现在特定状态下的行为

## 流程

- Context 对象将状态相关的方法委托给当前的 ConcreteState 对象执行。
- Context 对象可能将自己传递给 ConcreteState 供其获取必须的数据。
- 客户端可以配置 Context 的状态如何变化（例如调用特定方法时），一旦配置成功后，客户端就不需要直接使用 State 对象了。
- Context 和 ConcreteState 都可以决定 Context 的状态是否以及如何变化。

## 结果

- 将状态相关的逻辑封装到一个类中，使得新增状态变得相对容易，只需要实现状态接口增加新状态下的逻辑，而不是增加新的 `else` 逻辑。
