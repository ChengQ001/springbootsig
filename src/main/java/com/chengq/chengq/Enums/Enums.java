package com.chengq.chengq.Enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public class Enums {

    public enum StatusEnum implements IEnum<Integer> {
        /**
         * 0好  1很好 2差
         */
        SELF_SEND(0, "否"),
        CONSOLE_SEND(1, "是"),
        ;
        private Integer code;
        private String name;

        StatusEnum(final Integer code, final String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public Integer getValue() {
            return code;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
