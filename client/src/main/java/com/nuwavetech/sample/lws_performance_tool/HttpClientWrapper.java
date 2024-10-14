// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientWrapper {
  private final HttpClient client;

  public HttpClientWrapper() {
    this.client = HttpClient.newBuilder()
                      .version(HttpClient.Version.HTTP_1_1)
                      .connectTimeout(Duration.ofSeconds(10))
                      .build();
  }

  public HttpResponse<String> sendPostRequest(String url, String payload) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
                              .uri(URI.create(url))
                              .header("Content-Type", "application/json")
                              .POST(HttpRequest.BodyPublishers.ofString(payload))
                              .build();

    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}