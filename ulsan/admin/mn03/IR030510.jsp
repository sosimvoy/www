<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR030510.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-28
* ���α׷�����	   : ���Լ�������� > ���༼ ��ȸ/����/����
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn03.jsp" %>

<%
	List<CommonEntity>juheangList =    (List<CommonEntity>)request.getAttribute("page.mn03.juheangList");
  String SucMsg = (String)request.getAttribute("page.mn03.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String fis_date =    (String)request.getAttribute("page.mn03.fis_date");

  String to_day = TextHandler.getCurrentDate();
  
	String start_date           = request.getParameter("start_date");           //���۳�¥
  String end_date             = request.getParameter("end_date");             //���ᳯ¥
	 
	if (start_date.equals("")) {
      start_date  = TextHandler.formatDate(fis_date,"yyyyMMdd","yyyy-MM-dd");
  }  

  if (end_date.equals("")) {
      end_date  = TextHandler.formatDate(fis_date,"yyyyMMdd","yyyy-MM-dd");
  } 

  int listSize = 0;
	if (juheangList != null && juheangList.size() > 0) {
		listSize = juheangList.size();
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
        
        if (replaceStr(form.start_date.value, "-", "") > to_day) {
          alert("��ȸ���ڰ� ���Ϻ��� Ů�ϴ�.");
          return;
        }

        if (replaceStr(form.start_date.value, "-", "") > replaceStr(form.end_date.value, "-", "")) {
          alert("��ȸ�������� ��ȸ�����Ϻ��� Ů�ϴ�.");
          return;
        }

        if (replaceStr(form.start_date.value, "-", "").substring(0,6) != replaceStr(form.end_date.value, "-", "").substring(0,6)) {
          alert("���� ����� �ڷḸ ��ȸ�� �����մϴ�.");
          return;
        }
		    form.cmd.value = "juheangList";
		    form.action    = "IR030510.etax";
				form.target = "_self";
				eSubmit(form);
			}
     
		  function goCancel(){
				var form = document.sForm;
				var listSize = <%=listSize%>;
				var cnt = 0;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}
				if (listSize == 1) {
					if (!form.userChk.checked) {
						alert("������ ���� �����ϼ���.");
						return;
					}
				} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.userChk[i].checked) {
							cnt++;
						}
					}
					 
				  if (cnt == 0) {
						alert("������ ���� �����ϼ���.");
					  return;
					}

          if (cnt > 1) {
            alert("������ �ѰǸ� �����մϴ�.");
					  return;
          }
				}
        if (confirm("���� ��(��)�� �����ϰڽ��ϱ�?")) {
          form.action = "IR030510.etax";  
				  form.cmd.value ="DeleteJuheang";
          form.target = "_self";
			    eSubmit(form);
        }
			}

			 function goUpdate(){
				var form = document.sForm;
				var listSize = <%=listSize%>;
				var cnt = 0;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.userChk.checked) {
						alert("������ ���� �����ϼ���");
						return;
					}
				} else if (listSize > 1) {
						for (var i=0; i<listSize; i++) {
							if (form.userChk[i].checked) {
								cnt++;
							}
						}
					 
						if (cnt == 0) {
							alert("������ ���� �����ϼ���");
							return;
						}

						if (cnt > 1) {
							alert("�����ϰ����ϴ� ���� �ϳ��� �����ϼ���.");
							return;
						}
					}
					window.open('IR030511.etax', 'popView' ,'height=300,width=300,top=100,left=100,scrollbars=no');
					form.action = "IR030511.etax";
					form.cmd.value = "JuheangView";
					form.target = "popView";
					eSubmit(form);
				}
			 
			 function goView(b){
        var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("��ȸ������ �����ϴ�.");
			    return;
		    }
          
				window.open('IR030512.etax', 'popview' ,'height=600,width=410,top=10,left=100,scrollbars=yes');
				form.seq.value = b;
		    form.action = "IR030512.etax"
		    form.cmd.value = "YoungsuView";
		    form.target = "popview";
		    eSubmit(form);
				
        }
				
			 function goSunap1(){
				 var form = document.sForm;				
         form.M060_JINGSUTYPE.value="J2";
         form.action = "IR030513.etax";
			 	 form.cmd.value = "JuheangView";
				 window.open('IR030513.etax', 'popView' ,'height=800,width=800,top=100,left=100,scrollbars=yes');
				 form.target = "popView";
				 eSubmit(form);
			 }

			 function goSunap2(){
				 var form = document.sForm;		
         form.M060_JINGSUTYPE.value="J1";
         form.action = "IR030514.etax";
				 form.cmd.value = "JuheangView";
				 window.open('IR030514.etax', 'popView2' ,'height=800,width=800,top=100,left=100,scrollbars=yes');
				 form.target = "popView2";
				 eSubmit(form);
			 }

			 function goSunap3(){
				 var form = document.sForm;	
         form.action = "IR030515.etax";
				 form.cmd.value = "juhangDayView";
    		 window.open('IR030515.etax', 'popView3' ,'height=800,width=1050,top=100,left=100,scrollbars=yes');	
			   form.target = "popView3";
         eSubmit(form);
			 }	
    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
    <form name="sForm" method="post" action="IR030510.etax">
    <input type="hidden" name="cmd" value="juheangList">
    <input type="hidden" name="seq" value="">
    <input type="hidden" name="M060_JINGSUTYPE" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title3_7.gif"></td>
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
										<td width="690">
                    <span style="width:50"></span>
										<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">��ȸ����</span>
											<input type="text"  class="box3" name="start_date" value="<%=start_date%>" size="8" userType="date" required desc="��ȸ��������"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','start_date');" style="cursor:hand"> -
        			      	<input type="text"  class="box3" name="end_date" value="<%=end_date%>" size="8" userType="date" required desc="��ȸ��������"> 
                      <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','end_date');" style="cursor:hand">
										<span style="width:50"></span>
										<img src="../img/board_icon1.gif" align="absmiddle">		
										<span class="point">�ǹ���</span>
										    <input type="text" name="napseja" value="<%=request.getParameter("napseja")%>" size="20" class="box3">
										 </td>
										 <td width="200"> 
											<div align="right"><img src="../img/btn_search.gif" onClick="goSearch()" style="cursor:hand" align="absmiddle">&nbsp;&nbsp;
                      <img src="../img/btn_soo.gif" alt="������" align="absmiddle" onclick="goSunap1();goSunap2();goSunap3();" style="cursor:hand">
											</div>
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
              <th class="fstleft" width="4%">����</th>
							<th width="12%">¡������</th>
							<th width="10%">�����ݱ���</th>
              <th width="10%">�ݾױ���</th>
							<th width="16%">��������</th>
							<th width="16%">�ǹ���</th>
							<th width="16%">���Ծ�</th>
							<th width="16%">���޾�/�Ⱥо�</th>
            </tr>
         	 <%
						for (int i=0; juheangList != null && i < juheangList.size(); i++) {
							CommonEntity data = (CommonEntity)juheangList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td class="center">&nbsp;<%=data.getString("M060_JINGSUNAME")%></td>
						  <td>&nbsp;<%=data.getString("M060_REPAYNAME")%></td>
              <td>&nbsp;<%=data.getString("M060_CASHNAME")%></td>
							<td>&nbsp;<%=TextHandler.formatDate(data.getString("M060_DATE"), "yyyyMMdd", "yyyy�� MM�� dd��")%></td>
							<% if ("A1".equals(data.getString("M060_CASHTYPE")) ) {  %>
							<td class="left">&nbsp;<a href="javascript:goView('<%=data.getString("M060_SEQ")%>')"><font color="red"><%=data.getString("M060_NAPSEJA")%></font></a></td>
						  <%} else {%>
              <td class="left">&nbsp;<%=data.getString("M060_NAPSEJA")%></td>
              <% } %>

							<% if ("A1".equals(data.getString("M060_CASHTYPE")) ) {  %>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("SUIP_AMT"))%></td>
							<%}else if ("A2".equals(data.getString("M060_CASHTYPE")) ) {  %>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("GWAO_AMT"))%></td>
							<% }else{ %>
               <td>&nbsp;</td>
              <% } %>

							<% if ("A3".equals(data.getString("M060_CASHTYPE")) ) {  %>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("JIGUP_AMT"))%></td>
							<%}else if ("A4".equals(data.getString("M060_CASHTYPE")) ) {  %>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("BANNAP_AMT"))%></td>
							<% }else{ %>
               <td>&nbsp;</td>
							 <%}%>

							<input type="hidden" name="seq_list" value="<%=data.getString("M060_SEQ")%>">
							<input type="hidden" name="cashType" value="<%=data.getString("M060_CASHTYPE")%>">
              <input type="hidden" name="jingsuType" value="<%=data.getString("M060_JINGSUTYPE")%>">
              <input type="hidden" name="repayType" value="<%=data.getString("M060_REPAYTYPE")%>">
              <input type="hidden" name="input_amt" value="<%=data.getString("M060_AMT")%>">
              <input type="hidden" name="input_date" value="<%=data.getString("M060_DATE")%>">
            </tr>
					   <%				 
						} if (juheangList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="10">��ȸ ������ �����ϴ�.</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
					<table class="btn">
						<tr>
							<td>
								<img src="../img/btn_delete2.gif" alt="����" align="absmiddle" onclick="goCancel()" style="cursor:hand">
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