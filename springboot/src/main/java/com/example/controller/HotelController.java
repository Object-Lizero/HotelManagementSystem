package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Admin;
import com.example.entity.Hotel;
import com.example.service.HotelService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Hotel hotel,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Hotel> page = hotelService.selectPage(hotel, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Hotel hotel){
        hotelService.updateById(hotel);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){  //路径变量
        hotelService.deleteById(id);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){  //路径变量
        hotelService.deleteBatch(ids);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

}