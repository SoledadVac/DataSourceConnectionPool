package devTest.dynamicProxyDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午2:39
 * \* Description:代理类，当主题中增加方法时，可以保证，代理类不会随之变动
 * \
 */
public class ProxyHandler implements InvocationHandler {

    private Object target;//定义目标类

    public Object bind(Object target){
        this.target=target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=null;
        //这里就可以进行所谓的AOP编程了
        //在调用具体函数方法前，执行功能处理
        result = method.invoke(target,args);
        //在调用具体函数方法后，执行功能处理
        return result;
    }
}
