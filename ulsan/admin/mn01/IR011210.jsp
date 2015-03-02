<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR011210.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2014-10-20
* 프로그램내용	   : 세입 > GIRO대사등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ page import = "java.text.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%  
String job_dt = request.getParameter("job_dt");
if (job_dt.equals("")) {
    job_dt  = TextHandler.formatDate(StringUtil.getDate().substring(0, 8),"yyyyMMdd","yyyy-MM-dd");
}

String SucMsg = (String)request.getAttribute("page.mn01.SucMsg");
if (SucMsg == null) {
    SucMsg = "";
}

CommonEntity giroData = (CommonEntity)request.getAttribute("page.mn01.giroData");

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
    	form.encoding = "application/x-www-form-urlencoded";
    	form.cmd.value = "giroDaesaSearch";
    	form.action = "IR011210.etax";
    	eSubmit(form);
    }
    
    
    function goExcel() {
        var form = document.sForm;
        form.encoding = "application/x-www-form-urlencoded";
        form.cmd.value = "giroDaesaSearch";
        form.action = "IR011211.etax";
        eSubmit(form);
    }
    
			
	function goUpload(){
        var form = document.sForm;
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
            alert("GIRO대사 파일을 첨부하세요.");
            return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "dat" && form.upfile.value.substring(idx1+1, idx2) != "DAT"){
            alert("DAT 파일만 업로드 가능합니다.");
            form.upfile.select();
            document.selection.clear();
            return;
        }
        if (confirm("첨부한 대사파일("+form.upfile.value+")과 작업일자("+form.job_dt.value+")와 일치합니까?")) {
        	prc_Win.style.visibility = "visible";
            form.action = "IR011210.etax?cmd=giroDaesaUpload&uploadDir=giro";
            eSubmit(form);
        }
    }
	
	
	function goDelete() {
		var form = document.sForm;
		if (confirm("작업일자("+form.job_dt.value+") 자료를 삭제합니까?")) {
			form.encoding = "application/x-www-form-urlencoded";
			form.cmd.value = "giroDaesaDelete";
			form.action = "IR011210.etax";
			form.target = "";
			eSubmit(form);
		}
	}

    </script>
  </head>
  <body oncontextmenu="return false">
  <div id="prc_Win" style="visibility:  hidden; z-index:2; filter: alpha(opacity=50); -khtml-opacity: 0.5; -moz-opacity:0.5; opacity: 0.5; background-color:#747474; position: absolute; left: 0px; top: 0px; width: 1000px; height: 600px; text-align: center;">
    <table width="100%" border="0" cellpadding="2" cellspacing="2">
        <tr height="300">
            <td valign="middle"><font color="white" size="13pt">GIRO대사를 등록중입니다. 기다려주세요.</font><br></td>
        </tr>         
    </table>
  </div>
  <form name="sForm" method="post" enctype="multipart/form-data"  action="IR011210.etax">
	<input type="hidden" name="cmd" value="giroDaesaUpload">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_40.gif"></td>
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
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center" height="30">
                  <tr> 
                    <td>
                      
                      <span style="width:5"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8"> 
					  작업일자<input type="text" value="<%=job_dt%>" name="job_dt" size="8" class="box3" userType="date">
					  <img src="../img/btn_calendar.gif" onclick="Calendar(this,'sForm','job_dt');" style="cursor:hand">
					  <span style="width:5"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8">
                      파일
                      <input type="file" name="upfile" value="<%=request.getParameter("upfile")%>" size="45">
                    </td>
                    <td align="right">
                      <img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:pointer">
                      <img src="../img/btn_order.gif" alt="등록" onClick="goUpload()" style="cursor:pointer">
                      <img src="../img/btn_delete.gif" alt="삭제" onClick="goDelete()" style="cursor:pointer">
                      <img src="../img/btn_excel.gif" alt="엑셀" onClick="goExcel()" style="cursor:pointer">
                      &nbsp;&nbsp;
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
			<% if (giroData != null ) { %>
			<tr>
			<td width="18">&nbsp;</td>
             <td>
                 <table width="975" border="0" cellspacing="0" cellpadding="0" class="list">
                    <tr> 
                        <th width="10%" rowspan="2">구분</th>
                            <th width="30%" colspan="2">일반수납</th>
                            <th width="30%" colspan="2">카드승인</th>
                            <th width="30%" colspan="3">합계</th>
                    </tr>
                    <tr>
                        <th width="10%">건수</th>
                        <th>금액</th>
                        <th width="10%">건수</th>
                        <th>금액</th>
                        <th width="10%">건수</th>
                        <th colspan="2">금액</th>
                    </tr>
                    <%
                    long TOT_CNT = 0L; //합계건수
                    long TOT_AMT = 0L; //합계금액
                    TOT_CNT = giroData.getLong("ILCNT")+giroData.getLong("CACNT")+giroData.getLong("REGI_ILCNT")+giroData.getLong("REGI_CACNT");
                    TOT_AMT = giroData.getLong("ILAMT")+giroData.getLong("CAAMT")+giroData.getLong("REGI_ILAMT")+giroData.getLong("REGI_CAAMT");
                    %>
                    <tr>
                        <td>울산광역시</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("ILAMT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAAMT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(TOT_CNT) %></td>
                        <td class="right" colspan="2"><%=TextHandler.formatNumber(TOT_AMT) %></td>
                    </tr>
                    <tr>
                        <td colspan="8" height="6"></td>
                    </tr>
                    <tr>
                        <th rowspan="2">수납구분</th>
                        <th colspan="2">일자</th>
                        <th colspan="5">수납내역(취득세)</th>
                    </tr>
                    <tr>
                        <th>회계/수납</th>
                        <th>자금이체</th>
                        <th colspan="3">세목</th>
                        <th>건수</th>
                        <th>세액</th>
                    </tr>
                    <%
                    TOT_CNT = giroData.getLong("ILCNT")+giroData.getLong("CACNT");
                    TOT_AMT = giroData.getLong("ILAMT")+giroData.getLong("CAAMT");
                    %>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td ><b>합계</b></td>
                        <td>&nbsp;</td>
                        <td class="right"><b><%=TextHandler.formatNumber(TOT_CNT) %></b></td>
                        <td class="right"><b><%=TextHandler.formatNumber(TOT_AMT) %></b></td>
                    </tr>
                    <tr>
                        <td rowspan="5" colspan="3">합계</td>
                        <td rowspan="2">취득세</td>
                        <td rowspan="2">(차량)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td>농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">취득세</td>
                        <td rowspan="3">(기계장비)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="6">일반이체</td>
                        <td rowspan="6"><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="6"><%=TextHandler.formatDate(giroData.getString("ILBAN_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>소계</td>
                        <td >&nbsp;</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="2">취득세</td>
                        <td rowspan="2">(차량)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">취득세</td>
                        <td rowspan="3">(기계장비)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="6">신용카드</td>
                        <td rowspan="6"><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="6"><%=TextHandler.formatDate(giroData.getString("CARD_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>소계</td>
                        <td >&nbsp;</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="2">취득세</td>
                        <td rowspan="2">(차량)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">취득세</td>
                        <td rowspan="3">(기계장비)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td colspan="8" height="6"></td>
                    </tr>
                    <tr>
                        <th rowspan="2">수납구분</th>
                        <th colspan="2">일자</th>
                        <th colspan="5">수납내역(등록세)</th>
                    </tr>
                    <tr>
                        <th>회계/수납</th>
                        <th>자금이체</th>
                        <th colspan="3">세목</th>
                        <th>건수</th>
                        <th>세액</th>
                    </tr>
                    <%
                    TOT_CNT = giroData.getLong("REGI_ILCNT")+giroData.getLong("REGI_CACNT");
                    TOT_AMT = giroData.getLong("REGI_ILAMT")+giroData.getLong("REGI_CAAMT");
                    %>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td ><b>합계</b></td>
                        <td>&nbsp;</td>
                        <td class="right"><b><%=TextHandler.formatNumber(TOT_CNT) %></b></td>
                        <td class="right"><b><%=TextHandler.formatNumber(TOT_AMT) %></b></td>
                    </tr>
                    <tr>
                        <td rowspan="6" colspan="3">합계</td>
                        <td rowspan="3">등록세</td>
                        <td rowspan="3">(차량)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td>교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_AMT")) %></td>
                    </tr>
                    <tr>
                        <td>농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">등록세</td>
                        <td rowspan="3">(기계장비)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_CNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="7">일반이체</td>
                        <td rowspan="7"><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="7"><%=TextHandler.formatDate(giroData.getString("ILBAN_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>소계</td>
                        <td >&nbsp;</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("REGI_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("REGI_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">등록세</td>
                        <td rowspan="3">(차량)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">등록세</td>
                        <td rowspan="3">(기계장비)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_ILCNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="7">신용카드</td>
                        <td rowspan="7"><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="7"><%=TextHandler.formatDate(giroData.getString("CARD_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>소계</td>
                        <td >&nbsp;</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("REGI_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("REGI_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">등록세</td>
                        <td rowspan="3">(차량)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">등록세</td>
                        <td rowspan="3">(기계장비)</td>
                        <td >본세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >교육세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >농특세</td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_CACNT")) %></td>
                        <td class="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_CAAMT")) %></td>
                    </tr>
                </table>
             </td>
             </tr>
             
             <% } %>
    </table>
    <p>&nbsp;</p>
    
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  <% if (!"".equals(SucMsg)) {%>
        <script type="text/javascript">
        prc_Win.style.visibility = "hidden";
        alert('<%=SucMsg%>');
        </script> 
  <% } %>
  </body>
</html>