package org.rjgchw.hmall.storage.web.rest;

import org.rjgchw.hmall.storage.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Boolean> deduct(@RequestParam Long productId, @RequestParam Integer productQuantity) {
        return new ResponseEntity<>(
            storageService.lockStorage(productId, productQuantity).orElse(false),
            HttpStatus.OK
        );
    }
}
