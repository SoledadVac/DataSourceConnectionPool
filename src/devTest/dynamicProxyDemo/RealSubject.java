package devTest.dynamicProxyDemo;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午2:38
 * \* Description:
 * \
 */
public class RealSubject implements Subject{
    @Override
    public void doSomeThing() {
        System.out.println("RealSubject_doSomeThing");
    }

    @Override
    public void init() {
        System.out.println("RealSubject_init");
    }

    @Override
    public void begin() {
        System.out.println("RealSubject_begin");
    }

    @Override
    public void after() {
        System.out.println("RealSubject_after");
    }

    @Override
    public void doSomeThingElse() {
        System.out.println("RealSubject_doSomeThingElse");
    }

    @Override
    public void sleep() {
        System.out.println("RealSubject_sleep");
    }
}
