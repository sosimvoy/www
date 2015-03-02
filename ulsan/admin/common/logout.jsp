<%
/***************************************************************
* 프로젝트명	 : 대전광역시 가상계좌번호 납부관리자 시스템
* 프로그램명	 : logout.jsp
* 프로그램작성자 :
* 프로그램작성일 : 2007-02-26
* 프로그램내용	 : 비밀번호 변경
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"  %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ taglib uri="../WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	String userID = (String)session.getAttribute("session.userID");
	String userName = (String)session.getAttribute("session.userName");
%>
<html>
  <head>
	  <title>대전광역시 가상계좌번호 납부관리자 시스템</title>
	  <link href="../css/admin.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="../js/base.js"></script>
    <script language="javascript">
<!--
      function init() {
	      typeInitialize();
      }

      function goSave() {
	      var form = document.sForm;
      	form.formSubmit();
      }

      function checkPage() {
	      var form = document.sForm;
	      if( form.new_pwd.value != form.new_pwd_confirm.value ) {
		      alert("신규 비밀번호 확인 값이 다릅니다.");
		      form.new_pwd_confirm.select();
		      return false;
	      }
	      return true;
      }
-->
    </script>
  </head>

  <body>
    <form name="sForm" method="post" action="logout.etax">
      <input type="hidden" name="cmd" value="changePwd">
      <input type="hidden" name="message" value="S002">
      <input type="hidden" name="mgr_id" value="<%=userID%>">
      <table style="margin-left:16px">
        <tr valign="top">
      	  <td width="766">
        		<table class="title">
		          <tr>
          			<td>사용자 비밀번호 변경</td>
           			<td align="right">
           				<div id="location">
           					<img src="../img/common/icon_lo.gif" align="absmiddle"> HOME &gt; <span class="on">사용자 비밀번호 변경</span>
           				</div>
           			</td>
           		</tr>
        		</table>
		
        		<p></p>

        		<table class="input">
          		<tr>
          			<th width="20%">사용자ID</th>
          			<td class="last" width="85%"><%=userID%></td>
          		</tr>
          		<tr>
          			<th>사용자명</th>
          			<td class="last"><%=userName%></td>
          		</tr>
          		<tr>
          			<th>현행 비밀번호</th>
          			<td class="last"><input type="password" name="old_pwd" required desc="현행 비밀번호" userType="engNumber"></td>
          		</tr>
          		<tr>
          			<th>신규 비밀번호</th>
          			<td class="last"><input type="password" name="new_pwd" required desc="신규 비밀번호" userType="engNumber"></td>
          		</tr>
          		<tr>
          			<th>신규 비밀번호 확인</th>
          			<td class="last"><input type="password" name="new_pwd_confirm" required desc="신규 비밀번호 확인" userType="engNumber"></td>
          		</tr>
            </table>
          
          	<table class="btn">
          		<tr>
          			<td>
          				<a href="javascript:goSave()"><img src="../img/common/btn_save.gif" alt="저장"></a>
          			</td>
          		</tr>
        		</table>
        	</td>
        </tr>
      </table>

      <script language="JavaScript" src="../js/Bottom.js"></script>

    </form>
  </body>
</html>