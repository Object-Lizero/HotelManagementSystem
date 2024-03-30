package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.OrdersEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Orders;
import com.example.entity.Type;
import com.example.exception.CustomException;
import com.example.mapper.OrdersMapper;
import com.example.mapper.TypeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单信息表业务处理
 **/
@Service
public class OrdersService {

    private static final Logger log = LoggerFactory.getLogger(OrdersService.class);
    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private TypeMapper typeMapper;

    /**
     * 新增
     */
    public void add(Orders orders) {


        //防止用户不输入时间，报空指针 异常
        if (ObjectUtil.isEmpty(orders.getInTime()) || ObjectUtil.isEmpty(orders.getOutTime())) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        Type type = typeMapper.selectById(orders.getTypeId());

        if (type.getNum() == 0) {
            throw new CustomException(ResultCodeEnum.ROOM_CHECK_EMPTY);
        }
        try {
            orders.setStatus(OrdersEnum.STATUS_CHECKING.status);
            orders.setTime(DateUtil.now());//订单生成时间
            orders.setOrderId(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
            // 根据入住时间和离开时间 计算天数
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(orders.getInTime());
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(orders.getOutTime());
            // 比较两个时间的大小
            int result = start.compareTo(end);//start-end
            if (result >= 0) {
                throw new CustomException(ResultCodeEnum.TIME_CHECK_ERROR);
            }
            Long dayNum = getDayNum(orders.getInTime(), orders.getOutTime());
            orders.setDays(dayNum);

            orders.setPrice(type.getPrice() * dayNum);
            ordersMapper.insert(orders);
        } catch (CustomException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new CustomException(ResultCodeEnum.TIME_CHECK_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
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
        ordersMapper.deleteById(id);
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
    public void updateById(Orders orders) {
        ordersMapper.updateById(orders);
    }

    /**
     * 根据ID查询
     */
    public Orders selectById(Integer id) {
        return ordersMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Orders> selectAll(Orders orders) {
        return ordersMapper.selectAll(orders);
    }

    /**
     * 分页查询
     */
    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Orders> list = ordersMapper.selectAll(orders);
        return PageInfo.of(list);
    }

}