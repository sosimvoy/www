<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050710.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-06-15
* 프로그램내용	   : 자금배정 > 자금배정수기분등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> deptList = (List<CommonEntity>)request.getAttribute("page.mn05.deptList");

	List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn05.acctList");

  String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
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
		<script language="javascript" src="../js/utility.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }

			function goAcct(acct_kind) {
				var form = document.sForm;
				var fake_allot = form.allot_kind;
        /*
				if (acct_kind == "A") {
          deleteOptions(fake_allot);
					addOption(fake_allot, "1", "배정");
					addOption(fake_allot, "2", "재배정");
				} else {
					deleteOptions(fake_allot);
					addOption(fake_allot, "1", "배정");
					addOption(fake_allot, "2", "재배정");
					addOption(fake_allot, "3", "배정반납");
					addOption(fake_allot, "4", "재배정반납");
				}
        */
			}

			function changeSemok(acc) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+acc+"&allot_kind="+form.allot_kind.value+"&dept_kind="+form.dept_kind.value;
			}

			function chAllot(allot) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+form.acc_type.value+"&allot_kind="+allot+"&dept_kind="+form.dept_kind.value;
			}

			function chAcct(dept) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+form.acc_type.value+"&allot_kind="+form.allot_kind.value+"&dept_kind="+dept+"&bonus=1";
			}


      function chAcct_T(dept) {
				var form = document.sForm;
				document.hidFrame.location = "IR050711.etax?cmd=bankSugiList&fis_year="+form.fis_year.value+"&acc_type="+form.acc_type.value+"&allot_kind="+form.allot_kind.value+"&dept_kind="+dept+"&bonus=1";
			}
     
      function goRegister() {
				var form = document.sForm;

				if (form.dept_kind.value == "" || form.acc_kind.value == "") {
					alert("부서 또는 회계명을 선택하세요.");
					form.dept_kind.focus();
					return;
				}

				if (form.sugi_amt.value == "0" || form.sugi_amt.vlaue == "") {
					alert("금액을 기입하세요.");
					form.sugi_amt.focus();
					return;
				}
        if (confirm("자금배정수기분을 등록하시겠습니까?")) {
					form.cmd.value = "bankSugiReg";
				  form.action = "IR050710.etax";
				  eSubmit(form);
        }				
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050710.etax">
	<input type="hidden" name="cmd" value="bankSugiList">
  <input type="hidden" name="work_log" value="A05">
  <input type="hidden" name="trans_gubun" value="151">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_32.gif"></td>
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
									  <td width="25%">&nbsp;</td>
                    <td width="35%">
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도<span style="width:20"></span>
										  <select name="fis_year" value="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										</td>
                    <td width="40%">&nbsp;</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td>&nbsp;</td>
                    <td>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계일자<span style="width:20"></span>
											<input type="text" class="box3" size="10" name="acc_date" value="<%=acc_date%>" userType="date" required desc="회계일자">		
											<img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
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
									    배정구분<span style="width:20"></span>
											<select name="allot_kind" iValue="<%=request.getParameter("allot_kind")%>" onchange="chAllot(this.value); goAcct(this.value)">
                        <option value="1">배정</option>
												<option value="2">재배정</option>
												<option value="3">배정반납</option>
												<option value="4">재배정반납</option>
											</select>
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
									    회계구분<span style="width:20"></span>
											<select name="acc_type" onchange="changeSemok(this.value)">
												<option value="B">특별회계</option>
												<option value="E">기금</option>
                        <option value="A">일반회계</option>
											</select>
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
									    부서<span style="width:44"></span>
											<select name="dept_kind" value="<%=request.getParameter("dept_kind")%>" onchange="chAcct(this.value)">
											<%for (int i=0; deptList!=null && i<deptList.size(); i++) { 
												  CommonEntity dept = (CommonEntity)deptList.get(i); %>
												<option value="<%=dept.getString("M350_PARTCODE")%>"><%=dept.getString("M350_PARTNAME")%></option>
											<%}%>
											</select>
										</td>
                    <td>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    배정하는 부서<span style="width:44"></span>
											<select name="t_dept_kind" value="<%=request.getParameter("t_dept_kind")%>" onchange="chAcct_T(this.value)">
												<option value="00000">본청</option>
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
									    회계명<span style="width:32"></span>
											<select name="acc_kind" iValue="<%=request.getParameter("acc_kind")%>">
											<%for (int i=0; acctList!=null && i<acctList.size(); i++) { 
												  CommonEntity dept = (CommonEntity)acctList.get(i); %>
												<option value="<%=dept.getString("M360_ACCCODE")%>"><%=dept.getString("M360_ACCNAME")%></option>
											<%}%>
											</select>
										</td>
                    <td>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    배정하는 회계명<span style="width:32"></span>
											<select name="t_acc_kind" iValue="<%=request.getParameter("t_acc_kind")%>">
												<option value="11">일반회계</option>
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
									    금액<span style="width:44"></span>
											<input type="text" class="box3" size="15" name="sugi_amt" value="0" userType="money" required desc="금액">
											<span style="width:40"></span>
											<img src="../img/btn_order.gif" alt="등록" align="absmiddle" onClick="goRegister()" style="cursor:hand">
										</td>
                    <td>&nbsp;</td>
                  </tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
									</tr>
									<tr>
									  <td colspan="3">&nbsp;</td>
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