<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR060910.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱ�����䱸��ȸ/���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%

  List<CommonEntity> inchulReqList = (List<CommonEntity>)request.getAttribute("page.mn06.inchulReqList");
  
	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int inchulReqListSize = 0;
	if (inchulReqList != null ) {
		inchulReqListSize = inchulReqList.size();
	}
  String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
				form.action = "IR060910.etax";
				form.cmd.value = "inchulReqList";
      	eSubmit(form);
      }

			function goCancel() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=inchulReqListSize%>;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("����� ���� �����ϼ���.");
						return;
					}
				} else {
					for (var i=0; i<form.allotChk.length; i++) {						
						if (form.allotChk[i].checked)	{
							cnt++;
						}
					}

					if (cnt ==0) {
						alert("����� ���� �����ϼ���.");
						return;
					}
				}
        if (confirm("���� ��(��)�� �䱸����ϰڽ��ϱ�?")) {
          form.cmd.value = "inchulReqDel";
			  	form.action = "IR060910.etax";
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
  <form name="sForm" method="post" action="IR060910.etax">
	<input type="hidden" name="cmd" value="inchulReqList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_12.gif"></td>
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
											<input type="text" class="box3" size="10" name="acc_date" value="<%=acc_date%>" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
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
              <th width="5%">ȸ��<br>����</th>
							<th width="7%">����<br>����</th>
							<th width="12%">���¹�ȣ</th>
							<th width="7%">�¼�<br>��ȣ</th>
							<th width="8%">�ű���</th>
							<th width="8%">������</th>
							<th width="6%">��Ź<br>�Ⱓ</th>
							<th width="5%">����(%)</th>
							<th width="12%">����</th>
							<th width="12%">��������/<br>�����ݾ�</th>
							<th width="8%">�ڱ�<br>����</th>							
              <th width="6%">mmda<br>����</th>		
            </tr>
						<% long tot_amt = 0L;
               long inchul_amt = 0L;
               if (inchulReqListSize > 0 && inchulReqList != null) { 
							   String due_date = "";
							   for (int i=0; i < inchulReqListSize; i++) {
									 CommonEntity rowData = (CommonEntity)inchulReqList.get(i);
									 String state = rowData.getString("M130_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " ����";
									 } else if ("G2".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " ��";
									 }
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
              <td class="center">&nbsp;<%=rowData.getString("M130_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSIT_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M130_ACCOUNTNO"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("M130_JWASUNO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_SINKYUDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
							<td class="right">&nbsp;<%=rowData.getString("M130_INTERESTRATE")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_JANAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_INCHULAMT"))%></td>
							<td class="center">&nbsp;<%=rowData.getString("MMDA_TYPE")%></td>
              <td class="center">&nbsp;
              <% if ("G3".equals(state)) { %>
              <%=rowData.getString("M130_MMDA_CANCEL_YN")%>
              <% } %>
              </td>
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M130_SEQ")%>">
              <input type="hidden" name="input_amt" value="<%=rowData.getString("M130_INCHULAMT")%>">
              <input type="hidden" name="fis_year" value="<%=rowData.getString("M130_YEAR")%>">
              <input type="hidden" name="acct_no" value="<%=rowData.getString("M130_ACCOUNTNO")%>">
              <input type="hidden" name="deposit_type" value="<%=rowData.getString("M130_DEPOSITTYPE")%>">
              <input type="hidden" name="jwasu_no" value="<%=rowData.getString("M130_JWASUNO")%>">
            </tr>
						<%     tot_amt += rowData.getLong("M130_JANAMT");
                   inchul_amt += rowData.getLong("M130_INCHULAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="4">����</td>
              <td class="right" colspan="3"><%=TextHandler.formatNumber(tot_amt)%></td>
              <td class="center" colspan="3">����ݾ�</td>
              <td class="right" colspan="2"><%=TextHandler.formatNumber(inchul_amt)%></td>
            </tr>
		        <% } else { %>
						<tr> 
              <td class="fstleft" colspan="12">&nbsp;</td>
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
		<iframe name="hidFrame" width="0" height="0"></iframe>
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