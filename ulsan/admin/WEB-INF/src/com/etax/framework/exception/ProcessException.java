package com.etax.framework.exception;

/**
 * 발생한 모든 Exception에 대한 Wrapper RuntimeException<p>
 *
 */
@SuppressWarnings("serial")
public class ProcessException extends RuntimeException {

	/**
     * exception 이 발생한 root cause 
     */
	private Throwable t = null;
	
	/**
	 * default constructor
     */
	public ProcessException() {}

	/**
	 * constructor<p>
     *
     * @param msg exception message
     */	
	public ProcessException(String msg) {
		this(msg, null);
	}

	/**
	 * constructor<p>
     *
     * @param msg exception message
     * @param t root cause
     */
	public ProcessException(String msg, Throwable t) {
		//super(msg,t);
		super(msg);  // 에러페이지를 사용할때 적용
		this.t = t;
	}

	/**
	 * constructor<p>
     *
     * @param t root cause
     */
	public ProcessException(Throwable t) {
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