package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户前端操作接口
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 新增
     */
    @PostMapping("/add")
      public Result Add(@RequestBody User user){
        userService.add(user);
      return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(User user,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<User> page = userService.selectPage(user, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){  //路径变量
        userService.deleteById(id);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){  //路径变量
        userService.deleteBatch(ids);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

}