<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090810.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > �����ڵ����
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
	List<CommonEntity>semokCodeList =    (List<CommonEntity>)request.getAttribute("page.mn09.semokCodeList");
	List<CommonEntity> accCdList        = (List<CommonEntity>)request.getAttribute("page.mn09.accCdList");
	List<CommonEntity> accCdList1        = (List<CommonEntity>)request.getAttribute("page.mn09.accCdList1");

  String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (semokCodeList != null && semokCodeList.size() > 0) {
		listSize = semokCodeList.size();
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
		    form.cmd.value = "semokCodeList2";
		    form.action    = "IR090810.etax";
        form.target="_self";
				eSubmit(form);
			}
      
			 function gosaveData()	{
        var form = document.sForm;
			  	
					if (form.semokCode.value == "") {
          alert("���� �ڵ带 ����ϼ���.");
          form.semokCode.focus();
          return;
         }
					if (form.semokName.value == "") {
          alert("������� ����ϼ���.");
          form.semokName.focus();
          return;
         }

				form.action = "IR090810.etax";
				form.cmd.value = "insertSemokCode";
        form.target="_self";
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
				 form.action = "IR090810.etax";  
				 form.cmd.value ="deleteSemokCode";
         form.target="_self";
				 eSubmit(form);
			 }


      function goView(){
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

          if (cnt > 1) {
						alert("������ ��� �� �Ǹ� �����ϼ���");
					  return;
					}
				}
				form.action = "IR090811.etax";  
				form.cmd.value ="semokCodeView";
        window.open('IR090811.etax', 'semokCodeView' ,'height=170,width=700,top=10,left=200,scrollbars=no');				
				form.target = "semokCodeView";
				eSubmit(form);
			}


			function changeType(type) {
				document.location = "IR090810.etax?cmd=semokCodeList&accGubun="+type+"&year="+document.sForm.year.value;
			}

			function changeType1(type) {
				document.location = "IR090810.etax?cmd=semokCodeList&accGubun1="+type;
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR090810.etax">
    <input type="hidden" name="cmd" value="semokCodeList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_23.gif"></td>
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
										<td width="30">&nbsp;</td>
										<td width="780"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">ȸ�迬��</span>
											<select name="year" iValue="<%=request.getParameter("year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
                        <option value="<%=Integer.parseInt(this_year)+1%>"><%=Integer.parseInt(this_year)+1%></option>
											</select>
											<span style="width:10px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">ȸ�豸��</span>
											<select name="accGubun" iValue="<%=request.getParameter("accGubun")%>"  onchange="changeType(this.value)">
												<option value="A">�Ϲ�ȸ��</option>
												<option value="B">Ư��ȸ��</option>
												<option value="C">�����Ư��ȸ��</option>
												<option value="D">���Լ��������</option>
												<option value="E">���</option>
											</select>
											<span style="width:10px"></span>
												<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
												<span class="point">ȸ���</span>
												 <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" style="width:25%">
								        <% for (int i=0; accCdList != null && i < accCdList.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)accCdList.get(i); %>												    
								        <option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:10px"></span>
												<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
												<span class="point">��������</span>
												<select name="workGubun" iValue="<%=request.getParameter("workGubun")%>">
													<option value="0">����</option>
													<option value="1">����</option>
													<option value="2">�ڱݹ���</option>
													<option value="3">�ڱݿ��</option>
													<option value="4">���Լ��������</option>
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
						<td height="10"></td>
						</tr>
					  <tr>
							 <td width="900" style="padding-left:30px;">
								 <table width="800" border="0" cellspacing="0" cellpadding="0" class="list">
									<tr> 
										<th width="200">����׸�</th>
										<th width="200" colspan="2">�����ڵ�</th>
										<th width="200">�����</th>  
										<th width="200">��������</th>
									</tr>
									 <%
										 String mokGubun = "";
											for (int i=0; i < 4; i++) {
												if (i==0){
													mokGubun  = "��";
												} else if (i == 1){
													mokGubun = "��";
												} else if (i == 2){
													mokGubun = "��";
												} else if (i == 3){
													mokGubun = "��";
												}		 		 	 		 
										%>
									<tr>                 
										<td>&nbsp;
										 <%=mokGubun%>
										 <input type = "hidden" name="mokGubun" value="<%=i%>">
										</td>
										<td colspan="2">
                    <span style="width:30px"></span>
										  <input type="text" maxlength="7" name="semokCode" class="box3" size="20" value="">
										</td>
										<td>
										  <input type="text" maxlength="30" name="semokName" class="box3" size="20" value="">
										</td>
										<td>
                      <select name="segumGubun" iValue="<%=request.getParameter("segumGubun")%>">
												<option value="1">N</option>
												<option value="2">Y</option>
											</select>
										</td>
									</tr>
								 <% } %>
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
										<td width="50">&nbsp;</td>
										<td width="750"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">ȸ�迬��</span>
											<select name="under_year" iValue="<%=request.getParameter("under_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
                        <option value="<%=Integer.parseInt(this_year)+1%>"><%=Integer.parseInt(this_year)+1%></option>
											</select>
											<span style="width:30px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">ȸ�豸��</span>
											<select name="accGubun1" iValue="<%=request.getParameter("accGubun1")%>" onchange="changeType1(this.value)">
												<option value="A">�Ϲ�ȸ��</option>
												<option value="B">Ư��ȸ��</option>
												<option value="C">�����Ư��ȸ��</option>
												<option value="D">���Լ��������</option>
												<option value="E">���</option>
											</select>
											<span style="width:10px"></span>
												<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
												<span class="point">ȸ���</span>
												 <select name="accCode" iValue="<%=request.getParameter("accCode")%>" style="width:25%">
								        <% for (int i=0; accCdList1 != null && i < accCdList1.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)accCdList1.get(i); %>												    
								        <option value="<%=accCdInfo.getString("M360_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
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
							<th width="12%">ȸ�豸��</th>
							<th width="12%">ȸ���</th>
							<th width="12%">��������</th>
							<th width="12%">����׸�</th>
							<th width="12%">�����ڵ�</th>
							<th width="12%">�����</th>
							<th width="12%">��������</th>
            </tr>
         	 <%
						for (int i=0; semokCodeList != null && i < semokCodeList.size(); i++) {
							CommonEntity data = (CommonEntity)semokCodeList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M370_YEAR")%></td>
						  <td>&nbsp;<%=data.getString("ACCGUBUN")%></td>
							<td>&nbsp;<%=data.getString("M360_ACCNAME")%></td>
							<td>&nbsp;<%=data.getString("WORKGUBUN")%></td>
							<td>&nbsp;<%=data.getString("MOKGUBUN")%></td>
							<td>&nbsp;<%=data.getString("M370_SEMOKCODE")%></td>
						  <td>&nbsp;<%=data.getString("M370_SEMOKNAME")%></td>
							<td>&nbsp;<%=data.getString("M370_SEGUMGUBUN")%></td>
				
							<input type="hidden" name="year_list" value="<%=data.getString("M370_YEAR")%>">
							<input type="hidden" name="accGubun_list" value="<%=data.getString("M370_ACCGUBUN")%>">
							<input type="hidden" name="accCode_list" value="<%=data.getString("M370_ACCCODE")%>">
							<input type="hidden" name="workGubun_list" value="<%=data.getString("M370_WORKGUBUN")%>">
							<input type="hidden" name="semokCode_list" value="<%=data.getString("M370_SEMOKCODE")%>">
							<input type="hidden" name="mokGubun_list" value="<%=data.getString("M370_MOKGUBUN")%>">
            </tr>
					   <%				 
						} if (semokCodeList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="10">&nbsp;</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
					<table class="btn">
						<tr>
							<td>
                <img src="../img/btn_modify.gif" alt="����" align="absmiddle" onclick="goView()" style="cursor:hand">
                <span style="width:20"></span>
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