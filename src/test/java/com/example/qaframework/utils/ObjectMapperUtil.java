package com.example.qaframework.utils;

import com.example.qaframework.helpers.exceptions.JsonParseException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Утилитный класс для работы с {@link ObjectMapper}
 */
@Slf4j
@UtilityClass
public class ObjectMapperUtil {
    /**
     * используемый объект маппера
     */
    public static final ObjectMapper MAPPER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER = mapper;
    }

    /**
     * Десериализовать объект из json-текста. Метод следует использовать для десериализации generic-объектов
     *
     * @param content       json-текст
     * @param typeReference ссылка на тип объекта
     * @param <T>           тип объекта
     * @return десериализованный объект
     */
    public static <T> T readValue(String content, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(content, typeReference);
        } catch (JsonProcessingException e) {
            String error = "Ошибка парсинга " + content;
            log.error(error, e);
            throw new JsonParseException(error, e);
        }
    }
}
