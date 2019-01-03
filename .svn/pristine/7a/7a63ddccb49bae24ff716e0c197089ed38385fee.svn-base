package cn.newcapec.city.smart.common.utils;

import java.util.*;

/**
 * 集合工具类
 * Created by es on 2018/3/13.
 */
public final class CollectionUtils {

    /**
     * List转String，默认逗号分割
     *
     * @param list
     * @return
     */
    public static String listToString(List list) {
        return listToString(list, ',');
    }

    /**
     * List转String
     *
     * @param list
     * @param separator 分隔符
     * @return
     */
    public static String listToString(List list, char separator) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * Set集合转换成List集合
     *
     * @param sets
     * @return
     */
    public static <T> List<T> setToList(Set<T> sets) {
        List<T> list = new ArrayList<T>();
        for (T k : sets) {
            list.add(k);
        }
        return list;
    }

    /**
     * 对list进行排序(从小到大)
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable> void sort(List<T> list) {
        sort(list, 1);
    }

    /**
     * 对list进行排序
     *
     * @param list  要排序的list
     * @param order 排序规则 1正序 -1倒序 0不排序
     * @param <T>
     */
    public static <T extends Comparable> void sort(List<T> list, int order) {
        if (order > 0) { //正序
            Collections.sort(list, new Comparator<T>() { // 对list进行排序
                @Override
                public int compare(Comparable o1, Comparable o2) {
                    return o1.compareTo(o2);
                }
            });
        } else if (order < 0) { //倒序
            Collections.sort(list, new Comparator<T>() { // 对list进行排序
                @Override
                public int compare(Comparable o1, Comparable o2) {
                    return o2.compareTo(o1);
                }
            });
        }
    }

}
