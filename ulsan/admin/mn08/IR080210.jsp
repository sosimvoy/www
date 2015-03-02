<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR080210.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-20
* 프로그램내용	   : 시스템운영 > 사용자내역조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn08.jsp" %>

<%
	List<CommonEntity>userList = (List<CommonEntity>)request.getAttribute("page.mn08.userList");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      
			function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
				eSubmit(form);
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
  <form name="sForm" method="post" action="IR080210.etax">
	<input type="hidden" name="cmd" value="userHistoryList">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_2.gif"></td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr> 
                    <td><span style="width:700"></span></td>
                    <td width="100"> 
                      <img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()" align="absmiddle">
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
            </tr>
          </table>
        </td>
			</tr>	
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th width="13%">사용자명</th>
							<th width="13%">소속기관</th>
							<th width="13%">소속부서</th>
							<th width="13%">주요업무</th>
							<th width="12%">결재권구분</th>
							<th width="12%">책임자행번</th>
							<th width="12%">책임자번호</th>
							<th width="12%">단말번호</th>
            </tr>
						<%
						for (int i=0; userList != null && i < userList.size(); i++) {
							CommonEntity data = (CommonEntity)userList.get(i);
						%>
            <tr>                 
              <td>&nbsp;<%=data.getString("M260_USERNAME")%></td>
					    <td>&nbsp;<%=data.getString("M260_CURRENTORGAN")%></td>
							<td>&nbsp;<%=data.getString("M260_CURRENTPART")%></td>
							<td>&nbsp;<%=data.getString("M260_CURRENTWORK1")%></td>
							<td>&nbsp;<%=data.getString("M260_CURRENTSIGNTYPE")%></td>
							<td>&nbsp;<%=data.getString("M260_MANAGERBANKERNO")%></td>
							<td>&nbsp;<%=data.getString("M260_MANAGERNO")%></td>
							<td>&nbsp;<%=data.getString("M260_TERMINALNO")%></td>
            </tr>
					  <%				 
						} if (userList == null ) { 
					  %>
						<tr>
							<td class="fstleft" colspan="10">조회 내역이 없습니다.</td>
						</tr>
						<% 
							} 
						%>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>