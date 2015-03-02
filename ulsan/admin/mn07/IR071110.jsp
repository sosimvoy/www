<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR071110.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-19
* 프로그램내용	   : 세입 > 수입자료등록/취소
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn07.jsp" %>

<%
	List<CommonEntity>budgetsemokList =    (List<CommonEntity>)request.getAttribute("page.mn07.budgetsemokList");
  List<CommonEntity>budgetpartList =    (List<CommonEntity>)request.getAttribute("page.mn07.budgetpartList");
  List<CommonEntity>budgetList     =    (List<CommonEntity>)request.getAttribute("page.mn07.budgetList");
	
	String SucMsg = (String)request.getAttribute("page.mn07.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String ago_acc_date  = (String)request.getAttribute("page.mn07.ago_acc_date");    // 전영업일 받아오기

  String input_date = request.getParameter("input_date");
  
  if(input_date.equals("") || input_date == null){
      input_date = TextHandler.formatDate(ago_acc_date,"yyyyMMdd","yyyy-MM-dd");
  }
  
  String que_date = request.getParameter("que_date");
  if(que_date.equals("") || que_date == null){
      que_date = TextHandler.formatDate(ago_acc_date,"yyyyMMdd","yyyy-MM-dd");
  }

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int budgetSize = 0;
	if (budgetList != null && budgetList.size() > 0) {
		budgetSize = budgetList.size();
	}
  int listSize = 0;
	if (budgetsemokList != null && budgetsemokList.size() > 0) {
		listSize = budgetsemokList.size();
	}
  int partSize = 0;
	if (budgetpartList != null && budgetpartList.size() > 0) {
		partSize = budgetpartList.size();
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
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
					alert("<%=SucMsg%>");
				<% } %>
			}

      function goSearch() {
				var form = document.sForm;

		    form.cmd.value = "budgetdayList";
		    form.action    = "IR071110.etax";
				form.target = "_self";
				eSubmit(form);
			}
      
			 function gosaveData()	{
				var form = document.sForm;
        
				if (form.year.value == "") {
          alert("회계연도를 입력하세요.");
          form.year.focus();
          return;
        }
				if (form.input_date.value == "") {
          alert("작업일자를 입력하세요.");
          form.input_date.focus();
          return;
        }
				if (form.semokCode.value == "") {
          alert("과목코드를 입력하세요.");
          form.budgetsemokCode.focus();
          return;
        }
				if (form.partCode.value == "") {
          alert("부서코드를 입력하세요.");
          form.budgetsemokCode.focus();
          return;
        }
				if (form.sunapamt.value == "0" && form.hwanbuamt.value == "0") {
          alert("수납금액 또는 환부금액중 하나는 반드시입력해야합니다.");
          form.sunapamt.focus();
          return;
        }
				if (form.sunapcnt.value == "0" && form.hwanbucnt.value == "0") {
          alert("수납건수 또는 환부건수중 하나는 반드시입력해야합니다.");
          form.sunapcnt.focus();
          return;
        }
				if (!form.sunapamt.value > "0" && form.sunapcnt.value == "0") {
            alert("수납금액이 입력이 되었으면 수납건수도 반드시 입력해야합니다.");
            form.sunapcnt.focus();
            return;
        }
				if (!form.sunapcnt.value > "0" && form.sunapamt.value == "0") {
            alert("수납건수가 입력이 되었으면 수납금액도 반드시 입력해야합니다.");
            form.sunapamt.focus();
            return;
        }
				if (form.hwanbuamt.value > "0" && form.hwanbucnt.value == "0") {
            alert("환부금액이 입력이 되었으면 환부건수도 반드시 입력해야합니다.");
            form.hwanbucnt.focus();
            return;
        }
				if (form.hwanbucnt.value > "0" && form.hwanbuamt.value == "0") {
            alert("환부건수가 입력이 되었으면 환부금액도 반드시 입력해야합니다.");
            form.hwanbuamt.focus();
            return;
        }

				form.action = "IR071110.etax";
				form.cmd.value = "budgetdayInsert";
				eSubmit(form);
			}

			 function goprocData()	{
				var form = document.sForm;
        
				if (form.que_date.value == "") {
          alert("조회일자를 입력하세요.");
          form.que_date.focus();
          return;
        }

				form.action = "IR071110.etax";
				form.cmd.value = "budgetdayProc";
				eSubmit(form);
			}
			 function goprocCancelData()	{
				var form = document.sForm;
        
				if (form.que_date.value == "") {
          alert("조회일자를 입력하세요.");
          form.que_date.focus();
          return;
        }

				form.action = "IR071110.etax";
				form.cmd.value = "budgetdaycancelProc";
				eSubmit(form);
			}
			 function seltax(seltax)	{
				var form = document.sForm;
        var selmok = form.mokgubun.value;

				if (selmok == "") {
          return;
        }

				form.action = "IR071110.etax";
				form.cmd.value = "budgetsemok";
				eSubmit(form);
			}

      
			 function selmok(selmok)	{
				var form = document.sForm;
        var seltax = form.taxgbn.value;
        
				if (seltax == "") {
          return;
        }

				form.action = "IR071110.etax";
				form.cmd.value = "budgetsemok";
				eSubmit(form);
			}

		  function goCancel(){
				var form = document.sForm;
				var listSize = <%=budgetSize%>;
				var cnt = 0;

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}
				if (listSize == 1) {
					if (!form.userChk.checked) {
						alert("삭제할 건을 선택하세요");
						return;
					}
				} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.userChk[i].checked) {
              if( i == 0 ) {
                alert("합계를 위해 참조로 조회된 자료는 삭제할 수 없습니다.");
                return;
              }
							cnt++;
						}
					}
					 
						if (cnt == 0) {
						alert("삭제할 건을 선택하세요");
						return;
						}
					}
				 form.action = "IR071110.etax";  
				 form.cmd.value ="budgetdayDelete";
				 eSubmit(form);
			 }
     
     function ltrim(budgetsemokName) {
			 var form = document.sForm
			return form.budgetsemokName.replace(/^\s+/,"");
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
		<form name="sForm" method="post" action="IR071110.etax">
    <input type="hidden" name="cmd" value="budgetdayList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_30.gif"></td>
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
										<td width="80">&nbsp;</td>
										<td width="800">
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										  <span class="point">회계연도</span>
											  <select name="year" iValue="<%=request.getParameter("year")%>">
												  <option value="<%=this_year%>"><%=this_year%></option>
  												<option value="<%=last_year%>"><%=last_year%></option>
	  										</select>
  									  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">작업일자&nbsp; 
                      <input type="text" class="box3" name="input_date" value="<%=input_date%>" size="8" userType="date" desc="작업일자">
    									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','input_date');" style="cursor:hand">
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">부서</span>
												 <select name="partCode" iValue="<%=request.getParameter("partCode")%>" style="width:100">
								        <% for (int i=0; budgetpartList != null && i < budgetpartList.size(); i++) {
									         CommonEntity partCdInfo = (CommonEntity)budgetpartList.get(i); %>												    
								        <option value="<%=partCdInfo.getString("M551_PARTCODE")%>"><%=partCdInfo.getString("M551_PARTNAME")%></option>
									      <% } %>
								        </select>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">세목</span>
											<select name="taxgbn" iValue="<%=request.getParameter("taxgbn")%>" style="width:100" onChange="seltax(this.value)">
											  <option value="">--선택--</option>
												<option value="0">지방세</option>
												<option value="1">국세</option>
												<option value="2">경상적세외</option>
                        <option value="3">임시적세외</option>
												<option value="4">의존수입</option>
											</select>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">관항목</span>
											<select name="mokgubun" iValue="<%=request.getParameter("mokgubun")%>" style="width:50" onChange="selmok(this.value)">
											  <option value="">선택</option>
                        <option value="3">목</option>
                        <option value="5">세세목</option>
											</select>
                        <br><br>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">과목코드</span>
												 <select name="semokCode" iValue="<%=request.getParameter("semokCode")%>" style="width:20%">
								        <% for (int i=0; budgetsemokList != null && i < budgetsemokList.size(); i++) {
									         CommonEntity semokCdInfo = (CommonEntity)budgetsemokList.get(i); %>												    
								        <option value="<%=semokCdInfo.getString("M550_SEMOKCODE")%>"><%=semokCdInfo.getString("M550_SEMOKCODE")%>-<%=semokCdInfo.getString("M550_SEMOKNAME")%></option>
									      <% } %>
								        </select>
        							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                       수납금액&nbsp; <input type="text" name="sunapamt" class="box3" size="13" value="0" userType="money" required desc="수납금액" >
        						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">									
                       수납건&nbsp; <input type="text" name="sunapcnt" class="box3" size="2" value="0" userType="number" maxlength="5" required desc="수납건수" >		 
        							  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
                       환부금액&nbsp; <input type="text" name="hwanbuamt" class="box3" size="13" value="0" userType="money" required desc="수납금액" >
        						   <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">									
                       환부건&nbsp; <input type="text" name="hwanbucnt" class="box3" size="2" value="0" userType="number" maxlength="5" required desc="수납건수" >
                       </td>
											 <td width="10"> 
											<div align="right"><img src="../img/btn_order.gif" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
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
	        <br>
				  	<hr>
					<br>
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
						<tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="80">&nbsp;</td>
										<td width="800"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										  <span class="point">회계연도</span>
											  <select name="queyear" iValue="<%=request.getParameter("queyear")%>">
												  <option value="<%=this_year%>"><%=this_year%></option>
  												<option value="<%=last_year%>"><%=last_year%></option>
	  										</select>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">조회일자</span>
										<input type="text" class="box3" name="que_date" value="<%=que_date%>" size="8" userType="date" desc="조회일자">
    									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','que_date');" style="cursor:hand">
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">세목구분</span>
											<select name="quetaxgbn" iValue="<%=request.getParameter("quetaxgbn")%>" style="width:80" >
											  <option value="">--선택--</option>
												<option value="0">지방세</option>
												<option value="1">국세</option>
												<option value="2">경상적세외</option>
                        <option value="3">임시적세외</option>
												<option value="4">의존수입</option>
											</select>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">처리구분</span>
											<select name="queprocgbn" iValue="<%=request.getParameter("queprocgbn")%>" style="width:80" >
											  <option value="">--선택--</option>
												<option value="1">OCR</option>
												<option value="2">수기입력</option>
												<option value="3">차량엑셀</option>
                        <option value="4">광역시세</option>
                        <option value="5">농협(세입)</option>
                        <option value="6">농협(과오납)</option>
											</select>
							         <span style="width:20px"></span>
											<img src="../img/btn_search.gif" border="0" onClick="goSearch()" style="cursor:hand">
		    							<img src="../img/btn_ocr.gif" alt="처리" onClick="goprocData()" style="cursor:hand" >
		    							<img src="../img/btn_ocrcancel.gif" alt="취소" onClick="goprocCancelData()" style="cursor:hand" >
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
              <th class="fstleft" width="4%">택<input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.userChk, this.checked);"></th>
							<th width="5%">년도</th>
							<th width="8%">회계일자</th>
              <th width="17%">부서명</th>
              <th width="22%">세목명</th>
              <th width="6%">처리구분</th>
              <th width="6%">수납건수</th>
              <th width="12%">수납금액</th>
              <th width="6%">환부건수</th>
              <th width="12%">환부금액</th>
            </tr>
         	 <%
						for (int i=0; budgetList != null && i < budgetList.size(); i++) {
							CommonEntity data = (CommonEntity)budgetList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M500_YEAR")%></td>
						  <td>&nbsp;<%=data.getString("M500_DATE")%></td>
							<td>&nbsp;<%=data.getString("M551_PARTNAME")%></td>
							<td>&nbsp;<%=data.getString("M550_SEMOKNAME")%></td>
							<td>&nbsp;<%=data.getString("M500_PROCTYPE_NM")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("MST_SUNABCNT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("MST_SUNABAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("MST_HWANBUCNT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("MST_HWANBUAMT"))%></td>
							<input type="hidden" name="del_year" value="<%=data.getString("M500_YEAR")%>">
							<input type="hidden" name="del_date" value="<%=data.getString("M500_DATE")%>">
							<input type="hidden" name="del_prtdate" value="<%=data.getString("M500_PRTDATE")%>">
							<input type="hidden" name="del_taxgbn" value="<%=data.getString("M500_TAXGBN")%>">
							<input type="hidden" name="del_proctype" value="<%=data.getString("M500_PROCTYPE")%>">
							<input type="hidden" name="del_partcode" value="<%=data.getString("M500_PARTCODE")%>">
							<input type="hidden" name="del_semokcode" value="<%=data.getString("M500_SEMOKCODE")%>">
							<input type="hidden" name="del_seq" value="<%=data.getString("SEQ")%>">
							<input type="hidden" name="del_sunabamt" value="<%=data.getString("HST_SUNABAMT")%>">
							<input type="hidden" name="del_sunabcnt" value="<%=data.getString("HST_SUNABCNT")%>">
							<input type="hidden" name="del_hwanbuamt" value="<%=data.getString("HST_HWANBUAMT")%>">
							<input type="hidden" name="del_hwanbucnt" value="<%=data.getString("HST_HWANBUCNT")%>">
            </tr>
					   <%				 
						} if (budgetsemokList == null) { 
				    	%>
						<tr>
							<td class="fstleft" colspan="10">조회 내역이 없습니다.</td>
						</tr>
						<% 
				    } 
					  %>
          </table>
					<table class="btn">
						<tr>
							<td>
								<img src="../img/btn_delete2.gif" alt="삭제" align="absmiddle" onclick="goCancel()" style="cursor:hand">
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