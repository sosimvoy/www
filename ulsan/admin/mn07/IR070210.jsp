<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR070210.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-12
* 프로그램내용	   : 일계/보고서 > 일일업무마감
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
	List<CommonEntity> magamList = (List<CommonEntity>)request.getAttribute("page.mn07.magamList");
  CommonEntity dateData = (CommonEntity)request.getAttribute("page.mn07.dateData");
        String magamDate = "";
        String mimagamDate = "";
        if (dateData != null) {
          magamDate = dateData.getString("MAGAM_DATE");
          mimagamDate = dateData.getString("MIMAGAM_DATE");

        }
	String retMsg = (String)request.getAttribute("page.mn07.retMsg");
        String acc_year = request.getParameter("acc_year");
        if ("".equals(acc_year) || acc_year == null) {
          acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy");
        }

        String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
        int this_int = Integer.parseInt(this_year);

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
					form.action = "IR070210.etax";
					form.cmd.value = "workEndList";
					form.target = "_self";
					eSubmit(form);
	    }

			function getRadioValue(){
					var form = document.sForm;
					var len = form.choice_magam.length;
					var radioValue = "";

					if (len == undefined) {
							radioValue = form.choice_magam.value;
					} else {
							for(i=0; i<len; i++){
								 if(form.choice_magam[i].checked){
										radioValue = form.choice_magam[i].value;
										break;
								 }
							}
					}

				  return radioValue;
			}
			
			function goMagam(){
					var form = document.sForm;
					var magamData = getRadioValue();
          var mimagamDate = "<%=mimagamDate%>";

					if (magamData == '' ){
							alert('선택된 마감자료가 존재하지 않습니다.');
							return;
					}

					if (magamData.substring(12,13) == 'Y'){
					 	  alert('마감작업을 할 수 없습니다. 마감이 완료된 상태입니다. 재작업을 원하실 경우 해제후 작업하여 주십시요.');
						  return;
					} else if (magamData.substring(13,14) == 'N'){
					 	  alert('마감작업을 할 수 없습니다. 공금예금잔액등록이 미완료 상태입니다.');
						  return;
				  } else if (magamData.substring(14,15) == 'N'){
						  alert('마감작업을 할 수 없습니다. 기타예금잔액등록이 미완료 상태입니다.');
						  return;
				  }

          if (mimagamDate != magamData.substring(4,12)) {
            alert("마감하려는 일자를 확인바랍니다.");
            return;
          }
			
					if (confirm("회계년도["+magamData.substring(0,4)+"] 회계일자["+magamData.substring(4,8)+"-"+magamData.substring(8,10)+"-"+magamData.substring(10,12)+ "]마감처리 하시겠습니까?")) { 
						 form.magamdata.value = magamData;
						 form.action = "IR070210.etax";
						 form.cmd.value = "workEndProc";
						 form.target = "_self";
						 eSubmit(form);
					}

			}

			function goMagamCancel(){
					var form = document.sForm;
					var magamData = getRadioValue();
          var magamDate = "<%=magamDate%>";

					if (magamData == '' ){
							alert('선택된 마감자료가 존재하지 않습니다.');
							return;
					}

					if (magamData.substring(12,13) == 'N'){
					 	  alert('마감 해제 작업을 할 수 없습니다. 마감 작업을 하신 후 작업하여 주십시요.');
						  return;
					}

          if (magamDate != magamData.substring(4,12)) {
            alert("해제하려는 일자를 확인바랍니다.");
            return;
          }
			
					if (confirm("회계년도["+magamData.substring(0,4)+"] 회계일자["+magamData.substring(4,8)+"-"+magamData.substring(8,10)+"-"+magamData.substring(10,12)+ "]마감해제 작업을 하시겠습니까?")) { 
						 form.magamdata.value = magamData;
						 form.action = "IR070210.etax";
						 form.cmd.value = "workEndProcCancel";
						 form.target = "_self";
						 eSubmit(form);
					}
			}
			
    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
  <form name="sForm" method="post" action="IR070210.etax">
	<input type="hidden" name="cmd" value="workEndList">
  <input type="hidden" name="magamdata" value="">
	  <table width="993" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title7_2.gif"></td>
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
                    <td width="860"><span style="width:50px"></span>
                    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                    회계연도&nbsp;
                    <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
                    <% for ( int i= this_int; i >=2010; i--) { %>
                      <option value="<%=i%>"><%=i%></option>
                    <% } %>
                    </select>
										</td>
										<td>
							        <img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand" align="absmiddle">  
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
          <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="list">	
					  <tr>
						  <th class="fstleft">선택</th>
						  <th>회계년도</th>
							<th>회계일자</th>
						  <th>일일업무마감여부</th>
						  <th>공급예금잔액 등록여부</th>
						  <th>기타예금잔액 등록여부</th>
					  </tr>
				    <%
					    for (int i=0; magamList != null && i < magamList.size(); i++) {
						    CommonEntity magamdata = (CommonEntity)magamList.get(i);
				    %>
								<tr> 
									<td class="fstleft">&nbsp;
										  <input type="radio" name="choice_magam" value="<%=magamdata.getString("M210_YEAR")%><%=magamdata.getString("M210_DATE")%><%=magamdata.getString("M210_WORKENDSTATE")%><%=magamdata.getString("M210_PUBLICDEPOSITSTATE")%><%=magamdata.getString("M210_ETCDEPOSITSTATE")%>" <%=(i==0)? "checked ":""%> > 
									</td>
									<td class="center">&nbsp;<%=magamdata.getString("M210_YEAR")%></td>
									<td class="center">&nbsp;
									<%=magamdata.getString("M210_DATE").substring(0,4)%>-<%=magamdata.getString("M210_DATE").substring(4,6)%>-<%=magamdata.getString("M210_DATE").substring(6,8)%></td>
									<td class="center">&nbsp;<%=("Y".equals(magamdata.getString("M210_WORKENDSTATE")))? "완료":"미완료" %></td>
									<td class="center">&nbsp;<%=("Y".equals(magamdata.getString("M210_PUBLICDEPOSITSTATE")))? "완료":"미완료"%></td>
									<td class="center">&nbsp;<%=("Y".equals(magamdata.getString("M210_ETCDEPOSITSTATE")))? "완료":"미완료"%></td>
								</tr>
				    <% } 
						   if (magamList == null || magamList.size() == 0) {  %>
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
							  <img src="../img/btn_end.gif" alt="마감" onClick="goMagam()" style="cursor:hand" align="absmiddle">
					      <img src="../img/btn_cancel2.gif" alt="해제" onClick="goMagamCancel()" style="cursor:hand" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  <table width="1000">
      <tr>
        <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	    </tr>
    </table>
  </form>
  </body>
</html>

<% if (!"".equals(retMsg) && retMsg != null) { %>
<script language=javascript>
    alert('<%=retMsg%>');
</script>
<% } %>
