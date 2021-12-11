package com.kkb.cubemall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.vo.AttrRespVo;
import com.kkb.cubemall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.service.AttrService;



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
     * 请求路径 /product/attr/base/list/{categoryId}, 分页查询,模糊查询, 基本属性
     * 请求路径 /product/attr/sale/list/{categoryId}, 分页查询,模糊查询, 销售属性
     */
    @RequestMapping("/{attrType}/list/{categoryId}")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("categoryId") Long categoryId, @PathVariable("attrType") String attrType){
        PageUtils page = attrService.queryBaseAttrPage(params, categoryId, attrType);

        return R.ok().put("page", page);
    }


    /**
     * 指定属性信息的回显
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("id") Long id){
		// AttrEntity attr = attrService.getById(id);
        AttrRespVo attrRespVo =  attrService.getAttrInfo(id);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attrVo){
		attrService.saveAttr(attrVo);

        return R.ok();
    }

    /**
     * 修改提交操作
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attrVo){
		attrService.updateAttr(attrVo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] ids){
		attrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
