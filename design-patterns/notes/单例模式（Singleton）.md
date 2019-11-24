# 单例模式（Singleton）

## 意图

> Ensure a class only has one instance, and provide a global point of access to it.
>
> 确保一个类只有一个实例，这个实例作为使用这个类功能的全局唯一入口。

## 适用场景

- 某个类全局只能有一个实例，且需要为其他类提供方便获取这个实例的接口。

## 结构图

![单例模式结构图](https://youdu-markdown.oss-cn-shanghai.aliyuncs.com/20191124184017.png)

## 参与角色

- Singleton
  - 提供一个获取单例对象的静态方法
  - 负责 Singleton 对象的构造，且保证全局唯一

## 实现

1. 懒加载线程不安全的单例，多线程竞争下构造函数可能被执行多次。

```java
public class NotThreadSafeLazySingleton {
    private static NotThreadSafeLazySingleton INSTANCE = null;

    private NotThreadSafeLazySingleton() { }

    public static NotThreadSafeLazySingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotThreadSafeLazySingleton();
        }
        return INSTANCE;
    }
}
```

2. 非懒加载线程安全的单例，由虚拟机类加载机制保证多线程竞争下构造函数只会执行一次，缺点是构造函数在类加载时就会被执行，而不是第一次调用 `getInstance()` 的时候。

```java
public class ThreadSafeEagerSingleton {
    private static final ThreadSafeEagerSingleton INSTANCE =
        new ThreadSafeEagerSingleton();

    private ThreadSafeEagerSingleton() { }

    public static ThreadSafeEagerSingleton getInstance() {
        return INSTANCE;
    }
}
```

3. 懒加载线程安全的单例，`getInstance()` 由于加上了 `synchronized` 锁所以变成串行的了，虽然保证了多线程下构造函数只会执行一次，但是之后每一次获取实例都加上了完全没有必要的锁。上面两种单例模式的实现虽然有不足，但是还有一定的适用场景，而这种方式定义的单例模式完全找不到适用场景。

```java
public class ThreadSafeLazySingleton {
    private static ThreadSafeLazySingleton INSTANCE = null;

    private ThreadSafeLazySingleton() { }

    public static synchronized ThreadSafeLazySingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThreadSafeLazySingleton();
        }
        return INSTANCE;
    }
}
```

4. 利用 Double Checked Locking 实现懒加载线程安全的单例模式，在进入 `getInstance()` 方法获取单例对象时先判断对象是否为空再加锁，避免了对象实例化后重复加锁的问题。`synchronized` 代码块内仍需要再判断对象是否为空，是因为当第一个线程实例化对象后释放锁，被阻塞的第二个线程再次进入 `synchronized` 代码块后，就不会再次实例化对象了。另外，`INSTANCE` 对象使用 `volatile` 关键字修饰，禁用了 CPU 的指令重排序优化，使得 `getInstance()` 返回的一定是一个初始化完成的对象。

```java
public class DoubleCheckedLockingSingleton {
    private static volatile DoubleCheckedLockingSingleton INSTANCE = null;

    private DoubleCheckedLockingSingleton() { }

    public static DoubleCheckedLockingSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
```

5. 利用内部类实现懒加载线程安全的单例模式，利用虚拟机加载外部类时并不会加载内部类的机制实现懒加载，内部类只有在获取单例时才被真正加载，同时利用虚拟机加载类的线程安全性保证了单例的构造函数只被执行一次。优点是代码简洁，缺点是引入了毫无用处的内部类。

```java
public class InnerClassSingleton {
    private InnerClassSingleton() { }

    public static InnerClassSingleton getInstance() {
        return InnerClass.INSTANCE;
    }

    private static class InnerClass {
        private static final InnerClassSingleton INSTANCE =
            new InnerClassSingleton();
    }
}
```

以上五种方法都是基于 Java 类实现的单例，它们都有一个共同的问题，就是在反序列化或者反射生成时不能保证单例性。例如下面的测试代码，利用反射构造单例对象：

```java
@Test
public void testSingletonByReflection() throws NoSuchMethodException {
    Class<Singleton> singletonClass = Singleton.class;
    Constructor<Singleton> constructor = singletonClass.getDeclaredConstructor();
    constructor.setAccessible(true);
    try {
        Singleton singleton1 = constructor.newInstance();
        Singleton singleton2 = constructor.newInstance();
        System.out.print(singleton1 == singleton2);
    } catch (Exception ignored) { }
}
```

从输出结果可以看到单例 `Singleton` 的 `private` 构造函数被调用了两次，输出结果为 `false`。再看一个用 Jackson 框架反序列化 `Singleton` 的例子：

```java
@Test
public void testSingletonByJackson() throws JsonProcessingException {
    String json = "{}";
    JsonMapper jsonMapper = new JsonMapper();
    Singleton singleton1 = jsonMapper.readValue(json, Singleton.class);
    Singleton singleton2 = jsonMapper.readValue(json, Singleton.class);
    System.out.print(singleton1 == singleton2);
}
```

同样可以看到单例 `Singleton` 的 `private` 构造函数被调用了两次，输出结果为 `false`。由此猜想，Jackson 内部应该是利用反射机制构造了 `Singleton` 类。

`Singleton` 类定义如下：

```java
public class Singleton {
    private static volatile Singleton INSTANCE = null;

    private Singleton() {
        System.out.println("Singleton constructor called");
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
```

6. 使用 `enum` 实现线程安全、懒加载且在反序列化后仍唯一的单例模式，由于虚拟机禁用了使用反射方式构造一个 `enum` 对象，因此使用 `enum` 实现的单例可以做到在反序列化后仍保持单例。

```java
public enum EnumSingleton {
    INSTANCE;

    EnumSingleton() {
        System.out.println("EnumSingleton constructor called");
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
```

仍然用上面类似的测试代码来检测基于 `Enum` 的单例在反射和反序列化时的表现。反射的例子如下：

```java
@Test
public void testEnumSingletonByReflection() throws NoSuchMethodException {
    Class<EnumSingleton> singletonClass = EnumSingleton.class
    // enum 唯一的构造函数是一个参数为 String 和 int 的构造函数
    Constructor<EnumSingleton> constructor = singletonClass.getDeclaredConstructor(
        String.class, int.class);
    constructor.setAccessible(true);
    try {
        EnumSingleton singleton1 = constructor.newInstance("INSTANCE", 0);
        EnumSingleton singleton2 = constructor.newInstance("INSTANCE", 0);
        System.out.print(singleton1 == singleton2);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

抛出如下异常：

```shell
java.lang.IllegalArgumentException: Cannot reflectively create enum objects
```

再来看使用 Jackson 反序列化的例子：

```java
@Test
public void testEnumSingletonByJackson() throws JsonProcessingException {
    String json = "\"INSTANCE\"";
    JsonMapper jsonMapper = new JsonMapper();
    EnumSingleton singleton1 = jsonMapper.readValue(json, EnumSingleton.class);
    EnumSingleton singleton2 = jsonMapper.readValue(json, EnumSingleton.class);
    System.out.print(singleton1 == singleton2);
}
```

输出如下：

```shell
EnumSingleton constructor called
true
```

从结果上看单例对象只被实例化了一次，通过源码知道 Jackson 其实是用字符串匹配的方式找到要反序列化出的 `EnumSingleton.INSTANCE` 对象的，因为这个对象是一个 `enum` 自然就仍然可以保证全局唯一性。

总结一下，Java 实现单例的方式有很多，通常单例对象在不需要序列化和反序列化的情况下，推荐使用 Double Checked Locking 的方式实现线程安全的单例，如果单例对象有序列化反序列化的需求，则可以考虑使用 `enum` 实现单例。