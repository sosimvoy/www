<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : change_menu.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2010-06-16
* ���α׷�����   : �Ǻ�/���񺰿� ���� �޴� ����
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
        /* �θ� select ��ü�� ���� ���� ���� */
        var parentSelect = null;
        try {
					  <% if ("2".equals(menu_type)) { %>
            parentSelect = parent.document.sForm.semok_code;
        } catch(e) {
            parentSelect = parent.document.uForm.semok_code;
        }
    }        
</script>