// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

public class SimpleMsg {
  private short msgCode;
  private String payload;

  public SimpleMsg() {
  } // Default constructor for Jackson

  public SimpleMsg(short msgCode, String payload) {
    this.msgCode = msgCode;
    this.payload = payload;
  }

  public short getMsgCode() {
    return msgCode;
  }
  public void setMsgCode(short msgCode) {
    this.msgCode = msgCode;
  }
  public String getPayload() {
    return payload;
  }
  public void setPayload(String payload) {
    this.payload = payload;
  }
}
