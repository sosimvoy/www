<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091610.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > ǥ�ؼ����ڵ�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn09.jsp" %>

<%
	List<CommonEntity>standardsemokkList =    (List<CommonEntity>)request.getAttribute("page.mn09.standardsemokkList");

  List<CommonEntity>useSemokList =    (List<CommonEntity>)request.getAttribute("page.mn09.useSemokCdList");
	List<CommonEntity>nowIncomeList =    (List<CommonEntity>)request.getAttribute("page.mn09.nowincomeSemokCdList");
	List<CommonEntity>oldIncomeList =    (List<CommonEntity>)request.getAttribute("page.mn09.oldincomeSemokCdList");
 
	
	String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (standardsemokkList != null && standardsemokkList.size() > 0) {
		listSize = standardsemokkList.size();
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
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
					alert("<%=SucMsg%>");
				<% } %>
			}

      function goSearch() {
				var form = document.sForm;
			
		    form.cmd.value = "stdSemokSel";
		    form.action    = "IR091610.etax";
				form.target = "_self";
				eSubmit(form);
			}

      function selAcccode(acccode) {
				var form = document.sForm;
			
        document.hidFrame.location = "IR091611.etax?cmd=stdSemokSel2&year="+form.year.value+"&stdAcccode="+form.stdAcccode.value;
		    //form.cmd.value = "stdSemokSel2";
		    //form.action    = "IR091610.etax";
				//form.target = "_self";
				//eSubmit(form);
			}
			 function gosaveData()	{
				var form = document.sForm;
        
				if (form.year.value == "") {
          alert("�⵵�� �����ϼ���.");
          form.year.focus();
          return;
        }
				if (form.stdAcccode.value == "") {
          alert("ǥ��ȸ�踦 �����ϼ���.");
          form.stdAcccode.focus();
          return;
        }
				if (form.stdSemok.value == "") {
          alert("ǥ�ؼ����� �����ϼ���.");
          form.stdSemok.focus();
          return;
        }
				if (form.stdSemokName.value == "") {
          alert("������� �����ϼ���.");
          form.stdSemokName.focus();
          return;
        }

				if (form.nowincomeSemok.value == "") {
          alert("���⵵��������� �����ϼ���.");
          form.nowincomeSemok.focus();
          return;
        }
				if (form.oldincomeSemok.value == "") {
          alert("���⵵��������� �����ϼ���.");
          form.oldincomeSemok.focus();
          return;
        }
				form.stdSemokName.value=form.stdSemokName.value.replace(/\s/g,"");
				form.action = "IR091610.etax";
				form.cmd.value = "stdSemokInt";
				eSubmit(form);
			}


		  function goCancel(){
				var form = document.sForm;
				var listSize = <%=listSize%>;
				var cnt = 0;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}
				if (listSize == 1) {
					if (!form.userChk.checked) {
						alert("������ ���� �����ϼ���");
						return;
					}
				} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.userChk[i].checked) {
							cnt++;
						}
					}
					 
						if (cnt == 0) {
						alert("������ ���� �����ϼ���");
						return;
						}
					}
				 form.action = "IR091610.etax";  
				 form.cmd.value ="stdSemokDel";
				 eSubmit(form);
			 }
     
     function ltrim(stdSemokName) {
			 var form = document.sForm
			return form.stdSemokName.replace(/^\s+/,"");
		}

    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR091610.etax">
    <input type="hidden" name="cmd" value="partCodeList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_35.gif"></td>
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
										<td width="60">&nbsp;</td>
										<td width="740"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">ȸ�迬��</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<select name="year" iValue="<%=request.getParameter("year")%>">
															<option value="<%=this_year%>"><%=this_year%></option>
															<option value="<%=last_year%>"><%=last_year%></option>
														</select>
										 	    <span style="width:20px"></span>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
													<span class="point">ǥ��ȸ��</span>
														<select name="stdAcccode" iValue="<%=request.getParameter("stdAcccode")%>" onChange="selAcccode(this.value)">
                              <option value="">-- ���� --</option>
															<option value="31">�Ϲ�ȸ��</option>
															<option value="51">Ư��ȸ��</option>
													</select>
													<span style="width:20px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">ǥ�ؼ���</span>
													<input type="text" name="stdSemok" class="box3" size="6" value="" maxlength="6">
													<span style="width:30px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">�����</span>
													<input type="text" name="stdSemokName" class="box3" size="26" value="" maxlength="40">
													<br><br>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">�����ڵ�</span>
														<select name="sysSemokcode" iValue="<%=request.getParameter("sysSemokcode")%>" style="width:140px">
															<option value="">-- ���� --</option>
														<% for (int i=0; useSemokList != null && i < useSemokList.size(); i++) {
															 CommonEntity deptInfo = (CommonEntity)useSemokList.get(i); %>												    
															 <option value="<%=deptInfo.getString("M370_SEMOKCODE")%>"><%=deptInfo.getString("M370_SEMOKNAME")%></option>
														<% } %>
														</select>
										 	    <span style="width:20px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">���⵵�������</span>
														<select name="nowincomeSemok" iValue="<%=request.getParameter("nowincomeSemok")%>" style="width:140px">
															<option value="">-- ���� --</option>
														<% for (int i=0; nowIncomeList != null && i < nowIncomeList.size(); i++) {
															 CommonEntity deptInfo = (CommonEntity)nowIncomeList.get(i); %>												    
															 <option value="<%=deptInfo.getString("M550_SEMOKCODE")%>"><%=deptInfo.getString("M550_SEMOKNAME")%></option>
														<% } %>
													</select>
													<span style="width:15px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">���⵵�������</span>
													<select name="oldincomeSemok" iValue="<%=request.getParameter("oldincomeSemok")%>" style="width:140px">
															<option value="">-- ���� --</option>
														<% for (int i=0; oldIncomeList != null && i < oldIncomeList.size(); i++) {
															 CommonEntity deptInfo = (CommonEntity)oldIncomeList.get(i); %>												    
															 <option value="<%=deptInfo.getString("M550_SEMOKCODE")%>"><%=deptInfo.getString("M550_SEMOKNAME")%></option>
														<% } %>
													</select>
											 <td width="50"> 
											<div align="right"><img src="../img/btn_order.gif" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
											</div>
										 </td>
									</tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
            </tr>
          </table>
	        <br>
				  	<hr>
					<br>
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
						<tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="110">&nbsp;</td>
										<td width="600">
								  	<span style="width:150px"></span>
                    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">ȸ�迬��</span>
											<select name="queyear" iValue="<%=request.getParameter("queyear")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
								  	<span style="width:30px"></span>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
													<span class="point">ǥ��ȸ��</span>
														<select name="questdAcccode" iValue="<%=request.getParameter("questdAcccode")%>">
															<option value="31">�Ϲ�ȸ��</option>
															<option value="51">Ư��ȸ��</option>
													</select>
								  	<span style="width:30px"></span>
												<td width="150"> 
											<div align="right">
                      <img src="../img/btn_search.gif" border="0" onClick="goSearch()" style="cursor:hand">
											</div>
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
              <th class="fstleft" width="4%">����</th>
							<th width="8%">ȸ�迬��</th>
							<th width="8%">ǥ�ؼ���</th>
              <th width="12%">ǥ�ؼ����</th>
							<th width="12%">��뼼��</th>
							<th width="12%">���⵵�������</th>
							<th width="12%">���⵵�������</th>
            </tr>
         	 <%
						for (int i=0; standardsemokkList != null && i < standardsemokkList.size(); i++) {
							CommonEntity data = (CommonEntity)standardsemokkList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M420_YEAR")%></td>
							<td>&nbsp;<%=data.getString("M420_STANDARDSEMOKCODE")%></td>
							<td>&nbsp;<%=data.getString("M420_SEMOKNAME")%></td>
							<td>&nbsp;<%=data.getString("M420_SYSTEMSEMOKCODE")%></td>
							<td>&nbsp;<%=data.getString("M420_BUDGETSEMOKCODE")%></td>
							<td>&nbsp;<%=data.getString("M420_BUDGETSEMOKBYEAR")%></td>
							<input type="hidden" name="delyear" value="<%=data.getString("M420_YEAR")%>">
							<input type="hidden" name="delacccode" value="<%=data.getString("M420_STANDARDACCCODE")%>">
							<input type="hidden" name="delsemokcode" value="<%=data.getString("M420_STANDARDSEMOKCODE")%>">
            </tr>
					   <%				 
						} if (standardsemokkList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="8">��ȸ ������ �����ϴ�.</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
					<table class="btn">
						<tr>
							<td>
								<img src="../img/btn_delete2.gif" alt="����" align="absmiddle" onclick="goCancel()" style="cursor:hand">
							</td>
						</tr>
					</table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
    <iframe name="hidFrame" width="0" height="0"></iframe>
  </form>
  </body>
</html>