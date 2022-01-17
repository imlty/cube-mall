package com.kkb.cubemall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.service.AttrAttrgroupRelationService;
import com.kkb.cubemall.product.service.AttrService;
import com.kkb.cubemall.product.service.CategoryService;
import com.kkb.cubemall.product.vo.AttrGroupReationVo;
import com.kkb.cubemall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.service.AttrGroupService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 属性分组表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 14:04:42
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

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;



    @GetMapping("{attrGroupId}/attr/relation")
    public R getAttrRelation(@PathVariable("attrGroupId") Long attrGroupId){
        List<AttrEntity> entities = attrService.getRelationAttr(attrGroupId);
        return R.ok().put("data",entities);

    }

    @PostMapping("attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupReationVo[] vos){
        attrService.deleteRalation(vos);
        return R.ok();

    }





    /**
     * 列表，不同分类加载不同的属性分组
     * categoryId 分类ID
     */
    @RequestMapping("/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable Integer categoryId){
        PageUtils page = attrGroupService.queryPage(params,categoryId);

        return R.ok().put("page", page);
    }

    /**
     * 根据分类ID获取属性分组以及对应的属性
     * @param categoryId
     * @return
     */

    @GetMapping("/{categoryId}/withattr")
    public R getAttrGroupWithattr(@PathVariable("categoryId") Integer categoryId){
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithattrByCategroyId(categoryId);
        return R.ok().put("data", vos);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AttrGroupEntity attrGroup = attrGroupService.getById(id);
        attrGroup.setCategoryPath(categoryService.findCategoryPath(attrGroup.getCategoryId()));
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */


    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] ids){
		attrGroupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    @GetMapping("{attrGroupId}/noattr/relation")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId,
                          @RequestParam Map<String,Object> params){
        PageUtils page = attrService.getNoRelationAttr(params,attrGroupId);
        return R.ok().put("page",page);
    }

    @PostMapping("/attr/relation")
    public R relationAttr(@RequestBody List<AttrGroupReationVo> vos){
        attrAttrgroupRelationService.saveBatch(vos);

        return R.ok();
    }

}
