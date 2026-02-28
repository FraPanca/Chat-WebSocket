package it.unibo.tw.web.beans;

public class RequestWs {

	private String nome;
	private String group;
	private String msg;
	private String action;
	
	public RequestWs() {
		this.nome = "";
		this.group = "";
		this.msg = "";
		this.action = "";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
