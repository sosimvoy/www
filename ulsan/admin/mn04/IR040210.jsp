<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040210.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-04
* 프로그램내용	   : 세외수입 > 예산서 조회/수정/삭제
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn04.jsp" %>

<%
	List<CommonEntity>budgetList = (List<CommonEntity>)request.getAttribute("page.mn04.budgetList");
	
	String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
  int listSize = 0;
	if (budgetList != null && budgetList.size() > 0) {
		listSize = budgetList.size();
	}

		String last_year = "";
		String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		int last_int = Integer.parseInt(this_year) - 1;
		last_year = String.valueOf(last_int);

		String date = request.getParameter("date");
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
				form.action = "IR040210.etax";  
				form.cmd.value = "budgetList";
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
       if (confirm("선택 건(들)을 삭제하시겠습니까?")) {
         form.action = "IR040210.etax";  
			   form.cmd.value ="budgetDelete";
			   form.target = "_self";
			   eSubmit(form);
       }
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
				} else {
					form.gwamok_val.value = form.gwamok.value;
				}
			} else if (listSize > 1) {
					for (var i=0; i<listSize; i++) {
						if (form.userChk[i].checked) {
							form.gwamok_val.value = form.gwamok[i].value;
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
					window.open('IR040211.etax', 'popView' ,'height=460,width=400,top=100,left=100,scrollbars=no');
					form.action = "IR040211.etax";
					form.cmd.value = "budgetView";
					form.target = "popView";
					eSubmit(form);
				}		
		 

		 function goChukyung(a,b){
			var form = document.sForm;
			
			window.open('IR040212.etax', 'popView' ,'height=420,width=350,top=100,left=100,scrollbars=no');
			 form.seq.value=a;
			 form.year_list.value=b;
			 form.action = "IR040212.etax";  
			 form.cmd.value ="chukyungView";
			 form.target = "popView";
			 eSubmit(form);
		 }

     function goView(a,b){
			 var form = document.sForm;
       form.seq.value=a;
			 form.action = "IR040214.etax";  
			 form.cmd.value ="revInfoView";		
			 window.open('IR040214.etax', 'popView' ,'height=570,width=350,top=100,left=100,scrollbars=no');
			 form.target = "popView"; 
			 eSubmit(form);
		 }

			function goExcel() {
				var form = document.sForm;
				form.action = "IR040215.etax";
				form.cmd.value = "m080ExcelList";
      	eSubmit(form);
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR040210.etax">
	<input type="hidden" name="cmd" value="budgetList">
  <input type="hidden" name="gwamok_val" value="">
  <input type="hidden" name="seq">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title4_10.gif"></td>
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
                   <td>
									  <span style="width:50"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
							        <span class="point">회계연도</span>
											<select name="year" iValue="<%=request.getParameter("year")%>">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
									    </select>
                     
										  <span style="width:20"></span>                          
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
									    <span class="point">회계일자</span>
										   <input type="text" class="box3" size="8" name="date" value="<%=date%>" userType="date"> 
						           <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','date')" align="absmiddle">
									    
										  <span style="width:20"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
										  <span class="point">목</span>
										   <input type="text" name="mok" value="<%=request.getParameter("mok")%>">
		                  
											<span style="width:20"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
											<span class="point">소관부처</span>
										    <input type="text" name="sogwanpart" value="<%=request.getParameter("sogwanpart")%>" >

										  <span style="width:20"></span>
                      <img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()" align="absmiddle">
                      <br><br>

											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
											<span class="point">실과별</span>
										    <input type="text" name="silgwa" value="<%=request.getParameter("silgwa")%>" >
											
											<span style="width:60"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
											<span class="point">사업명</span>
										    <input type="text" name="businessname" value="<%=request.getParameter("businessname")%>">
											
											<span style="width:60"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
											<span class="point">수령액</span>
										    <input type="text" name="monthamt" value="<%=request.getParameter("monthamt")%>" onkeypress="keyNumericDash();">

										  <span style="width:20"></span>
                      &nbsp;&nbsp;&nbsp;<img src="../img/btn_excel.gif" alt="엑셀" style="cursor:hand" onClick="goExcel()" align="absmiddle">
								    </td>
                  </tr>
                  <tr>
                    <td style="font-size:10pt; color:red">
                    <br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    Excel DownLoad후 반드시 해당문서를 열어 다른이름으로 저장(통합문서 *.xls)후 사용바람 </H>
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
              <th class="fstleft" rowspan="2" width="4%">선택</th>
							<th width="9%">목</th>
                            <th width="9%">세목</th>
							<th width="10%">소관부처</th>
							<th width="9%">실과별</th>
							<th width="9%">과목</th>
							<th width="10%">사업명</th>
           </tr>
		   <tr>
							<th width="10%">미수령액</th>
							<th width="10%">당초예산</th>
							<th width="10%">추경예산</th>
							<th width="10%">수령액</th>
							<th width="10%">담당자</th>
							<th width="10%">내선번호</th>
            </tr>

						<%
            long mirev_amt = 0L;
						for (int i=0; budgetList != null && i < budgetList.size(); i++) {
							CommonEntity data = (CommonEntity)budgetList.get(i);
              mirev_amt = data.getLong("M080_DANGCHOAMT") + data.getLong("CHUKYUNGAMT") - data.getLong("MONTHAMT");
						%>
     
            <tr>                 
              <td class="fstleft" rowspan="2"> <input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M080_MOK")%></td>
              <td>&nbsp;<%=data.getString("M080_SEMOKCODE")%></td>
					    <td>&nbsp;<%=data.getString("M080_SOGWANPART")%></td>
							<td>&nbsp;<%=data.getString("M080_SILGWA")%></td>						
							<td>&nbsp;<%=data.getString("M080_GWAMOK")%></td>
							<td>&nbsp;<%=data.getString("M080_BUSINESSNAME")%> </td>
							</tr>
							<tr>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(mirev_amt)%></td>
					    <td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M080_DANGCHOAMT"))%></td>
							<td class="right">&nbsp;<a href="javascript:goChukyung('<%=data.getString("M080_SEQ")%>','<%=data.getString("M080_YEAR")%>')"><font color="red"><%=TextHandler.formatNumber(data.getString("CHUKYUNGAMT"))%></font></a></td>
							<td class="right">&nbsp;<a href="javascript:goView('<%=data.getString("M080_SEQ")%>','<%=data.getString("M080_YEAR")%>')"><font color="red"><%=TextHandler.formatNumber(data.getString("MONTHAMT"))%></font></a></td>
							<td>&nbsp;<%=data.getString("M080_USERNAME")%></td>						
							<td>&nbsp;<%=data.getString("M080_INTELNO")%></td>
							<input type="hidden" name="seq_list" value="<%=data.getString("M080_SEQ")%>">
							<input type="hidden" name="gwamok" value="<%=data.getString("M080_GWAMOK")%>">
							<input type="hidden" name="year_list" value="<%=data.getString("M080_YEAR")%>">							
            </tr>
					  <%				 
						} if (budgetList == null) { 
					  %>
						<tr>
							<td class="fstleft" colspan="07">조회 내역이 없습니다.</td>
						</tr>
						<% 
							}
						%>
          </table>
        </td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
            <tr> 
              <td>
							  <img src="../img/btn_modify.gif" alt="수정" style="cursor:hand" onClick="goUpdate()" align="absmiddle">
								<img src="../img/btn_delete2.gif" alt="삭제" style="cursor:hand" onClick="goCancel()" align="absmiddle">
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