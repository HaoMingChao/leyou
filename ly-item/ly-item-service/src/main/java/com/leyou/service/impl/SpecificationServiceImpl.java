package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.SpecGroupVoMapper;
import com.leyou.mapper.SpecParamMapper;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecificationService;
import com.leyou.vo.SpecGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SpecGroupServiceImpl
 * @Description TODO
 * @Date 2021/3/4 13:00
 * @Created by MingChao Hao
 */

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupVoMapper specGroupVoMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroupVo> findListGroupByCid(Long cid) {
        //查询规格组
        List<SpecGroupVo> specGroupVos = findGroupByCid(cid);
        //查询规格组
        List<SpecParam> specParams = findParamList(null, cid, null);
        System.out.println("specParams = " + specParams);
        //先把规格组编程map key为规格组di 值为规格组对应的所有参数
        Map<Long,List<SpecParam>> map = new HashMap<>();
        for (SpecParam specParam : specParams) {
            //判断map里边的key是否存在
            if (!map.containsKey(specParam.getGroupId())){
                //这个组id在map中不存在,新增一个list
                map.put(specParam.getGroupId(),new ArrayList<>());
            }
            map.get(specParam.getGroupId()).add(specParam);
        }
        //填充specParam到specGroupVo
        for (SpecGroupVo specGroupVo : specGroupVos) {
            specGroupVo.setSpecParams(map.get(specGroupVo.getId()));
        }
        return specGroupVos;
    }

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
    public int addSpecGroupVo(SpecGroupVo specGroupVo) {
        return specGroupVoMapper.insert(specGroupVo);
    }

    @Override
    public List<SpecGroupVo> findGroupByCid(Long cid) {
//        QueryWrapper<SpecGroupVo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("cid",cid);
//        List<SpecGroupVo> groupList = specGroupVoMapper.selectList(queryWrapper);
        List<SpecGroupVo> groupList = specGroupVoMapper.selectByCid(cid);
        System.out.println("groupList = " + groupList);
        if (CollectionUtils.isEmpty(groupList)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return groupList;
    }
}