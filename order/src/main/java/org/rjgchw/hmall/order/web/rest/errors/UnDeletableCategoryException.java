package org.rjgchw.hmall.order.web.rest.errors;


import org.rjgchw.hmall.common.web.rest.error.UnprocessableRequestAlertException;
import org.rjgchw.hmall.common.web.rest.error.UnprocessableRequestErrorCode;

/**
 * 商品类别存在子资源无法删除
 * @author Huangw
 * @date 2020-04-01 17:18
 */
public class UnDeletableCategoryException extends UnprocessableRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UnDeletableCategoryException(String message) {
        super(message, "product", "productCategory", UnprocessableRequestErrorCode.INVALID);
    }
}
