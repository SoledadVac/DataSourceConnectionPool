package connection;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午1:35
 * \* Description:连接池类-负责创建，持有连接
 * \
 */
public class ConnectionPool {

    private LinkedList<Connection> pool=new LinkedList<>();//从链表首部获取连接，从尾部回收连接

    /**
     * 数据库连接池构造函数
     * @param initSize 连接池初始化大小
     */
    public ConnectionPool(int initSize){
        if(initSize>0){
            for(int i=0;i<initSize;i++){
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    /**
     * 获取连接
     * @param mils 等待时间，即等待多久获取不到连接就超时
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mils) throws InterruptedException{
        synchronized (pool){
            //当传入的参数为负数时候，不使用等待超时，直接是一直等待到获取到通知为止
            if(mils<=0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return  pool.removeFirst();
            }else{//超时等待。。。
                long future=System.currentTimeMillis()+mils;
                long remaining=mils;
                while (pool.isEmpty()&&remaining>0){//使用等待超时模型
                    pool.wait(remaining);
                    remaining=future-System.currentTimeMillis();
                }
                /**注意，超时之后，说明一直没有获取到通知，此时，返回的连接为null**/
                Connection result=null;
                if(!pool.isEmpty()){
                    result=pool.removeFirst();
                }
                return result;
            }
        }
    }

    /**
     * 释放连接
     * @param connection
     */
    public void releaseConnection(Connection connection){
        if(connection!=null){
            synchronized (pool){
                //连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();//通知所有等待的线程，可以获取连接了
            }
        }

    }


}
