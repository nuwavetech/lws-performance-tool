// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

public class PayloadGenerator {

  public static String generatePayload(String requestType, int messageSize) throws Exception {

    String payload;

    if ("simple".equalsIgnoreCase(requestType)) {
      payload = generateSimplePayload(messageSize);
    } else if ("complex".equalsIgnoreCase(requestType)) {
      payload = generateComplexPayload(messageSize);
    } else {
      throw new IllegalArgumentException("Invalid request type: " + requestType);
    }

    return payload;
  }

  private static String generateSimplePayload(int messageSize) throws Exception {
    short msgCode = 1; // Example message code
    StringBuilder payloadBuilder = new StringBuilder(messageSize);
    payloadBuilder.append("x".repeat(Math.min(messageSize - 2, 2097150))); // -2 for msgCode
    SimpleMsg simpleMsg = new SimpleMsg(msgCode, payloadBuilder.toString());
    return JsonSerializer.serialize(simpleMsg);
  }

  private static String generateComplexPayload(int messageSize) throws Exception {
    short msgCode = 2;                                // Example message code
    int itemCount = Math.min(messageSize / 64, 1000); // Each ComplexType is 64 bytes. 1000 is max.
    ComplexType[] items = new ComplexType[itemCount];
    for (int i = 0; i < itemCount; i++) {
      items[i] = new ComplexType(i,                          // intField
                                 i,                          // doubleField
                                 String.format("%d.1", i),   // unumericField
                                 String.format("%+d.1", -i), // numericField
                                 String.format("Item %d", i) // charField
      );
    }
    ComplexMsg complexMsg = new ComplexMsg(msgCode, items);
    return JsonSerializer.serialize(complexMsg);
  }
}