package cn.newcapec.city.smart.common.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

/**
 * 可序列号对象的基类
 * Created by es on 2018/12/26.
 */
@Slf4j
public class BaseBin implements Serializable {

    public BaseBin() {
    }

    //region 对象的方法

    public JsonElement toJson() {
        return toJson(this);
    }

    @Override
    public String toString() {
        return toString(this);
    }

    /**
     * 将当前对象转换成另一个对象
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T toBean(Class<T> cls) {
        return toObject(this, cls);
    }

    /**
     * 将当前对象转换成map
     *
     * @return
     */
    public Map<String, Object> toMap() {
        return toMap(this);
    }


    //endregion

    //region 静态方法

    //gson对象
    private static final Gson myGson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    /**
     * 将实体对象转换成json字符串
     *
     * @param src
     * @return
     */
    public static String toString(Object src) {
        return myGson.toJson(src);
    }

    /**
     * 将实体对象转换成其他指定对象
     *
     * @param src 要转换的实体对象
     * @param cls 指定的其他对象类型
     * @param <T>
     * @return
     */
    public static <T> T toObject(Object src, Class<T> cls) {
        return myGson.fromJson(toString(src), cls);
    }

    public static <T> T toBean(String json, Class<T> cls) {
        return myGson.fromJson(json, cls);
    }

    /**
     * 将实体对象转换成json对象
     *
     * @param src
     * @return
     */
    public static JsonElement toJson(Object src) {
        return myGson.toJsonTree(src);
    }

    /**
     * 将实体对象转换成map对象
     *
     * @param src
     * @return
     */
    public static Map<String, Object> toMap(Object src) {
        if (src == null) {
            return null;
        }
        JsonElement jsonElement = toJson(src);
        if (jsonElement instanceof JsonObject) {
            return toMap((JsonObject) jsonElement);
        } else {
            return new HashMap<String, Object>();
        }
    }

    /**
     * 将jsonObject对象转换成map
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, Object> toMap(JsonObject jsonObject) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonObject == null) {
            return null;
        }
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray) {
                map.put(key, toList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                map.put(key, toMap((JsonObject) value));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 将json字符串转化为list
     *
     * @param json
     * @return
     */
    public static <T> List<T> toList(String json) {
        List<T> list = myGson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    /**
     * JsonArray转list数组
     *
     * @param json
     * @return
     */
    public static List<Object> toList(JsonArray json) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < json.size(); i++) {
            Object value = json.get(i);
            if (value instanceof JsonArray) {
                list.add(toList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                list.add(toMap((JsonObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }

    //endregion

}
