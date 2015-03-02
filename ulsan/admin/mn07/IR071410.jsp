<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR071410.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-06-15
* 프로그램내용	   : 자금운용 > 자금예탁(기금,특별)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
  
	List<CommonEntity> revCdList = (List<CommonEntity>)request.getAttribute("page.mn07.revCdList");
  List<CommonEntity> partCdList = (List<CommonEntity>)request.getAttribute("page.mn07.partCdList");
  List<CommonEntity> accCdList = (List<CommonEntity>)request.getAttribute("page.mn07.accCdList");
  List<CommonEntity> semokCdList = (List<CommonEntity>)request.getAttribute("page.mn07.semokCdList");
  String SucMsg = (String)request.getAttribute("page.mn07.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");

	String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
	
%>
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../js/calendar.js"></script>
  	<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }
      
      function changeTaxType(tax_type) {
        var form = document.sForm;
        document.hidFrame.location = "IR071411.etax?cmd=dailyRewriteList&gubun=A&tax_type="+tax_type+"&acc_year="+form.acc_year.value+"&acc_type="+form.acc_type.value;
      }
/*
      function changeYearGubun1(tax_type) {
        var form = document.sForm;
        document.hidFrame.location = "IR071411.etax?gubun=E&tax_type="+tax_type+"&acc_type="+form.acc_type.value;
      }
*/
      function changeAccType(acc_type) {
        var form = document.sForm;
        document.hidFrame.location = "IR071411.etax?cmd=dailyRewriteList&gubun=B&acc_year="+form.acc_year.value+"&tax_type="+form.tax_type.value+"&acc_type="+acc_type;
      }

      function changeYearGubun(acc_type) {
        var form = document.sForm;
        document.hidFrame.location = "IR071411.etax?gubun=E&tax_type="+form.tax_type.value+"&acc_type="+acc_type;
      }

      function changePartCode(part_code) {
        var form = document.sForm;
        document.hidFrame.location = "IR071411.etax?cmd=dailyRewriteList&gubun=C&acc_year="+form.acc_year.value+"&tax_type="+form.tax_type.value+"&acc_type="+form.acc_type.value+"&part_code="+part_code;
      }


      function changeAccCode(acc_code) {
        var form = document.sForm;
        document.hidFrame.location = "IR071411.etax?cmd=dailyRewriteList&gubun=D&acc_year="+form.acc_year.value+"&tax_type="+form.tax_type.value+"&acc_type="+form.acc_type.value+"&part_code="+form.part_code.value+"&acc_code="+acc_code;
      }


      function goRegister() {
				var form = document.sForm;
				var temp_amt;
        temp_amt = parseInt(form.amt.value.replace(/,/g, ""), 10);
        if (temp_amt < 0) {
          alert("마이너스 금액은 입력할 수 없습니다.");
          form.amt.focus();
          return;
        }

        if (form.amt.value == "" || form.amt.value == "0") {
          alert("금액을 입력하세요.");
          form.amt.focus();
          return;
        }
				if (confirm(form.acc_date.value+"일자 세입세출일계정정 작업을 하시겠습니까?")) {
					form.cmd.value = "dailyRewrite";
				  form.action = "IR071410.etax";
				  eSubmit(form);
				}
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR071410.etax">
	<input type="hidden" name="cmd" value="dailyRewriteList">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="169">
  <input type="hidden" name="gubun" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title7_16.gif"></td>
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
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td width="20%">&nbsp;</td>
									  <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도<span style="width:35"></span>
											<select name="acc_year" iValue="<%=request.getParameter("acc_year")%>" class="box3">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
                    <td width="40%"> 
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계일자<span style="width:10"></span>
											<input type="text" name="acc_date" value="<%=acc_date%>" size="7" class="box3" userType="date">
                      <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
											</select>
                    </td>
									</tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  <tr>
									  <td width="20%">&nbsp;</td>
                    <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    세입세출구분<span style="width:10"></span>
											<select name="tax_type" iValue="<%=request.getParameter("tax_type")%>"  class="box3" onchange="changeTaxType(this.value);">
												<option value="T1">세입</option>
                        <option value="T2">세출</option>
											</select>
										</td>
                    <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    수납기관<span style="width:10"></span>
											<select name="rev_cd" iValue="<%=request.getParameter("rev_cd")%>" class="box3">
											<% for(int i=0; revCdList != null && i < revCdList.size(); i++ ) { 
												   CommonEntity revInfo = (CommonEntity)revCdList.get(i);	%>
											  <option value="<%=revInfo.getString("REV_CD")%>"><%=revInfo.getString("REV_NM")%></option>
											<% } %>	
											</select>
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계구분<span style="width:35"></span>
											<select name="acc_type" iValue="<%=request.getParameter("acc_type")%>" onchange="changeAccType(this.value);"> 
											  <option value="A">일반회계</option>
												<option value="B">특별회계</option>
												<option value="E">기금</option>
											</select>
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    부서<span style="width:35"></span>
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changePartCode(this.value);"> 
                      <% for (int i=0; partCdList != null && i < partCdList.size(); i++) { 
                           CommonEntity partInfo =(CommonEntity)partCdList.get(i); %>
											  <option value="<%=partInfo.getString("PART_CD")%>"><%=partInfo.getString("PART_NM")%></option>
                      <% } %>
											</select>
									  </td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계명<span style="width:46"></span>
											<select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" class="box3" onchange="changeAccCode(this.value);"> 
                      <% for (int i=0; accCdList != null && i < accCdList.size(); i++) { 
                           CommonEntity accInfo =(CommonEntity)accCdList.get(i); %>
											  <option value="<%=accInfo.getString("ACC_CD")%>"><%=accInfo.getString("ACC_NM")%></option>
                      <% } %>
											</select>
									  </td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    세목명<span style="width:23"></span>
											<select name="semok_code" iValue="<%=request.getParameter("semok_code")%>" class="box3"> 
                      <% for (int i=0; semokCdList != null && i < semokCdList.size(); i++) { 
                           CommonEntity semokInfo =(CommonEntity)semokCdList.get(i); %>
											  <option value="<%=semokInfo.getString("SEMOK_CD")%>"><%=semokInfo.getString("SEMOK_NM")%></option>
                      <% } %>
											</select>
									  </td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    입력구분<span style="width:35"></span>
											<select name="input_type" iValue="<%=request.getParameter("input_type")%>" class="box3"> 
											  <option value="I1">세입세출</option>
												<option value="I2">과오납반납</option>
												<option value="I3">정정</option>
											</select>
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    년도구분<span style="width:10"></span>
											<select name="year_type" iValue="<%=request.getParameter("year_type")%>" class="box3"> 
											  <option value="Y1">현년도</option>
											</select>
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    금액증감구분<span style="width:10"></span>
											<select name="add_type" iValue="<%=request.getParameter("add_type")%>" class="box3"> 
											  <option value="P1">증액</option>
												<option value="P2">감액</option>
											</select>
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    금액<span style="width:34"></span>
											<input type="text" class="box3" size="15" name="amt" value="" userType="money">
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td colspan="3"></td>
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
        <td width="975" height="20">&nbsp;</td>
			</tr>
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20" align="right"> <img src="../img/btn_order.gif" alt="등록" align="absmiddle" onClick="goRegister()" style="cursor:hand"><span style="width:20"></span></td>
			</tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </form>
	<iframe name="hidFrame" width="100" height="100"></iframe>
  </body>
	<% 
	if (!"".equals(SucMsg)) { %>
		<script>
		alert("<%=SucMsg%>");
		</script>
	<%
	} %>
</html>