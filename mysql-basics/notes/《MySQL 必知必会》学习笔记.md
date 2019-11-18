# 《MySQL 必知必会》学习笔记

这里总结的是最基本的 MySQL 概念和用法，而不会涉及一些高级主题，例如 `UNION` 一节只会涉及 `UNION` 的用法和注意事项，而不会涉及 `UNION` 语句的执行性能如何；事务一节也只会涉及事务的基本概念，MySQL 事务相关的语法等，而不会涉及事务的隔离级别、锁等主题。

《MySQL 必知必会》出版日期比较早，书里基本针对的是 MySQL 5.1 及之前的版本论述的，有一部分内容已经过时了，但是由于书里涉及的都是 MySQL 基础，因此大部分内容仍然是适用的。另外笔记也参考了 MySQL 5.7 版本的[官方文档](<https://dev.mysql.com/doc/refman/5.7/en/>)，以及一些其他资料，具体见参考资料。

学习 MySQL 基础的最全的资料肯定是[官方文档](<https://dev.mysql.com/doc/refman/5.7/en/>)，《MySQL 必知必会》涉及的只是基础中的基础，肯定不可能覆盖所有内容。剩下的内容其实也不必要完全掌握，掌握了最核心的原理，其他的细节问题在遇到的时候再去查文档应该就可以了。

## 基本概念

回顾一下数据库相关的基本概念，包括数据库（database）、表（table）、模式（schema）、列（column）、数据类型（datatype）、行（row）、主键（primary key）、SQL（Structured Query Language，发音 S-Q-L 或者 sequel）等。

## 什么是 MySQL

MySQL 是一种 DBMS（数据库管理系统），即 MySQL 是一种数据库软件。

- 开源，可以免费使用，甚至可以免费修改
- 性能好，可信赖，是经过大公司检验的
- 安装容易，使用简单

## 基本命令

**SQL 关键字不区分大小写，select == SELECT == Select，以下所有命令都用大写表示关键字 / 命令行指令，小写表示非关键字。**

### `HELP`

```mysql
HELP;
# 查看具体命令的帮助
HELP SELECT;
```

### `USE`

选择数据库

```mysql
USE database;
```

### `SHOW`

显示数据库列表

```mysql
SHOW DATABASES;
```

显示数据库中所有表的列表

```mysql
SHOW TABLES;
```

显示表的列信息

```mysql
SHOW COLUMNS FROM table;
# or use
DESCRIBE table;
```

显示建库、建表语句

```mysql
SHOW CREATE DATABASE database;
SHOW CREATE TABLE table;
```

显示用户权限

```mysql
SHOW GRANTS;
SHOW GRANTS FOR user;
# 显示当前用户的权限
SHOW GRANTS FOR CURRENT_USER;
SHOW GRANTS FOR CURRENT_USER();
```

## 检索数据

### `SELECT`

```mysql
# SELECT 基本用法，SELECT 后指定要检索的列
# FROM 后指定要检索表，FROM 子句并不是必须的，例如 SELECT 1;
SELECT prod_name
FROM products;
# 限定列名
SELECT products.prod_name
FROM products;
# 检索多列
SELECT prod_id, prod_name, prod_price
FROM products;
# 使用 SELECT * 检索表中的每一列
# 除非明确需要表中的每一列数据，否则不要使用 SELECT *
SELECT *
FROM products;
```

### `DISTINCT`

`DISTINCT` 关键字指示 MySQL 只返回不同的值。`DISTINCT` 必须应用在 `SELECT` 指定的所有列上，即只要选定的所有列的组合是不同的就算做不同的值。例如：

```mysql
SELECT DISTINCT vend_id
FROM products;
```

### `LIMIT`

`LIMIT` 子句限制 `SELECT` 返回的结果集的数量，`LIMIT m,n` / `LIMIT n OFFSET m` 指示 MySQL 返回从第 m 行（不包括第 m 行）开始之后的 n 行记录，参数 `m` 省略则表示 `m = 0`。

```mysql
# 第 0 行之后的 5 行数据
SELECT prod_name
FROM products
LIMIT 5;
# 第 3 行之后的 5 行数据
SELECT prod_name
FROM products
LIMIT 3,5;
# 等价于，注意 LIMIT 必须在 OFFSET 前
SELECT prod_name
FROM products
LIMIT 5 OFFSET 3;
```

### `ORDER BY`

```mysql
# 默认升序排列
SELECT prod_name
FROM products
ORDER BY prod_name;
# 等价于
SELECT prod_name
FROM products
ORDER BY prod_name ASC;
# 按非检索的列排序
SELECT prod_name
FROM products
ORDER BY prod_price;
# 按多个列排序，只有 prod_price 值相同时，才按 prod_name 排序
SELECT prod_id, prod_price, prod_name
FROM products
ORDER BY prod_price, prod_name;
# 按 prod_price 降序
SELECT prod_id, prod_price, prod_name
FROM products
ORDER BY prod_price DESC;
# 按 prod_price 降序，prod_name 升序
SELECT prod_id, prod_price, prod_name
FROM products
ORDER BY prod_price DESC, prod_name;
# 如果要按每个列降序排序，需要在每个列上应用 DESC
SELECT prod_id, prod_price, prod_name
FROM products
ORDER BY prod_price DESC, prod_name DESC;
# 找出商品价格的最大值
# LIMIT 子句必须位于 ORDER BY 子句之后，否则报错
SELECT prod_price
FROM products
ORDER BY prod_price DESC
LIMIT 1;
```

### `WHERE`

```mysql
SELECT prod_name, prod_price
FROM products
WHERE prod_price = 2.50;
# 比较字符类型时比较值需要加上引号
SELECT prod_name, prod_price
FROM products
WHERE prod_name = 'Fuses';
# 用 IS NULL 检查空值
SELECT cust_id
FROM customers
WHERE cust_email IS NULL;
```

`WHERE` 子句支持的条件操作符

|       操作符        |               说明               |
| :-----------------: | :------------------------------: |
|          =          |               等于               |
|         <>          |              不等于              |
|         !=          |              不等于              |
|          <          |               小于               |
|         <=          |             小于等于             |
|          >          |               大于               |
|         \>=         |             大于等于             |
| BETWEEN ... AND ... | 在指定的两个值之间，包括指定的值 |

**值为 `NULL` 的列不会出现在不匹配检测的结果集中，因为 MySQL 认为 `NULL` 意味着未知，因此 MySQL 会认为值为 `NULL` 的列不匹配给出的条件。**

```mysql
# 查询 cust_email 不等于 sam@yosemite.com 的结果
# 最终结果集中没有包含 cust_email 为 NULL 的行
SELECT cust_name, cust_email
FROM customers
WHERE cust_email != 'sam@yosemite.com';
```

#### `AND`

```mysql
SELECT prod_id, prod_name, prod_price
FROM products
WHERE vend_id = 1003 AND prod_price <= 10;
```

#### `OR`

```mysql
SELECT prod_name, prod_price, vend_id
FROM products
WHERE vend_id = 1002 OR vend_id = 1003;
```

#### `AND` 和 `OR` 的计算次序

```mysql
# AND 操作符有更高的优先级会被优先计算
SELECT prod_name, prod_price
FROM products
WHERE vend_id = 1002
   OR vend_id = 1003
  AND prod_price >= 10;
# 等价于
SELECT prod_name, prod_price
FROM products
WHERE vend_id = 1002
   OR (vend_id = 1003 AND prod_price >= 10);
```

**任何时候都不要依赖运算符的默认计算次序，而是根据需要添加合适的括号，括号可以帮助消除歧义，避免不必要的错误。**

#### `IN`

```mysql
SELECT prod_name, prod_price, vend_id
FROM products
WHERE vend_id IN (1002, 1003)
ORDER BY prod_name;
```

**`IN` 子句相较于等价的 `OR` 子句执行更快，且语法更清楚直观，传输字符数更少。**

#### `NOT`

```mysql
SELECT prod_name, prod_price, vend_id
FROM products
WHERE vend_id NOT IN (1002, 1003)
ORDER BY prod_name;
```

**MySQL 中允许 `NOT` 对 `IN`、`BWTWEEN` 和 `EXISTS` 子句取反。**

**注：书里 MySQL 的最新版本是 5.1，查了 5.7 的文档，MySQL 是支持类似 `NOT expression` 的写法的，例如如下的例子：**

```mysql
# 一个奇怪的例子
SELECT prod_name, prod_price, vend_id
FROM products
WHERE NOT (vend_id = 1002 OR NOT vend_id = 1003)
  AND NOT prod_price >= 10;
```

根据 5.7 的文档，MySQL 语法也是支持逻辑操作符的，例如，`&&`、`||`、`!` 等，关于 MySQL 表达式的语法，参见[官方文档](<https://dev.mysql.com/doc/refman/5.7/en/expressions.html>)。

#### `LIKE`

```mysql
# % 表示任意字符出现任意次，即 0 次、1 次或多次
# % 可以出现任意次，例如 %Jet%、J%e%t%
# % 也不能匹配 NULL 值
SELECT prod_id, prod_name
FROM products
WHERE prod_name LIKE '% ton anvil';
# 下划线 _ 匹配一个任意字符
SELECT prod_id, prod_name
FROM products
WHERE prod_name LIKE '_ ton anvil';
```

通配符的搜索一般要比前面讨论的其他搜索效率更低，通配符使用注意事项：

- 如果不使用通配符可以达到相同的效果，则不要使用通配符。
- 带有通配符的子句要放到 `WHERE` 子句的最后。

#### `REGEXP`

```mysql
SELECT prod_name
FROM products
WHERE prod_name REGEXP '^[1-5] ton'
ORDER BY prod_name;
```

MySQL 支持的一些简单的正则表达式规则：

- `.` 匹配任意字符，例如 `'.000` 匹配 `'1000'`

- `|` 表示 OR 操作，例如 `1000|2000` 既匹配 `1000` 也匹配 `2000`

- `[]` 定义一组字符，例如 `[abc]` 匹配 `a`、`b` 和 `c`

- `[^]` 定义否定的字符种类，例如 `[^abc]` 匹配除了  `a`、`b` 和 `c` 的字符

- `-` 定义一个范围，例如 `[0-9]`、`[a-z]` 等

- `\\` 用来匹配特殊字符，例如 `\\.` 匹配字符 `.`，反斜杠 `\`本身用`\\\` 匹配。`\\` 也用来表示元字符，例如：

  | 元字符 |   说明   |
  | :----: | :------: |
  | `\\f`  |   换页   |
  | `\\n`  |   换行   |
  | `\\r`  |   回车   |
  | `\\t`  |   制表   |
  | `\\v`  | 纵向制表 |

- 重复元字符

  |       元字符       |                说明                |
  | :----------------: | :--------------------------------: |
  |        `*`         | 0 个或 1 个或多个匹配，等价于 {0,} |
  |        `+`         |    1 个或多个匹配，等价于 {1,}     |
  |        `?`         |    0 个或一个匹配，等价于 {0,1}    |
  | `{n}, {n,}, {n,m}` |           指定数目的匹配           |

- 定位符

  |  元字符   |    说明    |
  | :-------: | :--------: |
  |    `^`    | 文本的开始 |
  |    `$`    | 文本的结尾 |
  | `[[:<:]]` |  词的开始  |
  | `[[:>:]]` |  词的结尾  |

**利用 `SELECT` 语句可以测试通配符和正则表达式的匹配情况，例如下面的两句都返回 `1`。**

```mysql
SELECT 'JetBrain' LIKE 'Jet%';
SELECT '13566668888' REGEXP '^1[0-9]{10}$';
```

### 计算字段

```mysql
# Concat 拼接字符串
# RTrim 去掉右空格
# AS 创建别名
SELECT Concat(RTrim(vend_name), '(', RTrim(vend_country), ')') AS vend_title
FROM vendors
ORDER BY vend_name;
# 利用乘法计算总价
SELECT prod_id,
       quantity,
       item_price,
       quantity * item_price AS expanded_price
FROM orderitems
WHERE order_num = 20005;
```

### 函数

常用函数包括文本处理函数、日期和时间处理函数以及数值处理函数等。

```mysql
# 返回 cust_contact 的发音与 'Y Lie' 相近的
SELECT cust_name, cust_contact
FROM customers
WHERE Soundex(cust_contact) = Soundex('Y Lie');
# Date() 返回时间 / 日期数据类型对应的日期
SELECT cust_id, order_num
FROM orders
WHERE Date(order_date) = '2005-09-01';
```

**MySQL 的日期表示形式为 `'YYYY-mm-dd'`，例如 `'2005-09-01'`。**

### 聚集函数

- `AVG()` 计算平均值，需要指定列名，忽略列值为 `NULL` 的行
- `COUNT()` 统计个数
  - `COUNT(*)` 不忽略列值为 `NULL` 的行
  - `COUNT(column)` 忽略列值为 `NULL` 的行

- `MAX()` 返回最大值，需要指定列名，忽略列值为 `NULL` 的行
- `MIN()` 返回最小值，需要指定列名，忽略列值为 `NULL` 的行

- `SUM()` 用于求和，需要指定列名，忽略列值为 `NULL` 的行

```mysql
# 使用 DISTINCT 统计不同价格的个数
# 不能使用 COUNT(DISTINCT *)
SELECT COUNT(DISTINCT prod_price) AS price_count
FROM products
WHERE vend_id = 1003;
```

**这些聚集函数是高效设计的，一般比在客户端直接计算要快得多。**

### `GROUP BY`

```mysql
# 检索每一个供应商产品的数量
# 这里系统自动识别出 COUNT(*) 是针对每一个分组计算的
SELECT vend_id, COUNT(*) AS num_prods
FROM products
GROUP BY vend_id;
```

- `GROUP BY` 子句可以包含任意数目的列，指定的列全部参与分组计算
- `NULL` 值会被当做相同的值聚集在一起
- `GROUP BY` 子句必须出现在 `WHERE` 子句之后，`ORDER BY` 子句之前
- 使用 `WITH ROLLUP` 关键字可以按照 `GROUP BY` 的维度做进一步汇总，具体参见[官方文档](<https://dev.mysql.com/doc/refman/5.7/en/group-by-modifiers.html>)

### `HAVING`

`HAVING` 支持所有 `WHERE` 操作符，只不过 `HAVING` 子句用来过滤分组（`GROUP BY` 之后的结果），而 `WHERE` 子句用来过滤行。

```mysql
# 检索产品数目大于 2 的供应商
# HAVING 子句也可以使用别名，写为 HAVING num_prods > 2
SELECT vend_id, COUNT(*) AS num_prods
FROM products
GROUP BY vend_id
HAVING COUNT(*) > 2;
```

### `SELECT` 子句顺序

```mysql
SELECT
FROM
WHERE
GROUP BY
HAVING
ORDER BY
LIMIT
```

### 子查询

``` mysql
# 将子查询作为 IN 子句的输入
# MySQL 会自动将子查询的结果用逗号隔开
SELECT cust_id
FROM orders
WHERE order_num IN (SELECT order_num
                    FROM orderitems
                    WHERE prod_id = 'TNT2');
# 查询每个客户的订单数
# 外侧的 SELECT 查询的是 customers 表里的每一个客户
# 可以把内侧的 SELECT 理解为嵌套在 for 循环里了
# 内侧查询实际执行了 N 次，N 为外侧查询到的客户数
# 由于 cust_id 字段有歧义，所以需要使用用表名限定
SELECT cust_id,
       cust_name,
       (SELECT COUNT(cust_id)
        FROM orders
        WHERE orders.cust_id = customers.cust_id) AS orders
FROM customers
ORDER BY cust_name;
```

子查询的语义非常不好理解，写起来也很容易出错，好消息是 `JOIN` 子句往往可以达到相同目的而且效率更高，也就是说根本就不推荐使用子查询。

### 连接

**搜索了网上，似乎 `JOIN` 更多翻译为连接。**

SQL 定义的连接类型如下图所示：

![SQL JOINs](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191113150203.jpg)

`CROSS JOIN`（交叉连接），在上图中没有列出来，交叉连接会对连接的两张表做笛卡尔积，也就是结果集中的行是由第一张表中的每一行与第二张表中的每一行配对而成的，而不管它们逻辑上是否可以配在一起。从上面的连接的类型图中可以看出，SQL 定义的连接都是要指定连接的键的（ON A.Key = B.Key），内连接与外连接的区别主要在是否需要包含没有关联的数据，例如左外连接，不仅包括了两张表的关联数据，还包括了左表的所有与右表无关联的数据，右表对应的列全部用空值（`NULL`）填充。

MySQL 支持的连接类型 / 语法如下：

- `CROSS JOIN` / `JOIN`
- `INNER JOIN` / `JOIN`
- `LEFT OUTER JOIN` / `LEFT JOIN`
- `RIGHT OUTER JOIN` / `RIGHT JOIN`

根据[官方文档](<https://dev.mysql.com/doc/refman/5.7/en/join.html>)，在 MySQL 中，`JOIN`、`CROSS JOIN` 和 `INNER JOIN` 实现的功能是一致的，它们在语法上是等价的。从语义上来说，`CROSS JOIN` 特指无条件的连接（没有指定 `ON` 条件的 `JOIN` 或者没有指定 `WHERE` 连接条件的多表 `SELECT`），`INNER JOIN` 特指有条件的连接（指定了 `ON` 条件的 `JOIN` 或者指定了 `WHERE` 连接条件的多表 `SELECT`）。当然，如果你非要写 `... CROSS JOIN ... ON ...` 这样的语法，也是可以执行的，虽然写着交叉连接，实际上执行的是内连接。

#### `CROSS JOIN`

```mysql
SELECT vend_name, prod_name, prod_price
FROM vendors, products
ORDER BY vend_name, prod_name;
# 等价于
SELECT vend_name, prod_name, prod_price
FROM vendors CROSS JOIN products
ORDER BY vend_name, prod_name;
```

#### `INNER JOIN`

```mysql
SELECT vend_name, prod_name, prod_price
FROM vendors, products
WHERE vendors.vend_id = products.vend_id
ORDER BY vend_name, prod_name;
# 等价于
SELECT vend_name, prod_name, prod_price
FROM vendors INNER JOIN products
ON vendors.vend_id = products.vend_id
ORDER BY vend_name, prod_name;
# 连接多张表
SELECT cust_name, cust_contact
FROM customers, orders, orderitems
WHERE customers.cust_id = orders.cust_id
      AND orders.order_num = orderitems.order_num
      AND prod_id = 'TNT2';
SELECT cust_name, cust_contact
FROM customers JOIN orders JOIN orderitems
ON customers.cust_id = orders.cust_id
   AND orders.order_num = orderitems.order_num
   AND prod_id = 'TNT2';
```

#### `OUTER JOIN`

```mysql
# 左外连接，可以转换为等价的右外连接
SELECT customers.cust_id, orders.order_num
FROM customers LEFT OUTER JOIN orders
ON customers.cust_id = orders.cust_id;
# 右外连接，可以转换为等价的左外连接
SELECT customers.cust_id, orders.order_num
FROM customers RIGHT OUTER JOIN orders
ON customers.cust_id = orders.cust_id;
```

### `UNION`

MySQL 支持将多个 `SELECT` 的结果组合成单个结果集返回。

```mysql
# UNION 同一个表的查询结果
SELECT vend_id, prod_id, prod_price
FROM products
WHERE prod_price <= 5
UNION
SELECT vend_id, prod_id, prod_price
FROM products
WHERE vend_id IN (1001, 1002);
# 可以用多个 WHERE 子句达到相同效果
SELECT vend_id, prod_id, prod_price
FROM products
WHERE prod_price <= 5
OR vend_id IN (1001, 1002);
```

使用 `UNION` 的条件为多条 `SELECT` 语句具有相同数量的列，且对应列的数据类型必须兼容。**在 MySQL 5.7 上试验，最后结果集的列名会使用第一条 `SELECT` 指定的列名，似乎任何数据类型都可以兼容，`DECIMAL` 可以兼容 `CAHR`，`INT` 可以兼容 `DATATIME`。例如下面的查询：**

```mysql
# SELECT 1: 数据类型分别为 DATETIME、INT(11)、INT(11)
# SELECT 2: 数据类型分别为 INT(11)、CHAR(10)、DECIMAL(8, 2)
# SELECT 3: 数据类型分别为 TEXT、INT(11)、CHAR(10)
SELECT order_date, order_num, cust_id
FROM orders
UNION
SELECT vend_id, prod_id, prod_price
FROM products
UNION
SELECT note_text, note_id, prod_id
FROM productnotes;
```

`UNION` 默认行为是在查询结果中自动去除重复的行，如果需要返回所有结果，可以使用 `UNION ALL`，例如：

```mysql
SELECT vend_id, prod_id, prod_price
FROM products
WHERE prod_price <= 5
UNION ALL
SELECT vend_id, prod_id, prod_price
FROM products
WHERE vend_id IN (1001, 1002);
```

使用 `UNION` 组合查询时，不允许使用多个 `ORDER BY` 对子 `SELECT` 语句单独排序，而只能在所有 `SELECT` 之后使用一个 `ORDER BY` 对整个结果集做排序，例如：

```mysql
SELECT vend_id, prod_id, prod_price
FROM products
WHERE prod_price <= 5
UNION
SELECT vend_id, prod_id, prod_price
FROM products
WHERE vend_id IN (1001, 1002)
ORDER BY vend_id, prod_price;
```

### 全文本搜索

为了使用全文本搜索，必须对表中支持全文本搜索的列建立 `FULLTEXT` 索引。对于有大量数据的表，一个好的做法是，先将数据导入没有建立 `FULLTEXT` 索引的表，再建立 `FULLTEXT` 索引，因为如果先建立索引，之后每导入一条数据就会涉及索引数据结构的变动，非常耗时。

全文本搜索的语法如下是 `MATCH(columns) AGAINST('word' [mode])`，其中 `mode` 是可选的参数，有以下几种情况：

- 默认缺省或者显示申明为 `IN NATURAL LANGUAGE`，MySQL 按自然语义检索单词，是否区分大小写由相应列的字符排序（collation）决定。

- 申明为 `WITH QUERY EXPANSION` 查询扩展是对自然语义检索的扩展，在这种情况下，MySQL 实际会执行两次检索，第一用检索语句中指定的词，第二次使用第一次检索结果中的热点词再做检索。例如，检索条件指定“数据库”，但是你可能对“MySQL”、“Oracle”等也感兴趣，使用这种模式可能可以将包含这些词的列作为增强结果检索出来。

- 申明为 `IN BOOLEAN MODE` 布尔模式，MySQL 会按像理解正则表达式一样理解 `MATCH` 中指定的词，支持的操作符如下所示：

  | 操作符 |        说明        |                             例子                             |
  | :----: | :----------------: | :----------------------------------------------------------: |
  |  缺省  |         OR         |             `'apple banana'`，包含两个词中的一个             |
  |  `+`   |  包含，词必须存在  |             `'+apple +juice'`，两个词必须都包含              |
  |  `-`   | 排除，词必须不出现 | `'+apple -macintosh'`，包含 `apple`，但是不包含 `macintosh`  |
  |  `>`   |    增加词的分值    |  `'>apple banana'`，包含两个词中的一个，增加 `apple` 的评分  |
  |  `<`   |    减少词的分值    |  `'<apple banana'`，包含两个词中的一个，降低 `apple` 的评分  |
  |  `()`  |  把词组成子表达式  | `'+apple +(>turnover <strudel)'`，必须包含 `apple`，可以包含 `turnover` 或者 `strudel`，`turnover` 的评分比 `strudel` 高 |
  |  `~`   |  将词的分值变为负  | `'+apple ~macintosh'`，必须包含 `apple`，可以包含 `macintosh`，但是会减分 |
  |  `*`   |     词尾通配符     |       `'apple*'`，包含 `apple` 开头的词，例如 `apples`       |
  |  `""`  |    定义一个词组    |           `'"some words"'`，包含词组 `some words`            |

  需要说明的是，在 MyISAM 引擎上，使用这种模式的搜索语句可以不要求相应字段建立 `FULLTEXT` 索引，但是相应的，未建索引也意味着查询效率很低。

`MATCH()` 函数本身是一个评分函数，评分的标准有很多，例如词在相应行出现的先后、次数等。全文本搜索默认情况下其实就是返回了评分大于 0 的行。如果不指定结果的排序规则，全文本搜索的结果默认情况下会按照得分高低降序排列。

全文本索引的例子如下：

```mysql
# 自然语义检索
SELECT note_text
FROM productnotes
WHERE MATCH(note_text) AGAINST('rabbit');
# 检索每一行的评分
SELECT note_text,
       MATCH(note_text) AGAINST('rabbit') AS score
FROM productnotes;
# 扩展搜索
SELECT note_text
FROM productnotes
WHERE MATCH(note_text) AGAINST('rabbit' WITH QUERY EXPANSION);
# 布尔模式
SELECT note_text
FROM productnotes
WHERE MATCH(note_text) AGAINST('>rabbit -complaint' IN BOOLEAN MODE);
```

书中提到的以下几点目前已经**过时**了（《MySQL 必知必会》基于 5.1 及之前的版本）：

> 并非所有存储引擎都支持全文本搜索，常用的存储引擎中，MyISAM 支持全文本搜索，而 InnoDB 不支持。

根据[官方文档](<https://dev.mysql.com/doc/refman/5.7/en/fulltext-search.html>)，目前支持全文本搜索的存储引擎有两个：MyISAM 和 InnoDB。`FULLTEXT` 索引只能建立在数据类型为 `CHAR`、`VARCHAR`、`TEXT` 的列上。InnoDB 存储引擎下的 `FULLTEXT` 索引参见 [InnoDB `FULLTEXT` Indexes](<https://dev.mysql.com/doc/refman/5.7/en/innodb-fulltext-index.html>)。

> 不具有词分隔符（包括日语和汉语）的语言不能恰当地返回全文本搜索结果。

根据[官方文档](<https://dev.mysql.com/doc/refman/5.7/en/fulltext-search.html>)，从 MySQL 5.7.6 版本开始，MySQL 内置了一个 ngram 分词器，支持中文、日语和汉语的分词，具体参见 [ngram Full-Text Parser](<https://dev.mysql.com/doc/refman/5.7/en/fulltext-search-ngram.html>)。

**这里只讨论了全文本搜索的用法，至于 `FULLTEXT` 索引的优化、不同存储引擎的实现逻辑以及更详细的信息等请参见[Full-Text Search Functions](<https://dev.mysql.com/doc/refman/5.7/en/fulltext-search.html>)或其他资料。**

## 插入数据

`INSERT` 的基本用法如下：

```mysql
INSERT INTO customers
VALUES(
    NULL,
    'Pep E. LaPew',
    '100 Main Street',
    'Los Angeles',
    'CA',
    '90046',
    'USA',
    NULL,
    NULL
);
```

上面的语句依赖表字段的定义顺序，一旦表的结构改变，上面的语句可能就会出错，因此并不是推荐的写法。更安全（也更繁琐）的写法如下：

```mysql
INSERT INTO customers(
    cust_name,
    cust_address,
    cust_city,
    cust_state,
    cust_zip,
    cust_country,
    cust_contact,
    cust_email
)
VALUES(
    'Pep E. LaPew',
    '100 Main Street',
    'Los Angeles',
    'CA',
    '90046',
    'USA',
    NULL,
    NULL
);
```

`INSERT` 语句可以省略表中的部分列，前提是这些列的值可以为 `NULL` 或者定义了默认值。`INSERT` 语句也支持一次插入多条数据，可以在 `VALUES` 后用逗号分隔多个值。如下：

```mysql
INSERT INTO customers(
    cust_name,
    cust_address,
    cust_city,
    cust_state,
    cust_zip,
    cust_country
)
VALUES(
    'Pep E. LaPew',
    '100 Main Street',
    'Los Angeles',
    'CA',
    '90046',
    'USA'
), (
    'M. Martian',
    '42 Galaxy Way',
    'New York',
    'NY',
    '11213',
    'USA'
);
```

## 更新数据

更新数据的语法为 `UPDATE table SET updates WHERE conditions`，`UPDATE` 后指定要更新的表，`SET` 是指定修改的列名和它们的新值，`WHERE` 子句指定过滤要更新的行的条件。

```mysql
UPDATE customers
SET cust_name = 'The Fudds',
    cust_email = 'elmer@fudd.com'
WHERE cust_id = 10005;
```

`UPDATE` 更新多行时如果出现错误会回滚，如果希望出现错误时仍继续更新，则可以使用 `UPDATE IGNORE customers...`。

## 删除数据

删除数据的语法为 `DELETE FROM table WHERE conditions`，`FROM` 后指定要从中删除数据的表，`WHERE` 子句指定要被删除的行的条件。

```mysql
DELETE FROM customers
WHERE cust_id = 10006;
```

不指定 `WHERE` 子句将会删除表中的所有数据，删除表中的所有数据还有一个更快的方法，即 `TRUNCATE TABLE`，`TRUNCATE TABLE` 实际做的事情是删除表并创建一个新表，因此速度比一行一行地删除要快。

## 创建和操作表

### `CREATE TABLE`

完整的 `CREATE TABLE` 语法参见[5.7 版本官方文档](<https://dev.mysql.com/doc/refman/5.7/en/create-table.html>)。

```mysql
CREATE TABLE IF NOT EXISTS customers
(
  cust_id      int       NOT NULL AUTO_INCREMENT,
  cust_name    char(50)  NOT NULL DEFAULT '',
  cust_address char(50)  NULL ,
  cust_city    char(50)  NULL ,
  cust_state   char(5)   NULL ,
  cust_zip     char(10)  NULL ,
  cust_country char(50)  NULL ,
  cust_contact char(50)  NULL ,
  cust_email   char(255) NULL ,
  PRIMARY KEY (cust_id)
) ENGINE=InnoDB;
```

- `IF NOT EXIST` 可选参数，表示只有在表不存在（表名相同就认为存在）时才创建。
- `NOT NULL | NULL` 设置列值是否可空。
- `DEFAULT` 设置列的默认值，一般情况下最好不要创建值可空的列，而是创建默认值为 `''` 或者 `0` 的列。MySQL 仅支持设置常量为列的默认值。
- `AUTO_INCREMENT` 设置列值自增，一张表只能包含一列自增列，且必须为该列设置索引。
- `PRIMARY KEY` 指定主键，可以指定多个列作为组合主键，在括号中用逗号隔开相应的列名即可。
- `ENGINE` 指定表的存储引擎，只有在你非常清楚自己要做什么的时候才指定 InnoDB 以外的值，目前高版本的 MySQL 都把 InnoDB 作为 `ENGINE` 的默认值了，因此可以不用显示地指定。

### `ALTER TABLE`

完整的 `ALTER TABLE` 的语法参见[5.7 版本官方文档](<https://dev.mysql.com/doc/refman/5.7/en/alter-table.html>)。

`ALTER TABLE` 常用来增加索引、外键等，例如增加一个外键的例子如下：

```mysql
ALTER TABLE orders
ADD CONSTRAINT fk_orders_customers
    FOREIGN KEY (cust_id) REFERENCES customers (cust_id);
```

### `DROP TABLE`

删除一张表的语法为 `DROP TABLE [IF EXISTS] table`，例如：

```mysql
DROP TABLE IF EXISTS customers2;
```

### `RENAME TABLE`

重命名表的语法为 `RENAME TABLE old_name TO new_name`，例如：

```mysql
RENAME TABLE customers2 TO customers;
```

重命名多张表可以将 `old_name TO new_name` 子句用逗号隔开。

## 视图

视图（View）可以被理解为一个虚拟的表，是一种 MySQL 提供的 `SELECT` 语句层次的封装，我们可以把复杂的 `SELECT` 语句抽象为一个视图，简化数据检索，或者方便重用，或者隔离原始数据。

视图仅仅是用来查看存储在别处的数据的一种手段，其本身是不包含数据的，从视图中检索出来的数据最终还是从表中检索出来的。

例如下面的例子创建了一个可以检索客户的订单数据的视图，这个视图相当于 `customers`、`orders` 和 `orderitems` 内连接后产生的虚拟表，但是实际上数据并没有存储在这张虚拟表中：

```mysql
CREATE VIEW productcustomers AS
SELECT cust_name, cust_contact, prod_id
FROM customers, orders, orderitems
WHERE customers.cust_id = orders.cust_id
AND orderitems.order_num = orders.order_num;
```

创建了这个视图之后，我们就可以用以下语句查询订购了 TNT2 商品的客户了：

```mysql
SELECT cust_name, cust_contact
FROM productcustomers
WHERE prod_id = 'TNT2';
```

视图相关的命令如下：

- `CREATE [OR REPLACE] VIEW` 创建或者更新视图
- `SHOW CREATE VIEW viewname` 查看创建视图语句
- `DROP VIEW viewname` 删除视图

## 存储过程

存储过程（Procedure）是一条或多条语句的集合，可以将存储过程理解为存储在 MySQL 服务器的可以完成特定功能的“函数”，它可以为不同的客户端封装一致的数据库操作逻辑，供其在需要的时候调用。存储过程可能的好处有：

- 封装复杂逻辑提供简易接口
- 保证数据一致性，不同客户端处理相同逻辑可能有出错的可能，而存储过程退这段逻辑做了统一的封装
- 如果表结构变动，只需要相应修改存储过程，就可以做到对客户端透明的修改
- 可以提高性能，存储过程一般执行效率比在客户端写业务逻辑的效率要高

一个简单的例子如下，首先创建一个存储过程，接受一个订单号 `onumber` 参数，返回订单的总价 `ototal`，如下：

```mysql
CREATE PROCEDURE ordertotal(
    IN onumber INT,
    OUT ototal DECIMAL(8,2)
)
BEGIN
    SELECT Sum(item_price * quantity)
    FROM orderitems
    WHERE order_num = onumber
    INTO ototal;
END;
```

**注：如果在命令行中执行上面的语句会因为存储过程中的分号 `;` 导致执行错误，可以在执行前后加上 `DELIMITER //` 和 `DELIMITER ;` 临时将语句分隔符修改为 `//`，并将语句最后的分号 `;` 替换为 `//` 来解决这个问题。**

调用存储过程的代码如下：

```mysql
CALL ordertotal(20005, @total);
SELECT @total;
```

删除存储过程的语句如下：

```mysql
DROP PROCEDURE ordertotal;
```

对上面例子的解释：

- 存储过程支持定义参数，`IN` 表示入参，`OUT` 表示出参，`INOUT` 表示出参、入参皆可。

- 存储过程的实现定义在 `BEGIN` 与 `END` 之间，就像写代码一样，每一个语句都已分号 `;` 结尾，支持的语法参见[Compound Statement](<https://dev.mysql.com/doc/refman/5.7/en/sql-compound-statements.html>)。
- 调用存储过程需要用 `@total` 这样的形式指定入参，存储过程使用 `CALL` 关键字调用，调用结果并不会直接返回，而是需要用 `SELECT` 查询。
- 上面的例子只是为了演示什么是存储过程，这个例子非常简单，其实根本没有必要使用存储过程，一条简单的 `SELECT` 可以做到一样的事情。

说白了，存储过程中可以编写复杂的业务逻辑，相当于是把统一的业务逻辑抽象到数据库端了。至于如何在存储过程中写 `IF`、`ELSE`、`LOOP` 等，可以参考官方文档，例如 [`LOOP` 的例子](<https://dev.mysql.com/doc/refman/5.7/en/loop.html>)。另外，这些语法不只是可以用在存储过程，还可以用于定义触发器等。像存储过程、触发器这些需要存储在 MySQL 服务器的对象，在 MySQL 中一律可以称为[“存储对象”](<https://dev.mysql.com/doc/refman/5.7/en/stored-objects.html>)。

其他存储过程的命令如下：

- `SHOW CREATE PROCEDURE precedure_name` 显示一个存储过程的创建语句。
- `SHOW PROCEDURE STATUS` 显示存储过程的详细信息。
- `DROP PROCEDURE procedure_name` 删除存储过程。

## 游标

游标是在存储过程或者函数中用于逐行获取 `SELECT` 数据的方法。申明一个游标并打开后，在存储过程或者函数中就可以逐行获取检索出的数据，继而编写后续业务逻辑。在不使用游标后需要显示地关闭游标以释放服务器资源。如果没有显示关闭游标，在存储过程执行到 `END` 语句时，系统会自动关闭未关闭的游标。

一个使用游标的存储过程的例子如下，这个例子中的存储过程计算订单表中每一条订单数据的总价，并将结果保存到另一张表中：

```mysql
CREATE PROCEDURE processorders()
BEGIN
    -- 申明临时变量
    DECLARE done BOOLEAN DEFAULT 0;
    DECLARE o INT;
    DECLARE t DECIMAL(8, 2);
    
    -- 申明一个游标，申明后 MySQL 并不会实际执行 SELECT 语句
    DECLARE ordernumbers CURSOR
    FOR
    SELECT order_num FROM orders ORDER BY order_num;
    
    -- 申明一个 CONTINUE HANDLER 处理循环终止事件
    -- CONTINUE 意味着遇到错误继续执行
    -- SQLSTATE 02000 表示无数据，也就是下面循环里 FETCH 无数据了
    DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
    
    -- 创建一张表存储计算的结果
    CREATE TABLE IF NOT EXISTS ordertotals
        (order_num INT, total DECIMAL(8, 2));
    
    -- 打开游标，这个时候才会实际执行 SELECT 语句
    OPEN ordernumbers;
    
    -- 循环，逐行处理游标中的数据
    -- 定义一个 label，方便 LEAVE
    calctotal: LOOP
        -- FETCH 从游标中获取一行的数据，并将游标指针移到下一行
        FETCH ordernumbers INTO o;
        -- 前面定义的 CONTINUE HANDLER 会在 REPEAT 无数据可读后
        -- 将 done 置为 1，done 为 1 后循环就结束了
        IF done THEN
            LEAVE calctotal;
        END IF;
        -- 调用另一个存储过程，计算订单总价
        -- 结果存储在之前定义的临时变量 t 里
        CALL ordertotal(o, t);
        -- 可以使用 SELECT 语句打印 log 信息
        SELECT Concat("order ", o, " total price is ", t) AS message;
        -- 将计算结果插入之前创建的表
        INSERT INTO ordertotals(order_num, total)
        VALUES(o, t);
    END LOOP calctotal;
    -- 关闭游标
    CLOSE ordernumbers;
END;
```

## 触发器

触发器是 MySQL 响应 `DELETE`、`INSERT`、`UPDATE` 语句而自动执行的 MySQL 语句。除了这三种语句，其他语句不支持触发器。

可以申明前置或者后置触发器。如果前置触发器执行失败，MySQL 将不继续执行相应的语句；如果语句执行失败，MySQL 将不执行后置触发器。

使用 `CREATE TRIGGER` 语法创建触发器，使用 `DROP TRIGGER trigger_name` 删除触发器。

### `INSERT` 触发器

在 `INSERT` 触发器代码内，可以引用一个名为 `NEW` 的虚拟表，访问被插入的行。在前置触发器内，可以通过 `NEW` 修改插入的数据。对于 `AUTO_INCREMENT` 列，`NEW` 在 `INSERT` 执行之前该列的值为0，执行之后为自动生成的值。

```mysql
# 后置 INSERT 触发器，在每一行插入后展示自增的订单号
CREATE TRIGGER neworder AFTER INSERT ON orders
FOR EACH ROW SELECT NEW.order_num;
```

上面的例子是书里的，实际在 MySQL 5.7 版本执行结果如下：

```shell
mysql> CREATE TRIGGER neworder AFTER INSERT ON orders
    -> FOR EACH ROW SELECT NEW.order_num;
ERROR 1415 (0A000): Not allowed to return a result set from a trigger
```

MySQL 的  `DELETE`、`INSERT`、`UPDATE` 语句都不返回修改的数据，如果在触发器里返回相应的结果就违背这个设计了，因此 MySQL 在后续的版本中禁止在触发器里执行 `SELECT` 语句。

### `DELETE` 触发器

在 `DELETE` 触发器代码内，可以引用一个名为 `OLD` 的虚拟表，访问被删除的行。`OLD` 中的值是只读的，不能更新。

一个将被删除数据存档的例子如下，需要说明的是这是一个前置触发器，如果存档时发生错误，`DELETE` 语句也不会被执行，可以确保删除的数据一定被正确存档：

```mysql
CREATE TRIGGER deleteorder BEFORE DELETE ON orders
FOR EACH ROW
BEGIN
    INSERT INTO archive_orders(order_num, order_date, cust_id)
    VALUES(OLD.order_num, OLD.order_date, OLD.cust_id);
END;
```

### `UPDATE` 触发器

在 `UPDATE` 触发器代码内，可以引用一个名为 `OLD` 的虚拟表访问修改前的数据，和一个名为 `NEW` 的虚拟表访问修改后的数据。在前置触发器内，可以通过修改 `NEW` 的值来修改将要用于 `UPDATE` 语句的值，而 `OLD` 中的值是只读的。

一个确保更新数据中州名必须大写的例子如下：

```mysql
CREATE TRIGGER updatevendor BEFORE UPDATE ON vendors
FOR EACH ROW SET NEW.vend_state = Upper(NEW.vend_state);
```

## 事务

事务处理（transaction processing）可以用来维护数据库的完整性，它保证成批的 MySQL 操作要么完全执行，要么完全不执行。

执行事务的例子如下：

```mysql
# 开启一个事务
START TRANSACTION;
SELECT * FROM orderitems WHERE order_num = 20005;
DELETE FROM orderitems WHERE order_num = 20005;
# 一个事务对数据库的修改对本事务是可见的
# 下面的 SELECT 语句返回 0 行数据
SELECT * FROM orderitems WHERE order_num = 20005;
DELETE FROM orders WHERE order_num = 20005;
# 提交事务，事务结束
COMMIT;
```

上面的例子只有在两条 `DELETE` 语句都成功执行之后才会 `COMMIT`，任意一条语句执行失败都会触发自动回滚。也可以显示地调用回滚，如下：

```mysql
# 开启一个事务
START TRANSACTION;
SELECT * FROM orders WHERE order_num = 20005;
DELETE FROM orders WHERE order_num = 20005;
SELECT * FROM orders WHERE order_num = 20005;
# 回滚，事务结束
ROLLBACK;
# 在此查询，数据仍在
SELECT * FROM orders WHERE order_num = 20005;
```

当 `COMMIT` 或者 `ROLLBACK` 语句执行后，事务会自动关闭。

在事务中可以设置保留点，以此达到更精细化地控制回滚粒度的目的。使用 `SAVEPOINT identifier` 来设置保留点，使用 `ROLLBACK TO [SAVEPOINT] identifier` 回滚到某一个保留点，而不是回滚整个事务。

保留点回来事务完成后自动释放，也可以使用 `RELEASE SAVEPOINT identifier` 来显示地释放保留点。

MySQL 默认采取**自动提交（`AUTOCOMMIT`）**模式，也就是说如果不是显示地开启一个事务，则每个查询都被当作一个事务执行提交操作。在**当前连接**中，可以通过设置 `AUTOCOMMIT` 来启用或者禁用自动提交模式：

```shell
mysql> SHOW VARIABLES LIKE 'AUTOCOMMIT';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| autocommit    | ON    |
+---------------+-------+
1 row in set (0.00 sec)

mysql> SET AUTOCOMMIT = 1;
```

禁用自动提交后，所有查询都是在一个事务中执行，直到显示地执行 `COMMIT` 提交或者 `ROLLBACK` 回滚。

## 字符集和校对顺序

- 字符集（charset）是字母、符号的集合。

- 编码（encoding）是某个字符集成员的内部表示。

- 校对（collation）是规定字符如何比较的指令。

MySQL 中的字符集校对命名一般遵循一定的规律，例如 `_bin` 结尾的校对直接按字符的二进制编码大小比较，`_cs` 结尾的校对是大小写敏感的（case sensitive），`_ci` 结尾的校对是大小写不敏感的（case insensitive）等，具体可以参见 [Collation Naming Conventions](<https://dev.mysql.com/doc/refman/5.7/en/charset-collation-names.html>)。

字符集和校对相关的查询命令如下：

```mysqL
# 显示所有可用的字符集及其默认校对
SHOW CHARACTER SET;
# 显示所有可用的校对及其适用的字符集
SHOW COLLATION;
# 显示适用的字符集和校对情况
SHOW VARIABLES LIKE 'character%';
SHOW VARIABLES LIKE 'collation%';
```

后两条命令的查询结果示例如下：

```shell
mysql> SHOW VARIABLES LIKE 'character%';
+--------------------------+----------------------------+
| Variable_name            | Value                      |
+--------------------------+----------------------------+
| character_set_client     | utf8                       |
| character_set_connection | utf8                       |
| character_set_database   | utf8mb4                    |
| character_set_filesystem | binary                     |
| character_set_results    | utf8                       |
| character_set_server     | latin1                     |
| character_set_system     | utf8                       |
| character_sets_dir       | /usr/share/mysql/charsets/ |
+--------------------------+----------------------------+
8 rows in set (0.00 sec)
```

```shell
mysql> SHOW VARIABLES LIKE 'collation%';
+----------------------+-------------------+
| Variable_name        | Value             |
+----------------------+-------------------+
| collation_connection | utf8_general_ci   |
| collation_database   | utf8mb4_bin       |
| collation_server     | latin1_swedish_ci |
+----------------------+-------------------+
3 rows in set (0.00 sec)
```

如果在建表时指定了表的字符集和校对，则 MySQL 使用指定的字符集和校对建表，如果只指定了字符集，则 MySQL 使用指定的字符集及其默认校对建表，如果都没有指定，则 MySQL 使用数据库的字符集和校对建表。

建表时还可以单独指定某一列的字符集和校对。另外在 `SELECT` 查询时还可以指定特定的校对，例如下面的例子：

```mysql
SELECT * FROM customers
ORDER BY cust_name, cust_contact COLLATE utf8mb4_general_ci;
```

## 访问控制和用户管理

查询 MySQL 服务器上的所有用户信息。

```mysql
# 用户信息保存在 mysql 数据库的 user 表
use mysql;
SELECT user FROM user;
```

创建、修改、删除用户账号。

```mysql
CREATE USER ben IDENTIFIED BY 'password';
RENAME USER ben TO bforta;
# 会同时删除用户相关的权限
DROP USER bforta;
```

新创建的账号可以登录 MySQL，但是没有操作数据库的权限。分配权限的一个例子如下：

```mysql
# 给 bforta 分配 crashcourse 数据库上所有表的 SELECT 权限
GRANT SELECT ON crashcourse.* TO bforta;
```

显示用户的所有权限使用 `SHOW GRANTS FOR user` 命令，奇怪的是 `USAGE` 表示没有权限。

取消分配的权限的例子如下：

```mysql
REVOKE SELECT ON crashcourse.* FROM bforta;
```

修改用户密码的命令如下：

```mysql
SET PASSWORD FOR bforta = Password('newpassword');
```

## 参考资料

1. 《MySQL 必知必会》，Ben Forta 著，人民邮电出版社 2009 年 1 月第 1 版
2. [MySQL 5.7 版本官方文档](<https://dev.mysql.com/doc/refman/5.7/en/>)
3. [图解 SQL 里的各种 JOIN](<https://zhuanlan.zhihu.com/p/29234064>)