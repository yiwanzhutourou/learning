# 迭代器模式（Iterator）

## 意图

> Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.
>
> 提供一个顺序获取聚合对象的元素的方法而不暴露它的底层实现。

例如，Java 中所有实现了 `java.util.Collection` 接口的集合类都实现了 `Iterator<E> iterator()` 方法返回一个迭代器，`Iterator` 接口提供了一种不暴露内部实现的、**统一的**顺序访问集合中元素的方法。不管是 `LinkedList`、 `ArrayList` 还是 `HashSet`，由于提供了统一的迭代器接口，业务代码基于的是抽象的 `Iterator` 接口而不是具体的实现，因此对它们的遍历方式是统一的。

`Iterator` （Java 11）接口定义如下：

```java
public interface Iterator<E> {
    // 是否还有下一个元素
    boolean hasNext();
    // 获取下一个元素
    E next();
    // 删除当前元素
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }
    // 以 lamda 的方式遍历
    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}
```

为什么要提供一个单独的迭代器接口而不是直接将迭代器的接口定义在 `java.util.Collection` 里？将迭代的逻辑从集合的逻辑中剥离，使得集合专注于集合元素的管理（单一职责）。

## 适用场景

- 获取集合的元素而不暴露集合的内部实现。
- 支持对集合不同的遍历方式。
- 为不同的集合实现提供统一的遍历方式。

## 结构图

![迭代器模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191205170338.png)

## 参与角色

- Iterator
  - 定义获取和遍历元素的接口
- ConcreteIterator
  - 实现 Iterator 接口
  - 记录遍历的当前位置
- Aggregate
  - 定义创建 Iterator 对象的接口
- ConcreteAggregate
  - 实现创建 Iterator 的接口，返回一个合适的 ConcreteIterator

## 结果

- 可以对同一个集合支持不同的遍历算法。
- 简化集合接口。
- 迭代器的状态是独立的，因此可以同时遍历同一个集合。

## 与其他模式的比较

- 集合类返回迭代器的方法是一个工厂方法。
- 组合模式中经常使用迭代器递归遍历树形结构。

## 思考

Java 集合类的 fast fail 是什么？单线程下如何优雅地在遍历集合的同时新增或者删除元素？