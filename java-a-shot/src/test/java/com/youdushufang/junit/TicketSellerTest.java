package com.youdushufang.junit;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("售票器类型测试")
public class TicketSellerTest {

    private TicketSeller ticketSeller;

    @BeforeAll
    public static void init() {
        // 会在类的测试方法开始前调用，用于准备类全局测试环境
    }

    @AfterAll
    public static void cleanup() {
        // 会在类的测试方法结束后调用，用于清理资源
    }

    @BeforeEach
    public void create() {
        // 会在每一个类的测试方法开始前调用
        this.ticketSeller = new TicketSeller();
    }


    @AfterEach
    public void destroy() {
        // 会在每一个类的测试方法结束后调用
    }

    @Test
    @DisplayName("售票后余票应减少")
    public void testReduceInventoryAfterTicketSold() {
        ticketSeller.setInventory(10);
        ticketSeller.sell(1);
        Assertions.assertEquals(9, ticketSeller.getInventory());
        // AssertJ 写法
        assertThat(ticketSeller.getInventory()).isEqualTo(9);
    }

    @Test
    @DisplayName("余票不足应报错")
    public void testThrowExceptionWhenNoEnoughInventory() {
        ticketSeller.setInventory(0);
        Assertions.assertThrows(TicketException.class,
                () -> ticketSeller.sell(1),
                "all ticket sold out");
        // AssertJ 写法
        assertThatExceptionOfType(TicketException.class)
                .isThrownBy(() -> ticketSeller.sell(1))
                .withMessageContaining("all ticket sold out")
                .withNoCause();
    }

    @Test
    @DisplayName("有退票时余票应增加")
    public void testIncreaseInventoryAfterTicketRefunded() {
        ticketSeller.setInventory(10);
        ticketSeller.refund(1);
        Assertions.assertEquals(11, ticketSeller.getInventory());
        // AssertJ 写法
        assertThat(ticketSeller.getInventory()).isEqualTo(11);
    }

    @Test
    @DisplayName("设置异常余票")
    public void testThrowExceptionWhenSetNegativeInventory() {
        Assertions.assertThrows(TicketException.class,
                () -> ticketSeller.setInventory(-1),
                "negative inventory not support");
        // AssertJ 写法
        assertThatExceptionOfType(TicketException.class)
                .isThrownBy(() -> ticketSeller.setInventory(-1))
                .withMessageContaining("negative inventory not support")
                .withNoCause();
    }

    @Test
    @DisplayName("销售异常票数")
    public void testThrowExceptionWhenSellNegativeTicket() {
        Assertions.assertThrows(TicketException.class,
                () -> ticketSeller.sell(-1),
                "invalid sell number");
        // AssertJ 写法
        assertThatExceptionOfType(TicketException.class)
                .isThrownBy(() -> ticketSeller.sell(-1))
                .withMessageContaining("invalid sell number")
                .withNoCause();
    }

    @Test
    @DisplayName("退异常票数")
    public void testThrowExceptionWhenRefundNegativeTicket() {
        Assertions.assertThrows(TicketException.class,
                () -> ticketSeller.refund(-1),
                "invalid refund number");
        // AssertJ 写法
        assertThatExceptionOfType(TicketException.class)
                .isThrownBy(() -> ticketSeller.refund(-1))
                .withMessageContaining("invalid refund number")
                .withNoCause();
    }
}
