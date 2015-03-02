<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR080910.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-20
* 프로그램내용	   : 시스템운영 > 세목코드사용등록
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
	List<CommonEntity>userCodeList      = (List<CommonEntity>)request.getAttribute("page.mn08.userCodeList");	
  List<CommonEntity> deptList         = (List<CommonEntity>)request.getAttribute("page.mn08.deptList");
	List<CommonEntity> accCdList        = (List<CommonEntity>)request.getAttribute("page.mn08.accCdList");
	List<CommonEntity> accCdList1        = (List<CommonEntity>)request.getAttribute("page.mn08.accCdList1");
	List<CommonEntity> semokList        = (List<CommonEntity>)request.getAttribute("page.mn08.semokList");
  List<CommonEntity> userAccDeptList1  = (List<CommonEntity>)request.getAttribute("page.mn08.userAccDeptList1");		

  String SucMsg = (String)request.getAttribute("page.mn08.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}
  
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
 
  int listSize = 0;
	if (userCodeList != null && userCodeList.size() > 0) {
		listSize = userCodeList.size();
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
		    form.cmd.value = "userCodeList2";
		    form.action    = "IR080910.etax";
				eSubmit(form);
			}
      
			 function gosaveData()	{
				var form = document.sForm;
				form.action = "IR080910.etax";
				form.cmd.value = "insertUserCode";
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
				 form.action = "IR080910.etax";  
				 form.cmd.value ="deleteUserCode";
				 eSubmit(form);
			 }
       
			function changeType(type) {
				document.location = "IR080910.etax?cmd=userCodeList&accGubun="+type;
			}
			function changeDept(dept) {
				var form = document.sForm;
				document.location = "IR080910.etax?cmd=userCodeList&part_code="+dept+"&accGubun="+form.accGubun.value;
			}
			function changeNmae(name) {
				var form = document.sForm;
				document.location = "IR080910.etax?cmd=userCodeList&acc_code="+name+"&accGubun="+form.accGubun.value+"&part_code="+form.part_code.value;
			}
         	
		  function changeType1(type) {
				document.location = "IR080910.etax?cmd=userCodeList&accGubun1="+type;
			}

			function changeDept1(dept) {
				var form = document.sForm;
				document.location = "IR080910.etax?cmd=userCodeList&partCode="+dept+"&accGubun1="+form.accGubun1.value;
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR080910.etax">
    <input type="hidden" name="cmd" value="userCodeList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_24.gif"></td>
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
											<span style="width:175px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">회계구분</span>
											<select name="accGubun" iValue="<%=request.getParameter("accGubun")%>" onchange="changeType(this.value)" style="width:16%">
												<option value="A">일반회계</option>
												<option value="B">특별회계</option>
												<option value="C">공기업특별회계</option>
												<option value="D">세입세출외현금</option>
												<option value="E">기금</option>
											</select>
										  <span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">부서</span>&nbsp;&nbsp;&nbsp;&nbsp;
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changeDept(this.value)" style="width:19%">
								        <% for (int i=0; deptList != null && i < deptList.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)deptList.get(i); %>												    
										       <option value="<%=deptInfo.getString("M390_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
											<BR><BR>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">회계명</span>&nbsp;&nbsp;&nbsp;&nbsp;
											 <select name="acc_code" iValue="<%=request.getParameter("acc_code")%>" onchange="changeNmae(this.value)" style="width:27%">
								        <% for (int i=0; accCdList != null && i < accCdList.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)accCdList.get(i); %>												    
								        <option value="<%=accCdInfo.getString("M390_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:28px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">업무구분</span>
											<select name="workGubun" iValue="<%=request.getParameter("workGubun")%>" style="width:16%">
												<option value="0">세입</option>
												<option value="1">세출</option>
												<option value="2">자금배정</option>
												<option value="3">자금운용</option>
												<option value="4">세입세출외현금</option>
											</select>
											<span style="width:30px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">세목명</span>
										  <select name="semok_code" iValue="<%=request.getParameter("semok_code")%>" style="width:24%">
								        <% for (int i=0; semokList != null && i < semokList.size(); i++) {
									         CommonEntity semokInfo = (CommonEntity)semokList.get(i); %>												    
										    <option value="<%=semokInfo.getString("M390_SEMOKCODE")%>"><%=semokInfo.getString("M370_SEMOKNAME")%></option>
									      <% } %>
								      </select>
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
											<select name="year1" iValue="<%=request.getParameter("year1")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
											<span style="width:10px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">회계구분</span>
											<select name="accGubun1" iValue="<%=request.getParameter("accGubun1")%>" onchange="changeType1(this.value)">
												<option value="A">일반회계</option>
												<option value="B">특별회계</option>
												<option value="C">공기업특별회계</option>
												<option value="D">세입세출외현금</option>
												<option value="E">기금</option>
											</select>
											<span style="width:10px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											<span class="point">부서</span>
											<select name="partCode" iValue="<%=request.getParameter("partCode")%>" onchange="changeDept1(this.value)" style="width:17%">
								        <% for (int i=0; userAccDeptList1 != null && i < userAccDeptList1.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)userAccDeptList1.get(i); %>												    
										       <option value="<%=deptInfo.getString("M390_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
											<span style="width:10px"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
											<span class="point">회계명</span>
											 <select name="accCode" iValue="<%=request.getParameter("accCode")%>" style="width:25%">
								        <% for (int i=0; accCdList1 != null && i < accCdList1.size(); i++) {
									         CommonEntity accCdInfo = (CommonEntity)accCdList1.get(i); %>												    
								        <option value="<%=accCdInfo.getString("M390_ACCCODE")%>"><%=accCdInfo.getString("M360_ACCNAME")%></option>
									      <% } %>
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
							<th width="8%">회계연도</th>
							<th width="10%">회계구분</th>
							<th width="20%">회계명</th>
							<th width="10%">업무구분</th>
							<th width="8%">장관항목</th>
							<th width="9%">세목코드</th>
							<th width="19%">세목명</th>
							<th width="12%">국세지방세구분</th>
            </tr>
            <%
						for (int i=0; userCodeList != null && i < userCodeList.size(); i++) {
							CommonEntity data = (CommonEntity)userCodeList.get(i);
						%> 
            <tr>                 
              <td class="fstleft"><input type="checkbox" name="userChk" value="<%=i%>"></td>
							<td>&nbsp;<%=data.getString("M390_YEAR")%></td>
						  <td>&nbsp;<%=data.getString("M390_ACCGUBUN")%></td>
							<td>&nbsp;<%=data.getString("M360_ACCNAME")%></td>
							<td>&nbsp;<%=data.getString("M390_WORKGUBUN")%></td>
							<td>&nbsp;<%=data.getString("M370_MOKGUBUN")%></td>
						  <td>&nbsp;<%=data.getString("M390_SEMOKCODE")%></td>
							<td>&nbsp;<%=data.getString("M370_SEMOKNAME")%></td>
							<td>&nbsp;<%=data.getString("M370_SEGUMGUBUN")%></td>
							<input type="hidden" name="year_list"      value="<%=data.getString("M390_YEAR")%>">
							<input type="hidden" name="partCode_list"  value="<%=data.getString("M390_PARTCODE")%>">
							<input type="hidden" name="accGubun_list"  value="<%=data.getString("M390_ACCGUBUN")%>">
							<input type="hidden" name="accCode_list"   value="<%=data.getString("M390_ACCCODE")%>">
							<input type="hidden" name="semokCode_list" value="<%=data.getString("M390_SEMOKCODE")%>">
							<input type="hidden" name="workGubun_list" value="<%=data.getString("M390_WORKGUBUN")%>">
            </tr>
					  <%				 
						} if (userCodeList == null) { 
				    %>
						<tr>
							<td class="fstleft" colspan="9">조회 내역이 없습니다.</td>
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