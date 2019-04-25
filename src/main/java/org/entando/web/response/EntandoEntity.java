package org.entando.web.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.util.HashMap;
import java.util.Map;

@Getter@Setter
public class EntandoEntity<T> extends ResourceSupport {

    private T payload;
    private Map<String, Object> metadata = new HashMap<>();
    private Map<String, String> errors;

    public void addMetadata(final String key, final Object value) {
        metadata.put(key, value);
    }

}
