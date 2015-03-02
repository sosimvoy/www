<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR060410.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱݿ�Ź �ϰ��� ��ȸ/���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> bankRegisterList  = (List<CommonEntity>)request.getAttribute("page.mn06.bankRegisterList");

	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	int bankRegisterListSize = 0;
	if (bankRegisterList != null ) {
		bankRegisterListSize = bankRegisterList.size();
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
				form.action = "IR060410.etax";
				form.cmd.value = "bankRegisterList";
      	eSubmit(form);
      }

			function goRegister() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=bankRegisterListSize%>;

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
							if (form.stat_list[i].value != "S3")	{
								alert("�ϰ��� �Ǹ� ���� �����մϴ�.");
								return;
							}
						}
					}

					if (cnt ==0) {
						alert("����� ���� �����ϼ���.");
						return;
					}
				}
        if (confirm("���� ��(��)�� �ϰ��� ����ϰڽ��ϱ�?")) {
          form.cmd.value = "bankDepCancel";
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
  <form name="sForm" method="post" action="IR060410.etax">
	<input type="hidden" name="cmd" value="bankRegisterList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_6.gif"></td>
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
              <th class="fstleft" width="5%">����<input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
							<th width="8%">ȸ�迬��</th>
              <th width="8%">��������</th>
              <th width="18%">�űԱݾ�</th>
							<th width="8%">����(%)</th>
							<th width="8%">��Ź�Ⱓ</th>
							<th width="8%">������</th>
							<th width="11%">ó�����</th>
							<th width="16%">���¹�ȣ</th>
							<th width="10%">�¼���ȣ</th>
            </tr>
						<% long tot_amt = 0L;
               if (bankRegisterListSize > 0 && bankRegisterList != null) { 
							   String due_date = "";
							   for (int i=0; i < bankRegisterListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankRegisterList.get(i);
									 String state = rowData.getString("M120_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " ����";
									 } else if ("".equals(rowData.getString("M120_DEPOSITDATE")) ) {
                     due_date = "";
                   } else {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " ��";
									 }
						%>
            <tr>
						  <% if ("S3".equals(rowData.getString("M120_DEPOSITSTATE")) ) { %>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<% } else { %>
							<td class="fstleft">&nbsp;<input type="hidden" name="allotChk" value="<%=i%>"></td>
							<% } %>
							<td class="center">&nbsp;<%=rowData.getString("M120_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSITTYPE_NAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M120_DEPOSITAMT"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("M120_INTERESTRATE")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M120_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="center">&nbsp;<%=rowData.getString("STATE_NAME")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M120_ACCOUNTNO"))%></td>
							<td class="right">&nbsp;<%=rowData.getString("M120_JWASUNO")%></td>
							<input type="hidden" name="stat_list" value="<%=rowData.getString("M120_DEPOSITSTATE")%>">
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M120_SEQ")%>">
							<input type="hidden" name="stat_type" value="<%=rowData.getString("M120_DEPOSITTYPE")%>">
							<input type="hidden" name="inamt" value="<%=rowData.getString("M120_DEPOSITAMT")%>">
							<input type="hidden" name="due_day" value="<%=rowData.getString("M120_DEPOSITDATE")%>">
							<input type="hidden" name="acct_no" value="<%=rowData.getString("M120_ACCOUNTNO")%>">
							<input type="hidden" name="fis_year" value="<%=rowData.getString("M120_YEAR")%>">
							<input type="hidden" name="chk_val">
            </tr>
						<%     tot_amt += rowData.getLong("M120_DEPOSITAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="5">�հ�</td>
              <td class="right" colspan="5"><%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
		        <%   } else { %>
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
              <td><img src="../img/btn_cancel.gif" alt="���" style="cursor:hand" onClick="goRegister()" align="absmiddle"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>