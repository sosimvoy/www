<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR060810.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱ�����䱸���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%

  List<CommonEntity> bankOutList = (List<CommonEntity>)request.getAttribute("page.mn06.bankOutList");
  Integer totalCount = (Integer)request.getAttribute("page.mn06.totalCount");	//�� ī��Ʈ
	if (totalCount == null ) {
	  totalCount = 0;
	}
	String cpage = request.getParameter("cpage");	// ���� ������ ��ȣ �ޱ�
	if( "".equals(cpage) ) {
	 cpage = "1";
	}
	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int bankOutListSize = 0;
	if (bankOutList != null ) {
		bankOutListSize = bankOutList.size();
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
     
      function goSearch(cpage) {
				var form = document.sForm;
        if(cpage != null) {
      		form.cpage.value=cpage;
      	} else {
      		form.cpage.value="";
      	}
				form.action = "IR060810.etax";
				form.cmd.value = "bankOutList";
      	eSubmit(form);
      }

      function PAGE(cpage) {
      	goSearch(cpage);
      }


			function goRegister() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=bankOutListSize%>;

				if (listSize == 0) {
					alert("��ȸ������ �����ϴ�.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("����� ���� �����ϼ���.");
						return;
					}

          if (parseInt(form.req_amt.value.replace(/,/g, ""), 10) < parseInt(form.dep_amt.value.replace(/,/g, ""), 10)) {
            alert("���� ���ɾ�("+form.req_amt.value+")�� �ʰ��� �� �����ϴ�.");
            return;
          }

          if (parseInt(form.req_amt.value.replace(/,/g, ""), 10) - parseInt(form.dep_amt.value.replace(/,/g, ""), 10) != 0) {
            if (form.mmda_cancel.value == "Y") {
              alert("mmda ������ �����ܾ��� 0�� �Ǿ�� �մϴ�.");
              return;
            }
          }
				} else {
					for (var i=0; i<form.allotChk.length; i++) {						
						if (form.allotChk[i].checked)	{
							cnt++;
						}

            if (parseInt(form.req_amt[i].value.replace(/,/g, ""), 10) < parseInt(form.dep_amt[i].value.replace(/,/g, ""), 10)) {
              alert("���� ���ɾ�("+form.req_amt[i].value+")�� �ʰ��� �� �����ϴ�.");
              return;
            }

            if (parseInt(form.req_amt[i].value.replace(/,/g, ""), 10) - parseInt(form.dep_amt[i].value.replace(/,/g, ""), 10) != 0) {
              if (form.mmda_cancel[i].value == "Y") {
                alert("mmda ������ �����ܾ��� 0�� �Ǿ�� �մϴ�.");
                return;
              }
            }
					}

					if (cnt ==0) {
						alert("����� ���� �����ϼ���.");
						return;
					}
				}
        if (confirm("���� ��(��)�� ���� �䱸����ϰڽ��ϱ�?")) {
          form.cmd.value = "inchulReqReg";
			  	form.action = "IR060810.etax";
			  	eSubmit(form);
        }
			  
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
  <form name="sForm" method="post" action="IR060810.etax">
	<input type="hidden" name="cmd" value="bankOutList">
  <input type="hidden" name="cpage" value="">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="181">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_11.gif"></td>
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
        <td width="975" height="20"> &nbsp;</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="4%">����<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="5%">ȸ��<br>����</th>
							<th width="7%">����<br>����</th>
							<th width="12%">���¹�ȣ</th>
							<th width="6%">�¼�<br>��ȣ</th>
							<th width="8%">�ű���</th>
							<th width="8%">������</th>
							<th width="6%">��Ź<br>�Ⱓ</th>
							<th width="5%">����(%)</th>
							<th width="12%">����</th>
							<th width="14%">��������/<br>�����ݾ�</th>
							<th width="8%">�ڱ�<br>����</th>	
              <th width="5%">mmda<br>����</th>
            </tr>
						<% if (bankOutListSize > 0 && bankOutList != null) { 
							   String due_date = "";
                 long possible_amt = 0L;
							   for (int i=0; i < bankOutListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankOutList.get(i);
									 String state = rowData.getString("DEPOSIT_TYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("DEP_PERIOD") + " ����";
									 } else if ("G2".equals(state) ) {
										 due_date = rowData.getString("DEP_PERIOD") + " ��";
									 }
                   possible_amt = rowData.getLong("DEP_AMT") - rowData.getLong("M140_REQAMT");
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
              <td class="center">&nbsp;<%=rowData.getString("FIS_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSIT_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("ACCT_NO"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("JWASU_NO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("NEW_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("END_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
							<td class="right">&nbsp;<%=rowData.getString("DEP_RATE")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("DEP_AMT"))%></td>
              <td class="right"><input type="text" name="dep_amt" value="<%=possible_amt%>" size="15" userType="money" <%if (!"G3".equals(rowData.getString("DEPOSIT_TYPE")) && !"G4".equals(rowData.getString("DEPOSIT_TYPE")) ) { %> readonly <% } %>></td>
							<td class="center">
							  <select name="mmda_type">
								  <option value="G1">���ݿ���</option>
									<option value="G2">���⿹��</option>
									<option value="G3">ȯ��ä</option>
									<option value="G4">MMDA</option>
								</select>
							</td>
              <td>
                <% if ("G3".equals(state)) { %>
                <select name="mmda_cancel">
                  <option value="N">N</option>
                  <option value="Y">Y</option>
                </select>
                <% } else { %>
                &nbsp;<input type="hidden" name="mmda_cancel" value="N">
                <% } %>
              </td>
							<input type="hidden" name="dep_gubun" value="<%=rowData.getString("DEPOSIT_TYPE")%>">
							<input type="hidden" name="acct_no" value="<%=rowData.getString("ACCT_NO")%>">
							<input type="hidden" name="jwasu_no" value="<%=rowData.getString("JWASU_NO")%>">
              <input type="hidden" name="req_amt" value="<%=possible_amt%>">
            </tr>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="12">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <%// ������ %>
          <table width="100%">
            <tr>
              <td align="center">
                <br>
                <etax:pagelink totalCnt='<%=totalCount.intValue()%>' page='<%=cpage%>'	pageCnt='10' blockCnt='10'  />
              </td>
            </tr>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td><img src="../img/btn_order.gif" alt="���" style="cursor:hand" onClick="goRegister()" align="absmiddle"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  </form>
		<iframe name="hidFrame" width="0" height="0"></iframe>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>