package com.chengq.ulit.helper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 工具集
 *
 * @author cq
 * @Date 2018/3/18 21:55
 */
public class ToolUtil extends ValidateUtil {

    /**
     * 默认密码盐长度
     */
    public static final int SALT_LENGTH = 6;

    //校验文本标识
    public static final int V_TEXT = 1;
    //校验整型
    public static final int V_INTEGER = 2;
    //校验长整型
    public static final int V_LONG = 3;
    //校验大数类金额是否正确
    public static final int V_BIGD_ECIMAL = 4;
    //校验金额小数点后面是否大于2位
    public static final int V_BIGD_ECIMAL_UP = 7;
    //校验日期年月日
    public static final int V_DATE = 5;
    //校验日期年月日时分秒
    public static final int V_DATE_TIME = 6;

    /**
     * 获取随机字符,自定义长度
     *
     * @author fengshuonan
     * @Date 2018/3/18 21:55
     */
    public static String getRandomString(int length) {

        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * md5加密(加盐)
     *
     * @author fengshuonan
     * @Date 2018/3/18 21:56
     */
    public static String md5Hex(String password, String salt) {
        return md5Hex(password + salt);
    }

    /**
     * md5加密(不加盐)
     *
     * @author fengshuonan
     * @Date 2018/3/18 21:56
     */
    public static String md5Hex(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < bs.length; i++) {
                if (Integer.toHexString(0xFF & bs[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & bs[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & bs[i]));
                }
            }
            return md5StrBuff.toString();
        } catch (Exception e) {
            //throw new ServiceException(CoreExceptionEnum.ENCRYPT_ERROR);
        }
        return null;
    }

    /**
     * 过滤掉掉字符串中的空白
     *
     * @author fengshuonan
     * @Date 2018/3/22 15:16
     */
    public static String removeWhiteSpace(String value) {
        if (isEmpty(value)) {
            return "";
        } else {
            return value.replaceAll("\\s*", "");
        }
    }

