package com.ma.rest.service.impl;

import com.ma.common.utils.AESUtils;
import com.ma.common.utils.Constants;
import com.ma.common.utils.JavaUtils;
import com.ma.rest.mapper.dao.MaUserInfoMapper;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.service.UserInfoService;
import com.ma.rest.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Description: 查询用户信息
 * @date 2018/5/28 15:49
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
     @Resource
     private MaUserInfoMapper maUserInfoMapper;

    @Value("${REDIS_FILE}")
    private String REDIS_FILE;
    @Override
    public MaUserInfo queryByPhone(String phone) {
        MaUserInfo userInfo=new MaUserInfo();
        userInfo.setPhone(phone);
        return maUserInfoMapper.selectUserInfo(userInfo);
    }

    @Override
    public MaUserInfo queryByToken(String token) {
        MaUserInfo userInfo=new MaUserInfo();
        userInfo.setUserToken(token);
        return maUserInfoMapper.selectUserInfo(userInfo);
    }

    @Override
    public MaUserInfo queryByUserId(Integer userId) {
        MaUserInfo userInfo=new MaUserInfo();
        userInfo.setUserId(userId);
        userInfo.setType("3");
        return maUserInfoMapper.selectUserInfo(userInfo);
    }

    @Override
    public List<MaUserInfo> queryByCategoryId(Map<String, Object> paramMap) {
        return maUserInfoMapper.selectUserInfoList(paramMap);
    }

    @Override
    public Integer queryCountByCategoryId(Map<String, Object> paramMap) {
        return maUserInfoMapper.selectCountUserInfo(paramMap);
    }

    @Override
    public MaUserInfo loginUserInfo(MaUserInfo userInfo) throws Exception {
        //查询用户信息
        MaUserInfo maUserInfo=maUserInfoMapper.selectUserInfo(userInfo);
        if(maUserInfo!=null){
            maUserInfo.setUserToken(JavaUtils.getUUID());
            int resutcount=maUserInfoMapper.updateByUserInfo(maUserInfo);

            if(resutcount>0) {
                maUserInfo.setUserToken(
                    AESUtils.aesEncrypt(maUserInfo.getUserToken(), Constants.IOS_UserInfo_TOKEN_PWD));
            }
        }
        return maUserInfo;
    }

    @Override
    public Integer insertUserInfo(MaUserInfo userInfo) {
        return maUserInfoMapper.insertUserInfo(userInfo);
    }

    @Override
    public Integer updatetUserInfo(MaUserInfo userInfo) {

        return maUserInfoMapper.updateByUserInfo(userInfo);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            // 取图片扩展名
            String originalFilename = file.getOriginalFilename();
            // 取扩展名不要“.”
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            FastDFSClient client = new FastDFSClient(REDIS_FILE);
            String url = client.uploadFile(file.getBytes(), extName);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
