# 工厂方法（Factory Method）

## 意图

> Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.
>
> 定义一个创建对象的接口，但是由子类决定实例化哪一个类。工厂方法可以使一个类将实例化工作交由子类处理。

工厂方法是为了解决这样一种矛盾：父类负责对象的创建，然而父类并不具体知道实际要创建哪个对象，而只知道要创建对象的抽象类。这种情形通常发生在抽象的框架层，例如一个文件显示器的框架层有一个抽象的 `Application` 类和 `Document` 类，在一个图片编辑器的具体实现中 `ImageApplication` 和 `ImageDocument` 继承了这两个抽象类，文件对象的创建逻辑是由基类 `Application` 维护的，但是它仅知道要创建一个 `Document` 对象的子类，但不知道具体是哪一个子类。于是 `Application` 类可以定义一个抽象的工厂方法 `createDocument()`，将实际创建 `Document` 的实现“延后”到子类中，`ImageApplication` 实现了这个方法，返回一个 `ImageDocument` 对象。

## 适用场景

- 一个类无法参与必须由这个类创建的对象的创建过程时。
- 一个类希望由它的子类来决定具体创建哪个对象时。
- 一个类将创建对象的过程代理出去并且希望它的子类决定具体使用哪一个代理对象时。

## 结构图

![工厂方法结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191127203620.png)

## 参与角色

- Product
  - 定义被创建对象的接口
- ConcreteProduct
  - 实现 Product 接口
- Creator
  - 定义工厂方法返回一个 Product 类型的对象，这个方法可以是抽象方法，也可以有一个默认实现
  - 包含调用工厂方法创建对象的逻辑
- ConcreteCreator
  - 重写（override）工厂方法，返回一个 ConcreteProduct 实例

## 好处

- 工厂方法使得你的代码不依赖具体的实现，而只需要依赖抽象，你代码的逻辑将适用于所有具体的实现。
- 为子类提供了一个可以改变父类行为的钩子（hook）。

## 不足

- 调用方必须继承 Creator 类才能指定想要生产的 ConcreteProduct，而调用方可能本不需要继承 Creator 类。

## 与其他模式的对比

- 抽象工厂经常使用工厂方法实现。
- 原型模式同样可以做到对 Creator 隐藏被创建的具体对象，且不需要继承 Creator 类，但是原型模式在克隆一个对象后往往需要一个初始化的过程。