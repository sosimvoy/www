<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061610.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-08-15
* ���α׷�����	   : �ڱݿ�� > �ܾ����ڷ��Է�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  CommonEntity etcJanInfo  = (CommonEntity)request.getAttribute("page.mn06.etcJanInfo");

	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

  String reg_date = request.getParameter("reg_date");
	if ("".equals(reg_date) ) {
    reg_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String reg_date2 = request.getParameter("reg_date2");
	if ("".equals(reg_date2) ) {
    reg_date2 = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
				form.action = "IR061610.etax";
				form.cmd.value = "etcJanList";
      	eSubmit(form);
      }

			function goRegister() {
				var form = document.sForm;
				if (confirm("�ش������� ���ݿ��ݰ� ���뿹���� ����ϰڽ��ϱ�?")) {
					form.cmd.value = "janakJangReg";
				  eSubmit(form);
				}			  
			}


			function goUpdate() {
				var form = document.sForm;
				if (confirm("�ش������� ���ݿ��ݰ� ���뿹���� �����ϰڽ��ϱ�?")) {
					form.cmd.value = "janakJangUdt";
				  eSubmit(form);
				}			  
			}


			function goUpdate2() {
				var form = document.sForm;

				if (form.city_byul.value == "") {
					alert("���ܿ���(��û)�� �Է��ϼ���.");
					form.city_byul.focus();
					return;
				}

				if (form.ulsan_byul.value == "") {
					alert("���ܿ���(���)�� �Է��ϼ���.");
					form.ulsan_byul.focus();
					return;
				}

				if (form.seip_content[0].value == "") {
					alert("�����׸��� �Է��ϼ���.");
					return;
				}

				if (form.seip_amt[0].value == "") {
					alert("���Աݾ��� �Է��ϼ���.");
					return;
				}

				if (form.sechul_content[0].value == "") {
					alert("�����׸��� �Է��ϼ���.");
					return;
				}

				if (form.sechul_amt[0].value == "") {
					alert("���Աݾ��� �Է��ϼ���.");
					return;
				}

				if (confirm("�ش������� ��Ÿ������ �����ϰڽ��ϱ�?")) {
					form.cmd.value = "etcJanUdt";
				  eSubmit(form);
				}			  
			}

			function goRegister2() {
				var form = document.sForm;
				if (form.city_byul.value == "") {
					alert("���ܿ���(��û)�� �Է��ϼ���.");
					form.city_byul.focus();
					return;
				}

				if (form.ulsan_byul.value == "") {
					alert("���ܿ���(���)�� �Է��ϼ���.");
					form.ulsan_byul.focus();
					return;
				}

				if (form.seip_content[0].value == "") {
					alert("�����׸��� �Է��ϼ���.");
					return;
				}

				if (form.seip_amt[0].value == "") {
					alert("���Աݾ��� �Է��ϼ���.");
					return;
				}

				if (form.sechul_content[0].value == "") {
					alert("�����׸��� �Է��ϼ���.");
					return;
				}

				if (form.sechul_amt[0].value == "") {
					alert("���Աݾ��� �Է��ϼ���.");
					return;
				}

				if (confirm("�ش������� ��Ÿ������ ����ϰڽ��ϱ�?")) {
					form.cmd.value = "etcJanReg";
				  eSubmit(form);
				}			  
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR061610.etax">
	<input type="hidden" name="cmd" value="bankDepositPmsList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_25.gif"></td>
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
                    <td width="790"><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���ݿ���,���뿹�� ���/��������
										  <input type="text" class="box3" size="8" name="reg_date" value="<%=reg_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','reg_date')" align="absmiddle">
										</td>
                    <td width="170"> 
                      <img src="../img/btn_order.gif" alt="���" style="cursor:hand" onClick="goRegister()" align="absmiddle">
											<img src="../img/btn_modify.gif" alt="����" style="cursor:hand" onClick="goUpdate()" align="absmiddle">
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
									    ��Ÿ���� ���/��������
										  <input type="text" class="box3" size="8" name="reg_date2" value="<%=reg_date2%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','reg_date2')" align="absmiddle"><br><br>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���ܿ���(��û)
											<input type="text" class="box3" size="15" name="city_byul" <% if (etcJanInfo != null) { %> value="<%=etcJanInfo.getString("M170_CITYSPECIALDEPOSIT")%>" <% } else { %> value="" <% } %> userType="money">
											<span style="width:150"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���ܿ���(���)
										  <input type="text" class="box3" size="15" name="ulsan_byul" <% if (etcJanInfo != null) { %> value="<%=etcJanInfo.getString("M170_ULSANSPECIALDEPOSIT")%>" <% } else { %> value="" <% } %> userType="money"> 
										</td>
                    <td width="100" valign="top"> 
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
        <td width="975">�ֿ��������� <span style="width:820"></span>����(õ��)</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" colspan="2" width="50%">����</th>
              <th colspan="2" width="50%">����</th>
					  </tr>
						<tr>
              <th>�׸�</th>
							<th>�ݾ�</th>
							<th>�׸�</th>
							<th>�ݾ�</th>
            </tr>
						<% if (etcJanInfo != null) { %>
						<% for (int i=0; i < 9; i++) {
						%>
            <tr>
              <td class="fstleft">&nbsp;<input type="text" name="seip_content" value="<%=etcJanInfo.getString("M170_JEUNGGAMSEIPHANGMOK_"+(i+1)+"")%>" size="25" style="imd-mode:active" class="box3"></td>
							<td class="right">&nbsp;<input type="text" name="seip_amt" value="<%=etcJanInfo.getString("M170_JEUNGGAMSEIPAMT_"+(i+1)+"")%>" size="15" userType="money" class="box3"></td>
              <td class="center">&nbsp;<input type="text" name="sechul_content" value="<%=etcJanInfo.getString("M170_JEUNGGAMSECHULHANGMOK_"+(i+1)+"")%>" size="25" style="imd-mode:active" class="box3"></td>
							<td class="right">&nbsp;<input type="text" name="sechul_amt" value="<%=etcJanInfo.getString("M170_JEUNGGAMSECHULAMT_"+(i+1)+"")%>" size="15" userType="money" class="box3"></td>
            </tr>
						<% }
		        } else {%>
						<% for (int y=0; y < 9; y++) {
						%>
            <tr>
              <td class="fstleft">&nbsp;<input type="text" name="seip_content" value="" size="25" style="imd-mode:active" class="box3"></td>
							<td class="right">&nbsp;<input type="text" name="seip_amt" value="" size="15" userType="money" class="box3"></td>
              <td class="center">&nbsp;<input type="text" name="sechul_content" value="" size="25" style="imd-mode:active" class="box3"></td>
							<td class="right">&nbsp;<input type="text" name="sechul_amt" value="" size="15" userType="money" class="box3"></td>
            </tr>
						<% }
						} %>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_order.gif" alt="���" style="cursor:hand" onClick="goRegister2()" align="absmiddle">
								<img src="../img/btn_modify.gif" alt="����" style="cursor:hand" onClick="goUpdate2()" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>