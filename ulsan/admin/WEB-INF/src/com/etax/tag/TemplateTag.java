
package com.etax.tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;


/**
 * templet tag handler이다.<p>
 * page attribute로 입력된 page를 파싱해서 특정 expression 을 기준으로 해서
 * 앞부분과 뒷부분을 각각 시작 태그와 닫는 태그에 부분에 삽입한다.
 * 
 * @version 1.0, 2003.06.01
 */
public class TemplateTag extends TagSupport {

	/**
     * templet page에서 현재 page의 내용일 들어갈 부분을 지정하는 표현식
     */
	public static final String EXPRESSION = "&page&";

	//EXPRESSION 이전 부분
	private String prefix;
	//EXPRESSION 이후 부분
	private String suffix;

	private String pageParams = "";

	/**
	 * tag handler가 공유 리소스 풀로 돌아가기 전에 멤버변수를 초기화 한다.<p>
	 *
     */	
	public void release() {
        prefix = null;
		suffix = null;
    }

	/**
	 * templet jsp 파일을 Parsing 해서 EXPRESSION 부분을 찾아 이전 내용과 이후 내용을 
	 * 구분한다.<p>
     *
     * @throw JspException 그외 tag handler처리 도중 발생한 에러
     */	
	public void setPage(String page) throws JspException {

		try {
			BufferedResponse wrapper = new BufferedResponse(
				(HttpServletResponse)pageContext.getResponse()
			);
			
			//생성된 wrapper로 page를 include
			pageContext.getServletContext().getRequestDispatcher(page + this.pageParams).include(
				pageContext.getRequest(), wrapper
			);

			//wapper에 담겨진 page의 output에서 EXPRESSION 전까지 검색
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
	 * templet jsp 파일에 파라미터를 넘겨주기 위해서 파라미터 값을 세팅한다
     *
     * @throw JspException 그외 tag handler처리 도중 발생한 에러
     */	
	public void setParam(String params) {
		this.pageParams = "?" + params;
	}

	/**
	 * 시작태그에서 실행된다.<p>
	 * EXPRESSION 전까지 HTML을 jsp파일에 출력
	 *
     * @throw JspException 그외 tag handler처리 도중 발생한 에러
	 * @return  <tt>EVAL_BODY_INCLUDE</tt> 정상적으로 처리된 경우
     *          <tt>SKIP_BODY</tt> templet page에 표현식이 없을 경우
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
	 * 끝나는 태그에서 실행된다.<p>
     * EXPRESSION 이후의 HTML을 jsp파일에 출력
	 *
     * @throw JspException 그외 tag handler처리 도중 발생한 에러
     * @return  <tt>SKIP_PAGE</tt> 페이지의 맨 끝에 끝나는 태그가 위치하므로
     *			더이상 페이지 처리를 하지 않는다.
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