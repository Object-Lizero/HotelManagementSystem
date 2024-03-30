package com.example.entity;

import java.io.Serializable;

/**
 * 用户收藏表
*/
public class Collect implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    // 用户ID
    private Integer userId;
    /** 房间类型编号 */
    private Integer typeId;
    //房间名
    private String typeName;
    //    房间价格
    private Double typePrice;
    //剩余间数
    private Integer typeNum;

    public Integer getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(Double typePrice) {
        this.typePrice = typePrice;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(Integer typeNum) {
        this.typeNum = typeNum;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}