package com.etax.framework.exception;

/**
 * �߻��� ��� Exception�� ���� Wrapper RuntimeException<p>
 *
 */
@SuppressWarnings("serial")
public class ProcessException extends RuntimeException {

	/**
     * exception �� �߻��� root cause 
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
		super(msg);  // ������������ ����Ҷ� ����
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
	 * root cause�� �����´�.<p>
	 * @return root cause
     */
	public Throwable getRootCause() {
		return this.t;
	}

}