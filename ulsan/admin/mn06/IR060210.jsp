<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR060210.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱݿ�Ź �䱸��ȸ/���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> bankDepositList  = (List<CommonEntity>)request.getAttribute("page.mn06.bankDepositList");

	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int bankDepositListSize = 0;
	if (bankDepositList != null ) {
		bankDepositListSize = bankDepositList.size();
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
				form.action = "IR060210.etax";
				form.cmd.value = "bankDepositList";
      	eSubmit(form);
      }

			function goCancel() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=bankDepositListSize%>;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("����� ���� �����ϼ���.");
						return;
					}
          form.chk_val.value = "Y";
				} else {
					for (var i=0; i<form.stat_list.length; i++) {						
						if (form.allotChk[i].checked)	{
							cnt++;
              form.chk_val[i].value = "Y";
							if (form.stat_list[i].value != "S1")	{
								alert("��� �Ǹ� ���� �����մϴ�.");
								return;
							}
						}
					}

					if (cnt ==0) {
						alert("����� ���� �����ϼ���.");
						return;
					}
				}

				if (confirm("���� ���� ����Ͻðڽ��ϱ�?") ) {
					form.action = "IR060210.etax";
			    form.cmd.value = "bankDepositDel";
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
  <form name="sForm" method="post" action="IR060210.etax">
	<input type="hidden" name="cmd" value="bankDepositList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_2.gif"></td>
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
                    <td width="860"><span style="width:50"></span>
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
              <th class="fstleft" width="4%">����<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
							<th width="7%">ȸ�迬��</th>
              <th width="8%">��������</th>
              <th width="15%">�űԱݾ�</th>
							<th width="13%">����(%)</th>
							<th width="13%">��Ź�Ⱓ</th>
							<th width="15%">������</th>
							<th width="11%">�ڱ�����</th>
							<th width="12%">ó�����</th>
            </tr>
						<% long tot_amt = 0L;
               if (bankDepositListSize > 0 && bankDepositList != null) { 
							   String due_date = "";
							   for (int i=0; i < bankDepositListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankDepositList.get(i);
									 String state = rowData.getString("M120_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " ����";
									 } else {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " ��";
									 }
						%>
            <tr>
						  <% if ("S1".equals(rowData.getString("M120_DEPOSITSTATE")) ) { %>
              <td class="fstleft">&nbsp;<input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<% } else { %>
							<td class="fstleft">&nbsp;<input type="hidden" name="allotChk" value="<%=i%>"></td>
							<% } %>
							<td class="center">&nbsp;<%=rowData.getString("M120_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSITTYPE_NAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M120_DEPOSITAMT"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("M120_INTERESTRATE")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M120_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="center">&nbsp;<%=rowData.getString("MMDA_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("STATE_NAME")%></td>
							<input type="hidden" name="stat_list" value="<%=rowData.getString("M120_DEPOSITSTATE")%>">
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M120_SEQ")%>">
							<input type="hidden" name="chk_val">
            </tr>
						<%      tot_amt += rowData.getLong("M120_DEPOSITAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="5">�հ�</td>
              <td class="right" colspan="4"><%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
		        <%  } else { %>
						<tr> 
              <td class="fstleft" colspan="9">&nbsp;</td>
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
              <td><img src="../img/btn_cancel.gif" alt="���" style="cursor:hand" onClick="goCancel()" align="absmiddle"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>