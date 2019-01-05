package com.yd.java.jdk.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人基于HashMap 实现 Lru Cache
 * <p>
 * 原理：尾插法
 * 首先我们采用的是尾插法，也就是新插入的元素或者命中的元素往尾部移动，头部的元素即是最近最少使用。
 * </p>
 */
public class MyLru01<K, V> {

    private int maxSize;
    private Map<K, Entry<K, V>> map;
    private Entry head;
    private Entry tail;

    public MyLru01(int maxSize) {
        this.maxSize = maxSize;
        map = new HashMap<>();
    }

    public static void main(String[] args) {
        MyLru01<String, String> map = new MyLru01<>(5);
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        System.out.println(map.toString());

        map.put("6", "6");
        map.get("2");
        map.put("7", "7");
        map.get("4");

        System.out.println(map.toString());
    }

    /**
     * 把key，value包装成Entry节点。调用afterEntryInsertion(entry)方法，把Entry节点移动到双向链表尾部。
     * 然后将key,Entry放入到HashMap中。如果map中元素的数量大于maxSize，则删除双向链表中的头结点(头结点所在的元素就是最近最少使用的元素)。
     * 首先在map中删除head.key对应着的元素，然后调用 afterEntryRemoval(head)，在双向链表中删除头节点。
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        Entry<K, V> entry = new Entry<>();
        entry.key = key;
        entry.value = value;

        afterEntryInsertion(entry);
        map.put(key, entry);

        if (map.size() > maxSize) {
            map.remove(head.key);
            afterEntryRemoval(head);
        }
    }

    /**
     * 如果双向链表head节点为空的话，证明双向链表为空。
     * 那么我们把新插入的元素置为head节点和tail节点。
     * 否则我们把插入当前节点至尾部。这里是怎么插入呢？tail节点之前是尾部节点，现在突然要插入一个节点(entry节点)。
     * 那么tail节点再也不能占据尾部的位置，我们把置它为pre节点。pre节点也就是新的tail节点(也就是entry节点)的前一个节点。
     * entry的先驱节点指向pre，pre节点的后继节点指向entry，这样就完成了尾插入。
     *
     * @param entry
     */
    private void afterEntryInsertion(Entry<K, V> entry) {
        if (entry != null) {
            if (head == null) {
                head = entry;
                tail = head;
                return;
            }

            if (tail != entry) {
                Entry<K, V> pred = tail;
                entry.before = pred;
                tail = entry;
                pred.after = entry;
            }
        }
    }

    /**
     * 我们通过get()方法命中了entry节点。那么我们怎么把entry节点移动至双向链表中的尾部呢？如果当前节点已位于尾部，那么我们什么也不做。
     * 如果当前节点不在尾部，和上面操作一样首先获取它的先驱节点b和后继节点a。然后把先驱节点和后继节点都置为null，方便后续操作。
     * 如果b节点等于null，那么移动entry节点至尾部后，head节点应该为a节点。
     * 如果b节点不等于null，那么b的后继节点应该指向a。
     * 如果a节点等于null，那么新的尾部节点的前一个节点应该为b。
     * 如果a节点不等于null，那么a的先驱节点应该指向b。
     * 如果last节点(也就是新尾部节点的前一个节点)等于null的话，说明head节点应该为p节点。
     * 如果last节点不等于null的话，我们把p的先驱节点指向last，last的后继节点指向p。最后新的尾部节点就是p。
     *
     * @param entry
     */
    private void afterEntryAccess(Entry<K, V> entry) {
        Entry<K, V> last;

        if ((last = tail) != entry) {
            Entry<K, V> p = entry, b = p.before, a = p.after;
            p.before = p.after = null;

            if (b == null) {
                head = a;
            } else {
                b.after = a;
            }

            if (a == null) {
                last = b;
            } else {
                a.before = b;
            }

            if (last == null) {
                head = p;
            } else {
                p.before = last;
                last.after = p;
            }

            tail = p;
        }
    }

    private Entry<K, V> getEntry(K key) {
        return map.get(key);
    }

    public V get(K key) {
        Entry<K, V> entry = this.getEntry(key);

        if (entry == null) {
            return null;
        }
        afterEntryAccess(entry);
        return entry.value;
    }

    public void remove(K key) {
        Entry<K, V> entry = this.getEntry(key);
        afterEntryRemoval(entry);
    }

    /**
     * 我们是怎么在双向链表中删除一个节点呢？现在要删除的节点是entry节点。
     * 我们首先获取它的先驱节点b和后继节点a。如果b等于null，那么删除entry节点后，head节点应该为a。
     * 如果b不等于null，b的后继节点应该指向a。同样如果a等于null，那么删除entry节点后，tail节点应该为b。如果a不等于null，a的先驱节点应该指向b。
     * 这样就完成删除操作，如果还没明白的话，自己拿个笔画张图就差不多了。
     *
     * @param entry
     */
    private void afterEntryRemoval(Entry<K, V> entry) {
        if (entry != null) {
            Entry<K, V> p = entry, b = p.before, a = p.after;
            p.before = p.after = null;

            if (b == null) {
                head = a;
            } else {
                b.after = a;
            }

            if (a == null) {
                tail = b;
            } else {
                a.before = b;
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Entry<K, V> entry = head;

        while (entry != null) {
            sb.append(String.format("%s:%s", entry.key, entry.value));
            sb.append(" ");
            entry = entry.after;
        }

        return sb.toString();
    }

    /**
     * 双向链表结构
     */
    static final class Entry<K, V> {
        private Entry before;
        private Entry after;
        private K key;
        private V value;
    }
}