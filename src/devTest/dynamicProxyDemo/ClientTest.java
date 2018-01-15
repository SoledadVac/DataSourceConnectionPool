package devTest.dynamicProxyDemo;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午3:06
 * \* Description:动态代理，客户端测试代码
 * \
 */
public class ClientTest {
    public static void main(String[] args) {
        ProxyHandler proxyHandler=new ProxyHandler();
        Subject sub = (Subject) proxyHandler.bind(new RealSubject());
        sub.doSomeThing();
        sub.doSomeThingElse();
        //..........

    }
}
