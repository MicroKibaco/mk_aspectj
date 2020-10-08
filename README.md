> 本文相关代码Github地址[mk_aspectj](https://github.com/MicroKibaco/mk_aspectj)，有帮助的话Star一波吧。

### 《APM基础: AspectJ》 大纲
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6372842779de40bdb82c84b6ca166e41~tplv-k3u1fbpfcp-zoom-1.image)

&emsp;&emsp;作为Android开发,多多少少遇到过这种情况,我的App很卡,大概知道问题出现在哪?却无从下手,接受别人的工程代码写的一团糟,出现天大的bug,却因为不熟悉业务不敢乱动,有没有一种侵入性比较低,比较和谐的方式去修改业务代码呢<br/>&emsp;&emsp;学习设计模式可以一定程度降低业务耦合度,不过那都是 OOP 的思想,今天我给大家带来一份 AOP 的切面编程思想,无侵入方式织入代码到业务。如果对你有所启发,欢迎点赞转发

## 一. 关键技术
那么问题来了?
- 什么是 AOP?
- AOP 有什么用?
- AOP怎样学习?
> 小朋友，你是否有很多问号？

AOP 维基百科是这么说的?
> spect-oriented programming (AOP) is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. It does so by adding additional behavior to existing code (an advice) without modifying the code itself, instead separately specifying which code is modified via a "pointcut" specification, such as "log all function calls when the function's name begins with 'set'". This allows behaviors that are not central to the business logic (such as logging) to be added to a program without cluttering the code, core to the functionality. AOP forms a basis for aspect-oriented software development


&emsp;&emsp;是不是挺晦涩难懂的,我这边简单总结一下吧。

- AOP 即 面向切面编程,通过 AOP ,可以在编译器对代码进行动态管理,以达到统一维护的目的。
- AOP 其实是 OOP 思想的一种延续,同时也是 Spring 框架的一个重要的模块

&emsp;&emsp;对 Java 后端开发,其实并不陌生,因为 Spring 动态代理织入 其实就是借助AOP 思想。它解决了什么问题呢?

- 利用 AOP ,我们可以各个业务模块进行隔离,从而使得业务逻辑各个部分之间的耦合度降低,提高程序的可重用性,同时也会提高开发效率,比如:我们怎样做耗时测量,怎样在不修改代码前提下给指定业务库插入代码。
- 利用 AOP ,我们可以在无侵入的状态下在宿主中插入一些代码逻辑,从而实现一些特殊的功能,比如: 日志埋点, 性能监控,动态权限控制,代码调试等。

&emsp;&emsp;既然 AOP 这么好,那么我们怎么学习他呢?学习一个工具,先得了解他的一些专业术语如:
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e17edf86fbdb4d28bdcf890402380545~tplv-k3u1fbpfcp-zoom-1.image)
- **Advice: 增强**
  - 增强是织入到目标类连接点的一段程序代码,在Spring框架中,增强除了被用于描述一段程序代码外,还拥有另外一个连接点相关信息,这便是执行点的方位。结合执行点方位信息和切点信息,我们就能找到特地的连接 
  
- **JoinPoint: 连接点**
   - 什么是连接点?
     - 一个类或者一个程序代码拥有一些具有边界性质的特定点,这种特定点称为JoinPoint
     
  - 连接点执行的某个特定的位置
    - 1. 类初始化前
    - 2. 类初始化后
    - 3. 类中某个方法调用前
    - 4. 方法抛出异常后
    
   - Spring 框架的缺陷
     - 只支持方法的连接点
       - 方法调用前 方法调用后 方法抛出异常 方法调用前后程序执行点织入
       
       
- **PointCut: 切点**
  - 定位到某个连接点的查询工具,需要提供方位信息
  
- **Aspect: 切面**
  - 组成部分 : 增强 + 切点

-  **Weaving: 织入**
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0b3b5e3fd63d46e287b35156ff741255~tplv-k3u1fbpfcp-zoom-1.image)
   - 织入实现方式
     - 编译器织入
       - ajc 编译器提供
     - 类装载期织入
       - ClassLoader提供
     - 动态代理织入
       - 运行期为目标类添加增强生成子类
       
 - **Target: 目标对象**  
     - 定义 PointCut
       - 我们需要对哪些地方增加额外的操作,通过PointCut查询JoinPoint
     - 告诉程序 JointCut 怎样增强 Advice
       - Aspect 里面被修复的对象叫 Target,完成AOP操作叫Weaving
       
