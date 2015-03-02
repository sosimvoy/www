<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040510.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-09
* 프로그램내용	   : 세외수입 > 과오납징수결의 조회/삭제
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
	List<CommonEntity>gwaonapList = (List<CommonEntity>)request.getAttribute("page.mn04.gwaonapList");
	String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String to_day = TextHandler.getCurrentDate();

  int listSize = 0;
	if (gwaonapList != null && gwaonapList.size() > 0) {
		listSize = gwaonapList.size();
	}

	String su_sdate			  = request.getParameter("su_str_date");
	String su_edate			  = request.getParameter("su_end_date");

	if (su_sdate.equals("")) {
		su_sdate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}  

	if (su_edate.equals("")) {
		su_edate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
        var to_day = "<%=to_day%>";

				 if (replaceStr(form.su_str_date.value, "-", "") > to_day) {
          alert("조회일자가 금일보다 큽니다.");
          return;
        }

        if (replaceStr(form.su_str_date.value, "-", "") > replaceStr(form.su_end_date.value, "-", "")) {
          alert("조회종료일이 조회시작일보다 큽니다.");
          return;
        }

				form.action = "IR040510.etax";  
				form.cmd.value = "gwaonapList";
				form.target = "_self";
				eSubmit(form);
			}

		 function goView(seq, gwaonap, documentno, year){
			var form = document.sForm;
			var listSize = <%=listSize%>;
			var cnt = 0;

			if (listSize == 0) {
				alert("조회내역이 없습니다.");
				return;
			}
			if (listSize == 1) {
				if (!form.userChk.checked) {
					alert("결과통지할 건을 선택하세요");
					return;
				}
        cnt =1;
        form.seq.value =  form.seq_list.value;
        
			} else if (listSize > 1) {
				for (var i=0; i<listSize; i++) {
					if (form.userChk[i].checked) {
            form.seq.value = form.seq_list[i].value;
						cnt++;
					}
				}
				if (cnt == 0) {
          if ( seq < 1) {
            alert("결과통지 자료를 선택하세요");
	  				return;
          }else {
            form.seq.value =  seq;
		      }
        } 
				if (cnt > 1) {
          alert("결과통지 한 건만 선택하세요");
					return;
        }
        
    	}
			if (cnt == 0) {
				form.procyn.value = "N";
			}else {
        form.procyn.value = "Y";
      }
      if (form.seq.value != seq && seq > 0) {
        alert("체크된 결과통지 대상자료와 클릭된 물건명의 일련번호가 다릅니다.");
        return;
      }
       form.cmd.value ="gwaonapView";
			 form.action = "IR040511.etax"; 
			 
       window.open('IR040511.etax','reportPop' ,'height=600,width=460,top=10,left=200,scrollbars=yes'); 
			 form.target = "reportPop";
			 eSubmit(form);
		 }		  

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR040510.etax">
	<input type="hidden" name="cmd" value="gwaonapList">
  <input type="hidden" name="seq" value="">
  <input type="hidden" name="procyn" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title4_6.gif"></td>
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
									    <span style="width:550px"></span>
											<span class="point">조회일자</span>
											<input type="text" style="width:80" name="su_str_date" value="<%=su_sdate%>" userType="date" required desc="시작일"> 
											<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_str_date');" style="cursor:hand"> -
											<input type="text" style="width:80" name="su_end_date" value="<%=su_edate%>" userType="date" required desc="종료일"> 
											<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_end_date');" style="cursor:hand">
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
              <th class="fstleft" width="4%" rowspan="3">선택</th>
							<th width="9%">부과번호</th>
              <th width="9%">납부자명</th>
							<th width="9%"rowspan="3" valign="top">우편번호</th>
							<th width="9%">납부자주소</th>
							<th width="9%">과오납일자</th>
							<th width="9%">이자일수</th>
							<th width="9%" rowspan="3" valign="top">가산금</th>
							<th width="9%">과오납금액</th>
							<th width="8%">충당금액</th>
							<th width="8%">과오납사유</th>
							<th width="8%">지급일자</th>
						 </tr>
						 <tr>
							<th width="9%">수납순번</th>
							<th width="9%">주민등록번호</th>		
							<th width="9%" rowspan="2" valign="top">물건명</th>
							<th width="9%">충당일자</th>
							<th width="9%">환부통지일자</th>							
							<th width="9%">이자</th>
							<th width="8%">환급금액</th>
							<th width="8%" rowspan="2" valign="top">환부방법</th>
							<th width="8%">지급번호</th>
            </tr>
						 <tr>
							<th width="9%">부서</th>
							<th width="9%">과목</th>	
							<th width="9%">환부일자</th>
							<th width="9%">지급일자</th>							
							<th width="9%">합계</th>
							<th width="8%">결의일자</th>
							<th width="8%">문서번호</th>
            </tr>


						<%
						for (int i=0; gwaonapList != null && i < gwaonapList.size(); i++) {
							CommonEntity data = (CommonEntity)gwaonapList.get(i);
						%>
     
            <tr>                 
              <td class="fstleft" rowspan="3">
              <% if (!"R2".equals(data.getString("M090_GWAONAPSTATECODE")) ) { %>
              <input type="checkbox" name="userChk" value="<%=i%>">
              <% } else if ("R2".equals(data.getString("M090_GWAONAPSTATECODE")) ) { %>
              &nbsp;<input type="checkbox" name="userChk" value="<%=i%>" disabled>
              <% } %>
              </td>
							<td>&nbsp;<%=data.getString("M090_BUGWANO")%></td>
              <td>&nbsp;<%=data.getString("M090_NAPBUJANAME")%></td>
					    <td rowspan="3">&nbsp;<%=data.getString("")%></td>
							<td>&nbsp;<%=data.getString("M090_NAPBUJAADDRESS")%></td>						
							<td>&nbsp;<%=data.getString("M090_GWAONAPDATE")%></td>
							<td>&nbsp;<%=data.getString("M090_INTERESTDAY")%></td>
							<td rowspan="3">&nbsp;<%=TextHandler.formatNumber(data.getString("M090_GASANAMT"))%></td>
					    <td>&nbsp;<% if (!"".equals(data.getString("M090_DOCUMENTNO")) ) { %>
              <a href="javascript:goView('<%=data.getString("M090_SEQ")%>', '<%=data.getString("M090_GWAONAPSTATECODE")%>', '<%=data.getString("M090_DOCUMENTNO")%>', '<%=data.getString("M090_YEAR")%>')">
              <font color="red"><%=TextHandler.formatNumber(data.getString("M090_BONAMT"))%></font>
              </a>
              <% } else { %>
              <%=TextHandler.formatNumber(data.getString("M090_BONAMT"))%>
              <% } %>
              </td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_BONAMT"))%></td>
							<td>&nbsp;<%=data.getString("M090_GWAONAPREASON")%></td>
							<td>&nbsp;<%=data.getString("M090_NAPIBDATE")%></td>
            </tr>
						 <tr>                 
					    <td>&nbsp;01</td>
							<td>&nbsp;<%=data.getString("M090_JUMINNO")%></td>						
							<td rowspan="2">&nbsp;
              <%=data.getString("M090_BUSINESSNAME")%>
              </td>
							<td>&nbsp;<%=data.getString("M090_NAPBUJANAME")%></td>
							<td>&nbsp;<%=data.getString("M090_BALUIDATE")%></td>
					    <td>&nbsp;<%=TextHandler.formatNumber(data.getString("M090_INTERESTAMT"))%></td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("TOT_AMT"))%></td>
							<td rowspan="2">&nbsp;송금</td>
							<td>&nbsp;</td>
            </tr>
						 <tr>                 
					    <td>&nbsp;</td>
							<td>&nbsp;</td>						
							<td>&nbsp;</td>
							<td>&nbsp;<%=data.getString("M090_BALUIDATE")%></td>
							<td>&nbsp;<%=TextHandler.formatNumber(data.getString("TOT_AMT"))%></td>
					    <td>&nbsp;<%=data.getString("M090_BALUIDATE")%></td>
							<td>&nbsp;<%=data.getString("M090_DOCUMENTNO")%></td>
							<input type="hidden" name="seq_list" value="<%=data.getString("M090_SEQ")%>">
            </tr>
					  <%				 
						} if (gwaonapList == null) { 
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
								<img src="../img/btn_inform.gif" alt="결과통지" style="cursor:hand" onClick="goView(0,0,0,0)" align="absmiddle">
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