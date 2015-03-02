<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR091411.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-07-15
* 프로그램내용	   : 관리자 > 직인정보관리 > 직인등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<% 
   CommonEntity signInfo = (CommonEntity)request.getAttribute("page.mn09.signInfo");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
				<% if (signInfo != null && signInfo.size() > 0)	{ %>
					   alert("업로드가 완료되었습니다.");
				opener.goSearch();
				self.close();
				<% } %>
      }

			function goClose() {
				self.close();
			}

			function goUpload() {
				var form = document.sForm;
				idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;

				<% if (signInfo != null && signInfo.size() > 0)	{ %>
					alert("파일이 등록완료 되었습니다. 삭제 후 이용하세요.");
				  return;
				<% } %>

        if (form.upfile.value=="") {
					alert("파일을 첨부하세요.");
					return;
        }

				if(form.upfile.value.substring(idx1+1, idx2).toLowerCase() != "jpg"){
          alert("이미지(jpg) 파일만 업로드 가능합니다.");
          form.upfile.select();
          document.selection.clear();
          return;
        }

				eSubmit(form);
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
	<form name="sForm" method="post" enctype="multipart/form-data" action="IR091411.etax?cmd=signUpload&uploadDir=seal">
     <table width="450" border="0" cellspacing="0" cellpadding="5">
  <tr> 
    <td> 
      <table width="81%" border="0" cellspacing="0" cellpadding="0" class="sub_title">
        <tr> 
          <td>직인 업로드</td>
        </tr>
      </table>
    </td>
  </tr>
	<tr>
	  <td>&nbsp</td>
	</tr>
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
        <tr> 
          <th class="fstleft" width="40%">직인 업로드</th>
          <td width="60%"><input type="file" name="upfile" desc="직인 업로드" size="30" class="box3"></td>
        </tr>
      </table>
			<br>
			<table width="100%">
			  <tr>
				  <td style="text-align:right">
					 <img src="../img/btn_order.gif" onclick="goUpload()" style="cursor:hand" alt="등록">
					 <img src="../img/btn_close.gif" onclick="goClose()" style="cursor:hand" alt="닫기">
					</td>
			  </tr>
			</table>
    </td>
  </tr>
</table>
</form>
  </body>
</html>