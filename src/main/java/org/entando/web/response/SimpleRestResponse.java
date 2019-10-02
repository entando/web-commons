package org.entando.web.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Getter@Setter
public class SimpleRestResponse<T> extends RestResponse<T, Map<String, Object>> {

    public SimpleRestResponse(final T payload) {
        super(payload);
    }

    public SimpleRestResponse() {
        super();
    }

    public void addMetadata(final String key, final Object value) {
        ofNullable(getMetadata()).orElseGet(this::init)
                .put(key, value);
    }

    private Map<String, Object> init() {
        setMetaData(new HashMap<>());
        return getMetadata();
    }

}