&emsp;&emsp;掌握这些基础知识,AOP差不多就学会了,等等,这么快就学会了,不是还没走源码嘛,这个Spring我也没有太多研究过,后面有机会再和大伙过一遍,我们开始直接正入主题,进入我们的Title AspectJ 吧 ,学习 AspectJ 有一个很重要的基础,就是得了解AspectJ 注解,常见的切点表达式,只有这样,才能正确使用AspectJ,基于自定义Gradle Plugin 实现代码织入等一系列好玩的事情。

### 二. AspectJ
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8f05f19426d6455ba8682411b2c51cdb~tplv-k3u1fbpfcp-zoom-1.image)
#### 2.0.1 AspectJ 注解
![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8223ca3b794e4f29bb1ab1630cce9cf0~tplv-k3u1fbpfcp-zoom-1.image)
##### 2.0.1.1 @AspectJ
 - 切面类,目的是让 ajc 编译器识别
 
```java
 @Aspect
public class MkOnClickListenerAspectJ {}
```
MkOnClickListenerAspectJ 类 在编译器 会被 AspectJ 的 ajc 编译器识别

##### 2.0.1.2 @PointCut

- 定义切点标记方法
```java
    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    public void fastClickViewPointcut(JoinPoint point) {

        Log.e("fastClickViewPointcut", "------------------");

    }
```
&emsp;&emsp;Pointcut 注解用来匹配 OnClickListener 注解的 onClick 方法

##### 2.0.1.3 @Before


- 前置增强,在某个连接点之前执行


```java
    @Before("execution(void android.content.DialogInterface.OnClickListener.onClick(..))")
    public void fastClickViewBefore(JoinPoint point) {
        View view =  (View) point.getArgs()[0];
        Log.e("fastClickViewBefore", view.toString()+"------------------");
    }
```

&emsp;&emsp;这个切点表达式,可以匹配所有 android.content.DialogInterface.OnClickListener.onClick 方法,并在方法之前获取view,然后将其打印

##### 2.0.1.4 @After

- 后置增强,在某个连接点之后执行

```java
    @After("execution(void android.support.v7.app.AppCompatViewInflater.DeclaredOnClickListener.onClick(..))")
    public void fastClickViewAfter(JoinPoint point) {

      View view =  (View) point.getArgs()[0];
        Log.e("fastClickViewAfter", view.toString()+"------------------");
    }

```

&emsp;&emsp;上面的切点表达式,可以匹配所有DeclaredOnClickListener.onClick方法,并在方法之后获取 view 参数,然后将其打印 

##### 2.0.1.5 @Around

- 环绕增强,在切点前后执行

```java
    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void fastClickViewAround(ProceedingJoinPoint point) throws Throwable {

        Log.e("AspectJ", "before" );
        long startNanoTime = System.nanoTime();

        Object proceed = point.proceed();
        Log.e("AspectJ", "after" );

        long stopNanoTime = System.nanoTime();
        MethodSignature signature = (MethodSignature) point.getSignature();

        // 方法名
        String name = signature.getName();
        Log.e("AspectJ", "proceed" + name);

        Method method = signature.getMethod();
        Log.e("AspectJ", method.toGenericString());

        // 返回值类型
        Class returnType = signature.getReturnType();
        Log.e("AspectJ", returnType.getSimpleName());

        Class declaringType = signature.getDeclaringType();
        Log.e("AspectJ", declaringType.getSimpleName());

        Class signatureDeclaringType = signature.getDeclaringType();
        Log.e("AspectJ", signatureDeclaringType.getSimpleName());

        Class declaringType1 = signature.getDeclaringType();
        Log.e("AspectJ", declaringType1.getSimpleName());

        Class[] parameterTypes = signature.getParameterTypes();

        for (Class parameterType : parameterTypes) {
            Log.e("AspectJ", parameterType.getSimpleName());
        }

        for (String parameterName : signature.getParameterNames()) {
            Log.e("AspectJ", parameterName);
        }

        Log.e("AspectJ", String.valueOf(stopNanoTime - startNanoTime));
    }
```
&emsp;&emsp;方法在执行之前打印 Log.e("AspectJ", "before" ); 执行之后打印     Log.e("AspectJ", "after" );,主要是根据 proceed 返回值处理不同的业务场景

