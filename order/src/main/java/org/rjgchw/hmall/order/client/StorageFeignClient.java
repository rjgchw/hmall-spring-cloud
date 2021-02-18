package org.rjgchw.hmall.order.client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Huangw
 * @date 2021-02-18 16:46
 */
@AuthorizedFeignClient(name = "storage")
public interface StorageFeignClient {

    /**
     *  扣减库存
     * @param productId
     * @param productQuantity
     * @return
     */
    @PostMapping("/api/storages/deduct")
    Boolean deduct(@RequestParam("productId") Long productId, @RequestParam("productQuantity") Integer productQuantity);
}
