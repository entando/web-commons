package com.entando.web.config;

import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class GsonInstance {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Json.class, (JsonSerializer<Json>) (src, typeOfSrc, context) -> {
                    final JsonParser parser = new JsonParser();
                    return parser.parse(src.value());
                })
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (src, typeOfSrc, context) ->
                        LocalDateTime.parse(src.getAsJsonPrimitive().getAsString(), FORMATTER))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                        new JsonPrimitive((src).format(FORMATTER)))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer) (src, typeOfSrc, context) ->
                        new JsonPrimitive(((LocalDate) src).format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer) (src, typeOfSrc, context) ->
                        LocalDate.parse(src.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .create();
    }

}
