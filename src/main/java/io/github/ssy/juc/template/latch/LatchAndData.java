package io.github.ssy.juc.template.latch;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;

@Data
public class LatchAndData<T> {

  private CountDownLatch countDownLatch;

  private ResultData<T> resultData;

  private AtomicInteger atomicInteger;

  private String uuid;


  //要带debug的

  public LatchAndData() {
    uuid= UUID.randomUUID().toString();
    countDownLatch = new CountDownLatch(1);
    resultData = new ResultData<>();
    atomicInteger = new AtomicInteger(0);
  }
}
