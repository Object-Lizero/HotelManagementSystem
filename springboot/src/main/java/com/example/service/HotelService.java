package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Hotel;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.mapper.HotelMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 酒店管理员业务处理
 **/
@Service
public class HotelService {

    @Resource
    private HotelMapper hotelMapper;


    public void add(Hotel hotel) {
        hotelMapper.insert(hotel);
    }
}