package com.yd.java.algorithm;

/**
 * @author Yd on 2018/4/20
 * @description 分类算法——贝叶斯算法
 *  根据已分类的样本信息获得一组特征值的概率， 输入某个特征值，结合分类模型，就可以判断其分类
 *  公式：P(A∩B) = P(A)*P(B|A)=P(B)*P(A|B)
 *       P(B|A) = P(A|B)*P(B) / P(A)
 *  示例：目前有两个箱子，1箱子红白球个20个，2箱子10白30红；现在随机挑一个箱子，取出一个红色球，问这个球来自1箱子的概率是多少？
 */
public class Bayes {
}
