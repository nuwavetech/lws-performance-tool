// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonSerializer {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  public static String serialize(Object obj) throws Exception {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      System.err.println("Error serializing object: " + e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }

  public static <T> T deserialize(String json, Class<T> clazz) throws Exception {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (Exception e) {
      System.err.println("Error deserializing JSON: " + e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }
}