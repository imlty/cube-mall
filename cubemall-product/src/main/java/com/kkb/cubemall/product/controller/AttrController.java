package com.kkb.cubemall.product.controller;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.service.AttrAttrgroupRelationService;
import com.kkb.cubemall.product.service.AttrService;
import com.kkb.cubemall.product.vo.AttrRespVo;
import com.kkb.cubemall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 属性表
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-22 11:03:03
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     * product/attr/base/list/{categoryId} 基本属性列表查询
     * product/attr/sale/list/{categoryId} 销售属性列表查询
     */
    @RequestMapping("/{attrType}/list/{categoryId}")
    //@RequiresPermissions("product:attr:list")
    public R list(
            @RequestParam Map<String, Object> params,
            @PathVariable("attrType") String attrType,
            @PathVariable("categoryId") Long categoryId
    ) {

        PageUtils page = attrService.queryBaseAttrPage(params, attrType, categoryId);

        return R.ok().put("page", page);
    }


    /**
     * 指定属性信息的回显
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("id") Long id) {
        AttrRespVo attrRespVo = attrService.getAttrInfo(id);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attrVo) {
        attrService.saveAttrVo(attrVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attrVo) {
        attrService.updateAttr(attrVo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] ids) {
        attrService.removeByIds(Arrays.asList(ids));

        // 删除关联表信息
        attrService.removeAttrAttrgroupRelationByIds(ids);

        return R.ok();
    }

}
