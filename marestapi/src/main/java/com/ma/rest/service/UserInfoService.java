package com.ma.rest.service;

import com.ma.rest.pojo.MaUserInfo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Title: 用户信息
 * @date 2018/5/28 15:21
 */
public interface UserInfoService {
    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    public MaUserInfo queryByPhone(String phone);
    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    public MaUserInfo queryByToken(String token);
    /**
     * 根据userId查询用户信息
     * @param userId
     * @return
     */
    public MaUserInfo queryByUserId(Integer userId);

    /**
     * 根据分类Id查询商户列表信息
     * @param paramMap
     * @return
     */
    public List<MaUserInfo> queryByCategoryId(Map<String, Object> paramMap);

    /**
     * 根据分类Id查询商户列表信息总条数
     * @param paramMap
     * @return
     */
    public Integer queryCountByCategoryId(Map<String, Object> paramMap);

    /**
     * 用于登录接口
     * @param userInfo
     * @return
     */
    MaUserInfo loginUserInfo(MaUserInfo userInfo) throws Exception;

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    Integer insertUserInfo(MaUserInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    Integer updatetUserInfo(MaUserInfo userInfo);

    /**
     * 文件上传
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file);
}
