package devTest.staticProxyDemo;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午2:29
 * \* Description:客户端测试--静态代理
 * \
 */
public class ClientTest {
    public static void main(String[] args) {
        Subject subject=new Proxy();
        subject.doSomeThing();//there is real subject method
        subject.sleep();
        subject.doSomeThingElse();
    }
}
