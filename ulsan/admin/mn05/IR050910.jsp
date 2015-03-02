<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050910.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-06-15
* ���α׷�����	   : �ڱݹ��� > �׿������Կ䱸���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> outAcctList = (List<CommonEntity>)request.getAttribute("page.mn05.outAcctList");

	List<CommonEntity> inAcctList = (List<CommonEntity>)request.getAttribute("page.mn05.inAcctList");

  String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String last_year = "";
	String llast_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	int llast_int = last_int - 1;
	last_year = String.valueOf(last_int);
	llast_year = String.valueOf(llast_int);
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

			function chOutAcct(last_year) {
				var form = document.sForm;
				document.hidFrame.location = "IR050911.etax?cmd=surplusList&last_year="+last_year+"&flag=0";
			}

			function chInAcct(this_year) {
				var form = document.sForm;
				document.hidFrame.location = "IR050911.etax?cmd=surplusList&this_year="+this_year+"&flag=1";
			}
     
      function goRegister() {
				var form = document.sForm;

				if (form.out_acct.value == form.in_acct.value) {
					alert("�̿�ȸ�迬���� ����ȸ�迬������ �۾ƾ��մϴ�.");
					form.out_acct.focus();
					return;
				}

				if (form.in_amt.value == "0" || form.in_amt.vlaue == "") {
					alert("���Աݾ��� �����ϼ���.");
					form.in_amt.focus();
					return;
				}
        if (confirm("�׿��������� �䱸�Ͻðڽ��ϱ�?")) {
					form.cmd.value = "surplusReg";
				  form.action = "IR050910.etax";
				  eSubmit(form);
        }				
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050910.etax">
	<input type="hidden" name="cmd" value="surplusList">
	<input type="hidden" name="work_log" value="A05">
	<input type="hidden" name="trans_gubun" value="171">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_26.gif"></td>
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
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td width="20%">
											&nbsp;
										</td>
									  <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �̿�ȸ�迬��<span style="width:20"></span>
										  <select name="last_year" value="<%=request.getParameter("last_year")%>" onchange="chOutAcct(this.value)">
												<option value="<%=last_year%>"><%=last_year%></option>
												<option value="<%=llast_year%>"><%=llast_year%></option>
											</select>
                    </td>
                    <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ��ݰ���<span style="width:20"></span>
											<select name="out_acct" value="<%=request.getParameter("out_acct")%>">
											<%for (int i=0; outAcctList != null && i < outAcctList.size(); i++) { 
												CommonEntity outAcct = (CommonEntity)outAcctList.get(i); %>
												<option value="<%=outAcct.getString("M300_ACCOUNTNO")%>"><%=TextHandler.formatAccountNo(outAcct.getString("M300_ACCOUNTNO"))%>(<%=outAcct.getString("M300_ACCNAME")%>)</option>
											<%}%>
											</select>
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp; </td>
									  <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ����ȸ�迬��<span style="width:20"></span>
										  <select name="this_year" value="<%=request.getParameter("this_year")%>" onchange="chInAcct(this.value)">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �Աݰ���<span style="width:20"></span>
											<select name="in_acct" value="<%=request.getParameter("in_acct")%>">
											<%for (int i=0; inAcctList != null && i < inAcctList.size(); i++) { 
												CommonEntity inAcct = (CommonEntity)inAcctList.get(i); %>
												<option value="<%=inAcct.getString("M300_ACCOUNTNO")%>"><%=TextHandler.formatAccountNo(inAcct.getString("M300_ACCOUNTNO"))%>(<%=inAcct.getString("M300_ACCNAME")%>)</option>
											<%}%>
											</select>
										</td>										
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  <tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���Աݾ�<span style="width:44"></span>
											<input type="text" class="box3" size="15" name="in_amt" value="0" userType="money" required desc="�ݾ�">
										</td>
										<td>
										  <img src="../img/btn_order.gif" alt="���" align="absmiddle" onClick="goRegister()" style="cursor:hand">
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
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
	<iframe name="hidFrame" width="0" height="0"></iframe>
  </body>
	<% 
	if (!"".equals(SucMsg)) { %>
		<script>
		alert("<%=SucMsg%>");
		</script>
	<%
	} %>
</html>