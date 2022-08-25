package com.persoff68.fatodo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.persoff68.fatodo.service.exception.ModelInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonService {

    private final ObjectMapper objectMapper;

    public <T> T deserialize(String value, Class<T> clazz) {
        try {
            return objectMapper.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new ModelInvalidException();
        }
    }

    public <T> List<T> deserializeList(String value, Class<T> clazz) {
        try {
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapper.readValue(value, collectionType);
        } catch (JsonProcessingException e) {
            throw new ModelInvalidException();
        }
    }

}
