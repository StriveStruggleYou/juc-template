package io.github.ssy.juc.template.latch;

public class ResultData<T> {

  private T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
