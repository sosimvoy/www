<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR051110.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �׿������Խ�����ȸ/����ó��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> srpPmsList  = (List<CommonEntity>)request.getAttribute("page.mn05.srpPmsList");

  String magamState = (String)request.getAttribute("page.mn05.magamState");
  if (magamState == null) {
    magamState = "";
  }
	int srpPmsListSize = 0;
	if (srpPmsList != null ) {
		srpPmsListSize = srpPmsList.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
  String reg_date = request.getParameter("reg_date");
	if ("".equals(reg_date) ) {
    reg_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
      }
     
      function goSearch() {
				var form = document.sForm;
				
				form.action = "IR051110.etax";
				form.cmd.value = "bankSrpPmsList";
        form.target = "_self";
      	eSubmit(form);
      }

      function PowerChk(flag) {
				form = document.sForm;
				form.v_flag.value = flag;
        form.action = "../common/super_one.etax";
        form.target = "hidFrame";
        eSubmit(form);
			}

			function goProcess(){
				var form = document.sForm;
				var listSize = <%=srpPmsListSize%>;
        var cnt = 0;
        var magamState = "<%=magamState%>";
        
				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

        if (magamState == "Y") {
          alert("���Ͼ����� �����Ǿ����ϴ�. �ϰ���ó���� �� �� �����ϴ�.");
          return;
        }

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("�ϰ���ó���� ���� üũ�ϼ���.");
					  return;
				  } else {
						if (form.into_state.value != "S2") {
							alert("���������ΰǸ� �ϰ���ó���� �����մϴ�.");
							form.allotChk.checked = false;
							return;
						}
            cnt = 1;
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
							if (form.into_state[i].value != "S2") {
							alert("���������ΰǸ� �ϰ���ó���� �����մϴ�.");
							form.allotChk[i].checked = false;
							return;
						}
						  cnt++;							
					  }
				  }

				  if (cnt == 0)	{
					  alert("�ϰ���ó���� ���� üũ�ϼ���.");
					  return;
				  }
				}
        
				if (confirm("���� ��(��)�� �ϰ���ó���ϰڽ��ϱ�?")) {
          form.trans_gubun.value = "193";
					form.action = "IR051110.etax";
				  form.cmd.value = "bankSrpProcess";
          form.target = "_self";
				  eSubmit(form);
				}
			}



      function goDelete(){
				var form = document.sForm;
				var listSize = <%=srpPmsListSize%>;
        var cnt = 0;
        var magamState = "<%=magamState%>";

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}


        if (magamState == "Y") {
          alert("���Ͼ����� �����Ǿ����ϴ�. �������ó���� �� �� �����ϴ�.");
          return;
        }

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("�������ó���� ���� üũ�ϼ���.");
					  return;
				  } else {
						if (form.into_state.value != "S5") {
							alert("�ϰ���ó�� �Ǹ� �������ó���� �����մϴ�.");
							form.allotChk.checked = false;
							return;
						}
            cnt = 1;
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
							if (form.into_state[i].value != "S5") {
							alert("�ϰ���ó�� �Ǹ� �������ó���� �����մϴ�.");
							form.allotChk[i].checked = false;
							return;
						}
						  cnt++;							
					  }
				  }

				  if (cnt == 0)	{
					  alert("�������ó���� ���� üũ�ϼ���.");
					  return;
				  }
				}
        
				if (confirm("���� ��(��)�� �������ó���ϰڽ��ϱ�?")) {
          form.trans_gubun.value = "193";
					form.action = "IR051110.etax";
				  form.cmd.value = "srpDelete";
          form.target = "_self";
				  eSubmit(form);
				}
			}


			function goPermission2(){
				var form = document.sForm;
				var listSize = <%=srpPmsListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("å���ڽ����� ���� üũ�ϼ���.");
					  return;
				  } else {
						if (form.into_state.value != "S3") {
							alert("����ó���Ǹ� å���ڽ����� �����մϴ�.");
							form.allotChk.checked = false;
							return;
						}
            cnt = 1;
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
							if (form.into_state[i].value != "S3") {
							  alert("����ó���Ǹ� å���ڽ����� �����մϴ�.");
							  form.allotChk[i].checked = false;
							  return;
						  }
						  cnt++;							
					  }
				  }

				  if (cnt == 0)	{
					  alert("å���ڽ����� ���� üũ�ϼ���.");
					  return;
				  }

          if (cnt > 1) {
            alert("å���� ������ �Ѱ� ������ ó�������մϴ�.");
            return;
          }
				}
        
				if (confirm("���� ��(��)�� å���ڽ����ϰڽ��ϱ�?")) {
          form.trans_gubun.value = "195";
					form.action = "IR051111.etax";
				  form.cmd.value = "bankSrpTrs";
          form.target = "hidFrame";
				  eSubmit(form);
				}
			}

      function goPermission(){
				var form = document.sForm;
				var listSize = <%=srpPmsListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("å���ڽ����� ���� üũ�ϼ���.");
					  return;
				  } else {
						if (form.into_state.value != "S3") {
							alert("����ó���Ǹ� å���ڽ����� �����մϴ�.");
							form.allotChk.checked = false;
							return;
						}
            cnt = 1;
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
							if (form.into_state[i].value != "S3") {
							  alert("����ó���Ǹ� å���ڽ����� �����մϴ�.");
							  form.allotChk[i].checked = false;
							  return;
						  }
						  cnt++;							
					  }
				  }

				  if (cnt == 0)	{
					  alert("å���ڽ����� ���� üũ�ϼ���.");
					  return;
				  }

          if (cnt > 1) {
            alert("å���� ������ �Ѱ� ������ ó�������մϴ�.");
            return;
          }
				}
        
        form.trans_gubun.value = "195";
				form.action = "IR051110.etax";
				form.cmd.value = "bankSrpPms";
        form.target = "_self";
				eSubmit(form);
			}


      function goCancel() {
				var form = document.sForm;
				form.action = "IR051110.etax";
				form.cmd.value = "bankSrpRtn";
				form.target = "_self";
				eSubmit(form);
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
  <form name="sForm" method="post" action="IR051110.etax">
	<input type="hidden" name="cmd" value="bankSrpPmsList">
  <input type="hidden" name="work_log" value="A05">
  <input type="hidden" name="trans_gubun" value="">
  <input type="hidden" name="err_code" value="">
	<input type="hidden" name="pms_cnt" value="">
	<input type="hidden" name="send_no" value="">
  <input type="hidden" name="v_flag" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_28.gif"></td>
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
                    <td width="860">
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �������
										  <input type="text" class="box3" size="8" name="reg_date" value="<%=reg_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','reg_date')" align="absmiddle">
										</td>
                    <td width="100"> 
                      <img src="../img/btn_search.gif" alt="��ȸ" style="cursor:hand" onClick="goSearch()" align="absmiddle">
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
              <th class="fstleft" width="5%">����<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="13%">�̿�ȸ�迬��</th>
              <th width="17%">��ݰ���</th>
							<th width="13%">����ȸ�迬��</th>
							<th width="17%">�Աݰ���</th>
							<th width="15%">���Աݾ�</th>
							<th width="10%">ó������</th>
							<th width="10%">�̿����</th>
            </tr>
						<% 
               long comp_amt = 0L;
               long nocomp_amt = 0L; 
               if (srpPmsListSize > 0 && srpPmsList != null) { 
							   for (int i=0; i < srpPmsListSize; i++) {
									 CommonEntity rowData = (CommonEntity)srpPmsList.get(i);
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_YEAROVER")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOOVER"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("M310_YEARINTO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOINTO"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M310_INTOTAMT"))%></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_INTOSTATE_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_INTOCODE_NAME")%></td>
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M310_SEQ")%>">
              <input type="hidden" name="m010seq_list" value="<%=rowData.getString("M310_M010SEQ")%>">
              <input type="hidden" name="m240seq_list" value="<%=rowData.getString("M310_M240SEQ")%>">
							<input type="hidden" name="into_state" value="<%=rowData.getString("M310_INTOSTATE")%>">
              <input type="hidden" name="inamt" value="<%=rowData.getString("M310_INTOTAMT")%>">
              <input type="hidden" name="out_acctno" value="<%=rowData.getString("M310_ACCOUNTNOOVER")%>">
            </tr>
						<%    if ("S5".equals(rowData.getString("M310_INTOSTATE")) ) { 
                    comp_amt += rowData.getLong("M310_INTOTAMT");
                  } else if (!"S5".equals(rowData.getString("M310_INTOSTATE")) ) {
                    nocomp_amt += rowData.getLong("M310_INTOTAMT");
                  }   
                } %>
            <tr style="font-weight:bold">
              <td colspan="2" class="fstleft">�̿Ϸ�</td>
              <td colspan="2" class="right"><%=TextHandler.formatNumber(nocomp_amt)%></td>
              <td colspan="2" class="center">���ԿϷ�</td>
              <td colspan="2" class="right"><%=TextHandler.formatNumber(comp_amt)%></td>
            </tr>
		        <%  } else { %>
						<tr> 
              <td class="fstleft" colspan="8">&nbsp;</td>
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
							<img src="../img/btn_ilorder.gif" alt="�ϰ���" style="cursor:hand" onClick="goProcess()" align="absmiddle">&nbsp;
              <img src="../img/btn_iip_cancel.gif" alt="�������" style="cursor:hand" onClick="goDelete()" align="absmiddle">&nbsp;
              <!--
							<img src="../img/btn_adminok.gif" alt="å���ڽ���" style="cursor:hand" onClick="PowerChk('P')" align="absmiddle">
              -->
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <iframe name="hidFrame" width="0" height="0"></iframe>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
		<% if (!"".equals(SucMsg)) { %>
		<script>
		  alert("<%=SucMsg%>");
		</script>
		<% } %>
  </body>
</html>