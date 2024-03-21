package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.StatusEnum;
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
        //1. 做一下重复性校验
        Hotel hotelUser = hotelMapper.selectByUsername(hotel.getUsername());
//        List<Hotel> hotels = hotelMapper.selectAll(hotel);
        if(ObjectUtil.isNotNull(hotelUser)){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if(ObjectUtil.isEmpty(hotel.getPassword())){
            hotel.setPassword("123456");
        }
        if(ObjectUtil.isEmpty(hotel.getRole())){
            hotel.setRole(RoleEnum.HOTEL.name());
        }
        if(ObjectUtil.isEmpty(hotel.getAvatar())){
            hotel.setAvatar("http://localhost:9090/files/1697438073596-avatar.png");
        }
        hotel.setStatus(StatusEnum.CHECKING.status);

        hotelMapper.insert(hotel);
    }

    public PageInfo<Hotel> selectPage(Hotel hotel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Hotel> hotels = hotelMapper.selectAll(hotel);
        return PageInfo.of(hotels);
    }

    public void update(Hotel hotel) {
        hotelMapper.updateById(hotel);
    }

    public void deleteById(Integer id) {
        hotelMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            hotelMapper.deleteById(id);
        }
    }
}