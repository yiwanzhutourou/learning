## MySQL 学习笔记

学习资料包括：

- [《MySQL 必知必会》](<https://book.douban.com/subject/3354490/>)
- [《高性能 MySQL》](<https://book.douban.com/subject/23008813/>)
- [MySQL 5.7 版本官方文档](<https://dev.mysql.com/doc/refman/5.7/en/>)
- [MySQL 8.0 版本官方文档](<https://dev.mysql.com/doc/refman/8.0/en/>)

笔记内容全部在 `/notes` 目录下。

另外，项目目录下包括一个用 docker 启动的 MySQL 5.7 实例，启动后会初始化所有需要用到的数据库、表和数据（参见 [init.sql](<https://github.com/yiwanzhutourou/learning/blob/master/mysql-basics/docker/init.sql>)）。

#### 启动方式

1. 启动容器

   ```shell
   docker-compose up -d
   ```

2. 进入容器

   ```shell
   docker exec -it mysql-crash-course bash
   ```

3. 连接 MySQL，输入密码 root

   ```shell
   mysql -u root -p
   ```
