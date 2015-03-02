package com.etax.framework.exception;

/**
 * 인증관련 RuntimeException<p>
 *
 */
@SuppressWarnings("serial")
public class AuthException extends RuntimeException {

	/**
     * exception 이 발생한 root cause 
     */
	private Throwable t = null;
	
	/**
	 * default constructor
     */
	public AuthException() {}

	/**
	 * constructor<p>
     *
     * @param msg exception message
     */	
	public AuthException(String msg) {
		this(msg, null);
	}

	/**
	 * constructor<p>
     *
     * @param msg exception message
     * @param t root cause
     */
	public AuthException(String msg, Throwable t) {
		//super(msg,t);
		super(msg);  // 에러페이지를 사용할때 적용
		this.t = t;
	}

	/**
	 * constructor<p>
     *
     * @param t root cause
     */
	public AuthException(Throwable t) {
		super(t);
		this.t = t;
	}

	/**
	 * root cause를 가져온다.<p>
	 * @return root cause
     */
	public Throwable getRootCause() {
		return this.t;
	}

}