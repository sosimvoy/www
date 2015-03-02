<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090110.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-20
* ���α׷�����	   : �ý��ۿ > ����ڵ��/�����û������ȸ/����
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
	List<CommonEntity>historyList = (List<CommonEntity>)request.getAttribute("page.mn09.historyList");
	String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  int listSize = 0;
	if (historyList != null && historyList.size() > 0) {
		listSize = historyList.size();
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
				form.cmd.value = "loginHistoryList";
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
			 form.action = "IR090110.etax";  
			 form.cmd.value ="deleteUser";
			 form.target = "_self";
			 eSubmit(form);
		 }
	 
		function goConfirm(){
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

					if (cnt > 1) {
						alert("�����ϰ����ϴ� ���� �ϳ��� �����ϼ���.");
						return;
					}
				}
				
				form.action = "IR090110.etax"
				form.cmd.value = "updateUser";
				form.target = "_self";
				eSubmit(form);
			}		
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
  <form name="sForm" method="post" action="IR090110.etax">
	<input type="hidden" name="cmd" value="loginHistoryList">
	<input type="hidden" name="work_log" value="A09">
	<input type="hidden" name="trans_gubun" value="115">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_1.gif"></td>
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
                    <td width="860"><span style="width:500"></span></td>
                    <td width="100"> 
                      <img src="../img/btn_search.gif" alt="��ȸ" style="cursor:hand" onClick="goSearch()" align="absmiddle">
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
							<th width="10%">��û����</th>
              <th width="10%">����ڸ�</th>
							<th width="10%">�Ҽӱ��</th>
							<th width="10%">�ҼӺμ�</th>
							<th width="10%">�ֿ����</th>
							<th width="10%">����Ǳ���</th>
							<th width="9%">å�������</th>
							<th width="9%">å���ڹ�ȣ</th>
							<th width="9%">�ܸ���ȣ</th>
							<th width="9%">��û����</th>
            </tr>

       
						<%
						for (int i=0; historyList != null && i < historyList.size(); i++) {
							CommonEntity data = (CommonEntity)historyList.get(i);
						%>

            <tr>                 
              <td class="fstleft"> <input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M260_REQUESTDATE")%>  </td>
              <td>&nbsp;<%=data.getString("M260_USERNAME")%>     </td>
					    <td>&nbsp;<%=data.getString("M260_CURRENTORGAN")%> </td>
	           
							<% if ("Y".equals(data.getString("M260_PARTSTATE")) ) {  %>
              <td>
							 <input type="text" value="<%=data.getString("CHANGEPART")%>" size="8" name="change_part" readonly style="border:0px ; color:red">
							</td>
							<% } %>
							<% if ("N".equals(data.getString("M260_PARTSTATE")) ) {  %>
							<td>&nbsp;<%=data.getString("CURRENTPART")%> </td>
              <% } %>
							
							<% if ("Y".equals(data.getString("M260_WORKSTATE1")) ) {  %>
              <td>
						  	<input type="text" value="<%=data.getString("CHANGEWORK1")%>" size="8" name="change_work1" readonly style="border:0px ; color:red">
						  </td>
							<% } %>
							<% if ("N".equals(data.getString("M260_WORKSTATE1")) ) {  %>
							<td>&nbsp;<%=data.getString("CURRENTWORK1")%> </td>
              <% } %>
              
							<% if ("Y".equals(data.getString("M260_SIGNSTATE")) ) {  %>
              <td>
								<input type="text" value="<%=data.getString("CHANGESIGNTYPE")%>" size="8" name="change_signtype" readonly style="border:0px ; color:red">
							</td>
							<% } %>
							<% if ("N".equals(data.getString("M260_SIGNSTATE")) ) {  %>
							<td>&nbsp;<%=data.getString("CURRENTSIGNTYPE")%> </td>  
              <% } %>
	          
							<td>&nbsp;<%=data.getString("M260_MANAGERBANKERNO")%> </td>
							<td>&nbsp;<%=data.getString("M260_MANAGERNO")%> </td>
							<td>&nbsp;<%=data.getString("M260_TERMINALNO")%> </td>
			   
              <% if ("N".equals(data.getString("M260_USERSTATE")) ) { %>
								<td>���� ��û</td>
							<%} else if("Y".equals(data.getString("M260_PARTSTATE")) ||"Y".equals(data.getString("M260_WORKSTATE1"))|| "Y".equals(data.getString("M260_SIGNSTATE")) )  {  %>
						  <td> ���� ��û </td>
							<% } %>
							<input type="hidden" name="userid_list" value="<%=data.getString("M260_USERID")%>">
							<input type="hidden" name="change_part_list" value="<%=data.getString("M260_CHANGEPART")%>">
							<input type="hidden" name="change_work1_list" value="<%=data.getString("M260_CHANGEWORK1")%>">
							<input type="hidden" name="change_signtype_list" value="<%=data.getString("M260_CHANGESIGNTYPE")%>">
							<input type="hidden" name="current_part_list" value="<%=data.getString("M260_CURRENTPART")%>">
							<input type="hidden" name="current_work1_list" value="<%=data.getString("M260_CURRENTWORK1")%>">
							<input type="hidden" name="current_signtype_list" value="<%=data.getString("M260_CURRENTSIGNTYPE")%>">
            </tr>
					  <%				 
				      } if (historyList == null || historyList.size() == 0) { 
				    %>
				    <tr> 
              <td class="fstleft" colspan="11">&nbsp;</td>
            </tr>
				    <% } %>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_confirm.gif" alt="����" style="cursor:hand" onClick="goConfirm()" align="absmiddle">
								<img src="../img/btn_cancel.gif" alt="���" style="cursor:hand" onClick="goCancel()" align="absmiddle">
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