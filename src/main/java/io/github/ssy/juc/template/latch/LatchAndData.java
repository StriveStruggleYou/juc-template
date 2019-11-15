package io.github.ssy.juc.template.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;

@Data
public class LatchAndData<T> {

  private CountDownLatch countDownLatch;

  private ResultData<T> resultData;

  private AtomicInteger atomicInteger;

  public LatchAndData() {
    countDownLatch = new CountDownLatch(1);
    resultData = new ResultData<>();
    atomicInteger = new AtomicInteger(0);
  }
}
