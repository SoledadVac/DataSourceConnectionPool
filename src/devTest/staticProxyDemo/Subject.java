package devTest.staticProxyDemo;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午2:23
 * \* Description:主题对象，抽象要调用的方法
 * \
 */
public interface Subject {

    void doSomeThing();

    void init();

    void begin();

    void after();

    void doSomeThingElse();

    void sleep();

}
