package org.rjgchw.hmall.product.repository.search;

import org.rjgchw.hmall.product.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, String> {
}
