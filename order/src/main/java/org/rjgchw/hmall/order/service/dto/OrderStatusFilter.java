/*
 * Copyright 2016-2020 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.rjgchw.hmall.order.service.dto;

import tech.jhipster.service.filter.Filter;
import org.rjgchw.hmall.order.service.enums.OrderStatusEnum;

import java.util.Objects;

/**
 * Class for filtering attributes with {@link OrderStatusEnum} type.
 * It can be added to a criteria class as a member, to support the following query parameters:
 * <code>
 * fieldName.equals='something'
 * fieldName.specified=true
 * fieldName.specified=false
 * fieldName.in='something','other'
 * fieldName.contains='thing'
 * </code>
 * @author huangwei
 */
public class OrderStatusFilter extends Filter<OrderStatusEnum> {

    private static final long serialVersionUID = 1L;

    private OrderStatusEnum contains;
    private OrderStatusEnum doesNotContain;

    /**
     * <p>Constructor for StringFilter.</p>
     */
    public OrderStatusFilter() {
    }

    /**
     * <p>Constructor for StringFilter.</p>
     *
     * @param filter a {@link OrderStatusFilter} object.
     */
    public OrderStatusFilter(final OrderStatusFilter filter) {
        super(filter);
        this.contains = filter.contains;
        this.doesNotContain = filter.doesNotContain;
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link OrderStatusFilter} object.
     */
    @Override
    public OrderStatusFilter copy() {
        return new OrderStatusFilter(this);
    }

    /**
     * <p>Getter for the field <code>doesNotContain</code>.</p>
     *
     * @return a {@link OrderStatusEnum} object.
     */
    public OrderStatusEnum getDoesNotContain() {
        return doesNotContain;
    }

    /**
     * <p>Setter for the field <code>doesNotContain</code>.</p>
     *
     * @param doesNotContain a {@link OrderStatusEnum} object.
     * @return a {@link OrderStatusFilter} object.
     */
    public OrderStatusFilter setDoesNotContain(OrderStatusEnum doesNotContain) {
        this.doesNotContain = doesNotContain;
        return this;
    }

    /**
     * <p>Getter for the field <code>contains</code>.</p>
     *
     * @return a {@link OrderStatusEnum} object.
     */
    public OrderStatusEnum getContains() {
        return contains;
    }

    /**
     * <p>Setter for the field <code>contains</code>.</p>
     *
     * @param contains a {@link OrderStatusEnum} object.
     * @return a {@link OrderStatusFilter} object.
     */
    public OrderStatusFilter setContains(OrderStatusEnum contains) {
        this.contains = contains;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final OrderStatusFilter that = (OrderStatusFilter) o;
        return Objects.equals(contains, that.contains) &&
            Objects.equals(doesNotContain, that.doesNotContain);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contains, doesNotContain);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getFilterName() + " ["
            + (getContains() != null ? "contains=" + getContains() + ", " : "")
            + (getDoesNotContain() != null ? "doesNotContain=" + getDoesNotContain() + ", " : "")
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() : "")
            + "]";
    }

}
