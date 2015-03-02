<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR071310.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-19
* 프로그램내용	   : 세입 > 일계/월계조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn07.jsp" %>

<%
  List<CommonEntity>budgetpartList =    (List<CommonEntity>)request.getAttribute("page.mn07.budgetpartList");
  List<CommonEntity>budgetList     =    (List<CommonEntity>)request.getAttribute("page.mn07.budgetprtList");
	
	String SucMsg = (String)request.getAttribute("page.mn07.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String queprtty  = request.getParameter("queprtty");   // 조회구분

  String ago_acc_date  = (String)request.getAttribute("page.mn07.ago_acc_date");    // 전영업일 받아오기

  String input_date = request.getParameter("input_date");
  
  if(input_date.equals("") || input_date == null){
      input_date = TextHandler.formatDate(ago_acc_date,"yyyyMMdd","yyyy-MM-dd");
  }
  
  String que_date = request.getParameter("que_date");
  if(que_date.equals("") || que_date == null){
      que_date = TextHandler.formatDate(ago_acc_date,"yyyyMMdd","yyyy-MM-dd");
  }

  String end_date = request.getParameter("end_date");
  if(end_date.equals("") || end_date == null){
      end_date = TextHandler.formatDate(ago_acc_date,"yyyyMMdd","yyyy-MM-dd");
  }

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int budgetSize = 0;
	if (budgetList != null && budgetList.size() > 0) {
		budgetSize = budgetList.size();
	}
  int partSize = 0;
	if (budgetpartList != null && budgetpartList.size() > 0) {
		partSize = budgetpartList.size();
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

      	function init() {
				typeInitialize();
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
					alert("<%=SucMsg%>");
				<% } %>
			}

      function goSearch() {
				var form = document.sForm;

		    form.cmd.value = "budgetprtm500";
		    form.action    = "IR071310.etax";
				form.target = "_self";
				eSubmit(form);
			}

      function goexcel() {
				var form = document.sForm;

		    form.cmd.value = "budgetprtm501";
		    form.action    = "IR071311.etax";
				eSubmit(form);
			}
      
			 function selqueprtty(selmok)	{
				var form = document.sForm;

				form.action = "IR071310.etax";
				form.cmd.value = "budgetprtm500";
				eSubmit(form);
			}

     
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
		<form name="sForm" method="post" action="IR071310.etax">
    <input type="hidden" name="cmd" value="budgetdayList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_25.gif"></td>
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
										<td width="80">&nbsp;</td>
										<td width="800">
	  					         <span style="width:10px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">조회구분</span>
											<select name="queprtty" iValue="<%=request.getParameter("queprtty")%>" style="width:100" >
												<option value="1">세입일계</option>
												<option value="2">세입월계</option>
  										</select>
							         <span style="width:5px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">부서</span>
												 <select name="quepartCode" iValue="<%=request.getParameter("quepartCode")%>" style="width:100">
												<option value="">전체</option>
												<option value="SUM">본청합계</option>
								        <% for (int i=0; budgetpartList != null && i < budgetpartList.size(); i++) {
									         CommonEntity partCdInfo = (CommonEntity)budgetpartList.get(i); %>												    
								        <option value="<%=partCdInfo.getString("M551_PARTCODE")%>"><%=partCdInfo.getString("M551_PARTNAME")%></option>
									      <% } %>
								        </select>
							         <span style="width:30px"></span>
                    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">세구분</span>
											<select name="quetaxgbn" iValue="<%=request.getParameter("quetaxgbn")%>" style="width:90" >
												<option value="">전체</option>
												<option value="0">지방세</option>
												<option value="1">국세</option>
												<option value="2">세외수입</option>
												<option value="4">의존수입</option>
  										</select>
                      <br>
							         <span style="width:10px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										  <span class="point">회계연도</span>
											  <select name="queyear" iValue="<%=request.getParameter("queyear")%>">
												  <option value="<%=this_year%>"><%=this_year%></option>
  												<option value="<%=last_year%>"><%=last_year%></option>
	  										</select>
							         <span style="width:50px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">조회일자</span>
										<input type="text" class="box3" name="que_date" value="<%=que_date%>" size="8" userType="date" desc="조회일자">
    									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','que_date');" style="cursor:hand">
      - <input type="text" class="box3" name="end_date" value="<%=end_date%>" size="8" userType="date" desc="조회일자">
  <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','end_date');" style="cursor:hand">
							         <span style="width:200px"></span>
											<img src="../img/btn_search.gif" border="0" onClick="goSearch()" style="cursor:hand">
											<img src="../img/btn_excel.gif" border="0" onClick="goexcel()" style="cursor:hand">
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
							<th width="30%" colspan="2">과목</th>
							<th width="20%" colspan="2">수납액</th>
							<th width="20%" colspan="2">환부액</th>
							<th width="20%" colspan="2">차감금액</th>
            </tr>
            <tr> 
							<th width="6%">년도</th>
							<th width="10%">과목명</th>
              <th width="10%">건수</th>
              <th width="10%">금액</th>
              <th width="10%">건수</th>
              <th width="10%">금액</th>
              <th width="10%">건수</th>
              <th width="10%">금액</th>
            </tr>
         	 <%
						for (int i=0; budgetList != null && i < budgetList.size(); i++) {
							CommonEntity data = (CommonEntity)budgetList.get(i);
						%>

            <tr>                 
							<td>&nbsp;<%=data.getString("M550_YEAR")%></td>
						  <td>&nbsp;<%=data.getString("M550_SEMOKNAME")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M500_SUNABCNT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M500_SUNABAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M500_HWANBUCNT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M500_HWANBUAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("CHAGAM_CNT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("CHAGAM_AMT"))%></td>
            </tr>
					   <%				 
						} if (budgetList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="8">조회 내역이 없습니다.</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>
