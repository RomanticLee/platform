package cn.newcapec.city.smart.gateway.exception;

/**
 * 系统异常类
 * Created by es on 2018/3/14.
 */
public final class SysException extends RuntimeException {

    private IRet.Code code = IRet.Code.FAIL;

    public SysException() {
        super();
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(IRet.Code code) {
        super(code.getMessage());
        this.code = code;
    }

    public SysException(IRet.Code code, String message) {
        super(message);
        this.code = code;
        this.code.setMessage(message);
    }

    public SysException(Throwable cause) {
        super(cause);
    }

    public SysException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 获得系统返回码
     *
     * @return
     */
    public IRet.Code getCode() {
        return code;
    }
}
