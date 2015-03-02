<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050110.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �ڱݹ�����ó���ܾ���ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  CommonEntity rowData  = (CommonEntity)request.getAttribute("page.mn05.acctData");

	String errMsg  = (String)request.getAttribute("page.mn05.ErrMsg");
  if (errMsg == null) {
		errMsg = "";
	}
	int rowDataSize = 0;
	if (rowData != null ) {
		rowDataSize = rowData.size();
	}

	List<CommonEntity> resultList  = (List<CommonEntity>)request.getAttribute("page.mn05.resultList");

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
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR050110.etax";
				form.cmd.value = "bankAcctList";
      	eSubmit(document.sForm);
      }

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050110.etax">
	<input type="hidden" name="cmd" value="bankAcctList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_21.gif"></td>
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
										  ȸ�迬��
										  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
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
						  <th class="fstleft">ȸ�迬��</th>
              <th>������û�Ѿ�(��ó��)</th>
              <th>�������û�Ѿ�(��ó��)</th>
              <th>�ڱݼҿ���հ�</th>
            </tr>
						<% if (rowDataSize > 0 && rowData != null) { %>
            <tr> 
						  <td class="fstleft">&nbsp;<%=request.getParameter("fis_year")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT_N"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getLong("M100_ALLOTAMT") + rowData.getLong("M100_ALLOTAMT_N"))%></td>
            </tr>
						<% } else { %>
						<tr> 
              <td class="fstleft" colspan="4">&nbsp;</td>
            </tr>
						<% } %>
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
              <th class="fstleft">ȸ�迬��</th>
              <th>�μ�</th>
              <th>�ܾ�</th>
            </tr>
						<% for (int i=0; resultList != null && i < resultList.size(); i++) { 
							   CommonEntity rsData = (CommonEntity)resultList.get(i); 
						%>
            <tr> 
              <td class="fstleft">&nbsp;<%=rsData.getString("FIS_YEAR")%></td>
              <td class="center">&nbsp;<%=rsData.getString("ACCT_NICK")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rsData.getString("JAN_AMT"))%></td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
	<% if (!"".equals(errMsg)) { %>
	<script>
		alert("<%=errMsg%>");
	</script>
	<% } %>
  </body>
</html>