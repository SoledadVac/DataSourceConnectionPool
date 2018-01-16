package devTest.waitNotifyDemo;

import java.util.concurrent.TimeUnit;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午5:10
 * \* Description:等待--通知模型
 * \
 */
public class WaitNotifyDemo {

    static Object lock=new Object();
    static Boolean flag=true;//flag的操作，均在加锁情况下进行的，无需额外控制其在线程可见性的操作

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread=new Thread(new WaitObj());
        waitThread.start();

        Thread.sleep(2000);

        Thread notifyThread=new Thread(new NotifyObj());
        notifyThread.start();
    }

    /**
     * 等待线程
     * 模型描述：
     *
     *       synchronized (对象){
     *          while(条件不满足时候){
     *              对象.wait()
     *          }
     *          条件满足时候执行逻辑
     *       }
     *
     *
     *
     */
    static class WaitObj implements Runnable{
        @Override
        public void run() {
            synchronized (lock){ //wait,notify等方法首先获得该对象的锁后才能调用，否则会抛出IllegalMonitorStateException
                //当条件不满足时，继续wait，同时释放了lock的锁
                while (flag){
                    try {
                        System.out.println("WaitOjb--is--waiting");
                        lock.wait();//调用wait()方法后，会释放对象的锁
                        System.out.println("");
                    } catch (InterruptedException e) {  //除了notify通知，带超时的wait()方法、线程中断机制也能唤醒此线程
                        e.printStackTrace();
                    }
                    System.out.println("finish while");
                }
                System.out.println("flag is false,complate!!!");
            }
        }
    }


    /**
     * 通知线程
     * 模型描述：
     *
     *       synchronized (对象){
     *          改变条件
     *          对象.notify()
     *          处理逻辑。。
     *       }
     *
     */
    static class NotifyObj implements Runnable{
        @Override
        public void run() {

            synchronized (lock){
                //获取lock的锁，然后进行通知，通知时不会释放lock的锁，直到当前线程释放了lock，调用了notifyAll，并且WaitThread获得了锁之后，wait线程才能从wait()方法返回
                System.out.println("Notify get lock ,begin notify");
                lock.notifyAll();
                flag=false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//这里释放了lock的锁，wait线程有机会获取lock锁了
            try {
                TimeUnit.SECONDS.sleep(9);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(lock){ //这里又获得了锁
                System.out.println("Notify get lock again");
            }

        }
    }




}
