package devTest.waitNotifyDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/16
 * \* Time: 下午6:09
 * \* Description:等待，通知，超时模型
 * \
 */
public class WaitNotifyTimeOutDemo {

    static Object lock=new Object();
    static boolean isWaiting=true;



    public static void main(String[] args) {
        Thread waitThread=new Thread(new WaitThread(1000),"waitthread");
        waitThread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread notifyThread=new Thread(new NotifyThread(),"notifythread");
        notifyThread.start();

    }


    static class WaitThread implements  Runnable{
        long waitMis;

        WaitThread(long waitMis){
            this.waitMis=waitMis;
        }

        @Override
        public void run() {
            long futuretime=System.currentTimeMillis()+this.waitMis;//当前时间
            System.out.println("超时时间为："+System.currentTimeMillis());
            long remaining=this.waitMis;
            synchronized (lock){

                while (isWaiting && remaining>0){
                    remaining=futuretime-System.currentTimeMillis();//计算剩余时间
                    if(remaining<0){
                        System.out.println("超时了，返回吧");
                        return;
                    }

                        try {
                            System.out.println("lock begin to wait////////////");
                            lock.wait(remaining);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    System.out.println("finish while...");
                }
                System.out.println("finish ............"+System.currentTimeMillis());
                return;//超时了就返回
            }
        }
    }


    static class NotifyThread implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                isWaiting=false;
                lock.notifyAll();
                System.out.println("give up obj lock and notify waitobj~~~");
            }

        }
    }

}
