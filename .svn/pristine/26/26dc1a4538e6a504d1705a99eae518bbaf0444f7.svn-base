package cn.newcapec.city.smart.common.utils;

import java.util.regex.Pattern;

/**
 * 数据验证工具类
 * Created by wpp on 2018/4/10.
 */
public class ValidationUtils {

    private static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    private static boolean find(String reg, String str) {
        if (isEmpty(reg) || isEmpty(str)) {
            return false;
        }
        str = str.trim();
        return Pattern.matches(reg, str);
    }

    /**
     * 验证是否是数字
     *
     * @param number
     * @return
     */
    public static boolean checkNumber(String number) {
        String reg = "[0-9]*";
        return find(reg, number);
    }

    /**
     * 昵称合法性验证
     *
     * @param nickname
     * @return
     */
    public static boolean checkNickname(String nickname) {
        String reg = "^[A-Za-z0-9_\\u4e00-\\u9fa5]{1,12}$";
        return find(reg, nickname);
    }

    /**
     * 手机号码合法性验证
     *
     * @param tel
     * @return
     * @author 吴培培
     * @date 20160511
     */
    public static boolean checkTel(String tel) {
        String reg = "^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
        boolean result = find(reg, tel);
        return result;
    }

    /**
     * 证件号码合法性验证
     *
     * @param certno
     * @return
     * @author 吴培培
     * @date 20160511
     */
    public static boolean checkCertno(String certno) {
        String reg = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        boolean result = find(reg, certno);
        if (result) {
            if (certno.length() == 18) {
                //将前17位加权因子保存在数组里
                int[] idCardWi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                int[] idCardY = new int[]{1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
                int idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                for (int i = 0; i < 17; i++) {
                    idCardWiSum += Integer.parseInt(certno.substring(i, i + 1)) * idCardWi[i];
                }
                int idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                String idCardLast = certno.substring(17);//得到最后一位身份证号码
                //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                if (idCardMod == 2) { //暂时不验证最后一位是X的
                    if ("X".equals(idCardLast) || "x".equals(idCardLast)) {
                        result = true;
                    } else {
                        result = false;
                    }
                } else {
                    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    if (idCardLast.equals("" + idCardY[idCardMod])) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 密码合法性校验
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        //// 6-20 位，字母、数字、字符
        String reg = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,20}$";
        return find(reg, password);
    }

    /**
     * 支付密码合法性校验
     *
     * @param password
     * @return
     */
    public static boolean checkPayPassword(String password) {
        //只能输入6位数字
        String reg = "^\\d{6}$";
        return find(reg, password);
    }

    /**
     * 验证银行卡号的有效性
     *
     * @param cardno
     * @return
     */
    public static boolean checkBankCard(String cardno) {
        //只能输入15-19位的数字
        String reg = "^\\d+$";
        boolean result = find(reg, cardno);
        if (result) {
            int length = cardno.length();
            if (length > 19 || length < 15) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s
     * @return
     */
    public static int length(String s) {
        if (s == null) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 判断字符长度
     *
     * @param strlength
     * @param max
     * @return
     */
    public static boolean isMax(int strlength, int max) {
        if (strlength <= max) {
            return true;
        } else {
            return false;
        }
    }

}
