package com.crio.warmup.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trades {
  private String symbol;

  public String getSymbol() {
    return this.symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Trades() {
  }
    
  public Trades(String symbol) {
    this.symbol = symbol;
  }
}
