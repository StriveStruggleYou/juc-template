package io.github.ssy.juc.template;

import io.github.ssy.juc.template.latch.MergeAction;
import io.github.ssy.juc.template.latch.MergeActionByLatch;

public class JucTemplateApplication {


  public static void main(String args[]) throws Exception {
    final MergeActionByLatch mergeActionByLatch = new MergeActionByLatch();
    MergeAction mergeAction = new MergeAction() {
      @Override
      public Object action(String uniqueAction) throws Exception {
        Thread.sleep(100);
        System.out.println("==========");
        return System.currentTimeMillis();
      }

      @Override
      public String getMergeAction() {
        return "mergeAction";
      }
    };
    mergeActionByLatch.setTargetAction(mergeAction);

    //设置逻辑信息
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            System.out.println(mergeActionByLatch.action("1111"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }
    }).start();

    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            System.out.println(mergeActionByLatch.action("1111"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }
    }).start();


    //设置逻辑信息
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            System.out.println(mergeActionByLatch.action("1111"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }
    }).start();

    //设置逻辑信息
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            System.out.println(mergeActionByLatch.action("1111"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }
    }).start();

    //设置逻辑信息
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            System.out.println(mergeActionByLatch.action("1111"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }
    }).start();

    //设置逻辑信息
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            System.out.println(mergeActionByLatch.action("1111"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }
    }).start();


  }


}
