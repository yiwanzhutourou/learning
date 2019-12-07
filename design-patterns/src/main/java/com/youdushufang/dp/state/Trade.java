package com.youdushufang.dp.state;

public class Trade {

    private TradeState state;

    public Trade() {
        this.state = TradeState.NEW;
    }

    public void pay() {
        state.pay(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    @Override
    public String toString() {
        return "Trade(state=" + state + ")";
    }

    private void changeState(TradeState newState) {
        this.state = newState;
    }

    private enum TradeState {

        NEW {
            @Override
            void pay(Trade trade) {
                System.out.println("pay...");
                trade.changeState(TradeState.PAID);
            }

            @Override
            void cancel(Trade trade) {
                System.out.println("cancel...");
                trade.changeState(TradeState.CANCELED);
            }
        },

        PAID {
            @Override
            void deliver(Trade trade) {
                System.out.println("deliver...");
                trade.changeState(TradeState.DELIVERED);
            }
        },

        DELIVERED, CANCELED;

        void pay(Trade trade) {
            throw new UnsupportedOperationException("Not support to pay in state: " + this);
        }

        void deliver(Trade trade) {
            throw new UnsupportedOperationException("Not support to deliver in state: " + this);
        }

        void cancel(Trade trade) {
            throw new UnsupportedOperationException("Not support to cancel in state: " + this);
        }
    }
}
