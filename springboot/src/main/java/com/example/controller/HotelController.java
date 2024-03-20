package com.example.controller;

import com.example.common.Result;
import com.example.entity.Hotel;
import com.example.service.HotelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 酒店管理员前端操作接口
 **/
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private HotelService hotelService;

    /**
     * 新增
     */
    @PostMapping("/add")
      public Result Add(@RequestBody Hotel hotel){
        hotelService.add(hotel);
      return Result.success();
    }


}