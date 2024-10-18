// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

public class ResultsReporter {
  private final Metrics metrics;
  private final Configuration config;

  public ResultsReporter(Metrics metrics, Configuration config) {
    this.metrics = metrics;
    this.config = config;
  }

  public void printResults() {
    System.out.println("\nPerformance Test Results:");
    System.out.println("-------------------------");

    String[][] data = {
        {"Threads", String.format("%d", config.getThreadCount())},
        {"Type", config.getRequestType()},
        {"Target IPM Data Size", String.format("%,d", config.getIPMSize())},
        {"Duration", String.format("%d", config.getTestDuration())},
        {"JSON Request Payload Size", String.format("%,d", metrics.getRequestPayloadSize())},
        {"JSON Response Payload Size", String.format("%,d", metrics.getResponsePayloadSize())},
        {"Total Requests", String.format("%,d", metrics.getTotalRequests())},
        {"Total Errors", String.format("%,d", metrics.getTotalErrors())},
        {"Bytes Sent", String.format("%,d", metrics.getTotalBytesSent())},
        {"Bytes Received", String.format("%,d", metrics.getTotalBytesReceived())},
        {"Total Bytes Transferred", String.format("%,d", metrics.getTotalBytes())},
        {"Avg Response Time (ms)", String.format("%.2f", metrics.getAvgResponseTime())},
        {"Min Response Time (ms)", String.format("%d", metrics.getMinResponseTime())},
        {"Max Response Time (ms)", String.format("%d", metrics.getMaxResponseTime())},
        {"Requests per Second", String.format("%.2f", calculateRequestsPerSecond())}};

    int maxKeyLength = 0;
    int maxValueLength = 0;
    for (String[] row : data) {
      maxKeyLength = Math.max(maxKeyLength, row[0].length());
      maxValueLength = Math.max(maxValueLength, row[1].length());
    }

    int totalWidth = maxKeyLength + maxValueLength + 5; // 5 for minimum dots and spaces

    for (String[] row : data) {
      StringBuilder line = new StringBuilder(row[0]);
      line.append(" "); // Add one space after the name
      int dotsCount = totalWidth - row[0].length() - row[1].length() - 2; // -2 for the added spaces
      line.append(".".repeat(Math.max(0, dotsCount)));
      line.append(" "); // Add one space before the value
      line.append(row[1]);
      System.out.println(line);
    }
    System.out.println();
  }

  private double calculateRequestsPerSecond() {
    return (double)metrics.getTotalRequests() / config.getTestDuration();
  }
}