##### 2.0.1.6 @AfterReturing

- 返回增强切入点方法返回结果后执行

```java
    @AfterReturning("execution(@butterknife.OnClick * *(..))")
    public void fastClickViewAfterReturning(JoinPoint point) {
        Log.e("AfterReturning", "------------------");
    }

```

可以匹配所有 @butterknife.OnClick 方法,并在结果返回结果之后打印 "AfterReturning"

##### 2.0.1.7 @AfterThowing

- 异常增强,切点抛出异常时执行

```java
    @AfterThrowing("execution(@butterknife.OnClick * *(..))")
    public void fastClickViewThrowing(JoinPoint point) {
        Log.e("fastClickViewThrowing", "------------------");

    }
```
   
   execution(@butterknife.OnClick * *(..))  抛出异常的时候打印,可以用这个 API 做日志上报工作
   
   
   了解了这么多切点以及切点表达式的使用,那么他们使用有什么通用的公式呢,下面我们进入总结阶段。
   
#### 2.0.2 切点表达式
 - execution
   - execution 基本规范
     - execution(「修饰符模式」? 「返回类型模型」「方法名模型」(「参数模型」)「异常模型」?)
     	- 修饰符模式: 如 public private protected
        - 返回类型模型: 如 String Object 等
        - 方法名模型: 如 traceOnClickView
        - 参数模型:  如Params
        - 异常模型: 如ClassNotFoundException
        - ?  : 表示非必选
        
        最近在翻 AspectJ 官方文档发现一个有趣的 Q&A 后面有不少程序员大佬热议
        ![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/31023c6d9b614248b1c66adb372f8565~tplv-k3u1fbpfcp-zoom-1.image),这个问题我其实也挺尴尬的,之前没有细研过,于是写个测试用例来验证一下
#### 2.0.3 call 与 execution 区别        
        
```java    
public class C {
    public void foo() {
        bar();
    }

    public void bar() {
    }
}
public aspect A {

    // the output will be:
    // call(void C.bar())
    // execution(void C.foo())
    before() :
        call(public void C.bar()) {
          System.out.println(thisJoinPoint);
          System.out.println(thisEnclosingJoinPointStaticPart);
       }

    // the output will be:
    // execution(void C.bar())
    // execution(void C.bar())
    before() :
       execution(public void C.bar()) {
          System.out.println(thisJoinPoint);
          System.out.println(thisEnclosingJoinPointStaticPart);
       }

}

```

其实两者最大的区别是,一个是在调用点,一个是执行点。也就是说 execution 是切入方法中,call 是 在调用被切入的方法 


#### 2.0.4 AspectJ 使用方法

- 通过 Gradle Plugin 如: AspectJx
- 通过Gradle 配置

#### 2.0.5 通过 Gradle 配置使用 AspectJ
&emsp;&emsp; 之前在 `akulaku` 用这种方式做了一个需求,结果被领导批头盖脸骂了一顿,说我这边没把原理弄清楚,是的我是直接从github抄的参数,当时很无语,我用一天的时间做完了三天工时的需求,还给了两套方案,现在想想其实这种方法对不熟悉Gradle Plugin并不是很靠谱,除非你确实不想用三方插件实现

#### 2.0.6 自定义 Gradle Plugin
##### 2.0.6.1 新建一个项目,作为主moudle,即 :app![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/154320f62fd04be8aa705b9ab7ae05d8~tplv-k3u1fbpfcp-zoom-1.image)
##### 2.0.6.2 创建 plugin moudle
- 1. 创建 Android Lib, moudle 名字可以命名为 plugin,目录结构如下
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/62dde1f3aada495b8a585b95eb9065d4~tplv-k3u1fbpfcp-zoom-1.image)
  - 删除不用的src文件 之类的
  
#### 2.0.6.3 创建 grovvy 目录 
- 因为开发是基于 Groovy 语言的,所以插件 的代码放在 src/main/groovy 目录下,然后在该目录新建一个pageage,命名为: com.github.microkibaco.plugin.android

#### 2.0.6.4 创建 properties 文件

