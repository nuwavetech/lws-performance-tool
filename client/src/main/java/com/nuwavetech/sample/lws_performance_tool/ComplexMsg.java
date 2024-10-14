// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

public class ComplexMsg {
  private short msgCode;
  private short itemCount;
  private ComplexType[] complexItems;

  public ComplexMsg() {
  } // Default constructor for Jackson

  public ComplexMsg(short msgCode, ComplexType[] complexItems) {
    this.msgCode = msgCode;
    this.itemCount = (short)complexItems.length;
    this.complexItems = complexItems;
  }

  public short getItemCount() {
    return itemCount;
  }

  public void setItemCount(short itemCount) {
    this.itemCount = itemCount;
  }

  public short getMsgCode() {
    return msgCode;
  }
  public void setMsgCode(short msgCode) {
    this.msgCode = msgCode;
  }
  public ComplexType[] getComplexItems() {
    return complexItems;
  }
  public void setComplexItems(ComplexType[] complexItems) {
    this.complexItems = complexItems;
  }
}