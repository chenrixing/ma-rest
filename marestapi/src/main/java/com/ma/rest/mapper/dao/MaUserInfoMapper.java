package com.ma.rest.mapper.dao;

import com.ma.rest.pojo.MaUserInfo;

import java.util.List;
import java.util.Map;

public interface MaUserInfoMapper {

    MaUserInfo selectUserInfo(MaUserInfo maUserInfo);

    int updateByUserInfo(MaUserInfo maUserInfo);

    int insertUserInfo(MaUserInfo userInfo);

    List<MaUserInfo> selectUserInfoList(Map<String, Object> paramMap);
    Integer selectCountUserInfo(Map<String, Object> paramMap);

}
