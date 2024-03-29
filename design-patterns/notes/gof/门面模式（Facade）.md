# 门面模式（Facade）

## 意图

> Provide a **unified** interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.
>
> 为子系统的一系列接口提供一个统一的访问接口。门面模式定义了一种高等级接口来简化子系统的使用难度。

一个复杂系统往往由很多子系统组成，系统的设计目标之一就是要降低子系统之间的耦合度。一个子系统往往会对外暴露需要暴露的公共接口供外部子系统调用，一个降低系统耦合度的方法是，提供一个**统一**的门面对象（facade object）供外部子系统调用，这样做为什么可以降低子系统间的耦合度呢？试想，如果子系统的接口有变化，那么直接依赖这些接口的子系统也同样需要修改代码以适应这些变化，而只要门面的接口不变，依赖门面对象的子系统就不需要做任何修改了。门面相当于提供了一个统一的入口，由门面对象统一帮助外部子系统访问子系统的内部，门面模式的结构图比较清晰地展示了这一点。

面面相当于提供了一个系统对外的通用化接口，当然，如果这些通用化接口不能满足调用方需求时，调用方同样可以越过门面，直接使用系统内部的接口实现更加复杂和定制化的功能。

下面是一个运用门面模式的例子，一个编译子系统允许外部子系统使用其编译功能，这个编译子系统包括了 `Scanner`、`Parser`、`CodeGenerator` 等类实现编译器的词法分析、语法分析、字节码生成等功能。外部子系统可以直接使用这些公共的接口实现定制化的编译功能，也可以依赖一个统一的 `Compiler` 门面接口使用通用化的编译功能。`Compiler` 对外隐藏了 `Scanner`、`Parser`、`CodeGenerator` 等类的交互细节，只是提供一些通用化的编译过程，简化了外部子系统使用的难度（make life easier）。

## 适用场景

- 为复杂系统提供一个简单接口。
- 使用门面系统降低子系统间的耦合度，定义一套稳定的门面接口，系统功能变更后，只需要相应修改门面接口对内部的依赖，而不需要外部子系统修改任何代码。
- 为分层系统的每一层设置一个门面作为这一层的入口，上层系统只通过门面与下层系统交互。

## 结构图

![门面模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191120152955.png)

## 参与角色

- Facade（Compiler）
  - 知道每一个调用方请求是如何通过系统内部组件实现的
  - 作为调用方请求的代理
- subsystem classes（Scanner, Parser, CodeGenerator, ...）
  - 实现子系统功能
  - 完成 Facade 分配的工作
  - 完全不知道 Facade 的存在，内部类完全不需要区分是门面还是调用方直接调用

## 好处

- 使得调用变得更简单（make life easier）。
- 降低系统的耦合度。
- 门面并不禁止调用方直接访问系统内部，使得调用方可以在易用和定制化之间做选择。