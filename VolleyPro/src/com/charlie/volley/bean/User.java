package com.charlie.volley.bean;

public class User {
	private String password;
	private String name;
	private String say;
	private String id;

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getSay() {
		return say;
	}

	public User setSay(String say) {
		this.say = say;
		return this;
	}

	public String getId() {
		return id;
	}

	public User setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public String toString() {
		return "User [password=" + password + ", name=" + name + ", say=" + say
				+ ", id=" + id + "]";
	}

}
