package com.ma.rest.service.impl;

import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsCategory;
import com.ma.rest.mapper.dao.GoodsMapper;
import com.ma.rest.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Title: 商品实现类
 * @date 2018/6/21 7:29
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsCategory> selectFirstCategoryList() {
        return goodsMapper.selectFirstCategoryList();
    }

    @Override
    public List<GoodsCategory> selectSubCategoryList(Integer pid) {
        return goodsMapper.selectSubCategoryList(pid);
    }

    @Override
    public List<Goods> selectGoodsByCategoryId(Map<String, Object> params) {
        return goodsMapper.selectGoodsByCategoryId(params);
    }

    @Override
    public Integer selectCountGoodsByCategoryId(Map<String, Object> params) {
        return goodsMapper.selectCountGoodsByCategoryId(params);
    }

    @Override
    public Goods selectgoodsInfoById(Integer goodsId) {
        return goodsMapper.selectgoodsInfoById(goodsId);
    }

    @Override
    public Goods selectgoodsById(Integer goodsId) {
        return goodsMapper.selectgoodsById(goodsId);
    }

    @Override
    public List<Goods> selectOrderGoodsList(Integer orderId) {
        return goodsMapper.queryOrderGoodsList(orderId);
    }

    @Override
    public Integer updateGoods(Goods goods) {
        return goodsMapper.updateGoods(goods);
    }

    @Override
    public List selectListByQueryCon(Map<String, Object> params) {
        return goodsMapper.selectListByQueryCon(params);
    }

}
