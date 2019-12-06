# 访问者模式（Visitor）

## 意图

> Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements.
>
> 表示一个可以被执行在一个对象结构的每一个元素上的操作。访问者模式使你可以定义一个新的操作而不用修改这些元素的类。

也就是说应用在一个复杂对象上的算法本身不由这个对象实现，而是由一个访问者实现，这个复杂对象仅提供可以访问它的每一个组成部分的接口，将访问者对象依次在它的组成部分间传递，访问者维护一个算法的上下文，在访问这个对象的组成部分的过程中完成计算。

## 适用场景

- 如果一个对象由很多不同类的对象组成，且你想实现一个执行在这些对象上的算法时。由于这些对象的接口各不相同，你可以使用一个访问者对象分别造访这些对象，在造访的过程中中获取必须的信息以完成算法。
- 如果一个对象由很多不同类的对象组成，且有不同的算法需要应用在这些对象上，你不想要将这些算法的逻辑全部实现在这个对象的类里，而是实现不同的访问者类处理这些算法。
- 如果一个类的结构相对稳定，不经常修改，但是却需要经常增加应用在这个类上的算法。修改类结构意味着定义在这个类基础上的访问者接口也可能需要修改，修改的代价可能非常大，因此如果一个类的结构经常改变，那么就不适合使用访问者模式。

## 结构图

![访问者模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191206224330.png)


## 参与角色

- Visitor
  - 为 ObjectStructure 里的每一个 ConcreteElement 定义一个 `visit` 方法，一般方法定义为 `visitConcreteElementA(ConcreteElementA element)`，参数类型是要访问的元素的**具体类**，这样访问者就可以调用定义在特定元素类上的特定方法了。
- ConcreteVisitor
  - 实现 Visitor 接口定义的每一个方法。每一个方法都是访问者算法的一部分，ConcreteVisitor 需要维护算法的上下文，储存临时状态，这个状态往往在遍历对象结构时逐渐累加。
- Element
  - 定义一个 `accept` 方法，接收一个访问者。
- ConcreteElement
  - 实现 `accept` 方法
- ObjectStructure
  - 可以枚举它的组成部分
  - 可能提供一个方法供访问者访问它的组成部分
  - 可能是一个组合（composite）或一个集合

## 结果

- 访问者模式使新增算法比较容易。
- 访问者模式使新增 ConcreteElement 比较困难。新增 ConcreteElement 意味着需要修改所有已经存在的访问者类。因此，是否使用访问者模式的一个重要考量是，是对象的结构经常需要改变（不适合使用访问者模式），还是经常需要新增应用在对象上的算法（适合使用访问者模式）。
- 打破了封装。访问者模式迫使 ConcreteElement 提供各种公共方法供其使用，访问者需要知道 ConcreteElement 的内部细节。

## 实现

访问者接口与对象的结构是强绑定的，对于对象结构里每一个需要访问的组成部分，访问者接口都要定义一个相应的 `visit` 方法。每一个可被访问的 ConcreteElement 都要实现 `accept` 接口，在 `accept` 中调用相应的 `visit` 方法，并将自身作为参数，访问者在每一个 `visit` 方法里就获取到了对应的 ConcreteElement，通过调用相应的方法，最终完成算法。`Visitor`、 `Element` 接口以及一个 ConcreteElement 类的示例如下：

```java
interface Element {
    void accept(Visitor visitor);
}
```

```java
interface Visitor {
    void visitElementA(ElementA elementA);
    void visitElementB(ElementB elementB);
    // and so on for other concrete elements
}
```

```java
class ConcreteElementA implements Element {
    void accept(Visitor visitor) {
        visitor.visitElementA(this);
    }
}
```

## 与其他设计模式的联系

- 访问者模式可以用来实现对组合模式（Composite）的树形结构的操作。
- 访问者模式可以被用来实现解释器（Interpreter）。