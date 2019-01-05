package com.yd.java.concurrency.atomicV;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Michael-scott-1996 非阻塞队列算法中的插入
 * @author Yd on  2018-05-12
 * @description
 **/
public class LinkedQueue<E> {

    private final  Node<E> dummy = new Node<E>(null ,null);
    private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item){
        Node<E> newNode = new Node<E>(item,null);
        while (true){
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail ==tail.get()){
                if (tailNext!=null){
                    //队列处于静止状态，推进尾节点
                    tail.compareAndSet(curTail,tailNext);
                }else {
                    //队列处于静止状态，尝试插入新节点
                    if (curTail.next.compareAndSet(null,newNode)){
                        //插入成功，尝试推进尾节点
                        tail.compareAndSet(curTail,newNode);
                        return  true;
                    }
                }
            }
        }
    }

    private static class Node<E>{
        final E item;
        final AtomicReference<Node<E>> next;
        public Node(E item,Node<E> next){
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }
}
