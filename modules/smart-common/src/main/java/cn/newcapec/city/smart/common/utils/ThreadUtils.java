package cn.newcapec.city.smart.common.utils;


import cn.newcapec.city.smart.icall.IReturnBooleanCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * 线程工具类
 * Created by es on 2018/3/13.
 */
@Slf4j
public final class ThreadUtils {

    /**
     * 线程等待
     *
     * @param irbCallback 判断等待的逻辑，返回true继续等待，返回false结束等待；
     *                    每隔50毫秒执行一次，超时时间1000毫秒
     * @return 是否超时  true超时  false未超时
     */
    public static boolean threadWait(IReturnBooleanCallback irbCallback) {
        return threadWait(irbCallback, 50, 1000);
    }

    /**
     * 线程等待
     *
     * @param irbCallback 判断等待的逻辑，返回true继续等待，返回false结束等待
     * @param offtime     irbCallback执行的时间间隔 单位：毫秒ms
     * @param timeout     超时时间 单位：毫秒ms
     * @return 是否超时  true超时  false未超时
     */
    public static boolean threadWait(IReturnBooleanCallback irbCallback, long offtime, long timeout) {
        if (irbCallback == null) {
            return false;
        }
        if (irbCallback.callback()) {
            long start = System.currentTimeMillis();
            while (true) {
                try {
                    Thread.sleep(offtime);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                try {
                    if (!irbCallback.callback()) {
                        break;
                    }
                } catch (Exception e) {
                    break;
                }
                if (System.currentTimeMillis() - start > timeout) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 如果irbCallback执行失败，则尝试执行3次
     *
     * @param irbCallback 要执行的函数
     * @return 执行结果: true执行成功，false执行失败
     */
    public static boolean runThree(IReturnBooleanCallback irbCallback) {
        return runTimes(irbCallback, 3);
    }

    /**
     * 如果irbCallback执行失败，则尝试执行times次
     *
     * @param irbCallback 要执行的函数
     * @param times       尝试次数
     * @return 执行结果: true执行成功，false执行失败
     */
    public static boolean runTimes(IReturnBooleanCallback irbCallback, int times) {
        if (irbCallback == null || times < 1) {
            return false;
        }
        boolean result = false;
        int index = 0;
        while (index < times) {
            result = irbCallback.callback();
            if (result) {
                break;
            }
            index++;
        }
        return result;
    }

}
