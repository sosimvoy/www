<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR071510.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2014-12-19
* ���α׷�����	   : �ϰ�/���� > ��û������ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
List<CommonEntity> sechulList = (List<CommonEntity>)request.getAttribute("page.mn07.sechulList");
String acc_date_s = request.getParameter("acc_date_s");
if(acc_date_s.equals("") || acc_date_s == null){
    acc_date_s = TextHandler.formatDate(StringUtil.getDate().substring(0,8),"yyyyMMdd","yyyy-MM-dd");
}

String acc_date_e = request.getParameter("acc_date_e");
if(acc_date_e.equals("") || acc_date_e == null){
    acc_date_e = TextHandler.formatDate(StringUtil.getDate().substring(0,8),"yyyyMMdd","yyyy-MM-dd");
}

String acc_year = request.getParameter("acc_year");
if ("".equals(acc_year) || acc_year == null) {
    acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy");
}

String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
int this_int = Integer.parseInt(this_year);

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
        form.action = "IR071510.etax";
        form.cmd.value = "sechulList";
        form.target = "_self";
        eSubmit(form);
    }

    </script>
    </head>
    <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
    <form name="sForm" method="post" action="IR071510.etax">
    <input type="hidden" name="cmd" value="sechulList">
        <table width="993" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td width="18">&nbsp;</td>
                <td width="975" height="40"><img src="../img/title7_15.gif"></td>
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
                                        <td width="860"><span style="width:50px"></span>
	                                        <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
	                                        ȸ�迬��&nbsp;
	                                        <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
	                                        <% 
	                                        for ( int i= this_int; i >=2014; i--) { %>
	                                            <option value="<%=i%>"><%=i%></option>
	                                        <% } %>
	                                        </select>
	                                        <span style="width:10px"></span>
	                                        <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
	                                        ȸ������&nbsp;
	                                        <input type="text" class="box3" name="acc_date_s" value="<%=acc_date_s%>" size="8" userType="date" desc="��ȸ����">
	                                        <img src="../img/btn_calendar.gif" onclick="Calendar(this,'sForm','acc_date_s');" style="cursor:hand">
	                                        - <input type="text" class="box3" name="acc_date_e" value="<%=acc_date_e%>" size="8" userType="date" desc="��ȸ����">
	                                        <img src="../img/btn_calendar.gif" onclick="Calendar(this,'sForm','acc_date_e');" style="cursor:hand">
                                        </td>
                                        <td>
                                            <img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" align="absmiddle">  
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
                    <table style="width:1100px" border="0" cellspacing="0" cellpadding="0"  class="list">	
                        <tr>
                            <th class="fstleft">����</th>
                            <th>����</th>
                            <th>����������(A)</th>
                            <th>�Ź�������(B)</th>
                            <th>�Ź���</th>
                            <th>���⴩��(C)</th>
                            <th>����</th>
                            <th>�ݳ�����(D)</th>
                            <th>�ݳ�</th>
                            <th>����ݾ�(A+B+C-D)</th>
                            <th>���������Ѿ�</th>
                        </tr>
                        <%
                        long sanchul_amt = 0L;
                        long sechul_total = 0L;
                        for (int i=0; sechulList != null && i < sechulList.size(); i++) {
                            CommonEntity data = (CommonEntity)sechulList.get(i);
                            sanchul_amt = data.getLong("M220_AMTBAJEONGJEONILTOT") 
                                    + data.getLong("NEW_BAEJEONG")
                                    +data.getLong("M220_AMTSECHULJEONILTOT")
                                    -data.getLong("BON_BANNAP");
                            sechul_total = data.getLong("SECHUL_TOTAL");
                        %>
                        <tr> 
                            <td class="fstleft"><%=data.getString("M220_YEAR")%></td>
                            <td><%=TextHandler.formatDate(data.getString("M220_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getString("M220_AMTBAJEONGJEONILTOT")) %></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getString("NEW_BAEJEONG")) %></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getString("BAE_DANG")) %></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getString("M220_AMTSECHULJEONILTOT")) %></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getString("M220_AMTSECHUL")) %></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getString("BON_BANNAP")) %></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getString("BAN_DANG")) %></td>
                            <td class="right"><%=TextHandler.formatNumber(data.getLong("M220_AMTBAJEONGJEONILTOT") 
                                    + data.getLong("NEW_BAEJEONG")
                                    +data.getLong("M220_AMTSECHULJEONILTOT")
                                    -data.getLong("BON_BANNAP")) %></td>
                            <td class="right" <% if (sanchul_amt != sechul_total) { %> style="color:red" <%} %>><%=TextHandler.formatNumber(data.getString("SECHUL_TOTAL")) %></td>
                        </tr>
                        <% } 
                        if (sechulList == null || sechulList.size() == 0) {  %>
                        <tr>
                            <td class="fstleft" colspan="11">��ȸ������ �����ϴ�.</td>
                        </tr>
                        <% } %>
                    </table>
		    </td>
		  </tr>
    </table>
	  <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
  </body>
</html>