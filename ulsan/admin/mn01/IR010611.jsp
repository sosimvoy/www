<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010610.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 세입 > 농협자료등록/취소
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%

  List<CommonEntity> nongHyupList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupList");  //광역시세세입일계표&&차량등록사업소

	List<CommonEntity> nongHyupGwaList  = (List<CommonEntity>)request.getAttribute("page.mn01.nongHyupGwaList");  //과오납환부일계표
  
	String data_type =  (String)request.getAttribute("page.mn01.data_type");
	String SucMsg = (String)request.getAttribute("page.mn01.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int nongHyupListSize = 0;
	if (nongHyupList != null ) {
		nongHyupListSize = nongHyupList.size();
	}
  String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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

			function goTmpUpload(){
        var form = document.sForm;
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("엑셀 파일을 첨부하세요.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("엑셀 파일만 업로드 가능합니다.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "Y";
				if (confirm("첨부한 엑셀파일과 자료구분과 일치합니까?")) {
					form.action = "IR010611.etax?cmd=nongHyupTmpUp&uploadDir=excel&flag=Y";
          eSubmit(form);
				}
      } 
			
			
			function goRegister(){
        var form = document.sForm;

				if (form.flag.value != "Y")	{
					alert("미리보기 후 엑셀파일을 등록하십시오.");
					return;
				}
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("엑셀 파일을 첨부하세요.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("엑셀 파일만 업로드 가능합니다.");
          form.upfile.select();
          document.selection.clear();
          return;
        }
				form.flag.value = "N";
				if (confirm("첨부한 엑셀파일과 자료구분과 일치합니까?")) {
					form.action = "IR010610.etax?cmd=nongHyupReg&uploadDir=excel&flag=N";
          eSubmit(form);
				}        
      }          
      
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
	<form name="dForm" method="post" action="IR010610.etax">
	<input type="hidden" name="cmd" value="nongHyupDel">
	<input type="hidden" name="fis_year">
	<input type="hidden" name="acc_date">
	<input type="hidden" name="data_type">
	<input type="hidden" name="trans_gubun" value="161">
	<input type="hidden" name="work_log" value="A01">
	</form>
  <form name="sForm" method="post" enctype="multipart/form-data"  action="IR010610.etax">
	<input type="hidden" name="cmd" value="nongHyupList">
	<input type="hidden" name="flag" value="<%=request.getParameter("flag")%>">
	<input type="hidden" name="trans_gubun" value="161">
	<input type="hidden" name="work_log" value="A01">
    <table width="950" border="0" cellspacing="0" cellpadding="0">
			<tr>
        <td width="18">&nbsp;</td>
        <td width="930" height="20"> &nbsp;</td>
			</tr>
			<% if ("G1".equals(data_type) ) { %>
			<tr>
        <td colspan="2"><span style="width:20"></span>광역시세세입일계표</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="930"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="15%">세목</th>
							<th width="15%">합계</th>
              <th width="14%">중구</th>
              <th width="14%">남구</th>
							<th width="14%">동구</th>
							<th width="14%">북구</th>
							<th width="14%">울주군</th>
            </tr>
						<% 
               long left_tot = 0L;
						   if (nongHyupListSize > 0 && nongHyupList != null) { 
							   for (int i=0; i < nongHyupListSize; i++) {
									 CommonEntity rowData = (CommonEntity)nongHyupList.get(i);
									 left_tot = rowData.getLong("M450_JUNGGU") + rowData.getLong("M450_NAMGU") + rowData.getLong("M450_DONGGU")
										 + rowData.getLong("M450_BUKGU") + rowData.getLong("M450_ULJU");
						%>
            <% if ("1999999".equals(rowData.getString("M450_SEMOK_CD")) || "2999999".equals(rowData.getString("M450_SEMOK_CD")) || "9999999".equals(rowData.getString("M450_SEMOK_CD")) )  { %>
						<tr>
              <td class="fstleft">&nbsp;<b><%=rowData.getString("M450_SEMOK_NM")%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(left_tot)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></b></td>
            </tr>
						<% } else { %>
            <tr>
              <td class="fstleft">&nbsp;<%=rowData.getString("M450_SEMOK_NM")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(left_tot)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></td>
            </tr>
            <% } %>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="7">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
			<%} else if ("G2".equals(data_type) || "G3".equals(data_type)) { %>
			<tr>
        <td colspan="2"><span style="width:20"></span>과오납환부일계표(<% if ("G2".equals(data_type)) { %> 과오납 <% } else if ("G3".equals(data_type) ) { %> 세입 <% } %>)</td>
			</tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="930"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" rowspan="2">세목</th>
							<th rowspan="2">합계</th>
              <th colspan="6">현년도</th>
              <th colspan="6">과년도</th>
						</tr>
						<tr>
						  <th>소계</th>
						  <th>중구</th>
							<th>남구</th>
							<th>동구</th>
							<th>북구</th>
							<th>울주군</th>
							<th>소계</th>
						  <th>중구</th>
							<th>남구</th>
							<th>동구</th>
							<th>북구</th>
							<th>울주군</th>
            </tr>
						<% 
							 long tot_row = 0L;   // 로우당 합계
							 long this_row = 0L;  //현년도 로우당 소계
							 long last_row = 0L;  //과년도 로우당 소계

							 long tot_amt = 0L; //총금액
							 long this_amt = 0L; //현년도 소계금액
							 long last_amt = 0L; //과년도 소계금액
							 long jung_amt = 0L;
							 long nam_amt = 0L;
							 long dong_amt = 0L;
							 long buk_amt = 0L;
							 long ulju_amt = 0L;
							 long jung_gwa_amt = 0L;
							 long nam_gwa_amt = 0L;
							 long dong_gwa_amt = 0L;
							 long buk_gwa_amt = 0L;
							 long ulju_gwa_amt = 0L;
						   if (nongHyupGwaList.size() > 0 && nongHyupGwaList != null) { 
							   for (int i=0; i < nongHyupGwaList.size(); i++) {
									 CommonEntity rowData = (CommonEntity)nongHyupGwaList.get(i);
									 this_row = rowData.getLong("M450_JUNGGU") + rowData.getLong("M450_NAMGU") + rowData.getLong("M450_DONGGU")
										 + rowData.getLong("M450_BUKGU") + rowData.getLong("M450_ULJU");
									 last_row = rowData.getLong("M450_JUNGGU_GWA") + rowData.getLong("M450_NAMGU_GWA") + rowData.getLong("M450_DONGGU_GWA")
										 + rowData.getLong("M450_BUKGU_GWA") + rowData.getLong("M450_ULJU_GWA");
									 tot_row = this_row + last_row;
						%>
						<tr>
              <td class="fstleft">&nbsp;<%=rowData.getString("M450_SEMOK_NM")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(tot_row)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(this_row)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(last_row)%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU_GWA"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_NAMGU_GWA"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_DONGGU_GWA"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_BUKGU_GWA"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M450_ULJU_GWA"))%></td>
            </tr>
						<%     if (!"1130100".equals(rowData.getString("M450_SEMOK_CD")) && !"2290100".equals(rowData.getString("M450_SEMOK_CD")) ) {
							       tot_amt += tot_row;
										 this_amt += this_row;
										 last_amt += last_row;
										 jung_amt += rowData.getLong("M450_JUNGGU");
										 nam_amt += rowData.getLong("M450_NAMGU");
										 dong_amt += rowData.getLong("M450_DONGGU");
										 buk_amt += rowData.getLong("M450_BUKGU");
										 ulju_amt += rowData.getLong("M450_ULJU");
										 jung_gwa_amt += rowData.getLong("M450_JUNGGU_GWA");
										 nam_gwa_amt += rowData.getLong("M450_NAMGU_GWA");
										 dong_gwa_amt += rowData.getLong("M450_DONGGU_GWA");
										 buk_gwa_amt += rowData.getLong("M450_BUKGU_GWA");
										 ulju_gwa_amt += rowData.getLong("M450_ULJU_GWA");
						       } %>

							     
						<%   }
						%>
             <tr>
              <td class="fstleft">&nbsp;<b>합 계</b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(tot_amt)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(this_amt)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(jung_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(nam_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(dong_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(buk_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(ulju_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(last_amt)%></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(jung_gwa_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(nam_gwa_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(dong_gwa_amt)%></b></td>
              <td class="right">&nbsp;<b><%=TextHandler.formatNumber(buk_gwa_amt)%></b></td>
							<td class="right">&nbsp;<b><%=TextHandler.formatNumber(ulju_gwa_amt)%></b></td>
            </tr>
		         <%  } else { %>
						<tr> 
              <td class="fstleft" colspan="14">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
			<% } else if ("G4".equals(data_type)) { %>
      <tr>
        <td colspan="2"><span style="width:20"></span>차량등록사업소 세입일계표</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="930"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th rowspan="2" colspan="2" class="fstleft" width="15%">세목</th>
              <th colspan="2" width="17%">중구</th>
              <th colspan="2" width="17%">남구</th>
							<th colspan="2" width="17%">동구</th>
							<th colspan="2" width="17%">북구</th>
							<th colspan="2" width="17%">울주군</th>
            </tr>
            <tr> 
              <th>건수</th>
              <th>금액</th>
              <th>건수</th>
              <th>금액</th>
              <th>건수</th>
              <th>금액</th>
              <th>건수</th>
              <th>금액</th>
              <th>건수</th>
              <th>금액</th>
            </tr>
            <% for (int i=0; nongHyupList != null && i<nongHyupList.size(); i++) { 
                 CommonEntity rowData = (CommonEntity)nongHyupList.get(i);
            %>
            <tr>
              <% if (i == 0 || i == 3) { %>
              <td class="fstleft" rowspan="3">
              <% if (nongHyupList.size() == 6 && i == 0) { %>
              취득세
              <% } else if ((i == 3 && nongHyupList.size() == 6) || (i == 0 && nongHyupList.size() == 3)) { %>
              등록세
              <% } %>
              </td>
              <% } %>
              <td class="left"><%=rowData.getString("M450_SEMOK_NM")%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_JUNGGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_NAMGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_NAMGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_DONGGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_DONGGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_BUKGU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_BUKGU"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_ULJU_GWA"))%></td>
              <td class="right"><%=TextHandler.formatNumber(rowData.getString("M450_ULJU"))%></td>
            </tr>
            <% } %>
          </table>
        </td>
      </tr>
      <% } %>
    </table>
	  </form>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>
