package org.rjgchw.hmall.storage.service;

import org.rjgchw.hmall.storage.repository.StorageRepository;
import org.rjgchw.hmall.storage.service.error.ProductDoesNotExistException;
import org.rjgchw.hmall.storage.service.error.StorageShortageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author Huangw
 * @date 2021-02-17 11:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StorageService {

    private final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository productBrandRepository) {
        this.storageRepository = productBrandRepository;
    }

    /**
     * 锁定商品资源
     * @param productId
     * @param productQuantity
     * @return 成功/失败
     */
    public Optional<Boolean> lockStorage(Long productId, Integer productQuantity) {
        return storageRepository.findByProductId(productId)
            .map(x -> {
                if(x.getStorage() < productQuantity) {
                    throw new StorageShortageException();
                }
                return Optional.of(storageRepository.lockProduct(productId, x.getStorage(), productQuantity) > 0);
            })
            .orElseThrow(() -> new ProductDoesNotExistException(productId + " not exists"));
    }
}
