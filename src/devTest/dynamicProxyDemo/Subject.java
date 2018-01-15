package devTest.dynamicProxyDemo;

/**
 * \* Created: liuhuichao
 * \* Date: 2018/1/15
 * \* Time: 下午2:37
 * \* Description:动态代理，主题类
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
