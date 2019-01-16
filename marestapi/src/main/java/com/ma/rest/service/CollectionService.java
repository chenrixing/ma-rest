package com.ma.rest.service;



import com.ma.rest.base.model.Collection;
import com.ma.rest.base.model.Order;

import java.util.List;

/**
 * Descriptions
 *订单业务
 * @Author施龙飞
 * @date: Created in14:58 2018/7/7 0007
 **/
public interface CollectionService {
    /**
     * 取消关注、收藏
     * @param id
     * @return
     */
    Integer deleteByKey(Long id);

    /**
     * 添加关注、收藏
     * @param collection
     * @return
     */
    Integer insert(Collection collection);

    /**
     *查询收藏商品
     * @param userid
     * @return
     */
    List<Collection> selectCollections(Long userid);

    /**
     * 查询关注商家
     * @param userid
     * @return
     */
    List<Collection> selectFollow(Long userid);


}
