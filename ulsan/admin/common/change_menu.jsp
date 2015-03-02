<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : change_menu.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2010-06-16
* 프로그램내용   : 건별/세목별에 따른 메뉴 선택
****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.List" %>
<%
		String menu_type = (String)request.getParameter("change_menu");
%>
<script language="JavaScript" src="../js/utility.js"></script>
<script>    
    function window.onload() {
        /* 부모 select 객체에 세목 정보 생성 */
        var parentSelect = null;
        try {
					  <% if ("2".equals(menu_type)) { %>
            parentSelect = parent.document.sForm.semok_code;
        } catch(e) {
            parentSelect = parent.document.uForm.semok_code;
        }
    }        
</script>