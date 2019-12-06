# 桥接模式（Bridge）

## 意图

> Decouple an abstraction from its implementation so that the two can vary independently.
>
> 将抽象从它的实现中解耦出来，使得抽象和实现可以独立变化。

例如一个图形化界面基类 `Window`，在不同的图形化系统（X Window 或者 IBM Presentation Manager）中有不同的实现，如果使用继承，我们会得到 `XWindow` 和 `PMWindow` 两种实现。如果我们需要在两个系统中都提供多种风格的界面，例如 `IconWindow` 和 `TransientWindow`，那么我们将不得不实现四个子类：`XIconWindow`、`PMIconWindow`、`XTransientWindow`、`PMTransientWindow`，如果需要继续支持新的图形化系统和新增界面风格，那么子类的数量将继续增加。如果要基于这套 `Window` 系统做开发，那么你将针对具体的图形化系统做开发，对 X Window 和 IBM Presentation Manager 都要开发一套。使用桥接模式可以解决这个问题，将 `Window` 抽象与具体平台的实现解耦，如此形成两套继承结构：平台无关的不同风格的 `Window` 子类以及平台相关的具体实现。如下如所示：

![桥接模式下的图形化系统](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191206193104.jpeg)

采用桥接模式，具体风格的窗口，例如 `IconWindow` 的实现依赖的是 `Window` 中的抽象方法，而与平台无关。平台相关的实现都封装在 `WindowImp` 的子类中，`Window` 依赖的是抽象的 `WindowImp`。这两个继承结构可以独立变化而互不影响。

## 适用场景

- 当想要避免抽象和具体实现的永久绑定关系的时候，例如想要在运行时改变实现时。
- 当抽象和具体实现都要通过继承扩展的时候。在这种情况下，桥接模式允许将不同的抽象子类和实现子类绑定起来。
- 当需要修改具体实现而不影响到抽象时。
- 当你发现你的系统在扩展时，需要你的继承关系呈网状增长的时候，例如「意图」一节中的情况。

## 结构图

![桥接模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191206190708.png)

## 参与角色

- Abstraction（Window）
  - 定义抽象接口
  - 维护一个到 Implementor 的引用
- RefinedAbstraction（IconWindow, TransientWindow）
  - 继承 Abstraction
- Implementor（WindowImp）
  - 定义具体实现的接口，这部分接口可以和 Abstraction 定义的接口不同，一般情况下，Implementor 往往定义更底层的接口，而 Window 接口的实现需要依赖这些接口
- ConcreteImplementor（XWindowImp, PMWindowImp）
  - 实现 Implementor 接口提供具体实现

## 结果

- 将接口与实现解耦，使得它们可以独立变化。
- 提高了可扩展性。
- 对客户端隐藏实现细节。

## 与其他模式的比较

- 桥接模式（Bridge）与适配器模式（Adapter）很相似，适配器模式将已经存在的接口适配为客户端需要的接口，而桥接模式是一种提前设计，使得抽象与实现可以独立变化。