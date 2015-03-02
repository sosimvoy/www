package com.etax.framework.command;

public class CommandInfo {

	private String name;
	private String type;
	private String redirect;
	private String desc;

	public CommandInfo() {

	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public String getRedirect() {
		return this.redirect;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public void setRedirect( String redirect ) {
		this.redirect = redirect;
	}

	public void setDesc( String desc ) {
		this.desc = desc;
	}

}