package com.ma.rest.service.impl;


import com.ma.rest.base.model.Serial;
import com.ma.rest.mapper.dao.SerialMapper;
import com.ma.rest.service.SerialService;
import com.ma.rest.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:32 2018/7/19 0019
 **/
@Service
@Transactional
public class SerialServiceImpl implements SerialService {
    @Autowired
    private SerialMapper serialMapper;
    @Override
    public String getSerialNumber(Integer maxNum) {
        maxNum++;
       serialMapper.truncateTable();
        Serial se=new Serial();
        se.setSerialNumber(maxNum);
       serialMapper.insertSelective(se);
        String s = "0000000"+maxNum;
        String newS=null;
        newS=s.substring(s.length()-8);
        Date date = new Date();
        String s1 = DateUtil.toString(date).replace("-","").replace(":","");
        String sss = "0123456789";
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < 4; j++) {
            int x = (int) (Math.random() * 10);
            char c = sss.charAt(x);
            sb.append(c);
        }
        return s1+newS+sb.toString();
    }

    @Override
    public Serial selectSerial() {
        return serialMapper.selectSerial();
    }
}
