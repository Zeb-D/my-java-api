package com.yd.common.util.format;

import java.text.ChoiceFormat;
import java.text.DecimalFormat;

/**
 * @author Yd on  2018-01-27
 * @Description：
 **/
public class Directory {

    public static void main(String[] args) {
        DecimalFormat format1 = new DecimalFormat("#\u2030");
        System.out.println(format1.format(0.3345));//输出334‰

        double[] limits = { 3, 4, 5, 6, 7, 8, 9 };
        String[] formats = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
        ChoiceFormat format = new ChoiceFormat(limits, formats);
        System.out.println(format.format(2.5));//将会输出"星期一"
/**3.6介于3和4之间，所以会匹配3，又由于3在limits数组中的索引是0，所以会在formats数组徐照索引0的值，即输出"星期一"
 */
        System.out.println(format.format(3.6));
    }

}
