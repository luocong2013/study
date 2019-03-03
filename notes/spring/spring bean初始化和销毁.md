## Spring相关知识
### 一、Spring Bean初始化/销毁的三种方式
1. 在指定方法上加@PostConstruct或@PreDestroy注解
2. 通过实现InitializingBean/DisposableBean接口来定制初始化之后/销毁之前的操作方法
3. 通过为bean指定 initMethod/destroyMethod 属性来指定初始化之后/销毁之前的操作方法

> 三种初始化顺序
> Constructor > @PostConstruct > InitializingBean > initMethod

### 二、实例化Bean的过程
1. 调用InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation()方法，如果该Bean实现了InstantiationAwareBeanPostProcessor接口
2. 创建Bean实例
3. 调用InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation()方法，如果该Bean实现了InstantiationAwareBeanPostProcessor接口
4. 设置Bean属性值
5. 调用Bean中的BeanNameAware.setBeanName()方法，如果该Bean实现了BeanNameAware接口
6. 调用Bean中的BeanFactoryAware.setBeanFactory()方法，如果该Bean实现了BeanFactoryAware接口
7. 调用BeanPostProcessor.postProcessBeforeInitialization()方法（@PostConstruct注解后的方法就是在这里被执行的）
8. 调用InitializingBean的afterPropertiesSet()方法
9. 调用Bean中的initMethod，通常是在配置bean的时候指定了initMethod
10. 调用BeanPostProcessor.postProcessAfterInitialization()方法
11. 如果该Bean是单例的，则当容器销毁并且该Bean实现了DisposableBean接口的时候，调用destory方法；如果该Bean是prototype，则将准备好的Bean提交给调用者，后续不再管理该Bean的生命周期

### 三、Spring Bean的作用域
1. 默认情况下, IOC 容器中的 bean 是单例的! 若对象是单例的, 则在创建 IOC 容器时即创建 bean 的实例, 并对 bean 的属性进行初始化.
2. 可以通过 bean 的 scope 属性来修改 bean 的作用域. 若取值为 prototype, 则 bean 为原型的: 每次向容器获取实例, 得到的都是一个新的对象.而且, 不在创建 IOC 容器时创建 bean 的实例了.
3. IOC 容器中 bean 的生命周期:
   3.1 一般地, 讨论 bean 的生命周期, 是建立在 bean 是单例的基础上的.
   3.2 可以为 bean 指定 init 和 destroy 方法
   3.3 还可以通过 bean 的后置处理器来更加丰富 bean 的生命周期方法(面试时.).

### 四、Spring引入属性文件
```xml
<context:property-placeholder location="classpath*:properties/*.properties"/>
```

### 五、Spring自动扫包
```xml
1. 自动扫描(需要自动注入的类，对于那些类上有注解：@Repository、@Service、@Controller、@Component都进行注入)
2. 因为 Spring MVC 是 Spring 的子容器，所以我们在 Spring MVC 的配置中再指定扫描 Controller 的注解，这里是父容器的配置地方
Ant通配符：
1：?  匹配任意单字符
2：*  匹配0或者任意数量的字符
3：** 匹配0或者更多的目录
注解注入的相关材料可以看：
http://blog.csdn.net/u012763117/article/details/17253849
http://casheen.iteye.com/blog/295348
http://blog.csdn.net/zhang854429783/article/details/6785574
<context:component-scan base-package="cn.com.ccyw.**.mapper,cn.com.ccyw.**.dao,cn.com.ccyw.**.service"/>
```

### 六、corn表达式
```xml
cron表达式详解：
一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
按顺序依次为：
序号  字段          允许值                                                     允许的特殊字符
 1    秒           （0~59）                                                    , - * /
 2    分钟         （0~59）                                                    , - * /
 3    小时         （0~23）                                                    , - * /
 4    天           （1~31）                                                    , - * ? / L W C
 5    月           （1~12或 JAN-DEC）                                          , - * /
 6    星期         （1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）           , - * ? / L C #
 7    年份（可选）  （1970－2099）                                              , - * /

 CRON表达式                  含义
 "0 0 12 * * ?"          每天中午十二点触发
 "0 15 10 ? * *"         每天早上10：15触发
 "0 15 10 * * ?"         每天早上10：15触发
 "0 15 10 * * ? *"       每天早上10：15触发
 "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
 "0 * 14 * * ?"          每天从下午2点开始到2点59分每分钟一次触发
 "0 0/5 14 * * ?"        每天从下午2点开始到2：55分结束每5分钟一次触发
 "0 0/5 14,18 * * ?"     每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
 "0 0-5 14 * * ?"        每天14:00至14:05每分钟一次触发
 "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
 "0 15 10 ? * MON-FRI"   每个周一、周二、周三、周四、周五的10：15触发
```