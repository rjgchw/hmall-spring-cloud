package org.rjgchw.hmall.order.service.error;

/**
 * 锁定商品库存失败
 * @author Huangw
 * @date 2020-04-05 21:03
 */
public class LockStockFailException extends RuntimeException {
    public LockStockFailException() {
        super();
    }
}
