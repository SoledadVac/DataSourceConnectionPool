package devTest.waitNotifyDemo;

import java.util.concurrent.CountDownLatch;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午5:01
 * \* Description:CountDownLatch
 * \
 */
public class CountDownLatchDemo {

    static CountDownLatch latch=new CountDownLatch(2);

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1-----begin");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();//-1
                System.out.println("thread1-----end");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2-----begin");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();//-1
                System.out.println("thread2-----end");
            }
        }).start();


        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished....");

    }

}
