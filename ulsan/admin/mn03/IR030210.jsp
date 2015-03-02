<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030210.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입세출외현금 > 수기분 조회/수정/삭제
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn03.jsp" %>
<%
	List<CommonEntity> cashList = (List<CommonEntity>)request.getAttribute("page.mn03.cashList");
	List<CommonEntity> accNameList  = (List<CommonEntity>)request.getAttribute("page.mn03.accNameList");
	List<CommonEntity> cashDeptList  = (List<CommonEntity>)request.getAttribute("page.mn03.cashDeptList");
  CommonEntity dateInfo = (CommonEntity)request.getAttribute("page.mn01.fis_date");  //회계일자

  String delMsg =  (String)request.getAttribute("page.mn03.delMsg");
	if (delMsg == null) {
		delMsg = "";
	}

	int listSize = 0;
	if (cashList != null && cashList.size() != 0) {
		listSize = cashList.size();
	}
	
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);
   
	String fis_date = request.getParameter("fis_date");
  if (fis_date == null || "".equals(fis_date)) { 
    fis_date = TextHandler.formatDate(dateInfo.getString("FIS_DATE"),"yyyyMMdd","yyyy-MM-dd");
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
		    form.action = "IR030210.etax";
		    form.cmd.value = "cashList2";
		    form.target = "_self";
		    eSubmit(form);
	    }
 
	    function goDelete(){
		    var form = document.sForm;
	      var listSize = <%=listSize%>;
		    var cnt = 0;

        if (listSize == 0) {
		      alert("조회내역이 없습니다.");
			    return;
		    }
		    if (listSize == 1) {
		      if (!form.chk_yn.checked) {
			      alert("삭제할 건을 선택하세요");
				    return;
			    }
		    } else if (listSize > 1) {
		        for (var i=0; i<listSize; i++) {
			        if (form.chk_yn[i].checked) {
                cnt++;
                form.pop_part_code.value = form.chk_part_code[i].value;
			        }
			      }    
			 
            if (cnt == 0) {
		          alert("삭제할 건을 선택하세요");
			        return;
			      }
		      }
         form.action = "IR030210.etax";  
         form.cmd.value ="cashDelete";
		     form.target = "_self";
		     eSubmit(form);
	      }
	    
      function goView(){
        var form = document.sForm;
		    var listSize = <%=listSize%>;
		    var cnt = 0;

		    if (listSize == 0) {
		      alert("조회내역이 없습니다.");
			    return;
		    }

		    if (listSize == 1) {
	        if (!form.chk_yn.checked) {
			      alert("수정할 건을 선택하세요");
				    return;
			    }

          form.pop_part_code.value = form.chk_part_code.value;
          form.pop_acc_code.value = form.chk_acc_code.value;

		    } else if (listSize > 1) {
			      for (var i=0; i<listSize; i++) {
				      if (form.chk_yn[i].checked) {
              cnt++;
              form.pop_part_code.value = form.chk_part_code[i].value;
              form.pop_acc_code.value = form.chk_acc_code[i].value;
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
         window.open('IR030211.etax', 'popView' ,'height=560,width=450,top=100,left=100,scrollbars=no');
		     form.action = "IR030211.etax"
		     form.cmd.value = "cashView";
		     form.target = "popView";
		     eSubmit(form);
        }
				
		  function changeDept(dept) {
				document.hidFrame.location = "IR030212.etax?cmd=cashList&part_code="+dept;
			}

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" oncontextmenu="return false">
  <form name="sForm" method="post" action="IR030210.etax">
  <input type="hidden" name="cmd" value="cashList2">
	<input type="hidden" name="m040_seq" value="">
	<input type="hidden" name="org_year" value="">
   <!-- 팝업시 보내는 코드값 -->                                               
	<input type="hidden" name="pop_part_code" value="">
	<input type="hidden" name="pop_acc_code" value="">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title3_3.gif"></td>
      </tr>
      <tr> 
        <td width="15">&nbsp;</td>
        <td width="978" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							    <tr>
								    <td>
									    <span style="width:5px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 		
									    회계연도&nbsp;
									    <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
										    <option value="<%=this_year%>"><%=this_year%></option>
										    <option value="<%=last_year%>"><%=last_year%></option>
									    </select>
									    <span style="width:5px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
									    회계일자&nbsp; 
											<input type="text" name="fis_date" class="box3" size="8" value="<%=fis_date%>" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
											<span style="width:5px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    부서&nbsp;
											<select name="part_code" iValue="<%=request.getParameter("part_code")%>" onchange="changeDept(this.value)">
										    <option value="">전체
                        <% for (int i=0; cashDeptList != null && i < cashDeptList.size(); i++) {
									         CommonEntity deptInfo = (CommonEntity)cashDeptList.get(i); %>												    
										    <option value="<%=deptInfo.getString("M350_PARTCODE")%>"><%=deptInfo.getString("M350_PARTNAME")%></option>
									      <% } %>
								      </select>
									    <span style="width:5px"></span>
									    <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 	
								      회계명&nbsp; 
									  	<select name="acc_code" iValue="<%=request.getParameter("acc_code")%>">
										    <option value="">전체
                        <% for (int i=0; accNameList != null && i < accNameList.size(); i++) {
									         CommonEntity nameInfo = (CommonEntity)accNameList.get(i); %>												    
										    <option value="<%=nameInfo.getString("M360_ACCCODE")%>"><%=nameInfo.getString("M360_ACCNAME")%></option>
									    <% } %>
								      </select>
								    </td>
								    <td width="123"> 
                      <img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand">
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
        <td width="975" height="20"></td>
		  </tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
				    <tr> 
					    <th class="fstleft" width="4%">선택</th>
							<th width="8%">회계연도</th>
					    <th width="8%">회계일자</th>
					    <th width="8%">부서</th>
      		    <th width="8%">회계명</th>
							<th width="7%">입금지급구분</th>
					    <th width="7%">입력구분</th>
					    <th width="7%">현금구분</th>
					    <th width="7%">예금종류</th>
							<th width="8%">채주성명</th>
							<th width="8%">비고</th>
					    <th width="6%">지급번호</th>
					    <th width="5%">건수</th>
					    <th width="10%">금액</th>
				    </tr>
				    <%
					    for (int i=0; cashList != null && i < cashList.size(); i++) {
						    CommonEntity data = (CommonEntity)cashList.get(i);
				    %>
				    <tr>
              <input type="hidden" name="chk_part_code" value="<%=data.getString("M040_PARTCODE")%>">
              <input type="hidden" name="chk_acc_code" value="<%=data.getString("M040_ACCCODE")%>">
			  	    <td class="fstleft">&nbsp;<input type="checkbox" name="chk_yn" value="<%=i%>"></td>
							<td class="center">&nbsp;<%=data.getString("M040_YEAR")%></td>
              <td class="center">&nbsp;
							<%=data.getString("M040_DATE").substring(0,4)%>-<%=data.getString("M040_DATE").substring(4,6)%>-<%=data.getString("M040_DATE").substring(6,8)%></td>
					    <td class="center">&nbsp;<%=data.getString("PARTCODE")%></td>
              <td class="center">&nbsp;<%=data.getString("ACCCODE")%></td>
							<td class="center">&nbsp;<%=data.getString("M040_DWTYPE_NM")%></td>
              <td class="center">&nbsp;<%=data.getString("M040_INTYPE_NM")%></td>
              <td class="center">&nbsp;<%=data.getString("M040_CASHTYPE_NM")%></td>
              <td class="center">&nbsp;<%=data.getString("M040_DEPOSITTYPE_NM")%></td>
					    <td class="center">&nbsp;<%=data.getString("M040_ORDERNAME")%></td>
              <td class="center">&nbsp;<%=data.getString("M040_NOTE")%></td>
							<td class="center">&nbsp;<%=data.getString("M040_ORDERNO")%></td>
							<td class="center">&nbsp;<%=data.getString("M040_CNT")%></td>
					    <td class="right">&nbsp;<%=TextHandler.formatNumber(data.getString("M040_AMT"))%>원</td>
					    <input type="hidden" name="year_list" value="<%=data.getString("M040_YEAR")%>">
					    <input type="hidden" name="seq_list" value="<%=data.getString("M040_SEQ")%>">
				    </tr>
				    <%				 
					   } if (cashList == null || cashList.size() == 0) { 
				    %>
						<tr>
						  <td class="fstleft" colspan="14">&nbsp;</td>
						</tr>            
						<%
					  	}
						%>
			    </table>
			  </td>
			</tr>
			  <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				    <tr>
					    <td>
							  <img src="../img/btn_modify.gif" alt="수정" onClick="goView()" style="cursor:hand" align="absmiddle">
							  <img src="../img/btn_delete.gif" alt="삭제" onClick="goDelete()" style="cursor:hand" align="absmiddle">
							</td>
				    </tr>
			    </table>
		    </td>
	    </tr>
		</table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
	<iframe name="hidFrame" width="0" height="0"></iframe>
	<% if (!"".equals(delMsg)) { %>
	  <script>
	  alert("<%=delMsg%>");
		</script>
	<% } %>
  </body>
</html>