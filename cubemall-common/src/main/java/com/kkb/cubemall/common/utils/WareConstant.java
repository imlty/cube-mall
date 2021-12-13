package com.kkb.cubemall.common.utils;

import lombok.Data;
import lombok.Getter;

@Data
public class WareConstant {

    @Getter
    public enum PurchaseStatusEnum {
        CREATED(6),
        ASSIGNED(5),
        RECEIVE(4),
        HASERROR(2),
        FINISH(1);
        private final Integer code;

        PurchaseStatusEnum(Integer code) {
            this.code = code;
        }
    }

    @Getter
    public enum PurchaseDetailStatusEnum {
        ASSIGNED(1), BUYING(2), FINISH(3), HASERROR(4);
        private final int code;

        PurchaseDetailStatusEnum(int code) {
            this.code = code;
        }

    }

}
