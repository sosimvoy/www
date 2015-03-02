<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040511.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-09
* 프로그램내용	   : 세외수입 > 과오납송금반환 청구서
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn04.jsp" %>

<%
	CommonEntity gwaonapView = (CommonEntity)request.getAttribute("page.mn04.gwaonapView");
  CommonEntity sealInfo = (CommonEntity)request.getAttribute("page.mn04.sealInfo");
	String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
  int listSize = 0;
	if (gwaonapView != null && gwaonapView.size() > 0) {
		listSize = gwaonapView.size();
	}
%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function init() {
		typeInitialize();
	}

  function goWrite() {
    var form = document.sForm;
    if (form.doc_state.value == "R2") {
      alert("이미 결과통보된 문서입니다.");
      return;
    }
    form.action = "IR040511.etax";
    form.cmd.value = "rstSend";
    eSubmit(form);
  }
function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 2.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true); // 출력하기 
}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="0" leftmargin="50">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
  <form name="sForm" method="post" action="IR040511.etax">
  <input type="hidden" name="cmd" value="gwaonapView">
	<% if (gwaonapView != null && gwaonapView.size() > 0) {%>
  <input type="hidden" name="seq" value="<%=gwaonapView.getString("M090_SEQ")%>">
  <input type="hidden" name="doc_state" value="<%=gwaonapView.getString("M090_GWAONAPSTATECODE")%>">
  
<table width="370" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="356"> 
      <table width="349" border="0" cellspacing="0" cellpadding="1">
        <tr> 
          <td height="34"> 
            <div align="center"><b>과오납금송금반환통지</b></div>
          </td>
        </tr>
      </table>
      <table width="340" border="1" cellspacing="0" cellpadding="3" bordercolor="#000000" class="basic">
        <tr> 
          <td width="96"> 
            <div align="center">제<%=gwaonapView.getString("M090_DOCUMENTNO")%>호</div>
          </td>
          <td colspan="2" align="right"><%=gwaonapView.getString("M090_YEAR")%>년도<br>일반회계(시도제외) 회계</td>
        </tr>
        <tr> 
          <td width="96"> 
            <div align="center">채주</div>
          </td>
          <td colspan="2">&nbsp;<%=gwaonapView.getString("M090_ACCOUNTHOLDER")%></td>
        </tr>
        <tr> 
          <td width="96"> 
            <div align="center">과목</div>
          </td>
          <td colspan="2"><%=gwaonapView.getString("M090_MOK")%></td>
        </tr>
        <tr> 
          <td colspan="3">
            <table width="340" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="60"> 
                  <table width="330" border="1" cellspacing="0" cellpadding="3" bordercolor="#000000" class="basic" align="center">
                    <tr> 
                      <td width="100"> 
                        <div align="center">환부금액</div>
                      </td>
                      <td width="100"> 
                        <div align="center">환부이자</div>
                      </td>
                      <td width="114"> 
                        <div align="center">지급금액</div>
                      </td>
                    </tr>
                    <%
                      long jigup_amt = gwaonapView.getLong("M090_BONAMT") + gwaonapView.getLong("M090_INTERESTAMT");
                    %>
                    <tr> 
                      <td width="100"> 
                        <div align="right"><%=TextHandler.formatNumber(gwaonapView.getString("M090_BONAMT"))%></div>
                      </td>
                      <td width="62"> 
                        <div align="right"><%=TextHandler.formatNumber(gwaonapView.getString("M090_INTERESTAMT"))%></div>
                      </td>
                      <td width="114"> 
                        <div align="right"><%=TextHandler.formatNumber(jigup_amt)%></div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="80"> 
                  <table width="302" border="0" cellspacing="0" cellpadding="3" bordercolor="#000000" class="basic" align="center">
                    <tr> 
                      <td width="302"> 
                        <div align="right"></div>
                      </td>
                    </tr>
                    <tr> 
                      <td width="302"> 
                        <div align="center">위금액을 채주에게 송금 반환환하시기 바랍니다.</div>
												<br>
												 <div align="right"><%=TextHandler.formatDate(gwaonapView.getString("M090_RETURNORDERDATE"), "yyyyMMdd", "yyyy년 MM월 dd일")%><span style="width:20"></span></div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="120"> 
                  <table width="302" border="1" cellspacing="0" cellpadding="3" bordercolor="#000000" class="basic" align="center">
                    <tr> 
                      <td width="290" colspan="3"> 
                        <div align="center"> 
                          <table width="272" border="0" cellspacing="0" cellpadding="3" bordercolor="#000000" class="basic">
                            <tr> 
                              <td width="74"> 
                                <div align="left">은행명</div>
                              </td>
                              <td width="186"> 
                                <div align="left">&nbsp;</div>
                              </td>
                            </tr>
                            <tr> 
                              <td width="74"> 
                                <div align="left">예금주</div>
                              </td>
                              <td width="186"> 
                                <div align="left"><%=gwaonapView.getString("M090_ACCOUNTHOLDER")%></div>
                              </td>
                            </tr>
                            <tr> 
                              <td width="74"> 
                                <div align="left">계좌번호</div>
                              </td>
                              <td width="186"> 
                                <div align="left">반납고지서에 의함</div>
                              </td>
                            </tr>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table>
                    <tr>
                      <td width="240">
                        징수관성명   <b>세정과장</b> 
                        <br> <br> <br>
                      </td>
                      <td align="right">
                        <% if (sealInfo != null) { %> 
	                      <img src="/report/seal/<%=sealInfo.getString("M340_FNAME")%>" height="60" width="60" align="absmiddle">
	                      <% } %> 
                        <br>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <font size="3">울산광역시금고</font> 
                      </td>
                      <td> 귀하</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>      
      </table>
    </td>
  </tr>
</table>
<br><br>

	<div class="noprint">
<table width="350">
  <tr>
    <td  align="right">
    <% 
    if (!"R2".equals(gwaonapView.getString("M090_GWAONAPSTATECODE"))) {
    %>
		<img src="../img/btn_inform.gif" alt="결과통지" align="absmiddle" onclick="goWrite()" style="cursor:hand">
    <%
    }
    %>
    <img src="../img/btn_print.gif" alt="인쇄" align="absmiddle" onclick="javascript:self.goPrint();" style="cursor:hand">
		<img src="../img/btn_close2.gif" alt="닫기" align="absmiddle" onclick="javascript:self.close();" style="cursor:hand">
    </td>
  </tr>
</table>
	</div>
<% } %>
<p>&nbsp;</p>
</body>
</html>
