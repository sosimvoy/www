<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR040510.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-09
* ���α׷�����	   : ���ܼ��� > ������¡������ ��ȸ/����
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn04.jsp" %>

<%
	List<CommonEntity>gwaonapList = (List<CommonEntity>)request.getAttribute("page.mn04.gwaonapList");
	String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String to_day = TextHandler.getCurrentDate();

  int listSize = 0;
	if (gwaonapList != null && gwaonapList.size() > 0) {
		listSize = gwaonapList.size();
	}

	String su_sdate			  = request.getParameter("su_str_date");
	String su_edate			  = request.getParameter("su_end_date");

	if (su_sdate.equals("")) {
		su_sdate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}  

	if (su_edate.equals("")) {
		su_edate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
					alert("<%=SucMsg%>");
				<% } %>
			}
     
      function goSearch() {
				var form = document.sForm;
        var to_day = "<%=to_day%>";

				 if (replaceStr(form.su_str_date.value, "-", "") > to_day) {
          alert("��ȸ���ڰ� ���Ϻ��� Ů�ϴ�.");
          return;
        }

        if (replaceStr(form.su_str_date.value, "-", "") > replaceStr(form.su_end_date.value, "-", "")) {
          alert("��ȸ�������� ��ȸ�����Ϻ��� Ů�ϴ�.");
          return;
        }

				form.action = "IR040510.etax";  
				form.cmd.value = "gwaonapList";
				form.target = "_self";
				eSubmit(form);
			}

		 function goView(seq, gwaonap, documentno, year){
			var form = document.sForm;
			var listSize = <%=listSize%>;
			var cnt = 0;

			if (listSize == 0) {
				alert("��ȸ������ �����ϴ�.");
				return;
			}
			if (listSize == 1) {
				if (!form.userChk.checked) {
					alert("��������� ���� �����ϼ���");
					return;
				}
        cnt =1;
        form.seq.value =  form.seq_list.value;
        
			} else if (listSize > 1) {
				for (var i=0; i<listSize; i++) {
					if (form.userChk[i].checked) {
            form.seq.value = form.seq_list[i].value;
						cnt++;
					}
				}
				if (cnt == 0) {
          if ( seq < 1) {
            alert("������� �ڷḦ �����ϼ���");
	  				return;
          }else {
            form.seq.value =  seq;
		      }
        } 
				if (cnt > 1) {
          alert("������� �� �Ǹ� �����ϼ���");
					return;
        }
        
    	}
			if (cnt == 0) {
				form.procyn.value = "N";
			}else {
        form.procyn.value = "Y";
      }
      if (form.seq.value != seq && seq > 0) {
        alert("üũ�� ������� ����ڷ�� Ŭ���� ���Ǹ��� �Ϸù�ȣ�� �ٸ��ϴ�.");
        return;
      }
       form.cmd.value ="gwaonapView";
			 form.action = "IR040511.etax"; 
			 
       window.open('IR040511.etax','reportPop' ,'height=600,width=460,top=10,left=200,scrollbars=yes'); 
			 form.target = "reportPop";
			 eSubmit(form);
		 }		  

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR040510.etax">
	<input type="hidden" name="cmd" value="gwaonapList">
  <input type="hidden" name="seq" value="">
  <input type="hidden" name="procyn" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title4_6.gif"></td>
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
									    <span style="width:550px"></span>
											<span class="point">��ȸ����</span>
											<input type="text" style="width:80" name="su_str_date" value="<%=su_sdate%>" userType="date" required desc="������"> 
											<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_str_date');" style="cursor:hand"> -
											<input type="text" style="width:80" name="su_end_date" value="<%=su_edate%>" userType="date" required desc="������"> 
											<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_end_date');" style="cursor:hand">
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
              <th class="fstleft" width="4%" rowspan="3">����</th>
							<th width="9%">�ΰ���ȣ</th>
              <th width="9%">�����ڸ�</th>
							<th width="9%"rowspan="3" valign="top">�����ȣ</th>
							<th width="9%">�������ּ�</th>
							<th width="9%">����������</th>
							<th width="9%">�����ϼ�</th>
							<th width="9%" rowspan="3" valign="top">�����</th>
							<th width="9%">�������ݾ�</th>
							<th width="8%">���ݾ�</th>
							<th width="8%">����������</th>
							<th width="8%">��������</th>
						 </tr>
						 <tr>
							<th width="9%">��������</th>
							<th width="9%">�ֹε�Ϲ�ȣ</th>		
							<th width="9%" rowspan="2" valign="top">���Ǹ�</th>
							<th width="9%">�������</th>
							<th width="9%">ȯ����������</th>							
							<th width="9%">����</th>
							<th width="8%">ȯ�ޱݾ�</th>
							<th width="8%" rowspan="2" valign="top">ȯ�ι��</th>
							<th width="8%">���޹�ȣ</th>
            </tr>
						 <tr>
							<th width="9%">�μ�</th>
							<th width="9%">����</th>	
							<th width="9%">ȯ������</th>
							<th width="9%">��������</th>							
							<th width="9%">�հ�</th>
							<th width="8%">��������</th>
							<th width="8%">������ȣ</th>
            </tr>


						<%
						for (int i=0; gwaonapList != null && i < gwaonapList.size(); i++) {
							CommonEntity data = (CommonEntity)gwaonapList.get(i);
						%>
     
            <tr>                 
              <td class="fstleft" rowspan="3">
              <% if (!"R2".equals(data.getString("M090_GWAONAPSTATECODE")) ) { %>
              <input type="checkbox" name="userChk" value="<%=i%>">
              <% } else if ("R2".equals(data.getString("M090_GWAONAPSTATECODE")) ) { %>
              &nbsp;<input type="checkbox" name="userChk" value="<%=i%>" disabled>
              <% } %>
              </td>
							<td>&nbsp;<%=data.getString("M090_BUGWANO")%></td>
              <td>&nbsp;<%=data.getString("M090_NAPBUJANAME")%></td>
					    <td rowspan="3">&nbsp;<%=data.getString("")%></td>
							<td>&nbsp;<%=data.getString("M090_NAPBUJAADDRESS")%></td>						
							<td>&nbsp;<%=data.getString("M090_GWAONAPDATE")%></td>
							<td>&nbsp;<%=data.getString("M090_INTERESTDAY")%></td>
							<td rowspan="3">&nbsp;<%=TextHandler.formatNumber(data.getString("M090_GASANAMT"))%></td>
					    <td>&nbsp;<% if (!"".equals(data.getString("M090_DOCUMENTNO")) ) { %>
              <a href="javascript:goView('<%=data.getString("M090_SEQ")%>', '<%=data.getString("M090_GWAONAPSTATECODE")%>', '<%=data.getString("M090_DOCUMENTNO")%>', '<%=data.getString("M090_YEAR")%>')">
              <font color="red"><%=TextHandler.formatNumber(data.getString("M090_BONAMT"))%></font>
              </a>
              <% } else { %>
              <%=TextHandler.formatNumber(data.getString("M090_BONAMT"))%>
              <% } %>
              </td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_BONAMT"))%></td>
							<td>&nbsp;<%=data.getString("M090_GWAONAPREASON")%></td>
							<td>&nbsp;<%=data.getString("M090_NAPIBDATE")%></td>
            </tr>
						 <tr>                 
					    <td>&nbsp;01</td>
							<td>&nbsp;<%=data.getString("M090_JUMINNO")%></td>						
							<td rowspan="2">&nbsp;
              <%=data.getString("M090_BUSINESSNAME")%>
              </td>
							<td>&nbsp;<%=data.getString("M090_NAPBUJANAME")%></td>
							<td>&nbsp;<%=data.getString("M090_BALUIDATE")%></td>
					    <td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_INTERESTAMT"))%></td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("TOT_AMT"))%></td>
							<td rowspan="2">&nbsp;�۱�</td>
							<td>&nbsp;</td>
            </tr>
						 <tr>                 
					    <td>&nbsp;</td>
							<td>&nbsp;</td>						
							<td>&nbsp;</td>
							<td>&nbsp;<%=data.getString("M090_BALUIDATE")%></td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("TOT_AMT"))%></td>
					    <td>&nbsp;<%=data.getString("M090_BALUIDATE")%></td>
							<td>&nbsp;<%=data.getString("M090_DOCUMENTNO")%></td>
							<input type="hidden" name="seq_list" value="<%=data.getString("M090_SEQ")%>">
            </tr>
					  <%				 
						} if (gwaonapList == null) { 
					  %>
						<tr>
							<td class="fstleft" colspan="12">��ȸ ������ �����ϴ�.</td>
						</tr>
						<% 
							}
						%>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
								<img src="../img/btn_inform.gif" alt="�������" style="cursor:hand" onClick="goView(0,0,0,0)" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>