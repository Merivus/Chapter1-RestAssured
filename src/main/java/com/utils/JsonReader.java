package com.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonReader {
  public static List<Map<String, String>> readJson(String filePath) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(new File(filePath), new TypeReference<>() {});
  }
}
