package com.ma.rest.service.impl;



import com.ma.rest.base.model.Collection;
import com.ma.rest.base.model.Order;
import com.ma.rest.mapper.dao.CollectionMapper;
import com.ma.rest.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in15:06 2018/7/7 0007
 **/
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Override
    public Integer deleteByKey(Long id) {
        return collectionMapper.deleteByKey(id);
    }

    @Override
    public Integer insert(Collection collection) {
        return collectionMapper.insert(collection);
    }

    @Override
    public List<Collection> selectCollections(Long userid) {
        List<Collection> list =  collectionMapper.selectCollectionList(userid);
        return list;
    }

    @Override
    public List<Collection> selectFollow(Long userid) {
        List<Collection> list =  collectionMapper.selectFollowList(userid);
        return list;
    }
}
