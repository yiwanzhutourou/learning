# 装饰器模式（Decorator）

## 意图

> Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.
>
> 为对象动态增加额外的职责。装饰器模式为扩展功能提供了一种继承以外的灵活选项。

我们在开发中往往要扩展一个类的功能，我们可以采用继承的方式扩展父类的功能，但是利用继承来做扩展往往不够灵活；我们也可以利用组合的方式来扩展功能，当系统中的类很多，功能扩展有很多不同的维度时，采用组合的方式往往更灵活，其中装饰器模式就是一种典型的组合方式。我们来看一个典型的采用装饰器模式的例子，`java.io` 包下的各种读写流。

我们知道 Java 标准类库中对应字节流的输入输出的抽象基类分别是 `InputStream` 和 `OutputStream`，而对应字符流的输入输出的抽象基类分别是 `Reader` 和 `Writer`，它们的各种子类基本是一一对应的关系，我们就以其中的输出流为例。所有的输出流基本分为了两大类，一类是提供真正的写实现的类，例如 `FileOutputStream` 和 `ByteArrayOutputStream` 分别代表的是输出到文件的流，和输出到内存 `byte` 数组的流，如果你去查看这两个类的源码，你会发现，它们的 `write` 函数是有真正的输出实现的。另外一大类输出流是对其他输出流的功能增强，例如 `BufferedOutputStream` 为输出流增加了缓冲区的功能；`OutputStreamWriter` 增加了对字节流的编码，以字符的形式输出；`ObjectOutputStream` 将 Java 对象结构化输出等，这些类本质上就是一个“装饰器”，如果你去看它们的 `write` 方法，最终都是调用了被它们装饰的输出流的 `write` 方法。例如，下面的代码构造了一个有缓冲区的文件输出流：

```java
BufferedOutputStream out = new BufferedOutputStream(
    new FileOutputStream('filename'));
```

通过装饰器模式构造输出流还可以通过多层嵌套的方式来达到增强多个功能的目的，这也是装饰器模式，或者说通过组合方式来扩展功能的灵活之处，例如，下面的代码构造了一个将 Java 对象输出到文件的带缓冲区的流：

```java
ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                    new FileOutputStream("filename")));
```

试想，如果通过继承的方式实现这些输出流，那么一个“带有缓冲区的输出到文件的 Java 对象流”应该继承“文件输出流”还是“对象输出流呢”？如果我想对项目中已经存在的各种“文件输出流”、“对象输出流”、“字符输出流”都增加一个加密的功能，那么我是不是又要增加三个新的子类呢？而如果采用装饰器模式，我只需要定义一个 `EncryptedOutputStream` 来做功能增强即可。

面向对象编程的原则之一告诉我们，要优先考虑使用组合，而非继承，装饰器模式其实就提供了一种使用组合的思路。

## 适用场景

- 在不影响原对象功能的情况下为这个对象动态增加新的能力。
- 当需要各种功能可以灵活组装使用的时候，例如既有带缓冲区的流的使用场景，又有不带缓冲区的流的使用场景的时候。

- 当无法使用继承来扩展功能的时候。通常不能使用继承的场景有：1. final 类，无法继承；2. 需要扩展的功能维度很多，采用继承需要实现不同功能组合的各种子类，而导致类数量爆炸。

## 结构图

![装饰器模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191124170955.png)

## 参与角色

- Component
  - 定义可以动态增加功能的对象的抽象接口
- ConcreteComponent
  - 定义可以动态增加功能的对象
- Decorator
  - 依赖 Component 对象
  - 定义与被装饰对象一致的接口，其行为应该与 Component 基本一致
  - 方法的实现依赖 Component 对应的方法，可以在执行 Component 方法前后增加自己的逻辑来达到增强原方法的目的
  - 对 Component 是透明的，也就是说 Component 无需知道 Decorator 的存在
- ConcreteDecorator
  - 提供增强的功能

## 好处

- 比静态的继承更灵活。
- 使用简单的类增量地添加功能，而不是一开始就设计一个复杂的类，降低了维护成本，提高了系统扩展的灵活性。

## 不足

- 装饰器和被装饰对象是不同的对象，使用装饰器模式后，程序中依赖对象相等的逻辑需要做调整，或者应该尽量避免依赖对象相等的逻辑。
- 会造成系统中小对象的数量增加，对于不熟悉系统实现的开发来说理解和维护成本提高。

## 实现

- 装饰器类必须拥有和被装饰的类一致的接口，这样在使用时可以不区分使用的到底是原来的类还是装饰后的类。
- 装饰器可以不抽象基类（Decorator 角色）。
- 被装饰的类必须保持轻量级，例如输出流的基类 `OutputStream` 只定义了一个抽象的 `write` 接口，专注于少量的功能点更利于使用装饰器模式增加能力，否则如果装饰器只增强部分接口，那么实现剩下的接口就会成为装饰器额外的负担。
- 装饰器只对装饰类做功能增强，而不修改其具体逻辑。