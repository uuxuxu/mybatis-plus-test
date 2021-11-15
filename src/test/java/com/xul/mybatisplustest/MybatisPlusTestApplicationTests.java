package com.xul.mybatisplustest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xul.mybatisplustest.Service.UserService;
import com.xul.mybatisplustest.entity.Product;
import com.xul.mybatisplustest.entity.User;
import com.xul.mybatisplustest.mapper.ProductMapper;
import com.xul.mybatisplustest.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class MybatisPlusTestApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Resource
    private ProductMapper productMapper;

    @Test
    void testSelectList() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("ux");
        user.setAge(24);
        user.setEmail("1452170145@qq.com");

        /*user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());*/

        userMapper.insert(user);
    }

    @Test
    public void testSelect() {
        User user = userMapper.selectById(1);
        System.out.println(user);
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "xul");
        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1455163347921223681L);
        user.setAge(77);
        userMapper.updateById(user);
    }

    @Test
    public void testDelete() {
        userMapper.deleteById(2L);
    }

    @Test
    public void testCount() {
        int count = userService.count();
        System.out.println(count);
    }

    @Test
    public void testSaveBatch() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("小花" + i);
            user.setAge(18 + i);
            users.add(user);
        }
        userService.saveBatch(users);
    }

    @Test
    public void testSelectByName() {
        List<User> users = userMapper.getSelectByName("xul");
        users.forEach(System.out::println);

    }

    @Test
    public void testSelectListByName() {
        List<User> users = userService.getSelectByName("xul");
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectPage() {
        Page<User> userPage = new Page<>(1, 5);
        userMapper.selectPage(userPage, null);
        List<User> users = userPage.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void testGetSelectPage() {
        Page<User> userPage = new Page<>(2, 5);
        userMapper.getSelectPage(userPage, 20);
        List<User> users = userPage.getRecords();
        users.forEach(System.out::println);

    }

    /**
     * 模拟事务
     */
    @Test
    public void testConCurrentUpdate() {
        // 1、小李
        Product p1 = productMapper.selectById(1L);
        // 2、小王
        Product p2 = productMapper.selectById(1L);
        // 3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);
        // 4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + result2);
        // 最后的结果
        Product p3 = productMapper.selectById(1L);
        System.out.println("最后的结果：" + p3.getPrice());
    }

    /**
     * 添加乐观锁 添加注解 乐观锁插件
     */
    @Test
    public void testConCurrentUpdateOpt() {
        // 1、小李
        Product p1 = productMapper.selectById(1L);
        // 2、小王
        Product p2 = productMapper.selectById(1L);
        // 3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);
        // 4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + result2);
        if (result2 == 0) {
            p2 = productMapper.selectById(1L);
            p2.setPrice(p2.getPrice() - 30);
            result2 = productMapper.updateById(p2);
        }
        System.out.println("小王修改结果：" + result2);
        // 最后的结果
        Product p3 = productMapper.selectById(1L);
        System.out.println("最后的结果：" + p3.getPrice());
    }

    @Test
    public void testQueryWrapperFirst() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "小")
                .between("age", 20, 30)
                .isNull("email");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testQueryWrapperSecond() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);

    }

    @Test
    public void testQueryWrapperThird() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNull("email");
        int result = userMapper.delete(wrapper);
        System.out.println("删除的行数: " + result);
    }

    /**
     * 查询名字中包含n，且（年龄小于25或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为1452170145@qq.com
     */
    @Test
    public void testQueryWrapperFourth() {
        // 组装查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "x")
                .and(l -> l.ge("age", 25).or().isNull("email"));
        // 组装更新条件
        User user = new User();
        user.setAge(18);
        user.setEmail("1452170145@qq.com");
        userMapper.update(user, wrapper);
    }

    /**
     * 查询名字中包含n，且（年龄小于25或email为空的用户），并将这些用户的年龄设置为18，邮箱设置为1452170145@qq.com
     */
    @Test
    public void testQueryWrapperSixth() {
        // 组装查询条件
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.like("name", "x")
                .and(l -> l.ge("age", 25).or().isNull("email"))
                .set("age", 18)
                .set("email", "14521270145@qq.com");
        // 自动填充updateTime
        User user = new User();
        userMapper.update(user, wrapper);
    }

    /**
     * 查询所有用户的用户名和年龄
     */
    @Test
    public void testQueryWrapperFirth() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("name", "age");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);

    }

    /**
     * 查询名字中包含n，年龄大于10且小于20的用户，查询条件来源于用户输入，是可选的 condition组装条件
     */
    @Test
    public void testQueryWrapperSeventh() {
        String name = "x";
        Integer ageBegin = 18;
        Integer ageEnd = null;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "name", name)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

}
