package util;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8789308344325243842L;
	private String user;
	private String message;
	
	public Message(String user,	String message) {
		this.user = user;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return this.user + ": " + this.message;
	}
}
