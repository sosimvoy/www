<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040410.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-13
* 프로그램내용	   : 세외수입 > 등록내역 조회/수정/삭제
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
	List<CommonEntity>jingsuList = (List<CommonEntity>)request.getAttribute("page.mn04.jingsuList");
	String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy");
  
  int listSize = 0;
	if (jingsuList != null && jingsuList.size() > 0) {
		listSize = jingsuList.size();
	}

		String frombaluiDate = request.getParameter("frombaluiDate"); //발의일자
    if (frombaluiDate.equals("")) {
		frombaluiDate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
	}  
		String tobaluiDate = request.getParameter("tobaluiDate"); //발의일자
    if (tobaluiDate.equals("")) {
		tobaluiDate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyy-MM-dd","yyyyMMdd");
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
				form.action = "IR040410.etax";  
				form.cmd.value = "jingsuList";
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
                 cnt = 1;
                 form.seq_yn.value = "Y";
			 } else if (listSize > 1) {
				 for (var i=0; i<listSize; i++) {
					 if (form.userChk[i].checked) {
                         form.seq_yn[i].value = "Y";
						 cnt++;
					 }
				 }
				 
			   if (cnt == 0) {
					 alert("삭제할 건을 선택하세요.");
					 return;
				 }

         if (cnt > 1) {
           alert("한 건씩 삭제하세요.");
					 return;
         }
			 }
       if (confirm("선택 건을 삭제하시겠습니까?")) {
         form.action = "IR040410.etax";  
			   form.cmd.value ="jingsuDelete";
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
					window.open('IR040411.etax', 'popView' ,'height=550,width=550,top=100,left=100,scrollbars=no');
					form.action = "IR040411.etax";
					form.cmd.value = "jingsuView";
					form.target = "popView";
					eSubmit(form);
				}		
		 
		  
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR040410.etax">
	<input type="hidden" name="cmd" value="jingsuList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title4_27.gif"></td>
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
									    <span style="width:50px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
									    회계연도&nbsp; 
                      <select name="fis_year" class="box3">
                        <option value="<%=this_year%>"><%=this_year%></option>
                        <option value="<%=Integer.parseInt(this_year)-1%>"><%=Integer.parseInt(this_year)-1%></option>
                      </select>
                      <span style="width:50px"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
									    발의시작일자&nbsp; <input type="text" name="frombaluiDate" class="box3" size="8" value="<%=frombaluiDate%>" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','frombaluiDate');" style="cursor:hand">
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
									    발의종료일자&nbsp; <input type="text" name="tobaluiDate" class="box3" size="8" value="<%=tobaluiDate%>" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','tobaluiDate');" style="cursor:hand">
								    </td>
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
              <th class="fstleft" width="4%" rowspan="2">선택</th>
							<th width="11%">발의일자</th>
							<th width="11%">고지서발행</th>
							<th width="11%">납입기한</th>
							<th width="11%">징수부기재</th>
							<th width="11%">관</th>
							<th width="11%">항</th>
							<th width="10%">목</th>
							<th width="10%">세목</th>
							<th width="10%">본세</th>
						 </tr>
						 <tr>
							<th width="10%">가산금</th>
							<th width="10%">이자</th>
							<th width="10%">합계금액</th>
							<th width="10%">납부자성명</th>
							<th width="10%">납부자주소</th>
							<th width="10%">주민등록번호</th>
							<th width="10%">물건명</th>
							<th width="10%" colspan="2">시청담당자</th>
							
							
            </tr>

						<%
						for (int i=0; jingsuList != null && i < jingsuList.size(); i++) {
							CommonEntity data = (CommonEntity)jingsuList.get(i);
						%>
     
            <tr> 
              <% if ("".equals(data.getString("M090_DOCUMENTNO")) ) { %>
              <td class="fstleft" rowspan="2"> <input type="checkbox" name="userChk" value="<%=i%>"></td>              
              <% } else { %>
              <td class="fstleft" rowspan="2"> &nbsp;<input type="hidden" name="userChk" value="<%=i%>"></td>
              <% } %>
					    <td>&nbsp;<%=data.getString("M090_BALUIDATE")%></td>
							<td>&nbsp;<%=data.getString("M090_GOJISEOPUBLISH")%></td>						
							<td>&nbsp;<%=data.getString("M090_NAPIBDATE")%></td>
							<td>&nbsp;<%=data.getString("M090_JINGSUBUWRITE")%></td>
							<td>&nbsp;<%=data.getString("M090_GWAN")%></td>
					    <td>&nbsp;<%=data.getString("M090_HANG")%></td>
							<td>&nbsp;<%=data.getString("M090_MOK")%></td>
							<td>&nbsp;<%=data.getString("M090_SEMOKCODE")%></td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_BONAMT"))%></td>
            </tr>
						 <tr>                 
					    <td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_GASANAMT"))%></td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_INTERESTAMT"))%></td>						
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_TOTALAMT"))%></td>
							<td>&nbsp;<%=data.getString("M090_NAPBUJANAME")%></td>
							<td>&nbsp;<%=data.getString("M090_NAPBUJAADDRESS")%></td>
					    <td>&nbsp;<%=data.getString("M090_JUMINNO")%></td>
							<td>&nbsp;<%=data.getString("M090_BUSINESSNAME")%></td>
							<td colspan="2">&nbsp;<%=data.getString("M260_USERNAME")%></td>
              <input type="hidden" name="seq_yn" value="">
							<input type="hidden" name="seq_list" value="<%=data.getString("M090_SEQ")%>">
              <input type="hidden" name="m80_seq" value="<%=data.getString("M090_SEQ")%>">
              <input type="hidden" name="year" value="<%=data.getString("M090_YEAR")%>">
              <input type="hidden" name="acc_date" value="<%=data.getString("M090_DATE")%>">
              <input type="hidden" name="totalAmt" value="<%=data.getString("M090_TOTALAMT")%>">
            </tr>
					  <%				 
						} if (jingsuList == null) { 
					  %>
						<tr>
							<td class="fstleft" colspan="12">조회 내역이 없습니다.</td>
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
              <!--
							  <img src="../img/btn_modify.gif" alt="수정" style="cursor:hand" onClick="goUpdate()" align="absmiddle"> -->
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
