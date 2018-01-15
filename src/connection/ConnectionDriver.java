package connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午1:52
 * \* Description:获取连接
 * \
 */
public class ConnectionDriver {

    /**
     * 定义动态代理类
     */
    static class ConnectionHandler implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit")){
                TimeUnit.MICROSECONDS.sleep(100);
            }
            return null;
        }
    }

    /**
     * 创建连接
     * @return
     */
    public static final Connection createConnection(){
        return (Connection)Proxy.newProxyInstance(
                ConnectionDriver.class.getClassLoader(),
                new Class[]{Connection.class},
                new ConnectionHandler());
    }

}
