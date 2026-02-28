package it.unibo.tw.web.beans;

import java.util.ArrayList;
import java.util.List;

public class ResponseWs {
	
	private String nome;
	private String group;
	private String msg;
	private List<ResponseWs> previousMsg;
	
	public ResponseWs() {
		this.nome = "";
		this.group = "";
		this.msg = "";
		
		this.previousMsg = new ArrayList<>();
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

	public List<ResponseWs> getPreviousMsg() {
		return previousMsg;
	}

	public void setPreviousMsg(List<ResponseWs> previousMsg) {
		this.previousMsg = previousMsg;
	}
	
}
