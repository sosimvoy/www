<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061110.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱ����������ȸ/�ϰ���/���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%

  List<CommonEntity> inchulPmsList = (List<CommonEntity>)request.getAttribute("page.mn06.inchulPmsList");
  
	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int inchulPmsListSize = 0;
	if (inchulPmsList != null ) {
		inchulPmsListSize = inchulPmsList.size();
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
 
				form.action = "IR061110.etax";
				form.cmd.value = "inchulPmsDelList";
      	eSubmit(form);
      }

			function goConfirm() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=inchulPmsListSize%>;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("����� ���� �����ϼ���.");
						return;
					}

          if (form.state_code.value != "S3") {
            alert("�ϰ��� �Ǹ� ���ó�� �����մϴ�.");
            return;
          }

          form.chk_val.value = "Y";
				} else {
					for (var i=0; i<form.allotChk.length; i++) {						
						if (form.allotChk[i].checked)	{
              if (form.state_code[i].value != "S3") {
                alert("�ϰ��� �Ǹ� ��� ó�� �����մϴ�.");
                return;
              }

              form.chk_val[i].value = "Y";
							cnt++;
						}
					}

					if (cnt ==0) {
						alert("����� ���� �����ϼ���.");
						return;
					}
				}
        if (confirm("���� ��(��)�� �ϰ��� ���ó���ϰڽ��ϱ�?")) {
          form.cmd.value = "inchulPmsDel";
			  	form.action = "IR061110.etax";
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
  <form name="sForm" method="post" action="IR061110.etax">
	<input type="hidden" name="cmd" value="inchulPmsDelList">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="219">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_15.gif"></td>
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
              <th class="fstleft" width="3%">��<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="4%">ȸ��<br>����</th>
							<th width="6%">����<br>����</th>
							<th width="11%">���¹�ȣ</th>
							<th width="4%">�¼�<br>��ȣ</th>
							<th width="8%">�ű���</th>
							<th width="8%">������</th>
							<th width="5%">��Ź<br>�Ⱓ</th>
							<th width="4%">����(%)</th>
							<th width="11%">��������/<br>�����ݾ�</th>				
              <th width="6%">ó��<br>���</th>	
              <th width="8%">����/<br>��������</th>	
              <th width="8%">����<br>����</th>	
              <th width="4%">mmda<br>����</th>	
            </tr>
						<% long ija_amt = 0L;
               long inchul_amt = 0L;
               if (inchulPmsListSize > 0 && inchulPmsList != null) { 
							   String due_date = "";
							   for (int i=0; i < inchulPmsListSize; i++) {
									 CommonEntity rowData = (CommonEntity)inchulPmsList.get(i);
									 String state = rowData.getString("M130_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " ����";
									 } else if ("G2".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " ��";
									 } else {
                     due_date = "";
                   }
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
              <td class="center"><%=rowData.getString("M130_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSIT_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M130_ACCOUNTNO"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("M130_JWASUNO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_SINKYUDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
							<td class="right">&nbsp;<%=rowData.getString("M130_INTERESTRATE")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_INCHULAMT"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("STATE_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_INCHULDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_CANCELINTEREST"))%></td>
              <td>&nbsp;
              <% if ("G3".equals(state)) { %>
              <%=rowData.getString("M130_MMDA_CANCEL_YN")%>
              <% } %>
              </td>
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M130_SEQ")%>">
              <input type="hidden" name="state_code" value="<%=rowData.getString("M130_STATECODE")%>">
              <input type="hidden" name="input_amt" value="<%=rowData.getString("M130_INCHULAMT")%>">
              <input type="hidden" name="due_day" value="<%=rowData.getString("M130_DEPOSITDATE")%>">
              <input type="hidden" name="deposit_type" value="<%=rowData.getString("M130_DEPOSITTYPE")%>">
              <input type="hidden" name="fis_year" value="<%=rowData.getString("M130_YEAR")%>">
              <input type="hidden" name="acct_no" value="<%=rowData.getString("M130_ACCOUNTNO")%>">
              <input type="hidden" name="jwasu_no" value="<%=rowData.getString("M130_JWASUNO")%>">
              <input type="hidden" name="m160_seq" value="<%=rowData.getString("M130_M160SEQ")%>">
              <input type="hidden" name="inchul_date" value="<%=rowData.getString("M130_INCHULDATE")%>">
              <input type="hidden" name="chk_val">
            </tr>
						<%     ija_amt += rowData.getLong("M130_CANCELINTEREST");
                   inchul_amt += rowData.getLong("M130_INCHULAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="4">����ݾ�</td>
              <td class="right" colspan="3"><%=TextHandler.formatNumber(inchul_amt)%></td>
              <td class="center" colspan="3">��������</td>
              <td class="right" colspan="3"><%=TextHandler.formatNumber(ija_amt)%></td>
            </tr>    
		        <% } else { %>
						<tr> 
              <td class="fstleft" colspan="15">&nbsp;</td>
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
              <td><img src="../img/btn_cancel.gif" alt="���" style="cursor:hand" onClick="goConfirm()" align="absmiddle"></td>
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