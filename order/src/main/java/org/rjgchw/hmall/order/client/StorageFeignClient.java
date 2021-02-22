package org.rjgchw.hmall.order.client;

import org.rjgchw.hmall.common.feign.AuthorizedFeignClient;
import org.rjgchw.hmall.order.client.request.StorageDeductRequest;
import org.rjgchw.hmall.order.client.response.StorageDeductResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Huangw
 * @date 2021-02-18 16:46
 */
@AuthorizedFeignClient(name = "${ribbon-service.storage}")
public interface StorageFeignClient {

    /**
     *  扣减库存
     * @param storageDeductRequest
     * @return
     */
    @PostMapping("/api/storages/deduct")
    StorageDeductResponse deduct(@RequestBody StorageDeductRequest storageDeductRequest);
}
