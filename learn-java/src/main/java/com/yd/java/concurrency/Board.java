package com.yd.java.concurrency;

import lombok.Data;

import java.util.List;

/**
 * @author Yd on  2018-02-09
 * @description 细胞模拟活动
 **/
@Data
public class Board {
    private Integer x, y;//细胞位置

    private List<Board> subBoards;//子细胞


    public void commitNewValues() {

    }


}
