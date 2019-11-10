# 建造者模式（Builder）

## 意图

> Separate the construction of a complex object from its representation so that the same construction process can create different representations.
>
> 将复杂对象的创建和它的行为表现分开，这样同样的创建流程就可以构造不同行为表现的对象了。

例如，一个富文本阅读器（RTFReader）需要将一段富文本转换成其他格式，例如无格式文本，或者 markdown 格式，或者其他任意可以定义的格式。转换的流程是一致的，主要分为转换纯文本、转换字体表示方法、转换段落表示方法等步骤，这些步骤可以理解为一个复杂对象（各种表现形式的文本）的构建过程。对于 RTFReader (Director) 来说，它仅需要依赖一个抽象的 TextConverter (Builder) 来实现一个统一的转换（构建）流程即可，至于具体的转换（构建）算法，则由具体的 PlainTextConverter、MarkdownConverter 等来实现。

## 适用场景

- **当**复杂对象的构建算法应该独立于这个复杂对象的组成部分和组装方式时
- **当**构建过程必须允许被构建的对象有不同的行为表现时

## 结构图

![建造者模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191110172515.png)

## 参与角色

- Builder (TextConverter) 
  - 指定创建 Product 的每一个部件（part）的接口
- ConcreteBuilder (PlainTextConverter, MarkdownConverter) 
  - 实现 Builder 接口，也就是实现具体的构建、组装部件（part）的算法
  - 定义和记录需要构建的相应表现的 Product
  - 提供一个返回最终 Product 的接口
- Director (RTFReader)
  - 使用 Builder 接口构建一个复杂对象（控制构建流程）
- Product (PlainText, MarkdownText)
  - 被构建的复杂对象
  - 提供部件（part）的定义，组装接口供 ConcreteBuilder 调用
  - Product 不是一个抽象类而是一个具体类，因为 Builder 只强调构建过程是一致的，而不要求被构建的对象有一致的表现行为

## 好处

- 更换一个新的构建者对象（Builder），就可以构建一个不同表现形式的对象。
- 利用 Builder 隐藏了复杂对象的组成部分，以及复杂对象的构建和组装的细节，这样复杂对象可以只对外暴露“功能”而不暴露具体的实现，提高了封装性
- 利用 Builder 暴露的接口可以干预对象创建的流程

## 思考

其实复杂对象的构建往往都要用 Builder 模式，一个参数过多的构造函数肯定是不友好的。

另外，封装的原则就是暴露应该暴露的，隐藏应该隐藏的，Builder 接口暴露的方法应该是构建流程中调用方需要关心的。

最后，也可以考虑提供一组静态方法，返回一些接口提供方推荐的组装方式，减小调用方的使用难度。例如，Java 并发工具包中的 Executors 类（虽然 Executors 构建的对象没有用到 Builder 模式）。