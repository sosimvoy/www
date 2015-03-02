<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR062110.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > ������������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%

  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
  String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
  List<CommonEntity> segyeList = (List<CommonEntity>)request.getAttribute("page.mn06.segyeList");
  List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn06.accList");

  int listSize = 0;
  if (segyeList != null) {
    listSize = segyeList.size();
  }

  if (SucMsg == null) {
    SucMsg = "";
  }

	String acc_date = request.getParameter("acc_date");
  String s_date = request.getParameter("s_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

  if ("".equals(s_date) ) {
    s_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
        form.flag.value = "S";
				form.action = "IR062110.etax";
				form.cmd.value = "segyeList";
				eSubmit(form);
			}

      function goInsert() {
				var form = document.sForm;
        var temp_amt = 0;
        form.flag.value = "N";
        temp_amt = parseInt(form.amt.value.replace(/,/g, ""), 10);
        if (temp_amt < 0) {
          alert("���̳ʽ� �ݾ��� �Է��� �� �����ϴ�.");
          form.amt.value ="";
          form.amt.focus();
          return;
        }
				form.action = "IR062110.etax";
				form.cmd.value = "segyeInsert";
				eSubmit(form);
			}

      function goDelete() {
				var form = document.sForm;
        form.flag.value = "N";
        var cnt = 0;
        var listSize = 0;
				listSize = <%=listSize%>;

        if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

        if (listSize == 1) {
					if (!form.chk_val.checked) {
						alert("������ ���� �����ϼ���.");
						return;
					}
				} else {
					for (var i=0; i<form.chk_val.length; i++) {						
						if (form.chk_val[i].checked)	{
							cnt++;
						}
					}

					if (cnt ==0) {
						alert("������ ���� �����ϼ���.");
						return;
					}
				}
        
        if (confirm("���� ��(��)�� �����ϰڽ��ϱ�?")) {
          form.action = "IR062110.etax";
				  form.cmd.value = "segyeDelete";
				  eSubmit(form);
        }
			}

      function changeAccType(acc_type) {
				var form = document.sForm;
        form.flag.value = "N";
				form.action = "IR062110.etax";
				form.cmd.value = "segyeList";
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
  <form name="sForm" method="post" action="IR062110.etax">
	<input type="hidden" name="cmd" value="segyeList">
  <input type="hidden" name="flag" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_33.gif"></td>
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
                    <td>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�迬��
										  <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
											<% for (int i=0; i<3; i++) { %>
											  <option value="<%=Integer.parseInt(this_year)-i%>"><%=Integer.parseInt(this_year)-i%></option>
											<% } %>
											</select>
                      <span style="width:75"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ������
										  <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
                      <span style="width:108"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ���뱸��
										  <select name="jeon_type" iValue="<%=request.getParameter("jeon_type")%>">
											  <option value="1">�뿩</option>
                        <option value="2">�뿩ȸ��</option>
                        <option value="3">����</option>
                        <option value="4">���Ի�ȯ</option>
											</select>
                      <br><br>
                      <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�豸��
										  <select name="acc_type" iValue="<%=request.getParameter("acc_type")%>" onchange="changeAccType(this.value)">
											  <option value="A">�Ϲ�ȸ��</option>
                        <option value="B">Ư��ȸ��</option>
                        <option value="E">���</option>
											</select>
                      <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ���
										  <select name="acc_code">
                        <% for (int i=0; i<accList.size(); i++) { 
                          CommonEntity rowData = (CommonEntity)accList.get(i); %>
											  <option value="<%=rowData.getString("M360_ACCCODE")%>"><%=rowData.getString("M360_ACCNAME")%></option>
                        <% } %>
											</select>
                      <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ����ݾ�
										  <input type="text" name="amt" iValue="<%=request.getParameter("amt")%>" userType="money" size="15">
                      <span style="width:20"></span>
											<img src="../img/btn_order.gif" alt="���" style="cursor:hand" onClick="goInsert()" align="absmiddle">
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
                    <td>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ�迬��
										  <select name="s_year" iValue="<%=request.getParameter("s_year")%>">
											<% for (int i=0; i<3; i++) { %>
											  <option value="<%=Integer.parseInt(this_year)-i%>"><%=Integer.parseInt(this_year)-i%></option>
											<% } %>
											</select>
                      <span style="width:75"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ȸ������
										  <input type="text" class="box3" size="8" name="s_date" value="<%=s_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','s_date')" align="absmiddle">
                      <span style="width:320"></span>
											<img src="../img/btn_search.gif" alt="��ȸ" style="cursor:hand" onClick="goSearch()" align="absmiddle">
                      <img src="../img/btn_delete.gif" alt="����" style="cursor:hand" onClick="goDelete()" align="absmiddle">
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
              <th class="fstleft" width="6%">����<input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.chk_val, this.checked);"></th>
              <th width="10%">ȸ�迬��</th>
							<th width="13%">ȸ������</th>
							<th width="10%">���뱸��</th>
							<th width="10%">ȸ�豸��</th>
							<th width="36%">ȸ���</th>
              <th width="15%">����ݾ�</th>
            </tr>
						<% 
							   for (int i=0; i < listSize; i++) {
									 CommonEntity data = (CommonEntity)segyeList.get(i);
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="chk_val" value="<%=data.getString("M580_NO")%>"></td>
              <td class="center">&nbsp;<%=data.getString("M580_YEAR")%></td>
							<td class="center">&nbsp;<%=data.getString("M580_DATE")%></td>
              <td class="center">&nbsp;<%=data.getString("M580_JEONTYPE")%></td>
              <td class="center">&nbsp;<%=data.getString("M580_ACCTYPE")%></td>
							<td class="center">&nbsp;<%=data.getString("M360_ACCNAME")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M580_AMT"))%></td>
            </tr> 
		        <% } %>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </body>
  <% if (!"".equals(SucMsg)) { %>
  <script>
     alert("<%=SucMsg%>");
  </script>
  <% } %>
</html>
