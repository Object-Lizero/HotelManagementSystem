package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Admin;
import com.example.entity.Comment;
import com.example.entity.Hotel;
import com.example.entity.User;
import com.example.mapper.*;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论信息表业务处理
 **/
@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    HotelMapper hotelMapper;
    @Resource
    UserMapper userMapper;

    /**
     * 新增
     */
    public void add(Comment comment) {
        comment.setTime(DateUtil.now());
        comment.setUserId(TokenUtils.getCurrentUser().getId());
        commentMapper.insert(comment);

    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
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
    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    /**
     * 根据ID查询
     */
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    /**
     * 分页查询
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }


    public List<Comment> selectByTypeId(Integer typeId) {

        //获得所有的一级评论
        List<Comment> list = commentMapper.selectByTypeIdAndParentId(typeId, 0);

        //获得一级评论的所有的回复
        for (Comment comment : list) {
            addAvatarAndUserName(comment);

            //获得孩子节点
            List<Comment> childs = commentMapper.selectByTypeIdAndParentId(typeId, comment.getId());
            for (Comment child : childs) {
                addAvatarAndUserName(child);
            }
            comment.setChildren(childs);
        }
        return list;
    }

    private void addAvatarAndUserName(Comment comment) {
        //获得用户姓名和他的头像   也可以在sql中处理
        if (RoleEnum.ADMIN.name().equals(comment.getRole())){
            Admin admin = adminMapper.selectById(comment.getUserId());
            comment.setAvatar(admin.getAvatar());
            comment.setUserName(admin.getName());
        }
        if (RoleEnum.HOTEL.name().equals(comment.getRole())) {
            Hotel hotel = hotelMapper.selectById(comment.getUserId());
            comment.setAvatar(hotel.getAvatar());
            comment.setUserName(hotel.getName());
        }
        if (RoleEnum.USER.name().equals(comment.getRole())) {
            User user = userMapper.selectById(comment.getUserId());
            comment.setAvatar(user.getAvatar());
            comment.setUserName(user.getName());
        }
    }
}