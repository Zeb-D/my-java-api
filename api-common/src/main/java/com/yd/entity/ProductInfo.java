package com.yd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Yd on  2018-02-08
 * @description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductInfo {
    private String code;
    private String name;
    private String address;
    private String managerName;
    private String telephone;
}
