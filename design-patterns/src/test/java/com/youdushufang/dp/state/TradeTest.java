package com.youdushufang.dp.state;

import org.junit.jupiter.api.Test;

public class TradeTest {

    @Test
    public void testPayTrade() {
        Trade trade = new Trade();
        trade.pay();
        System.out.println(trade);
        try {
            trade.cancel();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDeliverTrade() {
        Trade trade = new Trade();
        trade.pay();
        trade.deliver();
        System.out.println(trade);
        try {
            trade.pay();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
        try {
            trade.cancel();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testCancelTrade() {
        Trade trade = new Trade();
        trade.cancel();
        System.out.println(trade);
        try {
            trade.pay();
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