    /**
     * 获取某个时间间隔以前的时间 时间格式：yyyy-MM-dd HH:mm:ss
     *
     * @author stylefeng
     * @Date 2018/5/8 22:05
     */
    public static String getCreateTimeBefore(int seconds) {
        long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();
        Date date = new Date(currentTimeInMillis - seconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 获取异常的具体信息
     *
     * @author fengshuonan
     * @Date 2017/3/30 9:21
     * @version 2.0
     */
    public static String getExceptionMsg(Throwable e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }


    /**
     * 获取ip地址
     *
     * @author fengshuonan
     * @Date 2018/5/15 下午6:36
     */
    public static String getIP() {
        try {
            StringBuilder IFCONFIG = new StringBuilder();
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        IFCONFIG.append(inetAddress.getHostAddress().toString() + "\n");
                    }

                }
            }
            return IFCONFIG.toString();

        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拷贝属性，为null的不拷贝
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午4:41
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true).ignoreError());
    }

    public static void copyPropertiesNull(Object source, Object target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(false).ignoreError());
    }

    /**
     * 判断是否是windows操作系统
     *
     * @author stylefeng
     * @Date 2017/5/24 22:34
     */
    public static Boolean isWinOs() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取临时目录
     *
     * @author stylefeng
     * @Date 2017/5/24 22:35
     */
    public static String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 把一个数转化为int
     *
     * @author fengshuonan
     * @Date 2017/11/15 下午11:10
     */
    public static Integer toInt(Object val) {
        if (val instanceof Double) {
            BigDecimal bigDecimal = new BigDecimal((Double) val);
            return bigDecimal.intValue();
        } else {
            return Integer.valueOf(val.toString());
        }

    }

    /**
     * 是否为数字
     *
     * @author fengshuonan
     * @Date 2017/11/15 下午11:10
     */
    public static boolean isNum(Object obj) {
        try {
            Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 是否为数字或小数，包括负数
     *
     * @author fengshuonan
     * @Date 2017/11/15 下午11:10
     */
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }


    /**
     * 判断一个对象是否是时间类型
     *
     * @author stylefeng
     * @Date 2017/4/18 12:55
     */
    public static String dateType(Object o) {
        if (o instanceof Date) {
            return DateUtil.formatDate((Date) o);
        } else {
            return o.toString();
        }
    }

    /**
     * 当前时间
     *
     * @author stylefeng
     * @Date 2017/5/7 21:56
     */
    public static String currentTime() {
        return DateUtil.formatDateTime(new Date());
    }

    /**
     * 分割数组
     * 逗号分割
     *
     * @param str
     * @return
     */
    public static List<Long> splitByLong(String str) {
        List<Long> list = new ArrayList<>();
        if (isEmpty(str)) {
            return list;
        }
        String[] split = str.split(",");
        if (null != split && split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                if (!isEmpty(split[i])) {
                    list.add(Long.parseLong(split[i]));
                }
            }
        }
        return list;
    }

    /**
     * 分割数组
     * 逗号分割
     *
     * @param str
     * @return
     */
    public static List<String> splitByComma(String str) {
        List<String> list = new ArrayList<>();
        if (isEmpty(str)) {
            return list;
        }
        String[] split = str.split(",");
        if (null != split && split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                if (!isEmpty(split[i])) {
                    list.add(split[i]);
                }
            }
        }
        return list;
    }

    /**
     * @param str     分割数据源
     * @param charStr 分割的符号
     * @return
     */
    public static List<String> splitByComma(String str, String charStr) {
        List<String> list = new ArrayList<>();
        if (isEmpty(str)) {
            return list;
        }

        String[] split = str.split(charStr);
        if (null != split && split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            return list;
        }
        return list;
    }

    /**
     * 保留两位小数--整数不显示小数点
     *
     * @param d
     * @return
     */
    public static String holdDecimal(String d) {
        if (ToolUtil.isEmpty(d)) {
            return "";
        }
        BigDecimal value = new BigDecimal(d);
        int dec = value.intValue();
        BigDecimal v = value.subtract(new BigDecimal(dec + ""));
        if (v.compareTo(new BigDecimal(0)) == 0) {
            return dec + "";
        }
        String str = "";
        try {
            DecimalFormat myFormat = new DecimalFormat();
            myFormat.applyPattern("0.00");
            str = myFormat.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String formatDecimal(Long d) {
        return formatDecimal(new BigDecimal(String.valueOf(d)));
    }

    public static String formatDecimal(String d) {
        if (ToolUtil.isEmpty(d)) {
            return "0";
        }
        return formatDecimal(new BigDecimal(d));
    }

    public static String formatDecimal(BigDecimal d) {
        if (ToolUtil.isEmpty(d)) {
            return "0";
        }
        if (d.compareTo(new BigDecimal(0)) == 0) {
            return "0";
        }
        String str = "";
        try {
            DecimalFormat myFormat = new DecimalFormat();
            myFormat.applyPattern("0.00");
            return myFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String holdZeroDecimal(String d) {
        if (ToolUtil.isEmpty(d)) {
            return "0";
        }
        return holdZeroDecimal(new BigDecimal(d));
    }

    public static String holdZeroDecimal(BigDecimal d) {
        if (ToolUtil.isEmpty(d)) {
            return "0";
        }
        if (d.compareTo(new BigDecimal(0)) == 0) {
            return "0";
        }
        String str = "";
        try {
            BigDecimal result3 = d.divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);
            return result3.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String holdDecimal(BigDecimal d) {
        if (isEmpty(d)) {
            return "";
        }
        DecimalFormat myFormat = new DecimalFormat();
        myFormat.applyPattern("0.00");
        return myFormat.format(d);
    }

    public static String holdDecimal(double d) {
        if (isEmpty(d)) {
            return "";
        }
        DecimalFormat myFormat = new DecimalFormat();
        myFormat.applyPattern("0.00");
        return myFormat.format(d);
    }

    public static String holdDecimalMax(BigDecimal d) {
        BigDecimal bigDecimal = d.setScale(0, BigDecimal.ROUND_HALF_UP);
        DecimalFormat myFormat = new DecimalFormat();
        myFormat.applyPattern("#");
        return myFormat.format(bigDecimal);
    }

    public static String holdDecimalMax1(BigDecimal d) {
        BigDecimal bigDecimal = d.setScale(1, BigDecimal.ROUND_HALF_UP);
        DecimalFormat myFormat = new DecimalFormat();
        myFormat.applyPattern("0.#");
        return myFormat.format(bigDecimal);
    }

    public static String holdDecimalMax2(BigDecimal d) {
        BigDecimal bigDecimal = d.setScale(2, BigDecimal.ROUND_HALF_UP);
        DecimalFormat myFormat = new DecimalFormat();
        myFormat.applyPattern("0.##");
        return myFormat.format(bigDecimal);
    }

    public static String formatArea(String d) {
        if (ToolUtil.isEmpty(d)) {
            return "";
        }
        if (new BigDecimal(d).compareTo(BigDecimal.ZERO) <= 0) {
            return "";
        }
        return d;
    }

    /**
     * @author yaowq
     * @since 2021.4.14.014
     * 对象去重
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * @author yaowq
     * @since 2021.5.10.010
     * 金额分四舍五入到元--返回分
     */
    public static Long formatHoldLong(Long totalPrice) {
        BigDecimal divide = new BigDecimal(String.valueOf(totalPrice)).divide(new BigDecimal(100),
                0, BigDecimal.ROUND_HALF_UP);
        return divide.longValue() * 100;
    }

    public static <T> List<T> removeNull(List<? extends T> oldList) {
        // 临时集合
        List<T> listTemp = new ArrayList();
        for (int i = 0; i < oldList.size(); i++) {
            // 保存不为空的元素
            boolean isNotNull = checkObjFieldIsNotNull(oldList.get(i));
            if (isNotNull) {
                listTemp.add(oldList.get(i));
            }
        }
        return listTemp;
    }

    public static boolean checkObjFieldIsNotNull(Object obj) {
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object o = f.get(obj);
                if (ToolUtil.isNotEmpty(o)) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkObjFieldIsNull(Object obj) {
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(obj) != null) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 校验不为空的数据是否合法, 可以为空
     *
     * @param type
     * @param obj
     * @return true 合法数据，
     */
    public static boolean checkFieldLegalData(int type, Object obj) {
        boolean falg = true;
        if (ToolUtil.isEmpty(obj)) {
            return true;
        }
        try {
            switch (type) {
                case V_TEXT:
                    String.valueOf(obj);
                    break;
                case V_INTEGER:
                    Integer.valueOf(obj.toString());
                    break;
                case V_LONG:
                    Long.valueOf(obj.toString());
                    break;
                case V_BIGD_ECIMAL:
                    new BigDecimal(obj.toString());
                    break;
                case V_BIGD_ECIMAL_UP:
                    BigDecimal decimal = new BigDecimal(obj.toString());
                    String[] number = decimal.toString().split("\\.");
                    if (number.length > 1) {
                        String intNumber = new BigDecimal(number[1]).toString();
                        if (intNumber.length() > 2) {
                            falg = false;
                        }
                    }
                    break;
                case V_DATE:
                    DateUtil.parseDate(obj.toString());
                    falg = checkDate(obj.toString());
                    break;
                case V_DATE_TIME:
                    DateUtil.parseDateTime(obj.toString());
                    break;
            }
        } catch (Exception e) {
            falg = false;
        }
        return falg;
    }

    /**
     * 校验不为空的数据是否合法, 不允许为空
     *
     * @param type
     * @param obj
     * @return true 合法数据，
     */
    public static boolean checkFieldLegalNotNullData(int type, Object obj) {
        boolean falg = true;
        if (ToolUtil.isEmpty(obj)) {
            return false;
        }
        try {
            switch (type) {
                case V_TEXT:
                    String.valueOf(obj);
                    break;
                case V_INTEGER:
                    Integer.valueOf(obj.toString());
                    break;
                case V_LONG:
                    Long.valueOf(obj.toString());
                    break;
                case V_BIGD_ECIMAL:
                    new BigDecimal(obj.toString());
                    break;
                case V_DATE:
                    DateUtil.parseDate(obj.toString());
                    falg = checkDate(obj.toString());
                    break;
                case V_DATE_TIME:
                    DateUtil.parseDateTime(obj.toString());
                    break;
            }
        } catch (Exception e) {
            falg = false;
        }
        return falg;
    }


    /**
     * 比较两个时间，开始时间大于结束时间 返回1 ，小于返回 0 , 日期不正常返回 -1
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int checkTwoTime(String startTime, String endTime) {
        if (checkFieldLegalNotNullData(ToolUtil.V_DATE, startTime) && checkFieldLegalNotNullData(ToolUtil.V_DATE, endTime)) {
            Date startDate = DateUtil.parseDate(startTime);
            Date endDate = DateUtil.parseDate(endTime);
            if (startDate.compareTo(endDate) == 1) {
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     * 区间内，返回 true  区间外 false
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return s
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkDate(String input) {
        String[] dateFields = input.split("-");

        int year = 0;
        int month = 0;
        int day = 0;
        try {
            year = Integer.parseInt(dateFields[0]);
            month = Integer.parseInt(dateFields[1]);
            day = Integer.parseInt(dateFields[2]);
        } catch (Exception e) {
            return false;
        }

        List<Integer> bigMonthList = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
        List<Integer> smallMonthList = Arrays.asList(4, 6, 9, 11);

        if (month == 2) {
            if (year % 4 == 0) {// 闰年2月
                if (day > 0 && day < 30) {
                    return true;
                }
            } else {// 平年二月
                if (day > 0 && day < 29) {
                    return true;
                }
            }
        }

        if (bigMonthList.contains(month)) {// 大月
            if (day > 0 && day < 32) {
                return true;
            }
        } else if (smallMonthList.contains(month)) {// 小月
            if (day > 0 && day < 31) {
                return true;
            }
        } else {// 非法
            return false;
        }

        return false;
    }

    public static String cleanEmpty(String name) {
        if (null == name) {
            return "";
        }
        if (ToolUtil.isEmpty(name)) {
            return "";
        }
        return name;
    }
}
