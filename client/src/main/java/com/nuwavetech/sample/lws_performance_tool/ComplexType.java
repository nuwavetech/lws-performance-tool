// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.
package com.nuwavetech.sample.lws_performance_tool;

public class ComplexType {
  private int intField;
  private double doubleField;
  private String unumericField; // PIC 9(12)V9(4)
  private String numericField;  // PIC S9(11)V9(4)
  private String charField;

  public ComplexType() {
  } // Default constructor for Jackson

  public ComplexType(int intField, double doubleField, String unumericField, String numericField,
                     String charField) {
    this.intField = intField;
    this.doubleField = doubleField;
    this.unumericField = unumericField;
    this.numericField = numericField;
    this.charField = charField;
  }

  public int getIntField() {
    return intField;
  }
  public void setIntField(int intField) {
    this.intField = intField;
  }
  public double getDoubleField() {
    return doubleField;
  }
  public void setDoubleField(double doubleField) {
    this.doubleField = doubleField;
  }
  public String getUnumericField() {
    return unumericField;
  }
  public void setUnumericField(String unumericField) {
    this.unumericField = unumericField;
  }
  public String getNumericField() {
    return numericField;
  }
  public void setNumericField(String numericField) {
    this.numericField = numericField;
  }
  public String getCharField() {
    return charField;
  }
  public void setCharField(String charField) {
    this.charField = charField;
  }
}
