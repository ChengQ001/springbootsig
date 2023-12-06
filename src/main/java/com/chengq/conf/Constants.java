package com.chengq.conf;

/**
 * 通用常量信息
 *
 * @author jnpf
 */
public class Constants {

    /**
     * token
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌有效期（分钟）
     */
    public final static long TOKEN_EXPIRE = 720;

    /**
     * swagger版本号
     */
    public final static String SWAGGER_VERSION = "3.1.0";

    /**
     * swagger版本号
     */
    public final static String USER_AGENT = "User-Agent";

    /**
     * 空集合字符串
     */
    public final static String EMPTY_LIST = "[]";

    /**
     * WPS 请求头token传值
     */
    public final static String WPS_TOKEN = "x-wps-weboffice-token";

    /**
     * WPS 请求头文件id传值
     */
    public final static String WPS_FILE_ID = "x-weboffice-file-id";
    public final static String WPS_FILE_SAVE_TYPE = "x-weboffice-save-type";
    public final static String WPS_FILE_SAVE_VERSION = "x-weboffice-save-version";

    public final static String[] prefixLabels= {"", "ZLHT-", "JF", "WYHT-", "CJ"};

    /**
     * 缴费单相关
     */
    public static final String CONTRACT_ORDER = String.format("$%s$", "合同编号");
    public static final String LEASE = String.format("$%s$", "合同乙方");
    public static final String BILL = String.format("$%s$", "账单列表");
    public static final String BILL2 = String.format("$%s$", "账单列表2");
    public static final String RESOURCE = String.format("$%s$", "合同房源");
    public static final String AREA = String.format("$%s$", "租赁面积");
    public static final String PAY_DATE = String.format("$%s$", "缴费期限");
    public static final String NAME = String.format("$%s$", "收款方账户名");
    public static final String BANK = String.format("$%s$", "收款方开户行");
    public static final String AMOUNT = String.format("$%s$", "收款方账号");
    public static final String RENT = String.format("$%s$", "租金及其它费项");
    public static final String LESSOR = String.format("$%s$", "合同甲方");
    public static final String OPEN_DATE = String.format("$%s$", "开具日期");
    public static final String RESOURCE_SHORT = String.format("$%s$", "房源简称");
    public static final String LESSOR_ADDRESS = String.format("$%s$", "甲方通讯地址");
    public static final String LESSOR_PERSON = String.format("$%s$", "甲方法定代表人");
    public static final String LESSOR_CODE = String.format("$%s$", "甲方统一社会信用代码");
    public static final String LESSOR_PHONE = String.format("$%s$", "甲方联系电话");

    public static final String LEASE_NAME = String.format("$%s$", "乙方账户名");
    public static final String LEASE_BANK = String.format("$%s$", "乙方开户行");
    public static final String LEASE_AMOUNT = String.format("$%s$", "乙方账号");
    public static final String LEASE_ADDRESS = String.format("$%s$", "乙方通讯地址");
    public static final String LEASE_PERSON = String.format("$%s$", "乙方法定代表人");
    public static final String LEASE_CODE = String.format("$%s$", "乙方统一社会信用代码");
    public static final String LEASE_PHONE = String.format("$%s$", "乙方联系电话");

    public static final String ZL_ZLYS = String.format("$%s$", "租赁月数");
    public static final String ZL_ZLQX = String.format("$%s$", "租赁期限");
    public static final String ZL_ZJDJ = String.format("$%s$", "租金单价");
    public static final String ZL_TJJG = String.format("$%s$", "调价间隔");
    public static final String ZL_TJJGSGZQ = String.format("$%s$", "调价间隔-首个周期");
    public static final String ZL_TJFD = String.format("$%s$", "调价幅度");
    public static final String ZL_ZLBZJ = String.format("$%s$", "租赁保证金");
    public static final String ZL_LYBZJ = String.format("$%s$", "履约保证金");
    public static final String ZL_WYGLFDJ = String.format("$%s$", "物业管理费单价");
    public static final String ZL_WYGLF = String.format("$%s$", "物业管理费(壹个月)");
    public static final String ZL_JTZJBZB = String.format("$%s$", "具体租金标准表");

