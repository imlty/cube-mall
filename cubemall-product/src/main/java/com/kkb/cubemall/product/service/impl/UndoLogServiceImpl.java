package com.kkb.cubemall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.common.utils.PageUtils;
import com.kkb.common.utils.Query;
import com.kkb.cubemall.product.dao.UndoLogDao;
import com.kkb.cubemall.product.entity.UndoLogEntity;
import com.kkb.cubemall.product.service.UndoLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("undoLogService")
public class UndoLogServiceImpl extends ServiceImpl<UndoLogDao, UndoLogEntity> implements UndoLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UndoLogEntity> page = this.page(
                new Query<UndoLogEntity>().getPage(params),
                new QueryWrapper<UndoLogEntity>()
        );

        return new PageUtils(page);
    }

}
