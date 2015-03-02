<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR090511.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-21
* 프로그램내용	   : 세외수입 > 예산서 수정
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

<% 
    CommonEntity budgetView = (CommonEntity)request.getAttribute("page.mn04.budgetView");

		String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
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
		form.action = "IR040211.etax";
		form.cmd.value = "budgetUpdate";
		form.target = "_self";
  	eSubmit(form);
  }
  function goChukyung(a,b){
			var form = document.sForm;
			
			window.open('IR020213.etax', 'popView' ,'height=420,width=350,top=100,left=100,scrollbars=no');
			 form.seq.value=a;
			 form.year.value=b;
			 form.action = "IR020213.etax";  
			 form.cmd.value ="chukyungView";
			 form.target = "popView";
			 eSubmit(form);
		 }
//-->
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"  oncontextmenu="return false">
<form name="sForm" method="post" action="IR040211.etax">
<input type="hidden" name="cmd" value="budgetUpdate">
<% if (budgetView != null && budgetView.size() > 0) {%>
<input type="hidden" name="seq" value="<%=budgetView.getString("M080_SEQ")%>">
<% } %>
<table width="500" border="0" cellspacing="0" cellpadding="5">
  <tr> 
    <td> 
      <table width="81%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
					<td width="18">&nbsp;</td>
					<td width="482" height="50"><img src="../img/title4_17.gif"></td>
				</tr>
      </table>
    </td>
  </tr>
	<% if (budgetView != null && budgetView.size() > 0) {%>
  <tr>
    <td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="500" border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td>
								  <span style="width:50px"></span>
								  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                  목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="mok" class="box3" size="12" value="<%=budgetView.getString("M080_MOK")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									세목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="semok" class="box3" size="12" value="<%=budgetView.getString("M080_SEMOKCODE")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									소관부처&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="sogwanpart" class="box3" size="12" value="<%=budgetView.getString("M080_SOGWANPART")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									실과별&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="silgwa" class="box3" size="12" value="<%=budgetView.getString("M080_SILGWA")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									담당자명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="username" class="box3" size="12" value="<%=budgetView.getString("M080_USERNAME")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									내선번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="intelno" class="box3" size="4" value="<%=budgetView.getString("M080_INTELNO")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									과목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select name="gwamok" ivalue="<%=budgetView.getString("M080_GWAMOK")%>">
								   <option value="511-01" <%if ("511-01".equals(request.getParameter("gwamok_val")) ) { %>selected <% } %>>국고</option>
									 <option value="511-02" <%if ("511-02".equals(request.getParameter("gwamok_val")) ) { %>selected <% } %>>광특</option>
									 <option value="511-03" <%if ("511-03".equals(request.getParameter("gwamok_val")) ) { %>selected <% } %>>기금</option>
								  </select>					
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									사업명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="businessname" class="box3" size="35" value="<%=budgetView.getString("M080_BUSINESSNAME")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									당초예산액&nbsp;
									<input type="text" name="dangchoamt" class="box3" size="8" style="text-align:right;"value="<%=budgetView.getString("M080_DANGCHOAMT")%>" userType="money">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 

							   	<a href="javascript:goChukyung('<%=budgetView.getString("M080_SEQ")%>','<%=budgetView.getString("M080_YEAR")%>')">
                  <font color="red">추경예산등록</font></a>
								  <input type="hidden" name="seq" value="<%=budgetView.getString("M080_SEQ")%>">
							    <input type="hidden" name="year" value="<%=budgetView.getString("M080_YEAR")%>">
                </td>
              </tr>
							<tr>
								<td width="360"> 
								<div align="center"><img src="../img/btn_modify.gif" alt="수정" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
								</div>
							</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
		</td>
	</tr>
	<% } %>
</table>
</form>
</body>
</html>