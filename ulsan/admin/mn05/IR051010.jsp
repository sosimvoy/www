<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR051010.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 잉여금이입요구조회/취소
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> surplusCancelList  = (List<CommonEntity>)request.getAttribute("page.mn05.srpCancelList");
	int surplusCancelListSize = 0;
	if (surplusCancelList != null ) {
		surplusCancelListSize = surplusCancelList.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
  String reg_date = request.getParameter("reg_date");
	if ("".equals(reg_date) ) {
    reg_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR051010.etax";
				form.cmd.value = "bankSrpCancelList";
      	eSubmit(form);
      }

			function goCancel(){
				var form = document.sForm;
				var listSize = <%=surplusCancelListSize%>;
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
						if (form.into_state.value != "S1") {
							alert("요구등록건만 취소처리가 가능합니다.");
							form.allotChk.checked = false;
							return;
						}
            cnt = 1;
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
							if (form.into_state[i].value != "S1") {
							  alert("요구등록건만 취소처리가 가능합니다.");
								form.allotChk[i].checked = false;
							  return;
						  }
						  cnt++;							
					  }
				  }

				  if (cnt == 0)	{
					  alert("취소처리할 건을 체크하세요.");
					  return;
				  }
				}
        
				if (confirm("선택건을 취소처리하시겠습니까?")) {
					form.action = "IR051010.etax";
				  form.cmd.value = "bankSrpCancel";
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
  <form name="sForm" method="post" action="IR051010.etax">
	<input type="hidden" name="cmd" value="bankSrpCancelList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_27.gif"></td>
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
									    등록일자
										  <input type="text" class="box3" size="8" name="reg_date" value="<%=reg_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','reg_date')" align="absmiddle">
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
              <th width="13%">이월회계연도</th>
              <th width="20%">출금계좌</th>
							<th width="13%">이입회계연도</th>
							<th width="20%">입금계좌</th>
							<th width="15%">이입금액</th>
							<th width="14%">처리상태</th>
            </tr>
						<% if (surplusCancelListSize > 0 && surplusCancelList != null) { 
							   for (int i=0; i < surplusCancelListSize; i++) {
									 CommonEntity rowData = (CommonEntity)surplusCancelList.get(i);
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_YEAROVER")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOOVER"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("M310_YEARINTO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOINTO"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M310_INTOTAMT"))%></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_INTOSTATE_NAME")%></td>
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M310_SEQ")%>">
							<input type="hidden" name="into_state" value="<%=rowData.getString("M310_INTOSTATE")%>">
            </tr>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="7">&nbsp;</td>
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