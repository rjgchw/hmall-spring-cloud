package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.domain.Authority;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * Spring Data R2DBC repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends R2dbcRepository<Authority, String> {
}
