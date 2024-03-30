package com.example.controller;

import com.example.common.Result;
import com.example.entity.Collect;
import com.example.entity.Type;
import com.example.service.CollectService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收藏信息表前端操作接口
 **/

//  TODO  增加批量删除功能
//  TODO  增加分页查询功能
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Collect collect) {
        collectService.add(collect);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/deleteByUserIdAndTypeId")
    public Result deleteByUserIdAndTypeId(@RequestParam Integer userId,@RequestParam Integer  typeId) {
        collectService.deleteByUserIdAndTypeId(userId,typeId);
        return Result.success();
    }

    /**
     * 批量删除
     */
//    @DeleteMapping("/delete/batch")
//    public Result deleteBatch(@RequestBody List<Integer> ids) {
//        collectService.deleteBatch(ids);
//        return Result.success();
//    }





    /**
     * 根据用户ID查询
     */
    @GetMapping("/selectOwn")
    public Result selectOwn(@RequestParam Integer id) {
        List<Type> list = collectService.selectOwn(id);
        return Result.success(list);
    }


    /**
     * 分页查询
     */
//    @GetMapping("/selectPage")
//    public Result selectPage(Collect collect,
//                             @RequestParam(defaultValue = "1") Integer pageNum,
//                             @RequestParam(defaultValue = "10") Integer pageSize) {
//        PageInfo<Collect> page = collectService.selectPage(collect, pageNum, pageSize);
//        return Result.success(page);
//    }

}