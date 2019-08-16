/*
 * Copyright 2018-Present Entando Inc. (http://www.entando.com) All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package org.entando.test.model;

import org.entando.web.request.Filter;
import org.entando.web.request.FilterUtils;
import org.entando.web.request.PagedListRequest;
import org.entando.web.request.RequestListProcessor;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class MockModelListProcessor extends RequestListProcessor<MockModel> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FAMILY_NAME = "familyName";
    private static final String ACTIVE = "active";
    private static final String AGE = "age";

    public MockModelListProcessor(PagedListRequest listRequest, List<MockModel> components) {
        super(listRequest, components);
    }

    @Override
    protected Function<Filter, Predicate<MockModel>> getPredicates() {
        return filter -> {
            switch (filter.getAttribute()) {
                case ID:
                    return c -> true;
                case NAME:
                    return c -> FilterUtils.filterString(filter, c.getName());
                case FAMILY_NAME:
                    return c -> FilterUtils.filterString(filter, c.getFamilyName());
                case ACTIVE:
                    return c -> FilterUtils.filterBoolean(filter, c.isActive());
                case AGE:
                    return c -> FilterUtils.filterInt(filter, c.getAge());
                default:
                    return null;
            }
        };
    }

    @Override
    protected Function<String, Comparator<MockModel>> getComparators() {
        return sort -> {
            switch (sort) {
                case FAMILY_NAME:
                    return Comparator.comparing(MockModel::getFamilyName);
                case ACTIVE:
                    return Comparator.comparing(MockModel::isActive);
                case AGE:
                    return Comparator.comparing(MockModel::getAge);
                case NAME:
                default:
                    return Comparator.comparing(MockModel::getName);
            }
        };
    }
}
