package io.github.ssy.juc.template.latch;

public interface MergeAction<T> {

  T action(String uniqueAction) throws Exception;


  String getMergeAction();


}
