// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

public class LWSPerformanceTool {
  public static void main(String[] args) {
    try {
      System.out.println("Starting LightWave Server Performance Tool ...");
      Configuration config = new Configuration(args);

      System.out.println("Configuration loaded. Arguments: [threads:" + config.getThreadCount() +
                         ", type:" + config.getRequestType() +
                         ", target IPM size:" + config.getIPMSize() +
                         ", duration:" + config.getTestDuration() + " secs ]");

      System.out.println("Base URL: " + config.getBaseUrl());
      System.out.println("Target URL for " + config.getRequestType() + ": " +
                         config.getTargetUrl());

      TestExecutor executor = new TestExecutor(config);
      System.out.println("Starting test execution...");
      executor.runTest();
      System.out.println("Test execution completed.");

      ResultsReporter reporter = new ResultsReporter(executor.getMetrics(), config);
      reporter.printResults();

    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