- 在 src/main 目录下,一次创建 resource/META-INFgradle-plugins 文件,在创建一个 后缀为.properties  的文件,用来声明名称以及对于常见的包名
   - 比如我创建了 com.github.microkibaco.properties
   ![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4ab79a9b4baa400fb877916be8f66b06~tplv-k3u1fbpfcp-zoom-1.image)

#### 2.0.6.5 添加依赖关系

- 修改 plugin/build.gradle 文件 对 gradle ,grovvy 的依赖
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f56063ae79e242ed90c5c5032d7169e6~tplv-k3u1fbpfcp-zoom-1.image)

#### 2.0.6.6 实现插件
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/66898fd631f1459f968621c6c2473159~tplv-k3u1fbpfcp-zoom-1.image)

#### 2.0.6.7 修改 .properties 文件
 - implementation-class= 包名 + 类名
   - implementation-class=com.github.microkibaco.plugin.android.MkAspectjPlugin
 - 编译这个 plugin,可以在 plugin/build 发现新生成的插件 .jar 文件![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/48b6acbfc2f54109aee058fc26f7a3b6~tplv-k3u1fbpfcp-zoom-1.image)![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1f8a3b50f2b245cc9f30582c3f15dd84~tplv-k3u1fbpfcp-zoom-1.image)  
 
 ### 2.0.7 发布 Gradle 插件
 - 2.0.7.1  发布方式
   - 发布插件到本地仓库
   	- 1. 引入 mavenDeployer 插件
      	- 修改 plugin/build.gradle 文件,引入 mavenDeployer 这个插件来发布到本地仓库,下面来讲讲这里面几个比较常见的参数含义是啥
        	- groupId 组织名称或者公司名称
            - artifactId 项目名或者模块名
            - version 项目或模块的当前版本包
    	- 2. 编译插件![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/295f2289f26d4b9aa405fbf3084878ed~tplv-k3u1fbpfcp-zoom-1.image)![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4119c262e94d48d6af17cf2a2dd0e3c0~tplv-k3u1fbpfcp-zoom-1.image)![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7d9f6a2cd8334624a16c1e90af53f36c~tplv-k3u1fbpfcp-zoom-1.image)
   - 发布插件到远程仓库
### 2.0.8 使用 Gradle Plugin

#### 2.0.8 使用 Gradle Plugin
- 修改 Project/build.gradle 配置,格式为: groupId.artfactId:version![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9c7aeb4315454f4fb660a5bd7d02a1e5~tplv-k3u1fbpfcp-zoom-1.image)
- 修改 app/build.gradle 格式为 resource/META-INFgradle-plugins  .properties前缀文件名
### 2.0.9 Plugin Project![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/79cfd2f733f3408fb68bf28b76f2fedc~tplv-k3u1fbpfcp-zoom-1.image)![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fb2fe4cf94724546a6ebdb50f02b508f~tplv-k3u1fbpfcp-zoom-1.image)
### 2.1.0 核心思想
- 最核心的模块是 ajc 编译器,它其实就是将 AspectJ 代码在编译期 插入目标程序当中,使用 AspectJ 最关键的是使用 ajc 编译器 , 编译器将 AspectJ 代码插入切出来的 PointCut 中,达到AOP 目的![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4e6b6f246c30415ea725e89b38618740~tplv-k3u1fbpfcp-zoom-1.image)
## 三. AspectJ 全埋点实现
### 3.1 AspectJ 全埋点原理![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/931c7bdc07804c149837b47b0edcad0b~tplv-k3u1fbpfcp-zoom-1.image)
- Android 系统中的 View 它的点击处理逻辑,都是通过设置相应的 listener 对象并重写相应回调方法实现
	- 在应用编译期间,如生成 .dex 前在 onClick 方法中插入相应埋点代码,即可做到自动埋点,也就是全埋点
    - AspectJ 的处理脚本放到我们的自定义插件里,编写相应的切面类没在定义合适的 PointCut 用来匹配我们织入目标方法,如 onClick ,这样就可以在编译期插入埋点代码
    
### 3.2 AspectJ 全埋点实现过程    
## 四. AspectJ 全埋点实现方案优化
## 五. AspectJ 扩展采集能力
## 六. AspectJ 的缺陷
- 无法织入第三方库
- 由于定义的切点以来编程语言,该方案无法兼容 Lambada 语法
- 会有一些兼容问题,如: D8 Gradle 4.x 等等

