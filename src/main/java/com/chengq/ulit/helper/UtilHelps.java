package com.chengq.ulit.helper;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UtilHelps {

    /**
     * 自定义函数去重
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static final List<String> ENTITY_BASE_FIELDS = Arrays.asList("id", "creatorTime", "creatorUserId", "creatorUserName", "lastModifyTime",
            "lastModifyUserId", "lastModifyUserName", "deleteMark", "deleteTime", "deleteUserId", "version");

    public static String formatBelongTime(String belongTime) {
        if (!ToolUtil.isNumeric(belongTime)) {
            return belongTime;
        }
        Calendar calendar = new GregorianCalendar(1900, Calendar.JANUARY, -1);
        Date d = calendar.getTime();
        Date date = DateUtils.addDays(d, Integer.parseInt(belongTime));
        System.out.println(DateUtil.format(date, "yyyy年MM月"));
        return DateUtil.format(date, "yyyy年MM月");
    }

    /***
     * 校验年月
     * @param date
     * @return
     */
    public static boolean checkYearMonth(String date) {
        if (ToolUtil.isEmpty(date)) {
            return false;
        }
        String regex = "^\\d{4}年(1[0-2]|0[1-9])月$";
        return date.matches(regex);
    }

    public static String getGroupKey(String orgId, String userId) {
        return String.join("-", orgId, userId);
    }

    /**
     * uuid去横杠
     *
     * @return
     */
    public static String uuid() {
        String s = UUID.randomUUID().toString();
        return s.replace("-", "");

    }


    /**
     * 处理图片数据
     *
     * @param images
     * @return
     */
    public static String dealImageData(String images) {
        String img = null;
        if (ToolUtil.isNotEmpty(images)) {
            JSONArray jsonArray = JSON.parseArray(images);
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if (jsonObject != null) {
                        String url = jsonObject.getString("url");
                        if (ToolUtil.isNotEmpty(url) && (url.endsWith(".png") || url.endsWith(".jpg"))) {
                            img = url;
                            break;
                        }
                    }
                }
            }
        }
        return img;
    }

    /**
     * 计算两个时间相差年月日
     *
     * @param fromDate
     * @param toDate
     * @return 1, 2, 1
     */
    public static String getYearMonthDay(Date fromDate, Date toDate) {
        Period period = Period.between(LocalDate.parse(convertDayFormat(fromDate)),
                LocalDate.parse(convertDayFormat(toDate)));
        StringBuffer sb = new StringBuffer();
        sb.append(period.getYears()).append(",")
                .append(period.getMonths()).append(",")
                .append(period.getDays());
        return sb.toString();
    }


    /**
     * 获取本周0点
     *
     * @return
     */
    public static Date getTimesWeekMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }


    /**
     * 获取年
     *
     * @param dt
     * @return
     */
    public static Integer getYear(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @param dt
     * @param isActual 是否为实际月
     * @return
     */
    public static Integer getMonth(Date dt, Boolean isActual) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        return isActual ? calendar.get(Calendar.MONTH) + 1 : calendar.get(Calendar.MONTH);
    }

    /**
     * 获取日
     *
     * @param dt
     * @return
     */
    public static Integer getDay(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        return calendar.get(Calendar.DATE);
    }


    /**
     * 字符串转Date
     *
     * @param date
     * @return
     */
    public static Date convertDate(String date, String format) {
        SimpleDateFormat simpleDateFormat = null;
        try {
            simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取当月天数
     *
     * @param year
     * @param month(注意获取月份需要+1,也就是正常的月份)
     * @return
     */
    public static Integer getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取时间月份天数
     *
     * @param fromDate
     * @return
     */
    public static Integer getMonthLastDay(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        return getMonthLastDay(year, month);
    }


    /**
     * 时间转成00:00:00
     *
     * @param fromDate
     * @return
     */
    public static String convertDateDayFormat(Date fromDate) {
        Date date = convertDateDay(fromDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return format.format(date);
    }

    /**
     * 获取最小时间默认(1900-01-01)
     *
     * @return
     */
    public static Date getMinDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse("1900-01-01");
        } catch (Exception e) {
            log.info("获取最小时间getMinDate异常{}", e.getMessage());
        }
        return date;
    }


    /**
     * 获取最小时间默认(1900-01-01 00:00:00)
     *
     * @return
     */
    public static String getMinDateFormat() {
        return "1900-01-01 00:00:00";
    }

    /**
     * 时间转成23:59:59
     *
     * @param fromDate
     * @return
     */
    public static Date convertDateDayLast(Date fromDate) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(fromDate);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();

    }

    /**
     * 时间转成23:59:59 （yyyy-MM-dd HH:mm:ss）
     *
     * @param fromDate
     * @return
     */
    public static String convertDateDayLastFormat(Date fromDate) {
        fromDate = convertDateDayLast(fromDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(fromDate);
    }

    /**
     * 时间转成 yyyy-MM-dd
     *
     * @param fromDate
     * @return
     */
    public static String convertDayFormat(Date fromDate) {
        fromDate = convertDateDayLast(fromDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(fromDate);
    }

    /**
     * 时间转成 yyyy-MM-dd HH:mm:ss
     *
     * @param fromDate
     * @return
     */
    public static String convertDayLongFormat(Date fromDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(fromDate);
    }

    /**
     * 时间转成 yyyy-MM-dd 00:00:00
     *
     * @param fromDate
     * @return
     */
    public static Date convertDateDay(Date fromDate) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(fromDate);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 00);
        calendarEnd.set(Calendar.MINUTE, 00);
        calendarEnd.set(Calendar.SECOND, 00);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }

    /**
     * 获取当月最后一天
     *
     * @param fromDate
     * @return
     */
    public static Date getDateLastDay(Date fromDate) {
        //获取当前月最后一天
        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取当月第一天
     *
     * @param fromDate
     * @return
     */
    public static Date getDateOneDay(Date fromDate) {
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }


    /**
     * 计算两个时间相差天数
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static Integer getDaysBetween(Date fromDate, Date toDate) {

        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            fromDate = sdf.parse(sdf.format(fromDate));
            toDate = sdf.parse(sdf.format(toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(toDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 转换最小时间(1900-01-01)返回
     *
     * @param fromDate     时间
     * @param isSimpleDate 是否需要时分秒
     * @return
     */
    public static String convertMinDate(Date fromDate, Boolean isSimpleDate) {
        String date = convertDayFormat(fromDate);
        if (date.equals("1900-01-01")) {
            return "-";
        } else {
            if (isSimpleDate) {
                return convertDayLongFormat(fromDate);
            } else {
                return date;
            }
        }
    }

    /**
     * 判断是否为最小值(1900-01-01)
     *
     * @param fromDate
     * @return
     */
    public static Boolean isMinDate(Date fromDate) {

        String date = "";
        try {
            date = convertDayFormat(fromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date.equals("1900-01-01"))
            return true;
        else {
            return false;
        }
    }

    /**
     * 转化为"-"
     *
     * @param str
     * @return
     */
    public static String convertNullStr(String str) {
        return ToolUtil.isEmpty(str) ? "-" : str;
    }

    /**
     * 自动前面补充0 (如 1,01,001,0001)
     *
     * @param code 需要添加的字符串
     * @param num  位数
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));

        return result;
    }

    /**
     * 数据正负转换
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal convertNegative(BigDecimal bigDecimal) {
        return NumberUtil.mul(bigDecimal, -1);
    }

    /**
     * 计算两个时间天的集合(yyyy-MM-dd)
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getBetweenDateStrList(Date startTime, Date endTime) {
        return collectLocalDates(LocalDate.parse(convertDayFormat(startTime)),
                LocalDate.parse(convertDayFormat(endTime)));
    }

    /**
     * 计算两个时间天的集合(yyyy-MM-dd)
     *
     * @author ChengQ
     * @date 2023-06-29 16:31:30
     */
    public static List<Date> getBetweenDateList(Date startTime, Date endTime) {
        List<String> betweenDayDateList = getBetweenDateStrList(startTime, endTime);
        List<Date> collect = null;
        if (ToolUtil.isNotEmpty(betweenDayDateList)) {
            collect = betweenDayDateList.stream().map(x -> DateUtil.parse(x)).collect(Collectors.toList());
        }
        return collect;
    }


    public static List<String> collectLocalDates(LocalDate start, LocalDate end) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(start, localDate -> localDate.plusDays(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.DAYS.between(start, end) + 1)
                // 由于最后要的是字符串，所以map转换一下
                .map(LocalDate::toString)
                // 把流收集为List
                .collect(Collectors.toList());
    }

    /**
     * 计算两个时间月的集合(yyyy-MM)
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getBetweenMonthDateList(Date startTime, Date endTime) {
        ArrayList<String> result = new ArrayList();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(startTime);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(endTime);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(convertFormat(curr.getTime(), "yyyy-MM"));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 自定义时间format
     *
     * @param time
     * @param format
     * @return
     */
    public static String convertFormat(Date time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(time);
    }

    /**
     * 月头到月头，月末到月末处理
     *
     * @param dt
     * @param monthNum
     * @return
     */
    public static Date getDateMonthConvert(Date dt, int monthNum) {
        Date newDate;
        Date dateDt = dt;
        Calendar b = Calendar.getInstance();
        b.setTime(dateDt);
        int lastDay = b.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = b.get(Calendar.DAY_OF_MONTH);
        if (lastDay != now) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(dateDt);
            calendar1.add(Calendar.MONTH, monthNum);
            newDate = calendar1.getTime();
        } else {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(dateDt);
            calendar1.add(Calendar.MONTH, monthNum);
            calendar1.set(Calendar.DAY_OF_MONTH, 1);
            calendar1.roll(Calendar.DAY_OF_MONTH, -1);
            newDate = calendar1.getTime();
        }
        return newDate;
    }


    /**
     * 保留小数转化(四舍五入)
     *
     * @param scale  小数位
     * @param source
     * @return
     */
    public static BigDecimal convertBigDecimal(int scale, BigDecimal source) {
        return source.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留小数转化(四舍五入)字符串
     *
     * @param scale  小数位
     * @param source
     * @return
     */
    public static String convertDecimalFormat(Integer scale, BigDecimal source) {
        if (ToolUtil.isNotEmpty(scale)) {
            source = convertBigDecimal(scale, source);
        }
        return source.toString();
    }

    public static String toJson(Object ob) {
        if (ToolUtil.isEmpty(ob)) {
            return null;
        }
        return JSONObject.toJSONString(ob);
    }

    /**
     * 将多个值合并成一个字符串，-分割
     *
     * @param ks
     * @return
     */
    public static String concatKey(Object... ks) {
        if (ToolUtil.isEmpty(ks)) {
            return "";
        }
        List<String> keys = new ArrayList<>(ks.length);
        for (Object k : ks) {
            if (k == null) {
                keys.add("");
            } else {
                if (k instanceof Collection) {
                    keys.add(JSON.toJSONString(k));
                } else if (k instanceof Date) {
                    keys.add(DateUtil.formatDateTime((Date) k));
                } else {
                    keys.add(k.toString());
                }
            }
        }
        return String.join("-", keys);
    }

    /**
     * 使用指定的字段组装map
     *
     * @return
     */
    public static <T> Map<String, T> buildMapByFields(List<T> list, List<String> fieldList) {
        Map<String, T> resultMap = new HashMap<>();
        if (ToolUtil.isEmpty(list)) {
            return resultMap;
        }
        if (fieldList == null) {
            fieldList = new ArrayList<>();
        }
        Class<?> clazz = list.get(0).getClass();
        Map<String, Field> fieldMap = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, a -> a, (k1, k2) -> k1));
        for (T t : list) {
            List<String> keyList = new ArrayList<>();
            for (String fieldName : fieldList) {
                Field field = fieldMap.get(fieldName);
                String key = "";
                if (field != null) {
                    try {
                        field.setAccessible(true);
                        key = UtilHelps.concatKey(field.get(t));
                    } catch (Exception ignore) {

                    }
                }
                keyList.add(key);
            }
            resultMap.put(String.join("-", keyList), t);
        }
        return resultMap;
    }

    public static List<String> findDates(String date, int num) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date dateTime = sdf.parse(date);
            return findDates(dateTime, num);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> findDates(Date beginDate, int num) {
        int minute = 30;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> listDate = new ArrayList<>();
        listDate.add(sdf.format(beginDate));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(beginDate);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(beginDate);
        calEnd.add(Calendar.DAY_OF_MONTH, 1);

        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.MINUTE, minute * num);
            if (calEnd.after(calBegin)) {
                listDate.add(sdf.format(calBegin.getTime()));
            } else {
                listDate.add(sdf.format(calEnd.getTime()));
            }
        }

        return listDate;
    }

    public static String getTimeRange(Date beginDate, Date endDate) {
        if (ToolUtil.isEmpty(beginDate) || ToolUtil.isEmpty(endDate)) {
            return "";
        }
        String startTime = DateUtil.format(beginDate, DatePattern.NORM_TIME_PATTERN);
        String endTime = DateUtil.format(beginDate, DatePattern.NORM_TIME_PATTERN);
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            return "";
        }
        return startTime.substring(0, 5) + "~" + endTime.substring(0, 5);
    }

    public static Date chargeDate(Date sourceDate, Date targetDate, boolean last) {
        try {
            if (ToolUtil.isEmpty(sourceDate) || ToolUtil.isEmpty(targetDate)) {
                return sourceDate;
            }
            if (last) {
                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(sourceDate);
                calEnd.add(Calendar.DAY_OF_MONTH, 1);
                sourceDate = calEnd.getTime();
            }

            String startTime = DateUtil.format(sourceDate, DatePattern.NORM_DATE_PATTERN);
            String endTime = DateUtil.format(targetDate, DatePattern.NORM_TIME_PATTERN);
            String dateTime = startTime + " " + endTime;
            return DatePattern.NORM_DATETIME_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sourceDate;
    }

    public static Date chargeDateStr(Date sourceDate, String targetDate, boolean last) {
        try {
            if (ToolUtil.isEmpty(sourceDate) || ToolUtil.isEmpty(targetDate)) {
                return sourceDate;
            }
            if (last) {
                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(sourceDate);
                calEnd.add(Calendar.DAY_OF_MONTH, 1);
                sourceDate = calEnd.getTime();
            }

            String startTime = DateUtil.format(sourceDate, DatePattern.NORM_DATE_PATTERN);
            String dateTime = startTime + " " + targetDate;
            return DatePattern.NORM_DATETIME_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sourceDate;
    }

    /**
     * 车牌转换 (如:粤-)
     *
     * @param carNo
     * @return
     */
    public static String convertCarNo(String carNo) {
        String str = "";
        if (ToolUtil.isNotEmpty(carNo)) {
            String trim = carNo.replaceAll("\\r|\n|\t", "")
                    .replaceAll(" ", "")
                    .replaceAll("-", "").trim();

            for (int i = 0; i < trim.length(); i++) {
                String item = String.valueOf(trim.charAt(i));
                str += item.matches("[\u4e00-\u9fa5]") ? item + "-" : item;
            }
        }
        return str.toUpperCase();
    }

    /**
     * 清除车牌-
     *
     * @param carNo
     * @return
     */
    public static String clearCarNo(String carNo) {
        String str = "";
        if (ToolUtil.isNotEmpty(carNo)) {
            str = carNo.replaceAll("\\r|\n|\t", "")
                    .replaceAll(" ", "")
                    .replaceAll("-", "").trim();
        }
        return str.toUpperCase();
    }

    /**
     * 身份证转****
     *
     * @param idCardNum
     * @return
     */
    public static String convertIdCardNum(String idCardNum) {
        String res = "";
        if (ToolUtil.isNotEmpty(idCardNum)) {
            StringBuilder stringBuilder = new StringBuilder(idCardNum);
            res = stringBuilder.replace(idCardNum.length() > 6 ? 6 : idCardNum.length(), 14, "********").toString();
        }
        return res;
    }

    /**
     * 将对象的id、创建信息、修改信息、删除信息置空
     */
    public static <T> T resetBaseField(T o) {
        if (o == null) {
            return null;
        }
        List<Field> declaredFields = getClassFields(o.getClass());
        for (Field field : declaredFields) {
            if (ENTITY_BASE_FIELDS.contains(field.getName())) {
                try {
                    field.setAccessible(true);
                    field.set(o, null);
                } catch (Exception ignore) {
                }
            }
        }
        return o;
    }

    /**
     * 获取类属性(含父类)
     *
     * @param clazz
     * @return
     */
    public static List<Field> getClassFields(Class<?> clazz) {
        List<Field> list = new ArrayList<>();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            if (ToolUtil.isNotEmpty(fields)) {
                list.addAll(Arrays.asList(fields));
            }
            // 父类
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    /**
     * 拷贝字段(不拷贝id、创建信息、修改信息、删除信息等基本字段)
     *
     * @param source
     * @param target
     */
    public static <T> T copyProperties(Object source, T target) {
        assert source != null && target != null;
        BeanUtil.copyProperties(source, target, ENTITY_BASE_FIELDS.toArray(new String[0]));
        return target;
    }

    public static <T> T toBean(Object source, Class<T> clazz) {
        return resetBaseField(BeanUtil.toBean(source, clazz));
    }


    /**
     * 根据对象取对象字段名字值
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定注解字段值
     *
     * @param object
     * @param c
     * @return
     */
    public static Object getAnnotationValue(Object object, Class c) {
        try {
            Object value = null;
            // 获取类变量注解：
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(c)) {
                    f.setAccessible(true);
                    value = f.get(object);
                    break;
                }
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象复制
     *
     * @param t
     * @param fieldName
     * @param value
     * @param <T>
     */
    public static <T> void setValue(T t, String fieldName, Object value) {
        try {
            Class<T> tClass = (Class<T>) t.getClass();
            Field declaredField = tClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(t, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static String toString(Object str) {
        if (ToolUtil.isEmpty(str)) {
            return "";
        } else {
            return str.toString();
        }
    }

    /**
     * aes加密 配套解密 decryptedText
     *
     * @author ChengQ
     * @date 2023-07-03 11:04:41
     */
    public static String encryptedText(String plainText, String privateText) {
        String encodedText = "";
        try {
            if (ToolUtil.isEmpty(privateText)) {
                privateText = "base64";
            }
            encodedText = cn.hutool.core.codec.Base64.encode(privateText + plainText);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return encodedText;
    }

    /**
     * aes解密  配套加密 encryptedText
     *
     * @author ChengQ
     * @date 2023-07-03 11:05:00
     */
    public static String decryptedText(String encryptedText, String privateText) {
        String decodedText = "";
        try {
            if (ToolUtil.isEmpty(privateText)) {
                privateText = "base64";
            }
            decodedText = cn.hutool.core.codec.Base64.decodeStr(encryptedText);
            decodedText = decodedText.replaceFirst(privateText, "");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return decodedText;
    }


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

    public static <T, R> List<R> beanToList(Collection<T> list, Class<R> rClass) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> BeanUtil.toBean(x, rClass)).collect(Collectors.toList());
    }

    public static <T, R> Set<R> beanToSet(Collection<T> list, Class<R> rClass) {
        if (ToolUtil.isEmpty(list)) {
            return null;
        }
        return list.stream().map(x -> BeanUtil.toBean(x, rClass)).collect(Collectors.toSet());
    }

}


