package com.michelin.cio.hudson.plugins.maskpasswords.keepass;

public class KeepassAccessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public KeepassAccessException(String msg, Throwable cause){
		super(msg, cause);
	}

}
