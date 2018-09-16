## <center>Spring Boot 各种注解</center>
### 一、Bean 配置相关
* @Configuration
> 用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法

* @Import
> 支持导入配置类，也支持导入普通的java类，并将其声明成一个bean

   ```java
   public class UserDao {
       public void say() {
           System.out.println("say......");
       }
   }

   @Configuration
   @Import(UserDao.class)
   public class Configure {

   }
   ```

* @ImportResource
> 就是之前xml配置中的import标签，引入spring xml 配置文件

   ```java
   @Configuration
   @ImportResource("classpath:spring-common.xml")
   public class Configure {

   }

   等同于如下配置：

   <import resource="spring-common.xml"/>
   ```

* @Bean
> 是一个方法级别上的注解，主要用在@Configuration注解的类里，也可以用在@Component注解的类里。添加的bean的id默认为方法名

   1. 这个配置就等同于之前在xml里的配置
   ```xml
    <beans>
        <bean id="bookDao" class="com.ccyw.dao.BookDao"/>
    </beans>
   ```
   2. bean 的依赖
   ```java
   @Configuration
   public class Configure {

       @Bean
       public BookDao bookDao(User user) {
           return new BookDao(user);
       }
   }
   ```
   3. bean 接受生命周期的回调
   ```java
   @Configuration
   public class Configure {

       @Bean(initMethod = "init", destroyMethon = "destroy")
       public BookDao bookDao() {
           return new BookDao();
       }
   }
   ```
   4. 指定bean的scope
   ```java
   @Configuration
   public class Configure {

       @Bean
       @Scope("prototype")
       public BookDao bookDao() {
           return new BookDao();
       }
   }
   ```

   5. 自定义bean的命名
   > 默认情况下bean的名称和方法名称相同，你也可以使用name属性来指定

   ```java
   @Configuration
   public class Configure {

       @Bean(name = "book")
       @Scope("singleton")
       public BookDao bookDao() {
           return new BookDao();
       }
   }
   ```

* @Conditional
> 根据满足某个特定的条件创建一个特定的Bean

* @ConditionalOnClass
> 某个class位于类路径上，才会实例化一个Bean

* @ConditionalOnMissingClass
> 某个class类路径上不存在的时候，才会实例化一个Bean

* @ConditionalOnBean
> DI容器中存在该类型Bean时，才会实例化一个Bean

* @ConditionalOnMissingBean
> DI容器中不存在该类型Bean时，才会实例化一个Bean

* @ConditionalOnSingleCandidate
> DI容器中该类型Bean只有一个或@Primary的只有一个时，才会实例化一个Bean

* @ConditionalOnExpression
> 当SpEL表达式结果为true时，才会实例化一个Bean

* @ConditionalOnProperty
> 参数设置或者值一致时起效

* @ConditionalOnResource
> 指定的文件存在时起效

* @ConditionalOnJndi
> 指定的JNDI存在时起效

* @ConditionalOnJava
> 指定的Java版本存在时起效

* @ConditionalOnCloudPlatform
> 云平台激活时起效

* @ConditionalOnWebApplication
> Web应用环境下起效

* @ConditionalOnNotWebApplication
> 非Web应用环境下起效

* @AutoConfigureAfter
> 在指定的配置类初始化后再加载

* @AutoConfigureBefore
> 在指定的配置类初始化前加载

* @AutoConfigureOrder
> 数越小越先初始化

### 二、Spring 常见注解
* @Autowired和@Resource
> @Autowired与@Resource都可以用来装配bean. 都可以写在字段上,或写在setter方法上

   1. @Autowired是spring的注解，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作，通过 @Autowired的使用来消除 set ，get方法，@Autowired默认按类型装配，默认情况下必须要求依赖对象必须存在，如果要允许null值，可以设置它的required属性为false，如：@Autowired(required=false) ，如果我们想使用名称装配可以结合@Qualifier注解进行使用，如：@Autowired() @Qualifier("bookDao")
   2. @Resource是JSR-250的注解,在Spring2.5中引入Spring，@Resource默认安照名称进行装配，名称可以通过name属性进行指定，如果没有指定name属性，当注解写在字段上时，默认取字段名进行按照名称查找，如果注解写在setter方法上默认取属性名进行装配。 当找不到与名称匹配的bean时才按照类型进行装配。但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。如：@Resource(name="bookDao")只会取bean名称为bookDao进行装配

* @PostConstruct和@PreDestroy
> 这两个注解修饰的方法会在bean初始化和销毁的时候调用，他们都是JSR-250的注解，在Spring2.5中引入Spring

   1. Bean初始化时的调用顺序：Constructor > @Autowired > @PostConstruct > initMethod > ... > @PreConstruct > destroyMethod