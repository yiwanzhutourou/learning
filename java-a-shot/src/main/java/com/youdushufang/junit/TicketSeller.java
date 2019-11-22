package com.youdushufang.junit;

public class TicketSeller {

    private int inventory;

    public synchronized void setInventory(int inventory) {
        if (inventory < 0) {
            throw new TicketException("negative inventory not support");
        }
        this.inventory = inventory;
    }

    public synchronized int getInventory() {
        return this.inventory;
    }

    public synchronized void sell(int number) {
        if (number <= 0) {
            throw new TicketException("invalid sell number");
        }
        if (number > inventory) {
            throw new TicketException("all ticket sold out");
        }
        inventory -= number;
    }

    public synchronized void refund(int number) {
        if (number <= 0) {
            throw new TicketException("invalid refund number");
        }
        inventory += number;
    }
}
