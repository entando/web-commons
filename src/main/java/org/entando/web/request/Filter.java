package org.entando.web.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Filter {

    public static final String ASC_ORDER = "ASC";
    public static final String DESC_ORDER = "DESC";

    private String attribute;
    private String entityAttr;
    private String operator;
    private String value;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String order;

    private String[] allowedValues;

}
