package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.StatusEnum;
import com.example.entity.Account;
import com.example.entity.Hotel;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户业务处理
 **/
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


    public void add(User user) {
        //1. 做一下重复性校验
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if(ObjectUtil.isNotNull(dbUser)){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        //默认密码
        if(ObjectUtil.isEmpty(user.getPassword())){
            user.setPassword("123456");
        }
        if(ObjectUtil.isEmpty(user.getRole())){
            user.setRole(RoleEnum.USER.name());
        }
        if(ObjectUtil.isEmpty(user.getAvatar())){
            user.setAvatar("http://localhost:9090/files/1697438073596-avatar.png");
        }

        userMapper.insert(user);
    }

    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userMapper.selectAll(user);
        return PageInfo.of(users);
    }

    public void updateById(User user) {
        userMapper.updateById(user);
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteById(id);
        }
    }

    public Account login(Account account) {
        Account user = userMapper.selectByUsername(account.getUsername());
        //  查数据库是否有这个用户
        if (ObjectUtil.isNull(user)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //  比对密码
        if (!account.getPassword().equals(user.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = user.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, user.getPassword());
        user.setToken(token);
        return user;
    }

    public void register(Account account) {
        User user = new User();
        BeanUtils.copyProperties(account, user);
        add(user);
    }

    public Account selectById(Integer id) {
        Account account = userMapper.selectById(id);
        return account;
    }

    //修改密码
    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }
}