package com.kkb.cubemall.common.utils;

import lombok.Data;
import lombok.Getter;

@Data
public class WareConstant {

    @Getter
    public enum PurchaseStatusEnum {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVE(2, "正在采购"),
        FINISH(3, "已完成"),
        HASERROR(4, "采购失败");
        private final Integer code;
        private final String msg;

        PurchaseStatusEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Getter
    public enum PurchaseDetailStatusEnum {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        BUYING(2, "正在采购"),
        FINISH(3, "已完成"),
        HASERROR(4, "采购失败");
        private final Integer code;
        private final String msg;

        PurchaseDetailStatusEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}
