package com.yd.guava.cache;

import com.google.common.base.Function;
import com.google.common.collect.*;

import java.util.*;

/**
 * @author Yd on  2018-01-15
 * @Description：
 **/
public class GoogleGuavaCache {

    public static void main(String[] args) {


    }

    public static void CollectionCreate() {
        Map<String, Map<String, String>> map = Maps.newHashMap();

        List<List<Map<String, String>>> list = Lists.newArrayList();

        //1,简化集合的创建
        List<Person> personList = Lists.newLinkedList();
        Set<Person> personSet = Sets.newHashSet();
        Map<String, Person> personMap = Maps.newHashMap();
        Integer[] intArrays = ObjectArrays.newArray(Integer.class, 10);
    }

    public static void CollectionInit() {
        Set<String> set = Sets.newHashSet("one", "two", "three");

        List<String> list = Lists.newArrayList("one", "two", "three");

        Map<String, String> map = ImmutableMap.of("ON", "TRUE", "OFF", "FALSE");
        //2,简化集合的初始化
        List<Person> personList2 = Lists.newArrayList(new Person("46546", 20), new Person("46546", 20));
        Set<Person> personSet2 = Sets.newHashSet(new Person("46546", 20), new Person("46546", 20));
        Map<String, Person> personMap2 = ImmutableMap.of("hello", new Person("46546", 20), "fuck", new Person("46546", 20));
    }

    public static void CollectionImmutable() {
        //2,简化集合的初始化
        List<Person> personList2 = Lists.newArrayList(new Person("46546", 20), new Person("46546", 20));
        Set<Person> personSet2 = Sets.newHashSet(new Person("46546", 20), new Person("46546", 20));
        Map<String, Person> personMap2 = ImmutableMap.of("hello", new Person("46546", 20), "fuck", new Person("46546", 20));

        Set<Integer> data = new HashSet<Integer>();

        data.addAll(Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80));

        Set<Integer> fixedData = Collections.unmodifiableSet(data); // fixedData - [50, 70, 80, 20, 40, 10, 60, 30]

        data.add(90); // fixedData - [50, 70, 80, 20, 40, 10, 90, 60, 30]

        //如何创建不可变的集合：
        ImmutableSet<Integer> numbers = ImmutableSet.of(10, 20, 30, 40, 50);

        // 使用copyOf方法
        Set<Integer> another = ImmutableSet.copyOf(numbers);

        //使用Builder方法
        ImmutableSet<Integer> numbers2 = ImmutableSet.<Integer>builder().addAll(numbers).add(60).add(70).add(80).build();

        //3,创建不可变的集合
        ImmutableList<Person> personImmutableList =
                ImmutableList.of(new Person("46546", 20), new Person("46546", 20));

        ImmutableSet<Person> personImmutableSet = ImmutableSet.copyOf(personSet2);

        ImmutableMap<String, Person> personImmutableMap = ImmutableMap.<String, Person>builder()
                .put("hell", new Person("46546", 20)).putAll(personMap2).build();
    }

    //不是集合，可以增加重复的元素，并且可以统计出重复元素的个数，例子如下：
    public static void testMulitiSet() {
        Multiset<Integer> multiSet = HashMultiset.create();
        multiSet.add(10);
        multiSet.add(30);
        multiSet.add(30);
        multiSet.add(40);

        System.out.println(multiSet.count(30)); // 2
        System.out.println(multiSet.size());    //4
    }

    //相当于有两个key的map，不多解释
    public static void testTable() {
        Table<Integer, Integer, Person> personTable = HashBasedTable.create();
        personTable.put(1, 20, new Person("46546", 20));
        personTable.put(0, 30, new Person("46546", 20));
        personTable.put(0, 25, new Person("46546", 22));
        personTable.put(1, 50, new Person("46546", 22));
        personTable.put(0, 27, new Person("46546", 22));
        personTable.put(1, 29, new Person("46546", 22));
        personTable.put(0, 33, new Person("46546", 20));
        personTable.put(1, 66, new Person("46546", 20));

        //1,得到行集合
        Map<Integer, Person> rowMap = personTable.row(0);
        int maxAge = Collections.max(rowMap.keySet());
    }

    //    是一个一一映射，可以通过key得到value，也可以通过value得到key；
    public static void testBitMap() {
        //双向map
        BiMap<Integer, String> biMap = HashBiMap.create();

        biMap.put(1, "hello");
        biMap.put(2, "helloa");
        biMap.put(3, "world");
        biMap.put(4, "worldb");
        biMap.put(5, "my");
        biMap.put(6, "myc");

        int value = biMap.inverse().get("my");
        System.out.println("my --" + value);
    }

    //    ClassToInstanceMap<Number> numberDefaults = MutableClassToInstanceMap.create();
    //numberDefaults.putInstance(Integer.class, Integer.valueOf(0));
    //从技术上来说，ClassToInstanceMap<B> 实现了Map<Class<? extends B>, B>，或者说，这是一个从B的子类到B对象的映射，这可能使得ClassToInstanceMap的泛型轻度混乱，但是只要记住B总是Map的上层绑定类型，通常来说B只是一个对象。
    //guava提供了有用的实现， MutableClassToInstanceMap 和 ImmutableClassToInstanceMap.
    // 重点：像其他的Map<Class,Object>,ClassToInstanceMap 含有的原生类型的项目，一个原生类型和他的相应的包装类可以映射到不同的值；
    private static void testClass() {
        ClassToInstanceMap<Person> classToInstanceMap = MutableClassToInstanceMap.create();

        Person person = new Person("46546", 20);
        classToInstanceMap.putInstance(Person.class, person);

        // System.out.println("string:"+classToInstanceMap.getInstance(String.class));
        // System.out.println("integer:" + classToInstanceMap.getInstance(Integer.class));

        Person person1 = classToInstanceMap.getInstance(Person.class);

    }

    //    转换一个集合为另外一个集合；
    public static ImmutableMultiset<String> testTransform() {
        List<Person> personList = Lists.newArrayList(new Person("46546", 20),
                new Person("46546", 21),
                new Person("46546", 22),
                new Person("46546", 23),
                new Person("46546", 24),
                new Person("46546", 26),
                new Person("46546", 25));

        return ImmutableMultiset.copyOf(Lists.transform(personList, new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return input.getName();
            }
        }));
    }

    //guava一份非常灵活的比较类，可以被用来操作，扩展，当作比较器，排序提供了集合排序的很多控制；
    public static void testOrdering() {
        List numbers = Lists.newArrayList(30, 20, 60, 80, 10);

        Ordering.natural().sortedCopy(numbers); //10,20,30,60,80
        Ordering.natural().reverse().sortedCopy(numbers); //80,60,30,20,10
        Ordering.natural().min(numbers); //10
        Ordering.natural().max(numbers); //80
        Lists.newArrayList(30, 20, 60, 80, null, 10);
        Ordering.natural().nullsLast().sortedCopy(numbers); //10, 20,30,60,80,null
        Ordering.natural().nullsFirst().sortedCopy(numbers); //null,10,20,30,60,80

        List<Person> personList = Lists.newArrayList(
                new Person("46546", 21),
                new Person("46546", 22),
                new Person("46546", 23),
                new Person("46546", 24),
                new Person("46546", 26),
                new Person("46546", 25));

        Ordering<Person> byAge = new Ordering<Person>() {
            @Override
            public int compare(Person left, Person right) {
                return right.getId() - left.getId();
            }
        };

        for (Person p : byAge.immutableSortedCopy(personList)) {
            System.out.println(p);
        }
    }
}
