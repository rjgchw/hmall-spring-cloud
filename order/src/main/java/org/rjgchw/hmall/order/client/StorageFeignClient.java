package org.rjgchw.hmall.order.client;

import org.rjgchw.hmall.common.feign.AuthorizedFeignClient;
import org.rjgchw.hmall.order.client.dto.StorageDeductDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * @param storageDeductDTO
     * @return
     */
    @PostMapping("/api/storages/deduct")
    void deduct(@RequestBody StorageDeductDTO storageDeductDTO);
}
