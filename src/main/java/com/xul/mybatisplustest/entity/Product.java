package com.xul.mybatisplustest.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author xul
 * @create 2021-11-02 21:15
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version
    private Integer version;
}
