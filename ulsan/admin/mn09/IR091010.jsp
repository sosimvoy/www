<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR091010.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-20
* 프로그램내용	   : 시스템운영 > 휴일관리
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn09.jsp" %>

<%
	List<CommonEntity>holList =    (List<CommonEntity>)request.getAttribute("page.mn09.holList");
  String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  int listSize = 0;
	if (holList != null && holList.size() > 0) {
		listSize = holList.size();
	}

	String last_year = "";
	String next_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
	int next_int = Integer.parseInt(this_year) + 1;
	next_year = String.valueOf(next_int);
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
			
		    form.cmd.value = "holList";
		    form.action    = "IR091010.etax";
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
				 form.action = "IR091010.etax";  
				 form.cmd.value ="DeleteHol";
				 form.target = "_self";
				 eSubmit(form);
			 }
			 function goUpdate(){
				var form = document.sForm;
				var listSize = <%=listSize%>;
				var cnt = 0;

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.userChk.checked) {
						alert("수정할 건을 선택하세요");
						return;
					}
				} else if (listSize > 1) {
						for (var i=0; i<listSize; i++) {
							if (form.userChk[i].checked) {
								cnt++;
							}
						}
					 
						if (cnt == 0) {
							alert("수정할 건을 선택하세요");
							return;
						}

						if (cnt > 1) {
							alert("수정하고자하는 건을 하나만 선택하세요.");
							return;
						}
					}
					window.open('IR091011.etax', 'popView' ,'height=300,width=300,top=100,left=100,scrollbars=no');
					form.action = "IR091011.etax";
					form.cmd.value = "HolView";
					form.target = "popView";
					eSubmit(form);
				}
				
			 function goSave(){
				var form = document.sForm;
				
					window.open('IR091012.etax', 'popView' ,'height=300,width=300,top=100,left=100,scrollbars=no');
					form.action = "IR091012.etax";
					form.target = "popView";
					eSubmit(form);
				}	
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
		<form name="sForm" method="post" action="IR091010.etax">
    <input type="hidden" name="cmd" value="holList">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_9.gif"></td>
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
										<td width="410">&nbsp;</td>
										<td width="390"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">연도</span>
											 <select name="yyyy" iValue="<%=request.getParameter("yyyy")%>">
											  <option value="<%=next_year%>"><%=next_year%></option>
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										<img src="../img/board_icon1.gif" align="absmiddle">
										<span class="point">휴일명</span>
										    <input type="text" name="hol_name" value="<%=request.getParameter("hol_name")%>" onkeypress="keyNumericDash();">
										 </td>
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
              <th class="fstleft" width="10%">선택</th>
							<th width="45%">휴일</th>
							<th width="45%">휴일명</th>
            </tr>
         	 <%
						for (int i=0; holList != null && i < holList.size(); i++) {
							CommonEntity data = (CommonEntity)holList.get(i);
						%>

            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=TextHandler.formatDate(data.getString("M290_DATE"),"yyyyMMdd","yyyy년 MM월 dd일")%></td>
						  <td>&nbsp;<%=data.getString("M290_HOLNAME")%></td>
							<input type="hidden" name="hol_list" value="<%=data.getString("M290_DATE")%>">
            </tr>
					   <%				 
						} if (holList == null) { 
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
								<img src="../img/btn_order.gif" alt="등록" align="absmiddle" onclick="goSave()" style="cursor:hand">&nbsp;
								<img src="../img/btn_modify.gif" alt="수정" align="absmiddle" onclick="goUpdate()" style="cursor:hand">
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