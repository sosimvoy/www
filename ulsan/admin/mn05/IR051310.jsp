<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR051310.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-06-15
* ���α׷�����	   : �ڱݹ��� > �׿������Լ���е��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn05.accList");

	List<CommonEntity> deptList = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");

  String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");

	String last_year = "";
	String llast_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	int llast_int = last_int - 1;
	last_year = String.valueOf(last_int);
	llast_year = String.valueOf(llast_int);

	String to_day = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","MMdd");
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

			function chAcc(dept_list) {
				var form = document.sForm;
				document.hidFrame.location = "IR051311.etax?cmd=srpSugiList&acc_gubun="+form.acc_gubun.value+"&dept_list="+dept_list;
			}
     
      function goRegister() {
				var form = document.sForm;
				var to_day = <%=to_day%>;
        /*
				if (to_day > '0310') {
					alert("3�� 10�� ���Ŀ��� �׿������Լ������ ����� �� �����ϴ�.");
					return;
				} */

				if (form.this_year.value == form.last_year.value) {
					alert("�̿�ȸ�迬���� ����ȸ�迬������ �۾ƾ��մϴ�.");
					form.this_year.focus();
					return;
				}

				if (form.in_amt.value == "0" || form.in_amt.vlaue == "") {
					alert("���Աݾ��� �����ϼ���.");
					form.in_amt.focus();
					return;
				}
        if (confirm("�׿������Լ������ ����Ͻðڽ��ϱ�?")) {
					form.cmd.value = "srpSugiReg";
				  form.action = "IR051310.etax";
				  eSubmit(form);
        }				
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR051310.etax">
	<input type="hidden" name="cmd" value="srpSugiList">
	<input type="hidden" name="trans_gubun" value="211">
	<input type="hidden" name="work_log" value="A05">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_30.gif"></td>
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
									  <td width="45%">
											&nbsp;
										</td>
									  <td width="55%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �̿�ȸ�迬��<span style="width:20"></span>
										  <select name="last_year" value="<%=request.getParameter("last_year")%>">
												<option value="<%=last_year%>"><%=last_year%></option>
												<option value="<%=llast_year%>"><%=llast_year%></option>
											</select>
                    </td>
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp; </td>
									  <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ����ȸ�迬��<span style="width:20"></span>
										  <select name="this_year" value="<%=request.getParameter("this_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>		
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp; </td>
									  <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ������<span style="width:44"></span>
										  <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
										</td>		
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp; </td>
									  <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�豸��<span style="width:44"></span>
										  <select name="acc_gubun" iValue="<%=request.getParameter("acc_gubun")%>">
												<option value="B">Ư��ȸ��</option>
											</select>
										</td>		
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp; </td>
									  <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �μ�<span style="width:68"></span>
										  <select name="dept_list" value="<%=request.getParameter("dept_list")%>" onchange="chAcc(this.value)">
											<%for (int i=0; deptList!=null && i<deptList.size(); i++) {
												CommonEntity acc = (CommonEntity)deptList.get(i); %>
												<option value="<%=acc.getString("M350_PARTCODE")%>"><%=acc.getString("M350_PARTNAME")%></option>
											<%}%>
											</select>
										</td>		
                  </tr>
									<tr>
									  <td colspan="2">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp; </td>
									  <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ���<span style="width:56"></span>
										  <select name="acc_list" value="<%=request.getParameter("acc_list")%>">
											<%for (int i=0; accList!=null && i<accList.size(); i++) {
												CommonEntity acc = (CommonEntity)accList.get(i); %>
												<option value="<%=acc.getString("M360_ACCCODE")%>"><%=acc.getString("M360_ACCNAME")%></option>
											<%}%>
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
									    ���Աݾ�<span style="width:44"></span>
											<input type="text" class="box3" size="15" name="in_amt" value="0" userType="money" required desc="�ݾ�">
											<span style="width:44"></span>
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