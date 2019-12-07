# 中介模式（Mediator）

## 意图

> Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeting objects from referring to each other explicitly, and it lets you vary their interaction independently.
>
> 定义一个对象封装一系列对象的交互逻辑。中介模式使得对象不同直接调用其他对象，从而促进了对象间的松耦合，中介模式同时使你可以独立改变对象间的交互。

一个系统中对象间的交互可能非常复杂，某些对象的某个动作可能要触发很多对象的一系列动作。你可以将这些交互封装在一个中介对象中，中介对象负责控制和协调一组对象，对象间不用知道彼此的存在，而只需要和中介对象沟通。通过中介对象，就将对象间的强耦合关系解除了。

## 适用场景

- 当一系列对象间有明确的但是复杂的交互的时候。
- 当一个对象依赖了很多其他对象并与它们之间有交互，使得这个对象的可复用度降低的时候。
- 当一个行为分布在多个类中，且这个行为需要定制化而又不希望有复杂的继承关系的时候。

## 结构图

![中介模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191207214735.png)

一个中介模式的典型对象关系如下图所示：

![中介模式对象关系图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191207214823.png)

## 参与角色

- Mediator
  - 定义和 Colleague 对象交互的接口
- ConcreteMediator
  - 实现 Mediator 定义的接口
  - 清楚并维护它的 Colleague 对象
- Colleague classes
  - 知道它的 Mediator
  - 在特定实际与 Mediator 交互

## 流程

- Colleague 接受来自 Mediator 的请求或者发送请求到 Mediator。Mediator 通过调用不同对象的方法实现对象间的交互逻辑。

## 结果

- 减少子类数量。对象间的交互逻辑集中在中介对象中，而不是分散在对象之间，使得需要扩展功能只需要继承中介类，而不是继承很多类。
- 使得对象解耦。需要交互的对象本来是紧耦合关系，通过中介模式，对象并不需要知道与它交互的对象的细节，而只需要和中介对象交互。
- 将多对多的关系简化为一对多的关系，使得系统逻辑更容易被理解。
- 封装了对象的交互逻辑，中介类更专注于对象间的交互。
- 中介类可能变得非常复杂而不易于维护。

## 实现

- 抽象的 Mediator 可以省略。
- Colleague 与 Mediator 可以有两种交互方式：一是可以使用观察者模式（Observer），当 Colleague 状态变化时通知 Mediator；二是 Colleague 直接调用 Mediator 的方法。

## 与其他模式的关系

- 门面模式（Facade）用于简化复杂接口的使用，门面依赖系统接口，而系统接口完全不知道门面的存在。而中介模式则实现了对象间的交互，中间与其管理的对象互相知道对方的存在。
- 观察者模式（Observer）可用于实现中介与其管理的对象间的交互。