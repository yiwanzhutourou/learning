# Reflection

Reflection is commonly used by programs which require the ability to examine or modify the runtime behavior of applications running in the Java virtual machine.

## Drawbacks of Reflection

Reflection is powerful, but should not be used indiscriminately. If it is possible to perform an operation without using reflection, then it is preferable to avoid using it. The following concerns should be kept in mind when accessing code via reflection.

### Performance Overhead

Because reflection involves types that are dynamically resolved, certain Java virtual machine optimizations can not be performed. Consequently, reflective operations have slower performance than their non-reflective counterparts, and should be avoided in sections of code which are called frequently in performance-sensitive applications.

### Security Restrictions

Reflection requires a runtime permission which may not be present when running under a security manager. This is in an important consideration for code which has to run in a restricted security context, such as in an Applet.

### Exposure of Internals

Since reflection allows code to perform operations that would be illegal in non-reflective code, such as accessing `private` fields and methods, the use of reflection can result in unexpected side-effects, which may render code dysfunctional and may destroy portability. Reflective code breaks abstractions and therefore may change behavior with upgrades of the platform.

## Classes

### Class Object

The entry point for all reflection operations is [`java.lang.Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html). With the exception of [`java.lang.reflect.ReflectPermission`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/ReflectPermission.html), none of the classes in [`java.lang.reflect`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html) have public constructors. To get to these classes, it is necessary to invoke appropriate methods on [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html). There are several ways to get a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) depending on whether the code has access to an object, the name of class, a type, or an existing [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html).

#### Object.getClass()

If an instance of an object is available, then the simplest way to get its [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) is to invoke [`Object.getClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass--).

```java
import java.util.HashSet;
import java.util.Set;

Set<String> s = new HashSet<String>();
Class c = s.getClass();
```

#### The .class Syntax

If the type is available but there is no instance then it is possible to obtain a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) by appending `".class"` to the name of the type. This is also the easiest way to obtain the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) for a primitive type.

```java
boolean b;
Class c = b.getClass();   // compile-time error

Class c = boolean.class;  // correct
```

#### Class.forName()

If the fully-qualified name of a class is available, it is possible to get the corresponding [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) using the static method [`Class.forName()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#forName-java.lang.String-).

```java
Class cDoubleArray = Class.forName("[D");

Class cStringArray = Class.forName("[[Ljava.lang.String;");
```

#### Methods that Return Classes

There are several Reflection APIs which return classes but these may only be accessed if a [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) has already been obtained either directly or indirectly.

- [`Class.getSuperclass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getSuperclass--)

  Returns the super class for the given class.

- [`Class.getClasses()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getClasses--)

  Returns all the public classes, interfaces, and enums that are members of the class including inherited members.

- [`Class.getDeclaredClasses()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredClasses--)

  Returns all of the classes interfaces, and enums that are explicitly declared in this class.

