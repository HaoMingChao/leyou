package com.leyou.service;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {
    List<SpecGroup> findGroupByCid(Long cid);

    List<SpecParam> findParamList(Long gid, Long cid, Boolean searching);

    int addSpecGroup(SpecGroup specGroup);
}
