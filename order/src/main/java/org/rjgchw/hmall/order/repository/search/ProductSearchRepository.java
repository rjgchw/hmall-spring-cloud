package org.rjgchw.hmall.order.repository.search;

import org.rjgchw.hmall.order.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
}
