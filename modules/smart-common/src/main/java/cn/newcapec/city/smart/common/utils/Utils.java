package cn.newcapec.city.smart.common.utils;

import cn.newcapec.city.smart.common.utils.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 通用工具类
 * Created by es on 2018/3/13.
 */
@Slf4j
public final class Utils {

    /**
     * 获得UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获得短UUID
     *
     * @return
     */
    public static String getUUIDShort() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    /**
     * 获得十六进制的随机数
     *
     * @param len 指定长度
     * @return
     */
    public static String randomHexString(int len) {
        try {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < len; i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获得指定长度的数字
     *
     * @param digit
     * @return
     */
    public static String getNumberString(int digit) {
        StringBuilder nstr = new StringBuilder();
        for (int i = 0; i < digit; i++) {
            nstr.append(random(0, 9));
        }
        return nstr.toString();
    }

    /**
     * 获取区间内的随机数
     *
     * @param n1
     * @param n2
     * @return
     */
    public static int random(int n1, int n2) {
        return new Random().nextInt(n2 - n1 + 1) + n1;
    }

    /**
     * 获得字符串
     *
     * @param object 如果为null则返回空字符串
     * @return
     */
    public static String getString(Object object) {
        return getString(object, "");
    }

    /**
     * 获得字符串
     *
     * @param object
     * @param def
     * @return
     */
    public static String getString(Object object, String def) {
        if (object == null) {
            return def;
        } else {
            try {
                return object.toString();
            } catch (Exception e) {
                return def;
            }
        }
    }

    /**
     * 获得数字
     *
     * @param object 如果为null则返回0
     * @return
     */
    public static int getInt(Object object) {
        return getInt(object, 0);
    }

    /**
     * 获得数字
     *
     * @param object
     * @param def
     * @return
     */
    public static int getInt(Object object, int def) {
        if (object == null) {
            return def;
        } else {
            if (object instanceof Integer) {
                return (Integer) object;
            }
            try {
                return Integer.parseInt(object.toString());
            } catch (Exception e) {
                return def;
            }
        }
    }

    public static long getLong(Object object) {
        return getLong(object, 0L);
    }

    public static long getLong(Object object, long def) {
        if (object == null) {
            return def;
        } else {
            if (object instanceof Long) {
                return (Long) object;
            }
            try {
                return Long.parseLong(object.toString());
            } catch (Exception e) {
                return def;
            }
        }
    }

    public static double getDouble(Object object) {
        return getDouble(object, 0d);
    }

    public static double getDouble(Object object, double def) {
        if (object == null) {
            return def;
        } else {
            if (object instanceof Double) {
                return (Double) object;
            }
            try {
                return Double.parseDouble(object.toString());
            } catch (Exception e) {
                return def;
            }
        }
    }

    /**
     * 获得时间
     *
     * @param object
     * @return
     */
    public static Date getDate(Object object) {
        return getDate(object, new Date());
    }

    /**
     * 获得时间
     *
     * @param object
     * @param def    如果时间转换异常获取的默认时间
     * @return
     */
    public static Date getDate(Object object, Date def) {
        if (object == null) {
            return def;
        }
        if (object instanceof Date) {
            return (Date) object;
        } else if (object instanceof String) {
            try {
                Date result = DateUtil.stringToDate(object.toString());
                if (result == null) {
                    return def;
                }
                return result;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return def;
            }
        } else if (object instanceof Long) {
            return new Date((Long) object);
        } else {
            return def;
        }
    }

    /**
     * 获得时间
     *
     * @param object
     * @return
     */
    public static Timestamp getTimestamp(Object object) {
        return getTimestamp(object, new Timestamp(System.currentTimeMillis()));
    }


    /**
     * 获得时间
     *
     * @param object
     * @param def    如果时间转换异常获取的默认时间
     * @return
     */
    public static Timestamp getTimestamp(Object object, Timestamp def) {
        if (object == null) {
            return def;
        }
        Date date = getDate(object, def);
        if (date == null) {
            return def;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * 获得BigDecimal
     *
     * @param object
     * @param def
     * @return
     */
    public static BigDecimal getBigDecimal(Object object, BigDecimal def) {
        if (object == null) {
            return def;
        }
        if (object instanceof BigDecimal) {
            return (BigDecimal) object;
        } else {
            try {
                return new BigDecimal(object.toString());
            } catch (Exception e) {
                return def;
            }
        }
    }

    /**
     * 获得BigDecimal
     *
     * @param object
     * @return
     */
    public static BigDecimal getBigDecimal(Object object) {
        return getBigDecimal(object, new BigDecimal(0));
    }

}