    public static final String ZL_ZLWYLX = String.format("$%s$", "租赁物业类型");
    public static final String ZL_JFDZYX = String.format("$%s$", "甲方电子邮箱");
    public static final String ZL_YFDZYX = String.format("$%s$", "乙方电子邮箱");
    public static final String LEASE_MAIN_EMAIL = String.format("$%s$", "乙方主体联系人电子邮箱");
    public static final String ZL_ZLRZRQ = String.format("$%s$", "租赁入住日期");
    public static final String ZL_YFKPTT = String.format("$%s$", "乙方开票抬头");
    public static final String ZL_QSSJ = String.format("$%s$", "签署时间");
    public static final String ZL_SFZHM = String.format("$%s$", "身份证号码");
    public static final String ZL_SQZJ = String.format("$%s$", "首期租金");
    public static final String ZL_SQZJDX = String.format("$%s$", "首期租金大写");
    public static final String ZL_MZQBG = String.format("$%s$", "免租期表格");
    public static final String ZL_ZLNS = String.format("$%s$", "租赁年数");
    public static final String ZL_ZLTS = String.format("$%s$", "租赁天数");
    public static final String ZL_ZLJSQXNYR = String.format("$%s$", "租赁结束日期年月日");
    public static final String ZL_ZLQSQXNYR = String.format("$%s$", "租赁起始日期年月日");
    public static final String ZL_ZLQSQX = String.format("$%s$", "租赁起始日期");
    public static final String ZL_GDZJBZB = String.format("$%s$", "固定租金标准表");
    public static final String ZL_ZQFYBZB = String.format("$%s$", "周期费用标准表");
    public static final String ZL_ZLBZJDX = String.format("$%s$", "租赁保证金大写");
    public static final String ZL_LYBZJDX = String.format("$%s$", "履约保证金大写");
    public static final String ZL_HTLD = String.format("$%s$", "合同楼栋");
    public static final String ZL_HTLC = String.format("$%s$", "合同楼层");
    public static final String ZL_JFDBH = String.format("$%s$", "缴费单编号");
    public static final String ZL_CJDBH = String.format("$%s$", "催缴单编号");

    public static final String WY_WYHTBH = String.format("$%s$", "物业合同编号");
    public static final String WY_MYGYWYGLF = String.format("$%s$", "每月公寓物业管理费");
    public static final String WY_MYSYWYGLF = String.format("$%s$", "每月商业物业管理费");
    public static final String WY_MYXZLWYGLF = String.format("$%s$", "每月写字楼物业管理费");
    public static final String WY_MYCFWYGLF = String.format("$%s$", "每月厂房物业管理费");
    public static final String WY_GYWYGLFDJ = String.format("$%s$", "公寓物业管理费单价");
    public static final String WY_SYWYGLFDJ = String.format("$%s$", "商业物业管理费单价");
    public static final String WY_XZLWYGLFDJ = String.format("$%s$", "写字楼物业管理费单价");
    public static final String WY_CFWYGLFDJ = String.format("$%s$", "厂房物业管理费单价");
    public static final String WY_GYWYGLFDJY = String.format("$%s$", "公寓物业管理费单价元");
    public static final String WY_SYWYGLFDJY = String.format("$%s$", "商业物业管理费单价元");
    public static final String WY_XZLWYGLFDJY = String.format("$%s$", "写字楼物业管理费单价元");
    public static final String WY_CFWYGLFDJY = String.format("$%s$", "厂房物业管理费单价元");
    public static final String WY_CYWYGLF = String.format("$%s$", "餐饮物业管理费单价");
    public static final String WY_CYWYGLFDJY = String.format("$%s$", "餐饮物业管理费单价元");

    public static final String WY_MYCYWYGLF = String.format("$%s$", "每月餐饮物业管理费");
    public static final String WY_SPBWYFDJ = String.format("$%s$", "审批表物业费单价");
    public static final String WY_MYSPBWYF = String.format("$%s$", "每月审批表物业费");

    public static final String CJ_CJDSJEHZ = String.format("$%s$", "催缴待收金额汇总");
    public static final String CJ_CJDSJEMXB = String.format("$%s$", "催缴待收金额明细表");
    public static final String ZL_ZLYT = String.format("$%s$", "租赁用途");
    public static final String ZL_QSYZJ = String.format("$%s$", "起始月租金");
    public static final String ZL_QSYZJDX = String.format("$%s$", "起始月租金大写");
    public static final String ZL_GDZJZRYHZBZB = String.format("$%s$", "固定租金自然月汇总标准表");
    public static final String RESOURCE_SHORT2 = String.format("$%s$", "房源简称2");
	public static final String ZL_MZQ = String.format("$%s$", "免租期");
	public static final String ZL_QZRQ = String.format("$%s$", "起租日期");
}
