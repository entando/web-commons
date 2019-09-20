package org.entando.web.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseMapModel {
    @JsonAnySetter
    protected Map<String,Object> data = new HashMap<>();

    @JsonAnyGetter
    public Map<String,Object> getData() {
        return data;
    }

    public BaseMapModel(Map<String,Object> map) {
        data = Optional.ofNullable(map)
            .orElse(new HashMap<>());
    }

    public void putAll(Map<String,Object> map) {
        data.putAll(map);
    }
}
