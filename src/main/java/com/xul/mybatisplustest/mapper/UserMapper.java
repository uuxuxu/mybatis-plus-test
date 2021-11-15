package com.xul.mybatisplustest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xul.mybatisplustest.entity.User;

import java.util.List;

/**
 * @author xul
 * @create 2021-10-31 16:18
 */

public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    List<User> getSelectByName(String name);

    /**
     * 查询age大于某个年龄的所以用户
     *
     * @param age
     * @return
     */
    IPage<User> getSelectPage(Page<?> page, Integer age);
}
