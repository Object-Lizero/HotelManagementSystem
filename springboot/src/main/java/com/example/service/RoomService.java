package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoomEnum;
import com.example.entity.Orders;
import com.example.entity.Room;
import com.example.entity.Type;
import com.example.exception.CustomException;
import com.example.mapper.OrdersMapper;
import com.example.mapper.RoomMapper;
import com.example.mapper.TypeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 客房信息表业务处理
 **/
@Service
public class RoomService {

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private OrdersMapper ordersMapper;
    /**
     * 新增
     */
    public void add(Room room) {
        room.setStatus(RoomEnum.STATUS_OK.status);
        roomMapper.insert(room);
        //改变对应的酒店里面该分类下面的客房数量
        Type type = typeMapper.selectById(room.getTypeId());
        if(ObjectUtil.isNotEmpty(type)){
            type.setNum(type.getNum()+1);
            typeMapper.updateById(type);
        }
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        //改变对应的酒店里面该分类下面的客房数量
        Room room = roomMapper.selectById(id);
        if(ObjectUtil.isEmpty(room)){
            //当前房间数据没取到 抛出异常
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        Type type = typeMapper.selectById(room.getTypeId());
        if(ObjectUtil.isNotEmpty(type)){
            type.setNum(type.getNum()-1);
            typeMapper.updateById(type);
        }
        roomMapper.deleteById(id);

    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {

        for (Integer id : ids) {
            deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Room room) {
        roomMapper.updateById(room);
    }

    /**
     * 根据ID查询
     */
    public Room selectById(Integer id) {
        return roomMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Room> selectAll(Room room) {
        return roomMapper.selectAll(room);
    }

    /**
     * 分页查询
     */
    public PageInfo<Room> selectPage(Room room, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Room> list = roomMapper.selectAll(room);
        return PageInfo.of(list);
    }

    public List<Room> selectByTypeId(String orderId) {
        //查询该订单选中的房型
        Orders orders = ordersMapper.selectByOrderId(orderId);
        //查询该房型剩余的空房间
        return roomMapper.selectByTypeId(orders.getTypeId());
    }
}