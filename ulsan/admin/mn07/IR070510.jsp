<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR070510.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-09-10
* 프로그램내용	   : 일계/보고서 > 지급증명서 내역조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
	String su_sdate			  = request.getParameter("su_str_date");
	String su_edate			  = request.getParameter("su_end_date");

	if (su_sdate.equals("")) {
		su_sdate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}  

	if (su_edate.equals("")) {
		su_edate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

	List<CommonEntity> accCdList        = (List<CommonEntity>)request.getAttribute("page.mn07.accCdList");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript">

		function goSearch() {
			var form = document.sForm;
		  
			form.flag.value="Y";

			/*
			document.hidFrame.location.href="IR070511.etax?cmd=jigupReportList&year="+form.year.value+"&su_str_date="+form.su_str_date.value+"&su_end_date="+form.su_end_date.value+"&accGubun="+form.accGubun.value+"&accCode="+form.accCode.value;
	   		form.action = "IR070511.etax";
				form.cmd.value = "jigupReportList";
				form.target = "hidFrame";
				eSubmit(form);
			*/

			pageurl = "/admin/oz51/IR070510.etax";
			funcOpenWindow(form,'post','NeWreport',pageurl , 10,10, 1000, 1000, 0, 0, 0, 0, 1);
		}

		function goPrint() {
			if (document.sForm.flag.value != "Y") {
				alert("검색을 눌러서 자료가 나온 후 인쇄가 가능합니다.");
				return;
			}
			document.hidFrame.focus();
			document.hidFrame.Frameprint();
		}


		function init() {
				typeInitialize();
		}

		function changeType(type) {
		document.location = "IR070510.etax?cmd=jigupReportList&accGubun="+type;
		}
		
		function iframe_autoresize(arg){
			arg.height = eval(arg.name + ".document.body.scrollHeight");
			arg.width = eval(arg.name + ".document.body.scrollWidth");
		}
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
<form name="sForm" method="post" action="IR070510.etax">
<input type="hidden" name="cmd"  value="jigupReportList">
<input type="hidden" name="flag" value="">
<table width="993" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40"><img src="../img/title7_12.gif"></td>
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
				<tr height="30"> 
					<td width="760">
						<span style="width:50px"></span>
						<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
						회계연도
						<select name="year" iValue="<%=request.getParameter("year")%>">
						<option value="<%=this_year%>"><%=this_year%></option>
						<option value="<%=last_year%>"><%=last_year%></option>
						</select>

						<span style="width:50px"></span>
						<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
						회계구분
						<select name="accGubun" desc="회계구분" iValue="<%=request.getParameter("accGubun")%>"  onchange="changeType(this.value)">
							<option value="A">일반회계</option>
							<option value="B">특별회계</option>
							<option value="C">공기업특별회계</option>
							<option value="D">세입세출외현금</option>
							<option value="E">기금</option>
						</select>

						<span style="width:50px"></span>
						<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
						회계명
					 <select name="accCode" iValue="<%=request.getParameter("accCode")%>" style="width:25%">
						<% for (int i=0; accCdList != null && i < accCdList.size(); i++) {
							 CommonEntity accCdInfo = (CommonEntity)accCdList.get(i); %>												    
						<option value="<%=accCdInfo.getString("M390_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
						<% } %>
					 </select>
            <br><br>
						<span style="width:50px"></span>
						<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
						조회기간
						<input type="text" style="width:80" name="su_str_date" value="<%=su_sdate%>" userType="date" required desc="시작일"> 
						<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_str_date');" style="cursor:hand"> -
						<input type="text" style="width:80" name="su_end_date" value="<%=su_edate%>" userType="date" required desc="종료일"> 
						<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_end_date');" style="cursor:hand">
					</td>
					<td width="100" align="right">  
						<img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand" align="absmiddle">&nbsp;
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
</table>

	<tr>
	  <td>
		<iframe name="hidFrame" width="1050" height="1000" onload="iframe_autoresize(this);" scrolling="no" frameBorder="0"></iframe>
		</td>
  </tr>
	<table width="1000">
		<tr>
			<td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
		</tr>
	</table>
</form>
<body>
</html>