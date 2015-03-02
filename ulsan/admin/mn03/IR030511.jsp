<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030511.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-28
* 프로그램내용	   : 세입세출외현금 > 주행세 조회/수정/삭제
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

<% 
    CommonEntity juheangView = (CommonEntity)request.getAttribute("page.mn03.juheangView");
		String SucMsg = (String)request.getAttribute("page.mn03.SucMsg");

		String last_year = "";
		String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		int last_int = Integer.parseInt(this_year) - 1;
		last_year = String.valueOf(last_int);
   
		String acc_date             = request.getParameter("acc_date");            //회계일자
	  
		if (acc_date.equals("")) {
			acc_date  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
	}  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function init() {
		typeInitialize();
		<% if (SucMsg != null && !"".equals(SucMsg)) { %>
       alert("<%=SucMsg%>");
	     self.close();	
   <% } %>
  }
	
  function gosaveData()	{
  	var form = document.sForm;
		form.action = "IR030511.etax";
		form.cmd.value = "UpdateJuheang";
		form.target = "_self";
  	eSubmit(form);
  }

//-->
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" oncontextmenu="return false">
<form name="sForm" method="post" action="IR030511.etax">
<input type="hidden" name="cmd" value="UpdateJuheang">
<% if (juheangView != null && juheangView.size() > 0) {%>
<input type="hidden" name="seq" value="<%=juheangView.getString("M060_SEQ")%>">
<% } %>
<table width="500" border="0" cellspacing="0" cellpadding="5">
  <tr> 
    <td> 
      <table width="81%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
					<td width="18">&nbsp;</td>
					<td width="482" height="50"><img src="../img/title3_8.gif"></td>
				</tr>
      </table>
    </td>
  </tr>
	<% if (juheangView != null && juheangView.size() > 0) {%>
  <tr>
    <td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
					<table width="372" border="0" cellspacing="1" cellpadding="2">
						<tr> 
							<th class="fstleft" width="20%"><font color="#000000" size="2">회계연도</font></th>
							<td width="58%"> 
								<div align="left"> 
									<select name="year" style="width:55%" iValue="<%=request.getParameter("year")%>">
										<option value="<%=this_year%>"><%=this_year%></option>
										<option value="<%=last_year%>"><%=last_year%></option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr> 
							<th class="fstleft" width="5%"><font color="#000000" size="2">회계일자</font></th>
							<td class="left" width="58%"> 
								<div align="left"> 
									<input type="text" name="acc_date" class="box3" size="8" value="<%=acc_date%>" userType="date"  style="width:48%">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand">
								</div>
							</td>
						</tr>
						<tr> 
							<th class="fstleft" width="5%"><font color="#000000" size="2">징수구분</font></th>
							<td class="left" width="58%"> 
								<div align="left"> 
									<select name="jingsuType" style="width:55%">
										<option value="J1">주된특별징수 수납부</option>
										<option value="J2">특별징수 수납부</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr> 
							<th class="fstleft" width="5%"><font color="#000000" size="2">원리금구분</font></th>
							<td class="left" width="58%"> 
								<div align="left"> 
									<select name="repayType" style="width:55%">
										<option value="R1">원금</option>
										<option value="R2">이자</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr> 
							<th class="fstleft" width="5%"><font color="#000000" size="2">금액구분</font></th>
							<td class="left" width="58%"> 
								<div align="left"> 
									<select name="cashType" style="width:55%">
										<option value="C1">수입액</option>
										<option value="C2">과오납</option>
										<option value="C3">지급액</option>
										<option value="C4">반납액</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr> 
							<th class="fstleft" width="20%"><font color="#000000" size="2">금액</font></th>
							<td width="58%"> 
								<div align="left"> 
								 <input type="text" name="amt" class="box3" size="19" value="<%=juheangView.getString("M060_AMT")%>">
								</div>
							</td>
						</tr>
						<tr> 
							<th class="fstleft" width="20%"><font color="#000000" size="2">납세의무자</font></th>
							<td width="58%"> 
								<div align="left"> 
									<input type="text" name="napseja" class="box3" size="19" value="<%=juheangView.getString("M060_NAPSEJA")%>">
								</div>
							</td>
						</tr>
					</td>
				</tr>
      </table> 
			<tr>
				<td width="250"> 
				<div align="right"><img src="../img/btn_modify.gif" alt="수정" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
				</div>
				</td>
			</tr>
		</td>
	</tr>
<% } %>
</table>
</form>
</body>
</html>