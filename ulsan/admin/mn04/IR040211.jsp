<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR090511.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-21
* ���α׷�����	   : ���ܼ��� > ���꼭 ����
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
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
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
                  ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="mok" class="box3" size="12" value="<%=budgetView.getString("M080_MOK")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="semok" class="box3" size="12" value="<%=budgetView.getString("M080_SEMOKCODE")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									�Ұ���ó&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="sogwanpart" class="box3" size="12" value="<%=budgetView.getString("M080_SOGWANPART")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									�ǰ���&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="silgwa" class="box3" size="12" value="<%=budgetView.getString("M080_SILGWA")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									����ڸ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="username" class="box3" size="12" value="<%=budgetView.getString("M080_USERNAME")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									������ȣ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="intelno" class="box3" size="4" value="<%=budgetView.getString("M080_INTELNO")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select name="gwamok" ivalue="<%=budgetView.getString("M080_GWAMOK")%>">
								   <option value="511-01" <%if ("511-01".equals(request.getParameter("gwamok_val")) ) { %>selected <% } %>>����</option>
									 <option value="511-02" <%if ("511-02".equals(request.getParameter("gwamok_val")) ) { %>selected <% } %>>��Ư</option>
									 <option value="511-03" <%if ("511-03".equals(request.getParameter("gwamok_val")) ) { %>selected <% } %>>���</option>
								  </select>					
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									�����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="businessname" class="box3" size="35" value="<%=budgetView.getString("M080_BUSINESSNAME")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									���ʿ����&nbsp;
									<input type="text" name="dangchoamt" class="box3" size="8" style="text-align:right;"value="<%=budgetView.getString("M080_DANGCHOAMT")%>" userType="money">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 

							   	<a href="javascript:goChukyung('<%=budgetView.getString("M080_SEQ")%>','<%=budgetView.getString("M080_YEAR")%>')">
                  <font color="red">�߰濹����</font></a>
								  <input type="hidden" name="seq" value="<%=budgetView.getString("M080_SEQ")%>">
							    <input type="hidden" name="year" value="<%=budgetView.getString("M080_YEAR")%>">
                </td>
              </tr>
							<tr>
								<td width="360"> 
								<div align="center"><img src="../img/btn_modify.gif" alt="����" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
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