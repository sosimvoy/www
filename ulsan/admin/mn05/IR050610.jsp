<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050610.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > ���º��ŷ�������ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> bankTransList  = (List<CommonEntity>)request.getAttribute("page.mn05.bankTransList");

	//List<CommonEntity> acctList  = (List<CommonEntity>)request.getAttribute("page.mn05.acctList");

	CommonEntity rstData = (CommonEntity)request.getAttribute("page.mn05.rstData");

	String errMsg  = (String)request.getAttribute("page.mn05.ErrMsg");
  if (errMsg == null) {
		errMsg = "";
	}


	int bankTransListSize = 0;
	if (bankTransList != null ) {
		bankTransListSize = bankTransList.size();
	}

	String start_date = request.getParameter("start_date");
	if ("".equals(start_date) ) {
    start_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String end_date = request.getParameter("end_date");
	if ("".equals(end_date) ) {
    end_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
				if (form.acct_no.value == "") {
					alert("���¹�ȣ�� �Է��ϼ���.");
					form.acct_no.focus();
					return;
				}

        form.acct_no.value = replaceStr(form.acct_no.value, "-", "");
				form.flag.value = "Y";
				form.action = "IR050610.etax";
				form.cmd.value = "bankTransList";
      	eSubmit(form);
      }
      <% if (rstData != null) { %>
				function goContinue() {
				  var form = document.sForm;
				  form.flag.value = "Y";
				  form.trans_no.value = "<%=rstData.getString("detail04")%>";
					form.deal_date.value = "<%=rstData.getString("detail05")%>";
				  form.action = "IR050610.etax";
				  form.cmd.value = "bankTransList";
      	  eSubmit(form);
        }
      <%} %>

			function changeAcctNo(fis_year) {
				var form = document.sForm;
				form.flag.value = "N";
				document.hidFrame.location = "IR050611.etax?cmd=bankTransList&fis_year="+fis_year+"&flag=N";
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050610.etax">
	<input type="hidden" name="cmd" value="bankTransList">
	<input type="hidden" name="flag" value="N">
	<input type="hidden" name="trans_no" value="00000">
	<input type="hidden" name="deal_date" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_23.gif"></td>
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
									    ���¹�ȣ
										  <input type="text" name="acct_no" value="<%=request.getParameter("acct_no")%>" class="box3" size="15" userType="number" required desc="���¹�ȣ">
											<span style="width:40"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ��ȸ����
											<input type="text" class="box3" size="8" name="start_date" value="<%=start_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','start_date')" align="absmiddle">-
											<input type="text" class="box3" size="8" name="end_date" value="<%=end_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','end_date')" align="absmiddle">
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
              <th class="fstleft" width="15%">�ŷ�����</th>
              <th width="15%">�ŷ�����</th>
							<th width="30%">����</th>
							<th width="20%">�ŷ��ݾ�</th>
							<th width="20%">�ŷ����ܾ�</th>
            </tr>
						<% if (bankTransListSize > 0 && bankTransList != null) {
								 String trans_gubun = "";
							   for (int i=0; i < bankTransListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankTransList.get(i);
									 if ("11".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "�Ա�����";
                                     } else if ("12".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "�Ա����";
                                     } else if ("13".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "�Ա�����";
                                     } else if ("16".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "�Ա���ҿ��ŷ�";
                                     } else if ("17".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "�Ա��������ŷ�";
                                     } else if ("21".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "��������";
                                     } else if ("22".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "�������";
                                     } else if ("23".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "��������";
                                     } else if ("26".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "������ҿ��ŷ�";
                                     } else if ("27".equals(rowData.getString("detail08")) ) {
                                         trans_gubun = "�����������ŷ�";
                                     }
						%>
            <tr>
              <td class="fstleft">&nbsp;<%=TextHandler.formatDate(rowData.getString("detail07"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=trans_gubun%></td>
              <td class="center">&nbsp;<%=rowData.getString("detail11")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("detail09"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("detail10"))%></td>
            </tr>
						<%   }
						%>
		        <% }else { %>
						<tr> 
              <td class="fstleft" colspan="5">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
			<% if (rstData != null && !"     ".equals(rstData.getString("detail04")) && !"00000".equals(rstData.getString("detail04")) 
							&& "0000".equals(rstData.getString("common10")) ) { %>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_continue.gif" alt="���" style="cursor:hand" onClick="goContinue()" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
			<% } %>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	<% if (!"".equals(errMsg)) { %>
	<script>
		alert("<%=errMsg%>");
	</script>
	<% } else {%>
	<% if (rstData != null && (!"00000".equals(rstData.getString("detail04"))&& !"     ".equals(rstData.getString("detail04")) 
		&& "0000".equals(rstData.getString("common10"))) ) { %>
	<script>
		alert("�߰� �ŷ������� �� �����մϴ�. �߰� ��ȸ�� ���Ͻø� ��� ��ư�� �����ֽʽÿ�.");
	</script>
	<% } else if (rstData != null && !"0000".equals(rstData.getString("common10")) ) { %>
	<script>
		alert("���� ������ �Խ��ϴ�. " + "<%=rstData.getString("common11")%>" );
	</script>
	<%}
	}%>
  </body>
</html>