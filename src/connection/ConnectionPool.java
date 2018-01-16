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

    private LinkedList<Connection> pool=new LinkedList<>();

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
     * @param mils
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mils) throws InterruptedException{
        synchronized (pool){
            //完全超时
            if(mils<=0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return  pool.removeFirst();
            }else{//超时等待。。。
                long future=System.currentTimeMillis()+mils;
                long remaining=mils;
                while (pool.isEmpty()&&remaining>0){
                    pool.wait(remaining);//超时等待
                    remaining=future-System.currentTimeMillis();
                }
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
                pool.notifyAll();
            }
        }

    }


}
