<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR051510.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금배정승인내역조회/배정처리
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> allotList  = (List<CommonEntity>)request.getAttribute("page.mn05.allotList");
	int allotListSize = 0;
	if (allotList != null ) {
		allotListSize = allotList.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
  String allot_date = request.getParameter("allot_date");
	if ("".equals(allot_date) ) {
    allot_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      var allotListSize = "<%=allotListSize%>";
      function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR051510.etax";
				form.cmd.value = "reprocList";
				form.target = "_self";
      	eSubmit(form);
      }
			function PowerChk(flag) {
				form = document.sForm;
				form.v_flag.value = flag;
        form.action = "../common/super_one.etax";
        form.target = "hidFrame";
        eSubmit(form);
			}

      function goCancel() {
				var form = document.sForm;
				var sendno1;
        var allotgubun1;
        var allotamt1;
        var proccnt1;
		
				if (allotListSize == 0)	{
					alert("조회 내역이 없습니다.");
					return;
				}
				if (allotListSize == 1)	{
					if (!form.sendno.checked) {
						alert("출금 건을 선택하세요.");
						return;
					}
					sendno1 = form.sendno.value;
          allotgubun1 = form.allotgubun.value;
          allotamt1 = form.allotamt.value;
          proccnt1 = form.proccnt.value;
				} else {
					var cp = 0;
					for (var i=0; i < allotListSize; i++)	{
						if (form.sendno[i].checked) {
							sendno1 = form.sendno[i].value;
		     	    allotgubun1 = form.allotgubun[i].value;
		     	    allotamt1 = form.allotamt[i].value;
		     	    proccnt1 = form.proccnt[i].value;
              cp ++;
						}
					}

			    if (cp == 0) {
						alert("출금 건을 선택하세요.");
						return;
					}
				}
				form.action = "IR051510.etax";
				form.cmd.value = "reprocDelete";
        form.allot_type.value = "1";
				form.target = "_self";
				eSubmit(form);
			}

			function goPermission(){
				var form = document.sForm;
				var sendno1;
        var allotgubun1;
        var allotamt1;
        var proccnt1;
		
				if (allotListSize == 0)	{
					alert("조회 내역이 없습니다.");
					return;
				}
				if (allotListSize == 1)	{
					if (!form.sendno.checked) {
						alert("출금 건을 선택하세요.");
						return;
					}
					sendno1 = form.sendno.value;
          allotgubun1 = form.allotgubun.value;
          allotamt1 = form.allotamt.value;
          proccnt1 = form.proccnt.value;
				} else {
					var cp = 0;
					for (var i=0; i < allotListSize; i++)	{
						if (form.sendno[i].checked) {
							sendno1 = form.sendno[i].value;
		     	    allotgubun1 = form.allotgubun[i].value;
		     	    allotamt1 = form.allotamt[i].value;
		     	    proccnt1 = form.proccnt[i].value;
              cp ++;
						}
					}

			    if (cp == 0) {
						alert("출금 건을 선택하세요.");
						return;
					}
				}
        
			  form.trans_gubun.value = "125";
				form.action = "IR051511.etax";
				form.cmd.value = "reprocInsert";
				form.target = "_self";
				eSubmit(form);				
			}

			function goPermission2() {
				var form = document.sForm;
				var sendno1;
        var allotgubun1;
        var allotamt1;
        var proccnt1;
		
				if (allotListSize == 0)	{
					alert("조회 내역이 없습니다.");
					return;
				}
				if (allotListSize == 1)	{
					if (!form.sendno.checked) {
						alert("출금 건을 선택하세요.");
						return;
					}
					sendno1 = form.sendno.value;
                    allotgubun1 = form.allotgubun.value;
                    allotamt1 = form.allotamt.value;
                    proccnt1 = form.proccnt.value;
				} else {
					var cp = 0;
					for (var i=0; i < allotListSize; i++)	{
						if (form.sendno[i].checked) {
							sendno1 = form.sendno[i].value;
		     	            allotgubun1 = form.allotgubun[i].value;
		     	            allotamt1 = form.allotamt[i].value;
		     	            proccnt1 = form.proccnt[i].value;
              cp ++;
						}
					}

					if (cp == 0) {
						alert("출금 건을 선택하세요.");
						return;
					}
				}
        if (confirm("선택 건(들)을 책임자승인하겠습니까?")) {
			  	  form.m100_sendno.value = sendno1;
			  	  form.m100_allotgubun.value = allotgubun1;
			  	  form.m100_allotamt.value = allotamt1;
			  	  form.m100_proccnt.value = proccnt1;
				  form.action = "IR051511.etax";
				  form.cmd.value = "reprocUpdate";
				  eSubmit(form);
        }
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR051510.etax">
	<input type="hidden" name="cmd" value="reprocList">
	<input type="hidden" name="trans_gubun" value="">
	<input type="hidden" name="m100_allotgubun" value="">
	<input type="hidden" name="m100_allotamt" value="">
	<input type="hidden" name="m100_sendno" value="">
	<input type="hidden" name="m100_proccnt" value="">
	<input type="hidden" name="err_code" value="">
	<input type="hidden" name="pms_cnt" value="">
	<input type="hidden" name="send_no" value="">
  <input type="hidden" name="v_flag" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_34.gif"></td>
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
									    배정일자
										  <input type="text" class="box3" size="8" name="allot_date" value="<%=allot_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','allot_date')" align="absmiddle">
										</td>
                    <td width="100"> 
                      <img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()">
                      <img src="../img/btn_adminok.gif" alt="책임자승인" style="cursor:hand" onClick="PowerChk('P')">
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
        <td width="975" height="20" align="right"> 단위(천원)</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
        <tr> 
          <th class="fstleft" width="4%">선택</th>
          <th width="6%">상태</th>
          <th width="6%">오류코드</th>
          <th width="10%">배정일자</th>
		  <th width="10%">출금전문</th>
          <th width="10%">출금거래일련번호</th>
          <th width="10%">배정금액</th>
          <th width="10%">입금건수</th>
        </tr>
				<%
				for (int i=0; allotList != null && i < allotList.size(); i++) {
				  CommonEntity data = (CommonEntity)allotList.get(i);
				%>
        <tr> 
          <td class="fstleft">
          <input type="hidden" name="proccnt" value="<%=data.getString("proccnt")%>">
          <input type="hidden" name="allotgubun" value="<%=data.getString("m100_allotgubun")%>">
          <input type="hidden" name="allotamt" value="<%=data.getString("m100_orgallotamt")%>">
          <input type="radio" name="sendno" value="<%=data.getString("m100_imp_tradeno")%>">
          </td>
          <td>&nbsp;<%=data.getString("m100_allotgbnnm")%></td>
          <td>&nbsp;<%=data.getString("m100_out_rec_cd")%></td>
          <td>&nbsp;<%=data.getString("m100_out_req_dt")%></td>
          <td>&nbsp;<%=data.getString("m100_imp_tradeno")%></td>
          <td>&nbsp;<%=data.getString("m100_trade_no")%></td>
          <td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("m100_allotamt"))%></td>
          <td>&nbsp;<%=data.getString("proccnt")%></td>
        </tr>
				<%	 
	      } if (allotList == null || allotList.size() == 0) { %>
        <tr>
				  <td class="fstleft" colspan="8">조회 내역이 없습니다.</td>
				</tr>
				<% 
				} %>
      </table>
        </td>
      </tr>
    </table>
		<iframe name="hidFrame" width="0" height="0"></iframe>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
		<% if (!"".equals(SucMsg)) { %>
		<script>
		  alert("<%=SucMsg%>");
		</script>
		<% } %>
  </body>
</html>