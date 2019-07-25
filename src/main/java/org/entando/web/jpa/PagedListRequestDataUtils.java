package org.entando.web.jpa;

import org.entando.web.request.PagedListRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PagedListRequestDataUtils {

    public static Pageable toPageable(final PagedListRequest request) {
        final Direction direction = "DESC".equals(request.getDirection()) ? Direction.DESC : Direction.ASC;
        final Sort sort = Sort.by(direction, request.getSort());
        return PageRequest.of(request.getPage() - 1, request.getPageSize(), sort);
    }

//    public static <T> Specification<T> toSpecification(final PagedListRequest request, Class<T> clazz) {
//    }

}
