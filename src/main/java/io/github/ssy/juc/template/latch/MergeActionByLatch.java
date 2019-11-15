package io.github.ssy.juc.template.latch;

import com.alibaba.fastjson.JSON;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Data
@Slf4j
public class MergeActionByLatch<T> implements MergeAction {


  MergeAction<T> targetAction;

  private ConcurrentHashMap<String, LatchAndData> latchAndDataMap;

  private ThreadPoolExecutor executor;

  private long awaitTimeout;

  public MergeActionByLatch() {
    this.latchAndDataMap = new ConcurrentHashMap();
    this.executor = new ThreadPoolExecutor(
      100,
      Integer.MAX_VALUE,
      10,
      TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue());
    //ms
    this.awaitTimeout = 500;
  }

  public T action(String uniqueAction) throws Exception {
    LatchAndData<T> latchAndData = latchAndDataMap.get(uniqueAction);
    if (latchAndData == null) {
      latchAndData = new LatchAndData<>();
      LatchAndData otherLatchAndData = latchAndDataMap.putIfAbsent(uniqueAction, latchAndData);
      if (otherLatchAndData != null) {
        latchAndData = otherLatchAndData;
      }
      if (latchAndData.getAtomicInteger().getAndIncrement() == 0) {
        executor
          .submit(new ActionTask<T>(latchAndData, targetAction, uniqueAction, latchAndDataMap));
      }
    }
    boolean b = latchAndData.getCountDownLatch().await(awaitTimeout, TimeUnit.MILLISECONDS);
    if (!b) {
      log.warn("MergeActionByLatch:" + uniqueAction + " ,timeout:" + awaitTimeout);
    }

//    log.warn("latchAndData" + JSON.toJSONString(latchAndData));
    return latchAndData.getResultData().getData();
  }

  @Override
  public String getMergeAction() {
    return "MergeActionByLatch";
  }


}
