package cn.newcapec.city.smart.gateway.authorization.model;

/**
 * token认证结果
 * Created by es on 2018/7/9.
 */
public class AuthResult {

    private Result result;
    private TokenModel tokenModel;

    private AuthResult() {
    }

    /**
     * 创建token认证结果对象
     *
     * @param result
     * @return
     */
    public static AuthResult create(Result result) {
        return new AuthResult().setResult(result);
    }

    public Result getResult() {
        return result;
    }

    public AuthResult setResult(Result result) {
        this.result = result;
        return this;
    }

    public TokenModel getTokenModel() {
        return tokenModel;
    }

    public AuthResult setTokenModel(TokenModel tokenModel) {
        this.tokenModel = tokenModel;
        return this;
    }

    public enum Result {
        SUCCESS, //认证成功
        FAIL, //认证失败
        TIMEOUT, //超时
        REPEAT; //认证重复
    }

}
