package com.ma.rest.controller;

import com.ma.common.utils.Constants;
import com.ma.common.utils.StringUtils;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsCategory;
import com.ma.rest.pojo.Comment;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.service.GoodsService;
import com.ma.rest.service.UserInfoService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author 李光明
 * @Title: 查询商品信息
 * @date 2018/6/21 4:02
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {

    private static Logger logger = Logger.getLogger(GoodsController.class);

    @Resource
    private GoodsService goodsService;
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "推荐商品")
    @RequestMapping(value = "/getPrimeGoods", method = RequestMethod.GET)
    public WebJsonResult getPrimeGoods(@RequestParam(value = "categoryId",required = false,defaultValue ="" ) String categoryId,
                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        WebJsonResult result = success();
        try {
            Map<String, Object> params = new HashMap<>();
            Map<String, Object> params1 = new HashMap<>();
            params.put("offset",(page-1)*pageSize);
            params.put("pageSize", pageSize);
            if(!categoryId.equals("")){
                params.put("categoryId", categoryId);
            }
            params.put("type", "1");
            List<Goods>  subCategoryList=goodsService.selectGoodsByCategoryId(params);
            for(Goods goods:subCategoryList){
                if(StringUtils.isNotEmpty(goods.getGoodsImg())){
                    String[] split = goods.getGoodsImg().split(",");
                    if(CollectionUtils.isNotEmpty(Arrays.asList(split))){
                        goods.setGoodsImgArr(Arrays.asList(split));
                    }
                }else{
                    goods.setGoodsImgArr(new ArrayList<>());
                }
            }
            params1.put("pageSize",pageSize);
            params1.put("nowPage",page);
            params1.put("totalPages",(goodsService.selectCountGoodsByCategoryId(params)-1)/pageSize+1);
            params1.put("data",subCategoryList);
            result.setData(params1);
            //result.setData(map);
            logger.info("getPrimeGoods success;");
            return result;
        }catch (Exception ex){
          logger.error("query prime Goods Exception {}",ex);
        }
        return result;
    }

    @ApiOperation(value = "查询商品详情")
    @RequestMapping(value = "/getGoodsInfo", method = RequestMethod.GET)
    public WebJsonResult getGoodsInfo(@RequestParam(value = "goodsId", required = true) Integer goodsId) {
        WebJsonResult result = success();
        try {

            Goods goods=goodsService.selectgoodsInfoById(goodsId);
            if(StringUtils.isNotEmpty(goods.getGoodsImg())){
                String[] split = goods.getGoodsImg().split(",");
                if(CollectionUtils.isNotEmpty(Arrays.asList(split))){
                    goods.setGoodsImgArr(Arrays.asList(split));
                }
            }else{
                goods.setGoodsImgArr(new ArrayList<>());
            }
            List<Comment> comments = goods.getComments();
            for (Comment comment:comments) {
                if(StringUtils.isNotEmpty(comment.getCommentPic())){
                    String[] split = comment.getCommentPic().replace("[","").replace("]","").replaceAll("\"","").split(",");
                    if(CollectionUtils.isNotEmpty(Arrays.asList(split))){
                       comment.setCommentPicAttr(Arrays.asList(split));
                    }
                }else{
                    comment.setCommentPicAttr(new ArrayList<>());
                }
            }
            result.setData(goods);
            logger.info("getGoodsInfo success;");
            return result;
        }catch (Exception ex){
            logger.error("query prime Goods Exception {}",ex);
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
        }
        return result;
    }

    @ApiOperation(value = "根据分类查询商家列表")
    @RequestMapping(value = "/getGoodsByCategoryId", method = RequestMethod.GET)
    public WebJsonResult getGoodsByCatalogId(@RequestParam(value = "categoryId", required = true) String categoryId,
                                             @RequestParam(name = "page", defaultValue = "1") int page,
                                             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        WebJsonResult result = success();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> params1 = new HashMap<>();
        params.put("offset",(page-1)*pageSize);
        params.put("pageSize", pageSize);
        params.put("categoryId", categoryId);
        try {
            List<MaUserInfo> maUserInfos = userInfoService.queryByCategoryId(params);
            params1.put("pageSize",pageSize);
            params1.put("nowPage",page);
            params1.put("totalPages",(userInfoService.queryCountByCategoryId(params)-1)/pageSize+1);
            params1.put("data",maUserInfos);
            result.setData(params1);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
            logger.error("query GoodsCategory  Exception {}",ex);
        }
        return result;
    }

    @ApiOperation(value = "根据商家Id查询商品列表")
    @RequestMapping(value = "/getGoodsByUserId", method = RequestMethod.GET)
    public WebJsonResult getGoodsByUserId(@RequestParam(value = "userId", required = true) Integer userId,
                                             @RequestParam(name = "page", required = false,defaultValue = "1") int page,
                                             @RequestParam(name = "pageSize", required = false,defaultValue = "10000") int pageSize) {
        WebJsonResult result = success();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> params1 = new HashMap<>();
        params.put("offset",(page-1)*pageSize);
        params.put("pageSize", pageSize);
        params.put("userId",userId);
        try {
            List<Goods>  subCategoryList=goodsService.selectGoodsByCategoryId(params);
            for(Goods goods:subCategoryList){
                if(StringUtils.isNotEmpty(goods.getGoodsImg())){
                    String[] split = goods.getGoodsImg().split(",");
                    if(CollectionUtils.isNotEmpty(Arrays.asList(split))){
                        goods.setGoodsImgArr(Arrays.asList(split));
                    }
                }else{
                    goods.setGoodsImgArr(new ArrayList<>());
                }
            }
            params1.put("pageSize",pageSize);
            params1.put("nowPage",page);
            params1.put("totalPages",(goodsService.selectCountGoodsByCategoryId(params)-1)/pageSize+1);
            params1.put("data",subCategoryList);
            result.setData(params1);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
            logger.error("query GoodsCategory  Exception {}",ex);
        }
        return result;
    }


    @ApiOperation(value = "查询一级分类")
    @RequestMapping(value = "/getFirstCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult getFirstCategoryList() {
        WebJsonResult result = success();
        try {
            List<GoodsCategory>  goodsCategoryList=goodsService.selectFirstCategoryList();
            result.setData(goodsCategoryList);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
            logger.error("query GoodsCategory  Exception {}",ex);
        }
        return result;
    }

    @ApiOperation(value = "查询子分类")
    @RequestMapping(value = "/getSubCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult getSubCategoryList(@ApiParam(name = "pid", value = "上级分类ID", required = true)@RequestParam(value = "pid", required = true) Integer pid) {
        WebJsonResult result = success();
        try {
            List<GoodsCategory>  subCategoryList=goodsService.selectSubCategoryList(pid);
            result.setData(subCategoryList);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
            logger.error("query GoodsCategory  Exception {}",ex);
        }
        return result;
    }

    @ApiOperation(value = "全局搜索")
    @RequestMapping(value = "/getListBQueryCon", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult getListBQueryCon(@ApiParam(name = "queryCon", value = "搜索条件", required = true)@RequestParam(value = "queryCon", required = true) String queryCon,
                                            @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        WebJsonResult result = success();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> params1 = new HashMap<>();
        params.put("offset",(page-1)*pageSize);
        params.put("pageSize", pageSize);
        params.put("queryCon", queryCon);
        try {
            List  subCategoryList=goodsService.selectListByQueryCon(params);
            result.setData(subCategoryList);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
            logger.error("query GoodsCategory  Exception {}",ex);
        }
        return result;
    }
    @ApiOperation(value = "首页数据")
    @RequestMapping(value = "/getHomePage", method = RequestMethod.GET)
    public WebJsonResult getHomePage(@RequestParam(value = "categoryId",required = false,defaultValue ="" ) String categoryId,
                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        WebJsonResult result = success();
        try {
            Map<String, Object> params = new HashMap<>();
            Map<String, Object> params1 = new HashMap<>();
            params.put("offset",(page-1)*pageSize);
            params.put("pageSize", pageSize);
            if(!categoryId.equals("")){
                params.put("categoryId", categoryId);
            }
            params.put("type", "1");
            List<Goods> subCategoryList=goodsService.selectGoodsByCategoryId(params);
            for(Goods goods:subCategoryList){
                if(StringUtils.isNotEmpty(goods.getGoodsImg())){
                    String[] split = goods.getGoodsImg().split(",");
                    if(CollectionUtils.isNotEmpty(Arrays.asList(split))){
                        goods.setGoodsImgArr(Arrays.asList(split));
                    }
                }else{
                    goods.setGoodsImgArr(new ArrayList<>());
                }
            }
            params1.put("pageSize",pageSize);
            params1.put("nowPage",page);
            params1.put("totalPages",(goodsService.selectCountGoodsByCategoryId(params)-1)/pageSize+1);
            params1.put("data",subCategoryList);
            List<GoodsCategory>  goodsCategoryList=goodsService.selectFirstCategoryList();
            params1.put("category",goodsCategoryList);
            List<Map<String,String>> banner = new ArrayList<Map<String, String>>();
            Map<String,String> testmap = new HashMap<String,String>();
            testmap.put("path", "http://52.81.31.200:8888/group1/M00/00/01/rB8ZTFuOOFGALNmhAAFrUGvDCSE699.jpg");
            testmap.put("id","11");
            testmap.put("type","1");
            banner.add(testmap);
            Map<String,String> testmap1 = new HashMap<String,String>();
            testmap1.put("path", "http://52.81.31.200:8888/group1/M00/00/01/rB8ZTFuOOFGALNmhAAFrUGvDCSE699.jpg");
            testmap1.put("id","12");
            testmap1.put("type","1");
            banner.add(testmap1);
            Map<String,String> testmap2 = new HashMap<String,String>();
            testmap2.put("path", "http://52.81.31.200:8888/group1/M00/00/00/rB8ZTFuONuyABlUOAACXe6kVjV4404.jpg");
            testmap2.put("id","17");
            testmap2.put("type","1");
            banner.add(testmap2);
            params1.put("banner",banner);
            result.setData(params1);
            //result.setData(map);
            logger.info("getPrimeGoods success;");
            return result;
        }catch (Exception ex){
            logger.error("query prime Goods Exception {}",ex);
        }
        return result;
    }
}