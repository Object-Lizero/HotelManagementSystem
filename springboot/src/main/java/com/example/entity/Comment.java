package com.example.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 评论信息表
*/
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    // 展示的字段
    /** ID */
    private Integer id;
    /** 评论内容 */
    private String content;
    /** 用户ID */
    private Integer userId;
    /** 酒店ID */
    private Integer hotelId;
    /** 房型ID */
    private Integer typeId;
    /** 评价时间 */
    private String time;
    /** 回复评论ID */
    private Integer parentId;
    /** 所属角色 */
    private String role;
    /** 用户姓名 */
    private String userName;
    /* 用户头像*/
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    //业务字段  孩子节点
    private List<Comment> children;

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}