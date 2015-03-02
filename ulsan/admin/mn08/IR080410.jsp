<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR080410.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-18
* 프로그램내용	   : 시스템운영 > 사용자 정보변경 신청
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
	CommonEntity chUserInfo = (CommonEntity)request.getAttribute("page.mn08.chUserInfo");
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

			
			function gosaveData(){
        var form = document.sForm;
				 form.action = "IR080410.etax"; 
				 form.cmd.value = "appUserInfo";
			   eSubmit(form); 
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
  <form name="sForm" method="post" action="IR080410.etax">
	<input type="hidden" name="cmd" value="chUserInfo">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_20.gif"></td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"> 
          
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
              <th width="33%">항목</th>
							<th width="33%">현재</th>
							<th width="33%">변경후</th>
            </tr>
						
            <tr>   
						  <td>&nbsp;부서</td>
              <% if (chUserInfo != null ) { %>
              <td>&nbsp;<input type="text" value="<%=chUserInfo.getString("M260_PARTNAME")%>" name="" size="20" maxlength="20" class="box1" readonly></td>
							<input type="hidden" name="part_name" value="<%=chUserInfo.getString("M260_CURRENTPART")%>">
	            <input type="hidden" name="work_name" value="<%=chUserInfo.getString("M260_CURRENTWORK1")%>">
	            <input type="hidden" name="sign_name" value="<%=chUserInfo.getString("M260_CURRENTSIGNTYPE")%>">
						  <%}%>
							<td>&nbsp;
								<select name="changePart" style="width:50%">
									<option value="">---------선택-------</option>
									<option value="B1">시청지점</option>
									<option value="B2">OCR센터</option>
								</select>
						  </td>
            </tr>

						<tr>   
						  <td>&nbsp;업무</td>
						  <% if (chUserInfo != null ) { %>
              <td>&nbsp; <input type="text" value="<%=chUserInfo.getString("M260_WORKNAME")%>" name="" size="20" maxlength="20" class="box1" readonly></td>
							<% } %>
							<td>&nbsp;
								<select name="changeWork1" style="width:50%">
									<option value="">---------선택-------</option>
									<option value="B1">세입등</option>
									<option value="B2">자금배정</option>
								</select>
						  </td>
            </tr>

						<tr>   
						  <td>&nbsp;결재권</td>
						  <% if (chUserInfo != null ) { %>
              <td>&nbsp;
							<input type="text" value="<%=chUserInfo.getString("M260_SIGNNAME")%>" name="" size="20" maxlength="20" class="box1" readonly></td>
							<% } %>
							<td>&nbsp; 
							  <select name="changeSignType" style="width:50%">
									<option value="">---------선택-------</option>
									<option value="B1">담당자</option>
									<option value="B2">책임자</option>
								</select>
								</td>
             </tr>
          </table>
					  <table width="980" border="0" cellspacing="1" cellpadding="1" align="center">
              <tr> 
                <td height="50"> 
                  <div align="right"><img src="../img/btn_modify.gif" width="66" border="0" onclick="gosaveData()" style="cursor:hand">
                  <img src="../img/btn_cancel.gif" width="66" height="20" border="0" onclick="history.back()" style="cursor:hand"></div>
                </td>
              </tr>
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