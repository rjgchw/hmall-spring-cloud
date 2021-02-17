package org.rjgchw.hmall.order.service.dto;

import io.github.jhipster.service.filter.StringFilter;

/**
 * @author Huangw
 * @date 2020-03-22 15:08
 */
public class ProductBrandCriteria {

    private StringFilter name;

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

}
