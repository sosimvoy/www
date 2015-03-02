<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR060710.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금예탁(기금,특별회계) 취소
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn06.acctList");
  List<CommonEntity> partList = (List<CommonEntity>)request.getAttribute("page.mn06.partList");

  List<CommonEntity> bankSpRegList = (List<CommonEntity>)request.getAttribute("page.mn06.bankSpRegList");
	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int bankSpRegListSize = 0;
	if (bankSpRegList != null ) {
		bankSpRegListSize = bankSpRegList.size();
	}
  String new_date = request.getParameter("new_date");
	if ("".equals(new_date) ) {
    new_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
      function init() {
  	    typeInitialize();
      }

      function changeAcc(acct_kind) {
				var form = document.sForm;
				document.hidFrame.location = "IR060711.etax?cmd=bankSpecialList&fis_year="+form.fis_year.value+"&acct_kind="+acct_kind;
			}


      function changePart(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR060711.etax?cmd=bankSpecialList&fis_year="+form.fis_year.value+"&acct_kind="+form.acct_kind.value+"&part_code="+part_code;
			}
     
      function goSearch() {
				var form = document.sForm;
        if (form.part_code.value == "" || form.part_code.value == null) {
          alert("부서를 선택하세요.");
          return;
        }

				form.action = "IR060710.etax";
				form.cmd.value = "bankSpRegList";
      	eSubmit(form);
      }

			function goCancel() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=bankSpRegListSize%>;

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("취소할 건을 선택하세요.");
						return;
					}
				} else {
					for (var i=0; i<form.allotChk.length; i++) {						
						if (form.allotChk[i].checked)	{
							cnt++;
						}
					}

					if (cnt ==0) {
						alert("취소할 건을 선택하세요.");
						return;
					}
				}
        
        if (confirm("선택 건(들)을 취소처리하겠습니까?")) {
          form.cmd.value = "bankSpCancel";
				  form.action = "IR060710.etax";
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
  <form name="sForm" method="post" action="IR060710.etax">
	<input type="hidden" name="cmd" value="bankSpRegList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_10_n.gif"></td>
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
                    <td width="880">
                      <span style="width:20"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도
											<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										  <span style="width:20"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계구분
											<select name="acct_kind" iValue="<%=request.getParameter("acct_kind")%>" onchange="changeAcc(this.value)" style="width:110">
												<option value="B">특별회계</option>
                        <option value="D">세입세출외현금</option>
												<option value="E">기금</option>
											</select><span style="width:20"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                      부서
                      <select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changePart(this.value)" style="width:180">
                      <%for (int i=0; partList != null && i<partList.size(); i++) {
                        CommonEntity partInfo = (CommonEntity)partList.get(i); %>
												<option value="<%=partInfo.getString("M350_PARTCODE")%>"><%=partInfo.getString("M350_PARTNAME")%></option>
                      <% } %>
											</select><span style="width:20"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											회계명
											<select name="acct_list" iValue="<%=request.getParameter("acct_list")%>" style="width:200">
                      <option value="">전체선택</option>
											<% for(int i=0; acctList != null && i < acctList.size(); i++ ) { 
												   CommonEntity acctInfo = (CommonEntity)acctList.get(i);
											%>
											  <option value="<%=acctInfo.getString("M360_ACCCODE")%>"><%=acctInfo.getString("M360_ACCNAME")%></option>
											<% } %>	
											</select>
                      <br><br>
                      <span style="width:20"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    신규일
											<input type="text" class="box3" size="10" name="new_date" value="<%=new_date%>" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','new_date')" align="absmiddle">
										</td>
                    <td width="80"> 
                    <br><br>
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
              <th class="fstleft" width="4%">선택<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="5%">회계<br>연도</th>
							<th width="6%">회계<br>구분</th>
							<th width="15%">회계명</th>
							<th width="6%">예금<br>종류</th>
							<th width="6%">자금<br>종별</th>
              <th width="12%">계좌번호</th>
              <th width="7%">좌수<br>번호</th>
							<th width="13%">금액</th>
							<th width="6%">예탁<br>기간</th>
							<th width="4%">이율(%)</th>
							<th width="8%">신규일</th>
							<th width="8%">만기일</th>
            </tr>
						<% long tot_amt = 0L;
               if (bankSpRegListSize > 0 && bankSpRegList != null) { 
							   String due_date = "";
							   for (int i=0; i < bankSpRegListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankSpRegList.get(i);
									 String state = rowData.getString("DEPOSIT_TYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("DEP_PERIOD") + " 개월";
									 } else if ("".equals(rowData.getString("DEP_PERIOD")) ) {
										 due_date = "";
									 } else {
                     due_date = rowData.getString("DEP_PERIOD") + " 일";
                   }
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
              <td class="center">&nbsp;<%=rowData.getString("FIS_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("TYPE_NAME")%></td>
              <td class="center">&nbsp;<%=rowData.getString("ACC_NAME")%></td>
              <td class="center">&nbsp;<%=rowData.getString("DEPOSIT_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("MMDA_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("ACCT_NO"))%></td>
							<td class="right">&nbsp;<%=rowData.getString("JWASU_NO")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("DEP_AMT"))%></td>
							<td class="right">&nbsp;<%=due_date%></td>
              <td class="right">&nbsp;<%=rowData.getString("DEP_RATE")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("NEW_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("END_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<input type="hidden" name="dep_gubun" value="<%=rowData.getString("DEPOSIT_TYPE")%>">
              <input type="hidden" name="acc_code" value="<%=rowData.getString("ACC_CODE")%>">
							<input type="hidden" name="seq_list" value="<%=rowData.getString("SEQ")%>">
              <input type="hidden" name="m120_seq" value="<%=rowData.getString("M120SEQ")%>">
							<input type="hidden" name="inamt" value="<%=rowData.getString("DEP_AMT")%>">
							<input type="hidden" name="due_day" value="<%=rowData.getString("DEP_PERIOD")%>">
							<input type="hidden" name="acc_type" value="<%=rowData.getString("ACC_TYPE")%>">
							<input type="hidden" name="acct_no" value="<%=rowData.getString("ACCT_NO")%>">
							<input type="hidden" name="jwasu_no" value="<%=rowData.getString("JWASU_NO")%>">
            </tr>
						<%     tot_amt += rowData.getLong("DEP_AMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="6">합계</td>
              <td class="right" colspan="7"><%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
		        <% } else { %>
						<tr> 
              <td class="fstleft" colspan="13">&nbsp;</td>
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
              <td><img src="../img/btn_cancel.gif" alt="취소" style="cursor:hand" onClick="goCancel()" align="absmiddle"></td>
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