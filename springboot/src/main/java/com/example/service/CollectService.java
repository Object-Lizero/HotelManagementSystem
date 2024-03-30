package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Collect;
import com.example.entity.Type;
import com.example.exception.CustomException;
import com.example.mapper.CollectMapper;
import com.example.mapper.TypeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 收藏信息表业务处理
 **/
@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private TypeMapper typeMapper;
    /**
     * 新增
     */
    public void add(Collect collect) {
        Collect dbCollect= collectMapper.selectByUserIdAndTypeId(collect.getUserId(), collect.getTypeId());
        if(ObjectUtil.isNotEmpty(dbCollect)){
            throw new CustomException(ResultCodeEnum.COLLECTED_ALEADY_ERROR);
        }
        collectMapper.insert(collect);
    }


    /**
     * 批量删除
     */
//    public void deleteBatch(List<Integer> ids) {
//        for (Integer id : ids) {
//            collectMapper.deleteById(id);
//        }
//    }


    /**
     * 分页查询
     */
//    public PageInfo<Collect> selectPage(Collect collect, Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Collect> list = collectMapper.selectAll(collect);
//        return PageInfo.of(list);
//    }

    public List<Type> selectOwn(Integer id) {
        List<Collect> list =  collectMapper.selectByUserId(id);
        List<Type> result = new ArrayList<>();
        for (Collect collect : list) {
            Type type = typeMapper.selectById(collect.getTypeId());
            if(ObjectUtil.isNotEmpty(type)){
                result.add(type);
            }
        }
        return result;
        //使用sql语句删除
        // return collectMapper.selectOwn(id);
    }
    //取消收藏功能实现
    public void deleteByUserIdAndTypeId(Integer userId, Integer typeId) {
        collectMapper.deleteByUserIdAndTypeId(userId, typeId);
    }
}