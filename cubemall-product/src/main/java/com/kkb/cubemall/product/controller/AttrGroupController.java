package com.kkb.cubemall.product.controller;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.service.AttrGroupService;
import com.kkb.cubemall.product.service.AttrService;
import com.kkb.cubemall.product.service.CategoryService;
import com.kkb.cubemall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组表
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-22 11:03:03
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @RequestMapping("/attr/relation/delete")// {attrId: 19, attrGroupId: 8}
    public R deleteAttrGroupAttrRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteRelation(vos);
        return R.ok();
    }

    @RequestMapping("{attrgroupId}/attr/relation")
    public R getAttrRelationGroup(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entityList= attrService.getRelationAttrs(attrgroupId);
        return R.ok().put("data", entityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{categoryId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId) {
        // PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, categoryId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("id") Long id) {
        AttrGroupEntity attrGroup = attrGroupService.getById(id);
        // 查询出 categoryPath 的路径
        Integer categoryId = attrGroup.getCategoryId();
        // 查询 categoryId 的祖宗 id
        Long[] categoryPath = categoryService.findCategoryPath(categoryId);
        attrGroup.setCategoryPath(categoryPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] ids) {
        attrGroupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
