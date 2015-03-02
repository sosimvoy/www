<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050810.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금배정승인내역조회/배정처리
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> sugiCancelList  = (List<CommonEntity>)request.getAttribute("page.mn05.sugiCancelList");
	int sugiCancelListSize = 0;
	if (sugiCancelList != null ) {
		sugiCancelListSize = sugiCancelList.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
  String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

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
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR050810.etax";
				form.cmd.value = "bankSugiCancelList";
      	eSubmit(form);
      }

			function goCancel(){
				var form = document.sForm;
				var listSize = <%=sugiCancelListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("취소처리할 건을 체크하세요.");
					  return;
				  } else {
            cnt = 1;
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;							
					  }
				  }

				  if (cnt == 0)	{
					  alert("취소처리할 건을 체크하세요.");
					  return;
				  }
				}
        
				if (confirm("선택건을 취소처리하시겠습니까?")) {
					form.action = "IR050810.etax";
				  form.cmd.value = "bankSugiCancel";
				  eSubmit(form);
				}
			}

			function change_check_box_status(obj, isChecked) {
        if (obj)  {
          if( obj.type == 'checkbox' ){
            obj.checked = isChecked;
          } else {
            for( var i = 0; i< obj.length; i++ ) {
							if (obj[i].type == 'checkbox') {
								obj[i].checked = isChecked;
							}
            }
          }
        }
      }

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050810.etax">
	<input type="hidden" name="cmd" value="bankSugiCancelList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_33.gif"></td>
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
                    <td width="860">
										  <span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도
										  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계일자
										  <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','acc_date')" align="absmiddle">
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
              <th class="fstleft" width="5%">선택<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="10%">회계연도</th>
              <th width="14%">회계일자</th>
							<th width="12%">배정구분</th>
							<th width="12%">회계구분</th>
							<th width="17%">회계명</th>
							<th width="15%">부서</th>
							<th width="15%">금액</th>
            </tr>
						<% if (sugiCancelListSize > 0 && sugiCancelList != null) { 
							   for (int i=0; i < sugiCancelListSize; i++) {
									 CommonEntity rowData = (CommonEntity)sugiCancelList.get(i);
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<td class="center">&nbsp;<%=rowData.getString("M330_YEAR")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M330_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=rowData.getString("ALLOTGUBUN_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("ACCGUBUN_NAME")%></td>
              <td class="center">&nbsp;<%=rowData.getString("M360_ACCNAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M350_PARTNAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M330_TAMT"))%>원</td>
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M330_SEQ")%>">
              <input type="hidden" name="log_no" value="<%=rowData.getString("M330_LOGNO")%>">
            </tr>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="8">&nbsp;</td>
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
							<img src="../img/btn_cancel.gif" alt="취소" style="cursor:hand" onClick="goCancel()" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
		<% if (!"".equals(SucMsg)) { %>
		<script>
		  alert("<%=SucMsg%>");
		</script>
		<% } %>
  </body>
</html>