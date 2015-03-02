<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091710.jsp
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
	List<CommonEntity>standardpartList =    (List<CommonEntity>)request.getAttribute("page.mn09.standardpartList");

  List<CommonEntity>usePartList    =    (List<CommonEntity>)request.getAttribute("page.mn09.usePartList");
	List<CommonEntity>IncomepartList =    (List<CommonEntity>)request.getAttribute("page.mn09.IncomepartList");
 
	
	String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (standardpartList != null && standardpartList.size() > 0) {
		listSize = standardpartList.size();
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
			
		    form.cmd.value = "stdPartSel";
		    form.action    = "IR091710.etax";
				form.target = "_self";
				eSubmit(form);
			}

			 function gosaveData()	{
				var form = document.sForm;
        
				if (form.year.value == "") {
          alert("�⵵�� �����ϼ���.");
          form.year.focus();
          return;
        }
				if (form.stdPart.value == "") {
          alert("ǥ�غμ��� �����ϼ���.");
          form.stdPart.focus();
          return;
        }
				if (form.stdPartName.value == "") {
          alert("ǥ�غμ����� �����ϼ���.");
          form.stdPartName.focus();
          return;
        }
				if (form.sysPartcode.value == "") {
          alert("�μ��� �����ϼ���.");
          form.sysPartcode.focus();
          return;
        }

				if (form.incomePart.value == "") {
          alert("����μ��� �����ϼ���.");
          form.incomePart.focus();
          return;
        }
				form.stdPartName.value=form.stdPartName.value.replace(/\s/g,"");
				form.action = "IR091710.etax";
				form.cmd.value = "stdPartInt";
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
				 form.action = "IR091710.etax";  
				 form.cmd.value ="stdPartDel";
				 eSubmit(form);
			 }
     
     function ltrim(stdPartName) {
			 var form = document.sForm
			return form.stdPartName.replace(/^\s+/,"");
		}

    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR091710.etax">
    <input type="hidden" name="cmd" value="stdPartSel">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_36.gif"></td>
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
													<span class="point">ǥ�غμ�</span>
													<input type="text" name="stdPart" class="box3" size="11" value="" maxlength="11">
													<span style="width:30px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">�μ���</span>
													<input type="text" name="stdPartName" class="box3" size="26" value="" maxlength="40">
													<br><br>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">�μ��ڵ�</span>
														<select name="sysPartcode" iValue="<%=request.getParameter("sysPartcode")%>" style="width:140px">
															<option value="">-- ���� --</option>
														<% for (int i=0; usePartList != null && i < usePartList.size(); i++) {
															 CommonEntity deptInfo = (CommonEntity)usePartList.get(i); %>												    
															 <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
														<% } %>
														</select>
										 	    <span style="width:20px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">����μ�</span>
														<select name="incomePart" iValue="<%=request.getParameter("incomePart")%>" style="width:140px">
															<option value="">-- ���� --</option>
														<% for (int i=0; IncomepartList != null && i < IncomepartList.size(); i++) {
															 CommonEntity deptInfo = (CommonEntity)IncomepartList.get(i); %>												    
															 <option value="<%=deptInfo.getString("M551_PARTCODE")%>"><%=deptInfo.getString("M551_PARTNAME")%></option>
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
										<td width="60">&nbsp;</td>
										<td width="740">
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">ȸ�迬��</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<select name="queyear" iValue="<%=request.getParameter("queyear")%>">
															<option value="<%=this_year%>"><%=this_year%></option>
															<option value="<%=last_year%>"><%=last_year%></option>
														</select>                     
										 </td>
                     <td width="50">
                        <img src="../img/btn_search.gif" border="0" onClick="goSearch()" style="cursor:hand">
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
							<th width="8%">ǥ�غμ�</th>
              <th width="12%">ǥ�غμ���</th>
							<th width="12%">���μ�</th>
							<th width="12%">����μ�</th>
            </tr>
         	 <%
						for (int i=0; standardpartList != null && i < standardpartList.size(); i++) {
							CommonEntity data = (CommonEntity)standardpartList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M410_YEAR")%></td>
							<td>&nbsp;<%=data.getString("M410_STANDARDPARTCODE")%></td>
							<td>&nbsp;<%=data.getString("M410_PARTNAME")%></td>
							<td>&nbsp;<%=data.getString("M410_SYSTEMPARTCODE")%></td>
							<td>&nbsp;<%=data.getString("M410_BUDGETPARTCODE")%></td>
							<input type="hidden" name="delyear" value="<%=data.getString("M410_YEAR")%>">
							<input type="hidden" name="delpart" value="<%=data.getString("M410_STANDARDPARTCODE")%>">
            </tr>
					   <%				 
						} if (standardpartList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="6">��ȸ ������ �����ϴ�.</td>
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