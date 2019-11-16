package io.github.ssy.juc.template.latch;

import java.util.concurrent.ConcurrentHashMap;
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

  private long awaitTimeout;

  public MergeActionByLatch() {
    this.latchAndDataMap = new ConcurrentHashMap();
    //ms
    this.awaitTimeout = 2000;
  }

  public T action(String uniqueAction) throws Exception {
    long start = System.currentTimeMillis();
    LatchAndData<T> latchAndData = latchAndDataMap.get(uniqueAction);
    if (latchAndData == null) {
      latchAndData = new LatchAndData<>();
      LatchAndData otherLatchAndData = latchAndDataMap.putIfAbsent(uniqueAction, latchAndData);
      if (otherLatchAndData != null) {
        latchAndData = otherLatchAndData;
      }
      if (latchAndData.getAndIncrement() == 0) {
        try {
          T result = targetAction.action(uniqueAction);
          latchAndData.setData(result);
        } catch (Exception e) {
          log.error("actionTask:" + targetAction.getMergeAction(), e);
          latchAndData.setData(null);
        } finally {
          latchAndData.countDown();
          latchAndDataMap.remove(uniqueAction);
        }
      }
    }
    boolean b = latchAndData.await(awaitTimeout, TimeUnit.MILLISECONDS);
    log.warn(Thread.currentThread().getName() + ",cost time:"
      + (System.currentTimeMillis() - start));
    if (!b) {
      log.warn("MergeActionByLatch:" + uniqueAction + " ,timeout:" + awaitTimeout);
      return targetAction.action(uniqueAction);
    }
//    log.warn("latchAndData" + JSON.toJSONString(latchAndData));
    return latchAndData.getData();
  }

  @Override
  public String getMergeAction() {
    return "MergeActionByLatch";
  }


}
