package com.leyou.service;

import com.leyou.pojo.SpecParam;
import com.leyou.vo.SpecGroupVo;

import java.util.List;

public interface SpecificationService {
    List<SpecGroupVo> findGroupByCid(Long cid);

    List<SpecParam> findParamList(Long gid, Long cid, Boolean searching);

    int addSpecGroupVo(SpecGroupVo specGroupVo);

    List<SpecGroupVo> findListGroupByCid(Long cid);
}
