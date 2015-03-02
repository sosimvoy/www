<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR080710.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-19
* 프로그램내용	   : 시스템운영 > 회계코드관리
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn08.jsp" %>

<%
	List<CommonEntity>accCodeList =    (List<CommonEntity>)request.getAttribute("page.mn08.accCodeList");
  String SucMsg = (String)request.getAttribute("page.mn08.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (accCodeList != null && accCodeList.size() > 0) {
		listSize = accCodeList.size();
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
		    form.cmd.value = "accCodeList";
		    form.action    = "IR080710.etax";
				form.target = "_self";
				eSubmit(form);
			}
      
			 function gosaveData()	{
				var form = document.sForm;

				form.accName.value=form.accName.value.replace(/\s/g,"");
				form.action = "IR080710.etax";
				form.cmd.value = "insertAccCode";
				form.target = "_self";
				eSubmit(form);
			}

		  function goCancel(){
				var form = document.sForm;
				var listSize = <%=listSize%>;
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
							cnt++;
						}
					}
					 
						if (cnt == 0) {
						alert("삭제할 건을 선택하세요");
						return;
						}
					}
				 form.action = "IR080710.etax";  
				 form.cmd.value ="deleteAccCode";
				 eSubmit(form);
			 }
    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR080710.etax">
    <input type="hidden" name="cmd" value="accCodeList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_22.gif"></td>
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
										<td width="50">&nbsp;</td>
										<td width="750"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">회계연도</span>
											<select name="year" iValue="<%=request.getParameter("year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">회계구분</span>
											<select name="accGubun1" iValue="<%=request.getParameter("accGubun1")%>">
												<option value="A">일반회계</option>
												<option value="B">특별회계</option>
												<option value="C">공기업특별회계</option>
												<option value="D">세입세출외현금</option>
												<option value="E">기금</option>
											</select>
										 	    <span style="width:30px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">회계코드</span>
													<input type="text" maxlength="2" name="accCode" class="box3" size="10" value=""> 
													<span style="width:30px"></span>
													<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
													<span class="point">회계명</span>
													<input type="text" name="accName" class="box3" size="10" value="">
											 <td width="50"> 
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
										<td width="50">&nbsp;</td>
										<td width="750"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">회계연도</span>
											<select name="year" iValue="<%=request.getParameter("year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
											<span style="width:30px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">회계구분</span>
											<select name="accGubun" iValue="<%=request.getParameter("accGubun")%>">
												<option value="A">일반회계</option>
												<option value="B">특별회계</option>
												<option value="C">공기업특별회계</option>
												<option value="D">세입세출외현금</option>
												<option value="E">기금</option>
											</select>
											 <td width="50"> 
											<div align="right"><img src="../img/btn_search.gif" width="55" height="21" border="0" onClick="goSearch()" style="cursor:hand">
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
              <th class="fstleft" width="4%">선택</th>
							<th width="24%">회계연도</th>
							<th width="24%">회계구분</th>
							<th width="24%">회계코드</th>
							<th width="24%">회계명</th>
            </tr>
         	 <%
						for (int i=0; accCodeList != null && i < accCodeList.size(); i++) {
							CommonEntity data = (CommonEntity)accCodeList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M360_YEAR")%></td>
						  <td>&nbsp;<%=data.getString("ACCGUBUN")%></td>
							<td>&nbsp;<%=data.getString("M360_ACCCODE")%></td>
							<td>&nbsp;<%=data.getString("M360_ACCNAME")%></td>
							<input type="hidden" name="year_list" value="<%=data.getString("M360_YEAR")%>">
							<input type="hidden" name="accGubun_list" value="<%=data.getString("M360_ACCGUBUN")%>">
							<input type="hidden" name="accCode_list" value="<%=data.getString("M360_ACCCODE")%>">
            </tr>
					   <%				 
						} if (accCodeList == null) { 
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