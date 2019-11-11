#《MySQL 必知必会》学习笔记

[TOC]

## 基本概念

数据库（database）、表（table）、模式（schema）、列（column）、数据类型（datatype）、行（row）、主键（primary key）、SQL（Structured Query Language，发音 S-Q-L 或者 sequel）

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
SELECT prod_name
FROM products;
# 限定列名
SELECT products.prod_name
FROM products;
# 检索多列
SELECT prod_id, prod_name, prod_price
FROM products;
# 除非明确需要表中的每一列数据，否则不要使用 SELECT *
SELECT *
FROM products;
```

### `DISTINCT`

```mysql
# DISTINCT 关键字指示 MySQL 只返回不同的值
# DISTINCT 应用在所有选中的列上
SELECT DISTINCT vend_id
FROM products;
```

### `LIMIT`

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

