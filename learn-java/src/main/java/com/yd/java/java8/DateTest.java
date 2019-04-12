package com.yd.java.java8;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author Yd created on 11:53
 **/
public class DateTest {
	public static void main(String[] args) {
		Instant instant = Instant.now();
		System.out.println(instant);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		System.out.println(dateTimeFormatter.format(instant));

		// 1. 获取当前日期(年月日) -----打印输出-----2018-01-29
		LocalDate localDate = LocalDate.now();
		System.out.println(localDate.toString());
		// 2. 根据年月日构建Date ----打印输出-----2018-01-30
		LocalDate localDate1 = LocalDate.of(2018, 01, 30);
		// 3. 字符串转换日期,默认按照yyyy-MM-dd格式，也可以自定义格式 -----打印输出-----2018-01-30
		LocalDate localDate2 = LocalDate.parse("2018-01-30");

		// 1. 获取当前时间，包含毫秒数 -----打印输出----- 21:03:26.315
		LocalTime localTime = LocalTime.now();
		// 2. 构建时间 -----打印输出----- 12:15:30
		LocalTime localTime1 = LocalTime.of(12, 15, 30);

		// 1. 获取当前年月日 时分秒 -----打印输出----- 2018-01-29T21:23:26.774
		LocalDateTime localDateTime = LocalDateTime.now().withMinute(23);
		// 2. 通过LocalDate和LocalTime构建 ----- 打印输出----- 2018-01-29T21:24:41.738
		LocalDateTime localDateTime1 = LocalDateTime.of(LocalDate.now(), LocalTime.now());

		//Duration中必须要支持秒数，如果没有的话，将会报错
		Duration duration = Duration.between(localTime1, localDateTime1);
		System.out.println(duration.getSeconds());  // 75

	}
}
