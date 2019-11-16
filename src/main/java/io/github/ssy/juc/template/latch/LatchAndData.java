package io.github.ssy.juc.template.latch;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;

@Data
public class LatchAndData<T> {

  private CountDownLatch countDownLatch;

  private ResultData<T> resultData;

  private AtomicInteger atomicInteger;

  private String uuid;

  //优化阻塞计数,包括自己
  private AtomicInteger lockThreadNum;

  private long lockTime;

  //要带debug的

  public LatchAndData() {
    uuid = UUID.randomUUID().toString();
    countDownLatch = new CountDownLatch(1);
    resultData = new ResultData<>();
    atomicInteger = new AtomicInteger(0);
    lockThreadNum = new AtomicInteger(0);
    lockTime = System.currentTimeMillis();
  }


  public void countDown() {
    this.countDownLatch.countDown();
  }

  public int getAndIncrement() {
    return this.atomicInteger.getAndIncrement();
  }

  boolean await(long timeout, TimeUnit unit) throws InterruptedException {
    lockThreadNum.incrementAndGet();
    return countDownLatch.await(timeout, unit);
  }

  public void setData(T data) {
    this.resultData.setData(data);
  }


  public T getData() {
    return this.resultData.getData();
  }
}
