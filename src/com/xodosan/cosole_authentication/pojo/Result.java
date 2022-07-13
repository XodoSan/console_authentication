package com.xodosan.cosole_authentication.pojo;

import com.xodosan.cosole_authentication.Error;

public class Result {
  private boolean result;

  private Error error;

  public Result(boolean result, Error error) {
    this.result = result;
    this.error = error;
  }

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public Error getError() {
    return error;
  }

  public void setError(Error error) {
    this.error = error;
  }
}
