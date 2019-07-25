package org.entando.web.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Getter@Setter
public class EntandoEntity<T> extends RestResponse<T, Map<String, Object>> {

    public EntandoEntity(final T payload) {
        super(payload);
    }

    public EntandoEntity() {
        super();
    }

    public void addMetadata(final String key, final Object value) {
        ofNullable(getMetadata()).orElseGet(this::init)
                .put(key, value);
    }

    private Map<String, Object> init() {
        setMetadata(new HashMap<>());
        return getMetadata();
    }

}
