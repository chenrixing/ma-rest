package com.ma.rest.service.impl;


import com.ma.rest.base.model.Round;
import com.ma.rest.mapper.dao.RoundMapper;
import com.ma.rest.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yantongan on 2017/6/13 at 15:08.
 */
@Service
@Transactional
public class RoundServiceImpl implements RoundService {
    @Autowired
    private RoundMapper roundMapper;

    @Override
    public List<Round> queryAllShowRound() {
        return roundMapper.selectAllShowRound();
    }

}
