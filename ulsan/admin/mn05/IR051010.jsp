<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR051010.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �׿������Կ䱸��ȸ/���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> surplusCancelList  = (List<CommonEntity>)request.getAttribute("page.mn05.srpCancelList");
	int surplusCancelListSize = 0;
	if (surplusCancelList != null ) {
		surplusCancelListSize = surplusCancelList.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
  String reg_date = request.getParameter("reg_date");
	if ("".equals(reg_date) ) {
    reg_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR051010.etax";
				form.cmd.value = "bankSrpCancelList";
      	eSubmit(form);
      }

			function goCancel(){
				var form = document.sForm;
				var listSize = <%=surplusCancelListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("���ó���� ���� üũ�ϼ���.");
					  return;
				  } else {
						if (form.into_state.value != "S1") {
							alert("�䱸��ϰǸ� ���ó���� �����մϴ�.");
							form.allotChk.checked = false;
							return;
						}
            cnt = 1;
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
							if (form.into_state[i].value != "S1") {
							  alert("�䱸��ϰǸ� ���ó���� �����մϴ�.");
								form.allotChk[i].checked = false;
							  return;
						  }
						  cnt++;							
					  }
				  }

				  if (cnt == 0)	{
					  alert("���ó���� ���� üũ�ϼ���.");
					  return;
				  }
				}
        
				if (confirm("���ð��� ���ó���Ͻðڽ��ϱ�?")) {
					form.action = "IR051010.etax";
				  form.cmd.value = "bankSrpCancel";
				  eSubmit(form);
				}
			}

			function change_check_box_status(obj, isChecked) {
        if (obj)  {
          if( obj.type == 'checkbox' ){
            obj.checked = isChecked;
          } else {
            for( var i = 0; i< obj.length; i++ ) {
							if (obj[i].type == 'checkbox') {
								obj[i].checked = isChecked;
							}
            }
          }
        }
      }

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR051010.etax">
	<input type="hidden" name="cmd" value="bankSrpCancelList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_27.gif"></td>
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
                    <td width="860">
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �������
										  <input type="text" class="box3" size="8" name="reg_date" value="<%=reg_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','reg_date')" align="absmiddle">
										</td>
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
              <th class="fstleft" width="5%">����<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="13%">�̿�ȸ�迬��</th>
              <th width="20%">��ݰ���</th>
							<th width="13%">����ȸ�迬��</th>
							<th width="20%">�Աݰ���</th>
							<th width="15%">���Աݾ�</th>
							<th width="14%">ó������</th>
            </tr>
						<% if (surplusCancelListSize > 0 && surplusCancelList != null) { 
							   for (int i=0; i < surplusCancelListSize; i++) {
									 CommonEntity rowData = (CommonEntity)surplusCancelList.get(i);
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_YEAROVER")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOOVER"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("M310_YEARINTO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOINTO"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M310_INTOTAMT"))%></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_INTOSTATE_NAME")%></td>
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M310_SEQ")%>">
							<input type="hidden" name="into_state" value="<%=rowData.getString("M310_INTOSTATE")%>">
            </tr>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="7">&nbsp;</td>
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
							<img src="../img/btn_cancel.gif" alt="���" style="cursor:hand" onClick="goCancel()" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
		<% if (!"".equals(SucMsg)) { %>
		<script>
		  alert("<%=SucMsg%>");
		</script>
		<% } %>
  </body>
</html>