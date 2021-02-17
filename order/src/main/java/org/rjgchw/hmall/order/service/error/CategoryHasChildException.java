package org.rjgchw.hmall.order.service.error;

/**
 * 存在子元素不允许删除
 * @author Huangw
 * @date 2020-04-01 16:36
 */
public class CategoryHasChildException extends RuntimeException {
    public CategoryHasChildException(String message) {
        super(message);
    }
}
