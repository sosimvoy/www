<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090110.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-20
* 프로그램내용	   : 시스템운영 > 사용자등록/변경신청내역조회/승인
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
	List<CommonEntity>historyList = (List<CommonEntity>)request.getAttribute("page.mn09.historyList");
	String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  int listSize = 0;
	if (historyList != null && historyList.size() > 0) {
		listSize = historyList.size();
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
				form.cmd.value = "loginHistoryList";
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
			 form.action = "IR090110.etax";  
			 form.cmd.value ="deleteUser";
			 form.target = "_self";
			 eSubmit(form);
		 }
	 
		function goConfirm(){
			var form = document.sForm;
			var listSize = <%=listSize%>;
			var cnt = 0;

			if (listSize == 0) {
				alert("조회내역이 없습니다.");
				return;
			}

			if (listSize == 1) {
				if (!form.userChk.checked) {
					alert("승인할 건을 선택하세요");
					return;
				}
			} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.userChk[i].checked) {
							cnt++;
						}
					}
				 
					if (cnt == 0) {
						alert("승인할 건을 선택하세요");
						return;
					}

					if (cnt > 1) {
						alert("승인하고자하는 건을 하나만 선택하세요.");
						return;
					}
				}
				
				form.action = "IR090110.etax"
				form.cmd.value = "updateUser";
				form.target = "_self";
				eSubmit(form);
			}		
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
  <form name="sForm" method="post" action="IR090110.etax">
	<input type="hidden" name="cmd" value="loginHistoryList">
	<input type="hidden" name="work_log" value="A09">
	<input type="hidden" name="trans_gubun" value="115">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_1.gif"></td>
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
                    <td width="860"><span style="width:500"></span></td>
                    <td width="100"> 
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
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="4%">선택</th>
							<th width="10%">신청일자</th>
              <th width="10%">사용자명</th>
							<th width="10%">소속기관</th>
							<th width="10%">소속부서</th>
							<th width="10%">주요업무</th>
							<th width="10%">결재권구분</th>
							<th width="9%">책임자행번</th>
							<th width="9%">책임자번호</th>
							<th width="9%">단말번호</th>
							<th width="9%">요청상태</th>
            </tr>

       
						<%
						for (int i=0; historyList != null && i < historyList.size(); i++) {
							CommonEntity data = (CommonEntity)historyList.get(i);
						%>

            <tr>                 
              <td class="fstleft"> <input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M260_REQUESTDATE")%>  </td>
              <td>&nbsp;<%=data.getString("M260_USERNAME")%>     </td>
					    <td>&nbsp;<%=data.getString("M260_CURRENTORGAN")%> </td>
	           
							<% if ("Y".equals(data.getString("M260_PARTSTATE")) ) {  %>
              <td>
							 <input type="text" value="<%=data.getString("CHANGEPART")%>" size="8" name="change_part" readonly style="border:0px ; color:red">
							</td>
							<% } %>
							<% if ("N".equals(data.getString("M260_PARTSTATE")) ) {  %>
							<td>&nbsp;<%=data.getString("CURRENTPART")%> </td>
              <% } %>
							
							<% if ("Y".equals(data.getString("M260_WORKSTATE1")) ) {  %>
              <td>
						  	<input type="text" value="<%=data.getString("CHANGEWORK1")%>" size="8" name="change_work1" readonly style="border:0px ; color:red">
						  </td>
							<% } %>
							<% if ("N".equals(data.getString("M260_WORKSTATE1")) ) {  %>
							<td>&nbsp;<%=data.getString("CURRENTWORK1")%> </td>
              <% } %>
              
							<% if ("Y".equals(data.getString("M260_SIGNSTATE")) ) {  %>
              <td>
								<input type="text" value="<%=data.getString("CHANGESIGNTYPE")%>" size="8" name="change_signtype" readonly style="border:0px ; color:red">
							</td>
							<% } %>
							<% if ("N".equals(data.getString("M260_SIGNSTATE")) ) {  %>
							<td>&nbsp;<%=data.getString("CURRENTSIGNTYPE")%> </td>  
              <% } %>
	          
							<td>&nbsp;<%=data.getString("M260_MANAGERBANKERNO")%> </td>
							<td>&nbsp;<%=data.getString("M260_MANAGERNO")%> </td>
							<td>&nbsp;<%=data.getString("M260_TERMINALNO")%> </td>
			   
              <% if ("N".equals(data.getString("M260_USERSTATE")) ) { %>
								<td>가입 신청</td>
							<%} else if("Y".equals(data.getString("M260_PARTSTATE")) ||"Y".equals(data.getString("M260_WORKSTATE1"))|| "Y".equals(data.getString("M260_SIGNSTATE")) )  {  %>
						  <td> 변경 요청 </td>
							<% } %>
							<input type="hidden" name="userid_list" value="<%=data.getString("M260_USERID")%>">
							<input type="hidden" name="change_part_list" value="<%=data.getString("M260_CHANGEPART")%>">
							<input type="hidden" name="change_work1_list" value="<%=data.getString("M260_CHANGEWORK1")%>">
							<input type="hidden" name="change_signtype_list" value="<%=data.getString("M260_CHANGESIGNTYPE")%>">
							<input type="hidden" name="current_part_list" value="<%=data.getString("M260_CURRENTPART")%>">
							<input type="hidden" name="current_work1_list" value="<%=data.getString("M260_CURRENTWORK1")%>">
							<input type="hidden" name="current_signtype_list" value="<%=data.getString("M260_CURRENTSIGNTYPE")%>">
            </tr>
					  <%				 
				      } if (historyList == null || historyList.size() == 0) { 
				    %>
				    <tr> 
              <td class="fstleft" colspan="11">&nbsp;</td>
            </tr>
				    <% } %>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_confirm.gif" alt="승인" style="cursor:hand" onClick="goConfirm()" align="absmiddle">
								<img src="../img/btn_cancel.gif" alt="취소" style="cursor:hand" onClick="goCancel()" align="absmiddle">
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