package com.youdushufang;

import com.youdushufang.generic.Animal;
import com.youdushufang.generic.Cat;
import com.youdushufang.generic.Dog;
import com.youdushufang.generic.Wrapper;
import org.junit.jupiter.api.Test;

class GenericTest {

    @Test
    void testExtendsAndSuper() {
        Wrapper<Cat> catWrapper = new Wrapper<>();
        catWrapper.set(new Cat("cat1"));
        Wrapper<Dog> dogWrapper = new Wrapper<>();
        dogWrapper.set(new Dog("dog1"));
        Wrapper<Animal> animalWrapper = new Wrapper<>();
        animalWrapper.set(new Animal("animal1"));

        // 通配符 ? 等价于 ? extends Object
        Wrapper<?> wildcardWrapper1 = catWrapper;
        Object animal1 = wildcardWrapper1.get();
        System.out.println(animal1);
        Wrapper<?> wildcardWrapper2 = dogWrapper;
        Object animal2 = wildcardWrapper2.get();
        System.out.println(animal2);
        Wrapper<?> wildcardWrapper3 = animalWrapper;
        Object animal3 = wildcardWrapper3.get();
        System.out.println(animal3);

        System.out.println();

        // extends，申明为 <? extends V> 的类，其中泛型的上界是 V，也就是
        // 通配 V 以及 V 的所有子类，因此这样的类不能写入任何数据，因为 set 函数
        // 需要传入类型 V 的对象，任何情况下都有可能出现类型不匹配
        Wrapper<? extends Animal> extendsAnimalWrapper1 = catWrapper;
        Animal animal4 = extendsAnimalWrapper1.get();
        System.out.println(animal4);
        Wrapper<? extends Animal> extendsAnimalWrapper2 = dogWrapper;
        Animal animal5 = extendsAnimalWrapper2.get();
        System.out.println(animal5);
        Wrapper<? extends Animal> extendsAnimalWrapper3 = animalWrapper;
        Animal animal6 = extendsAnimalWrapper3.get();
        System.out.println(animal6);

        System.out.println();

        // super，申明为 <? super V> 的类，其中泛型的下界是 V，也就是
        // 通配 V 以及 V 的所有父类，因此这样的类可以写入类型为 V 的数据，
        // 因为任何情况下 set 函数要求的 V 都一定是 V 或者是 V 的父类，
        // 但是读取出的类型是无法确定的，只能申明为 Object（Wrapper 类
        // 定义泛型是没有指定上界，因此上界是默认的 Object 类）
        Wrapper<? super Animal> superAnimalWrapper = animalWrapper;
        superAnimalWrapper.set(new Animal("animal2"));
        Object animal7 = superAnimalWrapper.get();
        System.out.println(animal7);
        Wrapper<? super Cat> superCatAnimalWrapper = animalWrapper;
        superCatAnimalWrapper.set(new Cat("cat2"));
        Object animal8 = superCatAnimalWrapper.get();
        System.out.println(animal8);

        System.out.println();

        // PECS
        Wrapper<Cat> from = new Wrapper<>();
        from.set(new Cat("cat3"));
        Wrapper<Animal> to = new Wrapper<>();
        Wrapper.copy(from, to);
        System.out.println(to.get());
    }
}
