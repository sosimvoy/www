<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050210.jsp
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
  List<CommonEntity> allotList  = (List<CommonEntity>)request.getAttribute("page.mn05.allotList");
	int allotListSize = 0;
	if (allotList != null ) {
		allotListSize = allotList.size();
	}

	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
  String allot_date = request.getParameter("allot_date");
	if ("".equals(allot_date) ) {
    allot_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
				form.action = "IR050210.etax";
				form.cmd.value = "bankAllotList";
				form.target = "_self";
      	eSubmit(form);
      }

			function PowerChk(flag) {
				form = document.sForm;
				form.v_flag.value = flag;
        form.action = "../common/super_one.etax";
        form.target = "hidFrame";
        eSubmit(form);
			}

			function goExcute(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("배정처리할 건을 체크하세요.");
					  return;
				  } else {
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S3")	{
							alert("세정과승인 건만 배정처리가 가능합니다.");
							return;
						}
            if (parseInt(form.allot_amt.value.replace(/,/g, ""), 10) <0 && form.edit_yn.value != "Y") {
              alert("반납 건은 요구정정 상태로 등록되어야 합니다. 세정과 취소후 사업소에서 요구정정으로 재등록하여야 합니다.");
              return;
            }
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S3")	{
							  alert("세정과승인 건만 배정처리가 가능합니다.");
							  return;
						  }
              if (parseInt(form.allot_amt[i].value.replace(/,/g, ""), 10) <0 && form.edit_yn[i].value != "Y") {
                alert("반납 건은 요구정정 상태로 등록되어야 합니다. 세정과 취소후 사업소에서 요구정정으로 재등록하여야 합니다.");
                return;
              }
					  }
				  }

				  if (cnt == 0)	{
					  alert("배정처리할 건을 체크하세요.");
					  return;
				  }
				}
        if (confirm("선택 건(들)을 배정처리하겠습니까?")) {
					form.trans_gubun.value = "123";
				  form.action = "IR050210.etax";
				  form.cmd.value = "bankAllotReg";
				  form.target = "_self";
				  eSubmit(form);
        }
			}


      function goDelete(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("배정취소할 건을 체크하세요.");
					  return;
				  } else {
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S4")	{
							alert("배정처리 건만 배정취소가 가능합니다.");
							return;
						}
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S4")	{
							  alert("배정처리 건만 배정취소가 가능합니다.");
							  return;
						  }
					  }
				  }

				  if (cnt == 0)	{
					  alert("배정취소할 건을 체크하세요.");
					  return;
				  }
				}
        if (confirm("선택 건(들)을 배정취소처리하겠습니까?")) {
					form.trans_gubun.value = "123";
				  form.action = "IR050210.etax";
				  form.cmd.value = "baeDelete";
				  form.target = "_self";
				  eSubmit(form);
        }
			}



			function goPermission(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("책임자승인할 건을 체크하세요.");
					  return;
				  } else {
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S4")	{
							alert("배정처리 건만 책임자승인이 가능합니다.");
							return;
						}
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S4")	{
							  alert("배정처리 건만 책임자승인이 가능합니다.");
							  return;
						  }
					  }
				  }

				  if (cnt == 0)	{
					  alert("책임자승인할 건을 체크하세요.");
					  return;
				  }
				}
        
			  form.trans_gubun.value = "125";
				form.action = "IR050210.etax";
				form.cmd.value = "bankAllotPms";
				form.target = "_self";
				eSubmit(form);
			}

      
			function goPermission2(){
				var form = document.sForm;
				var listSize = <%=allotListSize%>;
        var cnt = 0;
        
				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked)	{
					  alert("책임자승인할 건을 체크하세요.");
					  return;
				  } else {
						form.allot_yn.value = "Y";
						if (form.allot_state.value != "S4")	{
							alert("배정처리 건만 책임자승인이 가능합니다.");
							return;
						}
					}
				} else if (listSize > 1) {
					for (var i=0; i<form.allotChk.length; i++)	{
					  if (form.allotChk[i].checked)	{
						  cnt++;
							form.allot_yn[i].value = "Y";
							if (form.allot_state[i].value != "S4")	{
							  alert("배정처리 건만 책임자승인이 가능합니다.");
							  return;
						  }
					  }
				  }

				  if (cnt == 0)	{
					  alert("책임자승인할 건을 체크하세요.");
					  return;
				  }
				}
        
				if (confirm("선택 건(들)을 책임자승인처리하겠습니까?")) {
				  form.trans_gubun.value = "125";
				  form.action = "IR050211.etax";
				  form.cmd.value = "bankAllotTrs";
				  form.target = "hidFrame";
			  	eSubmit(form);		
				}
			}

      function goCancel() {
				var form = document.sForm;
				form.action = "IR050210.etax";
				form.cmd.value = "bankAllotRtn";
        form.allot_type.value = "1";
				form.target = "_self";
				eSubmit(form);
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
  <form name="sForm" method="post" action="IR050210.etax">
	<input type="hidden" name="cmd" value="bankAllotList">
	<input type="hidden" name="work_log" value="A05">
	<input type="hidden" name="trans_gubun" value="">
	<input type="hidden" name="err_code" value="">
	<input type="hidden" name="pms_cnt" value="">
	<input type="hidden" name="send_no" value="">
	<input type="hidden" name="err_code" value="">
  <input type="hidden" name="v_flag" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_2.gif"></td>
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
									    배정일자
										  <input type="text" class="box3" size="8" name="allot_date" value="<%=allot_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','allot_date')" align="absmiddle">
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
        <td width="975" height="20" align="right"> 단위(천원)</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" rowspan="2" width="4%">선택<input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th rowspan="2" width="10%">기관</th>
              <th rowspan="2" width="10%">예산현액</th>
							<th rowspan="2" width="10%">예산배정액</th>
							<th colspan="3" width="30%">자금</th>
							<th rowspan="2" width="10%">자금소요액</th>
							<th rowspan="2" width="10%">자금배정<br>요구액</th>
							<th rowspan="2" width="8%">처리상태</th>
							<th rowspan="2" width="8%">배정결과</th>
            </tr>
						<tr> 
              <th width="10%">기배정액</th>
              <th width="10%">지출액(누계)</th>
							<th width="10%">잔액</th>
            </tr>
						<% long comp_amt = 0L;
               long nocomp_amt = 0L;
               if (allotListSize > 0 && allotList != null) { 
							   for (int i=0; i < allotListSize; i++) {
									 CommonEntity rowData = (CommonEntity)allotList.get(i);
						%>
            <tr>
						  <% if ("S3".equals(rowData.getString("M100_ALLOTSTATE")) || "S4".equals(rowData.getString("M100_ALLOTSTATE")) ) { %>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
							<% } else { %>
							<td class="fstleft"><input type="hidden" name="allotChk" value="<%=i%>">&nbsp;</td>
							<% } %>
							<td class="center">&nbsp;<%=rowData.getString("M350_PARTNAME")%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_BUDGETAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_BUDGETALLOTAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ORIALLOTAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_TOTALJICHULAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_JANAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_SOYOAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("M100_ALLOTSTATE_NM")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M100_RST_NM")%></td>
							<input type="hidden" name="allot_state" value="<%=rowData.getString("M100_ALLOTSTATE")%>">
							<input type="hidden" name="year_list" value="<%=rowData.getString("M100_YEAR")%>">
							<input type="hidden" name="seq_list" value="<%=rowData.getString("M100_SEQ")%>">
							<input type="hidden" name="allot_amt" value="<%=rowData.getString("YOGU_AMT")%>">
							<input type="hidden" name="allot_yn" value="">
              <input type="hidden" name="edit_yn" value="<%=rowData.getString("M100_EDIT_YN")%>">
            </tr>
						<%  if ("S6".equals(rowData.getString("M100_ALLOTSTATE")) ) { 
                  comp_amt += rowData.getLong("M100_ALLOTAMT");
                } else if (!"S6".equals(rowData.getString("M100_ALLOTSTATE")) ) {
                  nocomp_amt += rowData.getLong("M100_ALLOTAMT");
                }   
              } %>

            <tr style="font-weight:bold">
              <td colspan="3" class="fstleft">미완료</td>
              <td colspan="2" class="right"><%=TextHandler.formatNumber(nocomp_amt)%></td>
              <td colspan="3" class="center">배정완료</td>
              <td colspan="3" class="right"><%=TextHandler.formatNumber(comp_amt)%></td>
            </tr>
		        <% } else { %>
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
							<img src="../img/btn_baedeal.gif" alt="배정처리" style="cursor:hand" onClick="goExcute()" align="absmiddle">&nbsp;
              <img src="../img/btn_bae_cancel.gif" alt="배정취소" style="cursor:hand" onClick="goDelete()" align="absmiddle">&nbsp;
							<img src="../img/btn_adminok.gif" alt="책임자승인" style="cursor:hand" onClick="PowerChk('P')" align="absmiddle">
							</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
		<iframe name="hidFrame" width="0" height="0"></iframe>
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