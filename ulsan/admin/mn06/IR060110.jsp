<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR060110.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-06-15
* ���α׷�����	   : �ڱݿ�� > �ڱݿ�Ź�䱸���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
	
%>
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../js/calendar.js"></script>
  	<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }
     
      function goRegister() {
				var form = document.sForm;
				if (form.deposit_kind.value == "G3" && form.end_date.value != "") {
					form.end_date.value="";
				}

        if ((form.deposit_kind.value == "G1" || form.deposit_kind.value == "G2") && form.end_date.value == "") {
          alert("���⿹��, ȯ��ä�� ������ �ʼ��Է��Դϴ�.");
          form.end_date.focus();
          return;
        }

        if ((form.deposit_kind.value == "G1" || form.deposit_kind.value == "G2") && form.deposit_due.value == "") {
          alert("���⿹��, ȯ��ä�� ��Ź�Ⱓ �ʼ��Է��Դϴ�.");
          form.deposit_due.focus();
          return;
        }

				if (form.deposit_kind.value != "G3" && form.end_date.value == "") {
					alert("�������� �Է��ϼ���.");
					form.end_date.focus();
					return;
				}

        if (form.new_deposit_amt.value == "0" || form.new_deposit_amt.value == "") {
          alert("�ݾ��� �Է��ϼ���.");
          form.new_deposit_amt.focus();
          return;
        }
        if (confirm("���Ի����� ����ϰڽ��ϱ�?")) {
          form.cmd.value = "bankMoneyReg";
			  	form.action = "IR060110.etax";
			  	eSubmit(form);
        }
				
      }

      //�Ѿ� ���ϱ�
			function getTotalAmt(inputObj) {
				var form = document.sForm;
				form.tot_amt.value = "";
				var temp_val = 0;        
				temp_val = parseInt(form.new_deposit_amt.value.replace(/,/g, ""), 10) * parseInt(form.x_cnt.value, 10);
				form.tot_amt.value = formatCurrency(temp_val);
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR060110.etax">
	<input type="hidden" name="cmd" value="bankMoneyReg">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="111">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_1.gif"></td>
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
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td width="45%">&nbsp;</td>
                    <td width="55%">
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�迬��<span style="width:20"></span>
										  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
                  <tr>
									  <td width="45%">&nbsp;</td>
                    <td width="55%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ��������<span style="width:20"></span>
											<select name="deposit_kind" iValue="<%=request.getParameter("deposit_kind")%>">
												<option value="G1">���⿹��</option>
												<option value="G2">ȯ��ä</option>
												<option value="G3">MMDA</option>
											</select>
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �űԱݾ�<span style="width:20"></span>
											<input type="text" class="box3" size="14" name="new_deposit_amt" value="<%="0"%>" required desc="�űԱݾ�" userType="number" onKeyup="getTotalAmt(this); keyupCurrencyObj(this)"> x <input type="text" name="x_cnt" size="2" value="<%="1"%>" class="box3" required desc="�� ��" userType="number" onKeyup="getTotalAmt(this); keyupCurrencyObj(this)"> = <input type="text" name="tot_amt" value="<%="0"%>" class="box3" size="17" userType="money" requried desc="�� ��" readonly>
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ����<span style="width:44"></span>
											<input type="text" class="box3" size="14" name="rate" value="<%="0.0"%>" required desc="����" userType="decimal">%
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ��Ź�Ⱓ<span style="width:20"></span>
											<input type="text" class="box3" size="14" name="deposit_due" value="" userType="number" desc="��Ź�Ⱓ"> ���⿹��(������), ȯ��ä(�ϼ�), MMDA(�ϼ�)
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ������<span style="width:32"></span>
											<input type="text" class="box3" size="14" name="end_date" value="" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','end_date')" align="absmiddle">
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �ڱ�����<span style="width:10"></span>
											<select name="mmda_kind" iValue="<%=request.getParameter("mmda_kind")%>">
                        <option value="G1">���ݿ���</option>
											  <option value="G2">���⿹��</option>
												<option value="G3">ȯ��ä</option>
												<option value="G4">MMDA</option>
											</select>
											<span style="width:40"></span>
											<img src="../img/btn_order.gif" alt="���" align="absmiddle" onClick="goRegister()" style="cursor:hand">
										</td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
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
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </form>
  </body>
	<% 
	if (!"".equals(SucMsg)) { %>
		<script>
		alert("<%=SucMsg%>");
		</script>
	<%
	} %>
</html>