- [`Class.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaringClass--)
  [`java.lang.reflect.Field.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getDeclaringClass--)
  [`java.lang.reflect.Method.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#getDeclaringClass--)
  [`java.lang.reflect.Constructor.getDeclaringClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#getDeclaringClass--)

  Returns the [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) in which these members were declared. [Anonymous Class Declarations](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.9.5) will not have a declaring class but will have an enclosing class.

- [`Class.getEnclosingClass()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getEnclosingClass--)

  Returns the immediately enclosing class of the class.

### Examining Class Modifiers and Types

A class may be declared with one or more modifiers which affect its runtime behavior:

- Access modifiers: `public`, `protected`, and `private`
- Modifier requiring override: `abstract`
- Modifier restricting to one instance: `static`
- Modifier prohibiting value modification: `final`
- Modifier forcing strict floating point behavior: `strictfp`
- Annotations

Not all modifiers are allowed on all classes, for example an interface cannot be `final` and an enum cannot be `abstract`. [`java.lang.reflect.Modifier`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Modifier.html) contains declarations for all possible modifiers. It also contains methods which may be used to decode the set of modifiers returned by [`Class.getModifiers()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getModifiers--).

> **Note:** Not all annotations are available via reflection. Only those which have a [`java.lang.annotation.RetentionPolicy`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html) of [`RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME) are accessible. Of the three annotations pre-defined in the language [`@Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html), [`@Override`](https://docs.oracle.com/javase/8/docs/api/java/lang/Override.html), and [`@SuppressWarnings`](https://docs.oracle.com/javase/8/docs/api/java/lang/SuppressWarnings.html) only [`@Deprecated`](https://docs.oracle.com/javase/8/docs/api/java/lang/Deprecated.html) is available at runtime.

### Discovering Class Members

There are two categories of methods provided in [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) for accessing fields, methods, and constructors: methods which enumerate these members and methods which search for particular members. Also there are distinct methods for accessing members declared directly on the class versus methods which search the superinterfaces and superclasses for inherited members. The following tables provide a summary of all the member-locating methods and their characteristics.

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API | List of members? | Inherited members? | Private members? |
| ------------------------------------------------------------ | ---------------- | ------------------ | ---------------- |
| [`getDeclaredField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredField-java.lang.String-) | no               | no                 | yes              |
| [`getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-) | no               | yes                | no               |
| [`getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--) | yes              | no                 | yes              |
| [`getFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--) | yes              | yes                | no               |

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API | List of members? | Inherited members? | Private members? |
| ------------------------------------------------------------ | ---------------- | ------------------ | ---------------- |
| [`getDeclaredMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethod-java.lang.String-java.lang.Class...-) | no               | no                 | yes              |
| [`getMethod()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-) | no               | yes                | no               |
| [`getDeclaredMethods()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethods--) | yes              | no                 | yes              |
| [`getMethods()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethods--) | yes              | yes                | no               |

| [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) API | List of members? | Inherited members? | Private members? |
| ------------------------------------------------------------ | ---------------- | ------------------ | ---------------- |
| [`getDeclaredConstructor()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructor-java.lang.Class...-) | no               | N/A                | yes              |
| [`getConstructor()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructor-java.lang.Class...-) | no               | N/A                | no               |
| [`getDeclaredConstructors()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructors--) | yes              | N/A                | yes              |
| [`getConstructors()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructors--) | yes              | N/A                | no               |

**Constructors are not inherited.**

## Members

Reflection defines an interface [`java.lang.reflect.Member`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Member.html) which is implemented by [`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html), [`java.lang.reflect.Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html), and [`java.lang.reflect.Constructor`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html) .

### Fields

A *field* is a class, interface, or enum with an associated value. Methods in the [`java.lang.reflect.Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) class can retrieve information about the field, such as its name, type, modifiers, and annotations. There are also methods which enable dynamic access and modification of the value of the field.

A field may be a class (instance) field, such as [`java.io.Reader.lock`](https://docs.oracle.com/javase/8/docs/api/java/io/Reader.html#lock) , a static field, such as [`java.lang.Integer.MAX_VALUE`](https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#MAX_VALUE) , or an enum constant, such as [`java.lang.Thread.State.WAITING`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.State.html#WAITING).

> **Note:** If a class object represents a class of arrays, then the internal form of the name consists of the name of the element type preceded by one or more '`[`' characters representing the depth of the array nesting. The encoding of element type names is as follows:
>
> | Element Type       |      | Encoding      |
> | ------------------ | ---- | ------------- |
> | boolean            |      | Z             |
> | byte               |      | B             |
> | char               |      | C             |
> | class or interface |      | L*classname*; |
> | double             |      | D             |
> | float              |      | F             |
> | int                |      | I             |
> | long               |      | J             |
> | short              |      | S             |

#### Retrieving and Parsing Field Modifiers

There are several modifiers that may be part of a field declaration:

- Access modifiers: `public`, `protected`, and `private`
- Field-specific modifiers governing runtime behavior: `transient` and `volatile`
- Modifier restricting to one instance: `static`
- Modifier prohibiting value modification: `final`
- Annotations

The method [`Field.getModifiers()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#getModifiers--) can be used to return the integer representing the set of declared modifiers for the field. The bits representing the modifiers in this integer are defined in [`java.lang.reflect.Modifier`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Modifier.html).

Notice that some fields are reported even though they are not declared in the original code. This is because the compiler will generate some *synthetic fields* which are needed during runtime. To test whether a field is synthetic, invoke [`Field.isSynthetic()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isSynthetic--). The set of synthetic fields is compiler-dependent; however commonly used fields include `this$0` for inner classes (i.e. nested classes that are not static member classes) to reference the outermost enclosing class and `$VALUES` used by enums to implement the implicitly defined static method `values()`. The names of synthetic class members are not specified and may not be the same in all compiler implementations or releases. These and other synthetic fields will be included in the array returned by [`Class.getDeclaredFields()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredFields--) but not identified by [`Class.getField()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-) since synthetic members are not typically `public`.

Because [`Field`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html) implements the interface [`java.lang.reflect.AnnotatedElement`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html), it is possible to retrieve any runtime annotation with [`java.lang.annotation.RetentionPolicy.RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME).

#### Getting and Setting Field Values

Given an instance of a class, it is possible to use reflection to set the values of fields in that class. This is typically done only in special circumstances when setting the values in the usual way is not possible. Because such access usually violates the design intentions of the class, it should be used with the utmost discretion.

Use of reflection can cause some runtime optimizations to be lost. For example, the following code is highly likely be optimized by a Java virtual machine:

```java
int x = 1;
x = 2;
x = 3;
```

Equivalent code using `Field.set*()` may not.

An access restriction exists which prevents `final` fields from being set after initialization of the class. However, `Field` is declared to extend [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) which provides the ability to suppress this check. If [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-) succeeds, then subsequent operations on this field value will not fail. This may have unexpected side-effects; for example, sometimes the original value will continue to be used by some sections of the application even though the value has been modified. [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-) will only succeed if the operation is allowed by the security context.

### Methods

A method declaration includes the name, modifiers, parameters, return type, and list of throwable exceptions. The [`java.lang.reflect.Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html) class provides a way to obtain this information.

#### Obtaining Names of Method Parameters

You can obtain the names of the formal parameters of any method or constructor with the method [`java.lang.reflect.Executable.getParameters`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Executable.html#getParameters--). (The classes [`Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html) and [`Constructor`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Executable.html) extend the class [`Executable`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Executable.html) and therefore inherit the method `Executable.getParameters`.) However, `.class` files do not store formal parameter names by default. This is because many tools that produce and consume class files may not expect the larger static and dynamic footprint of `.class` files that contain parameter names. In particular, these tools would have to handle larger `.class` files, and the Java Virtual Machine (JVM) would use more memory. In addition, some parameter names, such as `secret` or `password`, may expose information about security-sensitive methods.

To store formal parameter names in a particular `.class` file, and thus enable the Reflection API to retrieve formal parameter names, compile the source file with the `-parameters` option to the `javac` compiler.

**Methods in `Paramter`:**

- [`getName`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Parameter.html#getName--): Returns the name of the parameter. If the parameter's name is present, then this method returns the name provided by the `.class` file. Otherwise, this method synthesizes a name of the form `arg*N*`, where `*N*` is the index of the parameter in the descriptor of the method that declares the parameter.

- [`getModifiers`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Parameter.html#getModifiers--) : Returns an integer that represents various characteristics that the formal parameter possesses. This value is the sum of the following values, if applicable to the formal parameter:

  | Value (in decimal) | Value (in hexadecimal | Description                                                  |
  | ------------------ | --------------------- | ------------------------------------------------------------ |
  | 16                 | 0x0010                | The formal parameter is declared `final`                     |
  | 4096               | 0x1000                | The formal parameter is synthetic. Alternatively, you can invoke the method `isSynthetic`. |
  | 32768              | 0x8000                | The parameter is implicitly declared in source code. Alternatively, you can invoke the method `isImplicit` |

- [`isImplicit`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Parameter.html#isImplicit--): Returns `true` if this parameter is implicitly declared in source code.

- [`isNamePresent`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Parameter.html#isNamePresent--): Returns `true` if the parameter has a name according to the `.class` file.

- [`isSynthetic`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Parameter.html#isSynthetic--): Returns `true` if this parameter is neither implicitly nor explicitly declared in source code.

> **注：**`Parameter` 类的很多方法，例如 `getName`、`getModifiers`、`isImplicit`、`isSynthetic` 等都是拿不到源码中真正的情况的，因为在编译的过程中，为了减小 `.class` 文件的大小，很多参数的信息，例如名称、修饰符等，都被抹去了，为了保留这些信息，必须增加 `-parameters` 编译选项。

> **注：**`isImplicit()` 返回 `true` 的例子为非静态内部类的默认构造函数的参数，编译器为非静态内部类会自动生成一个类似如下代码的构造函数，其中的参数 `this$0` 就是 "implicitly declared" 参数。
>
> ```java
> public class OuterClass {
>     public class InnerClass {
>         final OuterClass parent;
>         InnerClass(final OuterClass this$0) {
>             parent = this$0; 
>         }
>     }
> }
> ```
>
>  `isSynthetic()` 返回 `true` 的例子为编译器为枚举类自动生成的构造函数和方法的参数，其中构造函数也被认为是 synthetic 的，而既不是 explicit 的，也不是 implicit 的，因此构造函数的参数也是 synthetic 的。（这一点不太好理解，可能是因为默认构造函数是 [Java Language Specification](https://docs.oracle.com/javase/specs/) 的一个规范，而不同的编译器对于如何生成枚举类的构造函数可以有不同的实现的，因此前者被认为是 implicit 的，隐式的，后者被认为是 synthetic 的，生成的。）对于一个如下代码所示的枚举类：
>
> ```java
> enum Colors {
>     RED, WHITE
> }
> ```
>
> 编译器可能会自动生成如下的代码：
>
> ```java
> final class Colors extends java.lang.Enum<Colors> {
>     public final static Colors RED = new Colors("RED", 0);
>     public final static Colors BLUE = new Colors("WHITE", 1);
>  
>     private final static values = new Colors[]{ RED, BLUE };
>  
>     private Colors(String name, int ordinal) {
>         super(name, ordinal);
>     }
>  
>     public static Colors[] values(){
>         return values;
>     }
>  
>     public static Colors valueOf(String name){
>         return (Colors)java.lang.Enum.valueOf(Colors.class, name);
>     }
> }
> ```
>
> 其中的构造函数 `Colors(String name, int ordinal)` 以及方法 `Colors values()` 和 `Colors valueOf(String name)` 都是生成的，它们的参数 `isSynthetic()` 都返回 `true`，而 `isImplicit` 都返回 `false`。我觉得这部分内容仅理解就可以，实际开发中很难用到，毕竟如果不是特殊的项目，编译时是不会带上 `-parameters` 选项的。

#### Retrieving and Parsing Method Modifiers

There are several modifiers that may be part of a method declaration:

- Access modifiers: `public`, `protected`, and `private`
- Modifier restricting to one instance: `static`
- Modifier prohibiting value modification: `final`
- Modifier requiring override: `abstract`
- Modifier preventing reentrancy: `synchronized`
- Modifier indicating implementation in another programming language: `native`
- Modifier forcing strict floating point behavior: `strictfp`
- Annotations

[`Method`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html) implements [`java.lang.reflect.AnnotatedElement`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html). Thus any runtime annotations with [`java.lang.annotation.RetentionPolicy.RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME) may be retrieved.

**Methods in `Method`：**

- [`isVarArgs`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#isVarArgs--): Returns `true` if this method is declared to take a variable number of arguments.

- [`isSynthetic`](<https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#isSynthetic-->): Returns `true` if this method is synthetic defined by the  [Java Language Specification](https://docs.oracle.com/javase/specs/).
- [`isBridge`](<https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#isBridge-->): Returns `true` if this method is a bridge method.

> **注：**bridge method 一般是编译器在实现带泛型的接口的类中自动生成的方法，因为泛型的类型在编译的时候会被擦除，而源码中实现带泛型的接口的类的实现方法肯定是带有类型的，这就造成如果编译器不自动生成一个方法，那么实际上是编译不过的。例如 `String` 类实现了 `Comparable<String>` 接口，代码里写的实现方法是 `compareTo(String)`，编译器会自动生成一个 `compareTo(Object)` 方法，后者就是一个 brige method。

#### Invoking Methods

Reflection provides a means for invoking methods on a class. Typically, this would only be necessary if it is not possible to cast an instance of the class to the desired type in non-reflective code. Methods are invoked with [`java.lang.reflect.Method.invoke()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#invoke-java.lang.Object-java.lang.Object...-). The first argument is the object instance on which this particular method is to be invoked. (If the method is `static`, the first argument should be `null`.) Subsequent arguments are the method's parameters. If the underlying method throws an exception, it will be wrapped by an [`java.lang.reflect.InvocationTargetException`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/InvocationTargetException.html). The method's original exception may be retrieved using the exception chaining mechanism's [`InvocationTargetException.getCause()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/InvocationTargetException.html#getCause--) method.

> **Tip:** An access restriction exists which prevents reflective invocation of methods which normally would not be accessible via direct invocation. (This includes---but is not limited to---`private` methods in a separate class and public methods in a separate private class.) However, `Method` is declared to extend [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) which provides the ability to suppress this check via [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-). If it succeeds, then subsequent invocations of this method object will not fail due to this problem.

> **Tip:** If an [`InvocationTargetException`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/InvocationTargetException.html) is thrown, the method was invoked. Diagnosis of the problem would be the same as if the method was called directly and threw the exception that is retrieved by [`getCause()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/InvocationTargetException.html#getCause--). This exception does not indicate a problem with the reflection package or its usage.

### Constructors

A *constructor* is used in the creation of an object that is an instance of a class. Typically it performs operations required to initialize the class before methods are invoked or fields are accessed. Constructors are never inherited.

A constructor declaration includes the name, modifiers, parameters, and list of throwable exceptions. The [`java.lang.reflect.Constructor`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html) class provides a way to obtain this information.

#### Retrieving and Parsing Constructor Modifiers

Because of the role of constructors in the language, fewer modifiers are meaningful than for methods:

- Access modifiers: `public`, `protected`, and `private`
- Annotations

Constructors implement [`java.lang.reflect.AnnotatedElement`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AnnotatedElement.html), which provides methods to retrieve runtime annotations with [`java.lang.annotation.RetentionPolicy.RUNTIME`](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/RetentionPolicy.html#RUNTIME).

#### Creating New Class Instances

There are two reflective methods for creating instances of classes: [`java.lang.reflect.Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) and [`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--). The former is preferred because:

- [`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--) can only invoke the zero-argument constructor, while [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) may invoke any constructor, regardless of the number of parameters.
- [`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--) throws any exception thrown by the constructor, regardless of whether it is checked or unchecked. [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) always wraps the thrown exception with an [`InvocationTargetException`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/InvocationTargetException.html).
- [`Class.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#newInstance--) requires that the constructor be visible; [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) may invoke `private` constructors under certain circumstances.

> **Tip:** An important difference between `new` and [`Constructor.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Constructor.html#newInstance-java.lang.Object...-) is that `new` performs method argument type checking, boxing, and method resolution. None of these occur in reflection, where explicit choices must be made.

> **Tip:** An access restriction exists which prevents reflective invocation of constructors which normally would not be accessible via direct invocation. (This includes, but is not limited to, private constructors in a separate class and public constructors in a separate private class.) However, `Constructor` is declared to extend [`AccessibleObject`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html) which provides the ability to suppress this check via [`AccessibleObject.setAccessible()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible-boolean-).

## Arrays and Enumerated Types

From the Java virtual machine's perspective, arrays and enumerated types (or enums) are just classes. Many of the methods in [`Class`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html) may be used on them. Reflection provides a few specific APIs for arrays and enums.

### Arrays

Arrays are implemented in the Java virtual machine. The only methods on arrays are those inherited from [`Object`](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html). The length of an array is not part of its type; arrays have a `length` field which is accessible via [`java.lang.reflect.Array.getLength()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#getLength-java.lang.Object-). Reflection provides methods for accessing array types and array component types, creating new arrays, and retrieving and setting array component values.

Array types may be identified by invoking [`Class.isArray()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#isArray--). [`Class.getComponentType`](<https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getComponentType-->) returns the `Class` representing the component type of an array. If this class does not represent an array class this method returns null.

#### Creating New Arrays

Just as in non-reflective code, reflection supports the ability to dynamically create arrays of arbitrary type and dimensions via [`java.lang.reflect.Array.newInstance()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#newInstance-java.lang.Class-int-).

#### Getting and Setting Arrays and Their Components

Just as in non-reflective code, an array field may be set or retrieved in its entirety or component by component. To set the entire array at once, use [`java.lang.reflect.Field.set(Object obj, Object value)`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#set-java.lang.Object-java.lang.Object-). To retrieve the entire array, use [`Field.get(Object)`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#get-java.lang.Object-). Individual components can be set or retrieved using methods in [`java.lang.reflect.Array`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html).

[`Array`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html) provides methods of the form `set*Foo*()` and `get*Foo*()` for setting and getting components of any primitive type. For example, the component of an `int` array may be set with [`Array.setInt(Object array, int index, int value)`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#setInt-java.lang.Objectint-int-) and may be retrieved with [`Array.getInt(Object array, int index)`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#getInt-java.lang.Object-int-).

These methods support automatic *widening* of data types. Therefore, [`Array.getShort()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#getShort-java.lang.Object-int-) may be used to set the values of an `int` array since a 16-bit `short` may be widened to a 32-bit `int` without loss of data; on the other hand, invoking [`Array.setLong()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#setLong-java.lang.Object-int-long-) on an array of `int` will cause an [`IllegalArgumentException`](https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalArgumentException.html) to be thrown because a 64-bit `long` can not be narrowed to for storage in a 32-bit `int` with out loss of information. This is true regardless of whether the actual values being passed could be accurately represented in the target data type.

The components of arrays of reference types (including arrays of arrays) are set and retrieved using [`Array.set(Object array, int index, int value)`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#set-java.lang.Object-int-int-) and [`Array.get(Object array, int index)`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html#get-java.lang.Object-int-).

> **Tip:** When using reflection to set or get an array component, the compiler does not have an opportunity to perform boxing. It can only convert types that are related as described by the specification for [`Class.isAssignableFrom()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#isAssignableFrom-java.lang.Class-).

### Enumerated Types

An *enum* is a language construct that is used to define type-safe enumerations which can be used when a fixed set of named values is desired. All enums implicitly extend [`java.lang.Enum`](https://docs.oracle.com/javase/8/docs/api/java/lang/Enum.html). Enums may contain one or more *enum constants*, which define unique instances of the enum type. An enum declaration defines an *enum type* which is very similar to a class in that it may have members such as fields, methods, and constructors (with some restrictions).

Since enums are classes, reflection has no need to define an explicit `java.lang.reflect.Enum` class. The only Reflection APIs that are specific to enums are [`Class.isEnum()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#isEnum--), [`Class.getEnumConstants()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getEnumConstants--), and [`java.lang.reflect.Field.isEnumConstant()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isEnumConstant--). Most reflective operations involving enums are the same as any other class or member.

[`Class.isEnum()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#isEnum--) indicates whether this class represents an enum type.

[`Class.getEnumConstants()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getEnumConstants--) retrieves the list of enum constants defined by the enum in the order they're declared.

[`java.lang.reflect.Field.isEnumConstant()`](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#isEnumConstant--) indicates whether this field represents an element of an enumerated type.

> **Tip:** It is a compile-time error to attempt to explicitly instantiate an enum because that would prevent the defined enum constants from being unique. This restriction is also enforced in reflective code. Code which attempts to instantiate classes using their default constructors should invoke [`Class.isEnum()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#isEnum--) first to determine if the class is an enum.