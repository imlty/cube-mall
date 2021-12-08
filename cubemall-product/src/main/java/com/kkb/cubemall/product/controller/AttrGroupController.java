package com.kkb.cubemall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.service.AttrAttrgroupRelationService;
import com.kkb.cubemall.product.service.AttrService;
import com.kkb.cubemall.product.service.CategoryService;
import com.kkb.cubemall.product.vo.AttrGroupRelationVo;
import com.kkb.cubemall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.service.AttrGroupService;



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
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 获取分类下所有分组&关联属性
     * @return
     */
    @GetMapping("/{categoryId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("categoryId") Long categoryId){
        //1.查询当前分类下的所有属性分组
        //2.查询当前属性分组下所有的属性
        List<AttrGroupWithAttrsVo> vos =  attrGroupService.getAttrGroupWithAttrsByCategoryId(categoryId);
        return R.ok().put("data", vos);
    }

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){
        attrAttrgroupRelationService.saveBatch(vos);

        return R.ok();
    }

    /**
     * 请求参数 [{attrId: 19, attrGroupId: 10}]
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteRelation(vos);

        return R.ok();
    }

    /**
     * 获取属性分组中, 当前分类下没有进行关联的所有属性
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    public R getNoAttrRelation(@RequestParam Map<String, Object> params, @PathVariable("attrGroupId") Long attrGroupId){
        PageUtils page = attrService.getNoRelationAttr(params, attrGroupId);

        return R.ok().put("page", page);
    }

    /**
     * 获取属性分组所关联的所有属性
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public R getAttrRelation(@PathVariable("attrGroupId") Long attrGroupId){
        List<AttrEntity> entities = attrService.getRelationAttr(attrGroupId);

        return R.ok().put("data", entities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{categoryId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId){
        //PageUtils page = attrGroupService.queryPage(params);

        PageUtils page = attrGroupService.queryPage(params, categoryId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("id") Long id){
		AttrGroupEntity attrGroup = attrGroupService.getById(id);
		//查询出categoryPath的路径
        //当前分类ID
        Integer categoryId = attrGroup.getCategoryId();
        //查询出当前分类ID的 祖宗ID
        Long[] path = categoryService.findCategoryPath(categoryId);
        attrGroup.setCategoryPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

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

}
