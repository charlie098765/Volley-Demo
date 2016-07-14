package com.charlie.volley.bean;

public class UserAllInfo extends Object{
	private boolean success;
	private User user;

	public boolean isSuccess() {
		return success;
	}

	public UserAllInfo setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserAllInfo setUser(User user) {
		this.user = user;
		return this;
	}

}
