# 用 Java 实现《Design Patterns》一书中的例子

用 Java 语言实现 GoF (Gang of Four) 《Design Patterns》一书中所有 23 个设计模式的例子。这 23 个设计模式罗列如下：

- 创建型模式 - Creational Patterns
  - 抽象工厂 - Abstract Factory
  - 建造者模式 - Builder
  - 工厂方法 - Factory Method
  - 原型模式 - Prototype
  - 单例模式 - Singleton
- 结构型模式 - Structural Patterns
  - 适配器模式 - Adapter
  - 桥接模式 - Bridge
  - 组合模式 - Composite
  - 装饰器模式 - Decorator
  - 门面模式 - Facade
  - 享元模式 - Flyweight
  - 代理模式 - Proxy
- 行为型模式 - Behavioral Patterns
  - 职责链模式 - Chain of Responsibility
  - 命令模式 - Command
  - 解释器模式 - Interpreter
  - 迭代器模式 - Iterator
  - 中介模式 - Mediator
  - 备忘录模式 - Memento
  - 观察者模式 - Observer
  - 状态模式 - State
  - 策略模式 - Strategy
  - 模板方法 - Template Method
  - 访问者模式 - Visitor

#### 思考

任何设计模式都应该被当做“参考模型”来理解，实际使用的时候不能硬套，也就是不能为了模式而模式，一切设计模式原则上都是为了代码的易用、易维护服务的。

在代码结构设计时我们可以始终问自己几个问题，来检验代码结构是否合理，例如：

- 如果要做功能扩展，我应该如何做？是要修改原有代码还是新增类做扩展呢？
- 这个类或者模块的职能是不是太多了？可否进一步拆分呢？
- 一定要依赖类或者模块的具体实现吗？可否修改为依赖接口呢？
- 这里用**继承**方式好还是用**组合**方式好？
- 调用方要调用我设计的接口，理解成本有多少？使用成本有多少？哪些功能是我内部就处理好的，哪些是需要调用方自己考虑的？