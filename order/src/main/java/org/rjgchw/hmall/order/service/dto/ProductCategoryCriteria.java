package org.rjgchw.hmall.order.service.dto;

import io.github.jhipster.service.filter.StringFilter;

/**
 * @author Huangw
 * @date 2020-04-01 16:17
 */
public class ProductCategoryCriteria {

    private StringFilter name;

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }
}
