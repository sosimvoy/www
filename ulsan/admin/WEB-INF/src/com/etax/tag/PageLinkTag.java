package com.etax.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * �Խ��� ������ ��ũ tag handler�̴�.<p>
 * �Խ��� ��Ͽ� ��Ÿ���� ���� ������ ��ũ�κ��� ǥ���Ѵ�<p>
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PageLinkTag extends TagSupport {

	/**
     * ó�� ������ ��ũ �κп� ��Ÿ�������� �̹���(�Ǵ� ��ȣ,����)
     */
	private String firstImg;
	
	/**
     * ���� ������ ��ũ �κп� ��Ÿ�������� �̹���(�Ǵ� ��ȣ,����)
     */
	private String prevImg;
	
	/**
     * ���� ������ ��ũ �κп� ��Ÿ�������� �̹���(�Ǵ� ��ȣ,����)
     */
	private String nextImg;
	
	/**
     * �Ǹ����� ������ ��ũ �κп� ��Ÿ�������� �̹���(�Ǵ� ��ȣ,����)
     */
	private String lastImg;

	/**
     * �������� �������� ���ڵ��� ��
     */
	private int pageCnt;
	
	/**
     * ������ ������ ���Ǵ� ������ �� ��
     */
	private int blockCnt;
	
	/**
     * �������� ����Ʈ�� �� ���ڵ� ��
     */
	private int totalCnt;
	
	/**
     * ���� ������ ��ȣ
     */
	private int page;
	
	/**
	 * constructor<p>
	 */
    public PageLinkTag() {
    	firstImg ="<img src='../img/list_first.gif' alt='ó��' align='absmiddle'>";
    	prevImg ="<img src='../img/list_prev.gif' alt='����' align='absmiddle'>";
    	nextImg ="<img src='../img/list_next.gif' alt='����' align='absmiddle'>";
    	lastImg ="<img src='../img/list_last.gif' alt='������' align='absmiddle'>";
    	pageCnt = 10;
    	blockCnt =10;
    	page =1;
    }
	/**
	 *	set ó�� ������ ��ũ �κп� ��Ÿ�������� �̹���<p>
	 *	@param firstImg	ó�� ������ ��ũ �κп� ��Ÿ�������� �̹���
	 */
	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}	
	/**
	 *	set ���� ������ ��ũ �κп� ��Ÿ�������� �̹���<p>
	 *	@param prevImg	���� ������ ��ũ �κп� ��Ÿ�������� �̹���
	 */
	public void setPrevImg(String prevImg) {
		this.prevImg = prevImg;
	}	
	/**
	 *	set ���� ������ ��ũ �κп� ��Ÿ�������� �̹���<p>
	 *	@param nextImg	���� ������ ��ũ �κп� ��Ÿ�������� �̹���
	 */
	public void setNextImg(String nextImg) {
		this.nextImg = nextImg;
	}	
	/**
	 *	set �Ǹ����� ������ ��ũ �κп� ��Ÿ�������� �̹���<p>
	 *	@param lastImg	�Ǹ����� ������ ��ũ �κп� ��Ÿ�������� �̹���
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
	 * �Խ��� ��Ͽ� ��Ÿ�������� ������ ��ũ�κ��� ǥ���Ѵ�.<p>
	 *
     * @throws JspException �׿� tag handleró�� ���� �߻��� ����
     * @return  SKIP_BODY
     */
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {

        	int totalPage = (int) Math.ceil((double)totalCnt / (double)pageCnt);
        	
        	StringBuffer strbuf = new StringBuffer() ;                    
		
			int crntGroup = (page - 1) / blockCnt + 1  ;  	// ���� �׷��ȣ
	    	int groupNum = (totalPage  - 1) / blockCnt + 1  ; // ��ü �׷��
	
			// �׷��� 2 �̻��϶�
			if( crntGroup > 1 ) {
				strbuf.append("<a href=javascript:PAGE(1)>"+ firstImg + "</a>");  
				strbuf.append("&nbsp;<a href=javascript:PAGE(" + 
						Integer.toString((crntGroup - 1) * blockCnt) + 
						")>" + prevImg + "</a>&nbsp;&nbsp;&nbsp;&nbsp;" );
			}
	
			//������ ��ũ ���
			for(int kk = ((crntGroup-1) * blockCnt + 1);
				(kk < (crntGroup * blockCnt + 1))&& (kk <(totalPage + 1)); 
				kk++ ) {
		
				//�����������̸� ��ũ ����
				if(kk == page ){
					strbuf.append("&nbsp;<B>[" +Integer.toString(kk)+"]</B>") ;
				} else {
					strbuf.append("&nbsp;<a href=javascript:PAGE(" + 
							Integer.toString(kk) +")>[" + Integer.toString(kk) + 
							"]</a>") ;
				}
			}
	
			// ������ �׷��� �ƴҶ�
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
	 * tag handler�� ���� ���ҽ� Ǯ�� ���ư��� ���� ��������� �ʱ�ȭ �Ѵ�.<p>
	 *
     */
    public void release() {
        firstImg = null;
    	prevImg = null;
    	nextImg = null;
    	lastImg = null;
    }
}
