package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作comment相关数据接口
*/
public interface CommentMapper {

    /**
      * 新增
    */
    int insert(Comment comment);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Comment comment);

    /**
      * 根据ID查询
    */
    Comment selectById(Integer id);

    /**
      * 查询所有
    */
    List<Comment> selectAll(Comment comment);


    @Select("select * from comment where type_id = #{typeId}")
    List<Comment> selectByTypeId(Integer typeId);

    @Select("select * from comment  where type_id = #{typeId} and parent_id = #{parentId}")
    List<Comment> selectByTypeIdAndParentId(@Param("typeId") Integer typeId,@Param("parentId") Integer parentId);
}