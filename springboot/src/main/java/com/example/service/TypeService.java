
package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Notice;
import com.example.entity.Type;
import com.example.exception.CustomException;
import com.example.mapper.NoticeMapper;
import com.example.mapper.TypeMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 房间类别表表业务处理
 **/
@Service
public class TypeService {

    @Resource
    private TypeMapper typeMapper;

    /**
     * 新增
     */
    public void add(Type type) {
        Account currentUser = TokenUtils.getCurrentUser();
        typeMapper.insert(type);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        typeMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            typeMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Type type) {
        typeMapper.updateById(type);
    }

    /**
     * 根据ID查询
     */
    public Type selectById(Integer id) {
        return typeMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Type> selectAll(Type type) {
        return typeMapper.selectAll(type);
    }

    /**
     * 分页查询
     */
    public PageInfo<Type> selectPage(Type type, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(Objects.isNull(currentUser)){
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        if (RoleEnum.HOTEL.name().equals(currentUser.getRole())) {
            type.setHotelId(currentUser.getId());  //查询当前酒店管理员所属的房间信息
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Type> list = typeMapper.selectAll(type);
        return PageInfo.of(list);
    }

}