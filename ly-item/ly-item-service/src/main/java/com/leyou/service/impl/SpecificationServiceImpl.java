package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.SpecGroupMapper;
import com.leyou.mapper.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Classname SpecGroupServiceImpl
 * @Description TODO
 * @Date 2021/3/4 13:00
 * @Created by MingChao Hao
 */

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecParam> findParamList(Long gid, Long cid, Boolean searching) {
        QueryWrapper<SpecParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",gid).or().eq("cid",cid).or().eq("searching",searching);
        List<SpecParam> paramList = specParamMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(paramList)){
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }
        return paramList;
    }

    @Override
    public List<SpecGroup> findGroupByCid(Long cid) {
        QueryWrapper<SpecGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        List<SpecGroup> groupList = specGroupMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(groupList)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return groupList;
    }

    @Override
    public int addSpecGroup(SpecGroup specGroup) {
        return specGroupMapper.insert(specGroup);
    }
}
