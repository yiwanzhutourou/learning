# 原型模式（Prototype）

## 意图

> Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.
>
> 使用一个原型实例来决定要创建的对象的种类，并且通过拷贝原型对象来创建新的对象。

## 适用场景

当系统应该独立于它的组件的创建、组合和表现时，并且

- 运行时才能决定具体实例化哪个类
- 或者，避免给每一类产品都定义一个工厂类，以减少系统中类的数量
- 或者，当一个类的状态有限时，可以考虑为每一种状态定义一个原型，并在需要时拷贝对应的原型，而不是直接创建带有合适状态的对象

## 结构图

![原型模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191127213041.png)

## 参与角色

- Prototype
  - 定义一个可以克隆（clone）自身的接口
- ConcretePrototype
  - 实现克隆（clone）接口
- Client
  - 通过克隆原型来创建对象

## 好处

原型模式和抽象工厂以及建造者模式很像，都对调用方屏蔽了创建的具体对象是什么。它的优点有：

- 运行时动态创建和销毁对象，原型模式比其他的创建型模式更灵活的地方在于，它不是通过继承父类来改变创建的对象，而是在运行时动态决定要创建的对象。
- 相对于工厂方法，原型模式不需要继承父类来改变构造的对象，因此可以减少系统中类的数量。

## 限制

- 某些类实现 `clone` 方法可能比较困难，例如某些类的成员变量不是 `Clonable` 的。

## 实现

- 使用原型管理器（PrototypeManager）管理需要创建的原型，调用方可以绑定、解绑要创建的原型。
- 正确地实现 `clone` 接口。考虑是否需要实现深拷贝（deep copy），还是浅拷贝（shadow copy）就可以满足需求了；考虑是否需要提供一个拷贝构造方法（copy constructor）。Java 中的深拷贝可以考虑通过序列化后再反序列化实现。
- 对象在拷贝后往往需要经过一个初始化之后才能提供调用方使用，因为拷贝后的对象中成员变量的值还是和原型对象一致的。

## 思考

- Java 中 `Object.clone()` 实现的是深拷贝（deep copy）还是浅拷贝（shadow copy）？Java 中如何实现深拷贝（deep copy）？
- Java 中如果类实现了 `Clonable` 接口，应该如何重写（override）`clone` 方法？