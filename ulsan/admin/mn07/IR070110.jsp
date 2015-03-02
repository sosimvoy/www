<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070110.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-09
* 프로그램내용		: 일계/보고서 > 보고서조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>

<%
	List<CommonEntity> reportCodeList = (List<CommonEntity>)request.getAttribute("page.mn07.reportCodeList");
	List<CommonEntity> partList = (List<CommonEntity>)request.getAttribute("page.mn07.partList");
	List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn07.accList");

	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");

    String acc_date = request.getParameter("acc_date");

    if(acc_date.equals("") || acc_date == null){
        acc_date =  TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	

<script language="javascript">
	
function goSearch(){
    var form =  document.forms[0];
	var pageurl = "";
    if(document.getElementById("accDiv").style.display == "none"){
        form.acc_type.value = "";
        form.part_code.value = "";
        form.acc_code.value = "";
    }
    form.part_name.value    = form.part_code.options[form.part_code.selectedIndex].text;   // 부서명값
    form.acc_name.value     = form.acc_code.options[form.acc_code.selectedIndex].text;      // 회계명값 
    
	form.target = "report";
    form.cmd.value = "dayReportAll";
	pageurl = "/admin/oz51/"+form.report_gubun.value + form.report_code.value.split("|")[0] + ".etax";

	/*
	form.action = "../mn07/IR0701"+form.report_code.value.substring(4);  // 앞3자리는 보고서코드
    form.submit();
  */

	funcOpenWindow(form,'post','NeWreport',pageurl , 10,10, 1000, 1000, 0, 0, 0, 0, 1);
	
}


function chAccBox(val){
    var report_code = val.substring(0,3);
    //특정보고서만 회계구분,부서,회계명 선택 가능
    if(report_code == "102" || report_code == "113" || report_code == "104" || report_code == "114" || report_code == "115" || report_code == "205" || report_code == "206" || report_code == "207" || report_code == "305" || report_code == "306" || report_code == "307"){
        document.getElementById("accDiv").style.display = 'block';
    }else{
        document.getElementById("accDiv").style.display = 'none';
    }
}


function chReport(report_gubun) {
    var form = document.sForm;
    document.hidFrame.location = "IR070101.etax?cmd=cityReportList&report_gubun="+report_gubun+"&flag=2";
    document.getElementById("accDiv").style.display = 'none';
}


function chDept(acc_type) {
    var form = document.sForm;
    var acc_code;
    if (acc_type == "A")	{
        acc_code = "11";
    }	else if (acc_type == "B")	{
        acc_code = "04";
    } else if (acc_type == "D") {
        acc_code = "01"
    } else if (acc_type == "E") {
        acc_code = "01"
    }
    document.hidFrame.location = "IR070101.etax?cmd=cityReportList&acc_type="+acc_type+"&acc_code="+acc_code+"&flag=0";
}


function chAcc(part_code) {
    var form = document.sForm;
    document.hidFrame.location = "IR070101.etax?cmd=cityReportList&acc_type="+form.acc_type.value+"&part_code="+part_code+"&flag=1";
}
function iframe_autoresize(arg){
	arg.height = eval(arg.name + ".document.body.scrollHeight");
	arg.width = eval(arg.name + ".document.body.scrollWidth");
}

function init() {
    typeInitialize();
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
<form name="sForm" method="post" action="IR070110.etax">
<input type="hidden" name="cmd" value="cityReportList">
<input type="hidden" name="part_name" value = "">
<input type="hidden" name="acc_name" value = "">
<input type="hidden" name="saveFile" value = "">
<table width="993" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40"><img src="../img/title7_1.gif"></td>
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
								<td width="880">
									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									보고서구분 
									<select name="report_gubun" desc="보고서구분" iValue="<%=request.getParameter("report_gubun")%>" onchange="chReport(this.value)">
										<option value="D">일일보고서</option>
										<option value="M">월말보고서</option>
										<option value="Q">분기말보고서</option>
									</select>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									보고서종류 
									<select name="report_code" desc="보고서종류" iValue="<%=request.getParameter("report_code")%>" onchange="chAccBox(this.value)">
                                        <% 
										if (reportCodeList.size() > 0 && reportCodeList != null) { 
											for (int i=0; i < reportCodeList.size(); i++) {
												CommonEntity rowData = (CommonEntity)reportCodeList.get(i);
										%>
										<option value="<%=rowData.getString("REPORTCODE")%>|<%=rowData.getString("REPORTURL")%>"><%=rowData.getString("REPORTNAME")%></option>
										<%
												}
											}
										%>
									</select>
                                    <br><br>
									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									회계연도
									<select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
									<% for (int i = Integer.parseInt(this_year); i >= 2010; i--) { %>
										<option value="<%=i%>"><%=i%></option>
                  <% } %>
									</select>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									회계일자 <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand"> 
									
                                    <!-- 회계구분/부서/회계명 START -->
                                    <div id="accDiv" style="display:none;">
                                    <br>
									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									회계구분
									<select name="acc_type" desc="회계구분" iValue="<%=request.getParameter("acc_type")%>" onchange="chDept(this.value)">
										<option value="">전체</option>
										<option value="A">일반회계</option>
										<option value="B">특별회계</option>
										<option value="C">공기업특별회계</option>
										<option value="D">세입세출외현금</option>
										<option value="E">기금</option>
									</select>

									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									부서
									<select name="part_code" desc="부서" iValue="<%=request.getParameter("part_code")%>" onchange="chAcc(this.value)">
										<% 
										if (partList.size() > 0 && partList != null) { 
											for (int i=0; i < partList.size(); i++) {
												CommonEntity rowData = (CommonEntity)partList.get(i);
										%>
										<option value="<%=rowData.getString("PARTCODE")%>"><%=rowData.getString("PARTNAME")%></option>
										<%
												}
											}
										%>
									</select>

									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									회계명
									<select name="acc_code" desc="회계명" iValue="<%=request.getParameter("acc_code")%>">
										<% 
										if (accList.size() > 0 && accList != null) { 
											for (int i=0; i < accList.size(); i++) {
												CommonEntity rowData = (CommonEntity)accList.get(i);
										%>
										<option value="<%=rowData.getString("ACCCODE")%>"><%=rowData.getString("ACCNAME")%></option>
										<%
												}
											}
										%>
									</select>
									<span style="width:20px"></span>
                                    </div>
                                    <!-- 회계구분/부서/회계명 END -->
								</td>
								<td>
									<img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand" align="absmiddle">  
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
	<tr height="20">
		<td width="18">&nbsp;</td>
		<td width="975">&nbsp;</td>
	</tr>
</table>
</form>

<form id="rForm" method="post">
<iframe id="report" name="report" width="1050" height="300" onload="iframe_autoresize(this);" scrolling="no" frameBorder="0">
</iframe>
</form>

<iframe name="hidFrame" width="0" height="0"></iframe>

<table width="993">
	<tr>
		<td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
</body>
</html>