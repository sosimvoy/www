<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050411.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금배정통지서조회-통지서팝업
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity srpRepInfo  = (CommonEntity)request.getAttribute("page.mn05.srpRepInfo");
	CommonEntity srpRepMan  = (CommonEntity)request.getAttribute("page.mn05.srpRepMan");
  CommonEntity sealInfo  = (CommonEntity)request.getAttribute("page.mn05.sealInfo");

	int srpRepInfoSize = 0;
	if (srpRepInfo != null ) {
		srpRepInfoSize = srpRepInfo.size();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 12pt; color: #333333}
.num   {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.line  {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333; text-decoration: underline}

@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
	function goPrint() {
           factory.printing.header = ""; // Header에 들어갈 문장
                factory.printing.footer = ""; // Footer에 들어갈 문장
                factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄
                factory.printing.leftMargin = 2.0; // 왼쪽 여백 사이즈
                factory.printing.topMargin = 2.0; // 위 여백 사이즈
                factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈
                factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈
                factory.printing.Print(true) // 출력하기	
        }
</script>
</head>
<body bgcolor="#FFFFFF" topmargin="10" leftmargin="10">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/sm
sx.cab#Version=6,5,439,50"></object>
<%
if (srpRepInfo != null) { %>
<form name="sForm" method="post" action="IR050411.etax">
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td colspan="7" height="21">
      <table width="711" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
        <tr> 
          <td colspan="7" height="21"> 
            <table width="690" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="60"> 
                  <div align="center"><b><font size="6">울 산 광 역 시</font></b></div>
                </td>
              </tr>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="6" class="basic" align="center">
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225">&nbsp;</td>
              </tr>
              <tr> 
                <td width="484">문서번호 : <%=srpRepInfo.getString("M310_DOCUMENTNO")%></td>
                <td width="225"> 
                  <div align="right">시행일 : <%=TextHandler.formatDate(srpRepInfo.getString("M310_DATE"), "yyyyMMdd", "yyyy. MM. dd")%>&nbsp;&nbsp;&nbsp;</div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="left"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">수신 : 경남은행울산시청지점장</td>
                <td width="225"> 
                  <div align="left">&nbsp;</div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">제목 : <%=srpRepInfo.getString("M310_YEAROVER")%>년 일반회계 세계잉여금의 결산 전 <%=srpRepInfo.getString("M310_YEARINTO")%>년도 이입</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"><hr color="black"></hr></td>
              </tr>
              <tr> 
                <td colspan="2" height="50">
                  <p>&nbsp;</p>
                  <p>&nbsp;</p>
                  <div align="left"><span style="width:33"></span>지방재정법 시행령 제61조의 규정에 의거 <%=srpRepInfo.getString("M310_YEAROVER")%>년도 일반회계 세계잉여금의 결산 전에 <br><span style="width:33"></span>다음 금액을 <%=srpRepInfo.getString("M310_YEARINTO")%>년도의 세입에 이입하고자 합니다.</div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"> 
                  <div align="center"></div>
                </td>
              </tr>
              <tr>
                <td colspan="2"> 
                  <div align="left"><span style="width:33"></span><b>
									1. 일자 : <%=TextHandler.formatDate(srpRepInfo.getString("M310_DATE"), "yyyyMMdd", "yyyy. MM. dd")%> <br>
									<span style="width:33"></span>
									2. 금액 : 금 <%=TextHandler.formatNumber(srpRepInfo.getString("M310_INTOTAMT"))%>원 (금<%=srpRepInfo.getString("HAN_AMT")%>) 끝.</b> </div>
                </td>
              </tr>
            </table>
            <p>&nbsp;</p>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
              <tr> 
                <td height="60" colspan="3"> 
                  <p>&nbsp;</p>
                  <p>&nbsp;</p>
                  <div align="center"><br><font size="4"><b>울산광역시장 </b></font>
                  <% if ("Y".equals(srpRepMan.getString("M180_SIGNFINISHSTATE")) ) { %>
                  <img src="/report/seal/<%=sealInfo.getString("M340_FNAME")%>" width="60" height="60" align="absmiddle">
                  <% } %>
                  <br></div>
                </td>
              </tr>
							<% if (srpRepMan != null) { %>
              <tr> 
                <td height="25" width="210">
                  <p>&nbsp;</p>
                  <div align="left">주무관 : <%=srpRepMan.getString("M180_TASKNAME")%> </div>
                </td>
                <td height="25" width="312"> 
                  <p>&nbsp;</p>
                  세입관리담당사무관 : <%=srpRepMan.getString("M180_MIDDLENAME")%> </td>
                <td height="30" width="162"> 
                  <p>&nbsp;</p>
                  세정담당관 : <%=srpRepMan.getString("M180_UPPERNAME")%></td>
              </tr>
							<% } %>
            </table>
						
          <p>&nbsp;</p></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
<% } %>
<br>
<div class="noprint">
<div align="right">
<img src="../img/btn_print.gif" alt="인쇄" onClick="goPrint()" style="cursor:hand">
<img src="../img/btn_close.gif" alt="닫기" onClick="self.close()" style="cursor:hand"><span style="width:30"></span>
</div>
</div>
</body>
</html>
