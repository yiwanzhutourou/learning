# 策略模式（Strategy）

## 意图

> Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.
>
> 封装一系列算法，使得它们可以替代彼此。策略模式使得算法的变化可以独立于使用它们的客户端。

假设 `Composition` 类是一个文本应用中负责文本渲染的类，文本渲染需要分行算法，而将文本分行有很多不同的算法，我们可以用继承的方式来扩展 `Compostion` 类实现不同的算法，但是使用继承的缺点是很明显的：

- 将分行算法耦合到 `Composition` 类及子类中会使得类变得臃肿，不易于维护。
- `Composition` 类可能还有其他行为，它们和不同的分行算法组合在一起可能会需要编写很多子类，这使得增加新的分行算法也会变得不那么容易。
- 采用继承的方式使得分行的行为静态化了，要在运行时动态替换分行算法可能需要编写很多 `if` `else`。

策略模式在扩展功能上可以作为继承的一种更灵活的替代方案，运用策略模式，将支持分行算法的行为抽象为一个 `Compositor` 接口，并且实现各种不同的分行算法。`Composition` 类仅依赖 `Compositor` 抽象接口，而不依赖具体的实现类，这样就将分行的逻辑完全从 `Composition` 中剥离开来，使得 `Composition` 类更易于维护。使用策略模式后，还可以在运行时动态决定使用什么分行算法，增加了灵活度。新增和修改已有算法也都变得非常容易。

## 适用场景

- 很多相关的类只在行为上有区别，策略模式提供了一个用多种行为配置一个类的方式。
- 在应用中提供了很多在时间、空间复杂度上不同的算法，需要在不同的场景下使用特定的算法。
- 使用策略模式可以避免将一些仅与算法相关的数据结构暴露给调用方。
- 一个定义了很多行为的类可以使用策略模式来避免写很多条件判断。

## 结构图

![策略模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191201010943.png)

## 参与角色

- Strategy
  - 定义通用的算法接口
- ConcreteStrategy
  - 实现算法接口
- Context
  - 被配置为使用某一个 ConcreteStrategy
  - 持有一个 Strategy 对象的引用
  - 可能会定义由 Strategy 获取必要数据的接口

## 好处

- 继承的一种替代方案，比继承更易于维护、扩展，且更灵活。

- 提供了一系列算法可供选择，调用方可以根据需要（时间换空间还是空间换时间）选择不同的算法。

## 缺点

- 调用方必须清楚具体有哪些算法可供选择，因此最好在调用方必须要明确区分不同的行为时采用策略模式。

## 实现

- 定义 Context 和 Strategy 之间的接口，可以以参数的形式将 Strategy 需要的数据定义在接口里，这样 Context 和 Strategy 是松耦合的，但是接口中定义的参数可能是部分具体的 Strategy 不需要的；另一种方式是将 Context 定义在接口中，由 Strategy 通过 Context 暴露的接口来获取数据，当然这种方式增加了两者的耦合度，实现时可以根据具体情况选择。