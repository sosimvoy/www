
package com.etax.tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;


/**
 * templet tag handler�̴�.<p>
 * page attribute�� �Էµ� page�� �Ľ��ؼ� Ư�� expression �� �������� �ؼ�
 * �պκа� �޺κ��� ���� ���� �±׿� �ݴ� �±׿� �κп� �����Ѵ�.
 * 
 * @version 1.0, 2003.06.01
 */
public class TemplateTag extends TagSupport {

	/**
     * templet page���� ���� page�� ������ �� �κ��� �����ϴ� ǥ����
     */
	public static final String EXPRESSION = "&page&";

	//EXPRESSION ���� �κ�
	private String prefix;
	//EXPRESSION ���� �κ�
	private String suffix;

	private String pageParams = "";

	/**
	 * tag handler�� ���� ���ҽ� Ǯ�� ���ư��� ���� ��������� �ʱ�ȭ �Ѵ�.<p>
	 *
     */	
	public void release() {
        prefix = null;
		suffix = null;
    }

	/**
	 * templet jsp ������ Parsing �ؼ� EXPRESSION �κ��� ã�� ���� ����� ���� ������ 
	 * �����Ѵ�.<p>
     *
     * @throw JspException �׿� tag handleró�� ���� �߻��� ����
     */	
	public void setPage(String page) throws JspException {

		try {
			BufferedResponse wrapper = new BufferedResponse(
				(HttpServletResponse)pageContext.getResponse()
			);
			
			//������ wrapper�� page�� include
			pageContext.getServletContext().getRequestDispatcher(page + this.pageParams).include(
				pageContext.getRequest(), wrapper
			);

			//wapper�� ����� page�� output���� EXPRESSION ������ �˻�
			String temp = wrapper.getString();
			int index = temp.indexOf(EXPRESSION);
			if (index == -1) {
				throw new JspException (
					"'" + EXPRESSION + "' not found in templet : " + page
				);
			}
			prefix = temp.substring(0, index);
			suffix = temp.substring(index + EXPRESSION.length());
		} catch (javax.servlet.ServletException e) {
			throw new JspException(e);
		} catch (java.io.IOException e) {
			throw new JspException(e);
		}
	}

	/**
	 * templet jsp ���Ͽ� �Ķ���͸� �Ѱ��ֱ� ���ؼ� �Ķ���� ���� �����Ѵ�
     *
     * @throw JspException �׿� tag handleró�� ���� �߻��� ����
     */	
	public void setParam(String params) {
		this.pageParams = "?" + params;
	}

	/**
	 * �����±׿��� ����ȴ�.<p>
	 * EXPRESSION ������ HTML�� jsp���Ͽ� ���
	 *
     * @throw JspException �׿� tag handleró�� ���� �߻��� ����
	 * @return  <tt>EVAL_BODY_INCLUDE</tt> ���������� ó���� ���
     *          <tt>SKIP_BODY</tt> templet page�� ǥ������ ���� ���
     */	
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print(prefix);
		} catch (java.io.IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * ������ �±׿��� ����ȴ�.<p>
     * EXPRESSION ������ HTML�� jsp���Ͽ� ���
	 *
     * @throw JspException �׿� tag handleró�� ���� �߻��� ����
     * @return  <tt>SKIP_PAGE</tt> �������� �� ���� ������ �±װ� ��ġ�ϹǷ�
     *			���̻� ������ ó���� ���� �ʴ´�.
     */	
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().print(suffix);
		} catch (java.io.IOException e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}
}