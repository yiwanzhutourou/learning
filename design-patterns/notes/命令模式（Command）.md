# 命令模式（Command）

## 意图

> Encapsulate a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.
>
> 将请求封装为一个对象，以此可以使你用不同的请求、队列或者日志请求参数化客户端，并支持撤销操作。

命令模式（Command）本质上是将一个直接的方法调用（method invocation）封装为一个命令对象（command object），这个对象往往提供一个无参的 `execute()` 方法，且封装了被调用类（receiver）、调用参数等信息，调用类（invoker）不直接调用被调用类的方法，而是执行命令对象的 `execute()` 方法。

例如，在一个图形化界面的库中，一个 `Button` 按钮需要支持点击事件，但是一个 `Button` 可以绑定任意的点击事件，`Button` 类知道自己什么时候被点击了，但并不知道自己被点击之后具体要做什么，这个时候就需要通过一个 `onClick(Action action)` 方法绑定一个点击事件到 `Button` 上，当按钮被点击后执行 `action.onClicked` 触发具体的事件，`Button` 类并不需要具体知道自己被点击后要做什么，而只需要执行绑定在自己身上的事件的回调方法即可。这里的 `Action` 就是一个命令对象。

命令模式的另外一个用途是异步任务，因为命令对象封装了所有的调用信息，因此命令对象完全可以被丢到一个队列里，而不是立即执行，由线程池中的线程从队列中获取后再依次执行。例如 Java 中，`Runnable` 对象就是一个命令对象，调用线程池 `submit(Runnable task)` 方法丢到线程池中执行。

命令对象可以定义 `undo()` 接口支持操作的回滚，例如在支持事务的系统中，可以将每一个操作封装为命令对象，方便对整个事务做回滚。或者在文本编辑的应用中，利用两个队列记录历史操作，可以方便实现操作的回滚和回放。

一个系统还可以通过序列化命令对象到日志的方式支持系统在宕机后的恢复功能。

总之，命令模式通过将一个直接的方法调用封装为一个对象，可以实现调用类和被调用类的解耦（调用类完全不知道自己调用了谁）、异步处理请求、回滚、宕机后的恢复等能力。

## 适用场景

- 回调方法，当调用类不需要或者无法知道被调用类的时候。
- 异步执行，将命令对象加入异步队列、线程池。
- 支持回滚。
- 记录系统增量变化，在系统宕机后重放以恢复系统。
- 支持事务。

## 结构图

![命令模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191204142909.png)

## 参与角色

- Command
  - 定义执行操作的 `execute()` 接口
- ConcreteCommand
  - 定义一个和被调用类（Receiver）以及被调用方法的强绑定关系
  - 实现 `execute()` 接口，执行调用逻辑
- Client
  - 创建一个 ConcreteCommand 对象，并建立它与 Receiver 的绑定
- Invoker
  - 调用 Command 的 `execute()` 方法

- Receiver
  - 真正的被调用方

## 流程图

![命令模式流程图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191204151045.png)

- Client 创建 ConcreteCommand 对象并绑定 Receiver
- 将 ConcreteCommand 对象设置到 Invoker 中存储
- Invoker 在特定实际调用 ConcreteCommand 的 `execute()` 方法
- ConcreteCommand 调用 Receiver 的方法执行具体逻辑

## 结果

- 命令模式解耦了调用方和被调用方，使得调用方可以完全不知道被调用方的存在。
- 可以将多个命令对象组合为一个宏命令（macro command）执行一系列动作。
- 新增命令对象是很容易的，且不需要改动现有的类。

## 实现

- 命令对象可以多智能？一个极端是，命令对象完全不包含逻辑，直接将请求转发到 Receiver 执行，另一个极端是，命令对象直接执行所有逻辑，而根本就没有 Receiver 存在。
- 实现多级回滚和重放的功能需要注意哪些细节？
- 需要考虑如何处理命令对象 `execute()` 方法执行出现的异常。例如，任务丢到线程池中去了，如果执行出现错误，主线程肯定是无法直接知道的，需要根据业务场景考虑是将错误信息记入数据库，还是尝试重试等。