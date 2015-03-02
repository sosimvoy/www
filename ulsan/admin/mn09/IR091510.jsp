<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091510.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > �������ڵ����
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn09.jsp" %>

<%
	List<CommonEntity>NapbujaList =    (List<CommonEntity>)request.getAttribute("page.mn09.NapbujaList");
  String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (NapbujaList != null && NapbujaList.size() > 0) {
		listSize = NapbujaList.size();
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
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
		    form.cmd.value = "NapbujaList";
		    form.action    = "IR091510.etax";
				form.target = "_self";
				eSubmit(form);
			}
      
			 function gosaveData()	{
				var form = document.sForm;
        
				if (form.Napbuja.value == "") {
          alert("�����ڼ����� ����ϼ���.");
          form.Napbuja.focus();
          return;
        }
				if (form.Address.value == "") {
          alert("�ּҸ� ����ϼ���.");
          form.Address.focus();
          return;
        }

				form.Napbuja.value=form.Napbuja.value.replace(/\s/g,"");
				form.Address.value=form.Address.value.replace(/\s/g,"");
				form.action = "IR091510.etax";
				form.cmd.value = "insertNapbuja";
				form.target = "_self";
				eSubmit(form);
			}

		  function goCancel(){
				var form = document.sForm;
				var listSize = <%=listSize%>;
				var cnt = 0;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}
				if (listSize == 1) {
					if (!form.userChk.checked) {
						alert("������ ���� �����ϼ���");
						return;
					}
				} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.userChk[i].checked) {
							cnt++;
						}
					}
					 
						if (cnt == 0) {
						alert("������ ���� �����ϼ���");
						return;
						}
					}
				 form.action = "IR091510.etax";  
				 form.cmd.value ="deleteNapbuja";
				 eSubmit(form);
			 }
    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR091510.etax">
    <input type="hidden" name="cmd" value="accCodeList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_34.gif"></td>
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
										<td width="50">&nbsp;</td>
										<td width="750">
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">������</span>
													<input type="text" maxlength="30" name="Napbuja" class="box3" size="10" value=""> 
													<span style="width:10px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">�ּ�</span>
													<input type="text" name="Address" class="box3" size="80" maxlength="100" value="">
											 <td width="50"> 
											<div align="right"><img src="../img/btn_order.gif" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
											</div>
										 </td>
									</tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
            </tr>
          </table>
	        <br>
				  	<hr>
					<br>
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
						<tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="50">&nbsp;</td>
										<td width="750">&nbsp;
											 <td width="50"> 
											<div align="right"><img src="../img/btn_search.gif" width="55" height="21" border="0" onClick="goSearch()" style="cursor:hand">
											</div>
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
              <th class="fstleft" width="4%">����</th>
							<th width="10%">������</th>
							<th width="86%">�ּ�</th>
            </tr>
         	 <%
						for (int i=0; NapbujaList != null && i < NapbujaList.size(); i++) {
							CommonEntity data = (CommonEntity)NapbujaList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M560_NAPBUJA")%></td>
						  <td>&nbsp;<%=data.getString("M560_ADDRESS")%></td>
							<input type="hidden" name="delnapbuja" value="<%=data.getString("M560_NAPBUJA")%>">
            </tr>
					   <%				 
						} if (NapbujaList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="3">&nbsp;</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
					<table class="btn">
						<tr>
							<td>
								<img src="../img/btn_delete2.gif" alt="����" align="absmiddle" onclick="goCancel()" style="cursor:hand">
							</td>
						</tr>
					</table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>	