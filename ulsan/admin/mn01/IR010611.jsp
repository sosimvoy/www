<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR010610.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : ���� > �����ڷ���/���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%

  List<CommonEntity> nongHyupList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupList");  //�����ü������ϰ�ǥ&&������ϻ����

	List<CommonEntity> nongHyupGwaList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupGwaList");  //������ȯ���ϰ�ǥ
  
	String data_type =  (String)request.getAttribute("page.mn01.data_type");
	String SucMsg = (String)request.getAttribute("page.mn01.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int nongHyupListSize = 0;
	if (nongHyupList != null ) {
		nongHyupListSize = nongHyupList.size();
	}
  String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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

			function goTmpUpload(){
        var form = document.sForm;
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("���� ������ ÷���ϼ���.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("���� ���ϸ� ���ε� �����մϴ�.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "Y";
				if (confirm("÷���� �������ϰ� �ڷᱸ�а� ��ġ�մϱ�?")) {
					form.action = "IR010611.etax?cmd=nongHyupTmpUp&uploadDir=excel&flag=Y";
          eSubmit(form);
				}
      } 
			
			
			function goRegister(){
        var form = document.sForm;

				if (form.flag.value != "Y")	{
					alert("�̸����� �� ���������� ����Ͻʽÿ�.");
					return;
				}
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("���� ������ ÷���ϼ���.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("���� ���ϸ� ���ε� �����մϴ�.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "N";
				if (confirm("÷���� �������ϰ� �ڷᱸ�а� ��ġ�մϱ�?")) {
					form.action = "IR010610.etax?cmd=nongHyupReg&uploadDir=excel&flag=N";
          eSubmit(form);
				}        
      }          
      
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
	<form name="dForm" method="post" action="IR010610.etax">
	<input type="hidden" name="cmd" value="nongHyupDel">
	<input type="hidden" name="fis_year">
	<input type="hidden" name="acc_date">
	<input type="hidden" name="data_type">
	<input type="hidden" name="trans_gubun" value="161">
	<input type="hidden" name="work_log" value="A01">
	</form>
  <form name="sForm" method="post" enctype="multipart/form-data"  action="IR010610.etax">
	<input type="hidden" name="cmd" value="nongHyupList">
	<input type="hidden" name="flag" value="<%=request.getParameter("flag")%>">
	<input type="hidden" name="trans_gubun" value="161">
	<input type="hidden" name="work_log" value="A01">
    <table width="950" border="0" cellspacing="0" cellpadding="0">
			<tr>
        <td width="18">&nbsp;</td>
        <td width="930" height="20"> &nbsp;</td>
			</tr>
			<% if ("G1".equals(data_type) ) { %>
			<tr>
        <td colspan="2"><span style="width:20"></span>�����ü������ϰ�ǥ</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="930"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="15%">����</th>
							<th width="15%">�հ�</th>
              <th width="14%">�߱�</th>
              <th width="14%">����</th>
							<th width="14%">����</th>
							<th width="14%">�ϱ�</th>
							<th width="14%">���ֱ�</th>
            </tr>
						<% 
               long left_tot = 0L;
						   if (nongHyupListSize > 0 && nongHyupList != null) { 
							   for (int i=0; i < nongHyupListSize; i++) {
									 CommonEntity rowData = (CommonEntity)nongHyupList.get(i);
									 left_tot = rowData.getLong("M450_JUNGGU") + rowData.getLong("M450_NAMGU") + rowData.getLong("M450_DONGGU")
										 + rowData.getLong("M450_BUKGU") + rowData.getLong("M450_ULJU");
						%>
            <% if ("1999999".equals(rowData.getString("M450_SEMOK_CD")) || "2999999".equals(rowData.getString("M450_SEMOK_CD")) || "9999999".equals(rowData.getString("M450_SEMOK_CD")) )  { %>
						<tr>
              <td class="fstleft">&nbsp;<b><%=rowData.getString("M450_SEMOK_NM")%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(left_tot)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></b></td>
            </tr>
						<% } else { %>
            <tr>
              <td class="fstleft">&nbsp;<%=rowData.getString("M450_SEMOK_NM")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(left_tot)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></td>
            </tr>
            <% } %>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="7">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
			<%} else if ("G2".equals(data_type) || "G3".equals(data_type)) { %>
			<tr>
        <td colspan="2"><span style="width:20"></span>������ȯ���ϰ�ǥ(<% if ("G2".equals(data_type)) { %> ������ <% } else if ("G3".equals(data_type) ) { %> ���� <% } %>)</td>
			</tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="930"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" rowspan="2">����</th>
							<th rowspan="2">�հ�</th>
              <th colspan="6">���⵵</th>
              <th colspan="6">���⵵</th>
						</tr>
						<tr>
						  <th>�Ұ�</th>
						  <th>�߱�</th>
							<th>����</th>
							<th>����</th>
							<th>�ϱ�</th>
							<th>���ֱ�</th>
							<th>�Ұ�</th>
						  <th>�߱�</th>
							<th>����</th>
							<th>����</th>
							<th>�ϱ�</th>
							<th>���ֱ�</th>
            </tr>
						<% 
							 long tot_row = 0L;   // �ο�� �հ�
							 long this_row = 0L;  //���⵵ �ο�� �Ұ�
							 long last_row = 0L;  //���⵵ �ο�� �Ұ�

							 long tot_amt = 0L; //�ѱݾ�
							 long this_amt = 0L; //���⵵ �Ұ�ݾ�
							 long last_amt = 0L; //���⵵ �Ұ�ݾ�
							 long jung_amt = 0L;
							 long nam_amt = 0L;
							 long dong_amt = 0L;
							 long buk_amt = 0L;
							 long ulju_amt = 0L;
							 long jung_gwa_amt = 0L;
							 long nam_gwa_amt = 0L;
							 long dong_gwa_amt = 0L;
							 long buk_gwa_amt = 0L;
							 long ulju_gwa_amt = 0L;
						   if (nongHyupGwaList.size() > 0 && nongHyupGwaList != null) { 
							   for (int i=0; i < nongHyupGwaList.size(); i++) {
									 CommonEntity rowData = (CommonEntity)nongHyupGwaList.get(i);
									 this_row = rowData.getLong("M450_JUNGGU") + rowData.getLong("M450_NAMGU") + rowData.getLong("M450_DONGGU")
										 + rowData.getLong("M450_BUKGU") + rowData.getLong("M450_ULJU");
									 last_row = rowData.getLong("M450_JUNGGU_GWA") + rowData.getLong("M450_NAMGU_GWA") + rowData.getLong("M450_DONGGU_GWA")
										 + rowData.getLong("M450_BUKGU_GWA") + rowData.getLong("M450_ULJU_GWA");
									 tot_row = this_row + last_row;
						%>
						<tr>
              <td class="fstleft">&nbsp;<%=rowData.getString("M450_SEMOK_NM")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(tot_row)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(this_row)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(last_row)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU_GWA"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_NAMGU_GWA"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_DONGGU_GWA"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_BUKGU_GWA"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_ULJU_GWA"))%></td>
            </tr>
						<%     if (!"1130100".equals(rowData.getString("M450_SEMOK_CD")) && !"2290100".equals(rowData.getString("M450_SEMOK_CD")) ) {
							       tot_amt += tot_row;
										 this_amt += this_row;
										 last_amt += last_row;
										 jung_amt += rowData.getLong("M450_JUNGGU");
										 nam_amt += rowData.getLong("M450_NAMGU");
										 dong_amt += rowData.getLong("M450_DONGGU");
										 buk_amt += rowData.getLong("M450_BUKGU");
										 ulju_amt += rowData.getLong("M450_ULJU");
										 jung_gwa_amt += rowData.getLong("M450_JUNGGU_GWA");
										 nam_gwa_amt += rowData.getLong("M450_NAMGU_GWA");
										 dong_gwa_amt += rowData.getLong("M450_DONGGU_GWA");
										 buk_gwa_amt += rowData.getLong("M450_BUKGU_GWA");
										 ulju_gwa_amt += rowData.getLong("M450_ULJU_GWA");
						       } %>

							     
						<%   }
						%>
             <tr>
              <td class="fstleft">&nbsp;<b>�� ��</b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(tot_amt)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(this_amt)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(jung_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(nam_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(dong_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(buk_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(ulju_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(last_amt)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(jung_gwa_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(nam_gwa_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(dong_gwa_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(buk_gwa_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(ulju_gwa_amt)%></b></td>
            </tr>
		         <%  } else { %>
						<tr> 
              <td class="fstleft" colspan="14">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
			<% } else if ("G4".equals(data_type)) { %>
      <tr>
        <td colspan="2"><span style="width:20"></span>������ϻ���� �����ϰ�ǥ</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="930"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th rowspan="2" colspan="2" class="fstleft" width="15%">����</th>
              <th colspan="2" width="17%">�߱�</th>
              <th colspan="2" width="17%">����</th>
							<th colspan="2" width="17%">����</th>
							<th colspan="2" width="17%">�ϱ�</th>
							<th colspan="2" width="17%">���ֱ�</th>
            </tr>
            <tr> 
              <th>�Ǽ�</th>
              <th>�ݾ�</th>
              <th>�Ǽ�</th>
              <th>�ݾ�</th>
              <th>�Ǽ�</th>
              <th>�ݾ�</th>
              <th>�Ǽ�</th>
              <th>�ݾ�</th>
              <th>�Ǽ�</th>
              <th>�ݾ�</th>
            </tr>
            <% for (int i=0; nongHyupList != null && i<nongHyupList.size(); i++) { 
                 CommonEntity rowData = (CommonEntity)nongHyupList.get(i);
            %>
            <tr>
              <% if (i == 0 || i == 3) { %>
              <td class="fstleft" rowspan="3">
              <% if (nongHyupList.size() == 6 && i == 0) { %>
              ��漼
              <% } else if ((i == 3 && nongHyupList.size() == 6) || (i == 0 && nongHyupList.size() == 3)) { %>
              ��ϼ�
              <% } %>
              </td>
              <% } %>
              <td class="left"><%=rowData.getString("M450_SEMOK_NM")%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_NAMGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_DONGGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_BUKGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_ULJU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></td>
            </tr>
            <% } %>
          </table>
        </td>
      </tr>
      <% } %>
    </table>
	  </form>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>
