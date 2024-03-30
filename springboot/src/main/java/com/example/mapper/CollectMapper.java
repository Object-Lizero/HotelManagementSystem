package com.example.mapper;

import com.example.entity.Collect;
import com.example.entity.Type;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作collect相关数据接口
*/
public interface CollectMapper {

    /**
      * 新增
    */
    int insert(Collect collect);



    //两种实现方式
    //根据用户id查询该用户收藏的房间信息
    List<Type> selectOwn(Integer id);
    //根据用户id查询该用户收藏的房间类型
    @Select("select * from collect where user_id = #{userId}")
    List<Collect> selectByUserId(Integer userId);

    @Select("select * from collect where user_id = #{userId} and type_id = #{typeId}")
    Collect selectByUserIdAndTypeId(@Param("userId") Integer userId, @Param("typeId") Integer typeId);

    @Delete("delete from collect where user_id = #{userId} and type_id = #{typeId}")
    void deleteByUserIdAndTypeId(@Param("userId") Integer userId, @Param("typeId") Integer typeId);
}