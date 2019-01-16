package com.ma.rest.base.model;

import lombok.Data;

/**
 * @author 李光明
 * @date 2018/7/7 10:03
 */
@Data
public class GoodsCategory {
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String  categoryName;
    /**
     * 分类副标题
     */
    private String categoryTitle;
    /**
     * 上级ID
     */
    private Integer pid;
    /**
     * 分类图标
     */
    private String categoryPic;
}
