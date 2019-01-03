package mytest;

import cn.newcapec.city.smart.common.utils.HexConvert;

/**
 * Created by es on 2018/12/25.
 */
public class HexConvertTest {

    public static void main(String[] args) {
        String hex = "77D067A83C5B010A7500ECB6666C0588";
        byte [] c = HexConvert.hexStringToByte(hex);
        System.out.println(HexConvert.bytesToHexString(c));
    }


    /**
     * 对卡唯一号 进行10进制转16进制 进行 两位反转 再从16进制转10进制
     * @param s
     * @return
     */
    public  static String toHex10_16_10_ReverseTo2(String s){
        long a = Long.parseLong(s);
        String reverseStr = Long.toHexString(a);
        //System.out.println("十六进制反转前："+reverseStr);
        String regex = "(.{2})";
        reverseStr = reverseStr.replaceAll (regex, "$1 ");
        //System.out.println (reverseStr);
        //System.out.println("十六进制加空格反转前："+reverseStr);
        //System.out.println("十六进制加空格反转后："+org.apache.commons.lang.StringUtils.reverse(reverseStr));
        String reverseDelimitedStr =org.apache.commons.lang.StringUtils.reverseDelimited(reverseStr,' ');
        //System.out.println("十六进制两位反转后："+reverseDelimitedStr);
        //System.out.println("十六进制两位反转后去空格："+reverseDelimitedStr.trim().replace(" ", ""));
        String ss =reverseDelimitedStr.trim().replace(" ", "");
        //System.out.println("十六进制转十进制："+Long.parseLong(ss,16));
        String res = Long.parseLong(ss,16)+"";
        return  res;
    }

}
