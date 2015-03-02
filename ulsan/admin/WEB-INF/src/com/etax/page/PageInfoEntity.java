package com.etax.page;

public class PageInfoEntity {
	private String name;	//페이지명
	private String login;	//로그인 필요 여부

	public String getName() {
		return this.name;
	}

	public String getLogin() {
		return this.login;
	}

	public void setName(String pageName) {
		this.name = pageName;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
