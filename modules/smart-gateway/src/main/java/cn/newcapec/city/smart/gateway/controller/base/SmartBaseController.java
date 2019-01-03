package cn.newcapec.city.smart.gateway.controller.base;

import cn.newcapec.city.smart.common.utils.CollectionUtils;
import cn.newcapec.city.smart.common.utils.security.MD5Util;
import cn.newcapec.city.smart.gateway.exception.SysException;
import cn.newcapec.city.smart.gateway.spring.interceptor.RequestHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;

/**
 * Created by es on 2018/12/26.
 */
public abstract class SmartBaseController extends BaseController {

    //参数的异常处理
    protected void bindingError(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError err : errors) {
                sb.append(err.getDefaultMessage() + "; ");
            }
            throw new SysException(sb.toString());
        }
    }

    /**
     * 获得商户编号
     *
     * @return
     */
    protected String merchantCode() {
        return RequestHolder.merchantCode();
    }

    /**
     * 获得设备编号
     *
     * @return
     */
    protected String deviceId() {
        return RequestHolder.deviceId();
    }

    /**
     * 对数据进行签名
     *
     * @param map
     * @return
     * @throws Exception
     */
    protected String sign(Map<String, String> map) throws Exception {
        StringBuilder result = new StringBuilder();
        List<String> list = CollectionUtils.setToList(map.keySet());
        // 对list进行排序
        CollectionUtils.sort(list);
        for (String key : list) {
            if ("sign".equals(key)) {
                continue;
            }
            String value = map.get(key);
            if (value == null || "".equals(value)) {
                continue;
            }
            result.append(key + "=" + value + "&");
        }
        return MD5Util.sha256(result.toString()).toUpperCase();
    }

}
