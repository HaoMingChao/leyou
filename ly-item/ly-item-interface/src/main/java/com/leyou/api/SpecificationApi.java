package com.leyou.api;

import com.leyou.pojo.SpecParam;
import com.leyou.vo.SpecGroupVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecificationApi {
    /**
     * 查询参数
     * @param gid 根据gid
     * @param cid 根据cid
     * @param searching 根据搜索条件
     * @return
     */
    @GetMapping("/spec/params")
    List<SpecParam> findParamByGid(@RequestParam(value = "gid",required = false)Long gid, @RequestParam(value = "cid",required = false)Long cid, @RequestParam(value = "searching",required = false)Boolean searching);

    /**
     * 根据cid查询分类组
     * @param cid
     * @return
     */
    @GetMapping("/spec/group")
    List<SpecGroupVo> findGroupByCid(@RequestParam("cid")Long cid);
}
