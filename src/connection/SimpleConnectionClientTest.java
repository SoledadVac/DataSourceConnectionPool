package connection;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/17
 * \* Time: 上午10:27
 * \* Description:简单测试连接池
 * \
 */
public class SimpleConnectionClientTest {

    static ConnectionPool connectionPool=new ConnectionPool(10);

    static AtomicInteger got=new AtomicInteger();

    static AtomicInteger notGot=new AtomicInteger();

    static int clientNum=100;//模拟客户端数量

    static int waitConnectTimeout=100;//获取连接时候等待超时的时间

    static int useConnectionTimeInterval=30;//客户端使用连接的时间

    static CountDownLatch begin=new CountDownLatch(1);//用于控制线程同时开始的

    static CountDownLatch finish=new CountDownLatch(clientNum);//用于保证main线程在所有客户端线程执行完之后可以获取执行结果


    /**
     * 测试代码
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        for (int i=0;i<clientNum;i++){
            Thread client=new Thread(new GetConnectionTest(),"client-"+(i+1));
            client.start();
        }

        begin.countDown();//客户端线程初始化完毕，可以开始了

        finish.await();
        System.out.println("got="+got);
        System.out.println("notGot="+notGot);
    }

    /**
     * 用于模拟客户端获取连接
     */
    static class GetConnectionTest implements Runnable{

        @Override
        public void run() {
            try {
                begin.await();
                Connection conn=connectionPool.fetchConnection(waitConnectTimeout);
                if(conn==null){
                    notGot.incrementAndGet();
                }else{
                    got.incrementAndGet();
                    Thread.sleep(useConnectionTimeInterval);//表示使用连接的过程
                    connectionPool.releaseConnection(conn);//表示使用连接完成之后释放连接的过程
                }
                finish.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
