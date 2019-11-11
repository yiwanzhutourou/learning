## 这是什么？

MySQL 基础中的基础，学习《MySQL 必知必会 / MySQL Crash Course》的过程记录。所有笔记内容在 notes.md 中，目录下包括一个用 docker 启动的 MySQL 5.7 实例，具体参见「如何开始？」。

## 如何开始？

1. 启动容器

   docker-compose up -d

2. 进入容器

   docker exec -it mysql-crash-course bash

3. 连接 MySQL，输入密码 root

   mysql -u root -p trade

4. 输入 `show tables;` 看到对应的内容即可开始
