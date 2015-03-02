package com.etax.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 게시판 페이지 링크 tag handler이다.<p>
 * 게시판 목록에 나타내어 지는 페이지 링크부분을 표시한다<p>
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PageLinkTag extends TagSupport {

	/**
     * 처음 페이지 링크 부분에 나타내어지는 이미지(또는 기호,문자)
     */
	private String firstImg;
	
	/**
     * 이전 페이지 링크 부분에 나타내어지는 이미지(또는 기호,문자)
     */
	private String prevImg;
	
	/**
     * 다음 페이지 링크 부분에 나타내어지는 이미지(또는 기호,문자)
     */
	private String nextImg;
	
	/**
     * 맨마지막 페이지 링크 부분에 나타내어지는 이미지(또는 기호,문자)
     */
	private String lastImg;

	/**
     * 페이지에 보여지는 레코드의 수
     */
	private int pageCnt;
	
	/**
     * 페이지 점프에 사용되는 페이지 블럭 수
     */
	private int blockCnt;
	
	/**
     * 보여지는 리스트의 총 레코드 수
     */
	private int totalCnt;
	
	/**
     * 현재 페이지 번호
     */
	private int page;
	
	/**
	 * constructor<p>
	 */
    public PageLinkTag() {
    	firstImg ="<img src='../img/list_first.gif' alt='처음' align='absmiddle'>";
    	prevImg ="<img src='../img/list_prev.gif' alt='이전' align='absmiddle'>";
    	nextImg ="<img src='../img/list_next.gif' alt='다음' align='absmiddle'>";
    	lastImg ="<img src='../img/list_last.gif' alt='마지막' align='absmiddle'>";
    	pageCnt = 10;
    	blockCnt =10;
    	page =1;
    }
	/**
	 *	set 처음 페이지 링크 부분에 나타내어지는 이미지<p>
	 *	@param firstImg	처음 페이지 링크 부분에 나타내어지는 이미지
	 */
	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}	
	/**
	 *	set 이전 페이지 링크 부분에 나타내어지는 이미지<p>
	 *	@param prevImg	이전 페이지 링크 부분에 나타내어지는 이미지
	 */
	public void setPrevImg(String prevImg) {
		this.prevImg = prevImg;
	}	
	/**
	 *	set 다음 페이지 링크 부분에 나타내어지는 이미지<p>
	 *	@param nextImg	다음 페이지 링크 부분에 나타내어지는 이미지
	 */
	public void setNextImg(String nextImg) {
		this.nextImg = nextImg;
	}	
	/**
	 *	set 맨마지막 페이지 링크 부분에 나타내어지는 이미지<p>
	 *	@param lastImg	맨마지막 페이지 링크 부분에 나타내어지는 이미지
	 */
	public void setLastImg(String lastImg) {
		this.lastImg = lastImg;
	}	
	
	public void setPageCnt(String pageCnt) {
		try {
			this.pageCnt = Integer.parseInt(pageCnt);
		} catch (Exception e)  {};	
	}
	
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
		
	public void setBlockCnt(String blockCnt) {
		try {
			this.blockCnt = Integer.parseInt(blockCnt);
		} catch (Exception e)  {};
	}	
	
	public void setBlockCnt(int blockCnt) {
		this.blockCnt = blockCnt;
	}
	
	public void setPage(String page) {
		try {
			this.page = Integer.parseInt(page);
		} catch (Exception e)  {};
	}	
	
	public void setPage(int page) {
		this.page = page;
	}	
	
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	
	/**
	 * 게시판 목록에 나타내어지는 페이지 링크부분을 표시한다.<p>
	 *
     * @throws JspException 그외 tag handler처리 도중 발생한 에러
     * @return  SKIP_BODY
     */
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {

        	int totalPage = (int) Math.ceil((double)totalCnt / (double)pageCnt);
        	
        	StringBuffer strbuf = new StringBuffer() ;                    
		
			int crntGroup = (page - 1) / blockCnt + 1  ;  	// 현재 그룹번호
	    	int groupNum = (totalPage  - 1) / blockCnt + 1  ; // 전체 그룹수
	
			// 그룹이 2 이상일때
			if( crntGroup > 1 ) {
				strbuf.append("<a href=javascript:PAGE(1)>"+ firstImg + "</a>");  
				strbuf.append("&nbsp;<a href=javascript:PAGE(" + 
						Integer.toString((crntGroup - 1) * blockCnt) + 
						")>" + prevImg + "</a>&nbsp;&nbsp;&nbsp;&nbsp;" );
			}
	
			//페이지 링크 출력
			for(int kk = ((crntGroup-1) * blockCnt + 1);
				(kk < (crntGroup * blockCnt + 1))&& (kk <(totalPage + 1)); 
				kk++ ) {
		
				//현재페이지이면 링크 없음
				if(kk == page ){
					strbuf.append("&nbsp;<B>[" +Integer.toString(kk)+"]</B>") ;
				} else {
					strbuf.append("&nbsp;<a href=javascript:PAGE(" + 
							Integer.toString(kk) +")>[" + Integer.toString(kk) + 
							"]</a>") ;
				}
			}
	
			// 마지막 그룹이 아닐때
			if( crntGroup != groupNum ) {
				strbuf.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=javascript:PAGE(" + 
						Integer.toString(crntGroup * blockCnt + 1) + 
						")>" + nextImg + "</a>") ;
			  	strbuf.append("&nbsp;<a href=javascript:PAGE(" + 
			  			Integer.toString( totalPage ) + ")>"+ lastImg + "</a>");
			}
        	
        	out.println(strbuf.toString());
        	
        } catch (Throwable t) {
			throw new JspException("[board tag] handler error", t);
		}	
       return SKIP_BODY;
    }
    
    /**
	 * tag handler가 공유 리소스 풀로 돌아가기 전에 멤버변수를 초기화 한다.<p>
	 *
     */
    public void release() {
        firstImg = null;
    	prevImg = null;
    	nextImg = null;
    	lastImg = null;
    }
}
