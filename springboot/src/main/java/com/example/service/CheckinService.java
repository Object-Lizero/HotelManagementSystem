package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.OrdersEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.RoomEnum;
import com.example.entity.*;
import com.example.mapper.CheckinMapper;
import com.example.mapper.OrdersMapper;
import com.example.mapper.RoomMapper;
import com.example.mapper.TypeMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 入住信息表业务处理
 **/
@Service
public class CheckinService {

    @Resource
    private CheckinMapper checkinMapper;


    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private TypeMapper typeMapper;

    /**
     * 新增
     */
    public void add(Checkin checkin) {
        String orderId = checkin.getOrderId();
        Orders orders = ordersMapper.selectByOrderId(orderId);

        checkin.setUserId(orders.getUserId());
        checkin.setHotelId(orders.getHotelId());
        checkin.setTypeId(orders.getTypeId());
        checkin.setInTime(DateUtil.now());
        checkinMapper.insert(checkin);

        // 修改关联的数据
        //  1. 对应的客房状态变成 占用
        Room room = roomMapper.selectById(checkin.getRoomId());
        room.setStatus(RoomEnum.STATUS_NO.status);
        roomMapper.updateById(room);

        //  2. 对应的客房分类剩余间数 要减1
        Type type = typeMapper.selectById(room.getTypeId());
        type.setNum(type.getNum() - 1);
        typeMapper.updateById(type);

        //  3. 对应的订单状态变成已入住
        orders.setStatus(OrdersEnum.STATUS_IN.status);
        ordersMapper.updateById(orders);
    }

    //退房逻辑实现
    public void checkout(Integer id) {
        Checkin checkin = checkinMapper.selectById(id);
        checkin.setOutTime(DateUtil.now());
        checkinMapper.updateById(checkin);

        // 1. 该客房状态还原为空闲
        Room room = roomMapper.selectById(checkin.getRoomId());
        room.setStatus(RoomEnum.STATUS_OK.status);
        roomMapper.updateById(room);

        // 2. 该房型对应的数量 +1
        Type type = typeMapper.selectById(checkin.getTypeId());
        type.setNum(type.getNum() + 1);
        typeMapper.updateById(type);

        //  3. 对应的订单状态变成 已退房
        Orders orders = ordersMapper.selectByOrderId(checkin.getOrderId());
        orders.setStatus(OrdersEnum.STATUS_OUT.status);
        ordersMapper.updateById(orders);

    }

    private Long getDayNum(String inTime, String outTime) throws ParseException {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date star = dft.parse(inTime);//开始时间
        Date end = dft.parse(outTime);//结束时间
        Long starTime = star.getTime();
        Long endTime = end.getTime();
        long num = endTime - starTime;//时间戳相差的毫秒值
        return num / 24 / 60 / 60 / 1000;
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        checkinMapper.deleteById(id);
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
    public void updateById(Checkin checkin) {
        checkinMapper.updateById(checkin);
    }

    /**
     * 根据ID查询
     */
    public Checkin selectById(Integer id) {
        return checkinMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Checkin> selectAll(Checkin checkin) {
        return checkinMapper.selectAll(checkin);
    }

    /**
     * 分页查询
     */
    public PageInfo<Checkin> selectPage(Checkin checkin, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.HOTEL.name().equals(currentUser.getRole())) {
            checkin.setHotelId(currentUser.getId());
        }
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            checkin.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Checkin> list = checkinMapper.selectAll(checkin);
        return PageInfo.of(list);
    }

    public List<Checkin> selectByUserId(Integer id) {
        Checkin checkin = new Checkin();
        checkin.setUserId(id);
        return checkinMapper.selectAll(checkin);
    }


}