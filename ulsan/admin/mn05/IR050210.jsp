<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050210.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �ڱݹ������γ�����ȸ/����ó��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> allotList  = (List<CommonEntity>)request.getAttribute("page.mn05.allotList");
	int allotListSize = 0;
	if (allotList != null ) {
		allotListSize = allotList.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
  String allot_date = request.getParameter("allot_date");
	if ("".equals(allot_date) ) {
    allot_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
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
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR050210.etax";
				form.cmd.value = "bankAllotList";
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

			function goExcute(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("����ó���� ���� üũ�ϼ���.");
					  return;
				  } else {
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S3")	{
							alert("���������� �Ǹ� ����ó���� �����մϴ�.");
							return;
						}
            if (parseInt(form.allot_amt.value.replace(/,/g, ""), 10) <0 && form.edit_yn.value != "Y") {
              alert("�ݳ� ���� �䱸���� ���·� ��ϵǾ�� �մϴ�. ������ ����� ����ҿ��� �䱸�������� �����Ͽ��� �մϴ�.");
              return;
            }
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S3")	{
							  alert("���������� �Ǹ� ����ó���� �����մϴ�.");
							  return;
						  }
              if (parseInt(form.allot_amt[i].value.replace(/,/g, ""), 10) <0 && form.edit_yn[i].value != "Y") {
                alert("�ݳ� ���� �䱸���� ���·� ��ϵǾ�� �մϴ�. ������ ����� ����ҿ��� �䱸�������� �����Ͽ��� �մϴ�.");
                return;
              }
					  }
				  }

				  if (cnt == 0)	{
					  alert("����ó���� ���� üũ�ϼ���.");
					  return;
				  }
				}
        if (confirm("���� ��(��)�� ����ó���ϰڽ��ϱ�?")) {
					form.trans_gubun.value = "123";
				  form.action = "IR050210.etax";
				  form.cmd.value = "bankAllotReg";
				  form.target = "_self";
				  eSubmit(form);
        }
			}


      function goDelete(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("��������� ���� üũ�ϼ���.");
					  return;
				  } else {
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S4")	{
							alert("����ó�� �Ǹ� ������Ұ� �����մϴ�.");
							return;
						}
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S4")	{
							  alert("����ó�� �Ǹ� ������Ұ� �����մϴ�.");
							  return;
						  }
					  }
				  }

				  if (cnt == 0)	{
					  alert("��������� ���� üũ�ϼ���.");
					  return;
				  }
				}
        if (confirm("���� ��(��)�� �������ó���ϰڽ��ϱ�?")) {
					form.trans_gubun.value = "123";
				  form.action = "IR050210.etax";
				  form.cmd.value = "baeDelete";
				  form.target = "_self";
				  eSubmit(form);
        }
			}



			function goPermission(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
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
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S4")	{
							alert("����ó�� �Ǹ� å���ڽ����� �����մϴ�.");
							return;
						}
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S4")	{
							  alert("����ó�� �Ǹ� å���ڽ����� �����մϴ�.");
							  return;
						  }
					  }
				  }

				  if (cnt == 0)	{
					  alert("å���ڽ����� ���� üũ�ϼ���.");
					  return;
				  }
				}
        
			  form.trans_gubun.value = "125";
				form.action = "IR050210.etax";
				form.cmd.value = "bankAllotPms";
				form.target = "_self";
				eSubmit(form);
			}

      
			function goPermission2(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
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
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S4")	{
							alert("����ó�� �Ǹ� å���ڽ����� �����մϴ�.");
							return;
						}
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S4")	{
							  alert("����ó�� �Ǹ� å���ڽ����� �����մϴ�.");
							  return;
						  }
					  }
				  }

				  if (cnt == 0)	{
					  alert("å���ڽ����� ���� üũ�ϼ���.");
					  return;
				  }
				}
        
				if (confirm("���� ��(��)�� å���ڽ���ó���ϰڽ��ϱ�?")) {
				  form.trans_gubun.value = "125";
				  form.action = "IR050211.etax";
				  form.cmd.value = "bankAllotTrs";
				  form.target = "hidFrame";
			  	eSubmit(form);		
				}
			}

      function goCancel() {
				var form = document.sForm;
				form.action = "IR050210.etax";
				form.cmd.value = "bankAllotRtn";
        form.allot_type.value = "1";
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
  <form name="sForm" method="post" action="IR050210.etax">
	<input type="hidden" name="cmd" value="bankAllotList">
	<input type="hidden" name="work_log" value="A05">
	<input type="hidden" name="trans_gubun" value="">
	<input type="hidden" name="err_code" value="">
	<input type="hidden" name="pms_cnt" value="">
	<input type="hidden" name="send_no" value="">
	<input type="hidden" name="err_code" value="">
  <input type="hidden" name="v_flag" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_2.gif"></td>
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
									    ȸ�迬��
										  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ��������
										  <input type="text" class="box3" size="8" name="allot_date" value="<%=allot_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','allot_date')" align="absmiddle">
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
        <td width="975" height="20" align="right"> ����(õ��)</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" rowspan="2" width="4%">����<input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th rowspan="2" width="10%">���</th>
              <th rowspan="2" width="10%">��������</th>
							<th rowspan="2" width="10%">���������</th>
							<th colspan="3" width="30%">�ڱ�</th>
							<th rowspan="2" width="10%">�ڱݼҿ��</th>
							<th rowspan="2" width="10%">�ڱݹ���<br>�䱸��</th>
							<th rowspan="2" width="8%">ó������</th>
							<th rowspan="2" width="8%">�������</th>
            </tr>
						<tr> 
              <th width="10%">�������</th>
              <th width="10%">�����(����)</th>
							<th width="10%">�ܾ�</th>
            </tr>
						<% long comp_amt = 0L;
               long nocomp_amt = 0L;
               if (allotListSize > 0 && allotList != null) { 
							   for (int i=0; i < allotListSize; i++) {
									 CommonEntity rowData = (CommonEntity)allotList.get(i);
						%>
            <tr>
						  <% if ("S3".equals(rowData.getString("M100_ALLOTSTATE")) || "S4".equals(rowData.getString("M100_ALLOTSTATE")) ) { %>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<% } else { %>
							<td class="fstleft"><input type="hidden" name="allotChk" value="<%=i%>">&nbsp;</td>
							<% } %>
							<td class="center">&nbsp;<%=rowData.getString("M350_PARTNAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_BUDGETAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_BUDGETALLOTAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ORIALLOTAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_TOTALJICHULAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_JANAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_SOYOAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("M100_ALLOTSTATE_NM")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M100_RST_NM")%></td>
							<input type="hidden" name="allot_state" value="<%=rowData.getString("M100_ALLOTSTATE")%>">
							<input type="hidden" name="year_list" value="<%=rowData.getString("M100_YEAR")%>">
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M100_SEQ")%>">
							<input type="hidden" name="allot_amt" value="<%=rowData.getString("YOGU_AMT")%>">
							<input type="hidden" name="allot_yn" value="">
              <input type="hidden" name="edit_yn" value="<%=rowData.getString("M100_EDIT_YN")%>">
            </tr>
						<%  if ("S6".equals(rowData.getString("M100_ALLOTSTATE")) ) { 
                  comp_amt += rowData.getLong("M100_ALLOTAMT");
                } else if (!"S6".equals(rowData.getString("M100_ALLOTSTATE")) ) {
                  nocomp_amt += rowData.getLong("M100_ALLOTAMT");
                }   
              } %>

            <tr style="font-weight:bold">
              <td colspan="3" class="fstleft">�̿Ϸ�</td>
              <td colspan="2" class="right"><%=TextHandler.formatNumber(nocomp_amt)%></td>
              <td colspan="3" class="center">�����Ϸ�</td>
              <td colspan="3" class="right"><%=TextHandler.formatNumber(comp_amt)%></td>
            </tr>
		        <% } else { %>
						<tr> 
              <td class="fstleft" colspan="11">&nbsp;</td>
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
							<img src="../img/btn_baedeal.gif" alt="����ó��" style="cursor:hand" onClick="goExcute()" align="absmiddle">&nbsp;
              <img src="../img/btn_bae_cancel.gif" alt="�������" style="cursor:hand" onClick="goDelete()" align="absmiddle">&nbsp;
							<img src="../img/btn_adminok.gif" alt="å���ڽ���" style="cursor:hand" onClick="PowerChk('P')" align="absmiddle">
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