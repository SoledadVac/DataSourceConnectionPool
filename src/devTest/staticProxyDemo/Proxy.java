package devTest.staticProxyDemo;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午2:26
 * \* Description:
 * \
 */
public class Proxy implements Subject{
    private Subject subject=new RealSubject();

    @Override
    public void doSomeThing() {
        subject.doSomeThing();
    }

    @Override
    public void init() {
        subject.init();
    }

    @Override
    public void begin() {
        subject.begin();
    }

    @Override
    public void after() {
        subject.after();
    }

    @Override
    public void doSomeThingElse() {
        subject.doSomeThingElse();
    }

    @Override
    public void sleep() {
        subject.sleep();
    }
}
