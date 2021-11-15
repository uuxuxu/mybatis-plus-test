package com.xul.mybatisplustest.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xul.mybatisplustest.Service.UserService;
import com.xul.mybatisplustest.entity.User;
import com.xul.mybatisplustest.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xul
 * @create 2021-10-31 16:47
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<User> getSelectByName(String name) {
        return baseMapper.getSelectByName(name);
    }
}
