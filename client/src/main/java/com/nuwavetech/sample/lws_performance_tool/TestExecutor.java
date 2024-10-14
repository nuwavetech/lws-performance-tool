// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestExecutor {
  private final Configuration config;
  private final HttpClientWrapper httpClient;
  private final Metrics metrics;
  private final AtomicBoolean isRunning = new AtomicBoolean(true);
  private final long startTime;
  private final String payload;

  public TestExecutor(Configuration config) throws Exception {
    this.config = config;
    this.httpClient = new HttpClientWrapper();
    this.metrics = new Metrics();
    this.startTime = System.currentTimeMillis();

    // Generate payload once
    this.payload = PayloadGenerator.generatePayload(config.getRequestType(), config.getIPMSize());
  }

  public void runTest() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(config.getThreadCount());
    long endTime = startTime + (config.getTestDuration() * 1000L);

    for (int i = 0; i < config.getThreadCount(); i++) {
      executor.submit(this::runContinuously);
    }

    // Start a thread to print progress
    Thread progressThread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        System.out.printf("Progress: %7d messages in %4d seconds.%n", metrics.getTotalRequests(),
                          elapsedSeconds);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
    progressThread.start();

    while (System.currentTimeMillis() < endTime) {
      Thread.sleep(100); // Check every 100ms
    }

    isRunning.set(false);
    executor.shutdown();
    executor.awaitTermination(10, TimeUnit.SECONDS);

    progressThread.interrupt();
    progressThread.join();
  }

    private void runContinuously() {
      while (isRunning.get()) {
        performSingleRequest();
      }
    }

    private void performSingleRequest() {
      long requestStartTime = System.currentTimeMillis();
      try {
        int requestSize = payload.getBytes().length;
        HttpResponse<String> response = httpClient.sendPostRequest(config.getTargetUrl(), payload);
        long requestEndTime = System.currentTimeMillis();
        int responseSize = response.body().getBytes().length;

        metrics.recordRequest(requestEndTime - requestStartTime, requestSize, responseSize,
                              response.statusCode() != 200);
        metrics.setPayloadSizes(requestSize, responseSize);
      } catch (Exception e) {
        long requestEndTime = System.currentTimeMillis();
        metrics.recordRequest(requestEndTime - requestStartTime, 0, 0, true);
        System.err.println("Error during request: " + e.getMessage());
      }
    }

    public Metrics getMetrics() {
      return metrics;
    }
}