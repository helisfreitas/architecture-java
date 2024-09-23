package br.helis.architecture.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonHelper {

    private static final Log LOG = LogFactory.getLog(JsonHelper.class);

    private final ObjectMapper objectMapper;

    JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOG.debug("Failed to write JSON", e);
        }
        return "";
    }
    

    public <T> T jsonToObject(String json, Class<T> clazz) {
        if(json == null || json.length() == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            LOG.debug("Failed to read JSON", e);
        }
        return null;
    }
}
