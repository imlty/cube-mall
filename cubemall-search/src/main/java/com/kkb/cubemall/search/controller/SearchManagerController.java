package com.kkb.cubemall.search.controller;


import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.search.service.SearchManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/searchManage")
public class SearchManagerController {

    @Autowired
    private SearchManagerService searchManagerService;
    private volatile Boolean executingFlag = false;

    @GetMapping("/importAll")
    public R importAll(){
        if (!executingFlag) {
            synchronized (this) {
                if (!executingFlag) {
                    executingFlag = true;
                    searchManagerService.importAll();
                    return R.ok("全部数据导入成功");
                }
            }
        }
        return R.ok("数据导入正在执行，请勿重复执行！");
    }

    @GetMapping("/putonsale/{spuId}")
    public R putOnSale(@PathVariable Long spuId) {
        R r = searchManagerService.addSpuById(spuId);
        return r;
    }

}
