package org.rjgchw.hmall.order.repository.search;

import org.rjgchw.hmall.order.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 *
 * @author Huangw
 * @date 2021-02-23 17:03
 */
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
}
