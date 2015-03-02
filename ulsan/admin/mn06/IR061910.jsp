<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061910.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱݿ��ȸ�迬���̿�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String last_year = "";
	String after_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	int after_int = last_int + 2;
	last_year = String.valueOf(last_int);
	after_year = String.valueOf(after_int);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      var this_year = "<%=this_year%>";
      var last_year = "<%=last_year%>";
      var after_year = "<%=after_year%>";
      function init() {
  	    typeInitialize();
      }

			function goCarry() {
				var form = document.sForm;
        if (confirm("����ȸ�迬�� "+form.this_year_list.value+"������ �̿�ó�� �۾��� �����ϰڽ��ϱ�??")) {
          form.action = "IR061910.etax";
				  form.cmd.value = "carryDeal";
				  eSubmit(form);
        }
			}


      function goCancel() {
				var form = document.sForm;
        if (confirm("����ȸ�迬�� "+form.this_year_list.value+"���� �̿���� �۾��� �����ϰڽ��ϱ�??")) {
				  form.action = "IR061910.etax";
				  form.cmd.value = "carryCancel";
				  eSubmit(form);
        }
			}


      function ChYear(year) {
        var form = document.sForm;
        if (year == last_year) {
          form.this_year_list.value = this_year;
        } else {
          form.this_year_list.value = after_year;
        }
      }

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR061910.etax">
	<input type="hidden" name="cmd" value="carryDeal">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_31.gif"></td>
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
									  <td width="300"> &nbsp; </td>
                    <td width="300">
											&nbsp;
										</td>
                    <td width="360">
										&nbsp;
                    </td>
                  </tr>
                  <tr>
									  <td> &nbsp; </td>
                    <td>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�豸��<span style="width:24"></span>
										  <select name="acc_type" iValue="<%=request.getParameter("acc_type")%>">
											  <option value="A">�Ϲ�ȸ��</option>
												<option value="B">Ư��ȸ��</option>
												<option value="D">���Լ��������</option>
												<option value="E">���</option>
											</select>
										</td>
                    <td>
                      &nbsp;
                    </td>
                  </tr>
									<tr> 
									  <td> &nbsp; </td>
                    <td>
											&nbsp;
										</td>
                    <td>
										&nbsp;
                    </td>
                  </tr>
									<tr> 
									  <td> &nbsp; </td>
                    <td>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    �̿�ȸ�迬��
										  <select name="last_year_list" iValue="<%=request.getParameter("last_year_list")%>" onchange="ChYear(this.value)">
                        <option value="<%=this_year%>"><%=this_year%></option>
											  <option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
                    <td>
										&nbsp;
                    </td>
                  </tr>
									<tr> 
									  <td> &nbsp; </td>
                    <td>
											&nbsp;
										</td>
                    <td>
										&nbsp;
                    </td>
                  </tr>
									<tr>
									  <td> &nbsp; </td>
                    <td>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ����ȸ�迬��
										  <select name="this_year_list" iValue="<%=request.getParameter("this_year_list")%>">
                        <option value="<%=after_year%>"><%=after_year%></option>
											  <option value="<%=this_year%>"><%=this_year%></option>												
											</select>
										</td>
                    <td valign="top">
                      <img src="../img/btn_carrydeal.gif" alt="�̿�ó��" style="cursor:hand" onClick="goCarry()" align="absmiddle">
                      <span style="width:40"></span>
                      <img src="../img/btn_carrycancel.gif" alt="�̿����" style="cursor:hand" onClick="goCancel()" align="absmiddle">
                    </td>
                  </tr>
									<tr> 
									  <td> &nbsp; </td>
                    <td>
											&nbsp;
										</td>
                    <td>
										&nbsp;
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