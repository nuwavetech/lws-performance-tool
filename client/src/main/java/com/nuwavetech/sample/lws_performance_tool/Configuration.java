// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
  private int threadCount;
  private String requestType;
  private int messageSize;
  private int testDuration;
  private String baseUrl;

  public Configuration(String[] args) throws IOException {
    if (args.length != 4) {
      throw new IllegalArgumentException(
          "Usage: java PerformanceTester <threads> <request-type> <target-IPM-size> <test-duration>");
    }

    this.threadCount = Integer.parseInt(args[0]);
    this.requestType = args[1];
    this.messageSize = Integer.parseInt(args[2]);
    this.testDuration = Integer.parseInt(args[3]);

    this.baseUrl = readBaseUrl();
  }

  private String readBaseUrl() throws IOException {
    String url = System.getenv("BASE_URL");
    if (url == null || url.isEmpty()) {
      throw new IOException("BASE_URL not found in environment variable.");
    }
    return url;
  }

  public int getThreadCount() {
    return threadCount;
  }

  public String getRequestType() {
    return requestType;
  }

  public int getIPMSize() {
    return messageSize;
  }

  public int getTestDuration() {
    return testDuration;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public String getTargetUrl() {
    String targetUrl = baseUrl + (requestType.equalsIgnoreCase("simple") ? "/simple" : "/complex");
    return targetUrl;
  }
}