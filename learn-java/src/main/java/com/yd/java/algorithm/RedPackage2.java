package com.yd.java.algorithm;

import java.math.BigDecimal;

/**
 * QA：
 * 1、红包的金额什么时候算？
 * 金额是拆的时候实时算出来，不是预先分配的，采用的是纯内存计算，不需要预算空间存储。 采取实时计算金额的考虑：预算需要占存储，实时效率很高，预算才效率低。
 * 2、实时性：为什么明明抢到红包，点开后发现没有？
 * 答：2014年的红包一点开就知道金额，分两次操作，先抢到金额，然后再转账。
 * 2015年的红包的拆和抢是分离的，需要点两次，因此会出现抢到红包了，但点开后告知红包已经被领完的状况。进入到第一个页面不代表抢到，只表示当时红包还有。
 * 3、分配：红包里的金额怎么算？为什么出现各个红包金额相差很大？
 * 答：随机，额度在0.01和(剩余平均值2)之间。
 * 例如：发100块钱，总共10个红包，那么平均值是10块钱一个，那么发出来的红包的额度在0.01元～20元之间波动。
 * 当前面3个红包总共被领了40块钱时，剩下60块钱，总共7个红包，那么这7个红包的额度在：0.01～（60/72）=17.14之间。
 * 注意：这里的算法是每被抢一个后，剩下的会再次执行上面的这样的算法
 * 4、红包的设计
 * 微信从财付通拉取金额数据过来，生成个数/红包类型/金额放到redis集群里，app端将红包ID的请求放入请求队列中，
 * 如果发现超过红包的个数，直接返回。根据红包的裸祭(逻辑)处理成功得到令牌请求，则由财付通进行一致性调用，
 * 通过像比特币一样，两边保存交易记录，交易后交给第三方服务审计，如果交易过程中出现不一致就强制回归。
 *
 * @author created by zouyd on 2019-07-14 16:26
 */
public class RedPackage2 {
    int remainSize;
    BigDecimal remainMoney;

    public static BigDecimal getRandomMoney(RedPackage2 _redPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_redPackage.remainSize == 1) {
            _redPackage.remainSize--;
            return _redPackage.remainMoney.setScale(2, BigDecimal.ROUND_DOWN);
        }

        BigDecimal random = BigDecimal.valueOf(Math.random());
        BigDecimal min = BigDecimal.valueOf(0.01);

        BigDecimal halfRemainSize = BigDecimal.valueOf(_redPackage.remainSize).divide(new BigDecimal(2), BigDecimal.ROUND_UP);
        BigDecimal max1 = _redPackage.remainMoney.divide(halfRemainSize, BigDecimal.ROUND_DOWN);
        BigDecimal minRemainAmount = min.multiply(BigDecimal.valueOf(_redPackage.remainSize - 1)).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal max2 = _redPackage.remainMoney.subtract(minRemainAmount);
        BigDecimal max = (max1.compareTo(max2) < 0) ? max1 : max2;

        BigDecimal money = random.multiply(max).setScale(2, BigDecimal.ROUND_DOWN);
        money = money.compareTo(min) < 0 ? min : money;

        _redPackage.remainSize--;
        _redPackage.remainMoney = _redPackage.remainMoney.subtract(money).setScale(2, BigDecimal.ROUND_DOWN);
        ;
        return money;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            RedPackage2 moneyPackage = new RedPackage2();
            moneyPackage.remainMoney = BigDecimal.valueOf(100);
            moneyPackage.remainSize = 5;

            while (moneyPackage.remainSize != 0) {
                System.out.print(getRandomMoney(moneyPackage) + "   ");
            }

            System.out.println();
        }
    }
}
