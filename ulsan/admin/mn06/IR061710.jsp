<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061710.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ܾ�����ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
	String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
        var Url;
        if (form.janak_type.value == "A") {
          Url =  "IR061711.etax";
          form.action = "IR061711.etax";
        } else if (form.janak_type.value == "B") {
          Url =  "IR061712.etax";
          form.action = "IR061712.etax";
        }        
        form.cmd.value = "restMoneyList";
        window.open(Url, 'janakJang' ,'height=700,width=1060,top=10,left=200,scrollbars=yes');
        form.target = "janakJang";
				eSubmit(form);
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR061710.etax">
	<input type="hidden" name="cmd" value="restMoneyList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_26.gif"></td>
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
									  <td width="100"> &nbsp;</td>
                    <td width="300"> &nbsp;</td>
                    <td width="560"> &nbsp;</td>
                  </tr>
                  <tr>
									  <td> &nbsp; </td>
                    <td>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									    �ܾ��� ����
										  <select name="janak_type" iValue="<%=request.getParameter("janak_type")%>">
											  <option value="A">�Ⱓ��</option>
                        <option value="B">���ϴ��</option>
											</select>
										</td>
                    <td>
                      <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									    ��ȸ����
										  <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle"><span style="width:200"></span>
											<img src="../img/btn_search.gif" alt="��ȸ" style="cursor:hand" onClick="goSearch()" align="absmiddle">
                    </td>
                  </tr>
									<tr>
									  <td> &nbsp; </td>
                    <td> &nbsp;	</td>
                    <td> &nbsp; </td>
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
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </body>
</html>