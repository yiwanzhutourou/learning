package com.youdushufang;

import com.youdushufang.lock.Account;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AccountTest {

    // jps 命令查看所有的 Java 进程，找到对应进程的 pid
    // jstack pid 可以 dump 栈信息，检测出一个死锁如下：

    // Found one Java-level deadlock:
    // =============================
    // "Thread-2":
    //   waiting to lock monitor 0x00007fa71c00d958 (object 0x00000007976382f0, a java.lang.Object),
    //   which is held by "Thread-1"
    // "Thread-1":
    //   waiting to lock monitor 0x00007fa71c00d538 (object 0x0000000797638300, a java.lang.Object),
    //   which is held by "Thread-2"

    @Disabled
    @Test
    void testDeadLock() throws InterruptedException {
        Account a = new Account();
        a.setBalance(200);
        Account b = new Account();
        b.setBalance(100);

        Thread thread1 = new Thread(() -> a.transferTo(b, 100));
        Thread thread2 = new Thread(() -> b.transferTo(a, 100));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("a = " + a.getBalance());
        System.out.println("b = " + b.getBalance());
    }
}
