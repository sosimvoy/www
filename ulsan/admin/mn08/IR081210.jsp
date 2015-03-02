<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR081010.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-16
* 프로그램내용	   : 시스템운영 > 회기초코드 등록
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
  List<CommonEntity>endYearCode =    (List<CommonEntity>)request.getAttribute("page.mn08.endYearCode");
	
	String last_year = "";
	String next_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	int next_int = Integer.parseInt(this_year) + 1;
	last_year = String.valueOf(last_int);
	next_year = String.valueOf(next_int);
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

			function gosaveData(){
        var form = document.sForm;
				 form.action = "IR081210.etax"; 
				 form.cmd.value = "insEndYearCode";
			   eSubmit(form);		
      }
			 
			 function goSearch() {
				var form = document.sForm;
		    form.cmd.value = "endYearCode";
		    form.action    = "IR081210.etax";
				eSubmit(form);
			}
			function goCancel(){
				var form = document.sForm;
			
				 form.action = "IR081210.etax";  
				 form.cmd.value ="deleteEndYearCode";
				 eSubmit(form);
			 }
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
  <form name="sForm" method="post" action="IR081210.etax">
	<input type="hidden" name="cmd" value="endYearCode">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_25.gif"></td>
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
                    <td width="150"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">회계연도</span>
											<select name="year" iValue="<%=request.getParameter("year")%>">
											  <option value="<%=next_year%>"><%=next_year%></option>
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
											</td>
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
              <th width="50%">구분</th>
							<th width="50%">등록건수</th>
            </tr>
						<%
						for (int i=0; endYearCode != null && i < endYearCode.size(); i++) {
							CommonEntity data = (CommonEntity)endYearCode.get(i);
						%>
            <tr>                 
							<td>&nbsp;<%=data.getString("cd_name")%></td>
						  <td>&nbsp;<%=data.getString("cnt")%></td>
            </tr>
            <%				 
						} if (endYearCode == null) { 
				    %>
						<tr>
							<td class="fstleft" colspan="2">조회 내역이 없습니다.</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
        </td>
      </tr>
			<table width="980" border="0" cellspacing="1" cellpadding="1">
				<tr> 
					<td height="50"> 
						<div align="right">
						  <img src="../img/btn_order.gif" alt="등록" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
					  	<img src="../img/btn_delete.gif" alt="삭제" width="55" height="21" border="0" onClick="goCancel()" style="cursor:hand">
					  </div>
					</td>
				</tr>
			</table>
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