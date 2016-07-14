package com.charlie.volley.core;

/**用来封装服务器返回的结果(以及tag)*/
public class Result {
	
	private int state = -100; //状态值 一般0表示ok
	private String message;
	private Object mTag;

	public int getState() {
		return state;
	}
	public Result setState(int state) {
		this.state = state;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public Result setMessage(String message) {	
		this.message = message;
		return this;
	}
	@Override
	public String toString() {
		return "Result [state=" + state + ", message=" + message + "]";
	}
	public Object getTag() {
		return mTag;
	}
	public Result setTag(Object mTag) {
		this.mTag = mTag;
		return this;
	}
	
}