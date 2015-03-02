<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061510.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금운용현황 조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> moneyRunList  = (List<CommonEntity>)request.getAttribute("page.mn06.moneyRunList");

	List<CommonEntity> accList  = (List<CommonEntity>)request.getAttribute("page.mn06.accList");

  CommonEntity total = (CommonEntity)request.getAttribute("page.mn06.tot");	//총 잔액 및 건수

	Integer totalCount = (Integer)request.getAttribute("page.mn06.count");	//총 카운트
	if (totalCount == null ) {
	  totalCount = 0;
	}
	String cpage = request.getParameter("cpage");	// 현재 페이지 번호 받기
	if( "".equals(cpage) ) {
	  cpage = "1";
	}

	int moneyRunListSize = 0;
	if (moneyRunList != null ) {
		moneyRunListSize = moneyRunList.size();
	}

	String fixed_date = request.getParameter("fixed_date");
  if ("".equals(fixed_date)) {
    fixed_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String start_date = request.getParameter("start_date");
  if ("".equals(start_date)) {
    start_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String end_date = request.getParameter("end_date");
  if ("".equals(end_date)) {
    end_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int this_int = Integer.parseInt(this_year);

  String due_day = "";

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
     
      function goSearch(cpage) {
				var form = document.sForm;
				if(cpage != null) {
      		form.cpage.value=cpage;
      	} else {
      		form.cpage.value="";
      	}
				form.action = "IR061510.etax";
				form.target = "_self";
				form.cmd.value = "moneyStatList";
				form.flag.value = "S";
      	eSubmit(form);
      }

			function PAGE(cpage) {
      	goSearch(cpage);
      }

			function goExcel() {
				var form = document.sForm;
        form.flag.value = "E";
				form.action = "IR061512.etax";
				form.cmd.value = "moneyExcelList";
      	eSubmit(form);
			}

			function changeAcc(year){
				var form = document.sForm;
				document.hidFrame.location = "IR061511.etax?cmd=moneyStatList&fis_year="+year+"&flag=H";
			}

			function goSort(sort, cpage) {
        var form = document.sForm;
				if(cpage != null) {
      		form.cpage.value=cpage;
      	} else {
      		form.cpage.value="";
      	}

				form.sort_list.value = sort;
				form.action = "IR061510.etax";
				form.target = "_self";
				form.cmd.value = "moneyStatList";
				form.flag.value = "S";
      	eSubmit(form);
      }

			function goPrint(){
				var form = document.sForm;
				form.action = "IR061512.etax";
				form.target = "hidFrame";
				form.cmd.value = "moneyExcelList";
				form.flag.value = "P";
				frames['hidFrame'].focus(); 
      	eSubmit(form);
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR061510.etax">
	<input type="hidden" name="cmd" value="moneyStatList">
	<input type="hidden" name="sort_list">
	<input type="hidden" name="flag" value="">
	<input type="hidden" name="cpage" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_24.gif"></td>
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
									    조회구분
											<select name="src_gubun" iValue="<%=request.getParameter("src_gubun")%>">
											  <option value="A">잔액명세</option>
												<option value="B">기간중 해지</option>
												<option value="C">기간중 신규</option>
                        <option value="D">자금운용내역장</option>
											</select>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도
										  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>" onchange="changeAcc(this.value)">
										  <% for (int i=this_int; i > this_int - 5; i--) { %>
												<option value="<%=i%>"><%=i%></option>
										  <% } %>
											</select>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계명
										  <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>">
											  <option value="">전체선택</option>
											<% for (int i=0; accList != null && i < accList.size(); i++) { 
												 CommonEntity accInfo = (CommonEntity)accList.get(i); %>
												<option value="<%=accInfo.getString("M360_VALUE")%>"><%=accInfo.getString("M360_ACCNAME")%></option>
											<% } %>
											</select>
											<br><br>
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											잔액명세 조회대상 기준일
										  <input type="text" class="box3" size="8" name="fixed_date" value="<%=fixed_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fixed_date');" style="cursor:hand">
                      <span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											mmda해지
                      <select name="mmda_cancel" iValue="<%=request.getParameter("mmda_cancel")%>">
                        <option value="N">N</option>
                        <option value="Y">Y</option>
                      </select>
											<br><br>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											신규/해지/MMDA/융자금 명세 조회 기간
										  <input type="text" class="box3" size="8" name="start_date" value="<%=start_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','start_date');" style="cursor:hand"> -
											<input type="text" class="box3" size="8" name="end_date" value="<%=end_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','end_date');" style="cursor:hand">
                      <span style="width:10"></span>

                      <% if ("A".equals(request.getParameter("src_gubun"))) {
                           if (total != null) { %>
                           <b>전체 건수</b> :  <%=TextHandler.formatNumber(total.getString("CNT"))%><span style="width:10"></span>
                           <b>전체 잔액</b> :  <%=TextHandler.formatNumber(total.getString("TOT_AMT"))%>
                      <%   }
                         } %>

										</td>
                    <td width="100" valign="bottom">
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
				  <% if ("A".equals(request.getParameter("src_gubun")) || "B".equals(request.getParameter("src_gubun"))
					    || "C".equals(request.getParameter("src_gubun")) ) { %>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
						  <th class="fstleft">회계명</th>
              <th onClick="goSort('new_date')"; style="cursor:hand">신규일</th>
              <th>예금종류</th>
							<th>계좌번호</th>
							<th>좌수번호</th>
							<% if ("B".equals(request.getParameter("src_gubun"))) { %>
							<th>해지금액</th>
              <% } else { %>
              <th>신규금액</th>
              <% } %>
							<th onClick="goSort('end_date')"; style="cursor:hand">만기일</th>
							<th>예치일수</th>
							<th>이율</th>
							<th onClick="goSort('cancel_date')"; style="cursor:hand">해지일</th>
							<th>해지이자</th>
							<th>현재잔액</th>
            </tr>
						<% if (moneyRunListSize > 0 && moneyRunList != null) { 
							   for (int i=0; i < moneyRunListSize; i++) {
									 CommonEntity rowData = (CommonEntity)moneyRunList.get(i);
                   if ("G1".equals(rowData.getString("DEPOSIT_TYPE")) ) { 
                     due_day = "개월";
                   } else if ("".equals(rowData.getString("DEPOSIT_DATE")) ) {
                     due_day = "";
                   } else {
                     due_day = "일";
                   }
						%>
            <tr>
						  <td class="fstleft">&nbsp;<%=rowData.getString("ACC_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("SINKYU_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=rowData.getString("TYPE_NAME")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("ACCOUNTNO"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("JWASUNO")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("DEPOSIT_AMT"))%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("MANGI_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td class="right">&nbsp;<%=rowData.getString("DEPOSIT_DATE")%><%=due_day%></td>
							<td class="right">&nbsp;<%=rowData.getString("RATE")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("CANCEL_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("IJA"))%></td>
              <% if ("A".equals(request.getParameter("src_gubun"))) { %>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("JAN_AMT"))%></td>
              <% } else { %>
              <td class="right">&nbsp;</td>
              <% } %>
            </tr>
						<%   }
						%>
		        <% }else { %>
						<tr> 
              <td class="fstleft" colspan="12">&nbsp;</td>
            </tr>
						<% } %>
          </table>
					<%// 페이지 %>
          <table width="100%">
            <tr>
              <td align="center">
              <Br>
              <etax:pagelink totalCnt='<%=totalCount.intValue()%>' page='<%=cpage%>'	pageCnt='20' blockCnt='10'  />
              </td>
            </tr>
          </table>
					<%  } else if ("D".equals(request.getParameter("src_gubun")) ) { %>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" rowspan="2" width="8%">거래일</th>
              <th rowspan="2" width="5%">적요</th>
							<th rowspan="2" width="5%">종별</th>
							<th colspan="2" width="30%">지불</th>
							<th rowspan="2" width="5%">종별</th>
							<th colspan="2" width="30%">환입</th>
							<th rowspan="2" width="17%">잔액</th>
            </tr>
						<tr>
						  <th>정기예금등</th>
							<th>계(A)</th>
							<th>정기예금등</th>
							<th>계(B)</th>
						</tr>
						<% if (moneyRunListSize > 0 && moneyRunList != null) { 
							 long tot_janamt = 0L;
               long gye1 = 0L;
               long gye2 = 0L;
               long tot_amt = 0L;
							   for (int i=0; i < moneyRunListSize; i++) {
									 CommonEntity rowData = (CommonEntity)moneyRunList.get(i);
									 tot_janamt = rowData.getLong("YETAK_AMT") - rowData.getLong("INCHUL_AMT");
                   
						%>
            <% if (i == 0) {
              gye1 = rowData.getLong("YETAK_AMT");
              gye2 = rowData.getLong("INCHUL_AMT");
              tot_amt = tot_janamt;
            %>
            <tr>
              <td class="fstleft">&nbsp;<%=TextHandler.formatDate(rowData.getString("ACC_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=rowData.getString("JUKYO")%></td>
							<td class="center">&nbsp;<%=rowData.getString("YETAK_NAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("YETAK_AMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("YETAK_AMT"))%></td>
							<td class="center">&nbsp;<%=rowData.getString("INCHUL_NAME")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("INCHUL_AMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("INCHUL_AMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(tot_janamt)%></td>
            </tr>
            <% } else { 
              gye1 = gye1+rowData.getLong("YETAK_AMT");
              gye2 = gye2+rowData.getLong("INCHUL_AMT");
              tot_amt = tot_amt + rowData.getLong("YETAK_AMT") - rowData.getLong("INCHUL_AMT");
            %>
            <tr>
              <td class="fstleft">&nbsp;<%=TextHandler.formatDate(rowData.getString("ACC_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=rowData.getString("JUKYO")%></td>
							<td class="center">&nbsp;<%=rowData.getString("YETAK_NAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("YETAK_AMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(gye1)%></td>
							<td class="center">&nbsp;<%=rowData.getString("INCHUL_NAME")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("INCHUL_AMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(gye2)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
            
						<%   }
               }
						%>
		        <% }else { %>
						<tr> 
              <td class="fstleft" colspan="9">&nbsp;</td>
            </tr>
						<% } %>
          </table>
					<% } %>
        </td>
      </tr>	
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_excel.gif" alt="엑셀" style="cursor:hand" onClick="goExcel()" align="absmiddle">
								<img src="../img/btn_print.gif" alt="인쇄" style="cursor:hand" onClick="goPrint()" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
		<iframe name="hidFrame" width="0" height="0"></iframe>
  </form>
  </body>
</html>