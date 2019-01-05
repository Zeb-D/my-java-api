package com.yd.java.concurrency.atomicV;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用CAS避免多元的不变约束
 *
 * @author Yd on  2018-05-12
 * @description
 **/
public class CasNumberRange {

    private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));

    public int getLower() {
        return values.get().lower;
    }

    public void setLower(int i) {
        while (true) {
            IntPair oldV = values.get();
            if (i > oldV.upper)
                throw new RuntimeException("Can't set lower to" + i + " > upper");
            IntPair newV = new IntPair(i, oldV.upper);
            if (values.compareAndSet(oldV, newV))
                return;
        }
    }

    public int getUpper() {
        return values.get().upper;
    }

    public void setUpper(int i) {
        while (true) {
            IntPair oldV = values.get();
            if (i < oldV.lower)
                throw new RuntimeException("Can't set upper to" + i + " < lower");
            IntPair newV = new IntPair(oldV.lower, i);
            if (values.compareAndSet(oldV, newV))
                return;
        }
    }


    private static class IntPair {
        //不变约束：lower<=upper
        final int lower;
        final int upper;

        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

}
