<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050710.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-06-15
* ���α׷�����	   : �ڱݹ��� > �ڱݹ�������е��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> deptList = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");

	List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn05.acctList");

  String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");

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
		<script language="javascript" src="../js/utility.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }

			function goAcct(acct_kind) {
				var form = document.sForm;
				var fake_allot = form.allot_kind;
        /*
				if (acct_kind == "A") {
          deleteOptions(fake_allot);
					addOption(fake_allot, "1", "����");
					addOption(fake_allot, "2", "�����");
				} else {
					deleteOptions(fake_allot);
					addOption(fake_allot, "1", "����");
					addOption(fake_allot, "2", "�����");
					addOption(fake_allot, "3", "�����ݳ�");
					addOption(fake_allot, "4", "������ݳ�");
				}
        */
			}

			function changeSemok(acc) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+acc+"&allot_kind="+form.allot_kind.value+"&dept_kind="+form.dept_kind.value;
			}

			function chAllot(allot) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+form.acc_type.value+"&allot_kind="+allot+"&dept_kind="+form.dept_kind.value;
			}

			function chAcct(dept) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+form.acc_type.value+"&allot_kind="+form.allot_kind.value+"&dept_kind="+dept+"&bonus=1";
			}


      function chAcct_T(dept) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+form.acc_type.value+"&allot_kind="+form.allot_kind.value+"&dept_kind="+dept+"&bonus=1";
			}
     
      function goRegister() {
				var form = document.sForm;

				if (form.dept_kind.value == "" || form.acc_kind.value == "") {
					alert("�μ� �Ǵ� ȸ����� �����ϼ���.");
					form.dept_kind.focus();
					return;
				}

				if (form.sugi_amt.value == "0" || form.sugi_amt.vlaue == "") {
					alert("�ݾ��� �����ϼ���.");
					form.sugi_amt.focus();
					return;
				}
        if (confirm("�ڱݹ���������� ����Ͻðڽ��ϱ�?")) {
					form.cmd.value = "bankSugiReg";
				  form.action = "IR050710.etax";
				  eSubmit(form);
        }				
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050710.etax">
	<input type="hidden" name="cmd" value="bankSugiList">
  <input type="hidden" name="work_log" value="A05">
  <input type="hidden" name="trans_gubun" value="151">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_32.gif"></td>
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
									  <td width="25%">&nbsp;</td>
                    <td width="35%">
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�迬��<span style="width:20"></span>
										  <select name="fis_year" value="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
                    <td width="40%">&nbsp;</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ������<span style="width:20"></span>
											<input type="text" class="box3" size="10" name="acc_date" value="<%=acc_date%>" userType="date" required desc="ȸ������">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
										</td>
                    <td>&nbsp;</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  <tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ��������<span style="width:20"></span>
											<select name="allot_kind" iValue="<%=request.getParameter("allot_kind")%>" onchange="chAllot(this.value); goAcct(this.value)">
                        <option value="1">����</option>
												<option value="2">�����</option>
												<option value="3">�����ݳ�</option>
												<option value="4">������ݳ�</option>
											</select>
										</td>
                    <td>&nbsp;</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  <tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�豸��<span style="width:20"></span>
											<select name="acc_type" onchange="changeSemok(this.value)">
												<option value="B">Ư��ȸ��</option>
												<option value="E">���</option>
                        <option value="A">�Ϲ�ȸ��</option>
											</select>
										</td>
                    <td>&nbsp;</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �μ�<span style="width:44"></span>
											<select name="dept_kind" value="<%=request.getParameter("dept_kind")%>" onchange="chAcct(this.value)">
											<%for (int i=0; deptList!=null && i<deptList.size(); i++) { 
												  CommonEntity dept = (CommonEntity)deptList.get(i); %>
												<option value="<%=dept.getString("M350_PARTCODE")%>"><%=dept.getString("M350_PARTNAME")%></option>
											<%}%>
											</select>
										</td>
                    <td>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �����ϴ� �μ�<span style="width:44"></span>
											<select name="t_dept_kind" value="<%=request.getParameter("t_dept_kind")%>" onchange="chAcct_T(this.value)">
												<option value="00000">��û</option>
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
									    ȸ���<span style="width:32"></span>
											<select name="acc_kind" iValue="<%=request.getParameter("acc_kind")%>">
											<%for (int i=0; acctList!=null && i<acctList.size(); i++) { 
												  CommonEntity dept = (CommonEntity)acctList.get(i); %>
												<option value="<%=dept.getString("M360_ACCCODE")%>"><%=dept.getString("M360_ACCNAME")%></option>
											<%}%>
											</select>
										</td>
                    <td>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �����ϴ� ȸ���<span style="width:32"></span>
											<select name="t_acc_kind" iValue="<%=request.getParameter("t_acc_kind")%>">
												<option value="11">�Ϲ�ȸ��</option>
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
									    �ݾ�<span style="width:44"></span>
											<input type="text" class="box3" size="15" name="sugi_amt" value="0" userType="money" required desc="�ݾ�">
											<span style="width:40"></span>
											<img src="../img/btn_order.gif" alt="���" align="absmiddle" onClick="goRegister()" style="cursor:hand">
										</td>
                    <td>&nbsp;</td>
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