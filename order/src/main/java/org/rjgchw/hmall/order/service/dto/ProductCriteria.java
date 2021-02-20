package org.rjgchw.hmall.order.service.dto;

import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * @author Huangw
 * @date 2020-04-01 16:21
 */
public class ProductCriteria {

    private StringFilter name;

    private LongFilter categoryId;

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }
}
