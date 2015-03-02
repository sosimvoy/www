<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR060810.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금인출요구등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%

  List<CommonEntity> bankOutList = (List<CommonEntity>)request.getAttribute("page.mn06.bankOutList");
  Integer totalCount = (Integer)request.getAttribute("page.mn06.totalCount");	//총 카운트
	if (totalCount == null ) {
	  totalCount = 0;
	}
	String cpage = request.getParameter("cpage");	// 현재 페이지 번호 받기
	if( "".equals(cpage) ) {
	 cpage = "1";
	}
	String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
	if (SucMsg == null) {
		SucMsg = "";
	}
	int bankOutListSize = 0;
	if (bankOutList != null ) {
		bankOutListSize = bankOutList.size();
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
     
      function goSearch(cpage) {
				var form = document.sForm;
        if(cpage != null) {
      		form.cpage.value=cpage;
      	} else {
      		form.cpage.value="";
      	}
				form.action = "IR060810.etax";
				form.cmd.value = "bankOutList";
      	eSubmit(form);
      }

      function PAGE(cpage) {
      	goSearch(cpage);
      }


			function goRegister() {
				var form = document.sForm;
				var cnt = 0;
				var listSize = 0;
				listSize = <%=bankOutListSize%>;

				if (listSize == 0) {
					alert("조회내역이 없습니다.");
					return;
				}

				if (listSize == 1) {
					if (!form.allotChk.checked) {
						alert("등록할 건을 선택하세요.");
						return;
					}

          if (parseInt(form.req_amt.value.replace(/,/g, ""), 10) < parseInt(form.dep_amt.value.replace(/,/g, ""), 10)) {
            alert("인출 가능액("+form.req_amt.value+")을 초과할 수 없습니다.");
            return;
          }

          if (parseInt(form.req_amt.value.replace(/,/g, ""), 10) - parseInt(form.dep_amt.value.replace(/,/g, ""), 10) != 0) {
            if (form.mmda_cancel.value == "Y") {
              alert("mmda 해지는 계좌잔액이 0이 되어야 합니다.");
              return;
            }
          }
				} else {
					for (var i=0; i<form.allotChk.length; i++) {						
						if (form.allotChk[i].checked)	{
							cnt++;
						}

            if (parseInt(form.req_amt[i].value.replace(/,/g, ""), 10) < parseInt(form.dep_amt[i].value.replace(/,/g, ""), 10)) {
              alert("인출 가능액("+form.req_amt[i].value+")을 초과할 수 없습니다.");
              return;
            }

            if (parseInt(form.req_amt[i].value.replace(/,/g, ""), 10) - parseInt(form.dep_amt[i].value.replace(/,/g, ""), 10) != 0) {
              if (form.mmda_cancel[i].value == "Y") {
                alert("mmda 해지는 계좌잔액이 0이 되어야 합니다.");
                return;
              }
            }
					}

					if (cnt ==0) {
						alert("등록할 건을 선택하세요.");
						return;
					}
				}
        if (confirm("선택 건(들)을 인출 요구등록하겠습니까?")) {
          form.cmd.value = "inchulReqReg";
			  	form.action = "IR060810.etax";
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
  <form name="sForm" method="post" action="IR060810.etax">
	<input type="hidden" name="cmd" value="bankOutList">
  <input type="hidden" name="cpage" value="">
  <input type="hidden" name="work_log" value="A06">
  <input type="hidden" name="trans_gubun" value="181">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_11.gif"></td>
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
              <th class="fstleft" width="4%">선택<br><input type="checkbox" name="allchk" onClick="change_check_box_status(sForm.allotChk, this.checked);"></th>
              <th width="5%">회계<br>연도</th>
							<th width="7%">예금<br>종류</th>
							<th width="12%">계좌번호</th>
							<th width="6%">좌수<br>번호</th>
							<th width="8%">신규일</th>
							<th width="8%">만기일</th>
							<th width="6%">예탁<br>기간</th>
							<th width="5%">이율(%)</th>
							<th width="12%">원금</th>
							<th width="14%">원금인출/<br>해지금액</th>
							<th width="8%">자금<br>종별</th>	
              <th width="5%">mmda<br>해지</th>
            </tr>
						<% if (bankOutListSize > 0 && bankOutList != null) { 
							   String due_date = "";
                 long possible_amt = 0L;
							   for (int i=0; i < bankOutListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankOutList.get(i);
									 String state = rowData.getString("DEPOSIT_TYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("DEP_PERIOD") + " 개월";
									 } else if ("G2".equals(state) ) {
										 due_date = rowData.getString("DEP_PERIOD") + " 일";
									 }
                   possible_amt = rowData.getLong("DEP_AMT") - rowData.getLong("M140_REQAMT");
						%>
            <tr>
              <td class="fstleft"><input type="checkbox" name="allotChk" value="<%=i%>"></td>
              <td class="center">&nbsp;<%=rowData.getString("FIS_YEAR")%></td>
							<td class="center">&nbsp;<%=rowData.getString("DEPOSIT_NAME")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("ACCT_NO"))%></td>
              <td class="right">&nbsp;<%=rowData.getString("JWASU_NO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("NEW_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("END_DATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
							<td class="right">&nbsp;<%=rowData.getString("DEP_RATE")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("DEP_AMT"))%></td>
              <td class="right"><input type="text" name="dep_amt" value="<%=possible_amt%>" size="15" userType="money" <%if (!"G3".equals(rowData.getString("DEPOSIT_TYPE")) && !"G4".equals(rowData.getString("DEPOSIT_TYPE")) ) { %> readonly <% } %>></td>
							<td class="center">
							  <select name="mmda_type">
								  <option value="G1">공금예금</option>
									<option value="G2">정기예금</option>
									<option value="G3">환매채</option>
									<option value="G4">MMDA</option>
								</select>
							</td>
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
							<input type="hidden" name="dep_gubun" value="<%=rowData.getString("DEPOSIT_TYPE")%>">
							<input type="hidden" name="acct_no" value="<%=rowData.getString("ACCT_NO")%>">
							<input type="hidden" name="jwasu_no" value="<%=rowData.getString("JWASU_NO")%>">
              <input type="hidden" name="req_amt" value="<%=possible_amt%>">
            </tr>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="12">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <%// 페이지 %>
          <table width="100%">
            <tr>
              <td align="center">
                <br>
                <etax:pagelink totalCnt='<%=totalCount.intValue()%>' page='<%=cpage%>'	pageCnt='10' blockCnt='10'  />
              </td>
            </tr>
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