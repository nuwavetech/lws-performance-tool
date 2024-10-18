// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

import java.util.concurrent.atomic.AtomicLong;

public class Metrics {
  private final AtomicLong totalRequests = new AtomicLong(0);
  private final AtomicLong totalErrors = new AtomicLong(0);
  private final AtomicLong totalBytesSent = new AtomicLong(0);
  private final AtomicLong totalBytesReceived = new AtomicLong(0);
  private final AtomicLong totalResponseTime = new AtomicLong(0);
  private volatile long minResponseTime = Long.MAX_VALUE;
  private volatile long maxResponseTime = 0;
  private volatile int requestPayloadSize = 0;
  private volatile int responsePayloadSize = 0;

  public synchronized void recordRequest(long responseTime, int bytesSent, int bytesReceived,
                                         boolean isError) {
    totalRequests.incrementAndGet();
    if (isError) {
      totalErrors.incrementAndGet();
    }
    totalBytesSent.addAndGet(bytesSent);
    totalBytesReceived.addAndGet(bytesReceived);
    totalResponseTime.addAndGet(responseTime);
    updateMinMaxResponseTime(responseTime);
  }

  public void setPayloadSizes(int requestSize, int responseSize) {
    if (requestPayloadSize == 0 && responsePayloadSize == 0) {
      requestPayloadSize = requestSize;
      responsePayloadSize = responseSize;
    }
  }

  private void updateMinMaxResponseTime(long responseTime) {
    minResponseTime = Math.min(minResponseTime, responseTime);
    maxResponseTime = Math.max(maxResponseTime, responseTime);
  }

  public long getTotalRequests() {
    return totalRequests.get();
  }
  public long getTotalErrors() {
    return totalErrors.get();
  }
  public long getTotalBytesSent() {
    return totalBytesSent.get();
  }
  public long getTotalBytesReceived() {
    return totalBytesReceived.get();
  }
  public long getTotalBytes() {
    return totalBytesSent.get() + totalBytesReceived.get();
  }
  public double getAvgResponseTime() {
    return totalRequests.get() > 0 ? (double)totalResponseTime.get() / totalRequests.get() : 0;
  }
  public long getMinResponseTime() {
    return minResponseTime;
  }
  public long getMaxResponseTime() {
    return maxResponseTime;
  }
  public int getRequestPayloadSize() {
    return requestPayloadSize;
  }
  public int getResponsePayloadSize() {
    return responsePayloadSize;
  }
}
