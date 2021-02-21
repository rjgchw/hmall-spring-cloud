package org.rjgchw.hmall.storage.web.rest;

import org.rjgchw.hmall.storage.service.StorageService;
import org.rjgchw.hmall.storage.web.rest.vo.StorageDeductVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Huangw
 * @date 2021-02-18 21:53
 */
@RestController
public class StorageResource {

    private final StorageService storageService;

    public StorageResource(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/api/storages/deduct")
    public ResponseEntity<Void> deduct(@RequestBody StorageDeductVO storageDeductVO) {
        storageService.lockStorage(storageDeductVO.getProductId(), storageDeductVO.getProductQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
