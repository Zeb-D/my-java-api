package com.yd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yd on  2018-01-16
 * @Description：
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User2 extends User{
    private String passWord;

    public User2(Integer id, String name, String addr, String passWord) {
        super(id, name, addr);
        this.passWord = passWord;
    }
}
