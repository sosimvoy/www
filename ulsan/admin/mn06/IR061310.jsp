<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061310.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금인출(기금,특별)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> acctList = (List<CommonEntity>)request.getAttribute("page.mn06.acctList");
  List<CommonEntity> partList = (List<CommonEntity>)request.getAttribute("page.mn06.partList");

  List<CommonEntity> inchulSpList = (List<CommonEntity>)request.getAttribute("page.mn06.inchulSpList");

	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int inchulSpListSize = 0;
	if (inchulSpList != null ) {
		inchulSpListSize = inchulSpList.size();
	}

  String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
  
  String to_day = TextHandler.getCurrentDate();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      var to_day = "<%=to_day%>";
      function init() {
  	    typeInitialize();
      }

      function changeAcc(acct_kind) {
				var form = document.sForm;
				document.hidFrame.location = "IR061311.etax?cmd=inchulSpList&fis_year="+form.fis_year.value+"&acct_kind="+acct_kind;
			}


      function changePart(part_code) {
				var form = document.sForm;
				document.hidFrame.location = "IR061311.etax?cmd=inchulSpList&fis_year="+form.fis_year.value+"&acct_kind="+form.acct_kind.value+"&part_code="+part_code;
			}
     
      function goSearch() {
				var form = document.sForm;
        
        if (form.part_code.value == "") {
          alert("부서코드를 선택하세요.");
          form.part_code.focus();
          return;
        }

				form.action = "IR061310.etax";
				form.cmd.value = "inchulSpList";
      	eSubmit(form);
      }

			function goRegister() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=inchulSpListSize%>;
        
        if (form.part_code.value == "") {
          alert("부서코드를 선택하세요.");
          form.part_code.focus();
          return;
        }

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("등록할 건을 선택하세요.");
						return;
					}

          if (form.cancel_date.value == "") {
            alert("해지일은 필수 입력입니다.");
            return;
          }
          
          if (replaceStr(form.cancel_date.value, "-", "") > to_day) {
            alert("금일보다 큰 해지일자는 입력이 불가합니다.");
            return;
          }
          
          if (parseInt(form.dep_amt.value.replace(/,/g, ""), 10) - parseInt(form.input_amt.value.replace(/,/g, ""), 10) != 0) {
            if (form.mmda_cancel.value == "Y") {
              alert("mmda 해지는 계좌잔액이 0이 되어야 합니다.");
              return;
            }
          }
				} else {
					for (var i=0; i<form.allotChk.length; i++) {						
						if (form.allotChk[i].checked)	{
              
              if (form.cancel_date[i].value == "") {
                alert("해지일은 필수 입력입니다.");
                return;
              }

              if (replaceStr(form.cancel_date[i].value, "-", "") > to_day) {
                alert("금일보다 큰 해지일자는 입력이 불가합니다.");
                return;
              }
              
              if (parseInt(form.dep_amt[i].value.replace(/,/g, ""), 10) - parseInt(form.input_amt[i].value.replace(/,/g, ""), 10) != 0) {
                if (form.mmda_cancel[i].value == "Y") {
                  alert("mmda 해지는 계좌잔액이 0이 되어야 합니다.");
                  return;
                }
              }
							cnt++;
						}
					}

					if (cnt ==0) {
						alert("등록할 건을 선택하세요.");
						return;
					}
				}
        if (confirm("선택 건(들)을 등록하겠습니까?")) {
          form.cmd.value = "inchulSpReg";
			  	form.action = "IR061310.etax";
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
  <form name="sForm" method="post" action="IR061310.etax">
	<input type="hidden" name="cmd" value="inchulSpList">
  <input type="hidden" name="cpage" value="">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="239">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_23.gif"></td>
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
                      <span style="width:40"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도
											<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
                      <span style="width:40"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계구분
											<select name="acct_kind" iValue="<%=request.getParameter("acct_kind")%>" onchange="changeAcc(this.value)">
												<option value="B">특별회계</option>
                        <option value="D">세입세출외현금</option>
												<option value="E">기금</option>
											</select><span style="width:40"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                      부서
                      <select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changePart(this.value)">
                      <%for (int i=0; partList != null && i<partList.size(); i++) {
                        CommonEntity partInfo = (CommonEntity)partList.get(i); %>
												<option value="<%=partInfo.getString("M350_PARTCODE")%>"><%=partInfo.getString("M350_PARTNAME")%></option>
                      <% } %>
											</select><span style="width:40"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
											회계명
											<select name="acct_list" iValue="<%=request.getParameter("acct_list")%>">
                      <!--<option value="">전체선택</option>-->
											<% for(int i=0; acctList != null && i < acctList.size(); i++ ) { 
												   CommonEntity acctInfo = (CommonEntity)acctList.get(i);
											%>
											  <option value="<%=acctInfo.getString("M360_ACCCODE")%>"><%=acctInfo.getString("M360_ACCNAME")%></option>
											<% } %>	
											</select>
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
              <th class="fstleft" width="3%">택<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="4%">회계<br>연도</th>
							<th width="5%">예금<br>종류</th>
							<th width="10%">계좌번호</th>
							<th width="5%">좌수<br>번호</th>
							<th width="9%">신규일</th>
							<th width="9%">만기일</th>
							<th width="6%">예탁<br>기간</th>
							<th width="4%">이율(%)</th>
							<th width="10%">원금</th>
							<th width="10%">원금인출/<br>해지금액</th>
							<th width="6%">자금<br>종별</th>		
              <th width="8%">인출/<br>해지일</th>
							<th width="8%">해지<br>이자</th>	
              <th width="3%">mmda<br>해지</th>	
            </tr>
						<% if (inchulSpListSize > 0 && inchulSpList != null) { 
							   String due_date = "";
							   for (int i=0; i < inchulSpListSize; i++) {
									 CommonEntity rowData = (CommonEntity)inchulSpList.get(i);
									 String state = rowData.getString("DEPOSIT_TYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("DEP_PERIOD") + " 개월";
									 } else if ("G2".equals(state)) {
										 due_date = rowData.getString("DEP_PERIOD") + " 일";
									 } else {
                     due_date = "";
                   }
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
              <td class="center"><%=rowData.getString("FIS_YEAR")%></td>
							<td class="center"><%=rowData.getString("DEPOSIT_NAME")%></td>
              <td class="center"><%=TextHandler.formatAccountNo(rowData.getString("ACCT_NO"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("JWASU_NO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("NEW_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("END_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
							<td class="right">&nbsp;<%=rowData.getString("DEP_RATE")%></td>
							<td class="right"><%=TextHandler.formatNumber(rowData.getString("DEP_AMT"))%></td>
              <td class="right"><input type="text" class="box3" name="input_amt" value="<%=rowData.getString("DEP_AMT")%>" size="11" userType="money" <%if (!"G3".equals(rowData.getString("DEPOSIT_TYPE")) && !"G4".equals(rowData.getString("DEPOSIT_TYPE")) ) { %> readonly <% } %>></td>
							<td class="center">
							  <select name="mmda_type" style="width:75">
								  <option value="G1">공금예금</option>
									<option value="G2">정기예금</option>
									<option value="G3">환매채</option>
									<option value="G4">MMDA</option>
								</select>
							</td>
              <td class="right"><input type="text" class="box3" name="cancel_date" size="7" userType="date"></td>
              <td class="right"><input type="text" class="box3" name="cancel_ija" size="9" userType="money"></td>
              <td>
                <% if ("G3".equals(state)) { %>
                <select name="mmda_cancel">
                  <option value="N">N</option>
                  <option value="Y">Y</option>
                </select>
                <% } else { %>
                &nbsp;<input type="hidden" name="mmda_cancel" value="N">
                <% } %>
              </td>
							<input type="hidden" name="deposit_type" value="<%=rowData.getString("DEPOSIT_TYPE")%>">
							<input type="hidden" name="acct_no" value="<%=rowData.getString("ACCT_NO")%>">
							<input type="hidden" name="jwasu_no" value="<%=rowData.getString("JWASU_NO")%>">
              <input type="hidden" name="due_day" value="<%=rowData.getString("DEP_PERIOD")%>">
              <input type="hidden" name="dep_amt" value="<%=rowData.getString("DEP_AMT")%>">
            </tr>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="14">&nbsp;</td>
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
              <td><img src="../img/btn_order.gif" alt="등록" style="cursor:hand" onClick="goRegister()" align="absmiddle"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	  </form>
		<iframe name="hidFrame" width="0" height="0"></iframe>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </body>
	<%
  if (!"".equals(SucMsg)) { %>
	<script>
	alert("<%=SucMsg%>");
	</script>
	<%
	} %>
</html>