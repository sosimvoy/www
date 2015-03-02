<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090610.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > �μ��ڵ����
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
	List<CommonEntity>partCodeList =    (List<CommonEntity>)request.getAttribute("page.mn09.partCodeList");
	List<CommonEntity>allotPartCodeList =    (List<CommonEntity>)request.getAttribute("page.mn09.allotPartCodeList");
 
	
	String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (partCodeList != null && partCodeList.size() > 0) {
		listSize = partCodeList.size();
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
			
		    form.cmd.value = "partCodeList";
		    form.action    = "IR090610.etax";
				form.target = "_self";
				eSubmit(form);
			}
      
			 function gosaveData()	{
				var form = document.sForm;
        
				if (form.partCode.value == "") {
          alert("�μ��ڵ带 �����ϼ���.");
          form.partCode.focus();
          return;
        }
				if (form.partName.value == "") {
          alert("�μ����� �����ϼ���.");
          form.partName.focus();
          return;
        }
				if (form.receiveName.value == "") {
          alert("����ó���� �����ϼ���.");
          form.receiveName.focus();
          return;
        }
				if (form.receiveCode.value == "") {
          alert("����ó�ڵ带 �����ϼ���.");
          form.receiveCode.focus();
          return;
        }

				if (form.reAllotPartYN.value == "N") {
          form.reAllotPartYN.value == "";
        }

				form.partName.value=form.partName.value.replace(/\s/g,"");
				form.action = "IR090610.etax";
				form.cmd.value = "insertPartCode";
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
				 form.action = "IR090610.etax";  
				 form.cmd.value ="deletePartCode";
				 eSubmit(form);
			 }
     
     function ltrim(partName) {
			 var form = document.sForm
			return form.partName.replace(/^\s+/,"");
		}

    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR090610.etax">
    <input type="hidden" name="cmd" value="partCodeList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_21.gif"></td>
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
										<td width="110">&nbsp;</td>
										<td width="690"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">ȸ�迬��</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<select name="year" iValue="<%=request.getParameter("year")%>">
															<option value="<%=this_year%>"><%=this_year%></option>
															<option value="<%=last_year%>"><%=last_year%></option>
                              <option value="<%=Integer.parseInt(this_year)+1%>"><%=Integer.parseInt(this_year)+1%></option>
														</select>
										 	    <span style="width:68px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">�μ��ڵ�</span>&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="text" name="partCode" class="box3" size="10" value="" maxlength="5"> 
													<span style="width:48px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">�μ���</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="text" name="partName" class="box3" size="10" value="" maxlength="20">
													<br><br>
                          <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">����ڵ��</span>
														<select name="insertYN" iValue="<%=request.getParameter("insertYN")%>">
															<option value="Y">Y</option>
															<option value="N">N</option>
														</select>
										 	    <span style="width:85px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">������μ�</span>
														<select name="reAllotPartYN" iValue="<%=request.getParameter("reAllotPartYN")%>">
															<option value="N">N</option>
															<option value="Y">Y</option>
													</select>
													<span style="width:85px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">��������μ�</span>
													<select name="reAllotPartCode" iValue="<%=request.getParameter("reAllotPartCode")%>">
														<% for (int i=0; allotPartCodeList != null && i < allotPartCodeList.size(); i++) {
															 CommonEntity deptInfo = (CommonEntity)allotPartCodeList.get(i); %>												    
															 <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
														<% } %>
													</select>
													<br><br>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">����ó��</span>&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="text" name="receiveName" class="box3" size="10" value="" maxlength="20"> 
													<span style="width:32px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">����ó�ڵ�</span>
													<input type="text" name="receiveCode" class="box3" size="10" value="" maxlength="3">
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
										<td width="690"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">ȸ�迬��</span>
											<select name="under_year" iValue="<%=request.getParameter("under_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
                        <option value="<%=Integer.parseInt(this_year)+1%>"><%=Integer.parseInt(this_year)+1%></option>
											</select>
											 <td width="50"> 
											<div align="right"><img src="../img/btn_search.gif" width="55" height="21" border="0" onClick="goSearch()" style="cursor:hand">
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
							<th width="12%">ȸ�迬��</th>
							<th width="12%">�μ��ڵ�</th>
              <th width="12%">�μ���</th>
							<th width="12%">����ڵ��</th>
							<th width="12%">������μ�</th>
							<th width="12%">��������μ�</th>
							<th width="12%">����ó</th>
							<th width="12%">����ó�ڵ�</th>
            </tr>
         	 <%
						for (int i=0; partCodeList != null && i < partCodeList.size(); i++) {
							CommonEntity data = (CommonEntity)partCodeList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("YEAR")%></td>
						  <td>&nbsp;<%=data.getString("M350_PARTCODE")%></td>
							<td>&nbsp;<%=data.getString("PARTNAME")%></td>
							<td>&nbsp;<%=data.getString("M350_INSERTYN")%></td>
							<td>&nbsp;<%=data.getString("M350_REALLOTPARTYN")%></td>
							<td>&nbsp;<%=data.getString("M350_PARTNAME")%></td>
							<td>&nbsp;<%=data.getString("M350_RECEIVENAME")%></td>
							<td>&nbsp;<%=data.getString("M350_RECEIVECODE")%></td>
							<input type="hidden" name="partCode_list" value="<%=data.getString("M350_PARTCODE")%>">
            </tr>
					   <%				 
						} if (partCodeList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="10">��ȸ ������ �����ϴ�.</td>
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
  </form>
  </body>
</html>