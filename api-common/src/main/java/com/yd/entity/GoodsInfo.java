package com.yd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yd on  2018-01-27
 * @description
 **/
@Data
@ToString
@Builder
@Accessors(chain=true)
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {
    private Integer id;
    private String name;
    private String type;
    private String goodStatus;
    private Integer age;
}
