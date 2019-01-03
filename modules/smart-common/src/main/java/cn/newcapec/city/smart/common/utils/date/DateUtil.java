package cn.newcapec.city.smart.common.utils.date;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @author WEIXING
 */
public abstract class DateUtil {

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    private static final Object object = new Object();

    /**
     * 获得当前日期的日期差
     *
     * @param field  差值类型 如：Calendar.DAY_OF_MONTH、Calendar.DAY_OF_YEAR等
     * @param amount 间隔时间
     * @return
     */
    public static Date getDateBaseNow(int field, int amount) {
        Calendar date = Calendar.getInstance();
        date.add(field, amount);
        return date.getTime();
    }

    /**
     * 获得指定日期的日期差
     *
     * @param nowDate 指定的日期
     * @param field   差值类型 如：Calendar.DAY_OF_MONTH、Calendar.DAY_OF_YEAR等
     * @param amount  间隔时间
     * @return
     */
    public static Date getDateBaseNow(Date nowDate, int field, int amount) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowDate);
        date.add(field, amount);
        return date.getTime();
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern)
            throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date     日期
     * @param dateType 日期格式
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期字符串
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = stringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = dateToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     *
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i)
                                - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i),
                                timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
                // 因此不能将minAbsoluteValue取默认值0
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne
                                : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     *
     * @param date 日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (getDateStyle(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     *
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            if (style.isShowOnly()) {
                continue;
            }
            Date dateTmp = null;
            if (date != null) {
                try {
                    ParsePosition pos = new ParsePosition(0);
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                    if (pos.getIndex() != date.length()) {
                        dateTmp = null;
                    }
                } catch (Exception e) {
                }
            }
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static Date stringToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return stringToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date stringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date      日期字符串
     * @param dateStyle 日期风格
     * @return 日期
     */
    public static Date stringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = stringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 转换日期格式
     *
     * @param dateStyle
     * @return
     */
    public static Date dateToDate(DateStyle dateStyle) {
        String dateStr = DateUtil.getDate(System.currentTimeMillis(), dateStyle);
        return DateUtil.stringToDate(dateStr, dateStyle);
    }

    /**
     * 转换日期格式
     *
     * @param date
     * @param dateStyle
     * @return
     */
    public static Date dateToDate(Date date, DateStyle dateStyle) {
        String dateStr = DateUtil.getDate(date, dateStyle);
        return DateUtil.stringToDate(dateStr, dateStyle);
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String dateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Timestamp date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date      日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String dateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = dateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date      日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String dateToString(Timestamp date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = dateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date       旧日期字符串
     * @param newPattern 新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return stringToString(date, oldDateStyle, newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return stringToString(date, oldDateStyle, newDateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date        旧日期字符串
     * @param olddPattern 旧日期格式
     * @param newPattern  新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String date, String olddPattern,
                                        String newPattern) {
        return dateToString(stringToDate(date, olddPattern), newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newParttern  新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyle olddDteStyle,
                                        String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = stringToString(date, olddDteStyle.getValue(),
                    newParttern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddPattern  旧日期格式
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, String olddPattern,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = stringToString(date, olddPattern,
                    newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyle olddDteStyle,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = stringToString(date, olddDteStyle.getValue(),
                    newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date        日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date        日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期字符串
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期字符串
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期字符串
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date         日期字符串
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期字符串
     */
    public static String addMinute(String date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date         日期
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date         日期字符串
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期字符串
     */
    public static String addSecond(String date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date         日期
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期字符串
     * @return 年份
     */
    public static int getYear(String date) {
        return getYear(stringToDate(date));
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期字符串
     * @return 月份
     */
    public static int getMonth(String date) {
        return getMonth(stringToDate(date));
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期字符串
     * @return 天
     */
    public static int getDay(String date) {
        return getDay(stringToDate(date));
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期字符串
     * @return 小时
     */
    public static int getHour(String date) {
        return getHour(stringToDate(date));
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 分钟
     */
    public static int getMinute(String date) {
        return getMinute(stringToDate(date));
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 秒钟
     */
    public static int getSecond(String date) {
        return getSecond(stringToDate(date));
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static String getDate(String date) {
        return stringToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获得当前系统时间的yyyy-MM-dd格式
     *
     * @return
     */
    public static String getDate() {
        return getDate(DateStyle.YYYY_MM_DD);
    }

    /**
     * 获得指定格式的当前系统时间
     *
     * @param dateStyle
     * @return
     */
    public static String getDate(DateStyle dateStyle) {
        return getDate(getCurrDay(), dateStyle);
    }

    /**
     * 获得指定格式的日期
     *
     * @param date
     * @param dateStyle
     * @return
     */
    public static String getDate(Date date, DateStyle dateStyle) {
        return dateToString(date, dateStyle);
    }

    /**
     * 获取自定义格式日期 。失败返回null。
     *
     * @param time 日期
     * @param ds   日期样式
     * @return 日期
     */
    public static String getDate(Long time, DateStyle ds) {
        return dateToString(new Date(time), ds);
    }

    /**
     * 获取现在格式日期 。失败返回null。
     *
     * @param ds 日期样式
     * @return 日期
     */
    public static String getNowDate(DateStyle ds) {
        return dateToString(new Date(), ds);
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return dateToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 时间
     */
    public static String getTime(String date) {
        return stringToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期
     * @return 时间
     */
    public static String getTime(Date date) {
        return dateToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期字符串
     * @return 星期
     */
    public static Week getWeek(String date) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = stringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期
     * @return 星期
     */
    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
                break;
        }
        return week;
    }


    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期
     * @return 星期
     */
    public static int getWeekReInt(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekNumber;
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param date      日期字符串
     * @param otherDate 另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(stringToDate(date), stringToDate(otherDate));
    }

    /**
     * @param date      日期
     * @param otherDate 另一个日期
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = DateUtil.stringToDate(DateUtil.getDate(date),
                DateStyle.YYYY_MM_DD);
        Date otherDateTmp = DateUtil.stringToDate(DateUtil.getDate(otherDate),
                DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    /**
     * 获得相差的日期
     *
     * @param date
     * @param off   偏差量
     * @param field Calendar.MONTH、Calendar.DAY_OF_MONTH等
     * @return
     */
    public static Date getOffDate(Date date, int off, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, off);
        return calendar.getTime();
    }

    /**
     * 获得相差的日期
     *
     * @param time
     * @param off   偏差量
     * @param field Calendar.MONTH、Calendar.DAY_OF_MONTH等
     * @return
     */
    public static Date getOffDate(long time, int off, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.add(field, off);
        return calendar.getTime();
    }

    /**
     * 获得相差月份的日期
     *
     * @param date
     * @param offMoney
     * @return
     */
    public static Date getOffMonth(Date date, int offMoney) {
        return getOffDate(date, offMoney, Calendar.MONTH);
    }

    /**
     * 获得相差月份的日期
     *
     * @param time
     * @param offMoney
     * @return
     */
    public static Date getOffMonth(long time, int offMoney) {
        return getOffDate(time, offMoney, Calendar.MONTH);
    }

    /**
     * 获得相差年份的日期
     *
     * @param date
     * @param offYear
     * @return
     */
    public static Date getOffYear(Date date, int offYear) {
        return getOffDate(date, offYear, Calendar.YEAR);
    }

    /**
     * 获得相差年份的日期
     *
     * @param time
     * @param offYear
     * @return
     */
    public static Date getOffYear(long time, int offYear) {
        return getOffDate(time, offYear, Calendar.YEAR);
    }

    /**
     * 获得日期的前一天
     *
     * @param date
     * @return
     */
    public static Date getLastDay(Date date) {
        return getOffDay(date, -1);
    }

    /**
     * 获得日期的前一天
     *
     * @param time
     * @return
     */
    public static Date getLastDay(Long time) {
        return getOffDay(time, -1);
    }

    /**
     * 获得日期的后一天
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        return getOffDay(date, 1);
    }

    /**
     * 获得日期的后一天
     *
     * @param time
     * @return
     */
    public static Date getNextDay(Long time) {
        return getOffDay(time, 1);
    }

    /**
     * 获得相差天数的日期
     *
     * @param date   参考日期
     * @param offDay 相差的天数
     * @return
     */
    public static Date getOffDay(Date date, int offDay) {
        return getOffDate(date, offDay, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得相差天数的日期
     *
     * @param time   参考日期
     * @param offDay 相差的天数
     * @return
     */
    public static Date getOffDay(Long time, int offDay) {
        return getOffDate(time, offDay, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得相差小时数的日期
     *
     * @param time    参考日期
     * @param offHour 相差的小时数
     * @return
     */
    public static Date getOffHour(Long time, int offHour) {
        return getOffDate(time, offHour, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得相差分钟数的日期
     *
     * @param time      参考日期
     * @param offMinute 相差的分钟数
     * @return
     */
    public static Date getOffMinute(Long time, int offMinute) {
        return getOffDate(time, offMinute, Calendar.MINUTE);
    }

    /**
     * 获得相差秒数的日期
     *
     * @param time      参考日期
     * @param offSecond 相差的秒数
     * @return
     */
    public static Date getOffSecond(Long time, int offSecond) {
        return getOffDate(time, offSecond, Calendar.SECOND);
    }

    /**
     * 功能描述：得到sql date
     *
     * @param date 给定的日期
     * @return java.sql.Date    返回转化后的日期
     */
    public static Timestamp getSqlDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 功能描述：得到sql date
     *
     * @param source 源	字符串
     * @param format 格式字符串
     * @return java.sql.Date    返回转化后的日期
     */
    public static Timestamp getSqlDate(String source, String format) {
        return new Timestamp(stringToDate(source, format).getTime());
    }

    /**
     * 获得当前日期
     *
     * @return
     */
    public static Date getCurrDay() {
        //数据库时间如何获取
        //return SequenceUtil.getDBDate();
        return new Date();
    }

    /**
     * 比较两个日期：0 相等  -1 date1小于date2  1 date1大于date2
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        return date1.compareTo(date2);
    }

    /**
     * 比较两个日期：0 相等  -1 date1小于date2  1 date1大于date2
     *
     * @param date1
     * @param date2
     * @param dateStyle
     * @return
     */
    public static int compare(String date1, String date2, DateStyle dateStyle) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Date d1 = DateUtil.stringToDate(date1, dateStyle);
        Date d2 = DateUtil.stringToDate(date2, dateStyle);
        return compare(d1, d2);
    }

    /**
     * 比较两个日期：0 相等  -1 date1小于date2  1 date1大于date2
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare(String date1, String date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Date d1 = DateUtil.stringToDate(date1);
        Date d2 = DateUtil.stringToDate(date2);
        return compare(d1, d2);
    }

}
