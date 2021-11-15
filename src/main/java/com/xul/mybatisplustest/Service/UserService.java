package com.xul.mybatisplustest.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xul.mybatisplustest.entity.User;

import java.util.List;

/**
 * @author xul
 * @create 2021-10-31 16:46
 */
public interface UserService extends IService<User> {

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    List<User> getSelectByName(String name);
}
