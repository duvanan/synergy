/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.utils;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.synergy.jackson.StandardObjectMapperBuilder;

@UtilityClass
@Slf4j
public class ObjectMapperUtil {
    
    private static final String EMPTY_STRING = "";
    
    public static final ObjectMapper DEFAULT_MAPPER = new StandardObjectMapperBuilder().build();
    
    public static final ObjectMapper MAPPER_UNESCAPED_CONTROL_CHAR;
    
    static {
        MAPPER_UNESCAPED_CONTROL_CHAR = new StandardObjectMapperBuilder().build();
        MAPPER_UNESCAPED_CONTROL_CHAR.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
    }
    
    public static <T> T jsonToObject(String jsonStr, Class<T> valueType) {
        if (jsonStr == null) {
            return null;
        }
        
        try {
            return DEFAULT_MAPPER.readValue(jsonStr, valueType);
        } catch (JsonProcessingException e) {
            log.error("Cannot convert from json to object");
            throw new IllegalStateException("Cannot convert from json to object");
        }
    }
    
    public static <T> T jsonToObject(String jsonStr, TypeReference<T> type) {
        if (jsonStr == null) {
            return null;
        }
        
        try {
            return DEFAULT_MAPPER.readValue(jsonStr, type);
        } catch (JsonProcessingException e) {
            log.error("Cannot convert from json to object. Json = {}, valueType = {}. Message = {}", jsonStr, type,
                    e.getMessage(), e);
            throw new IllegalStateException("Cannot convert from json to object");
        }
    }
    
    public static ObjectNode jsonToObjectNode(String jsonStr) {
        if (jsonStr == null) {
            return null;
        }
        
        try {
            return MAPPER_UNESCAPED_CONTROL_CHAR.readValue(jsonStr, ObjectNode.class);
        } catch (JsonProcessingException e) {
            log.error("Cannot convert from json to objectNode");
            throw new IllegalStateException("Cannot convert from json to objectNode");
        }
    }
    
    public static Map<String, Object> objectNodeToMap(ObjectNode objectNode) {
        try {
            return jsonToObject(MAPPER_UNESCAPED_CONTROL_CHAR.writeValueAsString(objectNode), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Cannot convert from objectNode to map");
            throw new IllegalStateException("Cannot convert from objectNode to map");
        }
    }
    
    public static Map<String, Object> jsonToMap(String jsonStr) {
        if (jsonStr == null) {
            return null;
        }
        
        try {
            return MAPPER_UNESCAPED_CONTROL_CHAR.readValue(jsonStr, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Cannot convert from json to map");
            throw new IllegalStateException("Cannot convert from json to map");
        }
    }
    
    public static String mapToJson(Map<String, Object> dataMap) {
        if (dataMap.isEmpty()) {
            return EMPTY_STRING;
        }
        
        try {
            return DEFAULT_MAPPER.writeValueAsString(dataMap);
        } catch (JsonProcessingException e) {
            log.error("Cannot convert from map to json");
            throw new IllegalStateException("Cannot convert from map to json");
        }
    }
    
    public static String objectToJson(Object object) {
        try {
            return DEFAULT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Cannot write object to json. Message = {}", e.getMessage(), e);
            throw new IllegalStateException("Cannot write object to json");
        }
    }
    
    public static String objectToJsonIncludeNonNull(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Cannot write object to json. Message = {}", e.getMessage(), e);
            throw new IllegalStateException("Cannot write object to json");
        }
    }
    
    public static Map<String, Object> flatten(Map<String, Object> map) {
        return map.entrySet().stream()
            .flatMap(ObjectMapperUtil::flatten)
            .collect(LinkedHashMap::new, (m, e) -> m.put("/" + e.getKey(), e.getValue()), LinkedHashMap::putAll);
    }
    
    private static Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {
        if (entry == null) {
            return Stream.empty();
        }
        
        if (entry.getValue() instanceof Map<?, ?>) {
            return ((Map<?, ?>) entry.getValue()).entrySet().stream()
                .flatMap(e -> flatten(new AbstractMap.SimpleEntry<>(entry.getKey() + "/" + e.getKey(), e.getValue())));
        }
        
        if (entry.getValue() instanceof List<?>) {
            List<?> list = (List<?>) entry.getValue();
            if (!list.isEmpty() && !(list.get(0) instanceof String)) {
                return IntStream.range(0, list.size())
                    .mapToObj(i -> new AbstractMap.SimpleEntry<String, Object>(entry.getKey() + "/" + i, list.get(i)))
                    .flatMap(ObjectMapperUtil::flatten);
            }
        }
        
        return Stream.of(entry);
    }
}
