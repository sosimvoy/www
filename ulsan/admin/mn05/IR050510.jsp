<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050510.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > ���µ��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> bankAcctInfoList  = (List<CommonEntity>)request.getAttribute("page.mn05.bankAcctInfoList");

	List<CommonEntity> deptList  = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");  //�μ�����ȸ
 
	List<CommonEntity> accList   = (List<CommonEntity>)request.getAttribute("page.mn05.accList");  //ȸ�����ȸ

	String delMsg =  (String)request.getAttribute("page.mn05.DelMsg");
	if (delMsg == null) {
		delMsg = "";
	}

	int bankAcctInfoListSize = 0;
	if (bankAcctInfoList != null ) {
		bankAcctInfoListSize = bankAcctInfoList.size();
	}

	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	String last_year = "";
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
		<script language="javascript" src="../js/utility.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }

			function chAcc(dept_cd) {
				var form = document.sForm;
				document.hidFrame.location = "IR050512.etax?cmd=bankAcctInfoList&acc_type="+form.acc_type.value+"&dept_cd="+dept_cd;
			}

			function chDept(acc_type) {
				var form = document.sForm;
				document.hidFrame.location = "IR050512.etax?cmd=bankAcctInfoList&acc_type="+acc_type+"&flag=1";
			}
      

			function goAcct(acct_kind) {
				var form = document.sForm;
				var fake_allot = form.allot_kind;
				if (acct_kind == "A") {
          deleteOptions(fake_allot);
					addOption(fake_allot, "3", "�����ݳ�");
					addOption(fake_allot, "4", "������ݳ�");
				} else {
					deleteOptions(fake_allot);
					addOption(fake_allot, "1", "����");
					addOption(fake_allot, "2", "�����");
					addOption(fake_allot, "3", "�����ݳ�");
					addOption(fake_allot, "4", "������ݳ�");
				}
			}

			function chBank(acct_gubun) {
				var form = document.sForm;
				var fake_bank_cd = form.bank_cd;

				if (acct_gubun == "01") {
					deleteOptions(fake_bank_cd);
					addOption(fake_bank_cd, "039", "�泲����");
					addOption(fake_bank_cd, "011", "����");
				} else {
					deleteOptions(fake_bank_cd);
					addOption(fake_bank_cd, "039", "�泲����");
				}
			}
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR050510.etax";
				form.cmd.value = "bankAcctInfoList";
				form.target = "_self";
      	eSubmit(form);
      }

			function goRegister() {
				var form = document.sForm;
				if (form.acct_nm.value == "")	{
					alert("���¸��� �ʼ��Է��Դϴ�.");
					form.acct_nm.focus();
					return;
				}

				if (form.acct_no.value == "")	{
					alert("���¹�ȣ�� �ʼ��Է��Դϴ�.");
					form.acct_no.focus();
					return;
				}

				if (form.juhaeng.value == "Y") {
					if (form.acct_gubun.value != "03") {
						alert("���༼�� ��ȸ���¸� �����ϼž� �մϴ�.");
						form.acct_gubun.focus();
						return;
					} else if (form.acct_no.value.substring(0,3) != "632") {
						alert("���༼�� ���¹�ȣ�� �������� ��û��(632)�̾�� �մϴ�.");
            form.acct_no.focus();
						return;
					} else if (form.acct_no.value.substring(3,5) != "07") {
						alert("���༼�� ���¹�ȣ�� ���뿹��(07)�̾�� �մϴ�.");
            form.acct_no.focus();
						return;
					} else if (form.bank_cd.value != "039") {
            alert("���༼�� �泲������¸� �����մϴ�.");
            form.bank_cd.focus();
            return;
          }
				}

        if (form.bank_cd.value == "039" && form.acct_no.value.substring(3,5) == "07") {
          if (form.acct_gubun.value != "03") {
            alert("�泲���� ���뿹���� ��ȸ���¸� �����ϼž� �մϴ�.");
            form.acct_gubun.value = "03";
            return;
          } else {
            form.acct_gubun.value = "03";
            form.acc_type.value = "";
            form.acc_cd.value = "";
            form.dept_cd.value = "";
          }
        }

				window.open('IR050511.etax', 'ownerPop', 'height=260,width=340,top=100,left=200,scrollbars=yes');
				form.action = "IR050511.etax";
				form.cmd.value = "bankAcctInfoView";
				form.target = "ownerPop";
      	eSubmit(form);
			}


			function goDelete(){
				var form = document.sForm;
				var listSize = <%=bankAcctInfoListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.chk_p.checked)	{
					  alert("������ ���� üũ�ϼ���.");
					  return;
				  }
				} else if (listSize > 1) {
					for (var i=0; i<form.chk_p.length; i++)	{
					  if (form.chk_p[i].checked)	{
						  cnt++;
					  }
				  }

				  if (cnt == 0)	{
					  alert("������ ���� üũ�ϼ���.");
					  return;
				  }
				}
        if (confirm("���� ��(��)�� �����ϰڽ��ϱ�?")) {
          form.action = "IR050510.etax";
			  	form.cmd.value = "bankAcctDel";
			  	form.target = "_self";
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
  <form name="sForm" method="post" action="IR050510.etax">
	<input type="hidden" name="cmd" value="bankAcctInfoList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_19.gif"></td>
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
                    <td width="860"><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�迬��
										  <select name="fis_year" value="<%=request.getParameter("fis_year")%>">
												<% for (int i=Integer.parseInt(this_year); i>=2010; i--) { %>
											<option value="<%=i%>"><%=i%></option>
											<% } %>
											</select>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�豸��
										  <select name="acc_type" iValue="<%=request.getParameter("acc_type")%>" onchange="chDept(this.value)">
												<option value="A">�Ϲ�ȸ��</option>
												<option value="B">Ư��ȸ��</option>
												<option value="D">���Լ��������</option>
												<option value="E">���</option>
											</select><span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �μ�
										  <select name="dept_cd" iValue="<%=request.getParameter("dept_cd")%>" onchange="chAcc(this.value)">
												<% for (int i=0; deptList != null && i < deptList.size(); i++) {
													CommonEntity deptInfo = (CommonEntity)deptList.get(i); %>												    
												<option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
												<% } %>
											</select>
											<br><br>
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ���
										  <select name="acc_cd" iValue="<%=request.getParameter("acc_cd")%>">
												<% for (int i=0; accList != null && i < accList.size(); i++) {
													CommonEntity accInfo = (CommonEntity)accList.get(i); %>												    
												<option value="<%=accInfo.getString("M360_ACCCODE")%>"><%=accInfo.getString("M360_ACCNAME")%></option>
												<% } %>
											</select>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���±���
										  <select name="acct_gubun" iValue="<%=request.getParameter("acct_gubun")%>" onchange="chBank(this.value)">
												<option value="01">�Աݰ���</option>
												<option value="02">��ݰ���</option>
												<option value="03">��ȸ����</option>
                        <option value="04">�ϻ���</option>
											</select><span style="width:122"></span>										  
										  <br><br>
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ����
										  <select name="bank_cd" iValue="<%=request.getParameter("bank_cd")%>">
												<option value="039">�泲����</option>
												<option value="011">����</option>
											</select>
											<span style="width:20"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���¹�ȣ
										  <input type="text" class="box3" size="20" name="acct_no" value="<%=request.getParameter("acct_no")%>" userType="number" maxlength="20">
											<span style="width:20"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���¸�
										  <input type="text" class="box3" size="30" name="acct_nm" value="<%=request.getParameter("acct_nm")%>" maxlength="30">
											<span style="width:20"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���༼����
										  <select name="juhaeng" iValue="<%=request.getParameter("juhaeng")%>">
												<option value="N">No</option>
												<option value="Y">Yes</option>
										  </select>
										</td>
                    <td width="100">
										  <br><br>
                      <img src="../img/btn_order.gif" alt="���" style="cursor:hand" onClick="goRegister()">
											<br><br>
											<img src="../img/btn_search.gif" alt="��ȸ" style="cursor:hand" onClick="goSearch()">
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
              <th class="fstleft" width="3%">
							<input type="checkbox" name="all_chk" onClick="change_check_box_status(sForm.chk_p, this.checked);"></th>
              <th width="5%">����</th>
							<th width="5%">����</th>
							<th width="15%">�μ�</th>
              <th width="7%">ȸ�豸��</th>
              <th width="15%">ȸ���</th>
							<th width="14%">���¸�</th>
							<th width="7%">����</th>
							<th width="12%">���¹�ȣ</th>
							<th width="10%">�����ָ�</th>
							<th width="7%">���༼<br>����</th>
            </tr>
						<% if (bankAcctInfoListSize > 0 && bankAcctInfoList != null) { 
							   for (int i=0; i < bankAcctInfoListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankAcctInfoList.get(i);
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="chk_p" value="<%=i%>"></td>
              <td class="center"><%=rowData.getString("M300_YEAR")%></td>
              <td class="center"><%=rowData.getString("TYPE_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M350_PARTNAME")%></td>
              <td class="center">&nbsp;<%=rowData.getString("ACC_GUBUN")%></td>
              <td class="center">&nbsp;<%=rowData.getString("M360_ACCNAME")%></td>
							<td class="center"><%=rowData.getString("M300_ACCNAME")%></td>
              <td class="center"><%=rowData.getString("M300_BANKCODE")%></td>
							<td class="center"><%=TextHandler.formatAccountNo(rowData.getString("M300_ACCOUNTNO"))%></td>
							<td class="center"><%=rowData.getString("M300_NAME")%></td>
							<td class="center"><%=rowData.getString("M300_JUHANGACCYN")%></td>
							<input type="hidden" name="acctNo_list" value="<%=rowData.getString("M300_ACCOUNTNO")%>">
              <input type="hidden" name="acc_code" value="<%=rowData.getString("M300_ACCCODE")%>">
            </tr>
						<%   }
						%>
		        <% }else { %>
						<tr> 
              <td class="fstleft" colspan="9">&nbsp;</td>
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
              <td>
							  <img src="../img/btn_delete.gif" alt="����" style="cursor:hand" onClick="goDelete()" align="absmiddle">
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
	<% if (!"".equals(delMsg)) { %>
	  <script>
	    alert("<%=delMsg%>");
		</script>
	<% } %>
  </body>
</html>