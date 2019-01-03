package cn.newcapec.city.smart.gateway.exception;

/**
 * 返回类型接口
 * Created by es on 2018/7/2.
 */
public interface IRet {

    enum Code {
        SUCCESS("0000", "成功"),
        FAIL("1000", "失败"),
        ILLEGAL_ARGUMENT("1001", "参数不对"),
        BINDERROR("1002", "数据绑定异常"),
        METHOD_ARGUMENT_NOTVALID("1003", "方法参数无效"),
        INVALIDITY_AUTHORIZATION("1004", "授权无效"),
        NOT_FOUND("1005", "接口不存在"),
        AUTHORIZATION_REPEAT("1006", "授权重复"),
        AUTHORIZATION_TIMEOUT("1007", "授权超时"),
        UNKONE("5000", "服务器未知错误");

        private String code;
        private String message;

        Code(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String toString() {
            return this.code;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
