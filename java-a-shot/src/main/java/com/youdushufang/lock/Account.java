package com.youdushufang.lock;

public class Account {

    private final Object object = new Object();

    private int balance;

    public int getBalance() {
        synchronized (object) {
            return this.balance;
        }
    }

    public void setBalance(int balance) {
        synchronized (object) {
            this.balance = balance;
        }
    }

    public void transferTo(Account account, int amount) {
        synchronized (object) {
            // 保证死锁一定会出现
            sleep();
            synchronized (account.object) {
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    account.balance += amount;
                }
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
