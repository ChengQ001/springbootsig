package com.chengq.ulit.helper;

import cn.hutool.core.bean.BeanUtil;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 简化流操作
 *
 * @author chengqing
 */
public class StreamHelper {
    /**
     * 通过字段分组过滤出重复的数据，默认取第一条
     *
     * @param list
     * @param key
     * @param <T>
     * @return
     */
    public static <T> List<T> distinctList(Collection<T> list, Function<T, Object> key) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().collect(Collectors.toMap(
                //key
                key
                //value
                , value -> value,
                //重复规则，取前者或者后者,默认取前者
                (x, y) -> x
        )).values().stream().collect(Collectors.toList());
    }

    /**
     * 通过自定义字段分组
     *
     * @param list
     * @param key
     * @param <T>
     * @return
     */
    public static <T> Map<Object, List<T>> groupMap(Collection<T> list, Function<T, Object> key) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().collect(Collectors.groupingBy(key));
    }

    /**
     * 查询首条数据,如果为空默认指定返回值
     *
     * @param list
     * @param filter
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T findListFirst(Collection<T> list, Predicate<T> filter, T t) {
        if (ToolUtil.isEmpty(list)) {
            return t;
        }
        return list.stream().filter(filter).findFirst().orElse(t);
    }

    /**
     * 查询首条数据,如果为空默认返回值null
     *
     * @param list
     * @param filter
     * @param <T>
     * @return
     */
    public static <T> T findListFirst(Collection<T> list, Predicate<T> filter) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().filter(filter).findFirst().orElse(null);
    }

    /**
     * 转换对象
     *
     * @param list
     * @param rClass
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> toBeanList(Collection<T> list, Class<R> rClass) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> BeanUtil.toBean(x, rClass)).collect(Collectors.toList());
    }

    /**
     * 转换对象
     *
     * @param list
     * @param rClass
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Set<R> toBeanSet(Collection<T> list, Class<R> rClass) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> BeanUtil.toBean(x, rClass)).collect(Collectors.toSet());
    }

    public static <T> List<Long> toLongList(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> new Long(x.toString())).collect(Collectors.toList());
    }

    public static <T> List<Integer> toIntList(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> Integer.valueOf(x.toString())).collect(Collectors.toList());
    }

    public static <T> List<String> toStringList(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> x.toString()).collect(Collectors.toList());
    }

    public static <T> Set<Long> toLongSet(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return toLongList(list).stream().collect(Collectors.toSet());
    }

    public static <T> Set<Integer> toIntSet(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return toIntList(list).stream().collect(Collectors.toSet());
    }

    public static <T> Set<String> toStringSet(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return toStringList(list).stream().collect(Collectors.toSet());
    }

    public static <T> List<BigDecimal> toBigDecimalList(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> new BigDecimal(x.toString())).collect(Collectors.toList());
    }

    public static <T> Set<BigDecimal> toBigDecimalSet(Collection<T> list) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return toBigDecimalList(list).stream().collect(Collectors.toSet());
    }
}
