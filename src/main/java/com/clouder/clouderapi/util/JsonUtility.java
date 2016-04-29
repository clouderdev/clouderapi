package com.clouder.clouderapi.util;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.exception.JsonReaderException;
import com.clouder.clouderapi.exception.JsonWriterException;

@Component
public class JsonUtility {

    @Autowired
    ObjectMapper objectMapper;

    public <T> String toJson(T object) {
        String json;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new JsonWriterException(
                    "Error in converting object of class " + object.getClass().getName() + " to JSON string", e);
        }
        return json;
    }

    public <T> T toObject(String json, Class<T> objectClass) {
        T object;
        try {
            object = objectMapper.readValue(json, objectClass);
        } catch (IOException e) {
            throw new JsonReaderException("Error in converting JSON string to object of class " + objectClass.getName(),
                    e);
        }
        return object;
    }

}
