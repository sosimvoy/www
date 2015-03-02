<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061010.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금인출승인조회/일계등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%

  List<CommonEntity> inchulPmsList = (List<CommonEntity>)request.getAttribute("page.mn06.inchulPmsList");
  
	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int inchulPmsListSize = 0;
	if (inchulPmsList != null ) {
		inchulPmsListSize = inchulPmsList.size();
	}
  String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}
  
  String to_day = TextHandler.getCurrentDate();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      var to_day = "<%=to_day%>";
      function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
 
				form.action = "IR061010.etax";
				form.cmd.value = "inchulPmsList";
      	eSubmit(form);
      }

			function goConfirm() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=inchulPmsListSize%>;

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("일계등록할 건을 선택하세요.");
						return;
					}

          if (form.state_code.value != "S2") {
            alert("승인 건만 일계등록 처리 가능합니다.");
            return;
          }

          if (form.cancel_date.value == "") {
            alert("해지일자를 입력하세요.");
            return;
          }
          
          if (replaceStr(form.acc_date.value, "-", "") > replaceStr(form.cancel_date.value, "-", "")) {
            alert("등록일자보다 작은 해지일자는 입력이 불가합니다.");
            return;
          }
          
          if (replaceStr(form.cancel_date.value, "-", "") > to_day) {
            alert("금일보다 큰 해지일자는 입력이 불가합니다.");
            return;
          }
          form.chk_val.value = "Y";
				} else {
					for (var i=0; i<form.allotChk.length; i++) {						
						if (form.allotChk[i].checked)	{
              if (form.state_code[i].value != "S2") {
                alert("승인 건만 일계등록 처리 가능합니다.");
                return;
              }

              if (form.cancel_date[i].value == "") {
                alert("해지일자를 입력하세요.");
                return;
              }
              
              if (replaceStr(form.acc_date.value, "-", "") > replaceStr(form.cancel_date[i].value, "-", "")) {
                alert("등록일자보다 작은 해지일자는 입력이 불가합니다.");
                return;
              }
              
              if (replaceStr(form.cancel_date[i].value, "-", "") > to_day) {
                alert("금일보다 큰 해지일자는 입력이 불가합니다.");
                return;
              }

              form.chk_val[i].value = "Y";
							cnt++;
						}
					}

					if (cnt ==0) {
						alert("일계등록할 건을 선택하세요.");
						return;
					}
				}
        if (confirm("선택 건(들)을 일계등록처리하겠습니까?")) {
          form.cmd.value = "inchulReqPms";
			  	form.action = "IR061010.etax";
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
  <form name="sForm" method="post" action="IR061010.etax">
	<input type="hidden" name="cmd" value="inchulPmsList">
  <input type="hidden" name="cpage" value="">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="201">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_13.gif"></td>
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
									    등록일자
											<input type="text" class="box3" size="10" name="acc_date" value="<%=acc_date%>" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
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
              <th class="fstleft" width="3%">택<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="4%">회계<br>연도</th>
							<th width="6%">예금<br>종류</th>
							<th width="11%">계좌번호</th>
							<th width="5%">좌수<br>번호</th>
							<th width="8%">신규일</th>
							<th width="8%">만기일</th>
							<th width="6%">예탁<br>기간</th>
							<th width="4%">이율(%)</th>
							<th width="11%">원금</th>
							<th width="11%">원금인출/<br>해지금액</th>					
              <th width="4%">처리<br>결과</th>	
              <th width="9%">해지/<br>인출일자</th>	
              <th width="6%">해지<br>이자</th>	
              <th width="4%">mmda<br>해지</th>	
            </tr>
						<% long tot_amt = 0L;
               long inchul_amt = 0L;
               if (inchulPmsListSize > 0 && inchulPmsList != null) { 
							   String due_date = "";
							   for (int i=0; i < inchulPmsListSize; i++) {
									 CommonEntity rowData = (CommonEntity)inchulPmsList.get(i);
									 String state = rowData.getString("M130_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " 개월";
									 } else if ("G2".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " 일";
									 } else {
                     due_date = "";
                   }
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
              <td class="center"><%=rowData.getString("M130_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSIT_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M130_ACCOUNTNO"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("M130_JWASUNO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_SINKYUDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
							<td class="right">&nbsp;<%=rowData.getString("M130_INTERESTRATE")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_JANAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_INCHULAMT"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("STATE_NAME")%></td>
              <td class="center"><input type="text" name="cancel_date" size="8" class="box3" maxlength="10" userType="date"></td>
							<td class="center"><input type="text" name="cancel_ija" size="10" class="box3" maxlength="15" userType="money"></td>
              <td class="center">&nbsp;
              <% if ("G3".equals(state)) { %>
              <%=rowData.getString("M130_MMDA_CANCEL_YN")%>
              <% } %>
              </td>
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M130_SEQ")%>">
              <input type="hidden" name="state_code" value="<%=rowData.getString("M130_STATECODE")%>">
              <input type="hidden" name="input_amt" value="<%=rowData.getString("M130_INCHULAMT")%>">
              <input type="hidden" name="due_day" value="<%=rowData.getString("M130_DEPOSITDATE")%>">
              <input type="hidden" name="deposit_type" value="<%=rowData.getString("M130_DEPOSITTYPE")%>">
              <input type="hidden" name="fis_year" value="<%=rowData.getString("M130_YEAR")%>">
              <input type="hidden" name="acct_no" value="<%=rowData.getString("M130_ACCOUNTNO")%>">
              <input type="hidden" name="jwasu_no" value="<%=rowData.getString("M130_JWASUNO")%>">
              <input type="hidden" name="chk_val">
            </tr>
						<%     tot_amt += rowData.getLong("M130_JANAMT");
                   inchul_amt += rowData.getLong("M130_INCHULAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="4">원금</td>
              <td class="right" colspan="4"><%=TextHandler.formatNumber(tot_amt)%></td>
              <td class="center" colspan="3">인출금액</td>
              <td class="right" colspan="3"><%=TextHandler.formatNumber(inchul_amt)%></td>
            </tr>
		        <% } else { %>
						<tr> 
              <td class="fstleft" colspan="15">&nbsp;</td>
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
              <td><img src="../img/btn_ilorder.gif" alt="일계등록" style="cursor:hand" onClick="goConfirm()" align="absmiddle"></td>
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