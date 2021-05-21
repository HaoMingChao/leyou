package com.leyou.controller;

import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupVoService;
import com.leyou.service.SpecParamService;
import com.leyou.service.SpecificationService;
import com.leyou.vo.SpecGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname SpecificationController
 * @Description TODO
 * @Date 2021/3/4 13:03
 * @Created by MingChao Hao
 */

@RestController
@RequestMapping("/spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private SpecGroupVoService SpecGroupVoService;
    @Autowired
    private SpecParamService specParamService;

    /**
     * 根据分类查询规格组及组内参数
     * @param cid
     * @return
     */
    @GetMapping("/group")
    public ResponseEntity<List<SpecGroupVo>> findListGroupByCid(@RequestParam("cid")Long cid){
        return ResponseEntity.ok(specificationService.findListGroupByCid(cid));
    }

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroupVo>> findGroupByCid(@PathVariable("cid")Long cid){
        return ResponseEntity.ok(specificationService.findGroupByCid(cid));
    }


    /**
     * 查询参数
     * @param gid 根据gid
     * @param cid 根据cid
     * @param searching 根据搜索条件
     * @return
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> findParamByGid(@RequestParam(value = "gid",required = false)Long gid,@RequestParam(value = "cid",required = false)Long cid,@RequestParam(value = "searching",required = false)Boolean searching){
        return ResponseEntity.ok(specificationService.findParamList(gid,cid,searching));
    }

    /**
     * 添加组
     * @param group
     * @return
     */
    @RequestMapping("/group")
    public ResponseEntity<Boolean> addSpecGroupVo(@RequestBody SpecGroupVo group){
        System.out.println("group = " + group.getId());
        return ResponseEntity.ok(SpecGroupVoService.saveOrUpdate(group));
    }

    /**
     * 根据id查询组
     * @param id
     * @return
     */
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Boolean> deleteSpeGroupById(@PathVariable(value = "id")Long id){
        return ResponseEntity.ok(SpecGroupVoService.removeById(id));
    }

    /**
     * 添加参数
     * @param specParam
     * @return
     */
    @RequestMapping("/param")
    public ResponseEntity<Boolean> addSpecParam(@RequestBody SpecParam specParam){
        return ResponseEntity.ok(specParamService.saveOrUpdate(specParam));
    }

    /**
     * 根据id删除参数
     * @param id
     * @return
     */
    @DeleteMapping("/param/{id}")
    public ResponseEntity<Boolean> deleteSpecParamById(@PathVariable(value = "id")Long id){
        return ResponseEntity.ok(specParamService.removeById(id));
    }

}