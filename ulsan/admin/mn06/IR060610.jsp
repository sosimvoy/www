<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR060610.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-06-15
* ���α׷�����	   : �ڱݿ�� > �ڱݿ�Ź(���,Ư��)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  
	List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn06.acctList");
  List<CommonEntity> partList = (List<CommonEntity>)request.getAttribute("page.mn06.partList");
  String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String new_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");

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

			function changeAcc(acct_kind) {
				var form = document.sForm;
				document.hidFrame.location = "IR060611.etax?cmd=bankSpecialList&fis_year="+form.fis_year.value+"&acct_kind="+acct_kind;
			}


      function changePart(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR060611.etax?cmd=bankSpecialList&fis_year="+form.fis_year.value+"&acct_kind="+form.acct_kind.value+"&part_code="+part_code;
			}
     
      function goRegister() {
				var form = document.sForm;
				var dep_amt;

				if (form.dep_list.value == "G2" && form.jwasu_no.value == "")	{
					alert("ȯ��ä�� �¼���ȣ�� �����ϼž� �մϴ�.");
					form.jwasu_no.focus();
					return;
				}

                if ((form.dep_list.value == "G1" || form.dep_list.value == "G2") && form.dep_due.value == "")	{
					alert("���⿹��, ȯ��ä�� ��Ź�Ⱓ�� �Է��ؾ� �մϴ�.");
					form.dep_due.focus();
					return;
				}

				if (form.acct_list.value == "" || form.acct_list.value == null)	{
					alert("ȸ����� �����ϼ���.");
					form.acct_list.focus();
					return;
				}

				if (form.dep_amt.value == "0" || form.dep_amt.value == "") {
					alert("�ݾ��� �����Ͻñ� �ٶ��ϴ�.");
					form.dep_amt.focus();
					return;
				}
				if (form.dep_list.value == "G3") {
					form.end_date.value="";
				}

				if ((form.dep_list.value == "G1" || form.dep_list.value == "G2") && form.end_date.value == "") {
					alert("�������� �Է��ϼ���.");
					form.end_date.focus();
					return;
				}

        if (form.dep_list.value != "G3" && (replaceStr(form.end_date.value, " ", "") <= replaceStr(form.new_date.value, " ", ""))) {
          alert("�ű����� �����Ϻ��� Ů�ϴ�.");
          form.end_date.focus();
          return;
        }

        if (form.dep_list.value == "G3" || form.dep_list.value == "G1" || form.dep_list.value == "G4") {
				  form.jwasu_no.value = "0";
				}

        dep_amt = replaceStr(form.dep_amt.value,",","");
				form.dep_amt.value = dep_amt;
				if (confirm("�����Ͻ� �ڱݿ�Ź ���� ����Ͻðڽ��ϱ�?")) {
					form.cmd.value = "bankSpecialReg";
				  form.action = "IR060610.etax";
				  eSubmit(form);
				}
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR060610.etax">
	<input type="hidden" name="cmd" value="bankSpecialList">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="169">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_10.gif"></td>
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
									  <td width="20%">&nbsp;</td>
									  <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�迬��<span style="width:20"></span>
											<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
										<td width="40%"> 
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�豸��<span style="width:20"></span>
											<select name="acct_kind" iValue="<%=request.getParameter("acct_kind")%>" onchange="changeAcc(this.value)">
												<option value="B">Ư��ȸ��</option>
												<option value="D">���Լ��������</option>
												<option value="E">���</option>
											</select>
                    </td>
									</tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  <tr>
									  <td width="20%">&nbsp;</td>
										<td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �μ�<span style="width:44"></span>
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changePart(this.value)">
                      <%for (int i=0; partList != null && i<partList.size(); i++) {
                        CommonEntity partInfo = (CommonEntity)partList.get(i); %>
												<option value="<%=partInfo.getString("M350_PARTCODE")%>"><%=partInfo.getString("M350_PARTNAME")%></option>
                      <% } %>
											</select>
										</td>
                    <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ���<span style="width:32"></span>
											<select name="acct_list" iValue="<%=request.getParameter("acct_list")%>">
											<% for(int i=0; acctList != null && i < acctList.size(); i++ ) { 
												   CommonEntity acctInfo = (CommonEntity)acctList.get(i);
											%>
											  <option value="<%=acctInfo.getString("M360_ACCCODE")%>"><%=acctInfo.getString("M360_ACCNAME")%></option>
											<% } %>	
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
									    ��������<span style="width:20"></span>
											<select name="dep_list" iValue="<%=request.getParameter("dep_list")%>"> 
											  <option value="G1">���⿹��</option>
												<option value="G2">ȯ��ä</option>
												<option value="G3">MMDA</option>
                        <option value="G4">���ڱ�</option>
											</select>
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �ڱ�����<span style="width:10"></span>
											<select name="mmda_list" iValue="<%=request.getParameter("mmda_list")%>"> 
											  <option value="G1">���ݿ���</option>
											  <option value="G2">���⿹��</option>
												<option value="G3">ȯ��ä</option>
												<option value="G4">MMDA</option>
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
									    ���¹�ȣ<span style="width:20"></span>
											<input type="text" class="box3" size="20" maxlength="14" name="acct_no" value="" userType="account" required desc="���¹�ȣ">
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �¼���ȣ<span style="width:20"></span>
											<input type="text" class="box3" size="14" name="jwasu_no" value="" userType="number">
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
											<input type="text" class="box3" size="14" name="dep_amt" value="<%=0%>" userType="money">
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
									    ��Ź�Ⱓ<span style="width:20"></span>
											<input type="text" class="box3" size="14" name="dep_due" value="" userType="number">
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ����<span style="width:44"></span>
											<input type="text" class="box3" size="5" name="dep_rate" value="" userType="decimal" required desc="����">%
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  <tr>
									  <td>&nbsp;</td>
                    <td>
										  ���⿹��(������), ȯ��ä(�ϼ�), MMDA(�ϼ�), ���ڱ�(�ϼ�)
										</td>
										<td>
										 &nbsp;
										</td>
                  </tr>
                  <tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �ű���<span style="width:32"></span>
											<input type="text" class="box3" size="10" name="new_date" value="<%=new_date%>" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','new_date')" align="absmiddle">
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ������<span style="width:32"></span>
											<input type="text" class="box3" size="10" name="end_date" value="" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','end_date')" align="absmiddle">
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td colspan="3"></td>
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
        <td width="975" height="20">&nbsp;</td>
			</tr>
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20" align="right"> <img src="../img/btn_order.gif" alt="���" align="absmiddle" onClick="goRegister()" style="cursor:hand"><span style="width:20"></span></td>
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