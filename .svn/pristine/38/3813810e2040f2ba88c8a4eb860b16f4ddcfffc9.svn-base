package com.smart.admin.modular.api.util;

import java.io.Serializable;

/**
 * 用于封装服务器到客户端的Json返回值
 * 
 * @author soft01
 * 
 */
public class JsonResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int SUCCESS = 0;
	public static final int FALSE = 1;


    /**
     * 响应状态
     */
    private int state;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
	private Object data;

	/**
	 * 操作成功
	 * */
	public JsonResult() {
		this.state = JsonResult.SUCCESS;
		this.message = "操作成功";
	}
	
	
	/**
	 * 返回状态码和信息
	 * @param state
	 * @param message
	 */
	public JsonResult(int state, String message) {
		super();
		this.state = state;
		this.message = message;
	}


	/**
	 * 操作成功，返回data信息
	 * */
	public JsonResult(Object data) {
		this.state = JsonResult.SUCCESS;
		this.message = "操作成功";
		this.data = data;
	}

	/**
	 * 返回JsonResult信息
	 * */
	public JsonResult(Integer state, String message,Object data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	
}
