# 解释器模式（Interpreter）

## 意图

> Given a language, define a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.
>
> 给定一门语言，定义这门语言的语法表示，以及一个使用定义的语法表示来解释这门语言中的句子的解释器。

解释器模式为简单语言提供了一种定义语法、表示语句、解析语句的方法。假设一个正则表达式的语法如下：

```
expression  ::= literal | alternation | sequence | repetiton | ( expression )
alternation ::= expression | expression
sequence    ::= expression & expression
repetition  ::= expression *
literal     ::= a | b | c | ... { a | b | c | ... } *
```

对于每一条语法规则，解释器模式都要用一个类来表示，例如上面的语法规则，需要 5 个类：`RegularExpresson` 以及它的 4 个子类 `LiteralExpression`、`AlternationExpresson`、`SequenceExpression`、`RepetitonExpression`，其中最后三个类定义了拥有子表达式的表达式。这个语言中的每一个表达式（正则表达式）都可以表示为一个由上面 4 个子类组成的语法树。每一个类都需要实现了一个解析方法，用于解析它所表示的语法规则。

给定任意的正则表达式的语法树，再给定任意字符串，为了检测该字符串是否符合正则表达式定义的模式，我们可以遍历语法树的每一个节点，调用节点的解析函数检查模式是否匹配。

- `LiteralExpression` 检查输入是否匹配它的字面量。
- `AlternationExpresson` 检查输入是否匹配它的任意一个子表达式。
- `SequenceExpression` 检查输入是否是它的子表达式的连接。
- `RepetitonExpression` 检查输入是否是它的子表达式的多次重复。

需要注意的是，解释器模式并没有指明如何生成语法树，语法树可能由某种 parser 生成，或者直接给定。

## 适用场景

当需要解释一门语言，并且这门语言的语句可以被表示为抽象的语法树的时候，使用解释器模式。解释器模式更适合于处理下面的场景：

- 语法简单的语言。解释器模式需要语句可以被表示为语法树，且对于每一条语法都会抽象出一个类，对于复杂的语法，可能根本无法生成语法树，或者需要抽象的类太多，这种情况下可能不适合使用解释器模式。
- 不考虑解析的效率。高效的解释器往往不直接解释语句，例如正则表达式往往先被“翻译”为状态机，但即使这样，“翻译”器依然可以用解释器模式来实现。

## 结构图

![解释器模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191207193957.png)

## 参与角色

- AbstractExpression
  - 语法树节点的抽象，定义了一个 `interpret(Context context)` 方法
- TerminalExpression
  - 语法树中的叶节点，语法中的基础组成部分，例如正则表达式中的字面量（`a`、`b`、`c`、`abc` 等）
- NonterminalExpression
  - 语法树中的非叶节点，语法中的表达式、语句等，由多个 TerminalExpression 和 NonterminalExpression 组合而成，例如 `R = R1R2...Rn`（`a*bc|a*cd`）
  - 维护 `R1` 到 `Rn` 的引用
  - `interpret(Context context)` 的实现通常是递归地调用 `R1` 到 `Rn` 的 `interpret(Context context)` 方法
- Context
  - 语法解析过程中的全局信息
- Client
  - 建立给定语言的语法树，语法树是由 NonterminalExpression 和 TerminalExpression 组合而成的树形结构
  - 调用 `interpret(Context context)` 方法

## 流程

- Client 构建由 NonterminalExpression 和 TerminalExpression 组成的语法树，初始化 Context，并调用 `interpret(Context context)` 方法。
- 每一个 NonterminalExpression 递归调用它的子句的 `interpret(Context context)` 方法，递归的终止在 TerminalExpression 的 `interpret(Context context)` 方法。
- 每一个 `interpret(Context context)` 方法使用 Context 存储或者获得解释器的状态。

## 结果

- 改变和扩展语法相对容易。解释器模式用类（TerminalExpression 和 NonterminalExpression）抽象语法规则，可以使用继承来扩展和改变语法。
- 实现语法也很简单，语法被拆分为细粒度的 TerminalExpression 和 NonterminalExpression，它们的编写都相对容易，甚至可以使用编译器或者 parser generator 自动生成。
- 比较难维护复杂语法。解释器模式为每一条语法规则都至少定义一个类，如果语法很复杂，那么使用解释器模式来实现将会使代码变得难于维护。
- 配合访问者模式（Visitor）可以比较容易地实现对语法的不同解释。

## 实现

- 解释器模式并没有指明如何生成语法树。语法树可能由某种 parser 生成，或者直接由客户端给定。
- `interpret(Context context)` 方法可以没有具体实现，而只是接受一个访问者（Visitor）的访问，将解析算法实现在访问者中。
- 可以使用享元模式（Flyweight）共享 TerminalExpression 对象，例如对于编程语言的编译器来说，出现在多个地方的局部变量其实是一个变量。

## 与其他模式的关系

- 语法树是组合模式（Composite）的一个实例。
- 可以使用享元模式（Flyweight）共享语法树中重复的对象。
- 使用迭代器模式（Iterator）遍历语法树。
- 可以使用访问者模式（Visitor）来实现不同的解析算法。