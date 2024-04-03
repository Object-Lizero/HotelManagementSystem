package com.example.mapper;

import com.example.entity.Account;
import com.example.entity.Hotel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作Hotel相关数据接口
*/
public interface HotelMapper {


    void insert(Hotel hotel);
    @Select("select * from hotel where username =  #{username}")
    Hotel selectByUsername(String username);

    List<Hotel> selectAll(Hotel hotel);

    void updateById(Hotel hotel);

    @Delete("delete from hotel where  id = #{id}")
    void deleteById(Integer id);
    @Select("select * from hotel where id =  #{id}")
    Hotel selectById(Integer id);

    @Select("select * from hotel where name like concat('%',#{name},'%')")
    List<Hotel> selectByName(String name);

    @Select("select * from hotel where name =#{username}")
    Hotel selectByUserName(String username);
}