package com.etax.framework.exception;

/**
 * �������� RuntimeException<p>
 *
 */
@SuppressWarnings("serial")
public class AuthException extends RuntimeException {

	/**
     * exception �� �߻��� root cause 
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
		super(msg);  // ������������ ����Ҷ� ����
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
	 * root cause�� �����´�.<p>
	 * @return root cause
     */
	public Throwable getRootCause() {
		return this.t;
	}

}