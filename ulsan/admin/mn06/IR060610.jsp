<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR060610.jsp
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
<%@include file="../menu/mn06.jsp" %>
<%
  
	List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn06.acctList");
  List<CommonEntity> partList = (List<CommonEntity>)request.getAttribute("page.mn06.partList");
  String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}

	String new_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");

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

			function changeAcc(acct_kind) {
				var form = document.sForm;
				document.hidFrame.location = "IR060611.etax?cmd=bankSpecialList&fis_year="+form.fis_year.value+"&acct_kind="+acct_kind;
			}


      function changePart(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR060611.etax?cmd=bankSpecialList&fis_year="+form.fis_year.value+"&acct_kind="+form.acct_kind.value+"&part_code="+part_code;
			}
     
      function goRegister() {
				var form = document.sForm;
				var dep_amt;

				if (form.dep_list.value == "G2" && form.jwasu_no.value == "")	{
					alert("환매채는 좌수번호를 기입하셔야 합니다.");
					form.jwasu_no.focus();
					return;
				}

                if ((form.dep_list.value == "G1" || form.dep_list.value == "G2") && form.dep_due.value == "")	{
					alert("정기예금, 환매채는 예탁기간을 입력해야 합니다.");
					form.dep_due.focus();
					return;
				}

				if (form.acct_list.value == "" || form.acct_list.value == null)	{
					alert("회계명을 선택하세요.");
					form.acct_list.focus();
					return;
				}

				if (form.dep_amt.value == "0" || form.dep_amt.value == "") {
					alert("금액을 기입하시기 바랍니다.");
					form.dep_amt.focus();
					return;
				}
				if (form.dep_list.value == "G3") {
					form.end_date.value="";
				}

				if ((form.dep_list.value == "G1" || form.dep_list.value == "G2") && form.end_date.value == "") {
					alert("만기일을 입력하세요.");
					form.end_date.focus();
					return;
				}

        if (form.dep_list.value != "G3" && (replaceStr(form.end_date.value, " ", "") <= replaceStr(form.new_date.value, " ", ""))) {
          alert("신규일이 만기일보다 큽니다.");
          form.end_date.focus();
          return;
        }

        if (form.dep_list.value == "G3" || form.dep_list.value == "G1" || form.dep_list.value == "G4") {
				  form.jwasu_no.value = "0";
				}

        dep_amt = replaceStr(form.dep_amt.value,",","");
				form.dep_amt.value = dep_amt;
				if (confirm("기입하신 자금예탁 건을 등록하시겠습니까?")) {
					form.cmd.value = "bankSpecialReg";
				  form.action = "IR060610.etax";
				  eSubmit(form);
				}
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR060610.etax">
	<input type="hidden" name="cmd" value="bankSpecialList">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="169">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_10.gif"></td>
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
									    회계연도<span style="width:20"></span>
											<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
										<td width="40%"> 
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계구분<span style="width:20"></span>
											<select name="acct_kind" iValue="<%=request.getParameter("acct_kind")%>" onchange="changeAcc(this.value)">
												<option value="B">특별회계</option>
												<option value="D">세입세출외현금</option>
												<option value="E">기금</option>
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
									    부서<span style="width:44"></span>
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changePart(this.value)">
                      <%for (int i=0; partList != null && i<partList.size(); i++) {
                        CommonEntity partInfo = (CommonEntity)partList.get(i); %>
												<option value="<%=partInfo.getString("M350_PARTCODE")%>"><%=partInfo.getString("M350_PARTNAME")%></option>
                      <% } %>
											</select>
										</td>
                    <td width="40%">
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계명<span style="width:32"></span>
											<select name="acct_list" iValue="<%=request.getParameter("acct_list")%>">
											<% for(int i=0; acctList != null && i < acctList.size(); i++ ) { 
												   CommonEntity acctInfo = (CommonEntity)acctList.get(i);
											%>
											  <option value="<%=acctInfo.getString("M360_ACCCODE")%>"><%=acctInfo.getString("M360_ACCNAME")%></option>
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
									    예금종류<span style="width:20"></span>
											<select name="dep_list" iValue="<%=request.getParameter("dep_list")%>"> 
											  <option value="G1">정기예금</option>
												<option value="G2">환매채</option>
												<option value="G3">MMDA</option>
                        <option value="G4">융자금</option>
											</select>
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    자금종별<span style="width:10"></span>
											<select name="mmda_list" iValue="<%=request.getParameter("mmda_list")%>"> 
											  <option value="G1">공금예금</option>
											  <option value="G2">정기예금</option>
												<option value="G3">환매채</option>
												<option value="G4">MMDA</option>
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
									    계좌번호<span style="width:20"></span>
											<input type="text" class="box3" size="20" maxlength="14" name="acct_no" value="" userType="account" required desc="계좌번호">
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    좌수번호<span style="width:20"></span>
											<input type="text" class="box3" size="14" name="jwasu_no" value="" userType="number">
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    금액<span style="width:44"></span>
											<input type="text" class="box3" size="14" name="dep_amt" value="<%=0%>" userType="money">
										</td>
										<td>&nbsp;</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr> 
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    예탁기간<span style="width:20"></span>
											<input type="text" class="box3" size="14" name="dep_due" value="" userType="number">
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    이율<span style="width:44"></span>
											<input type="text" class="box3" size="5" name="dep_rate" value="" userType="decimal" required desc="이율">%
										</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
                  <tr>
									  <td>&nbsp;</td>
                    <td>
										  정기예금(개월수), 환매채(일수), MMDA(일수), 융자금(일수)
										</td>
										<td>
										 &nbsp;
										</td>
                  </tr>
                  <tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    신규일<span style="width:32"></span>
											<input type="text" class="box3" size="10" name="new_date" value="<%=new_date%>" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','new_date')" align="absmiddle">
										</td>
										<td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    만기일<span style="width:32"></span>
											<input type="text" class="box3" size="10" name="end_date" value="" userType="date">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','end_date')" align="absmiddle">
										</td>
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
	<iframe name="hidFrame" width="0" height="0"></iframe>
  </body>
	<% 
	if (!"".equals(SucMsg)) { %>
		<script>
		alert("<%=SucMsg%>");
		</script>
	<%
	} %>
</html>