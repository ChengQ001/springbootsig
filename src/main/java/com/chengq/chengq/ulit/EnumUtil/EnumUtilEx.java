package com.chengq.chengq.ulit.EnumUtil;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EnumUtilEx {
    /**
     * 必填 枚举Class
     */
    private Class<?> enumsClass;

    /**
     * 所有枚举容器
     */
    private Map<String, List<SelectResp>> enumsMap = Maps.newHashMap();

    public static EnumUtilEx init(Class<?> enumsClass) {
        EnumUtilEx enumUtil = new EnumUtilEx();
        enumUtil.enumsClass = enumsClass;
        Class clazz = enumsClass;
        Class[] innerClazz = clazz.getDeclaredClasses();
        for (Class cls : innerClazz) {
            List<SelectResp> list = Stream.of(cls.getEnumConstants())
                    .map(x -> {
                        IEnum iEnum = (IEnum) x;
                        SelectResp selectResp = new SelectResp();
                        selectResp.setText(iEnum.toString());
                        selectResp.setId(iEnum.getValue().toString());
                        return selectResp;
                    })
                    .collect(Collectors.toList());
            enumUtil.enumsMap.put(cls.getSimpleName(), list);
        }
        return enumUtil;
    }

    /**
     * 枚举键值
     *
     * @param type
     * @return
     */
    public List<SelectResp> getEnumListByName(String type) {
        return enumsMap.get(type);
    }


}
