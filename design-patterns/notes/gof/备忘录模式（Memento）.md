# 备忘录模式（Memento）

## 意图

> Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later.
>
> 在不破坏封装的情况下，获取一个对象的内部状态，使得这个对象在稍后可以被恢复到这个状态。

## 适用场景

- 当一个对象的状态需要被快照下来并在之后的某个时间点被恢复时。
- **并且**当为了不破坏对象的封装，为了不暴露对象内部的实现，而不希望直接暴露返回这些状态的接口时。

## 结构图

![备忘录模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191207001834.png)


## 参与角色

- Memento
  - 存储 Originator 的内部状态，Memento 仅需要存储必要的状态
  - 禁止 Originator 以外的对象获取其属性
- Originator
  - 创建一个 Menento 对象，将其当前状态快照下来
  - 使用 Memento 对象恢复到特定状态
- Caretaker
  - 存储 Memento 对象
  - 不需要操作或检查 Memento 对象

## 流程图

![备忘录模式流程图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191207002956.png)

## 结果

- 维护了 Originator 的封装。
- 简化了 Originator 的实现，如果为了不破坏封装而将状态保存与恢复的机制实现在 Originator 中，那么 Originator 将增加额外的逻辑。
- 使用备忘录模式的开销可能很大，备忘录模式可能不适用于那种 Originator 需要拷贝大量的数据到 Memento 中的情况。
- Careteker 不清楚 Memento 的内部结构，因此可能会产生意想不到的大的存储开销。

## 实现

- 确保 Memento 中状态相关的接口只有 Originator 可以调用。
- Memento 中可以只保存增量状态。