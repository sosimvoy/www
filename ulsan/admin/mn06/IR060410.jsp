<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR060410.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금예탁 일계등록 조회/취소
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> bankRegisterList  = (List<CommonEntity>)request.getAttribute("page.mn06.bankRegisterList");

	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	int bankRegisterListSize = 0;
	if (bankRegisterList != null ) {
		bankRegisterListSize = bankRegisterList.size();
	}
  String reg_date = request.getParameter("reg_date");
	if ("".equals(reg_date) ) {
    reg_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR060410.etax";
				form.cmd.value = "bankRegisterList";
      	eSubmit(form);
      }

			function goRegister() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=bankRegisterListSize%>;

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("취소할 건을 선택하세요.");
						return;
					}
					form.chk_val.value = "Y";
				} else {
					for (var i=0; i<form.stat_list.length; i++) {						
						if (form.allotChk[i].checked)	{
							cnt++;
              form.chk_val[i].value = "Y";
							if (form.stat_list[i].value != "S3")	{
								alert("일계등록 건만 선택 가능합니다.");
								return;
							}
						}
					}

					if (cnt ==0) {
						alert("취소할 건을 선택하세요.");
						return;
					}
				}
        if (confirm("선택 건(들)을 일계등록 취소하겠습니까?")) {
          form.cmd.value = "bankDepCancel";
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
  <form name="sForm" method="post" action="IR060410.etax">
	<input type="hidden" name="cmd" value="bankRegisterList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_6.gif"></td>
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
									    등록일자
										  <input type="text" class="box3" size="8" name="reg_date" value="<%=reg_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','reg_date')" align="absmiddle">
										</td>
                    <td width="100"> 
                      <img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()" align="absmiddle">
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
              <th class="fstleft" width="5%">선택<input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
							<th width="8%">회계연도</th>
              <th width="8%">예금종류</th>
              <th width="18%">신규금액</th>
							<th width="8%">이율(%)</th>
							<th width="8%">예탁기간</th>
							<th width="8%">만기일</th>
							<th width="11%">처리결과</th>
							<th width="16%">계좌번호</th>
							<th width="10%">좌수번호</th>
            </tr>
						<% long tot_amt = 0L;
               if (bankRegisterListSize > 0 && bankRegisterList != null) { 
							   String due_date = "";
							   for (int i=0; i < bankRegisterListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankRegisterList.get(i);
									 String state = rowData.getString("M120_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " 개월";
									 } else if ("".equals(rowData.getString("M120_DEPOSITDATE")) ) {
                     due_date = "";
                   } else {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " 일";
									 }
						%>
            <tr>
						  <% if ("S3".equals(rowData.getString("M120_DEPOSITSTATE")) ) { %>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<% } else { %>
							<td class="fstleft">&nbsp;<input type="hidden" name="allotChk" value="<%=i%>"></td>
							<% } %>
							<td class="center">&nbsp;<%=rowData.getString("M120_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSITTYPE_NAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M120_DEPOSITAMT"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("M120_INTERESTRATE")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M120_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="center">&nbsp;<%=rowData.getString("STATE_NAME")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M120_ACCOUNTNO"))%></td>
							<td class="right">&nbsp;<%=rowData.getString("M120_JWASUNO")%></td>
							<input type="hidden" name="stat_list" value="<%=rowData.getString("M120_DEPOSITSTATE")%>">
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M120_SEQ")%>">
							<input type="hidden" name="stat_type" value="<%=rowData.getString("M120_DEPOSITTYPE")%>">
							<input type="hidden" name="inamt" value="<%=rowData.getString("M120_DEPOSITAMT")%>">
							<input type="hidden" name="due_day" value="<%=rowData.getString("M120_DEPOSITDATE")%>">
							<input type="hidden" name="acct_no" value="<%=rowData.getString("M120_ACCOUNTNO")%>">
							<input type="hidden" name="fis_year" value="<%=rowData.getString("M120_YEAR")%>">
							<input type="hidden" name="chk_val">
            </tr>
						<%     tot_amt += rowData.getLong("M120_DEPOSITAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="5">합계</td>
              <td class="right" colspan="5"><%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
		        <%   } else { %>
						<tr> 
              <td class="fstleft" colspan="11">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td><img src="../img/btn_cancel.gif" alt="취소" style="cursor:hand" onClick="goRegister()" align="absmiddle"></td>
            </tr>
          </table>
        </td>
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