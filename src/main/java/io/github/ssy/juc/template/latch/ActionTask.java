package io.github.ssy.juc.template.latch;

import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActionTask<T> implements Runnable {

  LatchAndData<T> latchAndData;

  MergeAction<T> targetAction;

  String uniqueAction;

  ConcurrentHashMap<String, LatchAndData> latchAndDataMap;

  public ActionTask(LatchAndData<T> latchAndData,
    MergeAction<T> targetAction, String uniqueAction,
    ConcurrentHashMap<String, LatchAndData> latchAndDataMap) {
    this.latchAndData = latchAndData;
    this.targetAction = targetAction;
    this.uniqueAction = uniqueAction;
    this.latchAndDataMap = latchAndDataMap;
  }

  @Override
  public void run() {
    try {
      T result = targetAction.action(uniqueAction);
      latchAndData.getResultData().setData(result);
    } catch (Exception e) {
      log.error("actionTask:" + targetAction.getMergeAction(), e);
      latchAndData.getResultData().setData(null);
    } finally {
      latchAndData.getCountDownLatch().countDown();
      latchAndDataMap.remove(uniqueAction);
    }

  }
}
